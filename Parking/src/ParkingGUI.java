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
	private JButton btnList, btnSearch, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private ParkingDB db;
	private List<Movies> list;
	private String[] columnNames = {"Title",
            "Year",
            "Length",
            "Genre",
            "StudioName"};
	
	private Object[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel pnlSearch;
	private JLabel lblTitle;;
	private JTextField txfTitle;
	private JButton btnTitleSearch;
	
	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[5];
	private JTextField[] txfField = new JTextField[5];
	private JButton btnAddMovie;
	
	
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
	private void createComponents()
	{
		pnlButtons = new JPanel();
		btnList = new JButton("Movie List");
		btnList.addActionListener(this);
		
		btnSearch = new JButton("Movie Search");
		btnSearch.addActionListener(this);
		
		btnAdd = new JButton("Add Movie");
		btnAdd.addActionListener(this);
		
		pnlButtons.add(btnList);
		pnlButtons.add(btnSearch);
		pnlButtons.add(btnAdd);
		add(pnlButtons, BorderLayout.NORTH);
		
		//List Panel
		pnlContent = new JPanel();
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		table.getModel().addTableModelListener(this);
		
		//Search Panel
		pnlSearch = new JPanel();
		lblTitle = new JLabel("Enter Title: ");
		txfTitle = new JTextField(25);
		btnTitleSearch = new JButton("Search");
		btnTitleSearch.addActionListener(this);
		pnlSearch.add(lblTitle);
		pnlSearch.add(txfTitle);
		pnlSearch.add(btnTitleSearch);
		
		//Add Panel
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(6, 0));
		String labelNames[] = {"Enter Title: ", "Enter Year: ", "Enter Length: ", "Enter Genre: ", "Enter Studio Name: "};
		for (int i=0; i<labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}
		JPanel panel = new JPanel();
		btnAddMovie = new JButton("Add");
		btnAddMovie.addActionListener(this);
		panel.add(btnAddMovie);
		pnlAdd.add(panel);
		
		add(pnlContent, BorderLayout.CENTER);
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
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
			pnlContent.removeAll();
			table = new JTable(data, columnNames);
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			pnlContent.add(scrollPane);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnSearch) {
			pnlContent.removeAll();
			pnlContent.add(pnlSearch);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAdd) {
			pnlContent.removeAll();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
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
				pnlContent.removeAll();
				table = new JTable(data, columnNames);
				table.getModel().addTableModelListener(this);
				scrollPane = new JScrollPane(table);
				pnlContent.add(scrollPane);
				pnlContent.revalidate();
				this.repaint();
			}
		} else if (e.getSource() == btnAddMovie) {
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
