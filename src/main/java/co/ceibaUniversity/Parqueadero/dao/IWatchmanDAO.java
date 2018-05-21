package co.ceibaUniversity.Parqueadero.dao;

import org.springframework.stereotype.Service;

@Service
public interface IWatchmanDAO {

	int getCarSpaces();
	int getBikeSpaces();
	
}
