package co.ceibaUniversity.Parqueadero.controllers;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import co.ceibaUniversity.Parqueadero.dataBuilder.VehicleTestDataBuilder;
import co.ceibaUniversity.Parqueadero.domain.impl.Watchman;
import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;
import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WatchmanControllerTest {
	
	private static final String TRUCK = "TRUCK";
	
	@Mock
	private Watchman watchman;
	@InjectMocks
	private WatchmanController watchmanController;
	
	private VehicleTestDataBuilder vehicleTestDataBuilder;
	private Vehicle vehicle;
	
	
	@Test
	public void addVehicleCarTest() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder();
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(watchman.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(watchman.vehicleDisponibility(vehicle.getType())).thenReturn(true);
		Mockito.when(watchman.plateValidToday(vehicle.getPlate())).thenReturn(true);
		Mockito.when(watchman.isVehicleParked(vehicle.getPlate())).thenReturn(true);
		//act
		watchmanController.addVehicle(vehicle);
		//assert
		assertTrue(watchmanController.vehicleParked(vehicle.getPlate()));
	}
	
	@Test
	public void addVehicleBikeTest() {
		//arrange
		String type = "BIKE";
		vehicleTestDataBuilder = new VehicleTestDataBuilder().usingType(type);
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(watchman.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(watchman.vehicleDisponibility(vehicle.getType())).thenReturn(true);
		Mockito.when(watchman.plateValidToday(vehicle.getPlate())).thenReturn(true);
		Mockito.when(watchman.isVehicleParked(vehicle.getPlate())).thenReturn(true);
		//act
		watchmanController.addVehicle(vehicle);
		//assert
		assertTrue(watchmanController.vehicleParked(vehicle.getPlate()));
	}
	
	@Test
	public void addVehicleCarNoSpaceTest() {
		//arrange
		vehicleTestDataBuilder = new VehicleTestDataBuilder();
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(watchman.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(watchman.vehicleDisponibility(vehicle.getType())).thenReturn(false);
		//act
		try {
			watchmanController.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(WatchmanController.NO_SPACE, e.getMessage());
		}
	}
	
	@Test
	public void addVehicleBikeNoSpaceTest() {
		//arrange
		String type = "BIKE";
		vehicleTestDataBuilder = new VehicleTestDataBuilder().usingType(type);
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(watchman.vehicleTypeAllowed(type)).thenReturn(true);
		Mockito.when(watchman.vehicleDisponibility(type)).thenReturn(false);
		try {
			watchmanController.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(WatchmanController.NO_SPACE, e.getMessage());
		}
	}
	
	@Test
	public void addVehicleNotAllowedDayTest() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder();
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(watchman.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(watchman.vehicleDisponibility(vehicle.getType())).thenReturn(true);
		Mockito.when(watchman.plateValidToday(vehicle.getPlate())).thenReturn(false);		
		try {
			watchmanController.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(WatchmanController.NOT_BUSINESS_DAY, e.getMessage());
		}
	}
	
	@Test
	public void addVehicleNotTypeAllowed() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder().usingType(TRUCK);
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(watchman.vehicleTypeAllowed(vehicle.getType())).thenReturn(false);
		try {
			watchmanController.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(WatchmanController.TYPE_NOT_ALLOWED, e.getMessage());
		}
	}
	
	@Test
	public void getVehicleTest() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder().usingType(TRUCK);
		vehicle = vehicleTestDataBuilder.build();
		Ticket ticket = new Ticket(vehicle.getType(), vehicle.getPlate(), vehicle.getCc(), new Date());
		Mockito.when(watchman.getTicket(vehicle.getPlate())).thenReturn(ticket);

		Ticket ticketGetted = watchmanController.getTicket(vehicle.getPlate());

		assertEquals(ticketGetted.getPlate(), vehicle.getPlate());
	}
	
	
	
	@Test
	public void removeVehicleTest() {
		//arrange
		String plate = "FCL798";
		Mockito.when(watchman.removeVehicle(plate)).thenReturn(true);
		//assert
		assertTrue(watchmanController.removeVehicle(plate));
	}


}