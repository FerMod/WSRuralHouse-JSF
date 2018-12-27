package com.ruralhousejsf;

import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.ruralhousejsf.businessLogic.AppFacade;
import com.ruralhousejsf.businessLogic.ApplicationFacadeInterface;
import com.ruralhousejsf.domain.Client;
import com.ruralhousejsf.domain.Offer;
import com.ruralhousejsf.domain.RuralHouse;
import com.ruralhousejsf.exceptions.BadDatesException;
import com.ruralhousejsf.extension.TimingExtension;
import com.ruralhousejsf.logger.ConsoleLogger;

@DisplayName("Hibernate Data Access Test")
@ExtendWith(TimingExtension.class)
class HibernateDataAccessTest {

	static final Logger LOGGER = ConsoleLogger.createLogger(HibernateDataAccessTest.class);
	
	final static String CLIENT_TEST_DATA = "/ClientTestData.csv";
	final static String OFFER_TEST_DATA = "/OfferTestData.csv";
	final static String OFFER_BAD_DATES_DATA = "/OfferBadDates.csv";
	final static String RURALHOUSE_TEST_DATA = "/RuralHouseTestData.csv";

	static ApplicationFacadeInterface afi;

	static List<Client> clientList;
	static List<Offer> offerList;
	static List<RuralHouse> ruralHouseList;

	@BeforeAll
	static void beforeAll() {

		try {
			afi = AppFacade.getImpl();
		} catch (Exception e) {
			assumeNoException("Exception raised when obtaining the application facade implementation instance", e);
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

	private Client createTestClient(String username, String password) {

		Client client = null;

		try {
			client = afi.createClient(username, password);
		} catch (Exception e) {
			assumeNoException("Exception raised when creating the Client test data", e);
		}

		return client;
	}

	private RuralHouse createTestRuralHouse(String description, String city) {

		RuralHouse ruralHouse = null;

		try {
			ruralHouse = afi.createRuralHouse(description, city);
		} catch (Exception e) {
			assumeNoException("Exception raised when creating the RuralHouse test data", e);
		}

		return ruralHouse;
	}

	private Offer createTestOffer(RuralHouse ruralHouse, LocalDate offerStartDate, LocalDate offerEndDate, double offerPrice) {

		Offer offer = null;

		try {
			offer = afi.createOffer(ruralHouse, offerStartDate, offerEndDate, offerPrice);
		} catch (Exception e) {
			assumeNoException("Exception raised when creating the Offer test data", e);
		}

		return offer;
	}

	@Nested
	@DisplayName("Client Test")
	class ClientTest {

		@ParameterizedTest
		@CsvFileSource(resources = CLIENT_TEST_DATA, numLinesToSkip = 1)
		@DisplayName("CreateClient - Correct Execution")
		void createClientTest(String username, String password) {

			try {
				Client client = createTestClient(username, password);			
				assumeNotNull(client);
				clientList.add(client);
				assertTrue(afi.exists(Client.class, client.getId()), () -> "Persistent instance not found in the database");
			} catch (Exception e) {
				fail("Exception thrown when trying to create an offer", e);
			}

		}
		
		@ParameterizedTest
		@CsvFileSource(resources = CLIENT_TEST_DATA, numLinesToSkip = 1)
		@DisplayName("GetClient - Correct Execution")
		void getClientTest(String username, String password) {
		
			try {
			
				Client client = createTestClient(username, password);			
				assumeNotNull(client);
				clientList.add(client);
				assertTrue(afi.exists(Client.class, client.getId()), () -> "Persistent instance not found in the database");
				
				Optional<Client> optClient = afi.getClient(username, password);
				assertTrue(optClient.isPresent(), () -> "No client returned");
				assertTrue(optClient.get().equals(client), () -> "The obtained client does not match");
				LOGGER.debug("Obtained cliend hash: " + optClient.get().hashCode() + "Created client hash: " + client.hashCode());
				
				
			} catch (Exception e) {
				fail("Exception thrown when trying to create an offer", e);
			}
		
		}

		@ParameterizedTest
		@CsvFileSource(resources = CLIENT_TEST_DATA, numLinesToSkip = 1)
		@DisplayName("Login - Correct Execution")
		void loginTest(String username, String password) {

			try {
			
				Client client = createTestClient(username, password);			
				assumeNotNull(client);
				clientList.add(client);
				assertTrue(afi.exists(Client.class, client.getId()), () -> "Persistent instance not found in the database");
				
				assertTrue(afi.login(username, password), () -> "Could not login an existing client");
				assertFalse(afi.login(username, password + "wrong"), () -> "Unexpected client login occurred");
			
			} catch (Exception e) {
				fail("Exception thrown when trying to create an offer", e);
			}

		}

	}

	@Nested
	@DisplayName("Offer Test")
	class OfferTest {

		@ParameterizedTest
		@CsvFileSource(resources = OFFER_BAD_DATES_DATA, numLinesToSkip = 1)
		@DisplayName("CreateOffer - BadDatesException")
		void createOfferTest1(String description, String city, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate startDate, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate endDate, double price) {

			try {

				RuralHouse ruralHouse = afi.createRuralHouse(description, city);
				assumeNotNull(ruralHouse);
				ruralHouseList.add(ruralHouse);
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Persistent instance not found in the database");

				assertThrows(BadDatesException.class, () -> {
					Optional<Offer> offer = Optional.ofNullable(afi.createOffer(ruralHouse, startDate, endDate, price));
					if(offer.isPresent()) {
						offerList.add(offer.get());
					}
				});

			} catch (Exception e) {
				fail("Expected " + BadDatesException.class.getCanonicalName() + " but got " + e.getClass().getCanonicalName(), e);
			}

		}

		@ParameterizedTest
		@CsvFileSource(resources = OFFER_TEST_DATA, numLinesToSkip = 1)
		@DisplayName("CreateOffer - Correct Execution")
		void createOfferTest2(String description, String city, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate startDate, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate endDate, double price) {

			try {

				RuralHouse ruralHouse = createTestRuralHouse(description, city);
				assumeNotNull(ruralHouse);
				ruralHouseList.add(ruralHouse);
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Assumption of persistent instance stored in the database not true");

				Offer offer = createTestOffer(ruralHouse, startDate, endDate, price);
				assumeNotNull(offer);
				offerList.add(offer);
				assertTrue(afi.exists(Offer.class, offer.getId()), () -> "Persistent instance not found in the database");

			} catch (Exception e) {
				fail("Exception thrown when trying to create an offer", e);
			}

		}

		@ParameterizedTest
		@CsvFileSource(resources = OFFER_BAD_DATES_DATA, numLinesToSkip = 1)
		@DisplayName("GetOffers - BadDatesException")
		void getOffersTest1(String description, String city, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate startDate, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate endDate, double price) {

			try {

				RuralHouse ruralHouse = afi.createRuralHouse(description, city);
				assumeNotNull(ruralHouse);
				ruralHouseList.add(ruralHouse);
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Persistent instance not found in the database");

				assertThrows(BadDatesException.class, () -> {
					afi.getOffers(ruralHouse, startDate, endDate);
				});

			} catch (Exception e) {
				fail("Expected " + BadDatesException.class.getCanonicalName() + " but got " + e.getClass().getCanonicalName(), e);
			}

		}

		@ParameterizedTest
		@CsvFileSource(resources = OFFER_TEST_DATA, numLinesToSkip = 1)
		@DisplayName("GetOffers - Correct Execution")
		void getOffersTest2(String description, String city, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate startDate, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate endDate, double price) {

			try {

				RuralHouse ruralHouse = createTestRuralHouse(description, city);
				assumeNotNull(ruralHouse);
				ruralHouseList.add(ruralHouse);
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Persistent instance not found in the database");

				List<Offer> offers = afi.getOffers(ruralHouse, startDate, endDate);
				assertFalse(offers.isEmpty(), () -> "No offers obtained");

			} catch (Exception e) {
				fail("Unexpected exception " + e.getClass().getCanonicalName() + " thrown", e);
			}

		}

	}

	@Nested
	@DisplayName("RuralHouse Test")
	class RuralHouseTest {

		@ParameterizedTest
		@CsvFileSource(resources = RURALHOUSE_TEST_DATA, numLinesToSkip = 1)
		@DisplayName("CreateRuralHouse - Correct Execution")
		void createRuralHouseTest(String description, String city) {

			try {
				RuralHouse ruralHouse =	createTestRuralHouse(description, city);
				assumeNotNull(ruralHouse);
				ruralHouseList.add(ruralHouse);
				assertTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Persistent instance not found in the database");
			} catch (Exception e) {
				fail("Exception thrown when trying to create a rural house", e);
			}

		}

		@Test
		@DisplayName("GetAllRuralHouses - Correct Execution")
		void getAllRuralHousesTest() {

			try {

				List<RuralHouse> ruralHouseListResult = afi.getAllRuralHouses();
				assertNotNull(ruralHouseListResult, () -> "The list is null");
				assertFalse(ruralHouseListResult.isEmpty(), () -> "The list is empty");

			} catch (Exception e) {
				fail("Exception thrown when trying to obtain all rural houses.", e);
			}

		}

		@ParameterizedTest
		@CsvFileSource(resources = OFFER_TEST_DATA, numLinesToSkip = 1)
		@DisplayName("GetOffers - Correct Execution")
		void getOffersTest(String description, String city, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate startDate, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate endDate, double price) {

			try {

				RuralHouse ruralHouse = createTestRuralHouse(description, city);
				assumeNotNull(ruralHouse);
				ruralHouseList.add(ruralHouse);
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Assumption of persistent instance stored in the database not true");

				Offer offer = createTestOffer(ruralHouse, startDate, endDate, price);
				assumeNotNull(offer);
				offerList.add(offer);
				assumeTrue(afi.exists(Offer.class, offer.getId()), () -> "Persistent instance not found in the database");


				Optional<RuralHouse> rh = afi.get(RuralHouse.class, ruralHouse.getId());

				Set<Offer> offersSetResult = rh.isPresent() ? offersSetResult = rh.get().getOffers() : null;

				assertNotNull(offersSetResult, () -> "The set is null");
				assertFalse(offersSetResult.isEmpty(), () -> "The set is empty");

			} catch (Exception e) {
				fail("Exception thrown when trying to create an offer", e);
			}

		}

	}

}
