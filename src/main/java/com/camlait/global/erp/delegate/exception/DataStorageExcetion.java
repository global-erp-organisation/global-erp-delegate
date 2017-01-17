package com.camlait.global.erp.delegate.exception;

/**
 * Generic exception for data starage.
 * 
 * @author Martin Blaise Signe
 *
 */
@SuppressWarnings("serial")
public class DataStorageExcetion extends RuntimeException {

	public DataStorageExcetion(String message) {
		super(message);
	}

	public DataStorageExcetion(String message, Throwable cause) {
		super(message, cause);
	}

	public DataStorageExcetion(Throwable cause) {
		super(cause);
	}
}
