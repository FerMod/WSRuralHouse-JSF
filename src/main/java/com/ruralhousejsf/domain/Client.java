package com.ruralhousejsf.domain;

import java.util.ArrayList;
import java.util.List;

//@XmlAccessorType(XmlAccessType.FIELD)
//@Entity
public class Client extends AbstractUser {

	private static final long serialVersionUID = -1989696498234692075L;
	
	private List<Booking> bookings;

	public Client(String email, String username, String password) {
		super(email, username, password, UserType.CLIENT);
		bookings = new ArrayList<Booking>();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public UserType getRole() {
		return UserType.CLIENT;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

}
