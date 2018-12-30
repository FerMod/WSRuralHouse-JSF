package com.ruralhousejsf.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * This interface defines tests to verify that the objects of each class that 
 * implements the {@link Comparable} interface, impose their total ordering with
 * the expected behavior. This ordering is referred to as the classes 
 * <i>natural ordering</i>, and the classes {@code compareTo} method is referred
 * to as its <i>natural comparison method</i>.
 * <p>
 * This class extends from {@link Testable}, and in order to function correctly 
 * all the required methods must be implemented.
 *
 * @param <T> the type of objects that this object may be compared to
 * 
 * @see Testable
 */
@DisplayName("Comparable Test")
@Tag("comparable")
public interface ComparableContract<T extends Comparable<T>> extends Testable<T> {
	
	/**
	 * Creates and returns a smaller value to use in the comparable tests.<br>
	 * This method, for its correct execution, should return a value smaller 
	 * than the returned by {@link Testable#createValue}.
	 * 
	 * @return the created value
	 */
	T createSmallerValue();

	/**
	 * Compares the created value to itself to test that returns a zero value.
	 */
	@DisplayName("Compare Itself - Return Zero")
	@Test
	default void comparedToItself() {
		T value = createValue();
		assertEquals(0, value.compareTo(value));
	}

	/**
	 * Compares the created value to a smaller value to test that returns a 
	 * positive value.
	 */
	@DisplayName("Compare To Smaller - Return Positive Number")
	@Test
	default void compareToSmallerValue() {
		T value = createValue();
		T smallerValue = createSmallerValue();
		assertTrue(value.compareTo(smallerValue) > 0);
	}

	/**
	 * Compares the created value to a larger value to test that returns a 
	 * negative value.
	 */
	@DisplayName("Compare To Larger - Return Negative Number")
	@Test
	default void compareToLargerValue() {
		T value = createValue();
		T smallerValue = createSmallerValue();
		assertTrue(smallerValue.compareTo(value) < 0);
	}

}
