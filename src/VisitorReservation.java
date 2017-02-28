import java.util.Date;

public class VisitorReservation {

	private int SpaceID;
	private Date ReservedDay;
	private int StaffID;
	private String License;
	public int getSpaceID() {
		return SpaceID;
	}
	public VisitorReservation(int spaceID, Date reservedDay, int staffID, String license) {
		super();
		SpaceID = spaceID;
		ReservedDay = reservedDay;
		StaffID = staffID;
		License = license;
	}
	public void setSpaceID(int spaceID) {
		SpaceID = spaceID;
	}
	public Date getReservedDay() {
		return ReservedDay;
	}
	public void setReservedDay(Date reservedDay) {
		ReservedDay = reservedDay;
	}
	public int getStaffID() {
		return StaffID;
	}
	public void setStaffID(int staffID) {
		StaffID = staffID;
	}
	public String getLicense() {
		return License;
	}
	public void setLicense(String license) {
		License = license;
	}
}
