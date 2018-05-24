package co.ceibauniversity.parkinglot.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceibauniversity.parkinglot.domain.impl.Calculator;
import co.ceibauniversity.parkinglot.domain.impl.Watchman;
import co.ceibauniversity.parkinglot.model.Vehicle;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculatorTest {
	
	private Calculator calculator = new Calculator();

	@Test
	public void getTotalPriceRegularHoursTest() {
		double price = 0;
		int totalHours = 6;
		price = calculator.getTotalPrice(Vehicle.BIKE, 200, totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE);
		assertEquals(price,3000,0);
	}
	
	@Test
	public void getTotalPriceDayTest() {
		double price = 0;
		int totalHours = 16;
		price = calculator.getTotalPrice(Vehicle.BIKE, 200, totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE);
		assertEquals(price,4000,0);
	}
	
	@Test
	public void getTotalPriceMoreThanDayTest() {
		double price = 0;
		int totalHours = 26;
		price = calculator.getTotalPrice(Vehicle.BIKE, 200, totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE);
		assertEquals(price,5000,0);
	}
	
	@Test
	public void getTotalPriceTwoDaysTest() {
		double price = 0;
		int totalHours = 46;
		price = calculator.getTotalPrice(Vehicle.BIKE, 200, totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE);
		assertEquals(price,8000,0);
	}

}
