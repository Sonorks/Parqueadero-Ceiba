package co.ceibaUniversity.Parqueadero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.ceibaUniversity.Parqueadero.domain.IWatchman;
import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;
import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@RestController
public class WatchmanController {
	
	public static final String VEHICLE_NOT_FOUND = "El vehiculo buscado no se encuentra en la base de datos";
	public static final String NOT_BUSINESS_DAY = "No puede ingresar porque no es un dia habil.";
	public static final String TYPE_NOT_ALLOWED = "No puede ingresar porque este tipo de vehiculo no es permitido.";
	public static final String NO_SPACE = "No puede ingresar porque no hay espacio en el parqueadero.";
	
	
	@Autowired
	private IWatchman watchman;

	@RequestMapping(value = "/parking/addVehicle", method = RequestMethod.POST)
	public boolean addVehicle(@RequestBody Vehicle vehicle) {
		if(!watchman.vehicleTypeAllowed(vehicle.getType())) {
			throw new ParkingLotException(TYPE_NOT_ALLOWED);
		}
		if(!watchman.vehicleDisponibility(vehicle.getType())) {
			throw new ParkingLotException(NO_SPACE);
		}
		if(!watchman.plateValidToday(vehicle.getPlate())) {
			throw new ParkingLotException(NOT_BUSINESS_DAY);
		}
		watchman.addVehicle(vehicle);
		return true;
	}

	public boolean vehicleParked(String plate) {
		return watchman.isVehicleParked(plate);
	}

	@RequestMapping(value ="/parking/removeVehicle/{plate}", method = RequestMethod.PUT)
	public boolean removeVehicle(@PathVariable String plate) {
		return watchman.removeVehicle(plate);
	}
	
	@RequestMapping(value = "/parking/ticket/{plate}", method = RequestMethod.GET)
	public Ticket getTicket(@PathVariable("plate") String plate) {
		Ticket ticket = watchman.getTicket(plate);
		if(ticket == null) {
			throw new ParkingLotException(VEHICLE_NOT_FOUND);
		}
		return ticket;
	}
}
