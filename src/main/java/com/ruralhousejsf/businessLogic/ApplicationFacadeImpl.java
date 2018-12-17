package com.ruralhousejsf.businessLogic;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import com.ruralhousejsf.dataAccess.HibernateDataAccessInterface;
import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.domain.util.ParseDate;
import com.ruralhousejsf.exceptions.BadDatesException;
import com.ruralhousejsf.logger.ConsoleLogger;

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
	public Offer createOffer(RuralHouse ruralHouse, LocalDate startDate, LocalDate endDate, double price) throws BadDatesException {
		return createOffer(ruralHouse, ParseDate.asDate(startDate), ParseDate.asDate(endDate), price);
	}

	@Override
	public Offer createOffer(RuralHouse ruralHouse, Date startDate, Date endDate, double price) throws BadDatesException {
		Offer offer = dataAccess.createOffer(ruralHouse, startDate, endDate, price);
		LOGGER.debug(offer.toString());
		return offer;
	}

	@Override
	public Client createClient(String username, String password) {
		LOGGER.debug("Create Client with user: " + username + " and pass: " + password + ".");
		return dataAccess.createClient(username, password);
	}

	@Override
	public List<RuralHouse> getAllRuralHouses() {
		LOGGER.debug("Get all RuralHouses");
		return dataAccess.getAllRuralHouses();
	}

	@Override
	public List<Offer> getOffers(RuralHouse ruralHouse, LocalDate startDate, LocalDate endDate) throws BadDatesException {
		return getOffers(ruralHouse, ParseDate.asDate(startDate), ParseDate.asDate(endDate));
	}

	@Override
	public List<Offer> getOffers(RuralHouse ruralHouse, Date startDate, Date endDate) throws BadDatesException {
		LOGGER.debug("Get offers of " + ruralHouse.toString() + " in startDate: " + startDate.toString() + " and in finalDate: " + endDate.toString() + ".");
		return dataAccess.getOffers(ruralHouse, startDate, endDate);
	}

	@Override
	public boolean login(String username, String password) {
		LOGGER.debug("Login with username: " + username + " and password: " + password + ".");
		Optional<Client> clients = dataAccess.getClient(username, password);
		return clients.isPresent();
	}

	@Override
	public <T extends Serializable> void delete(Class<?> cls, T key) {
		LOGGER.debug("Delete " + cls.getSimpleName() + " with key " + key);
		dataAccess.delete(cls, key);
	}

	public static void main(String[] args) {
		ApplicationFacadeInterface afi = AppFacade.getImpl();
		afi.initializeDB();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		List<RuralHouse> ruralHouseList = afi.getAllRuralHouses();

		LOGGER.trace("Iterate rural house list");
		for (RuralHouse ruralHouse : ruralHouseList) {
			LOGGER.trace(ruralHouse.toString());
		}
		LOGGER.trace("Login user: " + afi.login("user", "user1234"));
		try {
			LOGGER.trace(afi.getOffers(afi.getAllRuralHouses().get(0), sdf.parse("04/12/2018"), sdf.parse("07/01/2019")).toString());
		} catch (ParseException e) {
			LOGGER.error(e);
		} catch (BadDatesException e) {
			LOGGER.error(e);
		}

	}

}
