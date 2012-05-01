package de.stealmycode.beehive.utils;

import de.stealmycode.beehive.Beehive;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Projectwide logger.
 *
 * @author Florian Bogenhard
 */
public class Log {
	
	private final static Logger LOGGER	= Logger.getLogger("");
	
	/**
	 * Initialization befor loading config
	 */
	public static void preInit(){
        
	    LOGGER.setLevel(Level.INFO);   
		try {
		    // Handler for LOGGER_GLOBAL
		    Handler handler = new FileHandler(Constants.PATH_LOGFILE);
            LOGGER.addHandler(handler);
            handler.setLevel(LOGGER.getLevel());
		} catch (SecurityException e) {
            error("SecurityException in Log.preInit() ", e);
			System.exit(1);
		} catch (IOException e) {
            error( "IOException in Log.preInit() ", e);
			System.exit(1);
		}
	}
	
    private static void setLogLevel(Level lvl){
        LOGGER.finer("Change Log-Level to "+lvl.getName());
        LOGGER.setLevel(lvl);
        for(Handler handler : LOGGER.getHandlers()){
            handler.setLevel(lvl);
        }
    }
  
	/**
	 * Initialization after loading config
	 */
	public static void init(){
		Log.debug("Logger: Switch to Config Settings");
		
		Level globalLogLevel = Beehive.config.getGlobalLogLevel();
		LOGGER.setLevel(globalLogLevel);

	    for(Handler handler : LOGGER.getHandlers()){
	        handler.setLevel(globalLogLevel);
        }
		Log.debug("Logger: Switch done");
	}	

	/**
	 * Private constructor. This is a static class only.
	 */
	private Log() {}
	
    private static StackTraceElement caller(){
        // 1    Debug
        // 2    log
        // 3    caller()
        // 4    
        return Thread.currentThread().getStackTrace()[4];
    }
        
	/**
	 * Send the message to the logger.
	 * 
	 * @param logger
	 * @param level
	 * @param msgLvl
	 */
	private static void log(Level level, String msg){
        StackTraceElement caller = caller();
        
		Logger.getLogger(caller.getClassName()).logp(level, caller.getClassName(), caller.getMethodName(), msg);
	}
    
   private static void log(Level level, String msg,Throwable t){
       StackTraceElement caller = caller();
       
       Logger.getLogger(caller.getClassName()).logp(level, caller.getClassName(), caller.getMethodName(), msg, t);
        
    }
	
	/**
	 * Debug Log
	 * 
	 * @param msg		message
	 */
	public static void debug(String msg){
		log(Lvl.DEBUG,msg);
	}
    
    /**
	 * Debug Log
	 * 
	 * @param msg		message
     * @param exception exception
	 */
	public static void debug(String msg, Throwable t){
		log(Lvl.DEBUG,msg ,t);
	}
	
	/**
	 * Info Log
	 * 
	 * @param msg		message
	 */
	public static void info(String msg){
		log(Level.INFO,msg);
	}
	
	/**
	 * Info Log
	 * 
	 * @param msg		message
     * @param exception exception
	 */
	public static void info(String msg, Throwable exception){
		log(Level.INFO,msg,exception);
	}
	
	/**
	 * Warning Log
	 * 
	 * @param msg		message
	 */
	public static void warning(String msg){
		log(Level.WARNING,msg);
	}
	
	/**
	 * Warning Log
	 * 
	 * @param msg		message
     * @param exception exception
	 */
	public static void warning(String msg, Throwable exception){
		log(Level.WARNING,msg,exception);
	}
	
	/**
	 * Error Log
	 * 
	 * @param msg		message
	 */
	public static void error(String msg){
		log(Level.SEVERE,msg);
	}

	/**
	 * Error Log
	 * 
	 * @param msg		message
     * @param exception exception
	 */
	public static void error(String msg, Throwable exception){
		log(Level.SEVERE,msg,exception);
	}
}
