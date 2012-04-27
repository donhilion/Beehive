package utils;

/**
 * Projectwide logger.
 * 
 * @author donhilion
 *
 */
public class Logger {
	
	/**
	 * Private constructor. This is a static class only.
	 */
	private Logger() {}
	
	/**
	 * Debug log.
	 * 
	 * @param message Logmessage.
	 * @param clazz Calling class.
	 */
	public static void logd(String message, Class<?> clazz) {}
	
	/**
	 * Warning log.
	 * 
	 * @param message Logmessage.
	 * @param clazz Calling class.
	 */
	public static void logw(String message, Class<?> clazz) {}
	
	/**
	 * Error log.
	 * @param message Logmessage.
	 * @param e Causing exception.
	 * @param clazz Calling class.
	 */
	public static void loge(String message, Exception e, Class<?> clazz) {}
	
}
