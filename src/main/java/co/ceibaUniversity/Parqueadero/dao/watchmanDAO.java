package co.ceibaUniversity.Parqueadero.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class watchmanDAO implements IWatchmanDAO {

	@Override
	public int getCarSpaces() {
		return 5;
	}

	@Override
	public int getBikeSpaces() {
		return 5;
	}
	
	
}
