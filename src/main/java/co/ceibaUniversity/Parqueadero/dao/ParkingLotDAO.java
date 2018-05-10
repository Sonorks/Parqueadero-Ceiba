package co.ceibaUniversity.Parqueadero.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@Transactional
@Repository
public class ParkingLotDAO implements IParkingLotDAO {

	private static final String GET_VEHICLE_BY_TYPE_QUERY_ERROR = "Error obteniendo tipo de vehiculo.";
	private static final String GET_VEHICLE_BY_TYPE_QUERYPARAM = "plate";
	private static final String GET_VEHICLE_BY_TYPE_QUERY = "FROM Vehicle WHERE Vehicle.plate = :plate";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public String getVehicleType(String plate) {
		try {
			Vehicle vehicle = (Vehicle) entityManager.createQuery(GET_VEHICLE_BY_TYPE_QUERY)
					.setParameter(GET_VEHICLE_BY_TYPE_QUERYPARAM, plate).getSingleResult();
			return vehicle.getType();
		} catch (ParkingLotException e) {
			throw new ParkingLotException(GET_VEHICLE_BY_TYPE_QUERY_ERROR);
		}
	}
	
}