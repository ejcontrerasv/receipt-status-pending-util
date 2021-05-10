package com.sovos.status.pending.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DocStatusInfo {
	
	private Integer id;
	private Long docId;
	private Integer code;
	private String message;
	private Timestamp insertDate;
	private String detail;
	private Integer docType;
	private Integer rutEmi;

}
