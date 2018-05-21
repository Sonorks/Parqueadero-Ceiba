package co.ceibaUniversity.Parqueadero.domain.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ceibaUniversity.Parqueadero.dao.ITicketDAO;
import co.ceibaUniversity.Parqueadero.dao.IWatchmanDAO;
import co.ceibaUniversity.Parqueadero.dao.TicketDAO;
import co.ceibaUniversity.Parqueadero.dao.WatchmanDAO;
import co.ceibaUniversity.Parqueadero.domain.ICalculator;
import co.ceibaUniversity.Parqueadero.domain.ICalendarParkingLot;
import co.ceibaUniversity.Parqueadero.domain.IClock;
import co.ceibaUniversity.Parqueadero.domain.IDateFormatter;
import co.ceibaUniversity.Parqueadero.domain.IWatchman;
import co.ceibaUniversity.Parqueadero.model.Ticket;
import co.ceibaUniversity.Parqueadero.model.Vehicle;

@Service
public class Watchman implements IWatchman {


	private ITicketDAO ticketDAO;
	private IWatchmanDAO watchmanDAO;
	private ICalendarParkingLot calendario;
	private IClock clock;
	private IDateFormatter dateFormatter;
	private ICalculator calculator;

	final private int MAX_CAR = 20;
	final private int MAX_BIKE = 10;


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
		String type = ticketDAO.getVehicleType(plate);
		return type;
	}

	@Override
	public boolean vehicleTypeAllowed(String type) {
		return (type.equals(Vehicle.CAR) || type.equals(Vehicle.BIKE));
	}

	@Override
	public boolean plateValidToday(String plate) {
		if(plate.startsWith("A")) {
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
			ticket.setExitDate(dateFormatter.formatDate(ticket.getExitDate()));
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
			System.out.println("En cobro por moto: "+totalPrice);
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