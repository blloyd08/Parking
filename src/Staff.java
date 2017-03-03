
public class Staff {

	private int StaffID;
	private String FirstName;
	private String LastName;
	private String Telephone;
	private String Extention;
	private String License;
	public Staff(int staffID, String firstName, String lastName, String telephone, String extention, String license) {
		super();
		setStaffID(staffID);
		setFirstName(firstName);
		setLastName(lastName);
		setTelephone(telephone);
		setExtention(extention);
		setLicense(license);
	}
	public int getStaffID() {
		return StaffID;
	}
	public void setStaffID(int staffID) {
		if (staffID >= 0){
			StaffID = staffID;
		} else{
			throw new IllegalArgumentException();
		}
		
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		if (firstName == null){
			throw new IllegalArgumentException();
		}
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		if (lastName == null){
			throw new IllegalArgumentException();
		}
		LastName = lastName;
	}
	public String getTelephone() {
		return Telephone;
	}
	public void setTelephone(String telephone) {
		if (telephone == null){
			throw new IllegalArgumentException();
		}
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
	
	@Override
	public String toString(){
		return StaffID + "-" + FirstName + " " + LastName;
	}
}
