package com.ruralhousejsf.businessLogic;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.ruralhousejsf.dataAccess.HibernateDataAccessInterface;
import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;

public interface ApplicationFacadeInterface {
	
	/**
	 * Initialize the data base.
	 */
	public void initializeDB();
	
	/**
	 * Set the DataAccess object for the business logic
	 * 
	 * @param dataAccess
	 */
	public void setDataAccess(HibernateDataAccessInterface dataAccess);
	
	/**
	 * Creates a RuralHouse in the database with his description and his city.
	 * 
	 * @param description
	 * @param city
	 * @return RuralHouse created
	 */
	public RuralHouse createRuralHouse(String description, String city);
	
	/**
	 * Creates a Offer of a RuralHouse with his firstDay, his lastDay and his price.
	 * 
	 * @param ruralHouse
	 * @param firstDay
	 * @param lastDay
	 * @param price
	 * @return Offer of the RuralHouse created
	 */
	public Offer createOffer(RuralHouse ruralHouse, LocalDate firstDay, LocalDate lastDay, double price);
	
	/**
	 * Creates a Offer of a RuralHouse with his firstDay, his lastDay and his price.
	 * 
	 * @param ruralHouse
	 * @param firstDay
	 * @param lastDay
	 * @param price
	 * @return Offer of the RuralHouse created
	 */
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, double price);
	
	/**
	 * Creates a Client in the database and returns the created client
	 * 
	 * @param username
	 * @param password
	 * @return Client created
	 */
	public Client createClient(String username, String password);

	/**
	 * Obtain all the rural houses.
	 * 
	 * @return list of rural houses
	 */
	public List<RuralHouse> getAllRuralHouses();
	
	/**
	 * Obtain all the offers of a RuralHouse between the given range of dates.
	 * 
	 * @param ruralHouse
	 * @param firstDay
	 * @param lastDay
	 * @return vector of offers
	 */
	public List<Offer> getOffers(RuralHouse ruralHouse, LocalDate firstDay, LocalDate lastDay);
	
	/**
	 * Obtain all the offers of a RuralHouse between the given range of dates.
	 * 
	 * @param ruralHouse
	 * @param firstDay
	 * @param lastDay
	 * @return vector of offers
	 */
	public List<Offer> getOffers(RuralHouse ruralHouse, Date firstDay, Date lastDay);
	
	/**
	 * Verify that the login is correct with for the given username and a password.
	 * 
	 * @param username
	 * @param password
	 * @return <code>true</code> if the operation is successful, <code>false</code> otherwise
	 */
	public boolean login(String username, String password);
	
}
