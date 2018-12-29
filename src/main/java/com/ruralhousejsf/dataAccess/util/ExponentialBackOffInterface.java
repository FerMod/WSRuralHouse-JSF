package com.ruralhousejsf.dataAccess.util;

@FunctionalInterface
public interface ExponentialBackOffInterface<T> {
	T execute() throws Exception;
}