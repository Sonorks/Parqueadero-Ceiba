package co.ceibauniversity.parkinglot.domain;

import java.util.List;

import co.ceibauniversity.parkinglot.model.Ticket;
import co.ceibauniversity.parkinglot.model.Vehicle;

public interface IWatchman {

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
