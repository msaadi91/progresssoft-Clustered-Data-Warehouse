package com.progresssoft.util;

import org.apache.log4j.Level;

import com.progresssoft.controller.UploadController;

/**
 * This Class is the Log4j Wrapper we can use this to change the implementation of logging without affecting the rest of the logging code. We have
 * used the adaptor pattern here to encapsulate the the Log4j logging behaviour
 * 
 * @author Mohammad-Alsaadi
 * 
 */
public class Logger {

    // Enum for custom Log Levels
    // Holding the underlying implementation currenly using log4j logger
    private org.apache.log4j.Logger logger;
    private int intLevel;
    private LogLevel level;

    // only private constructor
    private Logger(org.apache.log4j.Logger log4j) {
        
        this.logger = log4j;
        
        this.level = LogLevel.fromLog4jLevel(log4j.getEffectiveLevel());
        this.intLevel = this.level.toInt();
    }
    

    public static Logger getLogger(String name) {
        
        return new Logger(org.apache.log4j.Logger.getLogger(name));
    }

    public static Logger getLogger(Class<?> clazz) {
        
        return new Logger(org.apache.log4j.Logger.getLogger(clazz));
    }

    public String getName() {
        return logger.getName();
    }

    /**
     * This method returns the log level from the logger implmentation
     * @return
     */
    
    public LogLevel getLogLevel() {
        
        return this.level;
    }

    /**
     * Not implemeted for log4j as this is determined by the log4j internally.
     * @param level
     * @return
     */
    public boolean isLoggable(LogLevel level) {
        
        return (level.toInt() >= this.intLevel);
    }

    /**
     * This method logs the message string containing the arguements on to the specified log level. 
     * @param level The level to log.
     * @param msg The messages to be loged containing the placeholders for arguements like {index}.
     * @param args The arguements of the message string. 
     */
    public void log(LogLevel level, String msg, Object... args) {
        
        if (isLoggable(level)) {
            
            this.logger.log(level.toLog4jLevel(), String.format(msg, args));
        }
    }

    /**
     * This method logs the message string containing the arguements on to the specified log level. 
     * @param level The level to log.
     * @param throwable The Exception to log
     * @param msg The messages to be loged containing the placeholders for arguements like {index}.
     * @param args The arguements of the message string. 
     */
    public void Log(LogLevel level, Throwable throwable, String msg, Object... args) {

        if (isLoggable(level)) {
            
            this.logger.log(level.toLog4jLevel(), String.format(msg, args), throwable);
        }
    }

    /**
     * This method logs the message string on to the specified log level. 
     * @param level The level to log.
     * @param msg The messages to be loged.
     * @param throwable The Exception to log
     */
    public void log(LogLevel level, String msg, Throwable throwable) {
        
        this.logger.log(level.toLog4jLevel(), msg, throwable);
    }

    /**
     * This method logs the message string on to the specified log level. 
     * @param level The level to log.
     * @param msg The messages to be loged.
     */
    public void log(LogLevel level, String msg) {
        
        this.logger.log(level.toLog4jLevel(), msg);
    }

    public void logSevere(String msg, Object... args) {

        if(this.isLoggable(LogLevel.Severe)) {
            
            this.logger.log(Level.FATAL,String.format(msg, args));
        }
    }

    public void logSevere(String msg) {
        
        this.logger.log(Level.FATAL, msg);
    }

    public void logError(String msg, Object... args) {

        if(this.isLoggable(LogLevel.Error)) {
            
            this.logger.log(Level.ERROR, String.format(msg, args));
        }
    }

    public void logError(String msg) {

        this.logger.log(Level.ERROR, msg);
    }

    public void logWarning(String msg, Object... args) {

        if(this.isLoggable(LogLevel.Warning)) {
            
            this.logger.log(Level.WARN, String.format(msg, args));
        }
    }

    public void logWarning(String msg) {

        this.logger.log(Level.WARN, msg);
    }

    public void logDebug(String msg, Object... args) {

        if(this.isLoggable(LogLevel.Debug)) {
            
            this.logger.log(Level.DEBUG, String.format(msg, args));
        }
    }

    public void logDebug(String msg) {

        this.logger.log(Level.DEBUG, msg);
    }

    public void logInfo(String msg, Object... args) {

        if(this.isLoggable(LogLevel.Info)) {
            
            this.logger.log(Level.INFO, String.format(msg, args));
        }
    }

    public void logInfo(String msg) {

        this.logger.log(Level.INFO, msg);
    }

    public void LogPrecise(LogLevel level, String srcClass, String srcMethod, Throwable excep, String msg, Object... args) {

        if(this.isLoggable(level)) {
        
            this.log(level, String.format("[%1$s].%2$s method, Log Msg: %3$s.%4$s Exception: %5$s.", srcClass, srcMethod, String.format(msg, args), System.
                    getProperty("line.separator"), excep), excep);
        }
    }

    public void LogPrecise(LogLevel level, String srcClass, String srcMethod, String msg, Throwable exception) {

        this.log(level, String.format("%1$s.%2$s method log: %3$s.%4$s, Exception: %5$s.", srcClass, srcMethod, msg, System.getProperty("line.separator"), exception),
                exception);
    }

    public void LogPrecise(LogLevel level, Throwable excep, String msg, Object... args) {

        try {
            if(this.isLoggable(level)) {
                StackTraceElement previousTrace = excep.getStackTrace()[1];
                this.LogPrecise(level, previousTrace.getClassName(), previousTrace.getMethodName(), excep, msg, args);
            }
        } 
        catch (Exception ex) {
            
            throw new LogException(ex, "Error traversing caller method in LogP method, Logger: %1$s", this.logger.getName());
        }
    }

    public void LogPrecise(LogLevel level, String msg, Throwable exception) {

        try {
            StackTraceElement previousTrace = exception.getStackTrace()[1];
            this.LogPrecise(level, previousTrace.getClassName(), previousTrace.getMethodName(), msg, exception);
        } catch (Exception ex) {
            throw new LogException(ex, "Error traversing caller method at LogP method, Logger: %1$s", this.logger.getName());
        }
    }
    
    public void LogException(String msg, Throwable exception) {
    	
        StringBuilder sb = new StringBuilder(""); 
        String lineSeparator = System.getProperty("line.separator");
        
        sb.append(msg).append(lineSeparator);
        sb.append("Caused by:: ").append(exception.getCause()).append(lineSeparator);
        lineSeparator += "\t";
        
        for (StackTraceElement st : exception.getStackTrace()) {
            sb.append(st.toString() + lineSeparator);
        }
        
        this.logger.error(sb.toString(), exception);
//        this.logger.error(ContextUtil.getApplicationContextTrace());
    }
    
    public void LogException(String msg, Throwable exception, Object...params) {
        
        StringBuilder sb = new StringBuilder(""); 
        String lineSeparator = System.getProperty("line.separator");
        
        sb.append(String.format(msg, params)).append(lineSeparator);
        sb.append("Caused by:: ").append(exception.getCause()).append(lineSeparator);
        lineSeparator += "\t";
        
        for (StackTraceElement st : exception.getStackTrace()) {
            sb.append(st.toString() + lineSeparator);
        }
        
        this.logger.error(sb.toString(), exception);
//        this.logger.error(ContextUtil.getApplicationContextTrace());
    }

    /**
     * Test method this must be moved into the test case....
     * @param args
     */
    public static void main(String[] args) {
        Logger logone = Logger.getLogger(Logger.class);
        logone.logDebug("logging working now....");
        
        Logger logtwo = Logger.getLogger(LogException.class);
        logtwo.logInfo("logginf info ...");
        
        Logger logt = Logger.getLogger(UploadController.class);
        logt.logSevere("logginf iahdahjanfo ...");
        
        logone.logDebug("logging working now....end");
        logtwo.logInfo("logginf info ...end");
        logt.logSevere("logginf iahdahjanfo ...end");
        logt.LogPrecise(LogLevel.Info, "myClass", "myMethod", new NullPointerException("null found"),"Another Message %1$s having args ones %2$s : another args %3$s.", "message","arg1","arg2");
    }
}
