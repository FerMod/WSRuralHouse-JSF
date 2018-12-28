package com.ruralhousejsf.domain;

import java.io.Serializable;

/**
 * The {@code Client} is a {@code Serializable} class that represents a client.
 * 
 * @see Serializable
 */
public class Client implements Serializable {

	private long id;
	private String username;
	private String password;

	/**
	 * An empty constructor used by hibernate.
	 * <p>
	 * Hibernate creates objects via reflection using {@linkplain java.lang.Class#newInstance()} to 
	 * create a new instance the classes.For that reason, it requires a no-arg constructor of at least
	 * package visibility, to be able to instantiate this object.
	 */
	Client() {}

	public Client(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Returns the Client id.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Change the client id to a new one.
	 * 
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get the client username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Change the client username to a new one.
	 * 
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the client password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Change the client password to a new one.
	 * 
	 * @param password the new password
	 */
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
