package com.ruralhousejsf.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import com.ruralhousejsf.domain.event.ValueAddedListener;

//@XmlAccessorType(XmlAccessType.FIELD)
//@Entity
public class TravelAgency extends AbstractUser {

	private List<Booking> bookings;
	transient private Map<RuralHouse, ValueAddedListener<Offer>> eventListenersMap;

	public TravelAgency(String email, String username, String password) {
		super(email, username, password, UserType.TRAVEL_AGENCY);
		bookings = new ArrayList<Booking>();
		eventListenersMap = new HashMap<RuralHouse, ValueAddedListener<Offer>>();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public UserType getRole() {
		return UserType.TRAVEL_AGENCY;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	private static final long serialVersionUID = -1989696498234692075L;

}
