package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import database.DbConnect;
import main.LoginSession;
import rentRegistration.RentRegistration;

public class SettleRentGui extends JFrame{

	private Color colortxt = new Color(251,241,199);
	/**
	 * 
	 */
	private static final long serialVersionUID = 6228787531300890603L;
	Container contentPane = getContentPane();
	JTextField searchField;
	JButton settleRentBtn;
	JButton backBtn;
	
	ImageIcon logoimg;
	JLabel dispLogo;
	JScrollPane sp;
	DefaultTableModel dm;
	Color colorSp = new Color(87,90,92); 
	private static DbConnect connect = new DbConnect();
	private static ArrayList<RentRegistration> rents;
	private JTable availabilityTable;
	private Color buttonCol = new Color(79,99,116);
	static SettleRentGui rentFrame;
	
	public SettleRentGui() {
		super("Settle Rent");
		getContentPane().setBackground(Color.DARK_GRAY);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					if(LoginSession.usertype.equals("clerk")) {
						ClerkMainGui Clerkmain = new ClerkMainGui(null);
						Clerkmain.setVisible(true);
					}
					else if(LoginSession.usertype.equals("manager")) {
						ManagerGui manager = new ManagerGui(null);
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
		dispLogo.setBounds(387,18,225,30);

		// search field
		
		searchField = new JTextField(20);
		searchField.setBackground(new Color(35,35,36));
		searchField.setFont(new Font("SansSerif", Font.PLAIN, 15));
		searchField.setForeground(Color.WHITE);
		searchField.setBounds(70,38,232,30);
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
		// footer settle rent btn
		
		// next btn
		settleRentBtn = new JButton("Settle Rent");
		settleRentBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		settleRentBtn.setBackground(buttonCol);
		settleRentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int rowSelected = availabilityTable.convertRowIndexToModel(availabilityTable.getSelectedRow());
				
					if(rowSelected!=-1) {
						
						int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to settle this rent?","Confirmation",JOptionPane.YES_NO_OPTION);
						
							if(confirm==0) {
								
								String rentId = availabilityTable.getModel().getValueAt(rowSelected, 0).toString();
								RentRegistration selectedRent = connect.BindTableRent(Integer.valueOf(rentId), -1).get(0);
						
//								LocalDate localDate = LocalDate.now();
//				       			String dateToday = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
								
								//set 2020-04-17 as Today Date for testing purposes
								String dateToday = "2020-04-17";
				        
								try {
				        	
									selectedRent.setDateReturned(dateToday);
									
									if(connect.updateRent(selectedRent)) {
										RentFinalReceipt receipt = new RentFinalReceipt(rentFrame, selectedRent);
										receipt.setVisible(true);
										
										if(LoginSession.usertype.equals("clerk")) {
											ClerkMainGui clerk = new ClerkMainGui(null);
											clerk.setVisible(true);
										}
										else if(LoginSession.usertype.equals("manager")) {
											ManagerGui manager = new ManagerGui(null);
											manager.setVisible(true);
										}
										dispose();
										
									}
									else {
										JOptionPane.showMessageDialog(null, "There has been an error! Re-try", "Error", JOptionPane.ERROR_MESSAGE);
									}
							
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
						}
				}
				else {
					JOptionPane.showMessageDialog(null, "Nothing is Selected!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		settleRentBtn.setBounds(841,600,143,30);
		// back btn
		backBtn = new JButton("Back");
		backBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		backBtn.setBackground(buttonCol);
		backBtn.setBounds(10,600,100,30);
		
	}

	// function to display available cars
	private void listAvailableCars() {
		
		rents = connect.BindTableRent(0, 1);
		//create array to store names of cars available
		//loop through array list of available cars objects
		String columnName [] = {"Id","Customer", "Car", "Date Rented", "Date due", "Rent Cost"};
		String [][] dataFromDb = new String[rents.size()][6];
		
		for (int i=0; i<rents.size(); i++) {
			
			for (int j=0; j <5; j++) {
				
				int id = rents.get(i).getRentId();
				String customer = rents.get(i).getCustomer().getFullName().toLowerCase();
				String car = rents.get(i).getCar().getFullCarName().toLowerCase();
				String rentDate = rents.get(i).getDateRented().toLowerCase();
				String rentDue = rents.get(i).getDateDue().toString().toLowerCase();
				float total = rents.get(i).getRentCost();
				
				dataFromDb[i][0] = String.valueOf(id);
				dataFromDb[i][1] = customer;
				dataFromDb[i][2] = car;
				dataFromDb[i][3] = rentDate;
				dataFromDb[i][4] = rentDue;
				dataFromDb[i][5] = String.valueOf(total);
				
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
	    availabilityTable.setForeground(new Color(214,217,220));
	    Color col = new Color(61,67,72);
	    availabilityTable.setSelectionBackground(col);
	    availabilityTable.setBorder(null);
	    availabilityTable.setBackground(colorSp);
		dm = (DefaultTableModel) availabilityTable.getModel();
		availabilityTable.setShowGrid(false);
		availabilityTable.setRowSelectionAllowed(true);
		availabilityTable.setDragEnabled(true);
		
		//some adjustments to the table and adding it to and jscrollpane which is then added to another jpanel sp
		availabilityTable.getColumnModel().getColumn(0).setResizable(false);
		availabilityTable.getColumnModel().getColumn(1).setResizable(false);
		availabilityTable.getColumnModel().getColumn(2).setResizable(false);
		availabilityTable.getColumnModel().getColumn(3).setResizable(false);
		availabilityTable.getColumnModel().getColumn(4).setResizable(false);
		availabilityTable.getTableHeader().setReorderingAllowed(false);
		availabilityTable.setRowHeight(25);
		ListSelectionModel select = availabilityTable.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		sp = new JScrollPane(availabilityTable);
		sp.setBorder(null);
		sp.getViewport().setBackground(colorSp);
		sp.setBounds(10,80, 974, 507);
		sp.setVisible(true);

	}
	
	private void addContentToScreen() {
		contentPane.setLayout(null);
		contentPane.add(dispLogo);
		contentPane.add(searchField);
		contentPane.add(settleRentBtn);
		contentPane.add(backBtn);
		contentPane.add(sp);
		
		JLabel searchLabel = new JLabel("Search");
		searchLabel.setForeground(colortxt);
		searchLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		searchLabel.setBounds(10, 45, 67, 16);
		getContentPane().add(searchLabel);
		
		JButton historyBtn = new JButton("Settled Rents History");
		historyBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		historyBtn.setBackground(buttonCol);
		historyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RentGui rentgui = null;
					rentgui = new RentGui();
				rentgui.setVisible(true);
				dispose();
			}
		});
		historyBtn.setBounds(394, 601, 170, 28);
		getContentPane().add(historyBtn);
		HandlerClass handler = new HandlerClass();
		backBtn.addActionListener(handler);
		
	}
	
	private class HandlerClass implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "Back");
			try {
				if(LoginSession.usertype.equals("clerk")) {
					ClerkMainGui Clerkmain = new ClerkMainGui(null);
					Clerkmain.setVisible(true);
					dispose();
				}
				else if(LoginSession.usertype.equals("manager")) {
					ManagerGui manager = new ManagerGui(null);
					manager.setVisible(true);
					dispose();
				}
				else {
					System.out.println("Log in first");
				}
			}
			catch(Exception ex) {
				System.out.println("exception: " +ex);
			}

		}
		
	}
}
