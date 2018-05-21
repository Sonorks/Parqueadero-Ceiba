package co.ceibaUniversity.Parqueadero.domain.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import co.ceibaUniversity.Parqueadero.domain.IDateFormatter;
import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;

@Service
public class DateFormatter implements IDateFormatter {

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

	@Override
	public Date formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String stringDate = sdf.format(date);
		try {
			return sdf.parse(stringDate);
		} catch (Exception e) {
			throw new ParkingLotException(e.getMessage());
		}
	}

}
