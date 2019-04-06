package group3.finalproj.car;

public enum CarType {
	CargoVan("Cargo Van"),
	Convertible("Convertible"),
	Coupe("Coupe"),
	CrewCabPickup("Crew Cab Pickup"),
	ExtendedCabPickup("Extended Cab Pickup"),
	Hatchback("Hatchback"),
	Minivan("Minivan"),
	PassengerVan("Passenger Van"),
	RegularCabPickup("Regular Cab Pickup"),
	Sedan("Sedan"),
	SUV("SUV"),
	Wagon("Wagon");
	

	private String carType;
	
	/**
	 * Retrieves the string value of a category
	 * @return string value
	 */
	@Override
	public String toString() {
		return this.carType;
	}
	
	/**
	 * Constructor for a carType
	 * @param category String of the category name
	 */
	CarType(String carType) {
		this.carType = carType;
	}
	
}