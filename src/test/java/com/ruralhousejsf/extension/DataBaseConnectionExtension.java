package com.ruralhousejsf.extension;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.ruralhousejsf.dataAccess.util.ExponentialBackOff;
import com.ruralhousejsf.logger.ConsoleLogger;

/**
 * The DataBaseConnectionExtension class implements the 
 * {@link BeforeAllCallback} interface, which overrides the {@code beforeAll} 
 * method.
 * <p>
 * This extension class should be added to another class with the 
 * "{@code @extendsWith}" tag.
 * 
 * @see #beforeAll(ExtensionContext)
 */
public class DataBaseConnectionExtension implements BeforeAllCallback {

	private static final Logger LOGGER = ConsoleLogger.createLogger(DataBaseConnectionExtension.class);

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/HibernateRuralHouseJSF?useSSL=false";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	/**
	 * This method will be invoked before any user defined teardown methods
	 * like "{@code @beforeAll}" is made and its main purpose is to test the 
	 * connection before hand.<br>
	 * This method makes an assertion to checks and guarantee that no issues 
	 * where found when establishing the connection with the database.
	 * 
	 * @param context the context in which the current test or container is 
	 * being executed. Extensions are provided an instance of ExtensionContext 
	 * to perform their work.
	 * 
	 * @see BeforeAllCallback#beforeAll(ExtensionContext)
	 */
	@Override
	public void beforeAll(ExtensionContext context) throws Exception {

		boolean success = false;

		Class.forName(DRIVER);
		try(Connection connection = ExponentialBackOff.execute(() -> DriverManager.getConnection(URL, USER, PASSWORD))) {
			success = true;
		} catch (Exception e) {
			String msg = "Exception thrown";
			if(context.getElement().isPresent()) {
				msg += " in " + context.getElement().get();
			}
			LOGGER.error(msg, e);				
		}

		LOGGER.info("Connection test succeeded: " + success);
		assertTrue(success, () -> "Could not connect to the database. Connection success ");

	}

}
