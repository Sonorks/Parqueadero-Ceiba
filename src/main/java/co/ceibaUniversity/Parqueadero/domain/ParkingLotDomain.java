package co.ceibaUniversity.Parqueadero.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ceibaUniversity.Parqueadero.dao.IParkingLotDAO;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@Service
public class ParkingLotDomain {
	
	@Autowired
	IParkingLotDAO parkingLotDAO;

	public int getHours(String plate) {
		
		return 0;
	}

	public String getType(String plate) {
		String type = parkingLotDAO.getVehicleType(plate);
		return type;
	}

	public boolean vehicleTypeAllowed(String type) {
		return true;
	}

	public boolean plateValidToday(String plate) {
		return true;
	}

	public boolean vehicleDisponibility(String type) {
		return true;
	}

	public boolean addVehicle(Vehicle vehicle) {
		return true;
	}

	public boolean vehicleParked(String plate) {
		return true;
	}

	public Vehicle getVehicle(String plate) {
		Vehicle vehicle = new Vehicle("CAR","FCL799",1400,new Date());
		return vehicle;
	}
}