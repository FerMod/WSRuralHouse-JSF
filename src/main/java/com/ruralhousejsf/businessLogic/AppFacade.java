package com.ruralhousejsf.businessLogic;

import org.apache.log4j.Logger;

import com.ruralhousejsf.dataAccess.HibernateDataAccess;
import com.ruralhousejsf.debug.ConsoleLogger;

public final class AppFacade {

	private static final ApplicationFacadeInterface APP_FACADE = createAppImpl();
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
	
	public static ApplicationFacadeInterface getImpl() {
		return getImpl(initializeDb);
	}

	public static ApplicationFacadeInterface getImpl(boolean shouldInitializeDb) {
		if(shouldInitializeDb) {
			initializeDb = false;
			APP_FACADE.initializeDB();
		}
		return APP_FACADE;
	}
	
}
