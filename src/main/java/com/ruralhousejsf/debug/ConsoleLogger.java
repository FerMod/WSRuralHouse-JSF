package com.ruralhousejsf.debug;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public final class ConsoleLogger {

	/**
	 * <code>OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL</code>
	 * <p>Log messages level priority. This means, if level is <code>WARN</code>, 
	 * only <code>WARN</code>, <code>ERROR</code> and <code>FATAL</code> logs will show up.
	 */
	private static final Level ROOT_LOGGER_LEVEL = Level.ALL;
	
	private static final Level LOGGER_LEVEL = Level.ALL;
	private static final String LOGGER_PATTERN = "[%-5p] [%d{dd/MM/yyyy HH:mm:ss}] %c %M - %m%n";
	
	private ConsoleLogger() {}
	
	public static Logger createLogger() {
		return createLogger(Thread.currentThread().getStackTrace()[2].getClassName());
	}
	
	public static Logger createLogger(Class<?> clss) {		
		return createLogger(clss.getSimpleName());
	}
	
	public static Logger createLogger(String name) {
		
		Logger logger = Logger.getLogger(name);
		
		// Setup basic configuration
		BasicConfigurator.configure();

		// Set the logger pattern
		PatternLayout layout = new PatternLayout(LOGGER_PATTERN);		
		logger.addAppender(new ConsoleAppender(layout));

		// Dont allow log propagation to parent loggers
		logger.setAdditivity(false);
		
		// Set the message level. It will use the more restrictive level 
		logger.setLevel(ROOT_LOGGER_LEVEL.isGreaterOrEqual(LOGGER_LEVEL) ? ROOT_LOGGER_LEVEL : LOGGER_LEVEL);
		
		// Set all loggers messages level
		Logger.getRootLogger().setLevel(ROOT_LOGGER_LEVEL);
		
		return logger;
	}
	
}
