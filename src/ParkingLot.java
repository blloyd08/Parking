
public class ParkingLot {

	private String LotName;
	private String Location;
	private int Capacity;
	public ParkingLot(String lotName, String location, int capacity, int floors) {
		super();
		LotName = lotName;
		Location = location;
		Capacity = capacity;
		Floors = floors;
	}
	private int Floors;
	public String getLotName() {
		return LotName;
	}
	public void setLotName(String lotName) {
		LotName = lotName;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public int getCapacity() {
		return Capacity;
	}
	public void setCapacity(int capacity) {
		Capacity = capacity;
	}
	public int getFloors() {
		return Floors;
	}
	public void setFloors(int floors) {
		Floors = floors;
	}
}
