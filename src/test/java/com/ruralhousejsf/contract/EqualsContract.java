package com.ruralhousejsf.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * This interface defines tests to verify that the objects implements correctly 
 * the {@code equals} method, which implements an equivalence relation on 
 * non-null object references.
 * <p>
 * Note that it is generally necessary to override the {@code hashCode} method
 * whenever the {@code equals} method is overridden, so as to maintain the general contract 
 * for the {@code hashCode} method, which states that equal objects must have 
 * equal hash codes.
 *  
 * @param <T> the type of objects that this object may be compared to
 * 
 * @see Object#equals(Object)
 * @see Object#hashCode()
 */
@DisplayName("Equals Test")
@Tag("equals")
public interface EqualsContract<T> extends Testable<T> {

	/**
	 * Creates and returns a non equal value to use in the equality tests.<br>
	 * This method, for its correct execution, should return value non equal 
	 * than the returned by {@link Testable#createValue}.
	 * 
	 * @return the created value
	 */
	T createNotEqualValue();

	/**
	 * Compares the created value to itself to test that the equality holds.
	 */
	@DisplayName("Equals Itself - True")
	@Test
	default void equalValueItself() {
		T value = createValue();
		assertEquals(value, value);
	}

	/**
	 * Compares the created value to a {@code null} to test that the equality 
	 * does not hold.
	 */
	@DisplayName("Equals Null - False")
	@Test
	default void notEqualNullValue() {
		T value = createValue();
		assertFalse(value.equals(null));
	}

	/**
	 * Compares the created value to a different value to test that the equality
	 * does not hold.
	 */
	@DisplayName("Equals Different - False")
	@Test
	default void notEqualDifferentValue() {
		T value = createValue();
		T differentValue = createNotEqualValue();
		assertNotEquals(value, differentValue);
		assertNotEquals(differentValue, value);
	}

}
