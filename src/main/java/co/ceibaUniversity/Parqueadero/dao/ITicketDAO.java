package co.ceibaUniversity.Parqueadero.dao;

import java.util.Date;

import org.springframework.stereotype.Service;

import co.ceibaUniversity.Parqueadero.model.Ticket;

@Service
public interface ITicketDAO {
	
	String getVehicleType(String plate);
	void addTicket(Ticket ticket);
	Ticket getTicket(String plate);
	void removeVehicle(String plate, int totalHours, int totalPrice, Date date);
	
}
