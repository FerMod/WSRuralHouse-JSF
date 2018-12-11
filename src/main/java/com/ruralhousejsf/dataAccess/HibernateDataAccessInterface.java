package com.ruralhousejsf.dataAccess;

import java.util.Date;
import java.util.List;
import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;

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
	 * @param lastDay
	 * @return list of offers
	 */
	public List<Offer> getOffers(RuralHouse ruralHouse, Date firstDay, Date lastDay);
	
	/**
	 * Obtain a Client with the username and the password given.
	 * 
	 * @param user
	 * @param pass
	 * @return client
	 */
	public List<Client> getClient(String user, String pass);
	
}