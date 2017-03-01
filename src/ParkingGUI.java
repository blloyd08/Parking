import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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
	private List<Movies> list;
	
	private Object[][] data;
	private String[] currentStaff = {"Get IDs", "of staff members", "already in system"};
	private String[] currentParking = {"Get IDs", "of parking spots", "already in system"};
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
			list = db.getMovies();
			
			data = new Object[list.size()][columnNames.length];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).getTitle();
				data[i][1] = list.get(i).getYear();
				data[i][2] = list.get(i).getLength();
				data[i][3] = list.get(i).getGenre();
				data[i][4] = list.get(i).getStudioName();
				
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		createComponents();
		setVisible(true);
		setSize(500, 500);
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
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane(table);
		pnlParking.add(scrollPane);
		table.getModel().addTableModelListener(this);

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
			try {
				list = db.getMovies();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			data = new Object[list.size()][columnNames.length];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).getTitle();
				data[i][1] = list.get(i).getYear();
				data[i][2] = list.get(i).getLength();
				data[i][3] = list.get(i).getGenre();
				data[i][4] = list.get(i).getStudioName();
			}
			pnlParking.removeAll();
			table = new JTable(data, columnNames);
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			pnlParking.add(scrollPane);
			pnlParking.revalidate();
			this.repaint();
			
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
		} else if (e.getSource() == btnSaveVisitorRes) {
			// Need to change this shit up
			String title = txfVisit[1].getText();
			if (title.length() > 0) {
				list = db.getMovies(title);
				data = new Object[list.size()][columnNames.length];
				for (int i=0; i<list.size(); i++) {
					data[i][0] = list.get(i).getTitle();
					data[i][1] = list.get(i).getYear();
					data[i][2] = list.get(i).getLength();
					data[i][3] = list.get(i).getGenre();
					data[i][4] = list.get(i).getStudioName();
				}
				pnlParking.removeAll();
				table = new JTable(data, columnNames);
				table.getModel().addTableModelListener(this);
				scrollPane = new JScrollPane(table);
				pnlParking.add(scrollPane);
				pnlParking.revalidate();
				this.repaint();
			}
		} else if (e.getSource() == btnAddStaff) {
			Movies movie = new Movies(txfStaff[0].getText(), Integer.parseInt(txfStaff[1].getText())
					,Integer.parseInt(txfStaff[2].getText()), txfStaff[3].getText(), txfStaff[4].getText() );
			db.addMovie(movie);
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			for (int i=0; i<txfStaff.length; i++) {
				txfStaff[i].setText("");
			}
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
        
        db.updateMovie(row, columnName, data);
		
	}

}
