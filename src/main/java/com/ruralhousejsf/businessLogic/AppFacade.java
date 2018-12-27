package com.ruralhousejsf.businessLogic;

import org.apache.log4j.Logger;

import com.ruralhousejsf.dataAccess.HibernateDataAccess;
import com.ruralhousejsf.logger.ConsoleLogger;

public final class AppFacade {

	private static final ApplicationFacadeInterface APP_FACADE = createAppImpl();
	private static boolean truncateDb = true;
	private static boolean initializeDb = true;
	public static final Logger LOGGER = ConsoleLogger.createLogger(AppFacade.class);

	private AppFacade() {}

	private static ApplicationFacadeInterface createAppImpl() {
		try {
			return new ApplicationFacadeImpl(new HibernateDataAccess());
		} catch (Throwable e) {
			System.err.println("Error during the creation of the aplication facade caused by " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Initializes and returns the application facade instance.
	 * <p>
	 * When getting the instance, this will truncate or initialize the database if 
	 * is planned to do so.
	 * 
	 * @return the initialized application facade instance
	 * 
	 * @see AppFacade#initialize(boolean)
	 * @see AppFacade#truncate(boolean)
	 */
	public static ApplicationFacadeInterface getImpl() {
		return getImpl(truncateDb, initializeDb);
	}

	/**
	 * Initializes and returns the application facade instance.
	 * <p>
	 * When getting the instance, this will truncate or initialize the database if 
	 * is planned to do so.
	 * 
	 * @param shouldTruncateDb {@code true} to remove the database tables content, {@code false} otherwise
	 * 
	 * @return the initialized application facade instance
	 * 
	 * @see AppFacade#initialize(boolean)
	 * @see AppFacade#truncate(boolean)
	 */
	public static ApplicationFacadeInterface getImpl(boolean shouldTruncateDb) {
		return getImpl(shouldTruncateDb, initializeDb);
	}

	/**
	 * Initializes and returns the application facade instance.
	 * <p>
	 * When getting the instance, this will truncate or initialize the database if 
	 * is planned to do so.
	 * 
	 * @param shouldTruncateDb {@code true} to remove the database tables content, {@code false} otherwise
	 * @param shouldInitializeDb {@code true} to initialize the database with data, {@code false} otherwise
	 * 
	 * @return the initialized application facade instance
	 * 
	 * @see AppFacade#truncate(boolean)
	 * @see AppFacade#initialize(boolean)
	 */
	public static ApplicationFacadeInterface getImpl(boolean shouldTruncateDb, boolean shouldInitializeDb) {
		if(shouldTruncateDb) {
			truncateDb = false;
			APP_FACADE.truncateDB();
		}
		if(shouldInitializeDb) {
			initializeDb = false;
			APP_FACADE.initializeDB();
		}
		return APP_FACADE;
	}

	/**
	 * Return if is planned to truncate the database in the next call of {@link AppFacade#getImpl()}
	 * @return {@code true} if is planned to truncate, {@code false} otherwise 
	 */
	public static boolean isTruncateEnabled() {
		return truncateDb;
	}

	/**
	 * Set to truncate the database data in the next call of {@link AppFacade#getImpl()}
	 * @param truncateDb {@code true} to plan to truncate the database, {@code false} otherwise 
	 */
	public static void truncate(boolean truncateDb) {
		AppFacade.truncateDb = truncateDb;
	}

	/**
	 * Return if is planned to initialize the database data in the next call of {@link AppFacade#getImpl()}
	 * @return {@code true} if is planned to initialize the database with data, {@code false} otherwise 
	 */
	public static boolean isInitializeEnabled() {
		return initializeDb;
	}

	/**
	 * Set to initialize the database data in the next call of {@link AppFacade#getImpl()}
	 * @param initializeDb {@code true} to plan the database initialization with data, {@code false} otherwise 
	 */
	public static void initialize(boolean initializeDb) {
		AppFacade.initializeDb = initializeDb;
	}



}
