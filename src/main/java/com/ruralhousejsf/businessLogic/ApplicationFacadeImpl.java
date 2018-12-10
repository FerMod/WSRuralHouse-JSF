package com.ruralhousejsf.businessLogic;

import java.util.Date;
import java.util.Vector;

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
	public RuralHouse createRuralHouse(String description, String city) {
		return dataAccess.createRuralHouse(description, city);
	}

	@Override
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, double price) {
		return dataAccess.createOffer(ruralHouse, firstDay, lastDay, price);
	}

	@Override
	public Client createClient(String user, String pass) {
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

}
