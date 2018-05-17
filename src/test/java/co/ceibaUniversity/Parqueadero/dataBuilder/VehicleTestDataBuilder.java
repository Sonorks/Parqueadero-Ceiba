package co.ceibaUniversity.Parqueadero.dataBuilder;

import co.ceibaUniversity.Parqueadero.model.Vehicle;

public class VehicleTestDataBuilder {
	
	private static final String TYPE = "CAR";
	private static final String PLATE = "FCL799";
	private static final int CC = 1400;
	
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