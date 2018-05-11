package co.ceibaUniversity.Parqueadero.domain;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceibaUniversity.Parqueadero.dao.ITicketDAO;

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
	private ITicketDAO parkingLotDAO;

	@Mock
	private CalendarParkingLot calendar;

	private WatchmanDomain parkingLotDomain;

	@Before
	public void init() {
		parkingLotDomain = new WatchmanDomain(parkingLotDAO, calendar);
	}

	@Test
	public void getTypeCarTest() {
		// arrange
		Mockito.when(parkingLotDAO.getVehicleType(CAR_PLATE)).thenReturn(CAR);
		String type;
		// act
		type = parkingLotDomain.getType(CAR_PLATE);
		// assert
		assertEquals(CAR, type);
	}

	@Test
	public void getTypeBikeTest() {
		// arrange
		String type;
		Mockito.when(parkingLotDAO.getVehicleType(BIKE_PLATE)).thenReturn(BIKE);
		// act
		type = parkingLotDomain.getType(BIKE_PLATE);
		// assert
		assertEquals(BIKE, type);
	}

	@Test
	public void getTypeFailTest() {
		String type;
		Mockito.when(parkingLotDAO.getVehicleType(OTHER_PLATE)).thenReturn(OTHER);
		type = parkingLotDomain.getType(OTHER_PLATE);

		assertEquals(OTHER, type);
	}

	@Test
	public void vehicleTypeCarAllowedTest() {
		assertTrue(parkingLotDomain.vehicleTypeAllowed(CAR));
	}

	@Test
	public void vehicleTypeBikeAllowedTest() {
		assertTrue(parkingLotDomain.vehicleTypeAllowed(BIKE));
	}

	@Test
	public void vehicleTypeOtherAllowedTest() {
		assertFalse(parkingLotDomain.vehicleTypeAllowed(OTHER));
	}

	@Test
	public void plateValidTodayTest() {
		assertTrue(parkingLotDomain.plateValidToday(CAR_PLATE));
	}

	@Test
	public void plateInitAValidDayTest() {
		String plate = "ACL799";
		Mockito.when(calendar.esDiaHabil()).thenReturn(true);
		assertTrue(parkingLotDomain.plateValidToday(plate));
	}

	@Test
	public void plateInitAValidDayFailTest() {
		String plate = "AAA111";
		Mockito.when(calendar.esDiaHabil()).thenReturn(false);
		assertFalse(parkingLotDomain.plateValidToday(plate));
	}

	@Test
	public void vehicleCarDisponibilityTest() {
		Mockito.when(parkingLotDAO.getCarSpaces()).thenReturn(5);
		assertTrue(parkingLotDomain.vehicleDisponibility(CAR));
	}

	@Test
	public void vehicleCarDisponibilityFailTest() {
		Mockito.when(parkingLotDAO.getCarSpaces()).thenReturn(20);
		assertFalse(parkingLotDomain.vehicleDisponibility(CAR));
	}

	@Test
	public void vehicleBikeDisponibilityTest() {
		Mockito.when(parkingLotDAO.getBikeSpaces()).thenReturn(1);
		assertTrue(parkingLotDomain.vehicleDisponibility(BIKE));
	}

	@Test
	public void vehicleBikeDisponibilityFailTest() {
		Mockito.when(parkingLotDAO.getBikeSpaces()).thenReturn(10);
		assertFalse(parkingLotDomain.vehicleDisponibility(BIKE));
	}
}