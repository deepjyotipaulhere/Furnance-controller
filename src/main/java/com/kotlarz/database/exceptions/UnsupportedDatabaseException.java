package com.kotlarz.database.exceptions;

@SuppressWarnings("serial")
public class UnsupportedDatabaseException extends Exception {
	public UnsupportedDatabaseException() {
		super();
	}

	public UnsupportedDatabaseException(Throwable ex) {
		super(ex);
	}

	public UnsupportedDatabaseException(String message) {
		super(message);
	}

	public UnsupportedDatabaseException(String message, Throwable ex) {
		super(message, ex);
	}
}
