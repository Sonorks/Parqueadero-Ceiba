package co.ceibaUniversity.Parqueadero.dao;

import java.util.Date;

import org.springframework.stereotype.Service;

import co.ceibaUniversity.Parqueadero.model.Ticket;

@Service
public interface ITicketDAO {
	
	public static final String SAVE_TICKET_ERROR = "Error guardando ticket";
	
	public static final String GET_TICKET_BY_PLATE = "FROM Ticket as ticket WHERE ticket.plate = :plate";
	
	public static final String GET_TICKET_QUERYPARAM_PLATE = "plate";
	
	
	String getVehicleType(String plate);
	void addTicket(Ticket ticket);
	Ticket getTicket(String plate);
	void removeVehicle(String plate, int totalHours, int totalPrice, Date date);
	
}
