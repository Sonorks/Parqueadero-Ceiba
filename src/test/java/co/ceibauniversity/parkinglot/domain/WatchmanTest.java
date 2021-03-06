package co.ceibauniversity.parkinglot.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.spy;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceibauniversity.parkinglot.dao.TicketDAO;
import co.ceibauniversity.parkinglot.dao.VehicleRepository;
import co.ceibauniversity.parkinglot.databuilder.VehicleTestDataBuilder;
import co.ceibauniversity.parkinglot.domain.impl.Calculator;
import co.ceibauniversity.parkinglot.domain.impl.CalendarParkingLot;
import co.ceibauniversity.parkinglot.domain.impl.Clock;
import co.ceibauniversity.parkinglot.domain.impl.DateFormatter;
import co.ceibauniversity.parkinglot.domain.impl.Watchman;
import co.ceibauniversity.parkinglot.exception.ParkingLotException;
import co.ceibauniversity.parkinglot.model.Ticket;
import co.ceibauniversity.parkinglot.model.Vehicle;


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
	private VehicleRepository watchmanDAO;
	
	@Mock
	private Clock clock;
	
	@Mock
	private DateFormatter dateFormatter;
	
	@Mock
	private Calculator calculator;

	private Watchman watchman;

	
	private VehicleTestDataBuilder vehicleTestDataBuilder;
	private Vehicle vehicle;
	Watchman watchmanMock;
	
	@Before
	public void init() {
		watchman = new Watchman(ticketDAO, watchmanDAO, calendar, clock, dateFormatter, calculator);
		watchmanMock = spy(watchman);
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
	public void plateInitaValidDayTest() {
		String plate = "acl799";
		Mockito.when(calendar.esDiaHabil()).thenReturn(true);
		assertTrue(watchman.plateValidToday(plate));
	}

	@Test
	public void plateInitaValidDayFailTest() {
		String plate = "aaa111";
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
		Mockito.when(calculator.getTotalPrice(Vehicle.CAR, 0, totalHours, Watchman.CAR_DAY_PRICE, Watchman.CAR_HOUR_PRICE)).thenReturn((double) 6000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertEquals(totalPrice,6000,0);
	}
	
	@Test
	public void calculateBikePaymentByHoursTest() {
		double totalPrice = 0;
		String type = BIKE;
		int totalHours = 6;
		Mockito.when(calculator.getTotalPrice(Vehicle.BIKE, 0, totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE)).thenReturn((double) 3000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertTrue(totalPrice == 3000);
	}
	
	@Test 
	public void calculateCarPaymentByDayTest() {
		double totalPrice = 0;
		String type = CAR;
		int totalHours = 10;
		Mockito.when(calculator.getTotalPrice(Vehicle.CAR, 0, totalHours, Watchman.CAR_DAY_PRICE, Watchman.CAR_HOUR_PRICE)).thenReturn((double) 8000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertTrue(totalPrice == 8000);
	}
	
	@Test 
	public void calculateBikePaymentByDayTest() {
		double totalPrice = 0;
		String type = BIKE;
		int totalHours = 10;
		Mockito.when(calculator.getTotalPrice(Vehicle.BIKE, 0, totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE)).thenReturn((double) 4000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours);
		
		assertEquals(totalPrice,4000,0);
	}
	
	@Test 
	public void calculateCarPaymentMoreThanDayTest() {
		double totalPrice = 0;
		String type = CAR;
		int totalHours = 30;
		Mockito.when(calculator.getTotalPrice(Vehicle.CAR, 0, totalHours, Watchman.CAR_DAY_PRICE, Watchman.CAR_HOUR_PRICE)).thenReturn((double) 14000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours); 	
		
		assertTrue(totalPrice == 14000);
	}
	
	@Test 
	public void calculateCarPaymentMoreThanDayTest2() {
		double totalPrice = 0;
		String type = CAR;
		int totalHours = 34;
		Mockito.when(calculator.getTotalPrice(Vehicle.CAR, 0, totalHours, Watchman.CAR_DAY_PRICE, Watchman.CAR_HOUR_PRICE)).thenReturn((double) 16000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours); 	
		
		assertTrue(totalPrice == 16000);
	}
	
	@Test 
	public void calculateBikePaymentMoreThanDayTest() {
		double totalPrice = 0;
		String type = BIKE;
		int totalHours = 28;
		Mockito.when(calculator.getTotalPrice(Vehicle.BIKE, 0, totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE)).thenReturn((double) 6000);
		totalPrice = watchman.calculatePayment(type, 0, totalHours); 	
		assertTrue(totalPrice == 6000);
	}
	
	@Test 
	public void calculateBikePaymentMoreCCDayTest() {
		double totalPrice = 0;
		String type = BIKE;
		int CC = 650;
		int totalHours = 28;
		Mockito.when(calculator.getTotalPrice(Vehicle.BIKE, CC, totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE)).thenReturn((double) 8000);
		totalPrice = watchman.calculatePayment(type, CC, totalHours); 	
		
		assertEquals(totalPrice,8000,0);
	}
	
	@Test 
	public void calculateBikePaymentMoreCCHourTest() {
		double totalPrice = 0;
		String type = BIKE;
		int CC = 650;
		int totalHours = 4;
		Mockito.when(calculator.getTotalPrice(Vehicle.BIKE, CC, totalHours, Watchman.BIKE_DAY_PRICE, Watchman.BIKE_HOUR_PRICE)).thenReturn((double) 4000);
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
	
	@Test
	public void isVehicleNotParkedTest() {
		String plate = CAR_PLATE;
		Ticket ticket = new Ticket();
		ticket.setExitDate(null);
		Mockito.when(ticketDAO.getTicket(plate)).thenReturn(ticket);
		assertTrue(watchman.isVehicleParked(plate));
	}
	
	@Test
	public void addVehicleAlreadyParked() {
		String type = "BIKE";
		vehicleTestDataBuilder = new VehicleTestDataBuilder().usingType(type);
		vehicle = vehicleTestDataBuilder.build();
		
		Mockito.doReturn(true).when(watchmanMock).vehicleTypeAllowed(vehicle.getType());
		Mockito.doReturn(true).when(watchmanMock).vehicleDisponibility(vehicle.getType());
		Mockito.doReturn(true).when(watchmanMock).plateValidToday(vehicle.getPlate());
		Mockito.doReturn(true).when(watchmanMock).isVehicleParked(vehicle.getPlate());
		try {
			watchmanMock.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(Watchman.VEHICLE_ALREADY_PARKED, e.getMessage());
		}
	}
	
	@Test
	public void addVehicleNotTypeAllowedTest() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder().usingType(OTHER);
		vehicle = vehicleTestDataBuilder.build();

		Mockito.doReturn(false).when(watchmanMock).vehicleTypeAllowed(vehicle.getType());
		try {
			watchmanMock.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(Watchman.TYPE_NOT_ALLOWED, e.getMessage());
		}
	}
	
	@Test
	public void addVehicleNotAllowedDayTest() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder();
		vehicle = vehicleTestDataBuilder.build();
		
		Mockito.doReturn(true).when(watchmanMock).vehicleTypeAllowed(vehicle.getType());
		Mockito.doReturn(true).when(watchmanMock).vehicleDisponibility(vehicle.getType());
		Mockito.doReturn(false).when(watchmanMock).plateValidToday(vehicle.getPlate());	
		try {
			watchmanMock.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(Watchman.NOT_BUSINESS_DAY, e.getMessage());
		}
	}
	
	@Test
	public void addVehicleBikeNoSpaceTest() {
		//arrange
		String type = "BIKE";
		vehicleTestDataBuilder = new VehicleTestDataBuilder().usingType(type);
		vehicle = vehicleTestDataBuilder.build();
		Mockito.doReturn(true).when(watchmanMock).vehicleTypeAllowed(vehicle.getType());
		Mockito.doReturn(false).when(watchmanMock).vehicleDisponibility(vehicle.getType());
		try {
			watchmanMock.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(Watchman.NO_SPACE, e.getMessage());
		}
	}
	
	@Test
	public void addVehicleCarNoSpaceTest() {
		//arrange
		vehicleTestDataBuilder = new VehicleTestDataBuilder();
		vehicle = vehicleTestDataBuilder.build();
		Mockito.doReturn(true).when(watchmanMock).vehicleTypeAllowed(vehicle.getType());
		Mockito.doReturn(false).when(watchmanMock).vehicleDisponibility(vehicle.getType());
		//act
		try {
			watchmanMock.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(Watchman.NO_SPACE, e.getMessage());
		}
	}
	

	

	
}