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
	 * Method used to truncate the data base.
	 */
	public void truncateDB();
	
	/**
	 * Method used to initialize the database
	 */
	public void initializeDB();
	
	/**
	 * Creates and stores in the database a {@link RuralHouse} in the database with his description and his city.
	 * 
	 * @param description the rural house description
	 * @param city the rural house city
	 * 
	 * @return the created {@link RuralHouse}
	 */
	public RuralHouse createRuralHouse(String description, String city);
	
	/**
	 * Creates and stores in the database an {@link Offer} for the given {@link RuralHouse} that will start and end in the given dates, and will 
	 * have the passed price.
	 * 
	 * @param ruralHouse this offers associated rural house  
	 * @param startDate the offer start date
	 * @param endDate the offer end date
	 * @param price the cost per day of the offer
	 * 
	 * @return the created Offer for the RuralHouse passed as parameter
	 * 
	 * @throws BadDatesException thrown when the start date is greater than the end date
	 */
	public Offer createOffer(RuralHouse ruralHouse, LocalDate startDate, LocalDate endDate, double price) throws BadDatesException;
	
	/**
	 * Creates and stores in the database an {@link Offer} for the given {@link RuralHouse} that will start and end in the given dates, and will 
	 * have the passed price.
	 * 
	 * @param ruralHouse this offers associated rural house  
	 * @param startDate the offer start date
	 * @param endDate the offer end date
	 * @param price the cost per day of the offer
	 * 
	 * @return the created {@link Offer}
	 * 
	 * @throws BadDatesException thrown when the start date is greater than the end date 
	 */
	public Offer createOffer(RuralHouse ruralHouse, Date startDate, Date endDate, double price) throws BadDatesException;

	/**
	 * Creates and stores in the database a {@link Client} with the passed user name and password.
	 * 
	 * @param username the client user name
	 * @param password the client password
	 * 
	 * @return the created {@link Client}
	 */
	public Client createClient(String username, String password);

	/**
	 * Obtains all rural houses stored in the database.
	 * 
	 * @return list of rural houses
	 */
	public List<RuralHouse> getAllRuralHouses();
	
	/**
	 * Obtains all offers of a given {@link RuralHouse} between the defined start and end dates 
	 * range (both inclusive).
	 * 
	 * @param ruralHouse the rural house to apply this search
	 * @param startDate the start date  
	 * @param endDate the end date
	 * 
	 * @return list of offers between both dates of the given rural house
	 * 
	 * @throws BadDatesException thrown when the start date is greater than the end date 
	 */
	public List<Offer> getOffers(RuralHouse ruralHouse, LocalDate startDate, LocalDate endDate) throws BadDatesException;

	/**
	 * Obtains all offers of a given {@link RuralHouse} between the defined start and end dates 
	 * range (both inclusive).
	 * 
	 * @param ruralHouse the rural house to apply this search
	 * @param startDate the start date  
	 * @param endDate the end date
	 * 
	 * @return list of offers between both dates of the given rural house
	 * 
	 * @throws BadDatesException thrown when the start date is greater than the end date 
	 */
	public List<Offer> getOffers(RuralHouse ruralHouse, Date startDate, Date endDate) throws BadDatesException;
	
	/**
	 * Obtains a Client with the given user name and the password, if exists and is found. 
	 * 
	 * @param username the client user name
	 * @param password the client password
	 * 
	 * @return optional containing the found client or an <code>Optional.Empty()</code> if none was found
	 */
	public Optional<Client> getClient(String username, String password);
	
	/**
	 * Returns the persistent instance of the given entity class with the given key
	 * 
	 * @param <T> the class type
	 * @param <U> the key type
	 * 
	 * @param cls the instance class
	 * @param key the instance key
	 * 
	 * @return <code>Optional</code> with the persistent instance, or <code>Optional.empty()</code> if 
	 * no persistent instance is found
	 */
	public <T extends Serializable, U extends Serializable> Optional<T> get(Class<T> cls, U key);
	
	/**
	 * Check if exists the persistent instance with the given key
	 * 
	 * @param <T> the class type
	 * @param <U> the key type
	 * 
	 * @param cls the instance class
	 * @param key the instance key
	 * 
	 * @return <code>true</code> if exists the persistent instance, <code>false</code> otherwise
	 */
	public <T extends Serializable, U extends Serializable> boolean exists(Class<T> cls, U key);

	/**
	 * Delete the persistent instance with the given id
	 * 
	 * @param <T> the class type
	 * @param <U> the key type
	 * 
	 * @param cls the instance class
	 * @param key the instance key
	 */
	public <T extends Serializable, U extends Serializable> void delete(Class<T> cls, U key);
	
}
