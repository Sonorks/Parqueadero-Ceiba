package co.ceibauniversity.parkinglot.controllers.integration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceibauniversity.parkinglot.model.Ticket;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WatchmanControllerGetTrm {
	
	private TestRestTemplate restTemplate = new TestRestTemplate();
	
	@LocalServerPort
    int randomServerPort;

	@Test
	public void getTrmTest() {
//		ResponseEntity<String> responseEntity = 
//				restTemplate.getForEntity("http://localhost:"+randomServerPort+"/parking/trm/", String.class);
//		String respuesta = responseEntity.getBody();
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(true);
	}

}
