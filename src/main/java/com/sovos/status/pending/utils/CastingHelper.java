package com.sovos.status.pending.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CastingHelper {
	/**
	 * String a Int
	 * 
	 * @param aValue
	 * @param aDefaultValue
	 * @return
	 */
	public static int stringToInt(String aValue, int aDefaultValue) {
		try {
			return Integer.parseInt(aValue);
		} catch(Throwable e) {
			return aDefaultValue;
		}
	}
	
	/**
	 * String a Long
	 * 
	 * @param aValue
	 * @param aDefaultValue
	 * @return
	 */
	public static long stringToLong(String aValue, long aDefaultValue) {
		try {
			// TRUNCA EL VALOR SI VIENEN DECIMALES
			return (long)Double.parseDouble(aValue);//Long.parseLong(aValue);
		} catch(Throwable e) {
			return aDefaultValue;
		}
	}
	
	/**
	 * String a Double
	 * 
	 * @param aValue
	 * @param aDefaultValue
	 * @return
	 */
	public static double stringToDouble(String aValue, double aDefaultValue) {
		try {
			return Double.parseDouble(aValue);
		} catch(Throwable e) {
			return aDefaultValue;
		}
	}
	
	public static boolean stringToBoolean(String aValue) {
		if (aValue!=null)
			return aValue.trim().equalsIgnoreCase("true");
		
		return false;
	}
	
	/**
	 * Convierte un string a java.util.Date
	 * El formato corresponde al formato de la fecha, por ejemplo:
	 * 31-10-2009, el formato es dd-MM-yyyy
	 * 2009/10/31, el formato es yyyy/MM/dd
	 * 2009/31/10, el formato es yyyy/dd/MM
	 * 
	 * Retorna null si el falla la conversion
	 * 
	 * @param fecha
	 * @param formato
	 * @return
	 */
	public static Date stringToDate(String fecha, String formato) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			sdf.setLenient(false);
			return sdf.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Convierte un string a java.sql.Timestamp
	 * El formato corresponde al formato de la fecha, por ejemplo:
	 * 31-10-2009 00:12:30, el formato es dd-MM-yyyy HH:mm:ss
	 * 2009/10/31 00:12:30, el formato es yyyy/MM/dd HH:mm:ss
	 * 2009/31/10 00:12:30, el formato es yyyy/dd/MM HH:mm:ss
	 * 
	 * Retorna null si el falla la conversion
	 * 
	 * @param fecha
	 * @param formato
	 * @return
	 */
	public static Timestamp stringToTimestamp(String fecha, String formato) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			return new Timestamp(sdf.parse(fecha).getTime());
		} catch (ParseException e) {
		}
		
		return null;
	}
	
	/**
	 * Convierte un java.util.Date o Timestamp en String
	 * El formato corresponde al formato de la fecha, por ejemplo:
	 * 31-10-2009, el formato es dd-MM-yyyy
	 * 2009/10/31, el formato es yyyy/MM/dd
	 * 2009/31/10, el formato es yyyy/dd/MM
	 * 
	 * Retorna "" si no se puede formatear la fecha
	 * 
	 * @param fecha
	 * @param formato
	 * @return
	 */
	public static String dateToString(Date fecha, String formato) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			return sdf.format(fecha);
		} catch (Exception e) {}
		
		return "";
	}

	/**
	 * Convierte un java.util.Date en java.sql.Date
	 * 
	 * @param fecha
	 * @return
	 */
	public static java.sql.Date dateToSqlDate(java.util.Date fecha) {
		return new java.sql.Date(fecha.getTime());
	}
	
	/**
	 * Convierte una java.util.Date en java.sql.Timestamp
	 * 
	 * @param fecha
	 * @return
	 */
	public static Timestamp dateToTimestamp(java.util.Date fecha) {
		return new java.sql.Timestamp(fecha.getTime());
	}
	
	/**
	 * Recupera la fecha-hora actual
	 * 
	 * @return
	 */
	public static Timestamp fechaHoraActual() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * Recupera la fecha actual
	 * 
	 * @return
	 */
	public static java.sql.Date fechaActual() {
		return new java.sql.Date(System.currentTimeMillis());
	}
	
	public static void main(String args[]) {
		//LoggerFactory.info(stringToLong(null, 0));
	}
}
