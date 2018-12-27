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

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}

		if (!Client.class.isAssignableFrom(obj.getClass())) {
			return false;
		}

		final Client other = (Client) obj;
		if (this.id != other.id) {
			return false;
		}

		if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
			return false;
		}

		if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 31 * hash + Long.hashCode(this.id);
		hash = 31 * hash + (this.username != null ? this.username.hashCode() : 0);
		hash = 31 * hash + (this.password != null ? this.password.hashCode() : 0);
		return hash;
	}

	/**
	 * Auto-generated serial version ID
	 */
	private static final long serialVersionUID = -1989696498234692075L;

}
