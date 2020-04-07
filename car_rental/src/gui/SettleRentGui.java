package gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import database.DbConnect;
import main.LoginSession;
import rentRegistration.RentRegistration;

public class SettleRentGui extends JFrame{

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
	

	private static DbConnect connect = new DbConnect();
	private static ArrayList<RentRegistration> rents;
	private JTable availabilityTable;
	
	static SettleRentGui rentFrame;
	
	public SettleRentGui() {
		super("Settle Rent");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\icon.png"));
		logoimg = new ImageIcon(getClass().getResource("companyName.png"));
		dispLogo = new JLabel(logoimg);
		dispLogo.setBounds(404,16,200,30);

		// search field
		searchField = new JTextField();
		searchField.setBounds(79,50,200,30);
	}
	
	private void footerBar() {
		// footer settle rent btn
		
		// next btn
		settleRentBtn = new JButton("Settle Rent");
		settleRentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to settle this rent?","Confirmation",JOptionPane.YES_NO_OPTION);
				
					if(confirm==0) {
					
					int rowSelected = availabilityTable.getSelectedRow();
						if(rowSelected!=0) {
						String rentId = availabilityTable.getModel().getValueAt(rowSelected, 0).toString();
						RentRegistration selectedRent = connect.BindTableRent(Integer.valueOf(rentId), -1).get(0);
						System.out.println(selectedRent);
						
//						LocalDate localDate = LocalDate.now();
//				        String dateToday = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
						
						String dateToday = "2020-04-17";
				        
				        try {
				        	
							selectedRent.setDateReturned(dateToday);
							System.out.println(selectedRent);
							if(connect.updateRent(selectedRent)) {
								System.out.println("Nice!");
								
								ClerkMainGui clerk = new ClerkMainGui(null);
								RentFinalReceipt receipt = new RentFinalReceipt(rentFrame, selectedRent);
								receipt.setVisible(true);
								dispose();
								clerk.setVisible(true);
							}
							else {
								JOptionPane.showMessageDialog(null, "There has been an error! Re-try", "Error", JOptionPane.ERROR_MESSAGE);
							}
							
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		settleRentBtn.setBounds(837,600,143,30);
		// back btn
		backBtn = new JButton("Back");
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
				String customer = rents.get(i).getCustomer().getFullName();
				String car = rents.get(i).getCar().getFullCarName();
				String rentDate = rents.get(i).getDateRented();
				String rentDue = rents.get(i).getDateDue().toString();
				float total = rents.get(i).getRentCost();
				
				dataFromDb[i][0] = String.valueOf(id);
				dataFromDb[i][1] = customer;
				dataFromDb[i][2] = car;
				dataFromDb[i][3] = rentDate;
				dataFromDb[i][4] = rentDue;
				dataFromDb[i][5] = String.valueOf(total);
				
			}
		}
		availabilityTable = new JTable(dataFromDb,columnName);
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
		
		ListSelectionModel select = availabilityTable.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//		select.addListSelectionListener(new ListSelectionListener(){
//			public void valueChanged(ListSelectionEvent e) {
//				String data = null;
//				int [] row = availabilityTable.getSelectedRows();
//				int [] column = availabilityTable.getSelectedColumns();
//				for(int i=0; i<row.length;i++) {
//					for(int j=0; j<column.length; j++) {
//						data = (String) availabilityTable.getValueAt(row[i], column[j]);
//					}
//				}
//				System.out.println("Table element selected is: "+data);
//			}
//		});
		sp = new JScrollPane(availabilityTable);
		sp.setBounds(6,84, 977, 514);
		sp.setVisible(true);

	}
	
	private void addContentToScreen() {
		contentPane.setLayout(null);
		contentPane.add(dispLogo);
		contentPane.add(searchField);
		contentPane.add(settleRentBtn);
		contentPane.add(backBtn);
		contentPane.add(sp);
		
		JLabel lblNewLabel = new JLabel("Search");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 19));
		lblNewLabel.setBounds(10, 54, 90, 16);
		getContentPane().add(lblNewLabel);
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
					ManagerGui manager = new ManagerGui();
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
