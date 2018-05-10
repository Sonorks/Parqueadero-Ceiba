package co.ceibaUniversity.Parqueadero.dao;

import org.springframework.stereotype.Service;

@Service
public interface IParkingLotDAO {

	String getVehicleType(String plate);
	
}
