package co.ceibaUniversity.Parqueadero.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ceibaUniversity.Parqueadero.dao.ITicketDAO;
import co.ceibaUniversity.Parqueadero.dao.IWatchmanDAO;
import co.ceibaUniversity.Parqueadero.exception.ParkingLotException;
import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@Service
public class Watchman {
	
	private static final String VEHICLE_ALREADY_PARKED = "El vehiculo ya se encuentra parqueado.";
	public static final double CAR_HOUR_PRICE = 1000;
	public static final double BIKE_HOUR_PRICE = 500;
	public static final double CAR_DAY_PRICE = 8000;
	public static final double BIKE_DAY_PRICE = 4000;
	public static final double EXTRA_CC_BIKE_PRICE = 2000;
	public static final int MIN_HOURS_TO_PAY_BY_DAY = 9;
	public static final int MAX_HOURS_TO_PAY_BY_DAY = 24;

	private ITicketDAO ticketDAO;
	private IWatchmanDAO watchmanDAO;
	private CalendarParkingLot calendario;
	private Clock clock;

	final private int MAX_CAR = 20;
	final private int MAX_BIKE = 10;


	@Autowired
	public Watchman(ITicketDAO ticketDAO, IWatchmanDAO watchmanDAO, CalendarParkingLot calendario, Clock clock) {
		this.watchmanDAO = watchmanDAO;
		this.ticketDAO = ticketDAO;
		this.calendario = calendario;
		this.clock = clock;
	}
	
	public String getType(String plate) {
		String type = ticketDAO.getVehicleType(plate);
		return type;
	}

	public boolean vehicleTypeAllowed(String type) {
		return (type.equals(Vehicle.CAR) || type.equals(Vehicle.BIKE));
	}

	public boolean plateValidToday(String plate) {
		if(plate.startsWith("A")) {
			return calendario.esDiaHabil();
		} else {
			return true;
		}
	}
	
	public boolean vehicleDisponibility(String type) {
		if (type.equals(Vehicle.CAR)) {
			int carSpacesUsed = watchmanDAO.getCarSpaces();
			return (MAX_CAR - carSpacesUsed) > 0;
		} else if (type.equals(Vehicle.BIKE)) {
			int bikeSpacesUsed = watchmanDAO.getBikeSpaces();
			return (MAX_BIKE - bikeSpacesUsed) > 0;
		} else {
			return false;
		}
	}

	public void addVehicle(Vehicle vehicle) {
		Ticket ticket = new Ticket(vehicle.getType(), vehicle.getPlate(), vehicle.getCc(), new Date());
		if(ticketDAO.isVehicleParked(vehicle.getPlate())) {
			throw new ParkingLotException(VEHICLE_ALREADY_PARKED);
		}
		ticketDAO.addTicket(ticket);
	}

	public boolean vehicleParked(String plate) {
		Ticket ticket = getTicket(plate);
		if (ticket.getExitDate().equals(null)) {
			return true;
		}
		return false;
	}

	public Ticket getTicket(String plate) {
		return ticketDAO.getTicket(plate);
	}

	public double calculatePayment(String type, int cc, Date entryDate) {
		int totalHours = clock.getTotalHours(entryDate);
		double totalPrice = 0;
		if (type == Vehicle.CAR) {
			if(totalHours >= MIN_HOURS_TO_PAY_BY_DAY && totalHours <= MAX_HOURS_TO_PAY_BY_DAY) {
				totalPrice = CAR_DAY_PRICE;
			} else if (totalHours > MAX_HOURS_TO_PAY_BY_DAY){
				int extraHours = totalHours % MAX_HOURS_TO_PAY_BY_DAY;
				totalPrice = (CAR_HOUR_PRICE*extraHours + CAR_DAY_PRICE*(totalHours/MAX_HOURS_TO_PAY_BY_DAY));
			} else {
				totalPrice = totalHours * CAR_HOUR_PRICE;
			}
		}
		else if (type == Vehicle.BIKE) {
			if(totalHours >= MIN_HOURS_TO_PAY_BY_DAY && totalHours <= MAX_HOURS_TO_PAY_BY_DAY) {
				totalPrice = BIKE_DAY_PRICE;
			} else if (totalHours > MAX_HOURS_TO_PAY_BY_DAY) {
				int extraHours = totalHours % MAX_HOURS_TO_PAY_BY_DAY;
				totalPrice = (BIKE_HOUR_PRICE*extraHours + BIKE_DAY_PRICE*(totalHours/MAX_HOURS_TO_PAY_BY_DAY));
			} else {
				totalPrice = totalHours * BIKE_HOUR_PRICE;
			}
			if(cc > 500) {
				totalPrice += 2000;
			}
		}
		return totalPrice;
	}
	
	
}