package com.sovos.status.pending.exception;

public class ModeloNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -4660667989174908260L;

	public ModeloNotFoundException(String mensaje) {
		super(mensaje);
	}

}
