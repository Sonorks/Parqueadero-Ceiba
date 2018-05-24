package co.ceibauniversity.parkinglot.controllers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import co.ceibauniversity.parkinglot.databuilder.VehicleTestDataBuilder;
import co.ceibauniversity.parkinglot.controllers.WatchmanController;
import co.ceibauniversity.parkinglot.domain.impl.Watchman;
import co.ceibauniversity.parkinglot.exception.ParkingLotException;
import co.ceibauniversity.parkinglot.model.Ticket;
import co.ceibauniversity.parkinglot.model.Vehicle;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WatchmanControllerTest {
	

	
	@Mock
	private Watchman watchman;
	@InjectMocks
	private WatchmanController watchmanController;
	
	private VehicleTestDataBuilder vehicleTestDataBuilder;
	private Vehicle vehicle;
	
		
	
	@Test
	public void getVehicleTest() {
		vehicleTestDataBuilder = new VehicleTestDataBuilder();
		vehicle = vehicleTestDataBuilder.build();
		Ticket ticket = new Ticket(vehicle.getType(), vehicle.getPlate(), vehicle.getCc(), new Date());
		Mockito.when(watchman.getTicket(vehicle.getPlate())).thenReturn(ticket);

		Ticket ticketGetted = watchmanController.getTicket(vehicle.getPlate());

		assertEquals(ticketGetted.getPlate(), vehicle.getPlate());
	}
	
	@Test
	public void getVehiclesTest() {
		List<Ticket> ticketsGetted = null;
		vehicleTestDataBuilder = new VehicleTestDataBuilder();
		vehicle = vehicleTestDataBuilder.build();
		Ticket ticket1 = new Ticket(vehicle.getType(), vehicle.getPlate(), vehicle.getCc(), new Date());
		List<Ticket> tickets = new ArrayList<>();
		tickets.add(ticket1);
		
		Mockito.when(watchman.getTickets()).thenReturn(tickets);

		ticketsGetted = watchmanController.getTickets();
		assertEquals(ticketsGetted.get(0).getPlate(),tickets.get(0).getPlate());
	}
	

	
	@Test
	public void getVehicleExceptionTest() {
		Mockito.when(watchman.getTicket(VehicleTestDataBuilder.PLATE)).thenReturn(null);
		try {
			watchmanController.getTicket(VehicleTestDataBuilder.PLATE);
			fail();
		}catch(ParkingLotException e) {
			assertEquals(WatchmanController.VEHICLE_NOT_FOUND, e.getMessage());
		}
	}

	@Test
	public void getVehiclesExceptionTest() {
		Mockito.when(watchman.getTickets()).thenReturn(Collections.emptyList());
		try {
			watchmanController.getTickets();
			fail();
		}catch(ParkingLotException e) {
			assertEquals(WatchmanController.VEHICLES_NOT_FOUND, e.getMessage());
		}
	}
	
}