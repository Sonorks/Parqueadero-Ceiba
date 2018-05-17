package co.ceibaUniversity.Parqueadero.dao;

import org.springframework.stereotype.Service;

@Service
public interface IWatchmanDAO {
	
	public static final String COUNT_BIKESLOTS_QUERY = "SELECT COUNT(*) - COUNT(exit_date) FROM Ticket as ticket WHERE ticket.type = 'BIKE'";
	public static final String COUNT_CARSLOTS_QUERY = "SELECT COUNT(*) - COUNT(exit_date) FROM Ticket as ticket WHERE ticket.type = 'CAR'";
	

	int getCarSpaces();
	int getBikeSpaces();
	
}
