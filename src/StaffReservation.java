import java.sql.Date;
import java.util.Calendar;

public class StaffReservation {

	private int SpaceID;
	public StaffReservation(int spaceID, Date endDate, int staffID, double rate) {
		super();
		setSpaceID(spaceID);
		StartDate = new Date(Calendar.getInstance().getTime().getTime());
		setEndDate(endDate);
		setStaffID(staffID);
		setRate(rate);
	}
	private Date StartDate;
	private Date EndDate;
	private int StaffID;
	private double Rate;
	public int getSpaceID() {
		return SpaceID;
	}
	public void setSpaceID(int spaceID) {
		if (spaceID < 0){
			throw new IllegalArgumentException("Staff Reservation spaceInt "
					+ "must be greater than 0");
		}
		SpaceID = spaceID;
	}
	public Date getStartDate() {
		return StartDate;
	}
	public void setStartDate(Date startDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		if (cal.get(Calendar.YEAR) - 1900 < 2010){
			throw new IllegalArgumentException("Staff reservation start date must be after 2010");
		}
		StartDate = startDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public int getStaffID() {
		return StaffID;
	}
	public void setStaffID(int staffID) {
		if (staffID < 0){
			throw new IllegalArgumentException("Staff reservation staff id must be >= 0");
		}
		StaffID = staffID;
	}
	public double getRate() {
		return Rate;
	}
	public void setRate(double rate) {
		if (rate < 0){
			throw new IllegalArgumentException("Staff reservation rate must be >= 0");
		}
		if (rate >= 1000.0){
			throw new IllegalArgumentException("Staff reservation rate must be less than 1000");
		}
		Rate = rate;
	}
}
