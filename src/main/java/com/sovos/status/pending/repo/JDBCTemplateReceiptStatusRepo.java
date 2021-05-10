package com.sovos.status.pending.repo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sovos.status.pending.config.ConfigProperties;
import com.sovos.status.pending.dto.DocumentOutbound;
import com.sovos.status.pending.exception.ModeloNotFoundException;
import com.sovos.status.pending.models.ReceiptStatusPending;
import com.sovos.status.pending.utils.CastingHelper;
import com.sovos.status.pending.utils.Constants;
import com.sovos.status.pending.utils.DocumentOutBoundMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class JDBCTemplateReceiptStatusRepo {
	
	@Autowired
	private JdbcTemplate template;
	
	@Autowired
	private ConfigProperties properties;
	
	public List<ReceiptStatusPending> findAll(){
		
		int maxRows = properties.getMaxRows();
		Timestamp endDateToFind = CastingHelper.stringToTimestamp(properties.getEndDate(), Constants.DATETIME_FORMAT);
		log.info(String.format("Propiedades para la consulta a la BD MaxRows:[%s] - EndDate:[%s]", maxRows, endDateToFind));
		
		template.setMaxRows(maxRows);
		String sql = "SELECT * FROM receiptstatuspending WHERE rspsiisenttime < ?";
		List<Map<String,Object>> list = template.queryForList(sql, new Object[]{endDateToFind});
		List<ReceiptStatusPending> documents = new ArrayList<>();
		
		for ( Map row : list) {
			ReceiptStatusPending doc = new ReceiptStatusPending();
			doc.setRspid((Integer)row.get("rspid"));
			doc.setRspcompid((Integer)row.get("rspcompid"));
			doc.setRspdoctype((Integer) row.get("rspdoctype"));
			doc.setRspfolio((Long)row.get("rspfolio"));
			doc.setRspdigest(row.get("rspdigest").toString());
			doc.setRspsiistatus(row.get("rspsiistatus").toString());
			doc.setRspaspstatus((Integer) row.get("rspaspstatus"));
			doc.setRspcloudreceptime((Timestamp)row.get("rspcloudreceptime"));
			doc.setRspsiisenttime((Timestamp) row.get("rspsiisenttime"));
			doc.setRspsiitrackid(row.get("rspsiitrackid").toString());
			doc.setRspsiiresponsetime(((Timestamp) row.get("rspsiiresponsetime")));
			doc.setRspsiistatusdetail(row.get("rspsiistatusdetail").toString());
			doc.setUpdateStatus("NO ACTUALIZADO");
			doc.setDocid(0L);
			documents.add(doc);
		}
		
		return documents;
	}
	
	public List<ReceiptStatusPending> findByCompanyId(Integer companyId){
		
		int maxRows = properties.getMaxRows();
		Timestamp endDateToFind = CastingHelper.stringToTimestamp(properties.getEndDate(), Constants.DATETIME_FORMAT);
		log.info(String.format("Propiedades para la consulta a la BD MaxRows:[%s] - EndDate:[%s]", maxRows, endDateToFind));
		
		template.setMaxRows(maxRows);
		String sql = "SELECT * FROM receiptstatuspending WHERE rspcompid= ? AND rspsiisenttime < ?";
		List<Map<String,Object>> list = template.queryForList(sql, new Object[]{companyId, endDateToFind});
		List<ReceiptStatusPending> documents = new ArrayList<>();
		
		for ( Map row : list) {
			ReceiptStatusPending doc = new ReceiptStatusPending();
			doc.setRspid((Integer)row.get("rspid"));
			doc.setRspcompid((Integer)row.get("rspcompid"));
			doc.setRspdoctype((Integer) row.get("rspdoctype"));
			doc.setRspfolio((Long)row.get("rspfolio"));
			doc.setRspdigest(row.get("rspdigest").toString());
			doc.setRspsiistatus(row.get("rspsiistatus").toString());
			doc.setRspaspstatus((Integer) row.get("rspaspstatus"));
			doc.setRspcloudreceptime((Timestamp)row.get("rspcloudreceptime"));
			doc.setRspsiisenttime((Timestamp) row.get("rspsiisenttime"));
			doc.setRspsiitrackid(row.get("rspsiitrackid").toString());
			doc.setRspsiiresponsetime(((Timestamp) row.get("rspsiiresponsetime")));
			doc.setRspsiistatusdetail(row.get("rspsiistatusdetail").toString());
			doc.setUpdateStatus("NO ACTUALIZADO");
			doc.setDocid(0L);
			documents.add(doc);
		}
		
		return documents;
	}
	
	public Optional<DocumentOutbound> findDocumentByCoidByDocidBySequenceByTypeByDigest(Integer companyId,
			Long sequence, Integer type, String digest){
		
		String sql = "SELECT dedocid,decoid,detipodte,defolio,dedigdigest,derutemi "
				+ "FROM docemi,docemidigest "
				+ "WHERE dedocid = dedigdocid "
				+ "AND decoid = ? AND defolio = ? AND detipodte= ? AND dedigdigest = ?";
		DocumentOutbound doc = null;
			
			try{
				doc = template.queryForObject(sql, new Object[]{companyId, sequence, type, digest}, new DocumentOutBoundMapper());
			} catch (DataAccessException ex) {
				log.info(String.format("No se encontro registro en docemi para documento con coid: [%s], folio: [%s], tipoDte: [%s], digest: [%s]",companyId, sequence, type, digest));
			}
		
		return Optional.ofNullable(doc);
	}
	
	public Integer updateDocumentStatusByDocStatusInfo(Integer siiStatus,
			String siiStatusMsg, Timestamp siidate, Long docId) {
		
		int update = template.update("UPDATE docemistate SET dessiistatus= ?, dessiistatusmsg= ?, dessiidate= ? WHERE desdocid= ?",
				siiStatus,siiStatusMsg,siidate,docId);
		if ( update == 1 ) {
			log.info(String.format("Se actualiza documento en docemistate con Docid: [%s]", docId));
		} else {
			log.info(String.format("No se pudo actualizar documento en docemistate con Docid: [%s]", docId));
		}
		
		return update;
	}
	
	public void insertDocumentLogByDocStatusInfo(Long docid, Integer detailCode,
			String detail, Timestamp insertDate) {
		
		String sql = "INSERT INTO docemilog (deldocid, delcode, delmsg, delinsdate) VALUES (?,?,?,?)";
		int insert = template.update(sql,docid,detailCode,detail,insertDate);
		
		if ( insert == 1 ) {
			log.info(String.format("Se ingresa log de documento en docemilog con Docid: [%s]", docid));
		} else {
			log.info(String.format("No se pudo ingresar log de documento en docemilog con Docid: [%s]", docid));
		}
	}
	
	public void deleteReceiptStatusPendingById (Integer id) {
		String sql = "DELETE FROM receiptstatuspending WHERE rspid = ?";
		int delete = template.update(sql,id);
		
		if ( delete == 1 ) {
			log.info(String.format("Se elimina documento de receiptstatuspending con id: [%s]", id));
		} else {
			log.info(String.format("No se pudo eliminar documento de receiptstatuspending con id: [%s]", id));
		}
	}
}
