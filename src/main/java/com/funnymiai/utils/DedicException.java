package com.funnymiai.utils;

public class DedicException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorCode;
	  
	  public DedicException(String errorCode)
	  {
	    super(errorCode);
	    this.errorCode = errorCode;
	  }
	  
	  public DedicException(String errorCode, String message)
	  {
	    super(message);
	    this.errorCode = errorCode;
	  }
	  
	  public DedicException(String errorCode, String message, Throwable cause)
	  {
	    super(message, cause);
	    this.errorCode = errorCode;
	  }
	  
	  public DedicException(String errorCode, Throwable cause)
	  {
	    this(errorCode, errorCode, cause);
	  }
	  
	  public String getErrorCode()
	  {
	    return this.errorCode;
	  }
	 
}
