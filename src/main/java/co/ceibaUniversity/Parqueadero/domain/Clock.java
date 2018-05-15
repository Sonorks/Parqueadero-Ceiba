package co.ceibaUniversity.Parqueadero.domain;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class Clock {

	final int MILLISECS_TO_HOURS = 1000 * 60 * 60;
	final int MIN_HOUR = 1;
	
	public int getTotalHours(Date entryDate) {
		Date exitDate = new Date();
		int totalHours = (int) ((exitDate.getTime() - entryDate.getTime()) / MILLISECS_TO_HOURS);
		if(totalHours == 0) {
			return MIN_HOUR;
		}
		return totalHours;
	}

}
