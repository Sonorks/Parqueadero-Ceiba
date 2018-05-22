package co.ceibauniversity.parkinglot.controllers.integration;

import static org.junit.Assert.*;

import org.junit.After;
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
import co.ceibauniversity.parkinglot.dao.TicketDAO;
import co.ceibauniversity.parkinglot.model.Vehicle;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WatchmanControllerAddTest {

	private TestRestTemplate restTemplate = new TestRestTemplate();
	
	@LocalServerPort
    int randomServerPort;
	
	@Autowired
	TicketDAO ticketDAO;
	
	@After
	public void removeVehiclesDatabase() {
		ticketDAO.deleteVehicle("PRUEBA");
	}
	
	@Test
	public void addVehicleTest() {
		VehicleTestDataBuilder vehicleTestDataBuilder = new VehicleTestDataBuilder().usingPlate("PRUEBA");
		Vehicle vehicle = vehicleTestDataBuilder.build();	
		ResponseEntity<Boolean> responseEntity = 
				restTemplate.postForEntity("http://localhost:"+randomServerPort+"/parking/addVehicle",vehicle, Boolean.class);
		Boolean respuesta = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(true,respuesta);
	}
	
}

