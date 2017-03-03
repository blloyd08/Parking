import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;

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
	private JButton btnList, btnVisitor, btnStaff, btnStaffRes;
    private JPanel pnlParking, pnlVisitor, pnlStaffRes, pnlLotSpace, pnlStaff;
    private JButton btnSaveVisitorRes, btnSaveStaffRes, btnAddLot, btnAddSpace, btnAddStaff, btnUpdateStaff;
    private JTextField[] txfLot, txfStaff, txfVisit, txfStaffRes;
	private JComboBox<Staff> myCmbStaffForVisitorReservation;
	private JComboBox<Staff> cmbIDStaffRes;
	private JComboBox<Staff> cmbIDStaff;
	private JComboBox<ParkingSpace> myCmbSpaceForVisitorReservation;
	private JComboBox<ParkingSpace> cmbParkStaffRes;

	private JLabel[] lblLot;
    private JComboBox<SpaceType> myCmbSpaceTypeForAddSpace;
    private JComboBox<ParkingLot> myCmbLotsForAddSpace;
    
    private SpaceType[] mySpaceTypes = new SpaceType[3];

    /**
	 * Creates the frame and components and launches the GUI.
	 */
	private ParkingGUI() {
		super("Parking");
		setSpaceTypes();
		createComponents();
		setVisible(true);
		setSize(500, 550);
	}
	
	/**
	 * Retrieves all space type objects from the DB and converts the list to an array of SpaceType
	 */
	private void setSpaceTypes(){
		try{
			List<SpaceType> spaceTypes = ParkingDB.getSpaceTypes();
			mySpaceTypes = new SpaceType[spaceTypes.size()];
			spaceTypes.toArray(mySpaceTypes);
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
	}
	

    
	/**
	 * Creates panels for Movie list, search, add and adds the corresponding 
	 * components to each panel.
	 */
	private void createComponents() {
        JLabel[] lblStaff;
        JLabel[] lblVisit;
        JLabel[] lblStaffRes;
        JPanel pnlButtons;

        //Add top buttons to select different panels
        pnlButtons = new JPanel();
		btnList = new JButton("Parking");
		btnList.addActionListener(this);

		btnVisitor = new JButton("Visitors");
		btnVisitor.addActionListener(this);
		
		btnStaff = new JButton("Staff");
		btnStaff.addActionListener(this);

		btnStaffRes = new JButton("Staff Reservation");
		btnStaffRes.addActionListener(this);
		
		pnlButtons.add(btnList);
		pnlButtons.add(btnVisitor);
		pnlButtons.add(btnStaff);
		pnlButtons.add(btnStaffRes);
		add(pnlButtons, BorderLayout.NORTH);
		
		//Parking Panel
		pnlParking = new JPanel();
		
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
		JComponent[] controls = new JComponent[spaceColNames.length];
		myCmbLotsForAddSpace = new JComboBox<ParkingLot>();
		controls[0] = myCmbLotsForAddSpace;
		myCmbSpaceTypeForAddSpace = new JComboBox<SpaceType>(mySpaceTypes);
		controls[1] = myCmbSpaceTypeForAddSpace;
		for (int i = 0; i< spaceColNames.length; i++) {
			JPanel panel = new JPanel();
			JLabel controlLabel = new JLabel(spaceColNames[i]);
			panel.add(controlLabel);
			panel.add(controls[i]);
			pnlLotSpace.add(panel);
		}
		//Fill Lots Combo box
		setAddParkingSpaceLots();
		
		//Add button to trigger add space
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
				myCmbStaffForVisitorReservation = new JComboBox<Staff>();
				panel.add(myCmbStaffForVisitorReservation);
			} else if(i == 0) {
				myCmbSpaceForVisitorReservation = new JComboBox<ParkingSpace>();
				panel.add(myCmbSpaceForVisitorReservation);
			} else {
				panel.add(txfVisit[i]);
			}
			pnlVisitor.add(panel);
		}

		setAddVisitorReservationSpace();
		
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
				cmbIDStaff = new JComboBox<Staff>();
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
		btnUpdateStaff = new JButton("Update");
		btnUpdateStaff.addActionListener(this);
		panel.add(btnAddStaff);
		panel.add(btnUpdateStaff);
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
				cmbIDStaffRes = new JComboBox<Staff>();
				newPanel.add(cmbIDStaffRes);
			} else if(i == 0) {
				cmbParkStaffRes = new JComboBox<ParkingSpace>();
				newPanel.add(cmbParkStaffRes);
			} else {
				newPanel.add(txfStaffRes[i]);
			}
			pnlStaffRes.add(newPanel);
		}
		setAllStaffCmb();
		setAddStaffReservationSpace();
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
		movieGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
			setAllStaffCmb();
            setAddParkingSpaceLots();
			this.repaint();
			
		} else if (e.getSource() == btnSaveVisitorRes){
			System.out.println("Reserve, clicked");
			addVisitorReservation();
		} else if (e.getSource() == btnVisitor) {
			pnlParking.removeAll();
			pnlParking.add(pnlVisitor);
			pnlParking.revalidate();
			setAllStaffCmb();
            setAddVisitorReservationSpace();
			this.repaint();
		} else if (e.getSource() == btnStaff) {
			pnlParking.removeAll();
			pnlParking.add(pnlStaff);
			pnlParking.revalidate();
			setAllStaffCmb();
			this.repaint();
		} else if (e.getSource() == btnStaffRes) {
			pnlParking.removeAll();
			pnlParking.add(pnlStaffRes);
			setAddStaffReservationSpace();
			setAllStaffCmb();
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
		} else if (e.getSource() == btnUpdateStaff) {
		    updateStaff();
        }
	}

    /**
     * Updates the staff given the currently selected ID in the combobox.
     */
    private void updateStaff() {
        int staffID;
        String newExtention;
        String newLicense;
        if (txfStaff[4].getText().isEmpty() || txfStaff[5].getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a extension and a license number");
        } else {
            newExtention = txfStaff[4].getText();
            newLicense = txfStaff[5].getText();
            staffID = Integer.parseInt(cmbIDStaff.getSelectedItem().toString());
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
		if (txfStaff[5].getText().length() < 2) {
			JOptionPane.showMessageDialog(this, "License plate too short");
			return;
		}
		String FirstName = txfStaff[1].getText();
		String LastName = txfStaff[2].getText();
		String Telephone = txfStaff[3].getText();
		String Extention = txfStaff[4].getText();
		String License = txfStaff[5].getText();
		Staff newStaff = new Staff(newStaffID, FirstName,LastName,Telephone,Extention, License);
		try {
			ParkingDB.addStaff(newStaff);
			setAllStaffCmb();
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
			setAddParkingSpaceLots();
			for (JTextField tf : txfLot){
				tf.setText("");
			}
			JOptionPane.showMessageDialog(this, "Lot added");
		}catch (SQLException sqlE){
			JOptionPane.showMessageDialog(this, sqlE.getMessage());
			
		}
	}

    /**
     * Inserts a parking space into the database
     */
	private void addVisitorReservation(){
		//Check if parking spaces are available
		if (myCmbSpaceForVisitorReservation.getSelectedIndex() == -1){
			return;
		}
		ParkingSpace space = (ParkingSpace) myCmbSpaceForVisitorReservation.getSelectedItem();
		int spaceID = space.getSpaceID();
		Staff staff = (Staff)myCmbStaffForVisitorReservation.getSelectedItem();
		String license = txfVisit[2].getText();
				
		try{
			VisitorReservation reservation = new VisitorReservation(spaceID, staff.getStaffID(), license);
			ParkingDB.addVisitorReservation(reservation);
			setAddVisitorReservationSpace();
			txfVisit[2].setText("");
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
			ParkingSpace space = (ParkingSpace)cmbParkStaffRes.getSelectedItem();
			Staff staff = (Staff)cmbIDStaffRes.getSelectedItem();
			String endDateStr = txfStaffRes[1].getText();
			String rateStr = txfStaffRes[3].getText();
			Double rate;
			//Validate date
			Date endDate;
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
			//Insert staff Reservation
			try{
				StaffReservation reservation = new StaffReservation(space.getSpaceID(), endDate, staff.getStaffID(), rate);
				ParkingDB.addStaffReservation(reservation);
				//Refresh controls for next entry
				setAllStaffCmb();
				setAddStaffReservationSpace();
				txfStaffRes[1].setText("");
				txfStaffRes[3].setText("");
				JOptionPane.showMessageDialog(this, "Staff reservation added successfully");
			} catch (Exception e){
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
			
		}

    /**
     * inserts a parking space into the data base.
     */
	private void addSpace(){
		ParkingLot lot = (ParkingLot)myCmbLotsForAddSpace.getSelectedItem();
		SpaceType spaceType = (SpaceType)myCmbSpaceTypeForAddSpace.getSelectedItem();
		try{
			ParkingSpace space = new ParkingSpace(lot.getLotName(), spaceType.getName());
			ParkingDB.addParkingSpace(space);
			setAddParkingSpaceLots();
			JOptionPane.showMessageDialog(this, "Parking space added successfully and can now"
					+ " be found in reservation combo boxes");
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		
	}
	
	/**
	 * Retrieves the Parking Spaces that aren't reserved today and adds them to the combo box
	 * used to select a parking space for visitor reservation
	 */
	private void setAllStaffCmb(){
		try{
			List<Staff> staff = ParkingDB.getStaff();
			Staff[] staffArray = new Staff[staff.size()];
			staff.toArray(staffArray);
			DefaultComboBoxModel<Staff> staffModel = new DefaultComboBoxModel<>(staffArray);
			cmbIDStaff.setModel(staffModel);
	        myCmbStaffForVisitorReservation.setModel(staffModel);
	        cmbIDStaffRes.setModel(staffModel);
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
	
	/**
	 * Retrieves the Parking Spaces that aren't reserved today and adds them to the combo box
	 * used to select a parking space for staff reservation
	 */
	private void setAddStaffReservationSpace(){
		try{
			List<ParkingSpace> spaces = ParkingDB.getStaffAvailParking();
			ParkingSpace[] spaceArray = new ParkingSpace[spaces.size()];
			spaces.toArray(spaceArray);
			DefaultComboBoxModel<ParkingSpace> spaceModel = new DefaultComboBoxModel<>(spaceArray);
			cmbParkStaffRes.setModel(spaceModel);
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
	/**
	 * Retrieves the Parking Spaces that aren't reserved today and adds them to the combo box
	 * used to select a parking space for visitor reservation
	 */
	private void setAddVisitorReservationSpace(){
		try{
			List<ParkingSpace> spaces = ParkingDB.getVisitorAvailParking();
			ParkingSpace[] spaceArray = new ParkingSpace[spaces.size()];
			spaces.toArray(spaceArray);
			DefaultComboBoxModel<ParkingSpace> spaceModel = new DefaultComboBoxModel<>(spaceArray);
			myCmbSpaceForVisitorReservation.setModel(spaceModel);
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
	
	/**
	 * Retrieves the Parking Lots that aren't at capacity from the DB and sets them to be used
	 * on the Add Parking Space lots combo box
	 */
	private void setAddParkingSpaceLots(){
		try{
			List<ParkingLot> lots = ParkingDB.getLotNamesBelowCapacity();
			ParkingLot[] lotsArray = new ParkingLot[lots.size()];
			lots.toArray(lotsArray);
			DefaultComboBoxModel<ParkingLot> lotsModel = new DefaultComboBoxModel<>(lotsArray);
			myCmbLotsForAddSpace.setModel(lotsModel);
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

}
