package com.ruralhousejsf.businessLogic;

import com.ruralhousejsf.dataAccess.HibernateDataAccess;

public final class AppFacade {

	private static final ApplicationFacadeInterface APP_FACADE = createAppImpl();
	private static boolean initialized = false;
	
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
		return APP_FACADE;
	}
	
	public static void initializeDB() {
		getImpl().initializeDB();
		initialized = true;
	}
	
	public static boolean isDBInitialized() {
		return initialized;
	}
}
