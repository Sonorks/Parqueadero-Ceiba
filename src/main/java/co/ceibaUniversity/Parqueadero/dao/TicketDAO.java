package co.ceibaUniversity.Parqueadero.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;
import co.ceibaUniversity.Parqueadero.model.Ticket;

@Transactional
@Repository
public class TicketDAO implements ITicketDAO {



	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public String getVehicleType(String plate) {
		return getTicket(plate).getType();
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
	public Ticket getTicket(String plate) {
		try {
			Ticket ticket = (Ticket) entityManager.createQuery(GET_TICKET_BY_PLATE)
					.setParameter(GET_TICKET_QUERYPARAM_PLATE, plate).getSingleResult();
			return ticket;
		}catch(Exception e) {
			return null;
		}
	}
	

	@Override
	public void removeVehicle(String plate, int totalHours, int totalPrice, Date date) {
		Ticket ticket = getTicket(plate);
		ticket.setTotalHours(totalHours);
		ticket.setTotalPrice(totalPrice);
		ticket.setExitDate(date);
		entityManager.flush();
	}


	
}