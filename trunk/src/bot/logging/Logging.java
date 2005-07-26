package bot.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * provides all classes with logging capabilities.
 * offers access to various loggers with associated get methods
 * @author y2uk
 *
 */
public class Logging {
	
	private static final Logger featureLogger;
	private static final Logger botLogger;
	
	static {
		featureLogger = Logger.getLogger("logger.features");
		featureLogger.setLevel(Level.FINEST);
		
		botLogger = Logger.getLogger("logger.bot");
		botLogger.setLevel(Level.FINEST);
		
		// TODO: add file handlers to save to log file
		// Handler handler = new FileHandler(logFileName);
		// logger.addHandler(handler);
	}
	
	/**
	 * returns the logger that should be used by all features.
	 * @return the logger for all features
	 */
	public static Logger getFeatureLogger() {
		return featureLogger;
	}
	
	/**
	 * returns the logger that should be used by the bot
	 * @return the logger for everything concerning the bot
	 */
	public static Logger getBotLogger() {
		return botLogger;
	}
	
	public static void setLogLevel(Level level) {
		featureLogger.setLevel(level);
		botLogger.setLevel(level);
	}
}
