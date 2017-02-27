
public class Staff {

	private int StaffID;
	private String FirstName;
	private String LastName;
	private String Telephone;
	private String Extention;
	private String License;
	public Staff(int staffID, String firstName, String lastName, String telephone, String extention) {
		super();
		StaffID = staffID;
		FirstName = firstName;
		LastName = lastName;
		Telephone = telephone;
		Extention = extention;
	}
	public int getStaffID() {
		return StaffID;
	}
	public void setStaffID(int staffID) {
		StaffID = staffID;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getTelephone() {
		return Telephone;
	}
	public void setTelephone(String telephone) {
		Telephone = telephone;
	}
	public String getExtention() {
		return Extention;
	}
	public void setExtention(String extention) {
		Extention = extention;
	}
	public String getLicense() {
		return License;
	}
	public void setLicense(String license) {
		License = license;
	}
}
