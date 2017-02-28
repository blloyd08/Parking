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
	private JButton btnList, btnSearch, btnStaff;
	private JPanel pnlButtons, pnlParking;
	private ParkingDB db;
	private List<Movies> list;
	
	private Object[][] data;
	private Object[][] staffData;
	private JTable table;
	private JTable staffTable;
	private JScrollPane scrollPane;
	private JPanel pnlVisitor;
	private JLabel lblTitle;
	private JTextField txfTitle;
	private JButton btnTitleSearch;
	private String[] columnNames = {"Title",
			"Year",
			"Length",
			"Genre",
			"StudioName"};
	
	private JPanel pnlStaff;
	private JLabel[] txfLabel = new JLabel[6];
	private JTextField[] txfField = new JTextField[6];
	private JButton btnAddStaff;
	
	
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
		
		pnlButtons.add(btnList);
		pnlButtons.add(btnSearch);
		pnlButtons.add(btnStaff);
		add(pnlButtons, BorderLayout.NORTH);
		
		//Parking Panel
		pnlParking = new JPanel();
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane(table);
		pnlParking.add(scrollPane);
		table.getModel().addTableModelListener(this);


		//Visitor Panel
		pnlVisitor = new JPanel();
		lblTitle = new JLabel("Enter Title: ");
		txfTitle = new JTextField(25);
		btnTitleSearch = new JButton("Search");
		btnTitleSearch.addActionListener(this);
		pnlVisitor.add(lblTitle);
		pnlVisitor.add(txfTitle);
		pnlVisitor.add(btnTitleSearch);
		
		//Staff Panel
		pnlStaff = new JPanel();
		pnlStaff.setLayout(new GridLayout(7, 0));
		String[] staffColNames = {"Staff ID", "First Name: ", "Last Name: ",
				"Telephone: ", "Extension: ", "License Number: "};
		String[] currentStaff = {"Get IDs", "of staff members", "already in system"};
		for (int i = 0; i< staffColNames.length; i++) {
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(staffColNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			if(i == 0) {
				JComboBox stfIDs = new JComboBox(currentStaff);
				stfIDs.setEditable(true);
				panel.add(stfIDs);
			} else {
				panel.add(txfField[i]);
			}
			pnlStaff.add(panel);
		}
		JPanel panel = new JPanel();
		btnAddStaff = new JButton("Add");
		btnAddStaff.addActionListener(this);
		panel.add(btnAddStaff);
		pnlStaff.add(panel);
		
		add(pnlParking, BorderLayout.CENTER);
		
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
			
		} else if (e.getSource() == btnTitleSearch) {
			String title = txfTitle.getText();
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
			Movies movie = new Movies(txfField[0].getText(), Integer.parseInt(txfField[1].getText())
					,Integer.parseInt(txfField[2].getText()), txfField[3].getText(), txfField[4].getText() );
			db.addMovie(movie);
			JOptionPane.showMessageDialog(null, "Added Successfully!");
			for (int i=0; i<txfField.length; i++) {
				txfField[i].setText("");
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
