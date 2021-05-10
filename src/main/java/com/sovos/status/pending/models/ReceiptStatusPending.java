package com.sovos.status.pending.models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReceiptStatusPending {
	
	 private Integer rspid;
	 private Integer rspcompid;
	 private Integer rspdoctype;
	 private Long rspfolio;
	 private String rspdigest;
	 private String rspsiistatus;
	 private Integer rspaspstatus;
	 private Timestamp rspcloudreceptime;
	 private Timestamp rspsiisenttime;
	 private String rspsiitrackid;
	 private Timestamp rspsiiresponsetime;
	 private String rspsiistatusdetail;
	 private String updateStatus;
	 private Long docid;

}
