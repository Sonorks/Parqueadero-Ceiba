package unitarias.controller;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import controllers.ParkingLotController;
import domain.ParkingLotDomain;
import exception.ParkingLotException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingLotControllerTest {
	

	private static final int CAR_HOUR_PRICE = 1000;
	private static final int BIKE_HOUR_PRICE = 500;
	private static final int CAR_DAY_PRICE = 8000;
	private static final int BIKE_DAY_PRICE = 4000;
	
	
	private ParkingLotDomain parkingLotDomain = mock(ParkingLotDomain.class);
	private ParkingLotController parkingLotController = new ParkingLotController(parkingLotDomain);
	
	
	@Test
	public void addVehicleCarTest() {
		//arrange
		String type = "CAR";
		String plate = "FCL799";
		int cc = 1400;
		when(parkingLotDomain.vehicleTypeAllowed(type)).thenReturn(true);
		when(parkingLotDomain.vehicleDisponibility(type)).thenReturn(true);
		when(parkingLotDomain.plateValidToday(plate)).thenReturn(true);
		when(parkingLotDomain.vehicleParked(plate)).thenReturn(true);
		//act
		parkingLotController.addVehicle(type,plate,cc);
		//assert
		assertTrue(parkingLotController.vehicleParked(plate));
	}
	
	@Test
	public void addVehicleBikeTest() {
		//arrange
		String type = "BIKE";
		String plate = "ELR01D";
		int cc = 150;
		when(parkingLotDomain.vehicleTypeAllowed(type)).thenReturn(true);
		when(parkingLotDomain.vehicleDisponibility(type)).thenReturn(true);
		when(parkingLotDomain.plateValidToday(plate)).thenReturn(true);
		when(parkingLotDomain.vehicleParked(plate)).thenReturn(true);
		//act
		parkingLotController.addVehicle(type,plate,cc);
		//assert
		assertTrue(parkingLotController.vehicleParked(plate));
	}
	
	@Test
	public void addVehicleCarNoSpaceTest() {
		//arrange
		String type = "CAR";
		String plate = "FCL799";
		int cc = 1400;
		when(parkingLotDomain.vehicleTypeAllowed(type)).thenReturn(true);
		when(parkingLotDomain.vehicleDisponibility(type)).thenReturn(false);
		when(parkingLotDomain.plateValidToday(plate)).thenReturn(true);
		when(parkingLotDomain.vehicleParked(plate)).thenReturn(false);
		//act
		parkingLotController.addVehicle(type, plate, cc);
		
		assertFalse(parkingLotController.vehicleParked(plate));
	}
	
	@Test
	public void addVehicleBikeNoSpaceTest() {
		//arrange
		String type = "BIKE";
		String plate = "ELR01D";
		int cc = 150;
		when(parkingLotDomain.vehicleTypeAllowed(type)).thenReturn(true);
		when(parkingLotDomain.vehicleDisponibility(type)).thenReturn(false);
		when(parkingLotDomain.plateValidToday(plate)).thenReturn(true);
		when(parkingLotDomain.addVehicle(type,plate,cc)).thenReturn(false);
		when(parkingLotDomain.vehicleParked(plate)).thenReturn(false);
		
		parkingLotController.addVehicle(type, plate, cc);
		
		assertFalse(parkingLotController.vehicleParked(plate));
	}
	
	@Test
	public void removeVehicleTest() {
		//arrange
		String plate = "FCL798";
		when(parkingLotDomain.vehicleParked(plate)).thenReturn(false);
		//act
		parkingLotController.removeVehicle(plate);
		//assert
		assertFalse(parkingLotController.vehicleParked(plate));
	}
	
	@Test
	public void makePaymentHoursCarTest() {
		//arrange
		int hour = 7;
		int totalPrice;
		String plate = "FCL799";
		String type = "CAR";

		//act
		totalPrice = parkingLotController.calculatePayment(plate,type);
		//assert
		assertEquals(totalPrice,CAR_HOUR_PRICE*hour);
	}
	
	@Test
	public void makePaymentHoursBikeTest() {
		//arrange
		int hour = 5;
		int totalPrice;
		String plate = "ELR01D";
		String type = "BIKE";
	
		//act
		totalPrice = parkingLotController.calculatePayment(plate,type);
		//assert
		assertEquals(totalPrice,BIKE_HOUR_PRICE*hour);
	}
	

}
