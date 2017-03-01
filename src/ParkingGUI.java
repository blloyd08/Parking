import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
 *
 */
public class ParkingGUI extends JFrame implements ActionListener, TableModelListener
{
	
	private static final long serialVersionUID = 1779520078061383929L;
	private JButton btnList, btnSearch, btnStaff, btnStaffRes;
	private JPanel pnlButtons, pnlParking;
	private ParkingDB db;
	//Which list?
	private List<Staff> list;
	
	private Object[][] data;
	private String[] currentStaff = {"Get IDs", "of staff members", "already in system"};
	private String[] currentParking = {"Get IDs", "of parking spots", "already in system"};
	private String[] belowCapacityLots = {"Lots"};
	private String[] spaceType = {"Free","Covered","Visitor"};
 	private JTable table;
	private JScrollPane scrollPane;
	private JPanel pnlVisitor;
	private JPanel pnlStaffRes;
	private JButton btnSaveVisitorRes;

	// gotta kill this
	private String[] columnNames = {"Title",
			"Year",
			"Length",
			"Genre",
			"StudioName"};
	
	private JPanel pnlLotSpace;
	private JButton btnAddLot;
	private JTextField[] txfLot;
	private JLabel[] lblLot;
	
	private JButton btnAddSpace;
	private JComboBox[] cmbSpace;
	private JLabel[] lblSpace;
	
	
	
	private JPanel pnlStaff;
	private JButton btnAddStaff;
	private JTextField[] txfStaff;
	private JLabel[] lblStaff;
	private JLabel[] lblVisit;
	private JTextField[] txfVisit;
	private JLabel[] lblStaffRes;
	private JTextField[] txfStaffRes;
	private JComboBox cmbIDVisit;
	private JComboBox cmbIDStaffRes;
	private JComboBox cmbIDStaff;
	private JComboBox cmbParkVisit;
	private JComboBox cmbParkStaffRes;
	/**
	 * Creates the frame and components and launches the GUI.
	 */
	public ParkingGUI() {
		super("Movie Store");
		
		db = new ParkingDB();
		try
		{
			list = db.getStaff();

			data = new Object[list.size()][columnNames.length];
//			for (int i=0; i<list.size(); i++) {
//				data[i][0] = list.get(i).getTitle();
//				data[i][1] = list.get(i).getYear();
//				data[i][2] = list.get(i).getLength();
//				data[i][3] = list.get(i).getGenre();
//				data[i][4] = list.get(i).getStudioName();
//
//			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
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
		
		belowCapacityLots = getBelowCapacityLots();
		String[] spaceColNames = {"Lot: ", "Type: "};
		lblSpace = new JLabel[spaceColNames.length];
		cmbSpace = new JComboBox[spaceColNames.length];
		for (int i = 0; i< spaceColNames.length; i++) {
			JPanel panel = new JPanel();
			lblSpace[i] = new JLabel(spaceColNames[i]);
			if (i == 0){
				cmbSpace[i] = new JComboBox<String>(belowCapacityLots);
			} else {
				cmbSpace[i] = new JComboBox<String>(spaceType);
			}
			panel.add(lblSpace[i]);
			panel.add(cmbSpace[i]);
			pnlLotSpace.add(panel);
		}
		
		btnPanel = new JPanel();
		btnAddSpace = new JButton("Add Space");
		btnAddSpace.addActionListener(this);
		btnPanel.add(btnAddSpace);
		
		
		pnlLotSpace.add(btnPanel);
	
		
		
		pnlParking.add(pnlLotSpace);
		
		
		
		
//		table = new JTable(data, columnNames);
//		scrollPane = new JScrollPane(table);
//		pnlParking.add(scrollPane);
//		table.getModel().addTableModelListener(this);

		//Visitor Panel
		pnlVisitor = new JPanel();
		pnlVisitor.setLayout(new GridLayout(7, 0));
		String[] lblVistors = {"Space: ", "Day: ", "Staff ID: ", "License: "};
		lblVisit = new JLabel[lblVistors.length];
		txfVisit = new JTextField[lblVistors.length];
		for (int i = 0; i< lblVistors.length; i++) {
			JPanel panel = new JPanel();
			lblVisit[i] = new JLabel(lblVistors[i]);
			txfVisit[i] = new JTextField(25);
			panel.add(lblVisit[i]);
			if(i == 2) {
				cmbIDVisit = new JComboBox(currentStaff);
				panel.add(cmbIDVisit);
			} else if(i == 0) {
				cmbParkVisit = new JComboBox(currentParking);
				panel.add(cmbParkVisit);
			} else {
				panel.add(txfVisit[i]);
			}
			pnlVisitor.add(panel);
		}

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
		String[] lblStaffResList = {"Space: ", "Start: ", "End: ", "Staff: ", "Rate: "};
		lblStaffRes = new JLabel[lblStaffResList.length];
		txfStaffRes = new JTextField[lblStaffResList.length];
		for (int i = 0; i< lblStaffResList.length; i++) {
			JPanel newPanel = new JPanel();
			lblStaffRes[i] = new JLabel(lblStaffResList[i]);
			txfStaffRes[i] = new JTextField(25);
			newPanel.add(lblStaffRes[i]);
			if(i == 3) {
				cmbIDStaffRes = new JComboBox(currentStaff);
				newPanel.add(cmbIDStaffRes);
			} else if(i == 0) {
				cmbParkStaffRes = new JComboBox(currentParking);
				newPanel.add(cmbParkStaffRes);
			} else {
				newPanel.add(txfStaffRes[i]);
			}
			pnlStaffRes.add(newPanel);
		}

		btnSaveVisitorRes = new JButton("Reserve");
		btnSaveVisitorRes.addActionListener(this);
		pnlStaffRes.add(btnSaveVisitorRes);
		
	}

	/**
	 * @param args
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
			this.repaint();
//			try {
//				list = db.getStaff();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			data = new Object[list.size()][columnNames.length];
//			for (int i=0; i<list.size(); i++) {
//				data[i][0] = list.get(i).getTitle();
//				data[i][1] = list.get(i).getYear();
//				data[i][2] = list.get(i).getLength();
//				data[i][3] = list.get(i).getGenre();
//				data[i][4] = list.get(i).getStudioName();
////			}
//			pnlParking.removeAll();
//			table = new JTable(data, columnNames);
//			table.getModel().addTableModelListener(this);
//			scrollPane = new JScrollPane(table);
//			pnlParking.add(scrollPane);
//			pnlParking.revalidate();
//			this.repaint();
			
		} else if (e.getSource() == btnSearch) {
			pnlParking.removeAll();
			pnlParking.add(pnlVisitor);
			pnlParking.revalidate();
			this.repaint();
		} else if (e.getSource() == btnStaff) {
			pnlParking.removeAll();
			pnlParking.add(pnlStaff);
			pnlParking.revalidate();
			this.repaint();
		} else if (e.getSource() == btnStaffRes) {
			pnlParking.removeAll();
			pnlParking.add(pnlStaffRes);
			pnlParking.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAddLot){
			addLot();
		}
//		else if (e.getSource() == btnSaveVisitorRes) {
//			// Need to change this shit up
//			String title = txfVisit[1].getText();
//			if (title.length() > 0) {
//				list = db.getStaff(1234);
//				data = new Object[list.size()][columnNames.length];
//				for (int i=0; i<list.size(); i++) {
//					data[i][0] = list.get(i).getTitle();
//					data[i][1] = list.get(i).getYear();
//					data[i][2] = list.get(i).getLength();
//					data[i][3] = list.get(i).getGenre();
//					data[i][4] = list.get(i).getStudioName();
//				}
//				pnlParking.removeAll();
//				table = new JTable(data, columnNames);
//				table.getModel().addTableModelListener(this);
//				scrollPane = new JScrollPane(table);
//				pnlParking.add(scrollPane);
//				pnlParking.revalidate();
//				this.repaint();
//			}
//		} else if (e.getSource() == btnAddStaff) {
//			JOptionPane.showMessageDialog(null, "Added Successfully!");
//			for (int i=0; i<txfStaff.length; i++) {
//				txfStaff[i].setText("");
//			}
//		}
		
	}
	
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
			ParkingLot lot = new ParkingLot(txfLot[0].getText(), txfLot[1].getText(),capacity,floors);
			ParkingDB.addParkingLot(lot);
			System.out.println("Lot created");
		}catch (SQLException sqlE){
			JOptionPane.showMessageDialog(this, sqlE.getMessage());
			
		}catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
			return;
		}		
	}
	
	private String[] getBelowCapacityLots(){
		List<String> spaceLots = new ArrayList<String>();
		try{
			spaceLots = ParkingDB.getLotNamesBelowCapacity();
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, "Lots for parking spaces could not be loaded,"
					+ " only below capacity lots are displayed");
		}
		if (spaceLots.size() > 0){
			String[] lotNames = new String[spaceLots.size()];
			for (int i = 0; i < spaceLots.size(); i++){
				lotNames[i] = spaceLots.get(i);
			}
			return lotNames;
		} else {
			return new String[0];
		}
	}

	/**
	 * Event handling for any cell being changed in the table.
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);


	}

}
