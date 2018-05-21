package co.ceibaUniversity.Parqueadero.controllers.integration;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceibaUniversity.Parqueadero.dao.TicketDAO;
import co.ceibaUniversity.Parqueadero.dataBuilder.VehicleTestDataBuilder;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WatchmanControllerAddTest {

	private TestRestTemplate restTemplate = new TestRestTemplate();
	
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
				restTemplate.postForEntity("http://localhost:8090/parking/addVehicle", vehicle, Boolean.class);
		boolean respuesta = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(true,respuesta);
	}
	
}

