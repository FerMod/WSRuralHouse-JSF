package com.ruralhousejsf;

import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.ruralhousejsf.extension.DataBaseConnectionExtension;
import com.ruralhousejsf.extension.TimingExtension;
import com.ruralhousejsf.logger.ConsoleLogger;

/**
 * This class defines test to guarantee a correct and an expected result of
 * the methods and classes that access the database.
 * <p>
 * Before any test is run, the connection with the database is checked and if
 * this check fails all the tests are aborted.
 * 
 */
@DisplayName("Hibernate Data Access Test")
@ExtendWith({DataBaseConnectionExtension.class, TimingExtension.class})
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

	@DisplayName("Client Test")
	@Nested
	class ClientTest {

		@DisplayName("CreateClient - Correct Execution")
		@ParameterizedTest
		@CsvFileSource(resources = CLIENT_TEST_DATA, numLinesToSkip = 1)
		void createClientTest(String username, String password) {

			try {
				Client client = createTestClient(username, password);
				assertTrue(afi.exists(Client.class, client.getId()), () -> "Persistent instance not found in the database");
			} catch (Exception e) {
				fail("Exception thrown when trying to create an offer", e);
			}

		}

		@DisplayName("GetClient - Correct Execution")
		@ParameterizedTest
		@CsvFileSource(resources = CLIENT_TEST_DATA, numLinesToSkip = 1)
		void getClientTest(String username, String password) {

			try {

				Client client = createTestClient(username, password);
				assertTrue(afi.exists(Client.class, client.getId()), () -> "Persistent instance not found in the database");

				Optional<Client> optClient = afi.getClient(username, password);
				assertTrue(optClient.isPresent(), () -> "No client returned");
				assertTrue(optClient.get().equals(client), () -> "The obtained client does not match");
				LOGGER.debug("Obtained cliend hash: " + optClient.get().hashCode() + "Created client hash: " + client.hashCode());


			} catch (Exception e) {
				fail("Exception thrown when trying to create an offer", e);
			}

		}

		@DisplayName("Login - True")
		@ParameterizedTest
		@CsvFileSource(resources = CLIENT_TEST_DATA, numLinesToSkip = 1)
		void loginTest1(String username, String password) {

			try {

				Client client = createTestClient(username, password);	
				assertTrue(afi.exists(Client.class, client.getId()), () -> "Persistent instance not found in the database");

				assertTrue(afi.login(username, password), () -> "Could not login an existing client");

			} catch (Exception e) {
				fail("Exception thrown when trying to create an offer", e);
			}

		}

		@DisplayName("Login - False")
		@ParameterizedTest
		@CsvFileSource(resources = CLIENT_TEST_DATA, numLinesToSkip = 1)
		void loginTest2(String username, String password) {

			try {

				Client client = createTestClient(username, password);	
				assertTrue(afi.exists(Client.class, client.getId()), () -> "Persistent instance not found in the database");

				assertFalse(afi.login(username, password + "wrong"), () -> "Unexpected client login occurred");

			} catch (Exception e) {
				fail("Exception thrown when trying to create an offer", e);
			}

		}

	}

	@DisplayName("Offer Test")
	@Nested
	class OfferTest {

		@DisplayName("CreateOffer - BadDatesException")
		@ParameterizedTest
		@CsvFileSource(resources = OFFER_BAD_DATES_DATA, numLinesToSkip = 1)
		void createOfferTest1(String description, String city, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate startDate, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate endDate, double price) {

			try {

				RuralHouse ruralHouse = afi.createRuralHouse(description, city);
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

		@DisplayName("CreateOffer - Correct Execution")
		@ParameterizedTest
		@CsvFileSource(resources = OFFER_TEST_DATA, numLinesToSkip = 1)
		void createOfferTest2(String description, String city, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate startDate, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate endDate, double price) {

			try {

				RuralHouse ruralHouse = createTestRuralHouse(description, city);
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Assumption of persistent instance stored in the database not true");

				Offer offer = createTestOffer(ruralHouse, startDate, endDate, price);
				assertTrue(afi.exists(Offer.class, offer.getId()), () -> "Persistent instance not found in the database");

			} catch (Exception e) {
				fail("Exception thrown when trying to create an offer", e);
			}

		}

		@DisplayName("GetOffers - BadDatesException")
		@ParameterizedTest
		@CsvFileSource(resources = OFFER_BAD_DATES_DATA, numLinesToSkip = 1)
		void getOffersTest1(String description, String city, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate startDate, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate endDate, double price) {

			try {

				RuralHouse ruralHouse = afi.createRuralHouse(description, city);
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Persistent instance not found in the database");

				assertThrows(BadDatesException.class, () -> {
					afi.getOffers(ruralHouse, startDate, endDate);
				});

			} catch (Exception e) {
				fail("Expected " + BadDatesException.class.getCanonicalName() + " but got " + e.getClass().getCanonicalName(), e);
			}

		}

		@DisplayName("GetOffers - Empty")
		@ParameterizedTest
		@CsvFileSource(resources = OFFER_TEST_DATA, numLinesToSkip = 1)
		void getOffersTest3(String description, String city, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate startDate, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate endDate, double price) {

			try {

				RuralHouse ruralHouse = createTestRuralHouse(description, city);
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Persistent instance not found in the database");

				List<Offer> offers = afi.getOffers(ruralHouse, startDate, endDate);
				assertTrue(offers.isEmpty(), () -> "Offers obtained");

			} catch (Exception e) {
				fail("Unexpected exception " + e.getClass().getCanonicalName() + " thrown", e);
			}

		}

		@DisplayName("GetOffers - Not Empty")
		@ParameterizedTest
		@CsvFileSource(resources = OFFER_TEST_DATA, numLinesToSkip = 1)
		void getOffersTest4(String description, String city, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate startDate, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate endDate, double price) {

			try {

				RuralHouse ruralHouse = createTestRuralHouse(description, city);
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Persistent instance not found in the database");

				int initialSize = afi.getOffers(ruralHouse, startDate, endDate).size();

				createTestOffer(ruralHouse, startDate, endDate, price);

				List<Offer> offers = afi.getOffers(ruralHouse, startDate, endDate);
				assertEquals(initialSize + offerList.size(), offers.size(), () -> "No offers obtained");

			} catch (Exception e) {
				fail("Unexpected exception " + e.getClass().getCanonicalName() + " thrown", e);
			}

		}

	}

	@DisplayName("RuralHouse Test")
	@Nested
	class RuralHouseTest {

		@ParameterizedTest
		@CsvFileSource(resources = RURALHOUSE_TEST_DATA, numLinesToSkip = 1)
		@DisplayName("CreateRuralHouse - Correct Execution")
		void createRuralHouseTest(String description, String city) {

			try {
				RuralHouse ruralHouse =	createTestRuralHouse(description, city);
				assertTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Persistent instance not found in the database");
			} catch (Exception e) {
				fail("Exception thrown when trying to create a rural house", e);
			}

		}

		@DisplayName("GetAllRuralHouses - List Not Empty")
		@Test
		void getAllRuralHousesTest() {

			try {

				int initialSize = afi.getAllRuralHouses().size();

				RuralHouse ruralHouse =	createTestRuralHouse("Test1", "City1");
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Persistent instance not found in the database");

				RuralHouse ruralHouse2 = createTestRuralHouse("Test2", "City2");
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse2.getId()), () -> "Persistent instance not found in the database");

				List<RuralHouse> ruralHouseListResult = afi.getAllRuralHouses();
				assertNotNull(ruralHouseListResult, () -> "The list is null");
				assertFalse(ruralHouseListResult.isEmpty(), () -> "The list is empty");
				assertEquals(initialSize + ruralHouseList.size(), ruralHouseListResult.size(), () -> "The returned number of instances does not match the expected");

			} catch (Exception e) {
				fail("Exception thrown when trying to obtain all rural houses.", e);
			}

		}

		@DisplayName("GetOffers - Correct Execution")
		@ParameterizedTest
		@CsvFileSource(resources = OFFER_TEST_DATA, numLinesToSkip = 1)
		void getOffersTest(String description, String city, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate startDate, @JavaTimeConversionPattern("dd/MM/yyyy") LocalDate endDate, double price) {

			try {

				RuralHouse ruralHouse = createTestRuralHouse(description, city);
				assumeTrue(afi.exists(RuralHouse.class, ruralHouse.getId()), () -> "Assumption of persistent instance stored in the database not true");

				Offer offer = createTestOffer(ruralHouse, startDate, endDate, price);
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
