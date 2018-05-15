package co.ceibaUniversity.Parqueadero.domain;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class Clock {

	final int MILLISECS_TO_HOURS = 1000 * 60 * 60;
	
	public int getTotalHours(Date entryDate) {
		Date exitDate = new Date();
		int totalHours = (int) ((exitDate.getTime() - entryDate.getTime()) / MILLISECS_TO_HOURS);
		return totalHours;
	}

}
