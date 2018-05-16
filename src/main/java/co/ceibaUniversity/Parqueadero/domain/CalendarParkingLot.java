package co.ceibaUniversity.Parqueadero.domain;

import java.util.Calendar;

import org.springframework.stereotype.Service;


@Service
public class CalendarParkingLot {
	
	private Calendar calendar = Calendar.getInstance();
	
	public boolean esDiaHabil() {
		return(!(calendar.get(Calendar.DAY_OF_WEEK) > 2));
	}

}
