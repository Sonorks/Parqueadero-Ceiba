package co.ceibauniversity.parkinglot.databuilder;

import co.ceibauniversity.parkinglot.model.Vehicle;

public class VehicleTestDataBuilder {
	
	public static final String TYPE = "CAR";
	public static final String PLATE = "FCL799";
	public static final int CC = 1400;
	
	private String type;
	private String plate;
	private int cc;
	
	
	public VehicleTestDataBuilder() {
		this.type = TYPE;
		this.plate = PLATE;
		this.cc = CC;
	}
	
	public VehicleTestDataBuilder usingType(String type) {
		this.type = type;
		return this;
	}
	public VehicleTestDataBuilder usingPlate(String plate) {
		this.plate = plate;
		return this;
	}
	public VehicleTestDataBuilder usingCC(int cc) {
		this.cc = cc;
		return this;
	}

	public Vehicle build() {
		return new Vehicle(this.type, this.plate, this.cc);
	}
	

}