package com.ruralhousejsf.businessLogic;

import java.util.Date;
import java.util.Vector;

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
	 * @return vector of rural houses
	 */
	public Vector<RuralHouse> getAllRuralHouses();
	
	/**
	 * Obtain all of the offers of a RuralHouse between a range of dates.
	 * 
	 * @param rh
	 * @param firstDay
	 * @param lastDay
	 * @return vector of offers
	 */
	public Vector<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay);
	
	/**
	 * Verify that the login is correct with a user and a pass given.
	 * 
	 * @param user
	 * @param pass
	 * @return boolean that check the login
	 */
	public boolean login(String user, String pass);
	
}
