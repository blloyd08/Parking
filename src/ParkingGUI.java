import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * A user interface to view the movies, add a new movie and to update an existing movie.
 * The list is a table with all the movie information in it. The TableModelListener listens to
 * any changes to the cells to modify the values for reach movie.
 * @author mmuppa
 * @author concox
 * @author blloyd08
 */
public class ParkingGUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1779520078061383929L;
	private JButton btnList, btnSearch, btnStaff, btnStaffRes;
	private JPanel pnlButtons, pnlParking;

	private String[] currentStaff = {"1234", "5678", "9101R"};
	private String[] currentParking = {"Get IDs", "of parking spots", "already in system"};
	private String[] belowCapacityLots = {"Lots"};
	private String[] spaceType = {"Free","Covered","Visitor"};

	private JPanel pnlVisitor;
	private JPanel pnlStaffRes;
	private JPanel pnlLotSpace;
    private JPanel pnlStaff;

    private JButton btnSaveVisitorRes;
	private JButton btnSaveStaffRes;
	private JButton btnAddLot;
	private JButton btnAddSpace;
    private JButton btnAddStaff;

	private JLabel[] lblLot;
	private JLabel[] lblSpace;
	private JLabel[] lblStaff;
	private JLabel[] lblVisit;
	private JLabel[] lblStaffRes;

    private JTextField[] txfLot;
	private JTextField[] txfStaff;
	private JTextField[] txfVisit;
	private JTextField[] txfStaffRes;

	private JComboBox cmbIDVisit;
	private JComboBox cmbIDStaffRes;
	private JComboBox cmbIDStaff;
	private JComboBox cmbParkVisit;
	private JComboBox cmbParkStaffRes;
    private JComboBox[] cmbSpace;

    /**
	 * Creates the frame and components and launches the GUI.
	 */
	public ParkingGUI() {
		super("Parking");
		createComponents();
		setVisible(true);
		setSize(500, 550);
	}
    
	/**
	 * Creates panels for Movie list, search, add and adds the corresponding 
	 * components to each panel.
	 */
	private void createComponents() {
		pnlButtons = new JPanel();
		btnList = new JButton("Parking");
		btnList.addActionListener(this);

		btnSearch = new JButton("Visitors");
		btnSearch.addActionListener(this);
		
		btnStaff = new JButton("Staff");
		btnStaff.addActionListener(this);

		btnStaffRes = new JButton("Staff Reservation");
		btnStaffRes.addActionListener(this);
		
		pnlButtons.add(btnList);
		pnlButtons.add(btnSearch);
		pnlButtons.add(btnStaff);
		pnlButtons.add(btnStaffRes);
		add(pnlButtons, BorderLayout.NORTH);
		
		//Parking Panel
		pnlParking = new JPanel();
		//pnlParking.setLayout(new GridLayout(7, 0));
		
		//Lot and Spaces
		pnlLotSpace = new JPanel();
		pnlLotSpace.setLayout(new GridLayout(0, 1));
		
		// Parking Lot Heading
		JLabel lotHeading  = new JLabel("--- Add Parking Lot ---");
		lotHeading.setHorizontalAlignment(JLabel.CENTER);
		pnlLotSpace.add(lotHeading);
		
		String[] lotColNames = {"Lot Name: ", "Location: ", "Capacity: ",
				"Floors: "};
		lblLot = new JLabel[lotColNames.length];
		txfLot = new JTextField[lotColNames.length];
		for (int i = 0; i< lotColNames.length; i++) {
			JPanel panel = new JPanel();
			lblLot[i] = new JLabel(lotColNames[i]);
			txfLot[i] = new JTextField(25);
			panel.add(lblLot[i]);
			panel.add(txfLot[i]);
			pnlLotSpace.add(panel);
		}
		JPanel btnPanel = new JPanel();
		btnAddLot = new JButton("Add Lot");
		btnAddLot.addActionListener(this);
		btnPanel.add(btnAddLot);
		pnlLotSpace.add(btnPanel);
		
		
		//Parking Spaces
		JLabel spaceHeading  = new JLabel("--- Add Parking Space ---");
		spaceHeading.setHorizontalAlignment(JLabel.CENTER);
		pnlLotSpace.add(spaceHeading);
		
		String[] spaceColNames = {"Lot: ", "Type: "};
		lblSpace = new JLabel[spaceColNames.length];
		cmbSpace = new JComboBox[spaceColNames.length];
		for (int i = 0; i< spaceColNames.length; i++) {
			JPanel panel = new JPanel();
			lblSpace[i] = new JLabel(spaceColNames[i]);
			if (i == 0){
				cmbSpace[i] = new JComboBox<String>();
			} else {
				cmbSpace[i] = new JComboBox<String>(spaceType);
			}
			panel.add(lblSpace[i]);
			panel.add(cmbSpace[i]);
			pnlLotSpace.add(panel);
		}
		
		refreshParkingSpaceLotCmb();
		
		btnPanel = new JPanel();
		btnAddSpace = new JButton("Add Space");
		btnAddSpace.addActionListener(this);
		btnPanel.add(btnAddSpace);
		pnlLotSpace.add(btnPanel);
		pnlParking.add(pnlLotSpace);
		
		//Visitor Panel
		pnlVisitor = new JPanel();
		pnlVisitor.setLayout(new GridLayout(7, 0));
		String[] lblVistors = {"Space: ", "Staff ID: ", "License: "};
		lblVisit = new JLabel[lblVistors.length];
		txfVisit = new JTextField[lblVistors.length];
		for (int i = 0; i< lblVistors.length; i++) {
			JPanel panel = new JPanel();
			lblVisit[i] = new JLabel(lblVistors[i]);
			txfVisit[i] = new JTextField(25);
			panel.add(lblVisit[i]);
			if(i == 1) {
				cmbIDVisit = new JComboBox(currentStaff);
				panel.add(cmbIDVisit);
			} else if(i == 0) {
				cmbParkVisit = new JComboBox();
				panel.add(cmbParkVisit);
			} else {
				panel.add(txfVisit[i]);
			}
			pnlVisitor.add(panel);
		}

		refreshVisitorSpacesCmb();
		
		btnSaveVisitorRes = new JButton("Reserve");
		btnSaveVisitorRes.addActionListener(this);
		pnlVisitor.add(btnSaveVisitorRes);
		
		//Staff Panel
		pnlStaff = new JPanel();
		pnlStaff.setLayout(new GridLayout(7, 0));
		String[] staffColNames = {"Staff ID", "First Name: ", "Last Name: ",
				"Telephone: ", "Extension: ", "License Number: "};
		lblStaff = new JLabel[staffColNames.length];
		txfStaff = new JTextField[staffColNames.length];
		for (int i = 0; i< staffColNames.length; i++) {
			JPanel panel = new JPanel();
			lblStaff[i] = new JLabel(staffColNames[i]);
			txfStaff[i] = new JTextField(25);
			panel.add(lblStaff[i]);
			if(i == 0) {
				cmbIDStaff = new JComboBox(currentStaff);
				cmbIDStaff.setEditable(true);
				panel.add(cmbIDStaff);
			} else {
				panel.add(txfStaff[i]);
			}
			pnlStaff.add(panel);
		}
		JPanel panel = new JPanel();
		btnAddStaff = new JButton("Add");
		btnAddStaff.addActionListener(this);
		panel.add(btnAddStaff);
		pnlStaff.add(panel);
		add(pnlParking, BorderLayout.CENTER);

		//Staff reservation
		pnlStaffRes = new JPanel();
		pnlStaffRes.setLayout(new GridLayout(7, 0));
		String[] lblStaffResList = {"Space: ", "End: ", "Staff: ", "Rate: "};
		lblStaffRes = new JLabel[lblStaffResList.length];
		txfStaffRes = new JTextField[lblStaffResList.length];
		for (int i = 0; i< lblStaffResList.length; i++) {
			JPanel newPanel = new JPanel();
			lblStaffRes[i] = new JLabel(lblStaffResList[i]);
			txfStaffRes[i] = new JTextField(25);
			newPanel.add(lblStaffRes[i]);
			if(i == 2) {
				cmbIDStaffRes = new JComboBox(currentStaff);
				newPanel.add(cmbIDStaffRes);
			} else if(i == 0) {
				cmbParkStaffRes = new JComboBox();
				newPanel.add(cmbParkStaffRes);
			} else {
				newPanel.add(txfStaffRes[i]);
			}
			pnlStaffRes.add(newPanel);
		}
		refreshStaffReservationSpacesCmb();
		btnSaveStaffRes = new JButton("Reserve");
		btnSaveStaffRes.addActionListener(this);
		pnlStaffRes.add(btnSaveStaffRes);
		
	}

	/**
     * Main function of the program, starts the gui.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		ParkingGUI movieGUI = new ParkingGUI();
		movieGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Event handling to change the panels when different tabs are clicked,
	 * add and search buttons are clicked on the corresponding add and search panels.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnList) {
			pnlParking.removeAll();
			pnlParking.add(pnlLotSpace);
			pnlParking.revalidate();
			refreshParkingSpaceLotCmb();
			this.repaint();
			
		} else if (e.getSource() == btnSaveVisitorRes){
			System.out.println("Reserve, clicked");
			addVisitorReservation();
		} else if (e.getSource() == btnSearch) {
			pnlParking.removeAll();
			pnlParking.add(pnlVisitor);
			pnlParking.revalidate();
			refreshVisitorSpacesCmb();
			this.repaint();
		} else if (e.getSource() == btnStaff) {
			pnlParking.removeAll();
			pnlParking.add(pnlStaff);
			pnlParking.revalidate();
			this.repaint();
		} else if (e.getSource() == btnStaffRes) {
			pnlParking.removeAll();
			pnlParking.add(pnlStaffRes);
			getStaffReservationSpaces();
			pnlParking.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAddLot){
			addLot();
		} else if (e.getSource() == btnAddStaff){
			addStaff();
		} else if (e.getSource() == btnAddSpace){
			addSpace();
		} else if (e.getSource() == btnSaveStaffRes){
			addStaffReservation();
		}
	}

    /**
     * Adds a new visitor reservation to the database
     */
	private void addVisitorRes(){
		int newSpaceID = 0;
		int newStaffID = 0;
		Date newDate = null;

		if (txfVisit[0].getText().length() != 0) {
			try {
				newSpaceID = Integer.parseInt(txfVisit[0].getText());
			} catch (NumberFormatException e ) {
				JOptionPane.showMessageDialog(this, "Invalid input for spaceID");
			}
		}
		if (txfVisit[2].getText().length() != 0) {
			try {
				newStaffID = Integer.parseInt(txfVisit[2].getText());
			} catch (NumberFormatException e ) {
				JOptionPane.showMessageDialog(this, "Invalid input for staffID");
			}
		}
		String newLicense = txfVisit[3].getText();
		VisitorReservation newVR = new VisitorReservation(newSpaceID, newStaffID, newLicense);
		try{
			ParkingDB.addVisitorReservation(newVR);
		} catch (SQLException e) {
			e.getMessage();
		}
	}

    /**
     * adds a new staff member to the database.
     */
	private void addStaff(){
		int newStaffID = 0;
		if (txfVisit[0].getText().length() != 0) {
			try {
				newStaffID = Integer.parseInt(txfVisit[0].getText());
			} catch (NumberFormatException e ) {
				JOptionPane.showMessageDialog(this, "Invalid input for staffID");
			}
		}
		if (txfStaff[1].getText().length() < 2) {
			JOptionPane.showMessageDialog(this, "Name too short");
			return;
		}
		if (txfStaff[2].getText().length() < 2) {
			JOptionPane.showMessageDialog(this, "Last name too short");
			return;
		}
		if (txfStaff[3].getText().length() < 5) {
			JOptionPane.showMessageDialog(this, "Telephone too short");
			return;
		}
		if (txfStaff[4].getText().length() > 5) {
			JOptionPane.showMessageDialog(this, "Extention too long");
			return;
		}
		String FirstName = txfStaff[1].getText();
		String LastName = txfStaff[2].getText();
		String Telephone = txfStaff[3].getText();
		String Extention = txfStaff[4].getText();
		Staff newStaff = new Staff(newStaffID, FirstName,LastName,Telephone,Extention);
		try {
			ParkingDB.addStaff(newStaff);
		} catch (SQLException e) {
			e.getMessage();
		}
		JOptionPane.showMessageDialog(this, "Added Staff");
		for (JTextField txf : txfStaff) {
			txf.setText("");
		}
	}

    /**
     * Adds a parking lot to the database.
     */
	private void addLot(){
		//Converted int values
		int capacity = 0;
		int floors = 0;
		
		//Validate input (Required fields)
		for (int i = 0; i < txfLot.length; i++){
			System.out.println(txfLot[i].getText().compareTo(""));
			//Check floors attribute
			if (i == 3){
				if (txfLot[i].getText().compareTo("") ==0){
					txfLot[i].setText("0");
					floors = 0;
				} else{
					try {
						floors = Integer.parseInt(txfLot[i].getText());
					} catch (Exception e){
						JOptionPane.showMessageDialog(this, "Invalid input for floors");
						return;
					}
				}
				continue;
			}
			
			//Check required fields
			if (txfLot[i].getText().compareTo("") ==0){
				String errorLabel = lblLot[i].getText().split(":")[0];
				JOptionPane.showMessageDialog(this, "Invalid input for " + errorLabel);
				return;
			}
			
			//Check Capacity is number
			if (i == 2){
				try {
					capacity = Integer.parseInt(txfLot[i].getText());
				} catch (Exception e){
					JOptionPane.showMessageDialog(this, "Invalid input for capacity");
					return;
				}
			}
		}
		try {
			//Add lot
			ParkingLot lot = new ParkingLot(txfLot[0].getText(), txfLot[1].getText(),capacity,floors);
			ParkingDB.addParkingLot(lot);
			refreshParkingSpaceLotCmb();
			for (JTextField tf : txfLot){
				tf.setText("");
			}
			JOptionPane.showMessageDialog(this, "Lot added");
		}catch (SQLException sqlE){
			JOptionPane.showMessageDialog(this, sqlE.getMessage());
			
		}catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
			return;
		}		
	}

    /**
     * Inserts a parking space into the database
     */
	private void addVisitorReservation(){
		//Check if parking spaces are available
		if (cmbParkVisit.getSelectedIndex() == -1){
			return;
		}
		String spaceIDStr =(String)cmbParkVisit.getSelectedItem();
		int spaceID = Integer.parseInt(spaceIDStr);
		String staffIDStr = (String)cmbIDVisit.getSelectedItem();
		int staffID = Integer.parseInt(staffIDStr);
		String license = txfVisit[2].getText();
				
		try{
			VisitorReservation reservation = new VisitorReservation(spaceID, staffID, license);
			ParkingDB.addVisitorReservation(reservation);
			refreshVisitorSpacesCmb();
			JOptionPane.showMessageDialog(this, "Visitor reservation added succesfully");
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
	}

    /**
     * Inserts a parking space into the database
     */
		private void addStaffReservation(){
			//Check if parking spaces are available
			if (cmbParkStaffRes.getSelectedIndex() == -1){
				return;
			}
			String spaceIDStr =(String)cmbParkStaffRes.getSelectedItem();
			int spaceID = Integer.parseInt(spaceIDStr);
			String staffIDStr = (String)cmbIDStaffRes.getSelectedItem();
			int staffID = Integer.parseInt(staffIDStr);
			String endDateStr = txfStaffRes[1].getText();
			String rateStr = txfStaffRes[3].getText();
			Double rate = 0.0;
			//Validate date
			Date endDate = null;
			try {
				Calendar cal = Calendar.getInstance();
				DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
				cal.setTime(dateFormat.parse(endDateStr));
				endDate = new Date(cal.getTime().getTime());
			} catch (Exception e){
				JOptionPane.showMessageDialog(this, "End date is not in the form yyyy-mm-dd");
				return;
			}
			//Validate rate
			try{
				rate = Double.parseDouble(rateStr);
			} catch (Exception e){
				JOptionPane.showMessageDialog(this, "Rate is not a valid decimal number");
				return;
			}
			try{
				StaffReservation reservation = new StaffReservation(spaceID, endDate, staffID, rate);
				ParkingDB.addStaffReservation(reservation);
				refreshStaffReservationSpacesCmb();
				JOptionPane.showMessageDialog(this, "Staff reservation added succesfully");
			} catch (Exception e){
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
			
		}

    /**
     * inserts a parking space into the data base.
     */
	private void addSpace(){
		String lotName = (String)cmbSpace[0].getSelectedItem();
		String spaceType = (String)cmbSpace[1].getSelectedItem();
		try{
			ParkingSpace space = new ParkingSpace(lotName, spaceType);
			ParkingDB.addParkingSpace(space);
			refreshParkingSpaceLotCmb();
			JOptionPane.showMessageDialog(this, "Parking space added succesffuly and can now"
					+ " be found in reservation combo boxes");
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
	}

    /**
     * refreshes the combo boxes with new visitor spaces.
     */
	private void refreshVisitorSpacesCmb(){
		String[] visitorSpaces = getVisitorSpaces();
		DefaultComboBoxModel spacesModel = new DefaultComboBoxModel(visitorSpaces);
		cmbParkVisit.setModel(spacesModel);
	}

    /**
     * Refreshes the combo boxes with new staff spaces.
     */
	private void refreshStaffReservationSpacesCmb(){
		String[] spaceNames = getStaffReservationSpaces();
		DefaultComboBoxModel spaceModels = new DefaultComboBoxModel(spaceNames);
		cmbParkStaffRes.setModel(spaceModels);
	}

    /**
     * Gets all the staff reservation spaces.
     * @return a string array with all the spaces that are open to staff.
     */
	private String[] getStaffReservationSpaces(){
		List<Integer> spaces = new ArrayList<Integer>();
		try{
			spaces = ParkingDB.getStaffAvailParking();
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		if (spaces.size() > 0){
			String[] staffSpaces = new String[spaces.size()];
			for (int i = 0; i < spaces.size(); i++){
				staffSpaces[i] = spaces.get(i).toString();
			}
			return staffSpaces;
		} else {
			JOptionPane.showMessageDialog(this, "Only staff reservation spaces not currently"
					+ " reserved are displayed, no spaces found");
			return new String[0];
		}
	}

    /**
     * Gets all available visitor spaces.
     * @return a string array with all the spaces that are open to visitors.
     */
	private String[] getVisitorSpaces(){
		List<Integer> spaces = new ArrayList<Integer>();
		try{
			spaces = ParkingDB.getVisitorAvailParking();
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		if (spaces.size() > 0){
			String[] visitorSpaces = new String[spaces.size()];
			for (int i = 0; i < spaces.size(); i++){
				visitorSpaces[i] = spaces.get(i).toString();
			}
			return visitorSpaces;
		} else {
			JOptionPane.showMessageDialog(this, "Only visitor spaces not currently"
					+ " reserved are displayed, no spaces found");
			return new String[0];
		}
	}

    /**
     * refreshes the combo boxes containing lots.
     */
	private void refreshParkingSpaceLotCmb(){
		String[] lotNames = getBelowCapacityLots();
		DefaultComboBoxModel lotsModel = new DefaultComboBoxModel(lotNames);
		cmbSpace[0].setModel(lotsModel);
	}

    /**
     * Gets the lots that have available parking.
     * @return a string array containing the open lots
     */
	private String[] getBelowCapacityLots(){
		List<String> spaceLots = new ArrayList<String>();
		try{
			spaceLots = ParkingDB.getLotNamesBelowCapacity();
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		if (spaceLots.size() > 0){
			String[] lotNames = new String[spaceLots.size()];
			for (int i = 0; i < spaceLots.size(); i++){
				lotNames[i] = spaceLots.get(i);
			}
			return lotNames;
		} else {
			JOptionPane.showMessageDialog(this, "Only parking lots below capacity"
					+ " are displayed, no unfilled lots found");
			return new String[0];
		}
	}

}
