import java.sql.Date;

public class StaffReservation {

	private int SpaceID;
	public StaffReservation(int spaceID, Date startDate, Date endDate, int staffID, double rate) {
		super();
		SpaceID = spaceID;
		StartDate = startDate;
		EndDate = endDate;
		StaffID = staffID;
		Rate = rate;
	}
	private Date StartDate;
	private Date EndDate;
	private int StaffID;
	private double Rate;
	public int getSpaceID() {
		return SpaceID;
	}
	public void setSpaceID(int spaceID) {
		SpaceID = spaceID;
	}
	public Date getStartDate() {
		return StartDate;
	}
	public void setStartDate(Date startDate) {
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
		StaffID = staffID;
	}
	public double getRate() {
		return Rate;
	}
	public void setRate(double rate) {
		Rate = rate;
	}
}
