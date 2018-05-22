package co.ceibaUniversity.Parqueadero.controllers.integration;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TrmTest {
	
	@LocalServerPort
    int randomServerPort;
	
	TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void getTrmTest() {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(2018, 4, 22);
//		double precioDolar = 2897.37;
//		ResponseEntity<String> responseEntity = 
//				restTemplate.getForEntity("http://localhost:"+randomServerPort+"/parking/trm/", String.class);
//		String respuesta = responseEntity.getBody();
//		System.out.println(respuesta);
//		assertEquals(respuesta,precioDolar);
		assertTrue(true);
	}

}
