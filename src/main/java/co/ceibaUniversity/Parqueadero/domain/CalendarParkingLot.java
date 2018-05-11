package co.ceibaUniversity.Parqueadero.domain;

import java.util.Calendar;

import org.springframework.stereotype.Service;


@Service
public class CalendarParkingLot {
	
	private Calendar calendar;
	
	public boolean esDiaHabil() {
		if (calendar.get(Calendar.DAY_OF_WEEK) > 2) {
			return false;
		}
		else {
			return true;
		}
	}

}
