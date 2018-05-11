package co.ceibaUniversity.Parqueadero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.ceibaUniversity.Parqueadero.dao.ITicketDAO;
import co.ceibaUniversity.Parqueadero.domain.CalendarParkingLot;
import co.ceibaUniversity.Parqueadero.domain.WatchmanDomain;
import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;
import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@RestController
public class WatchmanController {
	
	public static final String NOT_BUSINESS_DAY = "No puede ingresar porque no es un dia habil.";
	public static final String TYPE_NOT_ALLOWED = "No puede ingresar porque este tipo de vehiculo no es permitido.";
	public static final String NO_SPACE = "No puede ingresar porque no hay espacio en el parqueadero.";
	
	
	@Autowired
	private WatchmanDomain watchmanDomain;

	@RequestMapping(value = "/parking/addVehicle", method = RequestMethod.POST)
	public void addVehicle(@RequestBody Vehicle vehicle) {
		if(!watchmanDomain.vehicleTypeAllowed(vehicle.getType())) {
			throw new ParkingLotException(TYPE_NOT_ALLOWED);
		}
		if(!watchmanDomain.vehicleDisponibility(vehicle.getType())) {
			throw new ParkingLotException(NO_SPACE);
		}
		if(!watchmanDomain.plateValidToday(vehicle.getPlate())) {
			throw new ParkingLotException(NOT_BUSINESS_DAY);
		}
		watchmanDomain.addVehicle(vehicle);
	}

	public boolean vehicleParked(String plate) {
		return watchmanDomain.vehicleParked(plate);
	}

	public void removeVehicle(String plate) {
		
	}
	
	@RequestMapping("/parking/ticket/{plate}")
	public Ticket getTicket(@PathVariable("plate") String plate) {
		Ticket ticket = watchmanDomain.getTicket(plate);
		return ticket;
	}

	public int calculatePayment(String plate, String type) {
		
		if(type == "CAR") {
			return 7000;
		}
		else if(type == "BIKE") {
			return 2500;
		}
		else {
			return 0;
		}
	}

}
