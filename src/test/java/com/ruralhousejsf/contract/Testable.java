package com.ruralhousejsf.contract;

/**
 * The Testable class represents a testable type used by other classes for the
 * use of their own tests.
 *
 * @param <T> the type of objects that this object may be tested to
 */
public interface Testable<T> {
	
	/**
	 * Creates and returns a value used for testing.<br>
	 * This method should be implemented to guarantee the correct execution of 
	 * the classes that rely on this method.
	 * 
	 * @return the created value
	 */
    T createValue();

}
