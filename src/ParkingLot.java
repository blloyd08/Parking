
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
		if (lotName == null){
			throw new IllegalArgumentException("Parking lot name can't be null");
		}
		LotName = lotName;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		if (location == null){
			throw new IllegalArgumentException("Parking lot location can't be null");
		}
		Location = location;
	}
	public int getCapacity() {
		return Capacity;
	}
	public void setCapacity(int capacity) {
		if (capacity < 0 ){
			throw new IllegalArgumentException("Parking lot capacity can't be a negative number");
		}
		Capacity = capacity;
	}
	public int getFloors() {
		return Floors;
	}
	public void setFloors(int floors) {
		if (floors < 0 ){
			throw new IllegalArgumentException("Parking lot floors can't be a negative number");
		}
		Floors = floors;
	}
}
