package co.ceibauniversity.parkinglot.dao;

import org.springframework.stereotype.Service;

@Service
public interface IVehicleRepository {

	int getCarSpaces();
	int getBikeSpaces();
	
}
