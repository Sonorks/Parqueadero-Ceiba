package co.ceibaUniversity.Parqueadero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.ceibaUniversity.Parqueadero.domain.ParkingLotDomain;
import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@RestController
public class ParkingLotController {
	
	public static final String NOT_BUSINESS_DAY = "No puede ingresar porque no es un dia habil.";
	public static final String TYPE_NOT_ALLOWED = "No puede ingresar porque este tipo de vehiculo no es permitido.";
	public static final String NO_SPACE = "No puede ingresar porque no hay espacio en el parqueadero.";
	
	@Autowired
	private ParkingLotDomain parkingLotDomain;
	

//	public ParkingLotController(ParkingLotDomain parkingLotDomain) {
//		this.parkingLotDomain = parkingLotDomain;
//	}

	@RequestMapping(value = "/parking/addVehicle", method = RequestMethod.POST)
	public void addVehicle(@RequestBody Vehicle vehicle) {
		if(!parkingLotDomain.vehicleTypeAllowed(vehicle.getType())) {
			throw new ParkingLotException(TYPE_NOT_ALLOWED);
		}
		if(!parkingLotDomain.vehicleDisponibility(vehicle.getType())) {
			throw new ParkingLotException(NO_SPACE);
		}
		if(!parkingLotDomain.plateValidToday(vehicle.getPlate())) {
			throw new ParkingLotException(NOT_BUSINESS_DAY);
		}
		parkingLotDomain.addVehicle(vehicle);
	}

	public boolean vehicleParked(String plate) {
		return parkingLotDomain.vehicleParked(plate);
	}

	public void removeVehicle(String plate) {
		// TODO Auto-generated method stub
		
	}
	
	@RequestMapping("/parking/vehicle/{plate}")
	public Vehicle getVehiculo(@PathVariable("plate") String plate) {
		Vehicle vehicle = parkingLotDomain.getVehicle(plate);
		return vehicle;
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
