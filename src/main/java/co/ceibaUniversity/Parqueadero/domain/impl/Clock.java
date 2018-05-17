package co.ceibaUniversity.Parqueadero.domain.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import co.ceibaUniversity.Parqueadero.domain.IClock;

@Service
public class Clock implements IClock {
	
	@Override
	public int getTotalHours(Date entryDate) {
		Date exitDate = new Date();
		int totalHours = (int) ((exitDate.getTime() - entryDate.getTime()) / MILLISECS_TO_HOURS);
		totalHours = totalHours + MIN_HOUR;
		return totalHours;
	}

}
