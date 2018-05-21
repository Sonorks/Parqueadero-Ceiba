package co.ceibaUniversity.Parqueadero.model;

public class Vehicle {
	
	public static final String CAR = "CAR";
	public static final String BIKE = "BIKE";
	
	private String plate;
	private String type;
	private int cc;
	
	public Vehicle() {
		
	}
	
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
	
}