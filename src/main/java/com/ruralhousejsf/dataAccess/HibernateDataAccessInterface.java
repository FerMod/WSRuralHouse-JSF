package com.ruralhousejsf.dataAccess;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.exceptions.BadDatesException;

public interface HibernateDataAccessInterface {
	
	/**
	 * Method used to initialize the database
	 * 
	 */
	public void initializeDB();
	
	/**
	 * Creates a RuralHouse in the database with his description and his city.
	 * 
	 * @param description
	 * @param city
	 * @return RuralHouse created
	 */
	public RuralHouse createRuralHouse(String description, String city);
	
	/**
	 * Creates a Offer of a RuralHouse with his firstDay, his endDate and his price.
	 * 
	 * @param ruralHouse
	 * @param startDate
	 * @param endDate
	 * @param price
	 * @return the created Offer for the RuralHouse passed as parameter
	 * @throws BadDatesException 
	 */
	public Offer createOffer(RuralHouse ruralHouse, LocalDate firstDay, LocalDate endDate, double price) throws BadDatesException;
	
	/**
	 * Creates a Offer of a RuralHouse with his firstDay, his endDate and his price.
	 * 
	 * @param ruralHouse
	 * @param firstDay
	 * @param endDate
	 * @param price
	 * @return the created offer for the RuralHouse passed as parameter
	 * @throws BadDatesException 
	 */
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date endDate, double price) throws BadDatesException;

	/**
	 * Creates a Client in the database with his username and his password.
	 * 
	 * @param user
	 * @param pass
	 * @return Client created
	 */
	public Client createClient(String user, String pass);

	/**
	 * Obtain all the rural houses.
	 * 
	 * @return list of rural houses
	 */
	public List<RuralHouse> getAllRuralHouses();
	
	/**
	 * Obtain all of the offers of a RuralHouse between a range of dates.
	 * 
	 * @param ruralHouse
	 * @param firstDay
	 * @param endDate
	 * @return list of offers
	 * @throws BadDatesException 
	 */
	public List<Offer> getOffers(RuralHouse ruralHouse, LocalDate firstDay, LocalDate endDate) throws BadDatesException;

	/**
	 * Obtain all of the offers of a RuralHouse between a range of dates.
	 * 
	 * @param ruralHouse
	 * @param firstDay
	 * @param endDate
	 * @return list of offers
	 * @throws BadDatesException 
	 */
	public List<Offer> getOffers(RuralHouse ruralHouse, Date firstDay, Date endDate) throws BadDatesException;
	
	/**
	 * Obtain a Client with the username and the password given.
	 * 
	 * @param user
	 * @param pass
	 * @return optional with the found client or a <code>Optional.Empty()</code> if none was found
	 */
	public Optional<Client> getClient(String user, String pass);
	
	/**
	 * Delete the persistent instance with the given id
	 * 
	 * @param <T> the key type
	 * @param cls the instance class
	 * @param key the instance key
	 */
	public <T extends Serializable> void delete(Class<?> cls, T key);
	
}
