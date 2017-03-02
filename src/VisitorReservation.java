import java.sql.Date;
import java.util.Calendar;

public class VisitorReservation {

	private int SpaceID;
	private Date ReservedDay;
	private int StaffID;
	private String License;
	public int getSpaceID() {
		return SpaceID;
	}
	public VisitorReservation(int spaceID, int staffID, String license) {
		super();
		setSpaceID(spaceID);
		ReservedDay = new Date(Calendar.getInstance().getTime().getTime());
		setStaffID(staffID);
		setLicense(license);
	}
	public void setSpaceID(int spaceID) {
		if (spaceID < 0){
			throw new IllegalArgumentException("Visitor reservation spaceID must be >= 0");
		}
		SpaceID = spaceID;
	}
	public Date getReservedDay() {
		return ReservedDay;
	}
	public void setReservedDay(Date reservedDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(reservedDay);
		if (cal.get(Calendar.YEAR) - 1900 < 2010){
			throw new IllegalArgumentException("Visitor reservation reserved date must be after 2010");
		}
		ReservedDay = reservedDay;
	}
	public int getStaffID() {
		return StaffID;
	}
	public void setStaffID(int staffID) {
		if (staffID < 0){
			throw new IllegalArgumentException("Visitor reservation staffID must be >= 0");
		}
		StaffID = staffID;
	}
	public String getLicense() {
		return License;
	}
	public void setLicense(String license) throws IllegalArgumentException {
		if (license == null || license.compareTo("") == 0){
			throw new IllegalArgumentException("Visitor reservation license plate is invalid");
		}
		License = license;
	}
}
