package co.ceibaUniversity.Parqueadero.domain;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceibaUniversity.Parqueadero.domain.impl.Clock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClockTest {
	
	private IClock clock = new Clock();
	
	@Test
	public void calculateHoursTest() {
		int hours=8;
		Date date = new Date((long) (System.currentTimeMillis() - (hours*Clock.MILLISECS_TO_HOURS)));;
		assertEquals(clock.getTotalHours(date), hours+Clock.MIN_HOUR);
	}
	
	@Test
	public void calculateZeroHoursTest() {
		Date date = new Date();
		assertEquals(clock.getTotalHours(date), Clock.MIN_HOUR);
	}
}
