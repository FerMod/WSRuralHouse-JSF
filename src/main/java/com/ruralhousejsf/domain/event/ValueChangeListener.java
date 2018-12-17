package com.ruralhousejsf.domain.event;

import java.util.Optional;

@FunctionalInterface
public interface ValueChangeListener<T> {

	void onValueChanged(Optional<T> oldValue, Optional<T> newValue);
	
}
