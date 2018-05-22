package co.ceibauniversity.parkinglot.domain.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ceibauniversity.parkinglot.dao.ITicketDAO;
import co.ceibauniversity.parkinglot.dao.IWatchmanDAO;
import co.ceibauniversity.parkinglot.dao.TicketDAO;
import co.ceibauniversity.parkinglot.dao.WatchmanDAO;
import co.ceibauniversity.parkinglot.domain.ICalculator;
import co.ceibauniversity.parkinglot.domain.ICalendarParkingLot;
import co.ceibauniversity.parkinglot.domain.IClock;
import co.ceibauniversity.parkinglot.domain.IDateFormatter;
import co.ceibauniversity.parkinglot.domain.IWatchman;
import co.ceibauniversity.parkinglot.model.Ticket;
import co.ceibauniversity.parkinglot.model.Vehicle;

@Service
public class Watchman implements IWatchman {
	
	public static final double CAR_HOUR_PRICE = 1000;
	public static final double BIKE_HOUR_PRICE = 500;
	public static final double CAR_DAY_PRICE = 8000;
	public static final double BIKE_DAY_PRICE = 4000;
	public static final double EXTRA_CC_BIKE_PRICE = 2000;
	public static final int MIN_HOURS_TO_PAY_BY_DAY = 9;
	public static final int MAX_HOURS_TO_PAY_BY_DAY = 24;
	public static final int MAX_CAR = 20;
	public static final int MAX_BIKE = 10;

	private ITicketDAO ticketDAO;
	private IWatchmanDAO watchmanDAO;
	private ICalendarParkingLot calendario;
	private IClock clock;
	private IDateFormatter dateFormatter;
	private ICalculator calculator;




	@Autowired
	public Watchman(TicketDAO ticketDAO, WatchmanDAO watchmanDAO, CalendarParkingLot calendario, 
					Clock clock, DateFormatter dateFormatter, Calculator calculator) {
		this.watchmanDAO = watchmanDAO;
		this.ticketDAO = ticketDAO;
		this.calendario = calendario;
		this.clock = clock;
		this.dateFormatter = dateFormatter;
		this.calculator = calculator;
	}
	
	@Override
	public String getType(String plate) {
		return ticketDAO.getVehicleType(plate);
	}

	@Override
	public boolean vehicleTypeAllowed(String type) {
		return (type.equals(Vehicle.CAR) || type.equals(Vehicle.BIKE));
	}

	@Override
	public boolean plateValidToday(String plate) {
		if(plate.startsWith("A") || plate.startsWith("a")) {
			return calendario.esDiaHabil();
		} else {
			return true;
		}
	}
	
	@Override
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

	@Override
	public boolean addVehicle(Vehicle vehicle) {
		Ticket ticket = new Ticket(vehicle.getType(), vehicle.getPlate(), vehicle.getCc(), new Date());
		return ticketDAO.addTicket(ticket);
	}

	@Override
	public boolean isVehicleParked(String plate) {
		Ticket ticket = ticketDAO.getTicket(plate);
		if(ticket == null) {
			return false;
		}
		return (ticket.getExitDate() == null);
	}

	@Override
	public List<Ticket> getTickets() {
		List<Ticket> tickets= ticketDAO.getTickets();
		for (Ticket ticket : tickets) {
			ticket.setEntryDate(dateFormatter.formatDate(ticket.getEntryDate()));
		}
		return ticketDAO.getTickets();
	}
	
	@Override
	public Ticket getTicket(String plate) {
		Ticket ticket =  ticketDAO.getTicket(plate);
		ticket.setEntryDate(dateFormatter.formatDate(ticket.getEntryDate()));
		ticket.setExitDate(dateFormatter.formatDate(ticket.getExitDate()));
		return ticket;
	}

	@Override
	public double calculatePayment(String type, int cc, int totalHours) {
		double totalPrice = 0;
		if (type.equals(Vehicle.CAR)) {
			totalPrice = calculator.getTotalPrice(totalHours,CAR_DAY_PRICE,CAR_HOUR_PRICE);
		}
		else if (type.equals(Vehicle.BIKE)) {
			totalPrice = calculator.getTotalPrice(totalHours,BIKE_DAY_PRICE,BIKE_HOUR_PRICE);
			if(cc > 500) {
				totalPrice += 2000;
			}
		}
		return totalPrice;
	}


	@Override
	public boolean removeVehicle(String plate) {
		Ticket ticket = ticketDAO.getTicket(plate);
		int totalHours = clock.getTotalHours(ticket.getEntryDate());
		int totalPrice = (int) calculatePayment(ticket.getType(),ticket.getCc(),totalHours);
		ticketDAO.removeVehicle(plate,totalHours,totalPrice, new Date());
		return true;
	}
	
	
}