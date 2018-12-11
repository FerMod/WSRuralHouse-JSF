package com.ruralhousejsf.businessLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ruralhousejsf.dataAccess.HibernateDataAccessInterface;
import com.ruralhousejsf.debug.ConsoleLogger;
import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.domain.util.ParseDate;

public class ApplicationFacadeImpl implements ApplicationFacadeInterface {
	
	private HibernateDataAccessInterface dataAccess;
	private static final Logger LOGGER = ConsoleLogger.createLogger(ApplicationFacadeImpl.class); 
	
	public ApplicationFacadeImpl(HibernateDataAccessInterface dataAccess) {
		setDataAccess(dataAccess);
	}

	@Override
	public void setDataAccess(HibernateDataAccessInterface dataAccess) {
		this.dataAccess = dataAccess;
	}
	
	@Override
	public void initializeDB() {
		dataAccess.initializeDB();
	}

	@Override
	public RuralHouse createRuralHouse(String description, String city) {
		RuralHouse ruralHouse = dataAccess.createRuralHouse(description, city);
		LOGGER.debug(ruralHouse.toString());
		return ruralHouse;
	}
	
	@Override
	public Offer createOffer(RuralHouse ruralHouse, LocalDate firstDay, LocalDate lastDay, double price) {
		return createOffer(ruralHouse, ParseDate.asDate(firstDay), ParseDate.asDate(lastDay), price);
	}

	@Override
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, double price) {
		Offer o = dataAccess.createOffer(ruralHouse, firstDay, lastDay, price);
		LOGGER.debug(o.toString());
		return o;
	}

	@Override
	public Client createClient(String user, String pass) {
		LOGGER.debug("Create Client with user: " + user + " and pass: " + pass + ".");
		return dataAccess.createClient(user, pass);
	}

	@Override
	public List<RuralHouse> getAllRuralHouses() {
		LOGGER.debug("Get all RuralHouses");
		return dataAccess.getAllRuralHouses();
	}
	
	@Override
	public List<Offer> getOffers(RuralHouse ruralHouse, LocalDate firstDay, LocalDate lastDay) {
		return getOffers(ruralHouse, ParseDate.asDate(firstDay), ParseDate.asDate(lastDay));
	}

	@Override
	public List<Offer> getOffers(RuralHouse ruralHouse, Date firstDay, Date lastDay) {
		LOGGER.debug("Get offers of " + ruralHouse.toString() + " in startDate: " + firstDay.toString() + " and in finalDate: " + lastDay.toString() + ".");
		return dataAccess.getOffers(ruralHouse, firstDay, lastDay);
	}

	@Override
	public boolean login(String user, String pass) {
		LOGGER.debug("Login with username=" + user + " and password=" + pass + ".");
		List<Client> clients = dataAccess.getClient(user, pass);
		return clients.size() == 1;
	}
	
	public static void main(String[] args) {
		ApplicationFacadeInterface afi = AppFacade.getImpl();
		afi.initializeDB();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(afi.getAllRuralHouses().toString());
		System.out.println(afi.login("user", "user1234"));
		try {
			System.out.println(afi.getOffers(afi.getAllRuralHouses().get(0), sdf.parse("04/12/2018"), sdf.parse("07/01/2018")).toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

}
