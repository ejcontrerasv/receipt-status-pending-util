package com.sovos.status.pending.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sovos.status.pending.dto.DocStatusInfo;
import com.sovos.status.pending.dto.DocumentOutbound;
import com.sovos.status.pending.models.ReceiptStatusPending;

@Component
public class ObjectMapper {
	
	@Autowired
	private ParseHelper parseHelper;
	
	public DocStatusInfo ReceiptStatusPendingToDocStatusInfo(ReceiptStatusPending docStatusPending, DocumentOutbound docemi) {
		DocStatusInfo docStatusInfo = new DocStatusInfo();
		docStatusInfo.setDocId(docemi.getDedocid());
		SiiStatusEnum siiStatusEnum = SiiStatusEnum.getStatus(docStatusPending.getRspsiistatus());
		
		String time = " ";
        if (docStatusPending.getRspsiiresponsetime() != null){
            time = " Hora: " + CastingHelper.dateToString(docStatusPending.getRspsiiresponsetime(), Constants.DATETIME_FORMAT);
        }
        
        docStatusInfo.setId(parseHelper.isPresent(docStatusPending.getRspid()) ? docStatusPending.getRspid() : 0) ;
		docStatusInfo.setCode(parseHelper.isPresent(siiStatusEnum.getAspStatus()) ? siiStatusEnum.getAspStatus() : 0);
		docStatusInfo.setMessage(parseHelper.isPresent(siiStatusEnum.getMessage()) ? siiStatusEnum.getMessage() + docStatusPending.getRspsiistatus() + time : "");
		docStatusInfo.setInsertDate(parseHelper.isPresent(docStatusPending.getRspsiisenttime()) 
				? docStatusPending.getRspsiisenttime() : CastingHelper.stringToTimestamp(Timestamp.valueOf(LocalDateTime.now()).toString(), Constants.DATETIME_FORMAT));
		docStatusInfo.setDetail(parseHelper.isPresent(docStatusPending.getRspsiistatusdetail()) ? docStatusPending.getRspsiistatusdetail() : "" );
		docStatusInfo.setDocType(parseHelper.isPresent(docStatusPending.getRspdoctype()) ? docStatusPending.getRspdoctype() : 0);
		docStatusInfo.setRutEmi(parseHelper.isPresent(docemi.getDerutemi()) ? docemi.getDerutemi() : 0);
		
		return docStatusInfo;
	}

}
