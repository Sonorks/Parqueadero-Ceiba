package co.ceibaUniversity.Parqueadero.domain;

import java.util.List;

import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

public interface IWatchman {
	
	public static final String VEHICLE_ALREADY_PARKED = "El vehiculo ya se encuentra parqueado.";
	public static final double CAR_HOUR_PRICE = 1000;
	public static final double BIKE_HOUR_PRICE = 500;
	public static final double CAR_DAY_PRICE = 8000;
	public static final double BIKE_DAY_PRICE = 4000;
	public static final double EXTRA_CC_BIKE_PRICE = 2000;
	public static final int MIN_HOURS_TO_PAY_BY_DAY = 9;
	public static final int MAX_HOURS_TO_PAY_BY_DAY = 24;

	String getType(String plate);

	boolean vehicleTypeAllowed(String type);

	boolean plateValidToday(String plate);

	boolean vehicleDisponibility(String type);

	boolean addVehicle(Vehicle vehicle);

	boolean isVehicleParked(String plate);

	List<Ticket> getTickets();

	double calculatePayment(String type, int cc, int totalHours);

	boolean removeVehicle(String plate);

	Ticket getTicket(String plate);

}
