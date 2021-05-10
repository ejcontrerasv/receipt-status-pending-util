package com.sovos.status.pending.utils;

public class Constants {
	
	//ESTADOS DE UN DOCUMENTO ENVIADO AL SII
	public static final int EMISION_DTE_INSERT         =  0;
	public static final int EMISION_DTE_SENDING        =  1;
	public static final int EMISION_DTE_SIISEND        =  2;
	public static final int EMISION_DTE_SIIRECHAZADO_DETAIL =  3;
	public static final int EMISION_DTE_SIIRECHAZADO   =  4;
	public static final int EMISION_DTE_SIIREPARADO_DETAIL  =  5;
	public static final int EMISION_DTE_SIIREPARADO    =  6;
	public static final int EMISION_DTE_SIIREPARO_LEVE =  7;
	public static final int EMISION_DTE_SIIACEPTADO    =  8;
	public static final int EMISION_DTE_SIIGESTIONADO  =  9;
	
	
	public static final String SII_SENT_MESSAGE = "Documento enviado al SII, Hora: ";
	
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

}
