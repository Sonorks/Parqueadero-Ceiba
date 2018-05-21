package co.ceibaUniversity.Parqueadero.controllers.integration;

import static org.junit.Assert.*;

import java.util.Date;

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

import co.ceibaUniversity.Parqueadero.dao.TicketDAO;
import co.ceibaUniversity.Parqueadero.dataBuilder.VehicleTestDataBuilder;
import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WatchmanControllerGetAllTest {

private TestRestTemplate restTemplate = new TestRestTemplate();
	
	@LocalServerPort
    int randomServerPort;
	
	@Autowired
	TicketDAO ticketDAO;
	
	@Before
	public void addVehicleToDB() {
		VehicleTestDataBuilder vehicleTestDataBuilder = new VehicleTestDataBuilder().usingPlate("PRUEBAGETALL");
		Vehicle vehicle = vehicleTestDataBuilder.build();
		Ticket ticket = new Ticket(vehicle.getType(),vehicle.getPlate(),vehicle.getCc(), new Date());
		ticket.setExitDate(new Date());
		ticket.setTotalHours(1);
		ticketDAO.addTicket(ticket);
	}
	
	@After
	public void removeVehicleFromDB() {
		ticketDAO.deleteVehicle("PRUEBAGETALL");
	}
	
	@Test
	public void getVehicleByPlateTest() {
		ResponseEntity<Ticket> responseEntity = 
				restTemplate.getForEntity("http://localhost:"+randomServerPort+"/parking/tickets/", Ticket.class);
		Ticket respuesta = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("PRUEBAGET",respuesta.getPlate());
	}

}
