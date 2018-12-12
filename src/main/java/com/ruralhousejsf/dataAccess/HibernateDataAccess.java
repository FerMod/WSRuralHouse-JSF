package com.ruralhousejsf.dataAccess;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ruralhousejsf.debug.ConsoleLogger;
import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.domain.util.ParseDate;
import com.ruralhousejsf.exceptions.BadDatesException;

public class HibernateDataAccess implements HibernateDataAccessInterface {

	private static final Logger LOGGER = ConsoleLogger.createLogger(HibernateDataAccess.class);

	public static void main(String[] args) {
		new HibernateDataAccess().initializeDB();
	}

	public HibernateDataAccess() {
	}

	public void initializeDB() {

		LOGGER.debug("Inicializando la BD");

		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		LOGGER.trace("Sesion creada y empezada transaccion");

		Query queryClient = session.createSQLQuery("TRUNCATE Client");
		queryClient.executeUpdate();
		LOGGER.trace("Tabla Client eliminada");

		Query queryOffer = session.createSQLQuery("TRUNCATE Offer");
		queryOffer.executeUpdate();
		LOGGER.trace("Tabla Offer eliminada.");

		Query queryRuralHouse = session.createSQLQuery("TRUNCATE RuralHouse");
		queryRuralHouse.executeUpdate();
		LOGGER.trace("Tabla RuralHouse eliminada");

		session.getTransaction().commit();
		LOGGER.trace("Commit de transaccion y sesión cerrada");
		LOGGER.debug("BD borrada");

		createClient("cliente", "cliente123");
		createClient("user", "user123");
		createClient("paco", "paco123");
		LOGGER.trace("Datos de la tabla Client creados");

		RuralHouse rh1 = createRuralHouse("Ezkioko etxea","Ezkio");
		RuralHouse rh2 = createRuralHouse("Eskiatzeko etxea","Jaca");
		RuralHouse rh3 = createRuralHouse("Udaletxea","Bilbo");
		RuralHouse rh4 = createRuralHouse("Gaztetxea","Renteria");
		LOGGER.trace("Datos de la tabla RuralHouse creados");

		try {
			createOffer(rh1, LocalDate.of(2018, 12, 9), LocalDate.of(2018, 12, 11), 25.0);
			createOffer(rh1, LocalDate.of(2018, 12, 12), LocalDate.of(2018, 12, 15), 25.0);
			createOffer(rh2, LocalDate.of(2018, 12, 5), LocalDate.of(2018, 12, 10), 35.5);
			createOffer(rh3, LocalDate.of(2019, 1, 3), LocalDate.of(2019, 1, 9), 40.0);
			createOffer(rh4, LocalDate.of(2019, 1, 07), LocalDate.of(2019, 1, 21), 36.0);
		} catch (BadDatesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.trace("Datos de la tabla Offer creados");

		LOGGER.debug("Inicializacion de la BD terminada");

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

	public Offer createOffer(RuralHouse ruralHouse, LocalDate startDate, LocalDate endDate, double price) throws BadDatesException {		
		return createOffer(ruralHouse, ParseDate.asDate(startDate), ParseDate.asDate(endDate), price);
	}

	public Offer createOffer(RuralHouse ruralHouse, Date startDate, Date endDate, double price) throws BadDatesException {
		if(startDate.before(endDate)) {
			Session session = HibernateSession.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Offer offer = new Offer(ruralHouse, startDate, endDate, price);
			session.save(offer);

			session.getTransaction().commit();

			LOGGER.debug(offer.toString() + " for " + ruralHouse.toString() + " created.");
			return offer;
		} else {
			throw new BadDatesException("The startDate have to be before than the endDate.");
		}
	}

	public Client createClient(String user, String pass) {

		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Client client = new Client(user, pass);
		session.save(client);

		session.getTransaction().commit();

		LOGGER.debug(client.toString() + " created.");
		return client;
	}

	public List<RuralHouse> getAllRuralHouses() {
		LOGGER.debug("Obtener todas las casa rurales");

		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		LOGGER.trace("Sesion creada y empezada transaccion");

		Query query = session.createQuery("FROM RuralHouse");

		@SuppressWarnings("unchecked")
		List<RuralHouse> result = query.list();

		session.getTransaction().commit();
		LOGGER.trace("Commit de transaccion y sesion cerrada");

		return result;
	}

	public List<Offer> getOffers(RuralHouse ruralHouse, LocalDate firstDate, LocalDate endDate) throws BadDatesException {		
		return getOffers(ruralHouse, ParseDate.asDate(firstDate), ParseDate.asDate(endDate));
	}

	public List<Offer> getOffers(RuralHouse ruralHouse, Date startDate, Date endDate) throws BadDatesException {
		if(startDate.before(endDate)) {
			LOGGER.debug("getOffers of " + ruralHouse.toString() + " with startDate " + startDate.toString() + " and endDate " + endDate.toString());

			Session session = HibernateSession.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Query query = session.createQuery("FROM Offer WHERE startDate >= :startDate AND endDate <= :endDate");

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			query.setParameter("startDate", formatter.format(startDate));
			query.setParameter("endDate", formatter.format(endDate));

			@SuppressWarnings("unchecked")
			List<Offer> result = query.list();

			session.getTransaction().commit();

			return result;
		} else {
			throw new BadDatesException("The startDate have to be before than the endDate.");
		}
	}

	public Optional<Client> getClient(String username, String password) {
		LOGGER.debug("getUser " + username + " with password " + password + ".");

		Session session = HibernateSession.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query query = session.createQuery("FROM Client WHERE username = :username AND password = :password");
		query.setParameter("username", username);
		query.setParameter("password", password);

		Optional<Client> result = Optional.ofNullable((Client) query.uniqueResult());
		session.getTransaction().commit();

		return result;
	}

}
