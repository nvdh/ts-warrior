package be.nvdh.ts.exception;

import java.io.IOException;

public class ReportException extends Exception {

	private static final long serialVersionUID = 7732290456500742706L;

	public ReportException(String message){
		super(message);
	}

	public ReportException(String message, Exception e){
		super(message, e);
	}

	public ReportException(IOException e) {
		super(e);
	}

}
