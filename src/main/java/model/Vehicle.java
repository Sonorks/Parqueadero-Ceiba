package model;

public class Vehicle {
	
	private String type;
	private String plate;
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
