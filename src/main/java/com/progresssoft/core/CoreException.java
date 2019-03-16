package com.progresssoft.core;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * This class handles the RunTimeException  
 *
 * @author Mohammad-Alsaadi
 */
public class CoreException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	protected Exception innerExcep;
	
    public CoreException(Throwable t) {

        super(t);
        if(t instanceof Exception)
            this.innerExcep = (Exception) t;
    }
	
	public CoreException(String message) {

        super(message);
    }

    public CoreException(String format, Object... args) {

        super(String.format(format, args));
    }

    public CoreException(RuntimeException innerExcep, String message) {

        super(message, innerExcep);
    }

    public CoreException(RuntimeException innerExcep, String format, Object... args) {

        super(String.format(format, args), innerExcep);
    }
    
    public CoreException(Exception innerExcep, String message) {

    	super(message);
    	this.innerExcep = innerExcep;
    }

    public CoreException(Exception innerExcep, String format, Object... args) {
    	
        super(String.format(format, args));
        this.innerExcep = innerExcep;
    }

	public StackTraceElement[] getStackTrace() {
		
		if(this.innerExcep != null)
			return this.innerExcep.getStackTrace();
		
		return super.getStackTrace();
	}

	public void printStackTrace() {
		
		if(this.innerExcep != null)
			this.innerExcep.printStackTrace();
		else
			super.printStackTrace();
	}

	public void printStackTrace(PrintStream stream) {
		
		if(this.innerExcep != null)
			this.innerExcep.printStackTrace(stream);
		else
			super.printStackTrace(stream);	
	}

	public void printStackTrace(PrintWriter writer) {
		
		if(this.innerExcep != null)
			this.innerExcep.printStackTrace(writer);
		else
			super.printStackTrace(writer);
	}

	public void setStackTrace(StackTraceElement[] stackTrace) {

		if(this.innerExcep != null)
			this.innerExcep.setStackTrace(stackTrace);
		else
			super.setStackTrace(stackTrace);
	}
	
	public synchronized Throwable fillInStackTrace() {
		
		if(this.innerExcep != null)
			return this.innerExcep.fillInStackTrace();
		
		return super.fillInStackTrace();
	}

	public Throwable getCause() {
		
		if(this.innerExcep != null)
			return this.innerExcep.getCause();
		
		return super.getCause();
	}

	public synchronized Throwable initCause(Throwable cause) {
		
		if(this.innerExcep != null)
			return this.innerExcep.initCause(cause);
		
		return super.initCause(cause);
	}

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(""); 
        String lineSeparator = System.getProperty("line.separator");
        
        if (this.innerExcep != null)
            sb.append(this.innerExcep.toString() + " , ");
        else
            sb.append(super.toString() + " , ");
        
        sb.append("Caused by:: ").append(this.getCause()).append(lineSeparator);
        lineSeparator += "\t";
        
        for (StackTraceElement st : this.getStackTrace()) {
            sb.append(st.toString() + lineSeparator);
        }
        
        return sb.toString();
    }	
}