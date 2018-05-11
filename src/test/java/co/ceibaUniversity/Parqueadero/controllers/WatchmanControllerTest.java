package co.ceibaUniversity.Parqueadero.controllers;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.ceibaUniversity.Parqueadero.dataBuilder.VehicleTestDataBuilder;
import co.ceibaUniversity.Parqueadero.domain.WatchmanDomain;
import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;
import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WatchmanControllerTest {
	
	private static final String TRUCK = "TRUCK";
	private static final int CAR_HOUR_PRICE = 1000;
	private static final int BIKE_HOUR_PRICE = 500;
	private static final int CAR_DAY_PRICE = 8000;
	private static final int BIKE_DAY_PRICE = 4000;
	
	@Mock
	private WatchmanDomain watchmanDomain;
	@InjectMocks
	private WatchmanController watchmanController;
	
	private VehicleTestDataBuilder vehicleTestDataBuilder;
	private Vehicle vehicle;
	
	
	@Test
	public void addVehicleCarTest() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder();
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(watchmanDomain.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(watchmanDomain.vehicleDisponibility(vehicle.getType())).thenReturn(true);
		Mockito.when(watchmanDomain.plateValidToday(vehicle.getPlate())).thenReturn(true);
		Mockito.when(watchmanDomain.vehicleParked(vehicle.getPlate())).thenReturn(true);
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
		Mockito.when(watchmanDomain.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(watchmanDomain.vehicleDisponibility(vehicle.getType())).thenReturn(true);
		Mockito.when(watchmanDomain.plateValidToday(vehicle.getPlate())).thenReturn(true);
		Mockito.when(watchmanDomain.vehicleParked(vehicle.getPlate())).thenReturn(true);
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
		Mockito.when(watchmanDomain.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(watchmanDomain.vehicleDisponibility(vehicle.getType())).thenReturn(false);
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
		Mockito.when(watchmanDomain.vehicleTypeAllowed(type)).thenReturn(true);
		Mockito.when(watchmanDomain.vehicleDisponibility(type)).thenReturn(false);
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
		Mockito.when(watchmanDomain.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(watchmanDomain.vehicleDisponibility(vehicle.getType())).thenReturn(true);
		Mockito.when(watchmanDomain.plateValidToday(vehicle.getPlate())).thenReturn(false);		
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
		Mockito.when(watchmanDomain.vehicleTypeAllowed(vehicle.getType())).thenReturn(false);
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
		Mockito.when(watchmanDomain.getTicket(vehicle.getPlate())).thenReturn(ticket);

		Ticket ticketGetted = watchmanController.getTicket(vehicle.getPlate());

		assertEquals(ticketGetted.getPlate(), vehicle.getPlate());
	}
//	
//	
//	
//	@Test
//	public void removeVehicleTest() {
//		//arrange
//		String plate = "FCL798";
//		Mockito.when(watchmanDomain.vehicleParked(plate)).thenReturn(false);
//		//act
//		watchmanController.removeVehicle(plate);
//		//assert
//		assertFalse(watchmanController.vehicleParked(plate));
//	}
	
//	@Test
//	public void makePaymentHoursCarTest() {
//		//arrange
//		int hour = 7;
//		int totalPrice;
//		String plate = "FCL799";
//		String type = "CAR";
//
//		//act
//		totalPrice = watchmanController.calculatePayment(plate,type);
//		//assert
//		assertEquals(totalPrice,CAR_HOUR_PRICE*hour);
//	}
//	
//	@Test
//	public void makePaymentHoursBikeTest() {
//		//arrange
//		int hour = 5;
//		int totalPrice;
//		String plate = "ELR01D";
//		String type = "BIKE";
//	
//		//act
//		totalPrice = watchmanController.calculatePayment(plate,type);
//		//assert
//		assertEquals(totalPrice,BIKE_HOUR_PRICE*hour);
//	}
	

}