package com.ruralhousejsf.domain;

import java.io.Serializable;

public class Client implements Serializable {

	private static final long serialVersionUID = -1989696498234692075L;
	
	private String username;
	private String password;

	public Client() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Client [username=" + username + ", password=" + password + "]";
	}

}
