package co.ceibauniversity.parkinglot.domain;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceibauniversity.parkinglot.domain.IDateFormatter;
import co.ceibauniversity.parkinglot.domain.impl.DateFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateFormatterTest {

	private IDateFormatter dateFormatter = new DateFormatter();
	
	@SuppressWarnings("deprecation")
	@Test
	public void formatDateTest() {
		Date date = new Date(118,4,21,12,10);
		Date dateFormatted = dateFormatter.formatDate(new Date(118,4,21,12,10,55));
		assertEquals(dateFormatted,date);
	}
	
	@Test
	public void formatDateFailTest() {
		Date date = new Date(118,4,21,12,10);
		
		try {
			Date dateFormatted = dateFormatter.formatDate(null);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(),null);
		}
	}


}
