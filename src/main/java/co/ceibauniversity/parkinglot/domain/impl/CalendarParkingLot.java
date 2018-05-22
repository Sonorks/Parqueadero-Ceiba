package co.ceibauniversity.parkinglot.domain.impl;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import co.ceibauniversity.parkinglot.domain.ICalendarParkingLot;


@Service
public class CalendarParkingLot implements ICalendarParkingLot {
	
	private Calendar calendar = Calendar.getInstance();
	
	@Override
	public boolean esDiaHabil() {
		return(!(calendar.get(Calendar.DAY_OF_WEEK) > 2));
	}

}
