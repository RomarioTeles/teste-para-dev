package com.zallpy.fileprocessor.exceptions;

import java.text.MessageFormat;

public class InvalidLineException extends RuntimeException{

	private static final long serialVersionUID = 154238922457866560L;

	public InvalidLineException(String line) {
		super(MessageFormat.format("Invalid line found in the file {0}.", line));
	}
	
	public InvalidLineException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
