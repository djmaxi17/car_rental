package gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import database.DbConnect;
import main.LoginSession;
import rentRegistration.RentRegistration;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RentGui extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6228787531300890603L;
	Container contentPane = getContentPane();
	JTextField searchField;
	JButton backBtn;
	
	ImageIcon logoimg;
	JLabel dispLogo;
	JScrollPane sp;
	DefaultTableModel dm;

	private static DbConnect connect = new DbConnect();
	private static ArrayList<RentRegistration> rents;
	private JTable availabilityTable;
	
	static SettleRentGui rentFrame;
	
	public RentGui() {
		super("All Settled Rents");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if(LoginSession.usertype.equals("clerk")) {
						ClerkMainGui Clerkmain = new ClerkMainGui(null);
						Clerkmain.setVisible(true);
					}
					else if(LoginSession.usertype.equals("manager")) {
						ManagerGui manager = new ManagerGui();
						manager.setVisible(true);
					}
					else {
						System.out.println("Log in first");
					}
				}
				catch(Exception ex) {
					System.out.println("exception: " +ex);
				}
			}
		});
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(1010, 678);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		// functions
		headerBar();
		footerBar();
		listAvailableCars();
		addContentToScreen();
	}

	private void headerBar() {
		
		// header image
		setIconImage(new ImageIcon(LoginGui.class.getResource("icon.png")).getImage());
		logoimg = new ImageIcon(getClass().getResource("companyName.png"));
		dispLogo = new JLabel(logoimg);
		dispLogo.setBounds(404,16,200,30);

		// search field
		searchField = new JTextField();
		searchField.setBounds(79,50,200,30);
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				String searchValue = searchField.getText().toLowerCase();
				String removeEscapeChar = searchValue.replaceAll("[^a-z -.A-Z0-9]", "");
				TableRowSorter<DefaultTableModel> max = new TableRowSorter<DefaultTableModel>(dm);
				availabilityTable.setRowSorter(max);
				if(removeEscapeChar.length()!=0) {
					max.setRowFilter(RowFilter.regexFilter(removeEscapeChar));
				}
				else {
					max.setRowFilter(null);
				}
				
			}
		});
	}
	
	private void footerBar() {
		// back btn
		backBtn = new JButton("Back");
		backBtn.setBounds(10,600,100,30);
		
	}

	// function to display available cars
	private void listAvailableCars() {
		
		rents = connect.BindTableRent(0, 0);
		//create array to store names of cars available
		//loop through array list of available cars objects
		String columnName [] = {"Id","Customer", "Car", "Date Rented", "Date Due","DD","Date Returned","DT","Rent Cost","Penalty"};
		String [][] dataFromDb = new String[rents.size()][10];
		
		for (int i=0; i<rents.size(); i++) {
			
			for (int j=0; j <10; j++) {
				
				int id = rents.get(i).getRentId();
				String customer = rents.get(i).getCustomer().getFullName().toLowerCase();
				String car = rents.get(i).getCar().getFullCarName().toLowerCase();
				String rentDate = rents.get(i).getDateRented().toLowerCase();
				String rentDue = rents.get(i).getDateDue().toString().toLowerCase();
				int daysDefault = rents.get(i).getNumOfDaysDefault();
				String rentReturned = null;
				if(rents.get(i).getDateReturned()!=null) {
					rentReturned = rents.get(i).getDateReturned().toLowerCase();
				}
				
				int daysTaken = rents.get(i).getNumOfDaysTaken();
				float rentCost = rents.get(i).getRentCost();
				float penalty = rents.get(i).getPenalty();
				
				dataFromDb[i][0] = String.valueOf(id);
				dataFromDb[i][1] = customer;
				dataFromDb[i][2] = car;
				dataFromDb[i][3] = rentDate;
				dataFromDb[i][4] = rentDue;
				dataFromDb[i][5] = String.valueOf(daysDefault);
				dataFromDb[i][6] = rentReturned;
				dataFromDb[i][7] = String.valueOf(daysTaken);
				dataFromDb[i][8] = String.valueOf(rentCost);
				dataFromDb[i][9] = String.valueOf(penalty);
				
			}
		}
		DefaultTableModel tableModel =  new DefaultTableModel(dataFromDb,columnName) {
		        /**
				 * 
				 */
				private static final long serialVersionUID = -8353222665595177323L;

				@Override
		        public boolean isCellEditable(int row, int column) {
		           //all cells false
		           return false;
				}
		};
		availabilityTable = new JTable(tableModel);
//		availabilityTable = new JTable(dataFromDb,columnName);
		dm= (DefaultTableModel) availabilityTable.getModel();
		availabilityTable.setShowGrid(false);
		availabilityTable.setRowSelectionAllowed(true);
		availabilityTable.setDragEnabled(true);
		
		//some adjustments to the table and adding it to and jscrollpane which is then added to another jpanel sp
		availabilityTable.getColumnModel().getColumn(0).setResizable(false);
		availabilityTable.getColumnModel().getColumn(0).setMaxWidth(50);
		
		availabilityTable.getColumnModel().getColumn(1).setResizable(false);
		availabilityTable.getColumnModel().getColumn(2).setResizable(false);
		
		availabilityTable.getColumnModel().getColumn(3).setResizable(false);
		
		
		availabilityTable.getColumnModel().getColumn(4).setResizable(false);
		availabilityTable.getColumnModel().getColumn(5).setResizable(false);
		availabilityTable.getColumnModel().getColumn(5).setMaxWidth(50);
		
		availabilityTable.getColumnModel().getColumn(6).setResizable(false);
		availabilityTable.getColumnModel().getColumn(7).setResizable(false);
		availabilityTable.getColumnModel().getColumn(7).setMaxWidth(50);
		
		availabilityTable.getColumnModel().getColumn(8).setResizable(false);
		availabilityTable.getColumnModel().getColumn(9).setResizable(false);
		availabilityTable.getTableHeader().setReorderingAllowed(false);
		
		ListSelectionModel select = availabilityTable.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		sp = new JScrollPane(availabilityTable);
		sp.setBounds(6,84, 977, 514);
		sp.setVisible(true);

	}
	
	private void addContentToScreen() {
		contentPane.setLayout(null);
		contentPane.add(dispLogo);
		contentPane.add(searchField);
		contentPane.add(backBtn);
		contentPane.add(sp);
		
		JLabel lblNewLabel = new JLabel("Search");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 19));
		lblNewLabel.setBounds(10, 54, 90, 16);
		getContentPane().add(lblNewLabel);
		
		JLabel titleLabel = new JLabel("Settled Rents Details");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		titleLabel.setBounds(404, 50, 201, 16);
		getContentPane().add(titleLabel);
		HandlerClass handler = new HandlerClass();
		backBtn.addActionListener(handler);
		
	}
	
	private class HandlerClass implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "Back");
			try {
				SettleRentGui settle = new SettleRentGui();
				settle.setVisible(true);
				dispose();
			}
			catch(Exception ex) {
				System.out.println("exception: " +ex);
			}

		}
		
	}
	
	public static void main(String[] args) throws ParseException {
		try {
			UIManager.setLookAndFeel( new NimbusLookAndFeel() );
//=			UIManager.setLookAndFeel( new SyntheticaDarkLookAndFeel() );
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//instantiate login gui only if connection has been established with database
//		if (connect.DbConnect()) {
//			
		RentGui login = new RentGui();
		login.setVisible(true);
		
//		}else if (!connect.DbConnect()) {
//			
//			JOptionPane.showMessageDialog(null, "Failed to connect to database !", "ERROR", JOptionPane.ERROR_MESSAGE);
//		}
	}
}
