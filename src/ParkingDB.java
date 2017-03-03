import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * A class that consists of the database operations to insert and update the database information.
 * @author mmuppa
 * @author concox
 * @author blloyd08
 * This is going to work
 */

public class ParkingDB {
	private static Connection conn;
	//private List<Staff> staffList;
	private static String userName = "concox"; //Change to yours
	/**
	 * Creates a sql connection to MySQL using the properties for
	 * user id, password and server information.
	 * @throws SQLException if an error occurs.
	 */
	private static void createConnection() throws SQLException {
        String password = "fumCin";
        String serverName = "cssgate.insttech.washington.edu";
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		conn = DriverManager.getConnection("jdbc:" + "mysql" + "://"
				+ serverName + "/", connectionProps);

		System.out.println("Connected to database");
	}

	/**
     * Returns all visitor parking available on a specific day.
     * @return availParking a list of available parking.
     * @throws SQLException if an error occurs
     */
    public static List<ParkingSpace> getVisitorAvailParking() throws SQLException {
        if (conn == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "SELECT spaceID, lotName, spaceType FROM " + userName + ".UnregisteredVisitorSpaces";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return createParkingSpaces(rs);
    }
    
    /**
     * Creates a list of ParkingSpace objects represented in a result set
     * @param rs result set containing data on  parking spaces
     * @return List of ParkingSpace objects created from the result set
     * @throws SQLException
     */
    private static List<ParkingSpace> createParkingSpaces(ResultSet rs) throws SQLException{
    	ArrayList<ParkingSpace> spaces = new ArrayList<>();
        while (rs.next()) {
            int spaceID = rs.getInt("spaceID");
            String lotName = rs.getString("lotName");
            String spaceType = rs.getString("spaceType");
            spaces.add(new ParkingSpace(spaceID, lotName, spaceType));
        }
    return spaces;
    }

    /**
     * Returns all staff available parking on the current day.
     * @return availParking a list of available parking.
     * @throws SQLException if an error occurs
     */
    public static List<ParkingSpace> getStaffAvailParking() throws SQLException {
        if (conn == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "select * from "+ userName + ".ParkingSpace where spaceID not in "
        		+ "(select spaceID from " + userName +".CurrentStaffReservation) " +
                "and spaceType = 'Covered'";

        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
            
        return createParkingSpaces(rs);
    }
    
    /**
     * Returns a list of lot names.
     * @return lotNames list of names that the lots are named.
     * @throws SQLException if an error occurs
     */
    public static List<ParkingLot> getLotNamesBelowCapacity() throws SQLException {
        if (conn == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "SELECT lotName, location, capacity, floors FROM " + userName + ".UnfilledLots";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return createLots(rs);
    }
    
    /**
     * Returns a list of space types
     * @return lotNames list of space types that can be assigned to spaces
     * @throws SQLException if an error occurs
     */
    public static List<SpaceType> getSpaceTypes() throws SQLException {
        if (conn == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "SELECT `name` FROM " + userName +".SpaceType";

        ArrayList<SpaceType> spaceTypes = new ArrayList<>();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                spaceTypes.add(new SpaceType(name));
            }
        return spaceTypes;
    }

    /**
     * Returns a list of lot names.
     * @return lotNames list of names that the lots are named.
     * @throws SQLException if an error occurs
     */
    public static List<ParkingLot> getLots() throws SQLException {
        if (conn == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "SELECT lotName, location, capacity, floors from " + userName + ".ParkingLot";

        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
            
        return createLots(rs);
    }
    
    private static List<ParkingLot> createLots(ResultSet rs) throws SQLException{
    	ArrayList<ParkingLot> lots = new ArrayList<>();
        while (rs.next()) {
            String lotName = rs.getString("lotName");
            String location = rs.getString("location");
            int capacity = rs.getInt("capacity");
            int floors = rs.getInt("floors");
            lots.add(new ParkingLot(lotName, location, capacity, floors));
        }
    return lots;
    }

	/**
	 * Returns a list of staff objects.
	 * @return staffList staffList all the staff members in a list.
	 * @throws SQLException if an error occurs
	 */
	public static List<Staff> getStaff() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select staffID, firstName, lastName, telephone, extention, licenseNumber"
				+ " from " + userName + ".Staff ";
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		return createStaff(rs);
	}
	
	/**
     * Creates a list of Staff objects represented in a result set
     * @param rs result set containing data on  staff
     * @return List of Staff objects created from the result set
     * @throws SQLException
     */
    private static List<Staff> createStaff(ResultSet rs) throws SQLException{
    	ArrayList<Staff> spaces = new ArrayList<>();
        while (rs.next()) {
            int staffID = rs.getInt("staffID");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String telephone = rs.getString("telephone");
            String ext = rs.getString("extention");
            String license = rs.getString("licenseNumber");
            spaces.add(new Staff(staffID, firstName, lastName, telephone, ext, license));
        }
    return spaces;
    }

    /**
     * Returns A list of reservations held by staff members.
     * @return staffResList holds staff reservation objects.
     * @throws SQLException if an error occurs
     */
    public static List<StaffReservation> getStaffRes() throws SQLException {
        if (conn == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "select spaceID, startDate, endDate, staffID, rate "
                + "from " + userName + ".StaffReservation ";

        ArrayList<StaffReservation> staffResList = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int spaceID = rs.getInt("spaceID");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                int staffID = rs.getInt("staffID");
                Double rate = rs.getDouble("rate");
                StaffReservation sr = new StaffReservation(spaceID, endDate, staffID, rate);
                sr.setStartDate(startDate);
                staffResList.add(sr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return staffResList;
    }

    /**
     * Returns a list of visitor reservations.
     * @return visiorResList holds visitor reservation objects.
     * @throws SQLException if an error occurs
     */
    public static List<VisitorReservation> getVisitorRes() throws SQLException {
        if (conn == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "select spaceID, reservedDay, staffID, licenseNumber "
                + "from " + userName + ".StaffReservation ";
        List<VisitorReservation> visitorResList = new ArrayList<>();
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int spaceID = rs.getInt("spaceID");
            int staffID = rs.getInt("staffID");
            String licenseNumber = rs.getString("licenseNumber");
            VisitorReservation vr = new VisitorReservation(spaceID, staffID, licenseNumber);
            visitorResList.add(vr);
        }
        return visitorResList;
    }

	/**
	 * Retrieves the staff member given the ID. There ~should~ only be one.
	 * @param theID id of the staff member to retrieve
	 * @return a list of staff members with that id.
	 */
	public static List<Staff> getStaff(int theID) throws SQLException {
		if (conn == null) {
            createConnection();
        }
		List<Staff> staffList = new ArrayList<Staff>();
		List<Staff> filterList = new ArrayList<>();
		try {
			staffList = getStaff();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Staff staff : staffList) {
			if (staff.getStaffID() == (theID)) {
				filterList.add(staff);
			}
		}
		return filterList;
	}

	/**
	 * Adds a new staff member to the table.
	 * @param theStaff a staff member to add.
	 */
	public static void addStaff(Staff theStaff)  throws SQLException {
		if (conn == null) {
            createConnection();
        }
		String sql = "insert into " + userName + ".Staff values " + "(?, ?, ?, ?, ?, ?); ";

		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, theStaff.getStaffID());
			preparedStatement.setString(2, theStaff.getFirstName());
			preparedStatement.setString(3, theStaff.getLastName());
			preparedStatement.setString(4, theStaff.getTelephone());
			preparedStatement.setString(5, theStaff.getExtention());
			preparedStatement.setString(6, theStaff.getLicense());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

    /**
     * Adds a new staff reservation to the database.
     * @param theSR a staff reservation to add.
     */
    public static void addStaffReservation(StaffReservation theSR) throws SQLException {
    	if (conn == null) {
            createConnection();
        }
    	String sql = "insert into " + userName + ".StaffReservation values " + "(?, ?, ?, ?, ?); ";

        PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, theSR.getSpaceID());
            preparedStatement.setDate(2, theSR.getStartDate());
            preparedStatement.setDate(3, theSR.getEndDate());
            preparedStatement.setInt(4, theSR.getStaffID());
            preparedStatement.setDouble(5, theSR.getRate());
            preparedStatement.executeUpdate();
    }

    /**
     * Adds a new visitor reservation to the table.
     * @param theVR a visitor reservation to add.
     */
    public static void addVisitorReservation(VisitorReservation theVR) throws SQLException {
    	if (conn == null) {
            createConnection();
        }
    	String sql = "insert into " + userName + ".VisitorReservation values " + "(?, ?, ?, ?); ";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, theVR.getSpaceID());
            preparedStatement.setDate(2, theVR.getReservedDay());
            preparedStatement.setInt(3, theVR.getStaffID());
            preparedStatement.setString(4, theVR.getLicense());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new parking space to the table.
     * @param theParkingSpace a parking space to add.
     */
    public static void addParkingSpace(ParkingSpace theParkingSpace) throws SQLException {
    	if (conn == null) {
            createConnection();
        }
    	String sql = "INSERT INTO " + userName + ".ParkingSpace(lotName,spaceType) VALUES " + "(?, ?); ";

        PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, theParkingSpace.getLotName());
            preparedStatement.setString(2, theParkingSpace.getType());
            preparedStatement.executeUpdate();
    }

    /**
     * Adds a new parking lot to the table.
     * @param theParkingLot a parking lot to add.
     */
    public static void addParkingLot(ParkingLot theParkingLot) throws SQLException {
    	if (conn == null) {
            createConnection();
        }
    	String sql = "INSERT INTO " + userName + ".ParkingLot VALUES " + "(?, ?, ?, ?); ";

        PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, theParkingLot.getLotName());
            preparedStatement.setString(2, theParkingLot.getLocation());
            preparedStatement.setInt(3, theParkingLot.getCapacity());
            preparedStatement.setInt(4, theParkingLot.getFloors());
            preparedStatement.executeUpdate();
            System.out.println("Lot added");
    }

    public static Boolean updateStaff(int theStaffID, String theExtention, String theLic) throws SQLException {
        if (conn == null) {
            createConnection();
        }
        String sql = "UPDATE " + userName + ".Staff " +
                     "SET extention = '" + theExtention + "', licenseNumber = '" + theLic +
                     "' WHERE staffID = " + theStaffID + ";";
        System.out.println(sql);
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        return true;
    }
}
