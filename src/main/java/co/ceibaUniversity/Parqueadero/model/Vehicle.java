package co.ceibaUniversity.Parqueadero.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


public class Vehicle {
	
	public static final String CAR = "CAR";
	public static final String BIKE = "BIKE";
	
	private String plate;
	private String type;
	private int cc;
	
	public Vehicle(String type, String plate, int cc) {
		super();
		this.type = type;
		this.plate = plate;
		this.cc = cc;
	}
	
	public String getType() {
		return type;
	}
	public String getPlate() {
		return plate;
	}
	public int getCc() {
		return cc;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public void setCc(int cc) {
		this.cc = cc;
	}

	
}