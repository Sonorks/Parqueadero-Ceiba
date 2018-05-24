package co.ceibauniversity.parkinglot.controllers.integration;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceibauniversity.parkinglot.databuilder.VehicleTestDataBuilder;
import co.ceibauniversity.parkinglot.controllers.WatchmanController;
import co.ceibauniversity.parkinglot.dao.TicketDAO;
import co.ceibauniversity.parkinglot.model.Vehicle;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WatchmanControllerRemoveTest {
	
	private TestRestTemplate restTemplate = new TestRestTemplate();
	
	@LocalServerPort
    int randomServerPort;
	
	@Autowired
	WatchmanController watchmanController;
	
	@Autowired
	TicketDAO ticketDAO;

	@Before
	public void addVehicleDatabase() {
		VehicleTestDataBuilder vehicleTestDataBuilder = new VehicleTestDataBuilder().usingPlate("REMOVETHIS");
		Vehicle vehicle = vehicleTestDataBuilder.build();
		watchmanController.addVehicle(vehicle);
	}
	
	@After
	public void removeVehiclesDatabase() {
		ticketDAO.deleteVehicle("REMOVETHIS");
	}
	
	@Test
	public void removeVehicleTest() {
		ResponseEntity<Object> responseEntity = 
				restTemplate.postForEntity("http://localhost:"+randomServerPort+"/parking/removeVehicle/REMOVETHIS", null, null);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}


}
