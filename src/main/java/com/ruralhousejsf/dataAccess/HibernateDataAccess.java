package com.ruralhousejsf.dataAccess;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.domain.util.ParseDate;
import com.ruralhousejsf.exceptions.BadDatesException;
import com.ruralhousejsf.logger.ConsoleLogger;

public class HibernateDataAccess implements HibernateDataAccessInterface {

	private static final Logger LOGGER = ConsoleLogger.createLogger(HibernateDataAccess.class);
	
	public static void main(String[] args) {
		new HibernateDataAccess().initializeDB();
	}

	public HibernateDataAccess() {}

	public void initializeDB() {

		LOGGER.debug("Initializing DB...");
		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		LOGGER.trace("Hibernate session obtained");		
		session.beginTransaction();
		LOGGER.trace("Transaction started");

		LOGGER.debug("Deleting DB data...");

		Query queryClient = session.createSQLQuery("TRUNCATE Client");
		queryClient.executeUpdate();
		LOGGER.trace("Table Client truncated");

		Query queryOffer = session.createSQLQuery("TRUNCATE Offer");
		queryOffer.executeUpdate();
		LOGGER.trace("Table Offer truncated");

		Query queryRuralHouse = session.createSQLQuery("TRUNCATE RuralHouse");
		queryRuralHouse.executeUpdate();
		LOGGER.trace("Table RuralHouse truncated");

		session.getTransaction().commit();
		LOGGER.trace("Transaction commit and session closed");
		LOGGER.debug("DB data deleted");

		LOGGER.debug("Creating DB data...");

		createClient("cliente", "cliente123");
		createClient("user", "user123");
		createClient("paco", "paco123");
		LOGGER.trace("Data of Client created");

		RuralHouse rh1 = createRuralHouse("Ezkioko etxea","Ezkio");
		RuralHouse rh2 = createRuralHouse("Eskiatzeko etxea","Jaca");
		RuralHouse rh3 = createRuralHouse("Udaletxea","Bilbo");
		RuralHouse rh4 = createRuralHouse("Gaztetxea","Renteria");
		LOGGER.trace("Data of RuralHouse created");

		try {
			createOffer(rh1, LocalDate.of(2018, 12, 9), LocalDate.of(2018, 12, 11), 25.0);
			createOffer(rh1, LocalDate.of(2018, 12, 12), LocalDate.of(2018, 12, 15), 25.0);
			createOffer(rh2, LocalDate.of(2018, 12, 5), LocalDate.of(2018, 12, 10), 35.5);
			createOffer(rh3, LocalDate.of(2019, 1, 3), LocalDate.of(2019, 1, 9), 40.0);
			createOffer(rh4, LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 21), 36.0);
			createOffer(rh4, LocalDate.of(2018, 1, 7), LocalDate.of(2019, 1, 7), 78.0);

		} catch (BadDatesException e) {
			e.printStackTrace();
		}
		LOGGER.trace("Data of Offer created");

		LOGGER.debug("DB data created");		
		LOGGER.debug("Initialization of BD finished");		

	}

	public RuralHouse createRuralHouse(String description, String city) {

		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		LOGGER.trace("Hibernate session obtained");	
		session.beginTransaction();
		LOGGER.trace("Transaction started");

		RuralHouse ruralHouse = new RuralHouse(description, city);
		session.save(ruralHouse);

		session.getTransaction().commit();
		LOGGER.trace("Transaction commit and session closed");

		LOGGER.debug(ruralHouse.toString() + " created");
		return ruralHouse;
	}

	public Offer createOffer(RuralHouse ruralHouse, LocalDate startDate, LocalDate endDate, double price) throws BadDatesException {		
		return createOffer(ruralHouse, ParseDate.asDate(startDate), ParseDate.asDate(endDate), price);
	}

	public Offer createOffer(RuralHouse ruralHouse, Date startDate, Date endDate, double price) throws BadDatesException {

		if(startDate.after(endDate)) {
			throw new BadDatesException("The startDate have to be before than the endDate.");
		}

		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		LOGGER.trace("Hibernate session obtained");	
		session.beginTransaction();
		LOGGER.trace("Transaction started");

		Offer offer = new Offer(ruralHouse, startDate, endDate, price);
		session.save(offer);

		session.getTransaction().commit();
		LOGGER.trace("Transaction commit and session closed");

		LOGGER.debug(offer.toString() + " for " + ruralHouse.toString() + " created");

		return offer;
	}

	public Client createClient(String user, String pass) {

		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		LOGGER.trace("Hibernate session obtained");	
		session.beginTransaction();
		LOGGER.trace("Transaction started");

		Client client = new Client(user, pass);
		session.save(client);

		session.getTransaction().commit();
		LOGGER.trace("Transaction commit and session closed");

		LOGGER.debug(client.toString() + " created");
		return client;
	}

	public List<RuralHouse> getAllRuralHouses() {
		LOGGER.debug("Get all rural houses");

		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		LOGGER.trace("Hibernate session obtained");	
		session.beginTransaction();
		LOGGER.trace("Transaction started");

		Query query = session.createQuery("FROM RuralHouse");
		@SuppressWarnings("unchecked")
		List<RuralHouse> result = query.list();

		session.getTransaction().commit();
		LOGGER.trace("Transaction commit and session closed");

		return result;
	}

	public List<Offer> getOffers(RuralHouse ruralHouse, LocalDate firstDate, LocalDate endDate) throws BadDatesException {		
		return getOffers(ruralHouse, ParseDate.asDate(firstDate), ParseDate.asDate(endDate));
	}

	public List<Offer> getOffers(RuralHouse ruralHouse, Date startDate, Date endDate) throws BadDatesException {

		if(startDate.after(endDate)) {
			throw new BadDatesException("The startDate have to be before than the endDate.");
		}

		LOGGER.debug("getOffers of " + ruralHouse.toString() + " with startDate " + startDate.toString() + " and endDate " + endDate.toString());

		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		LOGGER.trace("Hibernate session obtained");	
		session.beginTransaction();
		LOGGER.trace("Transaction started");
		
		Criteria criteria = session.createCriteria(Offer.class);
		criteria.setFetchMode("ruralHouse", FetchMode.JOIN);
		
		Criterion betweenStartDate = Restrictions.between("startDate", startDate, endDate);
		Criterion betweenEndDate = Restrictions.between("endDate", startDate, endDate);
		Criterion leStartDate = Restrictions.le("startDate", startDate);
		Criterion geEndDate = Restrictions.ge("endDate", endDate);
		
		criteria.add(Restrictions.or(Restrictions.or(betweenStartDate, betweenEndDate), Restrictions.and(leStartDate, geEndDate)));
		
		@SuppressWarnings("unchecked")
		List<Offer> result = criteria.list();
		
		/*
		Query query = session.createQuery("FROM Offer " + 
				"WHERE (start_date BETWEEN :startDate AND :endDate) " + 
				"OR (end_date BETWEEN :startDate AND :endDate) " + 
				"OR (start_date <= :startDate AND end_date >= :endDate)"
				);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		query.setParameter("startDate", formatter.format(startDate));
		query.setParameter("endDate", formatter.format(endDate));

		@SuppressWarnings("unchecked")
		List<Offer> result = query.list();
		*/

		session.getTransaction().commit();
		LOGGER.trace("Transaction commit and session closed");

		return result;
	}

	public Optional<Client> getClient(String username, String password) {
		LOGGER.debug("getUser " + username + " with password " + password);

		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		LOGGER.trace("Hibernate session obtained");	
		session.beginTransaction();
		LOGGER.trace("Transaction started");

		Query query = session.createQuery("FROM Client WHERE username = :username AND password = :password");
		query.setParameter("username", username);
		query.setParameter("password", password);

		Optional<Client> result = Optional.ofNullable((Client) query.uniqueResult());

		session.getTransaction().commit();
		LOGGER.trace("Transaction commit and session closed");

		return result;
	}

	public <T extends Serializable> void delete(Class<?> cls, T key) {
		LOGGER.debug("delete " + cls.getSimpleName() + " with key " + key);

		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		LOGGER.trace("Hibernate session obtained");	
		session.beginTransaction();
		LOGGER.trace("Transaction started");

		Optional<Object> persistantInstance = Optional.ofNullable(session.get(cls, key));

		if(persistantInstance.isPresent()) {
			session.delete(persistantInstance.get());
			LOGGER.debug("Persistance instance " + cls.getSimpleName() + " with key " + key + " deleted.");
		} else {
			LOGGER.debug("No persistance instance " + cls.getSimpleName()  + " with key " + key + " was found.");
		}

		session.getTransaction().commit();
		LOGGER.trace("Transaction commit and session closed");

	}

}
