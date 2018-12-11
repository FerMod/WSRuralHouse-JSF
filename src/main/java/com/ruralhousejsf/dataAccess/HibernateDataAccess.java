package com.ruralhousejsf.dataAccess;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.hibernate.Session;

import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.domain.util.ParseDate;

public class HibernateDataAccess implements HibernateDataAccessInterface {
	
	private static final Level LOG_LEVEL = Level.ALL;
	private static final String LOG_PATTERN = "[%-5p] [%d{dd/MM/yyyy HH:mm:ss}] %c %M - %m%n";
	private static final Logger LOGGER = Logger.getLogger(HibernateDataAccess.class.getSimpleName());

	public static void main(String[] args) {
		new HibernateDataAccess();
	}

	public HibernateDataAccess() {
		initLogger();
		initializeDB();
	}
	
	private void initLogger() {
		
		// Setup basic configuration
		BasicConfigurator.configure();
		
		// Set the logger pattern
		PatternLayout layout = new PatternLayout(LOG_PATTERN);		
		LOGGER.addAppender(new ConsoleAppender(layout));
		
		// Dont allow log propagation to parent loggers
		LOGGER.setAdditivity(false);
		
		// Set the messages level
		LOGGER.setLevel(LOG_LEVEL);
		
	}

	public void initializeDB() {
		
		LOGGER.debug("Inicializando BD");
		
		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Client> resultClient = session.createQuery("from Client").list();		
		for (Client c : resultClient) {
			session.delete(c);
		}

		@SuppressWarnings("unchecked")
		List<Offer> resultOffer = session.createQuery("from Offer").list();
		for (Offer o : resultOffer) {
			session.delete(o);
		}

		@SuppressWarnings("unchecked")
		List<RuralHouse> resultRH = session.createQuery("from RuralHouse").list();
		for (RuralHouse rh : resultRH) {
			session.delete(rh);
		}

		session.getTransaction().commit();
		LOGGER.debug("BD borrada");

		createClient("cliente", "cliente123");
		createClient("user", "user123");
		createClient("paco", "paco123");

		RuralHouse rh1 = createRuralHouse("Ezkioko etxea","Ezkio");
		RuralHouse rh2 = createRuralHouse("Eskiatzeko etxea","Jaca");
		RuralHouse rh3 = createRuralHouse("Udaletxea","Bilbo");
		RuralHouse rh4 = createRuralHouse("Gaztetxea","Renteria");

		createOffer(rh1, LocalDate.of(2018, 12, 9), LocalDate.of(2018, 12, 11), 25.0);
		createOffer(rh1, LocalDate.of(2018, 12, 12), LocalDate.of(2018, 12, 15), 25.0);
		createOffer(rh2, LocalDate.of(2018, 12, 5), LocalDate.of(2018, 12, 10), 35.5);
		createOffer(rh3, LocalDate.of(2019, 1, 3), LocalDate.of(2019, 1, 9), 40.0);
		createOffer(rh4, LocalDate.of(2019, 1, 07), LocalDate.of(2019, 1, 21), 36.0);
		
		LOGGER.debug("BD inicializada");
	}

	public RuralHouse createRuralHouse(String description, String city) {
		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		RuralHouse ruralHouse = new RuralHouse(description, city);
		session.save(ruralHouse);
		session.getTransaction().commit();
		LOGGER.debug(ruralHouse.toString() + " created.");
		return ruralHouse;
	}
	
	public Offer createOffer(RuralHouse ruralHouse, LocalDate firstDay, LocalDate lastDay, double price) {		
		return createOffer(ruralHouse, ParseDate.asDate(firstDay), ParseDate.asDate(lastDay), price);
	}

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, double price) {
		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Offer offer = new Offer(ruralHouse, firstDay, lastDay, price);
		session.save(offer);
		LOGGER.debug(offer.toString() + " for " + ruralHouse.toString() + " created.");
		session.getTransaction().commit();
		return offer;
	}

	public Client createClient(String user, String pass) {
		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Client client = new Client(user, pass);
		session.save(client);
		LOGGER.debug(client.toString() + " created.");
		session.getTransaction().commit();
		return client;
	}

	public List<RuralHouse> getAllRuralHouses() {
		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<RuralHouse> result = session.createQuery("from RuralHouse").list();
		LOGGER.debug("getAllRuralHouses()");
		session.getTransaction().commit();
		return result;
	}
	
	public List<Offer> getOffers(RuralHouse ruralHouse, LocalDate firstDay, LocalDate lastDay) {		
		return getOffers(ruralHouse, ParseDate.asDate(firstDay), ParseDate.asDate(lastDay));
	}

	public List<Offer> getOffers(RuralHouse ruralHouse, Date firstDay, Date lastDay) {
		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		@SuppressWarnings("unchecked")
		List<Offer> result = session.createQuery("from Offer where firstDay >= '" + formatter.format(firstDay) + "' and lastDay <= '" + formatter.format(lastDay) + "'").list();
		LOGGER.debug("getOffers of " + ruralHouse.toString() + " with startDate: " + firstDay.toString() + " and finalDate" + lastDay.toString());
		session.getTransaction().commit();
		return result;
	}
	
	public List<Client> getClient(String user, String pass) {
		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Client> result = session.createQuery("from Client where username ='" + user + "' and password = '" + pass + "'").list();
		LOGGER.debug("getUser " + user + " with password " + pass + ".");
		session.getTransaction().commit();
		return result;
	}

}
