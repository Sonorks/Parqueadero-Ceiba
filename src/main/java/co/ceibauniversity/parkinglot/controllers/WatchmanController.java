package co.ceibauniversity.parkinglot.controllers;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.ceibauniversity.parkinglot.domain.IWatchman;
import co.ceibauniversity.parkinglot.exception.ParkingLotException;
import co.ceibauniversity.parkinglot.model.Ticket;
import co.ceibauniversity.parkinglot.model.Vehicle;

@RestController
public class WatchmanController {
	
	public static final String VEHICLES_NOT_FOUND = "No hay vehiculos en el parqueadero";
	public static final String VEHICLE_NOT_FOUND = "El vehiculo buscado no se encuentra en el parqueadero";
	
	
	
	@Autowired
	private IWatchman watchman;

	@RequestMapping(value = "/parking/addVehicle", method = RequestMethod.POST)
	public void addVehicle(@RequestBody Vehicle vehicle) {
		watchman.addVehicle(vehicle);
	}

	@RequestMapping(value ="/parking/removeVehicle/{plate}", method = RequestMethod.POST)
	public void removeVehicle(@PathVariable String plate) {
		watchman.removeVehicle(plate);
	}
	
	@RequestMapping(value = "/parking/ticket/{plate}", method = RequestMethod.GET)
	public Ticket getTicket(@PathVariable("plate") String plate) {
		Ticket ticket = watchman.getTicket(plate);
		if(ticket == null) {
			throw new ParkingLotException(VEHICLE_NOT_FOUND);
		}
		return ticket;
	}
	
	@RequestMapping(value = "/parking/tickets/", method = RequestMethod.GET)
	public List<Ticket> getTickets(){
		List<Ticket> tickets = watchman.getTickets();
		if(tickets.equals(Collections.emptyList())) {
			throw new ParkingLotException(VEHICLES_NOT_FOUND);
		}
		return tickets;
	}
}
