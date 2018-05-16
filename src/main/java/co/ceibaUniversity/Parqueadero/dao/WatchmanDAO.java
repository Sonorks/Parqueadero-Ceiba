package co.ceibaUniversity.Parqueadero.dao;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;
import co.ceibaUniversity.Parqueadero.model.Ticket;

@Transactional
@Repository
public class WatchmanDAO implements IWatchmanDAO {

	private static final String COUNT_BIKESLOTS_QUERY = "SELECT COUNT(*) - COUNT(exit_date) FROM Ticket as ticket WHERE ticket.type = 'BIKE'";
	private static final String COUNT_CARSLOTS_QUERY = "SELECT COUNT(*) - COUNT(exit_date) FROM Ticket as ticket WHERE ticket.type = 'CAR'";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public int getCarSpaces() {
		return countVehicleSpaces(COUNT_CARSLOTS_QUERY);
	}

	@Override
	public int getBikeSpaces() {
		return countVehicleSpaces(COUNT_BIKESLOTS_QUERY);
	}
	
	private int countVehicleSpaces(String query) {
		try {
			BigInteger countResult;
			countResult = (BigInteger) entityManager
					.createNativeQuery(query).getSingleResult();
			return countResult.intValue();
		}catch(Exception e) {
			System.out.println(e);
			throw new ParkingLotException(e.getMessage());
		}
	}

	
	
	
}
