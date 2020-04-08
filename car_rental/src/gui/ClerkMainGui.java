  
package gui;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import car.Car;
import database.DbConnect;
import main.LogoutSession;

public class ClerkMainGui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	//Connect to Database
	DbConnect connect = new DbConnect();
	private ArrayList<Car> cars = connect.getCar(null, 1);  
	
	//attributes for ManagerGui
	private JButton settleRent;
	private JButton next;
	private JTextField searchText;
	private JButton searchBtn;
	private JButton logout;

	private JTextField modelField;
	private JTextField makeField;
	private JLabel type;
	private JLabel year;
	private JLabel engineSize;
	private JLabel gear;
	private JLabel trunkSize;
	private JLabel fuel;
	private JLabel rate;
	private JLabel penalty;
	private JLabel available;
	private JTextField engineField;
	private JTextField trunkField;
	private JTextField typeField;
	private JTextField rateField;
	private JTextField penaltyField;
	private JTextField yearField;
	private JTextField gearField;
	private JTextField fuelField;
	private JTextField availableField;
	private JTextField cpnField;
	private JLabel cpn;
	private JLabel noSeatJL;
	private JTextField noSeatJT;
	private JPanel Detailpane;
	private static String plateValue;
	private JLabel imageComponent;
	private JTextField ratingField;
	
	//previously selected car in rent registration and back
	private ArrayList<Car> car;
	private Car selCar;
	private String getPlate;
	private int currentRow;
	

public ClerkMainGui(String revertPlate) {
	super("Clerk Interface");
	addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			LoginGui login = null;
			login = new LoginGui();
			login.setVisible(true);
		}
	});
	this.getContentPane().setLayout(null);
	this.setSize(1010,678);
	this.setResizable(false);
	this.setLocationRelativeTo(null);
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	this.getContentPane().setBackground(Color.LIGHT_GRAY);
	this.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\icon.png"));
	
	//logo
	ImageIcon logoimg = new ImageIcon(this.getClass().getResource("companyName.png"));
	JLabel logoLbl = new JLabel(logoimg);
	logoLbl.setBounds(404,16,200,30);
	this.getContentPane().add(logoLbl);
	
	//Instantiate attributes
	settleRent = new JButton("Settle Rent");
	settleRent.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			SettleRentGui settleRent = new SettleRentGui();
			settleRent.setVisible(true);
			dispose();
		}
	});
	settleRent.setBounds(10, 600, 100, 30);
	
	searchBtn = new JButton("Search");
	searchBtn.setBounds(149,38, 100,30);
	
	searchText = new JTextField(20);
	searchText.setBounds(10,38,140,30);

	
	cars = connect.bindTable(null, 1);
	//create array list to store names of cars available
	//loop through array list of available cars objects
	String columnName [] = {"Plate Number","Name","Seats", "Gear", "Price (MUR)"};
	Object [][] dataFromDb = new Object[cars.size()][5];
	
    for(int i = 0; i < cars.size(); i++){
    	
    	//stores selected car row if the back button is clicked on the RentRegistrationGui
    	if(cars.get(i).getCarPlateNumber().equalsIgnoreCase(revertPlate)) {
    		currentRow = i;
    	}
    	
    	//populates cars data in an object 2D array dataFromDb
    	 	dataFromDb[i][0] = cars.get(i).getCarPlateNumber();
    	 	dataFromDb[i][1] = cars.get(i).getFullCarName();
			dataFromDb[i][2] = cars.get(i).getSeats();
			dataFromDb[i][3] = cars.get(i).getGear();
			dataFromDb[i][4] = cars.get(i).getCarRate();
    }
 
    //creates table with data from dataFromDb and columnName
	final JTable availabilityTable = new JTable(dataFromDb,columnName);
	availabilityTable.setShowGrid(false);
	availabilityTable.setRowSelectionAllowed(true);
	availabilityTable.setDragEnabled(true);
	
	
	//selects the row of previously selected row if the back button is clicked on the RentRegistrationGui
	if(revertPlate != null) {
	availabilityTable.setRowSelectionInterval(currentRow, currentRow);
	}
	
	// else selects the first row on boot up
	else {
		availabilityTable.setRowSelectionInterval(0, 0);
	}
	
	//defines the selection model in variable called select
	final ListSelectionModel select = availabilityTable.getSelectionModel();
	
	//defines a complete row selection
	select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	
	//defines what to do when a selection is made
	select.addListSelectionListener(new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent e) {
			//fills the different fields on the right
			int rowSelected = availabilityTable.getSelectedRow();
			plateValue = availabilityTable.getModel().getValueAt(rowSelected, 0).toString();
			car = connect.getCar(plateValue, -1);
			selCar = car.get(0);
			modelField.setText(selCar.getCarModel());
			makeField.setText(selCar.getCarMake());
			typeField.setText(selCar.getCarType());
			yearField.setText(String.valueOf(selCar.getCarYear()));
			gearField.setText(selCar.getGear());
			fuelField.setText(selCar.getCarFuelType());
			engineField.setText(String.valueOf(selCar.getEngineSize()) + "L");
			trunkField.setText(String.valueOf(selCar.getCarTrunkSize()) + "L");
			rateField.setText("Rs "+String.valueOf(selCar.getCarRate()));
			penaltyField.setText("Rs "+String.valueOf(selCar.getPenaltyPrice()));
			noSeatJT.setText(String.valueOf(selCar.getCarSeatNo()));
			ratingField.setText(String.valueOf(selCar.getCarRating())); 
			if(selCar.isCarAvailability()) {
			availableField.setText("Yes");
			}
			else{
				availableField.setText("No");
			}
			
			cpnField.setText(selCar.getCarPlateNumber());
			
			ImageIcon image = new ImageIcon(new ImageIcon(selCar.getCarImage()).getImage().getScaledInstance(401, 198, Image.SCALE_SMOOTH));  
			imageComponent.setIcon(image);
			getPlate = cpnField.getText();
			
		}
	});
	
	//some adjustments to the table and adding it to and jscrollpane which is then added to another jpanel sp
	availabilityTable.getColumnModel().getColumn(0).setResizable(false);
	availabilityTable.getColumnModel().getColumn(1).setResizable(false);
	availabilityTable.getColumnModel().getColumn(2).setResizable(false);
	availabilityTable.getColumnModel().getColumn(3).setResizable(false);
	availabilityTable.getColumnModel().getColumn(4).setResizable(false);
	availabilityTable.getTableHeader().setReorderingAllowed(false);
	JScrollPane sp = new JScrollPane(availabilityTable);
	sp.setBounds(10,80, 568, 510);
	sp.setVisible(true);
	
	
	//defines the logout button and what to do what it is clicked
	logout = new JButton("Logout");
	logout.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int value=JOptionPane.showConfirmDialog(getParent(), "Are you sure to Log Out?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if(value==JOptionPane.YES_OPTION) {
				LoginGui login = new LoginGui();
				LogoutSession.logoutH(login);
				dispose();
			}
		}
	});
	logout.setBounds(843,38,100,30);
	
	//add to the main jpanel
	getContentPane().add(settleRent);
	getContentPane().add(searchText);
	getContentPane().add(searchBtn);
	getContentPane().add(logout);
	getContentPane().add(sp);
	
	
	//defines the text fields on the right for the details of the selected car
	imageComponent = new JLabel();
	imageComponent.setBounds(582, 87, 401, 198);
	getContentPane().add(imageComponent);
	
	JLabel Model = new JLabel("Model:");
	Model.setBounds(590, 309, 50, 16);
	getContentPane().add(Model);
	
	JLabel Make = new JLabel("Make:");
	Make.setBounds(590, 343, 50, 16);
	getContentPane().add(Make);
	
	modelField = new JTextField();
	modelField.setBackground(Color.LIGHT_GRAY);
	modelField.setHorizontalAlignment(SwingConstants.CENTER);
	modelField.setEditable(false);
	modelField.setBounds(635, 303, 112, 28);
	getContentPane().add(modelField);
	modelField.setColumns(10);
	
	makeField = new JTextField();
	makeField.setBackground(Color.LIGHT_GRAY);
	makeField.setHorizontalAlignment(SwingConstants.CENTER);
	makeField.setEditable(false);
	makeField.setBounds(635, 337, 112, 28);
	getContentPane().add(makeField);
	makeField.setColumns(10);
	
	cpnField = new JTextField();
	cpnField.setBackground(Color.LIGHT_GRAY);
	cpnField.setHorizontalAlignment(SwingConstants.CENTER);
	cpnField.setEditable(false);
	cpnField.setBounds(635, 371, 112, 28);
	getContentPane().add(cpnField);
	cpnField.setColumns(10);
	
	cpn = new JLabel("CPN:");
	cpn.setBounds(590, 380, 50, 16);
	getContentPane().add(cpn);
	
	Detailpane = new JPanel();
	Detailpane.setForeground(Color.LIGHT_GRAY);
	Detailpane.setBorder(new LineBorder(Color.GRAY));
	Detailpane.setBounds(581, 297, 401, 290);
	getContentPane().add(Detailpane);
	
	typeField = new JTextField();
	typeField.setBackground(Color.LIGHT_GRAY);
	typeField.setHorizontalAlignment(SwingConstants.CENTER);
	typeField.setEditable(false);
	typeField.setBounds(55, 131, 112, 28);
	typeField.setColumns(10);
	
	year = new JLabel("Year:");
	year.setBounds(6, 177, 50, 16);
	
	type = new JLabel("Type:");
	type.setBounds(6, 137, 41, 16);
	Detailpane.setLayout(null);
	Detailpane.add(year);
	Detailpane.add(type);
	Detailpane.add(typeField);
	
	
	
	//defines the selected car on boot up
	//fills the fields with the details of previously selected car
	//or fills the fields with the first car in the table
	if(revertPlate != null) {
		car = connect.getCar(revertPlate, -1);
		selCar = car.get(0);
	}
	else {
		selCar = cars.get(0);
	}
	
	modelField.setText(selCar.getCarModel());
	makeField.setText(selCar.getCarMake());
	typeField.setText(selCar.getCarType());
	
	fuelField = new JTextField();
	fuelField.setBackground(Color.LIGHT_GRAY);
	fuelField.setHorizontalAlignment(SwingConstants.CENTER);
	fuelField.setBounds(55, 256, 112, 28);
	Detailpane.add(fuelField);
	fuelField.setEditable(false);
	fuelField.setColumns(10);
	fuelField.setText(selCar.getCarFuelType());
	
	fuel = new JLabel("Fuel:");
	fuel.setBounds(6, 262, 50, 16);
	Detailpane.add(fuel);
	
	gear = new JLabel("Gear:");
	gear.setBounds(6, 222, 50, 16);
	Detailpane.add(gear);
	
	gearField = new JTextField();
	gearField.setBackground(Color.LIGHT_GRAY);
	gearField.setHorizontalAlignment(SwingConstants.CENTER);
	gearField.setBounds(55, 216, 112, 28);
	Detailpane.add(gearField);
	gearField.setEditable(false);
	gearField.setColumns(10);
	gearField.setText(selCar.getGear());
	
	yearField = new JTextField();
	yearField.setBackground(Color.LIGHT_GRAY);
	yearField.setHorizontalAlignment(SwingConstants.CENTER);
	yearField.setBounds(55, 171, 50, 28);
	Detailpane.add(yearField);
	yearField.setEditable(false);
	yearField.setColumns(10);
	yearField.setText(String.valueOf(selCar.getCarYear()));
	
	available = new JLabel("Available:");
	available.setBounds(225, 262, 66, 16);
	Detailpane.add(available);
	
	availableField = new JTextField();
	availableField.setBackground(Color.LIGHT_GRAY);
	availableField.setHorizontalAlignment(SwingConstants.CENTER);
	availableField.setBounds(309, 256, 41, 28);
	Detailpane.add(availableField);
	availableField.setEditable(false);
	availableField.setColumns(10);
	
	penalty = new JLabel("Penalty fee:");
	penalty.setBounds(225, 177, 96, 16);
	Detailpane.add(penalty);
	
	rate = new JLabel("Rate per day:");
	rate.setBounds(225, 137, 94, 16);
	Detailpane.add(rate);
	
	penaltyField = new JTextField();
	penaltyField.setBackground(Color.LIGHT_GRAY);
	penaltyField.setHorizontalAlignment(SwingConstants.CENTER);
	penaltyField.setBounds(309, 171, 86, 28);
	Detailpane.add(penaltyField);
	penaltyField.setEditable(false);
	penaltyField.setColumns(10);
	penaltyField.setText("Rs "+String.valueOf(selCar.getPenaltyPrice()));
	
	rateField = new JTextField();
	rateField.setBackground(Color.LIGHT_GRAY);
	rateField.setHorizontalAlignment(SwingConstants.CENTER);
	rateField.setBounds(309, 131, 86, 28);
	Detailpane.add(rateField);
	rateField.setEditable(false);
	rateField.setColumns(10);
	rateField.setText("Rs "+String.valueOf(selCar.getCarRate()));
	engineField = new JTextField();
	engineField.setBackground(Color.LIGHT_GRAY);
	engineField.setBounds(319, 6, 76, 28);
	Detailpane.add(engineField);
	engineField.setHorizontalAlignment(SwingConstants.CENTER);
	engineField.setEditable(false);
	engineField.setColumns(10);
	engineField.setText(String.valueOf(selCar.getEngineSize()) +"L");
	trunkField = new JTextField();
	trunkField.setBackground(Color.LIGHT_GRAY);
	trunkField.setBounds(319, 46, 76, 28);
	Detailpane.add(trunkField);
	trunkField.setHorizontalAlignment(SwingConstants.CENTER);
	trunkField.setEditable(false);
	trunkField.setColumns(10);
	trunkField.setText(String.valueOf(selCar.getCarTrunkSize()) + "L");
	
	noSeatJL = new JLabel("No of seats:");
	noSeatJL.setBounds(225, 78, 96, 28);
	Detailpane.add(noSeatJL);
	
	noSeatJT = new JTextField();
	noSeatJT.setBackground(Color.LIGHT_GRAY);
	noSeatJT.setBounds(319, 78, 28, 28);
	Detailpane.add(noSeatJT);
	noSeatJT.setHorizontalAlignment(SwingConstants.CENTER);
	noSeatJT.setEditable(false);
	noSeatJT.setText(String.valueOf(selCar.getCarSeatNo()));
	
	engineSize = new JLabel("Engine Size:");
	engineSize.setBounds(225, 12, 94, 16);
	Detailpane.add(engineSize);
	
	trunkSize = new JLabel("Trunk Size:");
	trunkSize.setBounds(225, 52, 94, 16);
	Detailpane.add(trunkSize);
	
	JLabel ratingLabel = new JLabel("Rating: ");
	ratingLabel.setBounds(225, 222, 50, 16);
	
	Detailpane.add(ratingLabel);
	
	ratingField = new JTextField();
	ratingField.setBackground(Color.LIGHT_GRAY);
	ratingField.setHorizontalAlignment(SwingConstants.CENTER);
	ratingField.setBounds(309, 216, 28, 28);
	ratingField.setEditable(false);
	Detailpane.add(ratingField);
	ratingField.setColumns(10);
	ratingField.setText(String.valueOf(selCar.getCarRating()));
	if(selCar.isCarAvailability()) {
	availableField.setText("Yes");
	}
	else{
		availableField.setText("No");
	}
	
	cpnField.setText(selCar.getCarPlateNumber());
	
	ImageIcon image = new ImageIcon(new ImageIcon(selCar.getCarImage()).getImage().getScaledInstance(401, 198, Image.SCALE_SMOOTH));  
	imageComponent.setIcon(image);
	
	
	getPlate = cpnField.getText();
	
	
		//defines the next button and send the selected car plate number to the rentRegistrationGui
		next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RentRegistrationGui regCus = new RentRegistrationGui(getPlate);
				regCus.setVisible(true);
				regCus.getNoDaysTf().requestFocus();
				dispose();
			}
		});
		next.setBounds(883,600,100,30);
		getContentPane().add(next);

		//setting button for the employee to change their password
		JButton settingButton = new JButton("New button");
		settingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingGui gui = new SettingGui();
				gui.setVisible(true);
			}
		});
		settingButton.setBounds(945, 39, 38, 28);
		getContentPane().add(settingButton);
	}
}