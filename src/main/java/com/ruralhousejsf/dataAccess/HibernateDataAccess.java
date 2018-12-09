package com.ruralhousejsf.dataAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;

import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;

public class HibernateDataAccess implements HibernateDataAccessInterface {

	public HibernateDataAccess() {
	}

	public void initializeDB() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List<Client> resultC = session.createQuery("from Client").list();
		
		for (Client c : resultC) {
			session.delete(c);
		}

		List<Offer> resultO = session.createQuery("from Offer").list();

		for (Offer o : resultO) {
			session.delete(o);
		}

		List<RuralHouse> resultRH = session.createQuery("from RuralHouse").list();

		for (RuralHouse rh : resultRH) {
			session.delete(rh);
		}
		session.getTransaction().commit();
		System.out.println("BD borrada");
		
		createClient("cliente", "cliente123");
		createClient("user", "user123");
		createClient("paco", "paco123");
		
		RuralHouse rh1 = createRuralHouse("Ezkioko etxea","Ezkio");
		RuralHouse rh2 = createRuralHouse("Eskiatzeko etxea","Jaca");
		RuralHouse rh3 = createRuralHouse("Udaletxea","Bilbo");
		RuralHouse rh4 = createRuralHouse("Gaztetxea","Renteria");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			createOffer(rh1, sdf.parse("09/12/2018"), sdf.parse("11/12/2018"), 25.0);
			createOffer(rh1, sdf.parse("12/12/2018"), sdf.parse("15/12/2018"), 25.0);
			createOffer(rh2, sdf.parse("05/12/2018"), sdf.parse("10/12/2018"), 35.5);
			createOffer(rh3, sdf.parse("03/01/2019"), sdf.parse("09/01/2019"), 40.0);
			createOffer(rh4, sdf.parse("07/01/2019"), sdf.parse("21/01/2019"), 36.0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("BD inicializada");
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

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, double price) {
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

	public Vector<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay) {
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
