package com.ruralhousejsf.dataAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;

import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;

public class HibernateDataAccess {

	public HibernateDataAccess() {
	}

	private void initializeDB() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Offer> resultO = session.createQuery("from Offer").list();

		for (Offer o : resultO) {
			session.delete(o);
		}

		List<RuralHouse> resultRH = session.createQuery("from RuralHouse").list();

		for (RuralHouse rh : resultRH) {
			session.delete(rh);
			session.getTransaction().commit();
		}
		System.out.println("BD borrada");
		
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
		return rh;
	}

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, double d) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Offer o = new Offer();
		o.setStartDate(firstDay);
		o.setEndDate(lastDay);
		o.setPrice(d);
		o.setRuralHouse(ruralHouse);
		session.save(o);
		session.getTransaction().commit();
		return o;
	}

	public Vector<RuralHouse> getAllRuralHouses() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<RuralHouse> resultRH = session.createQuery("from RuralHouse").list();
		Vector<RuralHouse> v = new Vector(resultRH);
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
		session.getTransaction().commit();
		return v;
	}

	public static void main(String[] args) {
		HibernateDataAccess hba = new HibernateDataAccess();
		hba.initializeDB();
	}
}
