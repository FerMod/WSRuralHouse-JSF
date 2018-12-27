package com.ruralhousejsf.logger;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.varia.LevelRangeFilter;

public final class ConsoleLogger {

	/**
	 * <code>OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL</code>
	 * <p>Log messages level priority. This means, if level is <code>WARN</code>, 
	 * only <code>WARN</code>, <code>ERROR</code> and <code>FATAL</code> logs will show up.
	 */
	private static final Level ROOT_LOGGER_LEVEL = Level.ALL;

	private static final Level DEFAULT_LOGGER_LEVEL = Level.ALL;
	
	private static final String LOGGER_PATTERN = "[%-5p] [%d{dd/MM/yyyy HH:mm:ss.SSS}] (%F:%L) %m%n";
	//private static final String LOGGER_PATTERN = "[%-5p] [%d{dd/MM/yyyy HH:mm:ss.SSS}] %c %M - %m%n";
	private static final String LOGGER_PATTERN_INFO = "[%-5p] [%d{dd/MM/yyyy HH:mm:ss.SSS}] %m%n";

	private ConsoleLogger() {}

	public static Logger createLogger() {
		return createLogger(Thread.currentThread().getStackTrace()[2].getClassName(), DEFAULT_LOGGER_LEVEL);
	}

	public static Logger createLogger(Class<?> cls) {
		return createLogger(cls, DEFAULT_LOGGER_LEVEL);
	}

	public static Logger createLogger(String name) {
		return createLogger(name, DEFAULT_LOGGER_LEVEL);
	}

	public static Logger createLogger(Level level) {
		return createLogger(Thread.currentThread().getStackTrace()[2].getClassName(), level);
	}

	public static Logger createLogger(Class<?> cls, Level level) {		
		return createLogger(cls.getSimpleName(), level);
	}

	public static Logger createLogger(String name, Level level) {

		Logger logger = Logger.getLogger(name);

		BasicConfigurator.configure();

		// Configure loggers that output to the standard output stream
				
		// TRACE, DEBUG
		ConsoleAppender consoleOut = new ConsoleAppender();
		consoleOut.setLayout(new PatternLayout(LOGGER_PATTERN));
		consoleOut.setTarget("System.out");
		
		LevelRangeFilter rangeFilter = new LevelRangeFilter();
		rangeFilter.setLevelMax(Level.DEBUG);

		consoleOut.addFilter(rangeFilter);
		consoleOut.activateOptions();
		

		// INFO
		ConsoleAppender consoleOutInfo = new ConsoleAppender();
		consoleOutInfo.setLayout(new PatternLayout(LOGGER_PATTERN_INFO));
		consoleOutInfo.setTarget("System.out");
		consoleOutInfo.setThreshold(Level.INFO);
		
		LevelRangeFilter rangeFilterInfo = new LevelRangeFilter();
		rangeFilterInfo.setLevelMax(Level.INFO);

		consoleOutInfo.addFilter(rangeFilterInfo);
		consoleOutInfo.activateOptions();
		

		// Configure loggers that output to the standard error stream
		
		// WARN, ERROR, FATAL
		ConsoleAppender consoleErr = new ConsoleAppender();
		consoleErr.setLayout(new PatternLayout(LOGGER_PATTERN));
		consoleErr.setTarget("System.err");
		consoleErr.setThreshold(Level.WARN);
		consoleErr.activateOptions();


		// Add the console appenders
		logger.addAppender(consoleOut);
		logger.addAppender(consoleOutInfo);
		logger.addAppender(consoleErr);

		// Dont allow log propagation to parent loggers
		logger.setAdditivity(false);

		// Set the message level. It will use the more restrictive level 
		logger.setLevel(ROOT_LOGGER_LEVEL.isGreaterOrEqual(level) ? ROOT_LOGGER_LEVEL : level);

		// Set all loggers messages level
		Logger.getRootLogger().setLevel(ROOT_LOGGER_LEVEL);

		return logger;
	}

}
