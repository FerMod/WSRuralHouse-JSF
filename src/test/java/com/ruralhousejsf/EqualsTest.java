package com.ruralhousejsf;

import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;

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
import com.ruralhousejsf.extension.DataBaseConnectionExtension;
import com.ruralhousejsf.extension.TimingExtension;

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

	@Nested
	@DisplayName("Client Equals Test")
	class ClientEqualsTest implements EqualsContract<Client> {

		@Override
		public Client createValue() {	
			return createTestClient("TestClient", "TestClient123");
		}

		@Override
		public Client createNotEqualValue() {
			return createTestClient("TestDifferentClient", "TestClient123");
		}

		private Client createTestClient(String username, String password) {

			Client client = null;

			try {
				client = afi.createClient(username, password);
			} catch (Exception e) {
				assumeNoException("Exception raised when creating the test data.", e);
			}

			assumeNotNull(client);
			clientList.add(client);

			return client;
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
			ruralHouseList.add(ruralHouse);

			return ruralHouse;
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
				ruralHouseList.add(ruralHouse);

				offer = afi.createOffer(ruralHouse, offerStartDate, offerEndDate, offerPrice);
			} catch (Exception e) {
				assumeNoException("Exception raised when creating the test data.", e);
			}

			assumeNotNull(offer);
			offerList.add(offer);

			return offer;
		}

	}

}
