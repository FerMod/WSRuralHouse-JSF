package com.ruralhousejsf.businessLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.ruralhousejsf.dataAccess.HibernateDataAccess;
import com.ruralhousejsf.dataAccess.HibernateDataAccessInterface;
import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;

public class ApplicationFacadeImpl implements ApplicationFacadeInterface {
	
	private HibernateDataAccessInterface dataAccess;
	
	public ApplicationFacadeImpl() {
	}
	
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
		RuralHouse rh = dataAccess.createRuralHouse(description, city);
		System.out.println(">> ApplicationFacadeImpl: createRuralHouse() " + rh.toString());
		return rh;
	}

	@Override
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, double price) {
		Offer o = dataAccess.createOffer(ruralHouse, firstDay, lastDay, price);
		System.out.println(">> ApplicationFacadeImpl: createOffer() " + o.toString());
		return o;
	}

	@Override
	public Client createClient(String user, String pass) {
		System.out.println(">> ApplicationFacadeImpl: createClient() with user: " + user + " and pass: " + pass + ".");
		return dataAccess.createClient(user, pass);
	}

	@Override
	public Vector<RuralHouse> getAllRuralHouses() {
		System.out.println(">> ApplicationFacadeImpl: getAllRuralHouses()");
		return dataAccess.getAllRuralHouses();
	}

	@Override
	public Vector<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay) {
		System.out.println(">> ApplicationFacadeImpl: getOffers() of " + rh.toString() + " in startDate: " + firstDay.toString() + " and in finalDate: " + lastDay.toString() + ".");
		return dataAccess.getOffers(rh, firstDay, lastDay);
	}

	@Override
	public boolean login(String user, String pass) {
		System.out.println(">> ApplicationFacadeImpl: login() with username=" + user + " and password=" + pass + ".");
		Vector<Client> clients = dataAccess.getClient(user, pass);
		return clients.size() == 1;
	}
	
	public static void main(String[] args) {
		ApplicationFacadeInterface afi = new ApplicationFacadeFactory().createApplicationFacade();
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
