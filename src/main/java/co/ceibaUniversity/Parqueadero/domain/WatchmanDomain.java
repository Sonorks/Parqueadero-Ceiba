package co.ceibaUniversity.Parqueadero.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ceibaUniversity.Parqueadero.dao.ITicketDAO;
import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@Service
public class WatchmanDomain {

	ITicketDAO ticketDAO;
	CalendarParkingLot calendario;

	final private int MAX_CAR = 20;
	final private int MAX_BIKE = 10;

	@Autowired
	public WatchmanDomain(ITicketDAO ticketDAO, CalendarParkingLot calendario) {
		this.ticketDAO = ticketDAO;
		this.calendario = calendario;
	}
	
	public String getType(String plate) {
		String type = ticketDAO.getVehicleType(plate);
		return type;
	}

	public boolean vehicleTypeAllowed(String type) {
		return (type.equals("CAR") || type.equals("BIKE"));
	}

	public boolean plateValidToday(String plate) {
		if(plate.startsWith("A")) {
			return calendario.esDiaHabil();
		} else {
			return true;
		}
	}
	
	public boolean vehicleDisponibility(String type) {
		if (type.equals("CAR")) {
			int carSpacesUsed = ticketDAO.getCarSpaces();
			return (MAX_CAR - carSpacesUsed) > 0;
		} else if (type.equals("BIKE")) {
			int bikeSpacesUsed = ticketDAO.getBikeSpaces();
			return (MAX_BIKE - bikeSpacesUsed) > 0;
		} else {
			return false;
		}
	}

	public void addVehicle(Vehicle vehicle) {
		Ticket ticket = new Ticket(vehicle.getType(), vehicle.getPlate(), vehicle.getCc(), new Date());
		ticketDAO.addTicket(ticket);
	}

	public boolean vehicleParked(String plate) {
		Ticket ticket = getTicket(plate);
		if (ticket.getExitDate().equals(null)) {
			return true;
		}
		return false;
	}

	public Ticket getTicket(String plate) {
		return ticketDAO.getTicket(plate);
	}
	
}