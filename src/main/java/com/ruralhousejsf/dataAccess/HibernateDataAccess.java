package com.ruralhousejsf.dataAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;

public class HibernateDataAccess implements HibernateDataAccessInterface {

	private final static Logger logger = Logger.getLogger(HibernateDataAccess.class.getSimpleName());

	public static void main(String[] args) {
		new HibernateDataAccess().initializeDB();
	}

	public HibernateDataAccess() {
		BasicConfigurator.configure();
	}

	public void initializeDB() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
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
		logger.debug("BD borrada");

		createClient("cliente", "cliente123");
		createClient("user", "user123");
		createClient("paco", "paco123");

		RuralHouse rh1 = createRuralHouse("Ezkioko etxea","Ezkio");
		RuralHouse rh2 = createRuralHouse("Eskiatzeko etxea","Jaca");
		RuralHouse rh3 = createRuralHouse("Udaletxea","Bilbo");
		RuralHouse rh4 = createRuralHouse("Gaztetxea","Renteria");


		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			createOffer(rh1, sdf.parse("09/12/2018"), sdf.parse("11/12/2018"), 25.0);
			createOffer(rh1, sdf.parse("12/12/2018"), sdf.parse("15/12/2018"), 25.0);
			createOffer(rh2, sdf.parse("05/12/2018"), sdf.parse("10/12/2018"), 35.5);
			createOffer(rh3, sdf.parse("03/01/2019"), sdf.parse("09/01/2019"), 40.0);
			createOffer(rh4, sdf.parse("07/01/2019"), sdf.parse("21/01/2019"), 36.0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		logger.debug("BD inicializada");
	}

	public RuralHouse createRuralHouse(String description, String city) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		RuralHouse rh = new RuralHouse();
		rh.setDescription(description);
		rh.setCity(city);
		session.save(rh);
		session.getTransaction().commit();
		logger.debug(rh.toString() + " created.");
		return rh;
	}

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, double price) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Offer offer = new Offer();
		offer.setStartDate(firstDay);
		offer.setEndDate(lastDay);
		offer.setPrice(price);
		offer.setRuralHouse(ruralHouse);
		session.save(offer);
		logger.debug(offer.toString() + " for " + ruralHouse.toString() + " created.");
		session.getTransaction().commit();
		return offer;
	}

	public Client createClient(String user, String pass) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Client client = new Client();
		client.setUsername(user);
		client.setPassword(pass);
		session.save(client);
		logger.debug(client.toString() + " created.");
		session.getTransaction().commit();
		return client;
	}

	public List<RuralHouse> getAllRuralHouses() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<RuralHouse> result = session.createQuery("from RuralHouse").list();
		logger.debug("getAllRuralHouses()");
		session.getTransaction().commit();
		return result;
	}

	public List<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(firstDay) + " " + sdf.format(lastDay));
		List<Offer> resultOf = session.createQuery("from Offer where ruralHouse=" + rh.getId() + " and startDate>='" + sdf.format(firstDay)
				+ "' and endDate<='" + sdf.format(lastDay) + "'").list();
		Vector<Offer> v = new Vector(resultOf);
		System.out.println(">> HibernateDataAccess: getOffers of " + rh.toString() + " with startDate: " + firstDay.toString() + " and finalDate" + lastDay.toString());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		@SuppressWarnings("unchecked")
		List<Offer> result = session.createQuery("from Offer where firstDay >= '" + formatter.format(firstDay) + "' and lastDay <= '" + formatter.format(lastDay) + "'").list();
		logger.debug("getOffers of " + rh.toString() + " with startDate: " + firstDay.toString() + " and finalDate" + lastDay.toString());
		session.getTransaction().commit();
		return v;
	}
	
	public Vector<Client> getClient(String user, String pass) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Client> result = session.createQuery("from Client where username ='" + user + "' and password = '" + pass + "'").list();
		logger.debug("getUser " + user + " with password " + pass + ".");
		session.getTransaction().commit();
		return result;
	}

}
