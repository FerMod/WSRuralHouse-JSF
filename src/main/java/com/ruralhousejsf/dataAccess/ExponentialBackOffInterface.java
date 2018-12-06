package com.ruralhousejsf.dataAccess;

@FunctionalInterface
public interface ExponentialBackOffInterface<T> {
	T execute();
}
