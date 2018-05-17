package co.ceibaUniversity.Parqueadero.domain;

import java.util.Date;

public interface IClock {
	
	public final int MILLISECS_TO_HOURS = 1000 * 60 * 60;
	public final int MIN_HOUR = 1;

	int getTotalHours(Date entryDate);

}
