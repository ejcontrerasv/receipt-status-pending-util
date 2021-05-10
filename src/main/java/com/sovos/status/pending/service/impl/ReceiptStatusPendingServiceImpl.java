package com.sovos.status.pending.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sovos.status.pending.dto.DocStatusInfo;
import com.sovos.status.pending.dto.DocumentOutbound;
import com.sovos.status.pending.models.ReceiptStatusPending;
import com.sovos.status.pending.repo.JDBCTemplateReceiptStatusRepo;
import com.sovos.status.pending.service.IReceiptStatusPendingService;
import com.sovos.status.pending.utils.CastingHelper;
import com.sovos.status.pending.utils.Constants;
import com.sovos.status.pending.utils.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReceiptStatusPendingServiceImpl implements IReceiptStatusPendingService{
	
	@Autowired
	private JDBCTemplateReceiptStatusRepo repo;
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public List<ReceiptStatusPending> executeProcess(Integer companyId) {
		
		List<ReceiptStatusPending> list = null;
		String message = "";
		String messageInitial = "";
		String messageError = "";
		boolean isByCompanyId = false;
		
		if (companyId != null && companyId > 0) {
			isByCompanyId = true;
			messageInitial = "BY-COMPANYID";
			message = String.format("para empresa con coid: [%s]", companyId);
			list = repo.findByCompanyId(companyId);
			messageError = String.format("No se han encontrado documentos con estado pendiente para empresa con coid: [%s]", companyId);
		} else {
			list = repo.findAll();
			messageInitial = "ALL-COMPANIES";
			messageError = "No se han encontrado documentos con estado pendiente";
		}
		
		log.info(String.format("################################### INITIATED PROCESS %s ###################################", messageInitial));
		
		if (list == null || list.size() == 0) {
			log.info(messageError);
		} else {
			
			String msg = String.format("Se han encontrado [%s] documentos con estado pendiente ", list.size());
			message = isByCompanyId ? msg + message : msg; 
			log.info(message);
			
			for (ReceiptStatusPending receipt : list) {
				
				int idCompany = isByCompanyId ? companyId : receipt.getRspcompid();
				
					
				log.info(String.format("Se obtiene documento de receiptstatuspending con Id: [%s] ,Company: [%s] ,Folio: [%s] ,TipoDte: [%s] ,Digest [%s] ",
						receipt.getRspid(), idCompany, receipt.getRspfolio(), receipt.getRspdoctype(), receipt.getRspdigest()));
				Optional<DocumentOutbound> op = repo.findDocumentByCoidByDocidBySequenceByTypeByDigest(idCompany, receipt.getRspfolio(), receipt.getRspdoctype(), receipt.getRspdigest());
				
				if (op.isPresent()) {
					DocumentOutbound docemi = op.get();
					
					DocStatusInfo docStatusInfo = mapper.ReceiptStatusPendingToDocStatusInfo(receipt, docemi);
					log.info("docStatusInfo id: [{}]", docStatusInfo.getId());
					log.info("docStatusInfo code: [{}]", docStatusInfo.getCode());
					log.info("docStatusInfo detail: [{}]", docStatusInfo.getDetail());
					log.info("docStatusInfo docid: [{}]", docStatusInfo.getDocId());
					log.info("docStatusInfo doctype: [{}]", docStatusInfo.getDocType());
					log.info("docStatusInfo insertdate: [{}]", docStatusInfo.getInsertDate());
					log.info("docStatusInfo message: [{}]", docStatusInfo.getMessage());
					log.info("docStatusInfo rutemi: [{}]", docStatusInfo.getRutEmi());
					
					repo.updateDocumentStatusByDocStatusInfo(docStatusInfo.getCode(), docStatusInfo.getMessage(), docStatusInfo.getInsertDate(), docemi.getDedocid());
					
					String detailMessage = Constants.SII_SENT_MESSAGE + docStatusInfo.getInsertDate().toString();
					repo.insertDocumentLogByDocStatusInfo(docStatusInfo.getDocId(), docStatusInfo.getCode(), detailMessage ,
							CastingHelper.stringToTimestamp(Timestamp.valueOf(LocalDateTime.now()).toString(), Constants.DATETIME_FORMAT) );
					
					String[] details = docStatusInfo.getDetail() != null ? docStatusInfo.getDetail().split("@##@") : new String[] {};
					Integer detailCode = docStatusInfo.getCode() == Constants.EMISION_DTE_SIIRECHAZADO
							? Constants.EMISION_DTE_SIIRECHAZADO_DETAIL
							: Constants.EMISION_DTE_SIIREPARADO_DETAIL;
					
					for (String detailError : details) {
						repo.insertDocumentLogByDocStatusInfo(docStatusInfo.getDocId(), detailCode, detailError,
								CastingHelper.stringToTimestamp(Timestamp.valueOf(LocalDateTime.now()).toString(), Constants.DATETIME_FORMAT));
					}
					
					repo.deleteReceiptStatusPendingById(receipt.getRspid());
					receipt.setUpdateStatus("ACTUALIZADO");
					receipt.setDocid(docemi.getDedocid());
					
				} 
			}
		}
		
		log.info(String.format("################################### FINISHED PROCESS %s ###################################", messageInitial));
		
		return list;
	}
}
