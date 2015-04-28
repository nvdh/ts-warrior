package be.nvdh.ts.exception;

import java.io.IOException;

public class FetchException extends Exception {

	private static final long serialVersionUID = 7253467876701956148L;

	public FetchException(String message){
		super(message);
	}

	public FetchException(String message, Exception e){
		super(message, e);
	}

	public FetchException(IOException e) {
		super(e);
	}

}
