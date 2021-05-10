package com.sovos.status.pending.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SiiStatusEnum {
	APPROVED("DOK",8,"El documento fue aceptado por el SII, estado: "),
	APPROVED_WITH_OBS("RPR",6,"El documento fue aceptado con reparos por el SII, estado: "),
	APPROVED_WITH_OBS_LIGHT("RLV",6,"El documento fue aceptado con reparos leves por el SII, estado: "),
	REJECTED("RCH",4,"El documento fue rechazado por el SII, estado: "),
	ERROR_INTERNO("ERR",2,"Documento no se pudo enviar al SII, error interno del servidor. Contacte a soporte: ");
	
	
	private String siiStatus;
	private int aspStatus;
	private String message;
	
	private static final Map<String,SiiStatusEnum> BY_SII = new HashMap<>();
	
	static {
		for (SiiStatusEnum e : values()) {
			BY_SII.put(e.siiStatus, e);
		}
	}
	
	public static SiiStatusEnum getStatus(String siiStatus) {
		return BY_SII.get(siiStatus);
	}

	public String getSiiStatus() {
		return siiStatus;
	}

	public void setSiiStatus(String siiStatus) {
		this.siiStatus = siiStatus;
	}

	public int getAspStatus() {
		return aspStatus;
	}

	public void setAspStatus(int aspStatus) {
		this.aspStatus = aspStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
