package com.ruralhousejsf;

import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;

import com.ruralhousejsf.businessLogic.AppFacade;
import com.ruralhousejsf.businessLogic.ApplicationFacadeInterface;
import com.ruralhousejsf.contract.EqualsContract;
import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.exceptions.BadDatesException;
import com.ruralhousejsf.extension.DataBaseConnectionExtension;
import com.ruralhousejsf.extension.TimingExtension;

/**
 * This class defines test of equality that the domain objects should pass.
 * <p>
 * Before any test is run, the connection with the database is checked and if
 * this check fails all the tests are aborted.
 * 
 */
@DisplayName("Equals Test")
@ExtendWith({DataBaseConnectionExtension.class, TimingExtension.class})
class EqualsTest {

	static ApplicationFacadeInterface afi;

	static List<Client> clientList;
	static List<Offer> offerList;
	static List<RuralHouse> ruralHouseList;

	@BeforeAll
	static void beforeAll() {

		try {
			afi = AppFacade.getImpl();
		} catch (Exception e) {
			assumeNoException("Exception raised when creating the test data.", e);
		}		

		clientList = new ArrayList<>();
		offerList = new ArrayList<>();
		ruralHouseList = new ArrayList<>();

	}

	@AfterAll
	static void afterAll() {
		removeTestData();
	}

	@BeforeEach
	void beforeEach() {
		removeTestData();
	}

	/**
	 * Remove any test data added in the database.<br>
	 * This method should be called at least once in the annotated method with
	 * "{@code @afterAll}".
	 */
	private static void removeTestData() {

		if(!clientList.isEmpty()) {
			clientList.forEach(c -> afi.delete(Client.class, c.getId()));
			clientList.clear();
		}

		if(!offerList.isEmpty()) {
			offerList.forEach(c -> {
				Optional<RuralHouse> rh = afi.get(RuralHouse.class, c.getRuralHouse().getId());
				if(rh.isPresent()) {
					rh.get().removeOffer(c.getId());
				} else {
					afi.delete(Offer.class, c.getId());
				}	
			});
			offerList.clear();
		}

		if(!ruralHouseList.isEmpty()) {
			ruralHouseList.forEach(c -> afi.delete(RuralHouse.class, c.getId()));
			ruralHouseList.clear();
		}	

	}
	
	/**
	 * Creates and stores a client to be used in the tests.<br>
	 * This method assumes that no {@code null} value will not be obtained when
	 * creating the client, in that extent, this method will not return a 
	 * {@code null} value.
	 * <p>
	 * This value is added to a list for the later removal and to avoid to 
	 * fill the database with invalid data.
	 * 
	 * @param username the client user name
	 * @param password the client password
	 * 
	 * @return the created {@code Client}
	 * 
	 * @see Client
	 */
	private Client createTestClient(String username, String password) {

		Client client = afi.createClient(username, password);
		assumeNotNull(client, "Assumption of a non null value not true");
		clientList.add(client);

		return client;
	}

	/**
	 * Creates and stores an offer to be used in the tests.<br>
	 * This method assumes that no {@code null} value will not be obtained when
	 * creating the client, in that extent, this method will not return a 
	 * {@code null} value.
	 * <p>
	 * This value is added to a list for the later removal and to avoid to 
	 * fill the database with invalid data.
	 * 
	 * @param ruralHouse this offers associated rural house  
	 * @param startDate the offer start date
	 * @param endDate the offer end date
	 * @param price the cost per day of the offer
	 * 
	 * @return the created {@code Offer} for the {@code RuralHouse}
	 * 
	 * @throws BadDatesException thrown when the start date is greater than the end date
	 * 
	 * @see Offer
	 */
	private Offer createTestOffer(RuralHouse ruralHouse, LocalDate offerStartDate, LocalDate offerEndDate, double offerPrice) throws BadDatesException {
	
		Offer offer = afi.createOffer(ruralHouse, offerStartDate, offerEndDate, offerPrice);
		assumeNotNull(offer, "Assumption of a non null value not true");
		offerList.add(offer);
		
		return offer;
	}

	/**
	 * Creates and stores a rural house to be used in the tests.<br>
	 * This method assumes that no {@code null} value will not be obtained when
	 * creating the client, in that extent, this method will not return a 
	 * {@code null} value.
	 * <p>
	 * This value is added to a list for the later removal and to avoid to 
	 * fill the database with invalid data.
	 * 
	 * @param description the rural house description
	 * @param city the rural house city
	 * 
	 * @return the created {@code RuralHouse}
	 * 
	 * @see RuralHouse
	 */
	private RuralHouse createTestRuralHouse(String description, String city) {

		RuralHouse ruralHouse = afi.createRuralHouse(description, city);
		assumeNotNull(ruralHouse, "Assumption of a non null value not true");
		ruralHouseList.add(ruralHouse);

		return ruralHouse;
	}

	@Nested
	@DisplayName("Client Equals Test")
	class ClientEqualsTest implements EqualsContract<Client> {

		@Override
		public Client createValue() {	
			
			Client client = null;

			try {
				client = createTestClient("TestClient", "TestClient123");
			} catch (Exception e) {
				fail("Exception raised when creating the test data.", e);
			}
			
			return client;
		}

		@Override
		public Client createNotEqualValue() {
			
			Client client = null;

			try {
				client = createTestClient("TestDifferentClient", "TestClient123");
			} catch (Exception e) {
				fail("Exception raised when creating the test data.", e);
			}
			
			return client;
		}

	}

	@Nested
	@DisplayName("RuralHouse Equals Test")
	class RuralHouseEqualsTest implements EqualsContract<RuralHouse> {

		@Override
		public RuralHouse createValue() {	
			RuralHouse ruralHouse = null;

			try {
				ruralHouse = createTestRuralHouse("Test RuralHouse", "Test City");
			} catch (Exception e) {
				fail("Exception raised when creating the test data.", e);
			}
			
			return ruralHouse;
		}

		@Override
		public RuralHouse createNotEqualValue() {	
			
			RuralHouse ruralHouse = null;

			try {
				ruralHouse = createTestRuralHouse("Test different RuralHouse", "Test City");
			} catch (Exception e) {
				fail("Exception raised when creating the test data.", e);
			}
			
			return ruralHouse;
		}
		
	}

	@Nested
	@DisplayName("Offer Equals Test")
	class OfferEqualsTest implements EqualsContract<Offer> {

		@Override
		public Offer createValue() {
			
			RuralHouse ruralHouse = null;
			Offer offer = null;
			
			try {
				ruralHouse = createTestRuralHouse("Test RuralHouse", "Test City");
				offer = createTestOffer(ruralHouse, LocalDate.of(2018, Month.SEPTEMBER, 12), LocalDate.of(2018, Month.SEPTEMBER, 13), 200);
			} catch (Exception e) {
				fail("Exception raised when creating the test data.", e);
			}
			
			return offer;
		}

		@Override
		public Offer createNotEqualValue() {
			
			RuralHouse ruralHouse = null;
			Offer offer = null;
			
			try {
				ruralHouse = createTestRuralHouse("Test different RuralHouse", "Test City");
				offer = createTestOffer(ruralHouse, LocalDate.of(2018, Month.DECEMBER, 12), LocalDate.of(2019, Month.SEPTEMBER, 13), 200);
			} catch (Exception e) {
				fail("Exception raised when creating the test data.", e);
			}
			
			return offer;
		}

	}

}
