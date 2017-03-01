import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * A class that consists of the database operations to insert and update the Movie information.
 * @author mmuppa
 *
 */

public class ParkingDB {
	private static String userName = "concox"; //Change to yours
	private static String password = "fumCin";
	private static String serverName = "cssgate.insttech.washington.edu";
	private static Connection conn;
	private List<Staff> list;

	/**
	 * Creates a sql connection to MySQL using the properties for
	 * userid, password and server information.
	 * @throws SQLException
	 */
	public static void createConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		conn = DriverManager.getConnection("jdbc:" + "mysql" + "://"
				+ serverName + "/", connectionProps);

		System.out.println("Connected to database");
	}

	/**
	 * Returns a list of movie objects from the database.
	 * @return list of movies
	 * @throws SQLException
	 */
	public List<Staff> getStaff() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select title, year, length, genre, studioName "
				+ "from youruwnetid.Movies ";

		list = new ArrayList<Staff>();
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
				list.add(staff);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return list;
	}

	/**
	 * Filters the movie list to find the given title. Returns a list with the
	 * movie objects that match the title provided.
	 * @param ID
	 * @return list of movies that contain the title.
	 */
	public List<Staff> getStaff(int ID) {
		List<Staff> filterList = new ArrayList<Staff>();
		try {
			list = getStaff();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Staff staff : list) {
			if (staff.getStaffID() == (ID)) {
				filterList.add(staff);
			}
		}
		return filterList;
	}

	/**
	 * Adds a new staff member to the table.
	 * @param staff
	 */
	public void addStaff(Staff staff) {
		String sql = "insert into youruwnetid.Staff values " + "(?, ?, ?, ?, ?, ?, null); ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, staff.getStaffID());
			preparedStatement.setString(2, staff.getFirstName());
			preparedStatement.setString(3, staff.getLastName());
			preparedStatement.setString(4, staff.getTelephone());
			preparedStatement.setString(5, staff.getExtention());
			preparedStatement.setString(6, staff.getLicense());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
	}

//	/**
//	 * Modifies the movie information corresponding to the index in the list.
//	 * @param row index of the element in the list
//	 * @param columnName attribute to modify
//	 * @param data value to supply
//	 */
//	public void updateStaff(int row, String columnName, Object data) {
//		Staff staff = list.get(row);
//        int ID = staff.getStaffID();
//		String sql = "update youruwnetid.Movies set " + columnName + " = ?  where staffID = ?";
//		System.out.println(sql);
//		PreparedStatement preparedStatement = null;
//		try {
//			preparedStatement = conn.prepareStatement(sql);
//			if (data instanceof String)
//				preparedStatement.setString(1, (String) data);
//			else if (data instanceof Integer)
//				preparedStatement.setInt(1, (Integer) data);
////			preparedStatement.setString(2, title);
////			preparedStatement.setInt(3, year);
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			System.out.println(e);
//			e.printStackTrace();
//		}
//	}
}
