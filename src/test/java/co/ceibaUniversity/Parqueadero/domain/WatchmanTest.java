package co.ceibaUniversity.Parqueadero.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import co.ceibaUniversity.Parqueadero.dao.TicketDAO;
import co.ceibaUniversity.Parqueadero.dao.WatchmanDAO;
import co.ceibaUniversity.Parqueadero.dataBuilder.VehicleTestDataBuilder;
import co.ceibaUniversity.Parqueadero.domain.impl.Calculator;
import co.ceibaUniversity.Parqueadero.domain.impl.CalendarParkingLot;
import co.ceibaUniversity.Parqueadero.domain.impl.Clock;
import co.ceibaUniversity.Parqueadero.domain.impl.DateFormatter;
import co.ceibaUniversity.Parqueadero.domain.impl.Watchman;
import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WatchmanTest {

	private static final String CAR_PLATE = "FCL799";
	private static final String CAR = "CAR";
	private static final String BIKE_PLATE = "ELR01D";
	private static final String BIKE = "BIKE";
	private static final String OTHER_PLATE = "ABC123";
	private static final String OTHER = "OTHER";

	@Mock
	private TicketDAO ticketDAO;

	@Mock
	private CalendarParkingLot calendar;
	
	@Mock 
	private WatchmanDAO watchmanDAO;
	
	@Mock
	private Clock clock;
	
	@Mock
	private DateFormatter dateFormatter;
	
	@Mock
	private Calculator calculator;

	private Watchman watchman;

	@Before
	public void init() {
		watchman = new Watchman(ticketDAO, watchmanDAO, calendar, clock, dateFormatter, calculator);
	}

	@Test
	public void getTypeCarTest() {
		// arrange
		Mockito.when(ticketDAO.getVehicleType(CAR_PLATE)).thenReturn(CAR);
		String type;
		// act
		type = watchman.getType(CAR_PLATE);
		// assert
		assertEquals(CAR, type);
	}

	@Test
	public void getTypeBikeTest() {
		// arrange
		String type;
		Mockito.when(ticketDAO.getVehicleType(BIKE_PLATE)).thenReturn(BIKE);
		// act
		type = watchman.getType(BIKE_PLATE);
		// assert
		assertEquals(BIKE, type);
	}

	@Test
	public void getTypeFailTest() {
		String type;
		Mockito.when(ticketDAO.getVehicleType(OTHER_PLATE)).thenReturn(OTHER);
		type = watchman.getType(OTHER_PLATE);

		assertEquals(OTHER, type);
	}

	@Test
	public void vehicleTypeCarAllowedTest() {
		assertTrue(watchman.vehicleTypeAllowed(CAR));
	}

	@Test
	public void vehicleTypeBikeAllowedTest() {
		assertTrue(watchman.vehicleTypeAllowed(BIKE));
	}

	@Test
	public void vehicleTypeOtherAllowedTest() {
		assertFalse(watchman.vehicleTypeAllowed(OTHER));
	}

	@Test
	public void plateValidTodayTest() {
		assertTrue(watchman.plateValidToday(CAR_PLATE));
	}

	@Test
	public void plateInitAValidDayTest() {
		String plate = "ACL799";
		Mockito.when(calendar.esDiaHabil()).thenReturn(true);
		assertTrue(watchman.plateValidToday(plate));
	}

	@Test
	public void plateInitAValidDayFailTest() {
		String plate = "AAA111";
		Mockito.when(calendar.esDiaHabil()).thenReturn(false);
		assertFalse(watchman.plateValidToday(plate));
	}

	@Test
	public void vehicleCarDisponibilityTest() {
		Mockito.when(watchmanDAO.getCarSpaces()).thenReturn(5);
		assertTrue(watchman.vehicleDisponibility(CAR));
	}

	@Test
	public void vehicleCarDisponibilityFailTest() {
		Mockito.when(watchmanDAO.getCarSpaces()).thenReturn(20);
		assertFalse(watchman.vehicleDisponibility(CAR));
	}

	@Test
	public void vehicleBikeDisponibilityTest() {
		Mockito.when(watchmanDAO.getBikeSpaces()).thenReturn(1);
		assertTrue(watchman.vehicleDisponibility(BIKE));
	}

	@Test
	public void vehicleBikeDisponibilityFailTest() {
		Mockito.when(watchmanDAO.getBikeSpaces()).thenReturn(10);
		assertFalse(watchman.vehicleDisponibility(BIKE));
	}
	
	@Test
	public void vehicleOtherDisponibilityTest() {
		assertFalse(watchman.vehicleDisponibility(OTHER));
	}
	
	@Test
	public void calculateCarPaymentByHoursTest() {
		double totalPrice = 0;
		String type = CAR;
		int totalHours = 6;
		Mockito.when(calculator.getTotalPrice(totalHours, Watchman.CAR_DAY_PRICE, Watchman.CAR_HOUR_PRICE)).thenReturn((double) 6000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertTrue(totalPrice == 6000);
	}
	
	@Test
	public void calculateBikePaymentByHoursTest() {
		double totalPrice = 0;
		String type = BIKE;
		int totalHours = 6;
		Mockito.when(calculator.getTotalPrice(totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE)).thenReturn((double) 3000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertTrue(totalPrice == 3000);
	}
	
	@Test 
	public void calculateCarPaymentByDayTest() {
		double totalPrice = 0;
		String type = CAR;
		int totalHours = 10;
		Mockito.when(calculator.getTotalPrice(totalHours, Watchman.CAR_DAY_PRICE, Watchman.CAR_HOUR_PRICE)).thenReturn((double) 8000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertTrue(totalPrice == 8000);
	}
	
	@Test 
	public void calculateBikePaymentByDayTest() {
		double totalPrice = 0;
		String type = BIKE;
		int totalHours = 10;
		Mockito.when(calculator.getTotalPrice(totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE)).thenReturn((double) 4000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertTrue(totalPrice == 4000);
	}
	
	@Test 
	public void calculateCarPaymentMoreThanDayTest() {
		double totalPrice = 0;
		String type = CAR;
		int totalHours = 30;
		Mockito.when(calculator.getTotalPrice(totalHours, Watchman.CAR_DAY_PRICE, Watchman.CAR_HOUR_PRICE)).thenReturn((double) 14000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours); 	
		
		assertTrue(totalPrice == 14000);
	}
	
	@Test 
	public void calculateCarPaymentMoreThanDayTest2() {
		double totalPrice = 0;
		String type = CAR;
		int totalHours = 34;
		Mockito.when(calculator.getTotalPrice(totalHours, Watchman.CAR_DAY_PRICE, Watchman.CAR_HOUR_PRICE)).thenReturn((double) 16000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours); 	
		
		assertTrue(totalPrice == 16000);
	}
	
	@Test 
	public void calculateBikePaymentMoreThanDayTest() {
		double totalPrice = 0;
		String type = BIKE;
		int totalHours = 28;
		Mockito.when(calculator.getTotalPrice(totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE)).thenReturn((double) 6000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours); 	
		assertTrue(totalPrice == 6000);
	}
	
	@Test 
	public void calculateBikePaymentMoreCCDayTest() {
		double totalPrice = 0;
		String type = BIKE;
		int CC = 650;
		int totalHours = 28;
		Mockito.when(calculator.getTotalPrice(totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE)).thenReturn((double) 6000);
		totalPrice = watchman.calculatePayment(type, CC, totalHours); 	
		
		assertTrue(totalPrice==8000);
	}
	
	@Test 
	public void calculateBikePaymentMoreCCHourTest() {
		double totalPrice = 0;
		String type = BIKE;
		int CC = 650;
		int totalHours = 4;
		Mockito.when(calculator.getTotalPrice(totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE)).thenReturn((double) 2000);
		totalPrice = watchman.calculatePayment(type, CC, totalHours); 	
		
		assertTrue(totalPrice == 4000);
	}
	
	@Test
	public void removeVehicleTest() {
		String plate = CAR_PLATE;
		Ticket ticket = new Ticket(CAR,CAR_PLATE,0,new Date());
		ticket.setExitDate(new Date());
		Mockito.when(ticketDAO.getTicket(plate)).thenReturn(ticket);
		Mockito.when(clock.getTotalHours(new Date())).thenReturn(8);
		watchman.removeVehicle(plate);
		assertFalse(watchman.isVehicleParked(plate));
	}
	
	@Test
	public void isVehicleParkedTest() {
		String plate = CAR_PLATE;
		Mockito.when(ticketDAO.getTicket(plate)).thenReturn(null);
		assertFalse(watchman.isVehicleParked(plate));
	}
	
//	@Test
//	public void addVehicleTest() {
//		VehicleTestDataBuilder vehicleTestDataBuilder = new VehicleTestDataBuilder();
//		Vehicle vehicle = vehicleTestDataBuilder.build();
//		Ticket ticket = new Ticket(vehicle.getType(), vehicle.getPlate(), vehicle.getCc(), new Date());
//		Mockito.when(ticketDAO.addTicket(ticket)).thenReturn(true);
//		
//		assertEquals(watchman.addVehicle(vehicle),true);
//	}
	
}