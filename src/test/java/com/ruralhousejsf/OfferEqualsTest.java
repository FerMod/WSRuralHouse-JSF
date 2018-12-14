package com.ruralhousejsf;

import static org.junit.Assume.assumeNoException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.ruralhousejsf.businessLogic.AppFacade;
import com.ruralhousejsf.businessLogic.ApplicationFacadeInterface;
import com.ruralhousejsf.contract.EqualsContract;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.util.TestUtilities;

public class OfferEqualsTest implements EqualsContract<Offer> {

	static ApplicationFacadeInterface afi;

	@BeforeAll
	static void beforeAll() {
		
		try {

		} catch (Exception e) {
			assumeNoException("Exception raised when creating the test data.", e);
		}	

		afi = AppFacade.getImpl();
		
	}

	static void createTestData() {

	}

	@BeforeEach
	void beforeEach() {

		startDate = null;
		endDate = null;
		offer1 = null;
		offer2 = null;
		booking = null;
		price = 550.0;

	}

	@AfterAll
	static void afterAll() {
		if (rh != null) {
			afi.remove(rh);
		}
		if (admin != null) {
			afi.remove(admin);
		}
		if (particularClient != null) {
			afi.remove(particularClient);
		}
		if (travelAgency != null) {
			afi.remove(travelAgency);
		}
		if (owner != null) {
			afi.remove(owner);
		}
		if (city != null) {
			afi.remove(city);
		}
	}

	@AfterEach
	void afterEach() {
		if(offer1 != null) {
			afi.remove(offer1);
		}
		if(offer2 != null) {
			afi.remove(offer2);
		}
	}

	@Override
	public Offer createValue() {
		Date firstDay = TestUtilities.parseToDate(LocalDate.of(2018, Month.SEPTEMBER, 12));
		Date lastDay = TestUtilities.parseToDate(LocalDate.of(2018, Month.SEPTEMBER, 13));
		offer1 = TestUtilities.createTestOffer(rh, firstDay, lastDay, price);
		return offer1;
	}

	@Override
	public Offer createNotEqualValue() {
		Date firstDay = TestUtilities.parseToDate(LocalDate.of(2018, Month.SEPTEMBER, 13));
		Date lastDay = TestUtilities.parseToDate(LocalDate.of(2030, Month.DECEMBER, 07));
		offer2 = TestUtilities.createTestOffer(rh, firstDay, lastDay, price);
		return offer2;
	}

}
