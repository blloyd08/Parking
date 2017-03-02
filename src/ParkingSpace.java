
public class ParkingSpace {

	public ParkingSpace(String lotName, String type) {
		super();
		setLotName(lotName);
		setType(type);
	}
	private int SpaceID;
	private String LotName;
	private String Type;
	
	public int getSpaceID() {
		return SpaceID;
	}
	public void setSpaceID(int spaceID) {
		if (spaceID < 0){
			throw new IllegalArgumentException("Parking space ID must be >= 0");
		}
		SpaceID = spaceID;
	}
	public String getLotName() {
		return LotName;
	}
	public void setLotName(String lotName) {
		if (lotName == null){
			throw new IllegalArgumentException("Parking space lot name can't be null");
		}
		LotName = lotName;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		if (type == null){
			throw new IllegalArgumentException("Parking space type can't be null");
		}
		Type = type;
	}	
}
