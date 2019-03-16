package com.progresssoft.util;

import org.apache.log4j.Level;


public enum LogLevel {
    Unknown(-1), All(Level.ALL_INT), Debug(Level.DEBUG_INT), Info(Level.INFO_INT), Warning(Level.WARN_INT), Error(Level.ERROR_INT), Severe(
            Level.FATAL_INT), Off(Level.OFF_INT);

    private final int logLevel;

    LogLevel(int llevel) {
        logLevel = llevel;
    }
    
    public int toInt() {
        
        return this.logLevel;
    }
    
    Level toLog4jLevel() {
        
        Level retVal = Level.ALL;
        switch(this) {
        
        case Debug:
            retVal = Level.DEBUG; break;
        case Info:
            retVal = Level.INFO; break;
        case  Warning:
            retVal = Level.WARN; break;
        case  Error:
            retVal = Level.ERROR; break;
        case  Severe:
            retVal = Level.FATAL; break;
        case  Off:
            retVal = Level.OFF; break;
        }
        
        return retVal;
    }
    
    static LogLevel fromLog4jLevel(Level level) {
        switch(level.toInt()) {
            case Level.ALL_INT:
                return LogLevel.All;
            case Level.DEBUG_INT:
                return LogLevel.Debug;
            case Level.INFO_INT:
                return LogLevel.Info;
            case Level.WARN_INT:
                return LogLevel.Warning;
            case Level.ERROR_INT:
                return LogLevel.Error;
            case Level.FATAL_INT:
                return LogLevel.Severe;
            case Level.OFF_INT:
                return LogLevel.Off;
            default:
                return LogLevel.Unknown;
        }
    }            
}
