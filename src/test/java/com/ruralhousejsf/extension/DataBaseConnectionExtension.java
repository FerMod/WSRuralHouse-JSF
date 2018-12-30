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
 * An Extension can be registered <tt>declaratively</tt> via {@link org.junit.jupiter.api.extension.ExtendWith @ExtendWith}, 
 * <tt>programmatically</tt> via {@linkplain org.junit.jupiter.api.extension.RegisterExtension @RegisterExtension}, 
 * or <tt>automatically</tt> via the {@linkplain java.util.ServiceLoader ServiceLoader} mechanism.
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
	 * This callback is invoked once <tt>before</tt> all tests in the 
	 * current container, and its only purpose is to test the connection before 
	 * hand.<br>
	 * This method makes an assertion to checks and guarantee that no issues 
	 * where found when establishing the connection with the database.
	 * 
	 * @param context the current extension context, never {@code null}
	 * 
	 * @see BeforeAllCallback#beforeAll(ExtensionContext)
	 * @see ExtensionContext
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
