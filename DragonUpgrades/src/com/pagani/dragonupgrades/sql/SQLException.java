package com.pagani.dragonupgrades.sql;

public class SQLException extends RuntimeException {

	private static final long serialVersionUID = -1746878370430655863L;

	public SQLException() {
		super();
	}

	public SQLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SQLException(String message, Throwable cause) {
		super(message, cause);
	}

	public SQLException(String message) {
		super(message);
	}

	public SQLException(Throwable cause) {
		super(cause);
	}

}
