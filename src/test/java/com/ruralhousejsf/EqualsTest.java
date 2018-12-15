package com.ruralhousejsf;

import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;

import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import com.ruralhousejsf.businessLogic.AppFacade;
import com.ruralhousejsf.businessLogic.ApplicationFacadeInterface;
import com.ruralhousejsf.contract.EqualsContract;
import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;

class EqualsTest {

	static ApplicationFacadeInterface afi;

//	@BeforeAll
//	static void beforeAll() {
//
//		try {
//			afi = AppFacade.getImpl(true);
//		} catch (Exception e) {
//			assumeNoException("Exception raised when creating the test data.", e);
//		}		
//
//	}

	@BeforeEach
	void beforeEach() {

		try {
			afi = AppFacade.getImpl(true);
		} catch (Exception e) {
			assumeNoException("Exception raised when creating the test data.", e);
		}		

	}

	@Nested
	@DisplayName("Client Equals Test")
	class ClientEqualsTest implements EqualsContract<Client> {

		@Override
		public Client createValue() {		
			return createTestClient("TestClient", "Client123");
		}

		@Override
		public Client createNotEqualValue() {		
			return createTestClient("TestDifferentClient", "Client123");
		}

		private Client createTestClient(String username, String password) {

			Client client = null;

			try {
				client = afi.createClient(username, password);
			} catch (Exception e) {
				assumeNoException("Exception raised when creating the test data.", e);
			}

			assumeNotNull(client);

			return client;
		}

	}

	@Nested
	@DisplayName("Offer Equals Test")
	class OfferEqualsTest implements EqualsContract<Offer> {

		@Override
		public Offer createValue() {		
			return createTestOffer("Test RuralHouse", "Test City", LocalDate.of(2018, Month.SEPTEMBER, 12), LocalDate.of(2018, Month.SEPTEMBER, 13), 200);
		}

		@Override
		public Offer createNotEqualValue() {		
			return createTestOffer("Test different RuralHouse", "Test City", LocalDate.of(2018, Month.DECEMBER, 12), LocalDate.of(2019, Month.SEPTEMBER, 13), 200);
		}

		private Offer createTestOffer(String rhDescription, String rhCity, LocalDate offerStartDate, LocalDate offerEndDate, double offerPrice) {
			
			RuralHouse ruralHouse = null;
			Offer offer = null;
			
			try {
				ruralHouse = afi.createRuralHouse(rhDescription, rhCity);
				assumeNotNull(ruralHouse);
				offer = afi.createOffer(ruralHouse, offerStartDate, offerEndDate, offerPrice);
			} catch (Exception e) {
				assumeNoException("Exception raised when creating the test data.", e);
			}

			assumeNotNull(offer);

			return offer;
		}


	}


	@Nested
	@DisplayName("RuralHouse Equals Test")
	class RuralHouseEqualsTest implements EqualsContract<RuralHouse> {

		@Override
		public RuralHouse createValue() {		
			return createTestRuralHouse("Test RuralHouse", "Test City");
		}

		@Override
		public RuralHouse createNotEqualValue() {		
			return createTestRuralHouse("Test different RuralHouse", "Test City");
		}

		private RuralHouse createTestRuralHouse(String description, String city) {
			
			RuralHouse ruralHouse = null;
			
			try {
				ruralHouse = afi.createRuralHouse(description, city);
			} catch (Exception e) {
				assumeNoException("Exception raised when creating the test data.", e);
			}

			assumeNotNull(ruralHouse);

			return ruralHouse;
		}


	}

}
