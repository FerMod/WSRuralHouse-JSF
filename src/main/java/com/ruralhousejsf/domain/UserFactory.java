package com.ruralhousejsf.domain;

@FunctionalInterface
public interface UserFactory<T extends AbstractUser> {
	T create(String email, String username, String password);
}
