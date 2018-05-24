package co.ceibauniversity.parkinglot.domain.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import co.ceibauniversity.parkinglot.domain.IDateFormatter;
import co.ceibauniversity.parkinglot.exception.ParkingLotException;

@Service
public class DateFormatter implements IDateFormatter {

	public static final String DATE_INVALID = "La fecha ingresada no tiene un formato valido";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

	@Override
	public Date formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String stringDate = sdf.format(date);
		try {
			return sdf.parse(stringDate);
		} catch (Exception e) {
			throw new ParkingLotException(DATE_INVALID);
		}
	}

}
