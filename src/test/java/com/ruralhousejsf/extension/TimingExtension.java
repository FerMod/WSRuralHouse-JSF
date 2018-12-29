package com.ruralhousejsf.extension;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import com.ruralhousejsf.logger.ConsoleLogger;

/**
 * The TimingExtension class implements the {@link BeforeTestExecutionCallback}
 * and the {@link AfterTestExecutionCallback} interfaces, which overrides the 
 * {@code beforeTestExecution} method and the {@code afterTestExecution} method.
 * <p>
 * This extension class should be added to another class with the 
 * "{@code @extendsWith}" tag.
 * 
 * @see #beforeTestExecution(ExtensionContext)
 * @see #afterTestExecution(ExtensionContext)
 */
public class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

	private static final Logger LOGGER = ConsoleLogger.createLogger(TimingExtension.class);

	private static final String START_TIME = "start time";

	/**
	 * 
	 * This will store the current system time value for later retrieval under
	 * the {@code START_TIME} key.<br>
	 * This method will be invoked before any user defined teardown methods
	 * like "{@code @Test}" is made and its main purpose is to start the test
	 * elapsed time.
	 * 
	 * @param context the context in which the current test or container is 
	 * being executed. Extensions are provided an instance of ExtensionContext 
	 * to perform their work.
	 * 
	 * @see BeforeTestExecutionCallback#beforeTestExecution(ExtensionContext)
	 */
	@Override
	public void beforeTestExecution(ExtensionContext context) throws Exception {
		getStore(context).put(START_TIME, System.currentTimeMillis());
	}

	/**
	 * 
	 * 
	 * This method will retrieve the stored value under the {@code START_TIME} key and 
	 * calculate the elapsed time.<br>
	 * This will be invoked after the user defined teardown methods
	 * like "{@code @Test}" is made and its main purpose is to stop and log out
	 * the test elapsed time.
	 * 
	 * @param context the context in which the current test or container is 
	 * being executed. Extensions are provided an instance of ExtensionContext 
	 * to perform their work.
	 * 
	 * @see AfterTestExecutionCallback#afterTestExecution(ExtensionContext)
	 */
	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		Method testMethod = context.getRequiredTestMethod();
		long startTime = getStore(context).remove(START_TIME, long.class);
		long duration = System.currentTimeMillis() - startTime;

		LOGGER.info(String.format("Method [%s] took %s ms.", testMethod.getName(), duration));
	}

	/**
	 * Returns the {@code Store} instance that is associated for the given
	 * {@code ExtensionContext}.
	 * 
	 * @param context the context in which the current test or container is 
	 * being executed. Extensions are provided an instance of ExtensionContext 
	 * to perform their work.
	 * 
	 * @return the Store instance that provides methods for extensions to save and retrieve data.
	 */
	private Store getStore(ExtensionContext context) {
		return context.getStore(Namespace.create(getClass(), context.getRequiredTestMethod()));
	}

}
