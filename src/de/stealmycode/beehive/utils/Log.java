package de.stealmycode.beehive.utils;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.stealmycode.beehive.Beehive;
import de.stealmycode.beehive.utils.Constants;

/**
 * Projectwide logger.
 *
 * @author Florian Bogenhard
 */
public class Log {
	
	private final static String LOGGER_NAME		= "Beehive";
	private final static Logger LOGGER_GLOBAL	= Logger.getLogger("");
	
	/**
	 * Initialization befor loading config
	 */
	public static void preInit(){

	    LOGGER_GLOBAL.setLevel(Lvl.INFO);
	    
		// delete handler
		for(Handler handler : LOGGER_GLOBAL.getHandlers()){
            LOGGER_GLOBAL.removeHandler(handler);
        } 
		
		Handler handler;
		try {
		    // Handler for LOGGER_GLOBAL
		    handler = new FileHandler(Constants.PATH_LOGFILE);
            LOGGER_GLOBAL.addHandler(handler);
            handler.setLevel(LOGGER_GLOBAL.getLevel());
            
            handler = new ConsoleHandler();
            LOGGER_GLOBAL.addHandler(handler);
            handler.setLevel(LOGGER_GLOBAL.getLevel());
		} catch (SecurityException e) {
			error("SecurityException: "+e);
			System.exit(1);
		} catch (IOException e) {
			error("IOException: "+e);
			System.exit(1);
		}
	}
	
	/**
	 * Initialization after loading config
	 */
	public static void init(){
		Log.debug("Logger: Switch to Config Settings");
		
		Level globalLogLevel = Beehive.config.getGlobalLogLevel();
		LOGGER_GLOBAL.setLevel(globalLogLevel);

	    for(Handler handler : LOGGER_GLOBAL.getHandlers()){
	        handler.setLevel(globalLogLevel);
        }
		Log.debug("Switch done");
	}	

	/**
	 * Private constructor. This is a static class only.
	 */
	private Log() {}
	
	/**
	 * Send the message to the logger.
	 * 
	 * @param logger
	 * @param level
	 * @param msgLvl
	 */
	private static void log(String logger, Level level, String msg){
		int stackTraceLength = Thread.currentThread().getStackTrace().length-1;
		
		StackTraceElement lastElement = Thread.currentThread().getStackTrace()[stackTraceLength];
		String sourceClass	= lastElement.getClassName();
		String sourceMethod = lastElement.getMethodName();
		Logger.getLogger(logger).logp(level, sourceClass, sourceMethod, msg);
	}

	/**
	 * Build the Loggername for Sublogger ( LOGGER_NAME.[name] )
	 * @param name
	 * @return LOGGER_NAME.[name]
	 */
	private static String buildLoggerName(String name){
		return new StringBuilder(LOGGER_NAME).append(".").append(name).toString();
	}
	
	/**
	 * Debug Log
	 * 
	 * @param msg		massage
	 */
	public static void debug(String msg){
		log(LOGGER_NAME,Lvl.DEBUG,msg);
	}
	
	/**
	 * Debug Log
	 * 
	 * @param logger	logger
	 * @param msg		massage
	 */
	public static void debug(String logger, String msg){
		log(buildLoggerName(logger), Lvl.DEBUG,msg);
	}
	
	/**
	 * Info Log
	 * 
	 * @param msg		massage
	 */
	public static void info(String msg){
		log(LOGGER_NAME,Lvl.INFO,msg);
	}
	
	/**
	 * Info Log
	 * 
	 * @param logger	logger
	 * @param msg		massage
	 */
	public static void info(String logger, String msg){
		log(buildLoggerName(logger), Lvl.INFO,msg);
	}
	
	/**
	 * Warning Log
	 * 
	 * @param msg		massage
	 */
	public static void warning(String msg){
		log(LOGGER_NAME,Lvl.WARNING,msg);
	}
	
	/**
	 * Warning Log
	 * 
	 * @param logger	logger
	 * @param msg		massage
	 */
	public static void warning(String logger, String msg){
		log(buildLoggerName(logger), Lvl.WARNING,msg);
	}
	
	/**
	 * Error Log
	 * 
	 * @param msg		message
	 */
	public static void error(String msg){
		log(LOGGER_NAME, Lvl.ERROR,msg);
	}

	/**
	 * Error Log
	 * 
	 * @param logger	logger
	 * @param msg		message
	 */
	public static void error(String logger, String msg){
		log(buildLoggerName(logger), Lvl.ERROR,msg);
	}
}
