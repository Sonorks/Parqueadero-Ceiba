package co.ceibaUniversity.Parqueadero.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;
import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@Transactional
@Repository
public class TicketDAO implements ITicketDAO {

	
	private static final String GET_TICKET_BY_TYPE_QUERY_ERROR = "Error obteniendo tipo de vehiculo.";
	private static final String GET_TICKET_ERROR = "Error obteniendo ticket";
	private static final String SAVE_TICKET_ERROR = "Error guardando ticket";
	
	private static final String GET_TICKET_BY_PLATE = "FROM Ticket as ticket WHERE ticket.plate = :plate";
	private static final String GET_TICKET_BY_PLATE_QUERY = "FROM Ticket WHERE Ticket.plate = :plate";
	private static final String GET_ALL_TICKETS = "FROM Ticket";
	
	private static final String GET_TICKET_QUERYPARAM_PLATE = "plate";


	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public String getVehicleType(String plate) {
		try {
			Ticket ticket = (Ticket) entityManager.createQuery(GET_TICKET_BY_PLATE_QUERY)
					.setParameter(GET_TICKET_QUERYPARAM_PLATE, plate).getSingleResult();
			return ticket.getType();
		} catch (Exception e) {
			throw new ParkingLotException(GET_TICKET_BY_TYPE_QUERY_ERROR);
		}
	}
	
	@Override
	public void addTicket(Ticket ticket) {
		try {
			entityManager.persist(ticket);
		} catch (Exception e) {
			throw new ParkingLotException(SAVE_TICKET_ERROR);
		}
	}

	@Override
	public int getCarSpaces() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public int getBikeSpaces() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Ticket getTicket(String plate) {
		try {
			Ticket ticket = (Ticket) entityManager.createQuery(GET_TICKET_BY_PLATE)
					.setParameter(GET_TICKET_QUERYPARAM_PLATE, plate).getSingleResult();
			return ticket;
		} catch (Exception e) {
			throw new ParkingLotException("Error obteniendo ticket de la BD");
		}
	}
	
}