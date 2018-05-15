package co.ceibaUniversity.Parqueadero.dao;

import org.springframework.stereotype.Service;

import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@Service
public interface ITicketDAO {
	
	String getVehicleType(String plate);
	void addTicket(Ticket ticket);
	Ticket getTicket(String plate);
	boolean isVehicleParked(String plate);
	
}
