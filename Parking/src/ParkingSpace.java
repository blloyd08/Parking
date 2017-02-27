
public class ParkingSpace {

	public ParkingSpace(int spaceID, String lotName, String type) {
		super();
		SpaceID = spaceID;
		LotName = lotName;
		Type = type;
	}
	private int SpaceID;
	private String LotName;
	private String Type;
	public int getSpaceID() {
		return SpaceID;
	}
	public void setSpaceID(int spaceID) {
		SpaceID = spaceID;
	}
	public String getLotName() {
		return LotName;
	}
	public void setLotName(String lotName) {
		LotName = lotName;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}	
}
