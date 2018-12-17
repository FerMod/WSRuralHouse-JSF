package com.ruralhousejsf.domain;

import java.io.Serializable;

public class Client implements Serializable {

	private long id;
	private String username;
	private String password;

	public Client() {}

	public Client(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return String.format("Client [id: %s, username: %s, password: %s]", id, username, password);
	}

	/**
	 * Auto-generated serial version ID
	 */
	private static final long serialVersionUID = -1989696498234692075L;

}
