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

import co.ceibaUniversity.Parqueadero.dao.ITicketDAO;
import co.ceibaUniversity.Parqueadero.dao.IWatchmanDAO;
import co.ceibaUniversity.Parqueadero.model.Ticket;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WatchmanDomainTest {

	private static final String CAR_PLATE = "FCL799";
	private static final String CAR = "CAR";
	private static final String BIKE_PLATE = "ELR01D";
	private static final String BIKE = "BIKE";
	private static final String OTHER_PLATE = "ABC123";
	private static final String OTHER = "OTHER";

	@Mock
	private ITicketDAO ticketDAO;

	@Mock
	private CalendarParkingLot calendar;
	
	@Mock 
	private IWatchmanDAO watchmanDAO;
	
	@Mock
	private Clock clock;

	private Watchman watchman;

	@Before
	public void init() {
		watchman = new Watchman(ticketDAO, watchmanDAO, calendar, clock);
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
	public void calculateCarPaymentByHoursTest() {
		double totalPrice = 0;
		String type = CAR;
		int totalHours = 6;
		
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertTrue(totalPrice == 6000);
	}
	
	@Test
	public void calculateBikePaymentByHoursTest() {
		double totalPrice = 0;
		String type = BIKE;
		int totalHours = 6;
		
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertTrue(totalPrice == 3000);
	}
	
	@Test 
	public void calculateCarPaymentByDayTest() {
		double totalPrice = 0;
		String type = CAR;
		int totalHours = 10;
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertTrue(totalPrice == 8000);
	}
	
	@Test 
	public void calculateBikePaymentByDayTest() {
		double totalPrice = 0;
		String type = BIKE;
		int totalHours = 10;
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertTrue(totalPrice == 4000);
	}
	
	@Test 
	public void calculateCarPaymentMoreThanDayTest() {
		double totalPrice = 0;
		String type = CAR;
		int totalHours = 30;
		totalPrice = watchman.calculatePayment(type, 0, totalHours); 	
		
		assertTrue(totalPrice == 14000);
	}
	
	@Test 
	public void calculateCarPaymentMoreThanDayTest2() {
		double totalPrice = 0;
		String type = CAR;
		int totalHours = 34;
		totalPrice = watchman.calculatePayment(type, 0, totalHours); 	
		
		assertTrue(totalPrice == 16000);
	}
	
	@Test 
	public void calculateBikePaymentMoreThanDayTest() {
		double totalPrice = 0;
		String type = BIKE;
		int totalHours = 28;
		totalPrice = watchman.calculatePayment(type, 0, totalHours); 	
		
		assertTrue(totalPrice == 6000);
	}
	
	@Test 
	public void calculateBikePaymentMoreCCDayTest() {
		double totalPrice = 0;
		String type = BIKE;
		int CC = 650;
		int totalHours = 28;
		totalPrice = watchman.calculatePayment(type, CC, totalHours); 	
		
		assertTrue(totalPrice == 8000);
	}
	
	@Test 
	public void calculateBikePaymentMoreCCHourTest() {
		double totalPrice = 0;
		String type = BIKE;
		int CC = 650;
		int totalHours = 4;
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
}