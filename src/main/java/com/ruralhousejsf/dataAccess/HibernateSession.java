package com.ruralhousejsf.dataAccess;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateSession {

	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	private HibernateSession() {}

	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Error during the creation of session: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
