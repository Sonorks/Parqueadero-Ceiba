package co.ceibaUniversity.Parqueadero.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Vehicle {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String plate;
	private String type;
	private int cc;
	private Date entryDate;
	private Date exitDate;
	
	public Vehicle(String type, String plate, int cc, Date entryDate) {
		super();
		this.type = type;
		this.plate = plate;
		this.cc = cc;
		this.entryDate = entryDate;
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

	public Date getEntryDate() {
		return entryDate;
	}

	public Date getExitDate() {
		return exitDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}
	
}