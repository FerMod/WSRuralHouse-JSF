package com.ruralhousejsf.dataAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;

public class HibernateDataAccess implements HibernateDataAccessInterface {
	
	final static Logger logger = Logger.getLogger(HibernateDataAccess.class);

	public HibernateDataAccess() {
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
		
		createOffer(rh1, LocalDate.of(2018, 12, 9), LocalDate.of(2018, 12, 9), 25.0);
		createOffer(rh1, LocalDate.of(2018, 12, 12), LocalDate.of(2018, 15, 9), 25.0);
		createOffer(rh2, LocalDate.of(2018, 12, 5), LocalDate.of(2018, 12, 10), 35.5);
		createOffer(rh3, LocalDate.of(2018, 1, 3), LocalDate.of(2018, 1, 9), 40.0);
		createOffer(rh4, LocalDate.of(2018, 1, 7), LocalDate.of(2018, 1, 21), 36.0);
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
		System.out.println(">> HibernateDataAccess: " + rh.toString() + " created.");
		return rh;
	}

	public Offer createOffer(RuralHouse ruralHouse, LocalDate firstDay, LocalDate lastDay, double price) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Offer o = new Offer();
		o.setStartDate(firstDay);
		o.setEndDate(lastDay);
		o.setPrice(price);
		o.setRuralHouse(ruralHouse);
		session.save(o);
		System.out.println(">> HibernateDataAccess: " + o.toString() + " for " + ruralHouse.toString() + " created.");
		session.getTransaction().commit();
		return o;
	}
	
	public Client createClient(String user, String pass) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Client c = new Client();
		c.setUsername(user);
		c.setPassword(pass);
		session.save(c);
		System.out.println(">> HibernateDataAccess: " + c.toString() + " created.");
		session.getTransaction().commit();
		return c;
	}

	public Vector<RuralHouse> getAllRuralHouses() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<RuralHouse> resultRH = session.createQuery("from RuralHouse").list();
		Vector<RuralHouse> v = new Vector(resultRH);
		System.out.println(">> HibernateDataAccess: getAllRuralHouses()");
		session.getTransaction().commit();
		return v;
	}

	public Vector<Offer> getOffers(RuralHouse rh, LocalDate firstDay, LocalDate lastDay) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		SimpleDateFormat formateador = new SimpleDateFormat("yy-MM-dd");
		List<Offer> resultOf = session.createQuery("from Offer where firstDay>='" + formateador.format(firstDay)
				+ "' and lastDay<='" + formateador.format(lastDay) + "'").list();
		Vector<Offer> v = new Vector(resultOf);
		System.out.println(">> HibernateDataAccess: getOffers of " + rh.toString() + " with startDate: " + firstDay.toString() + " and finalDate" + lastDay.toString());
		session.getTransaction().commit();
		return v;
	}
	
	public Vector<Client> getClient(String user, String pass) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Client> resultOf = session.createQuery("from Client where username='" + user
		+ "' and password='" + pass + "'").list();
		Vector<Client> v = new Vector(resultOf);
		System.out.println(">> HibernateDataAccess: getUser " + user + " with password " + pass + ".");
		session.getTransaction().commit();
		return v;
	}

	public static void main(String[] args) {
		HibernateDataAccess hba = new HibernateDataAccess();
		hba.initializeDB();
	}
}
