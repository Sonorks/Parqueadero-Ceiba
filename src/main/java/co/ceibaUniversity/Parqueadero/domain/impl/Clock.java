package co.ceibaUniversity.Parqueadero.domain.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import co.ceibaUniversity.Parqueadero.domain.IClock;

@Service
public class Clock implements IClock {
	
	public static final int MILLISECS_TO_HOURS = 1000 * 60 * 60;
	public static final int MIN_HOUR = 1;
	
	@Override
	public int getTotalHours(Date entryDate) {
		Date exitDate = new Date();
		int totalHours = (int) ((exitDate.getTime() - entryDate.getTime()) / MILLISECS_TO_HOURS);
		totalHours = totalHours + MIN_HOUR;
		return totalHours;
	}

}
