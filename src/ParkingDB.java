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
 * A class that consists of the database operations to insert and update the Movie information.
 * @author mmuppa
 * @author concox
 * @author bbloyd
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
        String userName = "concox"; //Change to yours
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
    public static List<Integer> getVisitorAvailParking(Date theSelectedDate) throws SQLException {
        if (conn == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "select * from VisitorSpaces V where V.spaceID not in (select spaceID from VisitorReservation) " +
                "where reservedDay !=" + theSelectedDate + ")";

        ArrayList<Integer> availParking = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int spaceID = rs.getInt("spaceID");
                availParking.add(spaceID);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return availParking;
    }

    /**
     * Returns all staff available parking on the current day.
     * @return availParking a list of available parking.
     * @throws SQLException if an error occurs
     */
    public static List<Integer> getStaffAvailParking() throws SQLException {
        if (conn == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "select * from ParkingSpace where spaceID not in (select spaceID from CurrentStaffReservation) " +
                "and spaceType = 'Covered'";

        ArrayList<Integer> availParking = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int spaceID = rs.getInt("spaceID");
                availParking.add(spaceID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return availParking;
    }
    

    /**
     * Returns a list of lot names.
     * @return lotNames list of names that the lots are named.
     * @throws SQLException if an error occurs
     */
    public static List<String> getLotNames() throws SQLException {
        if (conn == null) {
            createConnection();
        }
        Statement stmt = null;
        String query = "select lotName from ParkingLot";

        ArrayList<String> lotNames = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String lotName = rs.getString("lotName");
                lotNames.add(lotName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return lotNames;
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
		String query = "select firstName, lastName, telephone, extention"
				+ " from " + userName + ".Staff ";

		ArrayList<Staff> staffList = new ArrayList<Staff>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int staffID = rs.getInt("staffID");
				String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String telephone = rs.getString("telephone");
                String extention = rs.getString("extention");
                Staff staff = new Staff(staffID, firstName, lastName, telephone, extention);
				staffList.add(staff);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return staffList;
	}

    /**
     * Returns a a list of reservations held by staff members.
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
                StaffReservation sr = new StaffReservation(spaceID, startDate, endDate, staffID, rate);
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
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int spaceID = rs.getInt("spaceID");
                Date startDate = rs.getDate("reservedDay");
                int staffID = rs.getInt("staffID");
                String licenseNumber = rs.getString("licenseNumber");
                VisitorReservation vr = new VisitorReservation(spaceID, startDate, staffID, licenseNumber);
                visitorResList.add(vr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
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
    	String sql = "insert into " + userName + ".Staff values " + "(?, ?, ?, ?, ?); ";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, theSR.getSpaceID());
            preparedStatement.setDate(2, theSR.getStartDate());
            preparedStatement.setDate(3, theSR.getEndDate());
            preparedStatement.setInt(4, theSR.getStaffID());
            preparedStatement.setDouble(5, theSR.getRate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new visitor reservation to the table.
     * @param theVR a visitor reservation to add.
     */
    public static void addVisitorReservation(VisitorReservation theVR) throws SQLException {
    	if (conn == null) {
            createConnection();
        }
    	String sql = "insert into " + userName + ".Staff values " + "(?, ?, ?, ?); ";

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
    	String sql = "insert into " + userName + ".Staff values " + "(?, ?, ?); ";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, theParkingSpace.getSpaceID());
            preparedStatement.setString(2, theParkingSpace.getLotName());
            preparedStatement.setString(3, theParkingSpace.getType());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, theParkingLot.getLotName());
            preparedStatement.setString(2, theParkingLot.getLocation());
            preparedStatement.setInt(3, theParkingLot.getCapacity());
            preparedStatement.setInt(4, theParkingLot.getFloors());
            preparedStatement.executeUpdate();
            System.out.println("Lot added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifies the staff's data to be updated with the new information.
     * @param theStaffID the staff member to modify
     * @param theExtention the new extention
     * @param theLicenseNumber the new license number
     */
    public static void updateMovie(int theStaffID, String theExtention, String theLicenseNumber) throws SQLException {
    	if (conn == null) {
            createConnection();
        }
        String sql = "update " + userName + ".Staff set extention = ?, licenseNumber = ? where staffID =" + theStaffID;
        System.out.println(sql);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, theExtention);
            preparedStatement.setString(2, theLicenseNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
