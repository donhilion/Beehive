package utils;

/**
 * Projectwide logger.
 * 
 * @author donhilion
 *
 */
public class Logger {
	
	// TODO configuration
	
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
	public static void logd(String message, Class<?> clazz) {
		String text = new StringBuilder("DEBUG: ").append(clazz.toString())
				.append(": ").append(message).toString();
		System.out.println(text);
	}
	
	/**
	 * Warning log.
	 * 
	 * @param message Logmessage.
	 * @param clazz Calling class.
	 */
	public static void logw(String message, Class<?> clazz) {
		String text = new StringBuilder("WARNING: ").append(clazz.toString())
				.append(": ").append(message).toString();
		System.out.println(text);
	}
	
	/**
	 * Error log.
	 * @param message Logmessage.
	 * @param e Causing exception.
	 * @param clazz Calling class.
	 */
	public static void loge(String message, Exception e, Class<?> clazz) {
		String text = new StringBuilder("ERROR: ").append(clazz.toString())
				.append(": ").append(message).append(": ")
				.append(e.toString()).toString();
		System.out.println(text);
	}
	
}
