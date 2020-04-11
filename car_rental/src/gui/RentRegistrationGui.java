package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import car.Car;
import customer.Customer;
import customer.FidelityCard;
import database.DbConnect;
import main.LoginSession;
import rentRegistration.RentRegistration;


public class RentRegistrationGui extends JFrame {
	
	private static final long serialVersionUID = -4007151554919040858L;
	private ImageIcon logoimg;
	private JLabel logoLbl;
	private JPanel contentPane;
	private JTextField makeTf;
	private JTextField modelJt;
	private JTextField cpnTf;
	private JTextField typeTf;
	private JTextField gearTf;
	private JTextField ppdTf;
	private JTextField searchJt;
	private JTextField firstNameJt;
	private JTextField emailJt;
	private JTextField addressJt;
	private JTextField phoneJt;
	private JTextField lastNameJT;
	private JTextField liscenceNoTf;
	private JTextField noDaysTf;
	private JTextField costPdTf;
	private JTextField fidelityTf;
	private JTextField CDITf;
	private JTextField CPTf;
	private JTextField penaltyTf;
	private ImageIcon carImg;
	private String currentPlate;
	private JDateChooser dateOfBirth;
	
	//db process
	private DbConnect connect = new DbConnect();
	private ArrayList<Car>cars;
	private ArrayList<Customer>customers;
	
	private DefaultTableModel dm;
	private int cId;
	private Car selectedCar; 
	private Customer customer;
	private String dateToday; 
	@SuppressWarnings("unused")
	private int customerId;
	private JLabel subTotallbl;
	private JLabel discountLabel;
	private double total;
	private JComboBox<?> genderCb;
	private String [] genderPopulate = {"Male","Female"};
	
	//variables to hold fields names new customer
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private Date selectedDob;
	private int customerPhone;
	private String customerAddress;
	private String customerLicensenum;
	private static String customerGender;
	private Customer selectedCustomer;
	private FidelityCard fid;
	
	//colors
	private Color textCB = new Color(248,250,252);
	private Color buttonCol = new Color(79,99,116);
	private Color colortest = new Color(87,90,92);
	private Color col = new Color(61,67,72);
	private Color colorSp = new Color(87,90,92);
	private Color sideCol = new Color(107,106,103);
	private Color colortxt = new Color(251,241,199);
	
	static RentRegistrationGui rentFrame;
	private JTable availCustomerTable;

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RentRegistrationGui(String getPlate) {
		super("Rent Registration");
		addWindowListener(new WindowAdapter() {
			@Override
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
		setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1010, 678);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(new ImageIcon(LoginGui.class.getResource("icon.png")).getImage());
		
		//main panel
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		
		//logo
		logoimg = new ImageIcon(this.getClass().getResource("companyName.png"));
		panel.setLayout(null);
		logoLbl = new JLabel(logoimg);
		logoLbl.setBounds(387, 18, 225, 30);
		panel.add(logoLbl);
		
		//default customerGender is male
		customerGender = "Male";
		
		//car summary
		cars = connect.getCar(getPlate, -1);
		selectedCar = cars.get(0);
		this.currentPlate = getPlate;
		
		//car panel
		JPanel carInfoPanel = new JPanel();
		carInfoPanel.setBounds(6, 292, 460, 296);
		carInfoPanel.setBackground(sideCol);
		panel.add(carInfoPanel);
		carInfoPanel.setLayout(null);
		
		JLabel carPlateNumJl = new JLabel("CPN:");
		carPlateNumJl.setForeground(textCB);
		carPlateNumJl.setFont(new Font("SansSerif", Font.BOLD, 12));
		carPlateNumJl.setBounds(9, 264, 61, 16);
		carInfoPanel.add(carPlateNumJl);
		
		cpnTf = new JTextField();
		cpnTf.setForeground(new Color(51, 51, 51));
		cpnTf.setBackground(new Color(153, 153, 153));
		cpnTf.setEditable(false);
		cpnTf.setBounds(77, 259, 130, 26);
		carInfoPanel.add(cpnTf);
		cpnTf.setColumns(10);
		
		JLabel modelLbl = new JLabel("Model:");
		modelLbl.setForeground(textCB);
		modelLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
		modelLbl.setBounds(9, 226, 61, 16);
		carInfoPanel.add(modelLbl);
		
		modelJt = new JTextField();
		modelJt.setForeground(new Color(51, 51, 51));
		modelJt.setBackground(new Color(153, 153, 153));
		modelJt.setEditable(false);
		modelJt.setBounds(77, 221, 130, 26);
		carInfoPanel.add(modelJt);
		modelJt.setColumns(10);
		
		JLabel makeLbl = new JLabel("Make:");
		makeLbl.setForeground(textCB);
		makeLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
		makeLbl.setBounds(9, 188, 39, 16);
		carInfoPanel.add(makeLbl);
		
		makeTf = new JTextField();
		makeTf.setForeground(new Color(51, 51, 51));
		makeTf.setBackground(new Color(153, 153, 153));
		makeTf.setEditable(false);
		makeTf.setBounds(77, 183, 130, 26);
		carInfoPanel.add(makeTf);
		makeTf.setColumns(10);
		
		JLabel typeLbl = new JLabel("Type:");
		typeLbl.setForeground(textCB);
		typeLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
		typeLbl.setBounds(219, 188, 61, 16);
		carInfoPanel.add(typeLbl);
		
		JLabel gearLbl = new JLabel("Gear:");
		gearLbl.setForeground(textCB);
		gearLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
		gearLbl.setBounds(219, 226, 61, 16);
		carInfoPanel.add(gearLbl);
		
		JLabel pricePerDayLbl = new JLabel("Price/Day:");
		pricePerDayLbl.setForeground(textCB);
		pricePerDayLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
		pricePerDayLbl.setBounds(219, 264, 81, 16);
		carInfoPanel.add(pricePerDayLbl);
		
		typeTf = new JTextField();
		typeTf.setForeground(new Color(51, 51, 51));
		typeTf.setBackground(new Color(153, 153, 153));
		typeTf.setEditable(false);
		typeTf.setBounds(292, 183, 130, 26);
		carInfoPanel.add(typeTf);
		typeTf.setColumns(10);
		
		gearTf = new JTextField();
		gearTf.setForeground(new Color(51, 51, 51));
		gearTf.setBackground(new Color(153, 153, 153));
		gearTf.setEditable(false);
		gearTf.setBounds(292, 221, 130, 26);
		carInfoPanel.add(gearTf);
		gearTf.setColumns(10);
		
		ppdTf = new JTextField();
		ppdTf.setForeground(new Color(51, 51, 51));
		ppdTf.setBackground(new Color(153, 153, 153));
		ppdTf.setEditable(false);
		ppdTf.setBounds(292, 259, 130, 26);
		carInfoPanel.add(ppdTf);
		ppdTf.setColumns(10);
		
		
		//search panel
		JPanel searchCustomerPanel = new JPanel();
		searchCustomerPanel.setBounds(6, 50, 460, 240);
		searchCustomerPanel.setBackground(colorSp);
		panel.add(searchCustomerPanel);
		searchCustomerPanel.setLayout(null);
		
		//table for displaying customers with no pending rent
		customers = connect.BindTableCustomer(0, 0);
		Object [][] dataFromDb = new Object[customers.size()][3];
		String columnName[] = {"Name","Email","ID"};
	    
	    for(int i=0; i<customers.size(); i++) {
	    	dataFromDb[i][0] = customers.get(i).getFullName().toLowerCase();
    	 	dataFromDb[i][1] = customers.get(i).getEmail();
			dataFromDb[i][2] = String.valueOf(customers.get(i).getCustId());
	    }
	   
	    DefaultTableModel tableModel =  new DefaultTableModel(dataFromDb,columnName) {
			private static final long serialVersionUID = -8353222665595177323L;

			@Override
	        public boolean isCellEditable(int row, int column) {
	           //all cells false
	           return false;
	        }
	    };
	    
	    //adjustments to table
		availCustomerTable = new JTable(tableModel);
		availCustomerTable.setBorder(null);
		availCustomerTable.setBackground(colortest);
		availCustomerTable.setSelectionBackground(col);
		availCustomerTable.setForeground(textCB);
		availCustomerTable.setRowHeight(25);
		dm = (DefaultTableModel) availCustomerTable.getModel();
		
		availCustomerTable.setShowGrid(false);
		availCustomerTable.setRowSelectionAllowed(true);
		availCustomerTable.setDragEnabled(true);
		
		availCustomerTable.getColumnModel().getColumn(0).setResizable(false);
		availCustomerTable.getColumnModel().getColumn(1).setResizable(false);
		availCustomerTable.getColumnModel().getColumn(2).setResizable(false);

		availCustomerTable.getTableHeader().setReorderingAllowed(false);
		
		//another scrollpane for a blank table on boot up
		JScrollPane defaultcsp = new JScrollPane(availCustomerTable);
		defaultcsp.setBorder(null);
		defaultcsp.getViewport().setBackground(colorSp);
		defaultcsp.setBounds(6, 43, 448, 191);
		searchCustomerPanel.add(defaultcsp);
		
		//hides the availCustomerTable 
		defaultcsp.setVisible(false);
		
		// creates and displays blank table on boot up -start
	    DefaultTableModel blankModel = new DefaultTableModel();
	    blankModel.addColumn("Name");
	    blankModel.addColumn("Email");
	    blankModel.addColumn("ID");
	
	    final JTable blankTable = new JTable(blankModel);
	    blankTable.setBorder(null);
	  
	    blankTable.setBackground(UIManager.getColor("CheckBox.disabledText"));
		
	    blankTable.setShowGrid(false);
	    blankTable.setRowSelectionAllowed(true);
	    blankTable.setDragEnabled(true);
	    
	    blankTable.getColumnModel().getColumn(0).setResizable(false);
	    blankTable.getColumnModel().getColumn(1).setResizable(false);
	    blankTable.getColumnModel().getColumn(2).setResizable(false);

	    blankTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane blankcsp = new JScrollPane(blankTable);
		blankcsp.setBorder(null);
		blankcsp.setBounds(6, 43, 448, 191);
		searchCustomerPanel.add(blankcsp);
		blankcsp.setVisible(true);
		blankcsp.getViewport().setBackground(colorSp);
		// creates and displays blank table on boot up - end
		
	    
		//implementing search filtering
		searchJt = new JTextField();
		searchJt.setForeground(Color.WHITE);
		searchJt.setBackground(Color.DARK_GRAY);
		searchJt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				String searchValue = searchJt.getText().toLowerCase();
				String removeEscapeChar = searchValue.replaceAll("[^a-z -.A-Z0-9]", "");
				TableRowSorter<DefaultTableModel> max = new TableRowSorter<DefaultTableModel>(dm);
				availCustomerTable.setRowSorter(max);
				if(removeEscapeChar.length()!=0) {
					max.setRowFilter(RowFilter.regexFilter(removeEscapeChar));
					defaultcsp.setVisible(true);
				}
				else {
					max.setRowFilter(null);
					defaultcsp.setVisible(false);
				}
				
			}
		});
		
		searchJt.setBounds(151, 5, 303, 26);
		searchCustomerPanel.add(searchJt);
		searchJt.setColumns(10);
		
		
		
		//defines the selection model to row
		ListSelectionModel select = availCustomerTable.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		//defines what to do when a row is selected
		select.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				
				//fills the empty fields for rent info on the right
				if(!availCustomerTable.getSelectionModel().isSelectionEmpty()){
				Vector<?> selectedRow = tableModel.getDataVector().elementAt(availCustomerTable.convertRowIndexToModel(availCustomerTable.getSelectedRow()));
					if(selectedRow!=null) {
						cId = Integer.valueOf((String) selectedRow.get(2));
						customer = connect.BindTableCustomer(cId, -1).get(0);
						fidelityTf.setText(String.valueOf(customer.getCustCard().getCardPoints()));
						calTotal();
						CDITf.setText(customer.getLiscenceNo());
						customerId = customer.getCustId();
					}
				}
			}
			
		});
		
		//defines other J components
		JLabel searchlbl = new JLabel("Search Customer:");
		searchlbl.setFont(new Font("SansSerif", Font.BOLD, 15));
		searchlbl.setForeground(textCB);
		searchlbl.setBounds(11, 10, 157, 16);
		searchCustomerPanel.add(searchlbl);
		
		JButton registerRentBtn = new JButton("Register Rent");
		registerRentBtn.setBounds(841, 600, 141, 29);
		registerRentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				//validations
				String numDays = noDaysTf.getText();
				if(numDays.length() == 0) {
					JOptionPane.showMessageDialog(null, "Please enter a value for number of days!","ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else if(!isInteger(numDays)){
					JOptionPane.showMessageDialog(null, "Please enter a number for the number of days!","ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else if(isInteger(numDays)) {
					int numDaysInt = Integer.parseInt(numDays);
					if(numDaysInt<1 || numDaysInt>365 ) {
						JOptionPane.showMessageDialog(null, "Please enter a value between 1 & 365","ERROR", JOptionPane.ERROR_MESSAGE);
					}
					else if(CDITf.getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "Please select a customer","ERROR", JOptionPane.ERROR_MESSAGE);
					}
					else {
						int confirm = JOptionPane.showConfirmDialog(null, "Confirm Rent?","Confirmation",JOptionPane.YES_NO_OPTION);
						if(confirm == 0) {
							
							RentRegistration rentReg;
							try {
				
								rentReg = connect.insertRent(selectedCar, customer, dateToday, numDaysInt, LoginSession.userId);

								RentReceiptGui receipt = new RentReceiptGui(rentFrame, rentReg);
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
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		registerRentBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		registerRentBtn.setBackground(buttonCol);
		panel.add(registerRentBtn);
		
		
		//contains J components mainly for adding New Customer
		JPanel addCustomerJp = new JPanel();
		addCustomerJp.setBounds(468, 50, 514, 240);
		addCustomerJp.setBackground(sideCol);
		panel.add(addCustomerJp);
		addCustomerJp.setLayout(null);
		
		JLabel newClientHeader = new JLabel("Add A New Customer");
		newClientHeader.setFont(new Font("Serif", Font.BOLD, 20));
		newClientHeader.setForeground(colortxt);
		newClientHeader.setBounds(171, 6, 204, 20);
		addCustomerJp.add(newClientHeader);
		
		JLabel cusFirstNameJl = new JLabel("First Name:");
		cusFirstNameJl.setForeground(textCB);
		cusFirstNameJl.setFont(new Font("SansSerif", Font.BOLD, 12));
		cusFirstNameJl.setBounds(6, 45, 78, 16);
		addCustomerJp.add(cusFirstNameJl);
		
		JLabel emailJl = new JLabel("Email:");
		emailJl.setForeground(textCB);
		emailJl.setFont(new Font("SansSerif", Font.BOLD, 12));
		emailJl.setBounds(6, 197, 61, 16);
		addCustomerJp.add(emailJl);
		
		JLabel addressJl = new JLabel("Address:");
		addressJl.setForeground(textCB);
		addressJl.setFont(new Font("SansSerif", Font.BOLD, 12));
		addressJl.setBounds(6, 121, 61, 16);
		addCustomerJp.add(addressJl);
		
		JLabel phoneJl = new JLabel("Phone:");
		phoneJl.setForeground(textCB);
		phoneJl.setFont(new Font("SansSerif", Font.BOLD, 12));
		phoneJl.setBounds(6, 157, 61, 16);
		addCustomerJp.add(phoneJl);
		
		firstNameJt = new JTextField();
		firstNameJt.setBounds(96, 40, 130, 26);
		addCustomerJp.add(firstNameJt);
		firstNameJt.setColumns(10);
		
		emailJt = new JTextField();
		emailJt.setBounds(96, 192, 212, 26);
		addCustomerJp.add(emailJt);
		emailJt.setColumns(10);
		
		addressJt = new JTextField();
		addressJt.setBounds(96, 116, 378, 26);
		addCustomerJp.add(addressJt);
		addressJt.setColumns(10);
		
		phoneJt = new JTextField();
		phoneJt.setBounds(96, 152, 130, 26);
		addCustomerJp.add(phoneJt);
		phoneJt.setColumns(10);
		
		JLabel lastNameJl = new JLabel("Last Name:");
		lastNameJl.setForeground(textCB);
		lastNameJl.setFont(new Font("SansSerif", Font.BOLD, 12));
		lastNameJl.setBounds(254, 45, 78, 16);
		addCustomerJp.add(lastNameJl);
		
		lastNameJT = new JTextField();
		lastNameJT.setBounds(344, 40, 130, 26);
		addCustomerJp.add(lastNameJT);
		lastNameJT.setColumns(10);
		
		JLabel dobJl = new JLabel("DOB:");
		dobJl.setForeground(textCB);
		dobJl.setFont(new Font("SansSerif", Font.BOLD, 12));
		dobJl.setBounds(254, 83, 61, 16);
		addCustomerJp.add(dobJl);
		
		dateOfBirth = new JDateChooser();
		dateOfBirth.setForeground(colortest);
		dateOfBirth.setBounds(344, 78, 130, 26);
		addCustomerJp.add(dateOfBirth);
		
		JLabel genderJl = new JLabel("Gender:");
		genderJl.setForeground(textCB);
		genderJl.setFont(new Font("SansSerif", Font.BOLD, 12));
		genderJl.setBounds(254, 157, 61, 16);
		addCustomerJp.add(genderJl);
		
		genderCb = new JComboBox(genderPopulate);
		genderCb.setBounds(344, 153, 130, 27);
		addCustomerJp.add(genderCb);
		
		JLabel licenceJl = new JLabel("Licence No");
		licenceJl.setForeground(textCB);
		licenceJl.setFont(new Font("SansSerif", Font.BOLD, 12));
		licenceJl.setBounds(6, 83, 89, 16);
		addCustomerJp.add(licenceJl);
		
		liscenceNoTf = new JTextField();
		liscenceNoTf.setBounds(96, 78, 130, 26);
		addCustomerJp.add(liscenceNoTf);
		liscenceNoTf.setColumns(10);
		
		JButton registerCustomerBtn = new JButton("Register Customer");
		registerCustomerBtn.setBackground(buttonCol);
		registerCustomerBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		registerCustomerBtn.setBounds(314, 192, 160, 29);
		addCustomerJp.add(registerCustomerBtn);
		
		
		// Components for selected car info on the left bottom side
		JLabel imageComp = new JLabel();
		imageComp.setForeground(textCB);
		imageComp.setBounds(64, 1, 323, 170);
		carInfoPanel.add(imageComp);
		
		// autofill functionalities must be at the bottom
		makeTf.setText(selectedCar.getCarMake());
		modelJt.setText(selectedCar.getCarModel());
		typeTf.setText(selectedCar.getCarType());
		gearTf.setText(selectedCar.getGear());
		ppdTf.setText(String.valueOf(selectedCar.getCarRate()));
		cpnTf.setText(selectedCar.getCarPlateNumber());
		carImg = new ImageIcon(new ImageIcon(selectedCar.getCarImage()).getImage().getScaledInstance(323, 170, Image.SCALE_SMOOTH));
		imageComp.setIcon(carImg);
		
		
		//components and functionalities for the rent info panel
		JPanel rentPanel = new JPanel();
		rentPanel.setBounds(468, 292, 514, 296);
		rentPanel.setBorder(null);
		rentPanel.setBackground(colorSp);
		panel.add(rentPanel);
		rentPanel.setLayout(null);
		
		JLabel rentInfoLbl = new JLabel("Rent Info");
		rentInfoLbl.setBounds(234, 6, 94, 20);
		rentPanel.add(rentInfoLbl);
		rentInfoLbl.setForeground(colortxt);
		rentInfoLbl.setFont(new Font("Serif", Font.BOLD, 20));
		
		JLabel dateRentedlbl = new JLabel("Date Rented:");
		dateRentedlbl.setBounds(6, 41, 94, 16);
		dateRentedlbl.setFont(new Font("SansSerif", Font.BOLD, 13));
		dateRentedlbl.setForeground(textCB);
		rentPanel.add(dateRentedlbl);
		
		penaltyTf = new JTextField();
		penaltyTf.setForeground(new Color(51, 51, 51));
		penaltyTf.setBackground(new Color(153, 153, 153));
		penaltyTf.setHorizontalAlignment(SwingConstants.CENTER);
		penaltyTf.setEditable(false);
		penaltyTf.setBounds(94, 134, 130, 26);
		rentPanel.add(penaltyTf);
		penaltyTf.setColumns(10);
		penaltyTf.setText(String.valueOf(selectedCar.getPenaltyPrice()));
		
		JLabel noDaysLbl = new JLabel("No Days:");
		noDaysLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
		noDaysLbl.setForeground(textCB);
		noDaysLbl.setBounds(250, 41, 61, 16);
		rentPanel.add(noDaysLbl);
		
		noDaysTf = new JTextField();
		getNoDaysTf().setFont(new Font("SansSerif", Font.PLAIN, 14));
		getNoDaysTf().addKeyListener(new KeyAdapter() {
			
			public void keyReleased(KeyEvent e) {
				calTotal();
			}
		});
		getNoDaysTf().setHorizontalAlignment(SwingConstants.CENTER);
		getNoDaysTf().setBounds(340, 36, 130, 26);
		rentPanel.add(getNoDaysTf());
		getNoDaysTf().setColumns(10);
		
		
		JTextField dateRentedField = new JTextField();
		dateRentedField.setForeground(new Color(51, 51, 51));
		dateRentedField.setBackground(new Color(153, 153, 153));
		dateRentedField.setEditable(false);
		dateRentedField.setHorizontalAlignment(SwingConstants.CENTER);
		dateRentedField.setBounds(94, 36, 130, 26);
		rentPanel.add(dateRentedField);
		dateRentedField.setColumns(10);
		
		//autofill the date rented with todayDate
		LocalDate localDate = LocalDate.now();
        dateToday = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
        dateRentedField.setText(dateToday);
        
		JLabel CusIdLbl = new JLabel("Customer Licence Number:");
		CusIdLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
		CusIdLbl.setForeground(textCB);
		CusIdLbl.setBounds(6, 188, 192, 16);
		rentPanel.add(CusIdLbl);
		
		CDITf = new JTextField();
		CDITf.setForeground(new Color(255, 255, 255));
		CDITf.setBackground(SystemColor.windowBorder);
		CDITf.setEditable(false);
		CDITf.setHorizontalAlignment(SwingConstants.CENTER);
		CDITf.setBounds(199, 183, 271, 26);
		rentPanel.add(CDITf);
		CDITf.setColumns(10);
		
		JLabel carPlate = new JLabel("Car Plate:");
		carPlate.setFont(new Font("SansSerif", Font.BOLD, 13));
		carPlate.setForeground(textCB);
		carPlate.setBounds(6, 88, 78, 16);
		rentPanel.add(carPlate);
		
		
		CPTf = new JTextField();
		CPTf.setHorizontalAlignment(SwingConstants.CENTER);
		CPTf.setForeground(new Color(51, 51, 51));
		CPTf.setBackground(new Color(153, 153, 153));
		CPTf.setEditable(false);
		CPTf.setBounds(94, 83, 130, 26);
		rentPanel.add(CPTf);
		CPTf.setColumns(10);
		CPTf.setText(selectedCar.getCarPlateNumber());
		
		JLabel rentPd = new JLabel("Cost/Day:");
		rentPd.setFont(new Font("SansSerif", Font.BOLD, 13));
		rentPd.setForeground(textCB);
		rentPd.setBounds(250, 88, 78, 16);
		rentPanel.add(rentPd);
		
		costPdTf = new JTextField();
		costPdTf.setForeground(new Color(51, 51, 51));
		costPdTf.setBackground(new Color(153, 153, 153));
		costPdTf.setHorizontalAlignment(SwingConstants.CENTER);
		costPdTf.setEditable(false);
		costPdTf.setBounds(340, 83, 130, 26);
		rentPanel.add(costPdTf);
		costPdTf.setColumns(10);
		costPdTf.setText(String.valueOf(selectedCar.getCarRate()));
		
		JLabel penaltyCostlbl = new JLabel("Penalty/Day");
		penaltyCostlbl.setFont(new Font("SansSerif", Font.BOLD, 13));
		penaltyCostlbl.setForeground(textCB);
		penaltyCostlbl.setBounds(6, 139, 84, 16);
		rentPanel.add(penaltyCostlbl);
		
		JLabel customerPointsLbl = new JLabel("Fidelity Pts:");
		customerPointsLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
		customerPointsLbl.setForeground(textCB);
		customerPointsLbl.setBounds(250, 139, 104, 16);
		rentPanel.add(customerPointsLbl);
		
		fidelityTf = new JTextField();
		fidelityTf.setForeground(new Color(255, 255, 255));
		fidelityTf.setBackground(SystemColor.windowBorder);
		fidelityTf.setHorizontalAlignment(SwingConstants.CENTER);
		fidelityTf.setEditable(false);
		fidelityTf.setBounds(340, 134, 130, 26);
		rentPanel.add(fidelityTf);
		fidelityTf.setColumns(10);
		
		JLabel totalLbl = new JLabel("TOTAL:");
		totalLbl.setForeground(Color.WHITE);
		totalLbl.setHorizontalAlignment(SwingConstants.CENTER);
		totalLbl.setFont(new Font("Serif", Font.BOLD, 18));
		totalLbl.setBounds(234, 221, 78, 16);
		rentPanel.add(totalLbl);
		
		subTotallbl = new JLabel("");
		subTotallbl.setForeground(Color.RED);
		subTotallbl.setFont(new Font("Serif", Font.BOLD, 30));
		subTotallbl.setHorizontalAlignment(SwingConstants.CENTER);
		subTotallbl.setBounds(139, 237, 252, 53);
		rentPanel.add(subTotallbl);
		
		discountLabel = new JLabel("");
		discountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		discountLabel.setBounds(38, 278, 432, 16);
		rentPanel.add(discountLabel);

		//defines what to do when the back btn is clicked
		JButton backBtn = new JButton("Back");
		backBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		backBtn.setBackground(buttonCol);
		backBtn.setBounds(10, 600, 100, 30);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(LoginSession.usertype.equals("clerk")) {
					//sends the selected plate number to the clerkMainGui
					ClerkMainGui cmain = new ClerkMainGui(currentPlate);
					cmain.setVisible(true);
					dispose();
				}
				else if(LoginSession.usertype.equals("manager")) {
				//sends the selected plate number to the manager gui
					ManagerGui manG = new ManagerGui(currentPlate);
					manG.setVisible(true);
					dispose();
				}
			}
		});
		panel.add(backBtn);
		
		
		HandlerClass handler = new HandlerClass();
		registerCustomerBtn.addActionListener(handler);
		genderCb.addItemListener(new ItemClass());
	}

	/**
	 * @return the noDaysTf
	 */
	
	public void close() {
		dispose();
	}
	
	public JTextField getNoDaysTf() {
		return noDaysTf;
	}
	
	//method to check if value is integer
	public boolean isInteger(String str) {
			
			try {
				
				Integer.parseInt(str);
				return true;
				
			} catch (NumberFormatException e) {
				
				return false;
			}
		}
	
	
	public void calTotal() {
		String numOfDays =getNoDaysTf().getText();
		boolean numeric = true;
		Integer numDays = null;

        try {
            numDays =Integer.parseInt(numOfDays);
        } catch (NumberFormatException e1) {
            numeric = false;
        }
        
		
		if(numeric) {
			if(numDays>=1) {
				if(fidelityTf.getText().length()==0) {
						 total = numDays*(selectedCar.getCarRate());
						subTotallbl.setText(String.valueOf(total));
						discountLabel.setText("");
					
				}
				else if(fidelityTf.getText().length()!=0) {
					int fp = Integer.parseInt(fidelityTf.getText());
					if(fp<90) {
						 total = numDays*(selectedCar.getCarRate());
						subTotallbl.setText(String.valueOf(total));
						discountLabel.setText("");
					}
					else {
						 total = (numDays*(selectedCar.getCarRate()))/2;
						subTotallbl.setText(String.valueOf(total));
						discountLabel.setText("100pts reached, Rent is at half price! :) ");
					}
				}
			}
		}
		else {
			subTotallbl.setText(null);
		}
	}
	private class HandlerClass implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			 
			//performs validation if registerCustomerBtn is clicked
			if (event.getActionCommand() == "Register Customer") {
				
				String message = "Invalid";
				if(dateOfBirth.getDate() == null) {
					message+= " (DOB)";
				}	
				else {
					selectedDob = dateOfBirth.getDate();
				}
		
				//validate first name
				if ( (firstNameJt.getText().length() == 0) || (firstNameJt.getText().length() > 15) ) {
					
					message += " (First Name)";
					firstNameJt.setText(null);
					
				}else {
					
					customerFirstName = firstNameJt.getText();
				}
				
				//validate last name
				if ( (lastNameJT.getText().length() == 0) || (lastNameJT.getText().length() > 15) ) {
					
					message += " (Last Name)";
					lastNameJT.setText(null);
					
				}else {
					
					customerLastName = lastNameJT.getText();
				}
				
				//validate email
				if (isValidEmailAddress(emailJt.getText())){


					customerEmail = emailJt.getText();
				}else {
					
					
					message += " (Email)";
					emailJt.setText(null);
				}

				//validate phone
				if ( !(phoneJt.getText().length() == 8) ) {	
					message += " (Phone - < 8 digits)";
					phoneJt.setText(null);
					
				}else {
					
					customerPhone = Integer.valueOf(phoneJt.getText());
				}
				
				//validate address
				if ( (addressJt.getText().length() == 0) ) {
					
					message += " (Address)";
					addressJt.setText(null);
					
				}else {
					
					customerAddress = addressJt.getText();
				}
				
				//validate licenseNum
				if ( (liscenceNoTf.getText().length() == 0) || (liscenceNoTf.getText().length() > 30) ) {
					
					message += " (License No.)";
					liscenceNoTf.setText(null);
					
				}else {
					
					customerLicensenum = liscenceNoTf.getText();
				}

				if (message.equalsIgnoreCase("Invalid")) {
					int confirm = JOptionPane.showConfirmDialog(null, String.format("Are you sure you want to register customer %s ?", customerLastName),"Confirmation",JOptionPane.YES_NO_OPTION);
						if(confirm == 0) {
							//checks if query is successful
							if (connect.insertCustomer(customerFirstName, customerLastName, customerEmail, 
									selectedDob, customerPhone, customerAddress, customerLicensenum, 
									customerGender, LoginSession.userId)) {
								JOptionPane.showMessageDialog(null, "Customer Registered And Selected");
								
								fid = new FidelityCard(connect.getCustomerId(customerLicensenum), customerFirstName, customerLastName, customerEmail, 0);
								selectedCustomer = new Customer(connect.getCustomerId(customerLicensenum), customerFirstName, customerLastName, customerEmail, selectedDob, customerPhone, customerAddress, customerLicensenum, customerGender, LoginSession.userId, 0, fid);
								customer = selectedCustomer;
								fidelityTf.setText(String.valueOf(0));;
								CDITf.setText(customerLicensenum);
								firstNameJt.setText(null);
								lastNameJT.setText(null);
								emailJt.setText(null);
								dateOfBirth.setDate(null);
								phoneJt.setText(null);
								addressJt.setText(null);
								liscenceNoTf.setText(null);
								genderCb.setSelectedIndex(0);
								
							}else {
								//if query fails
								JOptionPane.showMessageDialog(null, "Error in saving to Database");
							
							}
						}
						
				}else if (!message.equalsIgnoreCase("Invalid")) {
					
					JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE);
					
					}
			
				
				}
		}
	}
	//class to handle events with combo boxes
	private class ItemClass implements ItemListener{
		//listens for event to occur with combo boxes
		public void itemStateChanged(ItemEvent event) {
			customerGender = genderPopulate[genderCb.getSelectedIndex()];

		}
		
	}
	
	//method to validate email
	public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}


}
