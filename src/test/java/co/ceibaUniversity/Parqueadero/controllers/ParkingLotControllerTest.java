package co.ceibaUniversity.Parqueadero.controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.ceibaUniversity.Parqueadero.dataBuilder.VehicleTestDataBuilder;
import co.ceibaUniversity.Parqueadero.domain.ParkingLotDomain;
import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;
import co.ceibaUniversity.Parqueadero.model.Vehicle;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingLotControllerTest {
	
	private static final String TRUCK = "TRUCK";
	private static final int CAR_HOUR_PRICE = 1000;
	private static final int BIKE_HOUR_PRICE = 500;
	private static final int CAR_DAY_PRICE = 8000;
	private static final int BIKE_DAY_PRICE = 4000;
	
	@Mock
	private ParkingLotDomain parkingLotDomain;
	@InjectMocks
	private ParkingLotController parkingLotController;
	
	private VehicleTestDataBuilder vehicleTestDataBuilder;
	private Vehicle vehicle;
	
	
	@Test
	public void addVehicleCarTest() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder();
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(parkingLotDomain.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(parkingLotDomain.vehicleDisponibility(vehicle.getType())).thenReturn(true);
		Mockito.when(parkingLotDomain.plateValidToday(vehicle.getPlate())).thenReturn(true);
		Mockito.when(parkingLotDomain.vehicleParked(vehicle.getPlate())).thenReturn(true);
		//act
		parkingLotController.addVehicle(vehicle);
		//assert
		assertTrue(parkingLotController.vehicleParked(vehicle.getPlate()));
	}
	
	@Test
	public void addVehicleBikeTest() {
		//arrange
		String type = "BIKE";
		vehicleTestDataBuilder = new VehicleTestDataBuilder().usingType(type);
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(parkingLotDomain.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(parkingLotDomain.vehicleDisponibility(vehicle.getType())).thenReturn(true);
		Mockito.when(parkingLotDomain.plateValidToday(vehicle.getPlate())).thenReturn(true);
		Mockito.when(parkingLotDomain.vehicleParked(vehicle.getPlate())).thenReturn(true);
		//act
		parkingLotController.addVehicle(vehicle);
		//assert
		assertTrue(parkingLotController.vehicleParked(vehicle.getPlate()));
	}
	
	@Test
	public void addVehicleCarNoSpaceTest() {
		//arrange
		vehicleTestDataBuilder = new VehicleTestDataBuilder();
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(parkingLotDomain.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(parkingLotDomain.vehicleDisponibility(vehicle.getType())).thenReturn(false);
		//act
		try {
			parkingLotController.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(ParkingLotController.NO_SPACE, e.getMessage());
		}
	}
	
	@Test
	public void addVehicleBikeNoSpaceTest() {
		//arrange
		String type = "BIKE";
		vehicleTestDataBuilder = new VehicleTestDataBuilder().usingType(type);
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(parkingLotDomain.vehicleTypeAllowed(type)).thenReturn(true);
		Mockito.when(parkingLotDomain.vehicleDisponibility(type)).thenReturn(false);
		try {
			parkingLotController.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(ParkingLotController.NO_SPACE, e.getMessage());
		}
	}
	
	@Test
	public void addVehicleNotAllowedDayTest() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder();
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(parkingLotDomain.vehicleTypeAllowed(vehicle.getType())).thenReturn(true);
		Mockito.when(parkingLotDomain.vehicleDisponibility(vehicle.getType())).thenReturn(true);
		Mockito.when(parkingLotDomain.plateValidToday(vehicle.getPlate())).thenReturn(false);		
		try {
			parkingLotController.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(ParkingLotController.NOT_BUSINESS_DAY, e.getMessage());
		}
	}
	
	@Test
	public void addVehicleNotTypeAllowed() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder().usingType(TRUCK);
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(parkingLotDomain.vehicleTypeAllowed(vehicle.getType())).thenReturn(false);
		try {
			parkingLotController.addVehicle(vehicle);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(ParkingLotController.TYPE_NOT_ALLOWED, e.getMessage());
		}
	}
	
	@Test
	public void getVehicleTest() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder().usingType(TRUCK);
		vehicle = vehicleTestDataBuilder.build();
		Mockito.when(parkingLotDomain.getVehicle(vehicle.getPlate())).thenReturn(vehicle);
		
		Vehicle vehicleGetted = parkingLotController.getVehiculo(vehicle.getPlate());
	
		assertEquals(vehicleGetted, vehicle);
	}
	
	
//	
//	@Test
//	public void removeVehicleTest() {
//		//arrange
//		String plate = "FCL798";
//		Mockito.when(parkingLotDomain.vehicleParked(plate)).thenReturn(false);
//		//act
//		parkingLotController.removeVehicle(plate);
//		//assert
//		assertFalse(parkingLotController.vehicleParked(plate));
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
//		totalPrice = parkingLotController.calculatePayment(plate,type);
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
//		totalPrice = parkingLotController.calculatePayment(plate,type);
//		//assert
//		assertEquals(totalPrice,BIKE_HOUR_PRICE*hour);
//	}
	

}