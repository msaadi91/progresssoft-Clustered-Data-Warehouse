package com.progresssoft.util;

import com.progresssoft.core.CoreException;


public class LogException extends CoreException {

    private static final long serialVersionUID = 3L;
    
    public LogException(String message) {

        super(message);
    }

    public LogException(String format, Object... args) {

        super(format, args);
    }

    public LogException(RuntimeException innerExcep, String message) {

        super(message, innerExcep);
    }

    public LogException(RuntimeException innerExcep, String format, Object... args) {

        super(innerExcep, format, args);
    }
    
    public LogException(Exception innerExcep, String message) {

        super(innerExcep, message);
    }

    public LogException(Exception innerExcep, String format, Object... args) {
        
        super(innerExcep, format, args);
    }   
}
