package controllers;

import org.springframework.beans.factory.annotation.Autowired;

import domain.ParkingLotDomain;

public class ParkingLotController {
	
	private ParkingLotDomain parkingLotDomain;

	public ParkingLotController(ParkingLotDomain parkingLotDomain2) {
		this.parkingLotDomain = parkingLotDomain2;
	}

	public void addVehicle(String type, String plate, int cc) {
		if(!parkingLotDomain.vehicleTypeAllowed(type)) {
			
		}
		if(!parkingLotDomain.vehicleDisponibility(type)) {
			
		}
		if(!parkingLotDomain.plateValidToday(plate)) {
			
		}
		parkingLotDomain.addVehicle(type, plate, cc);
	}

	public boolean vehicleParked(String plate) {
		return parkingLotDomain.vehicleParked(plate);
	}

	public void removeVehicle(String plate) {
		// TODO Auto-generated method stub
		
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
