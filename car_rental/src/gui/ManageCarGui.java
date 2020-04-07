package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import car.Car;
import database.DbConnect;
import main.LoginSession;
import javax.swing.JPanel;

public class ManageCarGui extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//attributes for buttons , text fields, combo boxes and images icons
	private JTextField plateNo;
	private JTextField make;
	private JTextField model;
	private JComboBox<?> type;
	private JTextField year;
	private JComboBox<?> transmission;
	private JComboBox<?> fuel;
	private JComboBox<?> seat;
	private JTextField price;
	private JTextField penalty;
	private JTextField trunk;
	private JTextField engine;
	private JComboBox<?> rating;
	private JTextField registeredBy;
	private JComboBox<?> availability;
	private JComboBox<?> updateAvailableCb;
	private JButton back;
	private JButton submit;
	private ImageIcon logo;
	private JButton browse;
	private JLabel imageLocation;
	private JLabel carImgAddNewCar;
	private ImageIcon browseImg;
	
	//attributes to hold values from text boxes
	private String carPlateNo;
	private String carMake;
	private String carModel;
	private String carType;
	private int carYear;
	private String carGear;
	private FileInputStream fis;
	private int fileLength;
	private String carFuel;
	private int carSeats;
	private double carPricePerDay;
	private double carPenalty;
	private double carTrunkSize;
	private int carEngineSize;
	private int carRating;
	private int empId;
	private int carAvailability;
	private int updateCarAvailable = 1;
	private String updatePlateNo;
	
	//array for gear types
	private static String[] gears = {"Manual", "Automatic", "Both"};
	//array for fuel types
	private static String[] fuels = {"Petrol", "Diesel", "Hybrid"};
	//array for number of seats
	private static String[] seats = {"2","3","4","5","6","7","8"};
	//array for ratings
	private static String[] ratings = {"1","2","3","4","5"};
	//array for availability
	private static String[] available = {"True","False"};
	//array for car types
	private static String[] types = {"Compact", "Sedan", "Convertible", "Sport", "Luxury", "SUV"};
	private JPanel panel_1;
	private JLabel lblNewLabel_1;
	private JTextField carPlate;
	private JTextField carPlateUpdate;
	private JTextField updateMake;
	private JTextField updateModel;

	
	//defining constructor
	public ManageCarGui() {
		//title
		super("Manage Car");
		getContentPane().setLayout(null);
		this.setSize(1010,678);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\icon.png"));

		//font for text
		Font font = new Font("Sans Serif", Font.BOLD,14);
		
		//registered by 
		registeredBy = new JTextField(14);
		registeredBy.setBounds(500,380,150,30);
		JLabel registerLabel = new JLabel("Manger ID  ");
		registerLabel.setFont(font);
		registerLabel.setForeground(Color.WHITE);
		registerLabel.setBounds(430,380,100,30);
		
		//back
		back = new JButton("Back");
		back.setBounds(16, 619, 100,30);
		
		//logo
		logo = new ImageIcon(this.getClass().getResource("companyName.png"));
		JLabel companyLogo = new JLabel(logo);
		companyLogo.setBounds(404,16,200,30);
		getContentPane().add(back);
		getContentPane().add(companyLogo);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 51, 610, 556);
		getContentPane().add(panel);
		panel.setLayout(null);
		JLabel plateLabel = new JLabel("Plate No. ");
		plateLabel.setBounds(6, 60, 69, 17);
		panel.add(plateLabel);
		plateLabel.setFont(font);
		plateLabel.setForeground(Color.BLACK);
		
		//instantiate attributes
		//plateNo
		plateNo = new JTextField(14);
		plateNo.setBounds(101, 54, 150, 30);
		panel.add(plateNo);
		
		JLabel lblNewLabel = new JLabel("Add A New Car");
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 16));
		lblNewLabel.setBounds(212, 12, 116, 30);
		panel.add(lblNewLabel);
		JLabel makeLabel = new JLabel("Make ");
		makeLabel.setBounds(6, 100, 51, 30);
		panel.add(makeLabel);
		makeLabel.setFont(font);
		makeLabel.setForeground(Color.BLACK);
		
		
		//make
		make = new JTextField(14);
		make.setBounds(101, 101, 150, 30);
		panel.add(make);
		JLabel modelLabel = new JLabel("Model ");
		modelLabel.setBounds(6, 143, 51, 30);
		panel.add(modelLabel);
		modelLabel.setFont(font);
		modelLabel.setForeground(Color.BLACK);
		
		
		//model
		model = new JTextField(14);
		model.setBounds(101, 143, 150, 30);
		panel.add(model);
		JLabel typeLabel = new JLabel("Type ");
		typeLabel.setBounds(6, 185, 51, 30);
		panel.add(typeLabel);
		typeLabel.setFont(font);
		typeLabel.setForeground(Color.BLACK);
		
		//type
		type = new JComboBox<Object>(types);
		type.setBounds(101, 185, 150, 30);
		panel.add(type);
		JLabel transmissionLabel = new JLabel("Gear ");
		transmissionLabel.setBounds(6, 227, 51, 30);
		panel.add(transmissionLabel);
		transmissionLabel.setFont(font);
		transmissionLabel.setForeground(Color.BLACK);
		
		//transmission
		transmission = new JComboBox<Object>(gears);
		transmission.setBounds(101, 227, 150, 30);
		panel.add(transmission);
		JLabel fuelLabel = new JLabel("Fuel ");
		fuelLabel.setBounds(6, 269, 100, 30);
		panel.add(fuelLabel);
		fuelLabel.setFont(font);
		fuelLabel.setForeground(Color.BLACK);
		
		//fuel
		fuel = new JComboBox<Object>(fuels);
		fuel.setBounds(101, 271, 150, 30);
		panel.add(fuel);
		JLabel yearLabel = new JLabel("Year ");
		yearLabel.setBounds(6, 312, 100, 30);
		panel.add(yearLabel);
		yearLabel.setFont(font);
		yearLabel.setForeground(Color.BLACK);
		

		//year
		year = new JTextField(14);
		year.setBounds(101, 311, 150, 30);
		panel.add(year);
		
		//image
//		image = new JTextField(14);
//		image.setBounds(120,380,150,30);
		JLabel imageLabel = new JLabel("Image ");
		imageLabel.setBounds(6, 348, 100, 30);
		panel.add(imageLabel);
		imageLabel.setFont(font);
		imageLabel.setForeground(Color.BLACK);
		browse = new JButton("Browse");
		browse.setBounds(101, 350, 100, 30);
		panel.add(browse);
		
		//submit
		submit = new JButton("Register");
		submit.setBounds(469, 520, 100, 30);
		panel.add(submit);
		JRootPane rootPane = SwingUtilities.getRootPane(submit); 
		rootPane.setDefaultButton(submit);
		
		//image location text
		imageLocation = new JLabel("");
		imageLocation.setBounds(212, 353, 357, 30);
		panel.add(imageLocation);
		imageLocation.setFont(new Font("Sans Serif",Font.BOLD,14));
		imageLocation.setForeground(Color.BLACK);
		
		
		//seats
		seat = new JComboBox<Object>(seats);
		seat.setBounds(419, 55, 150, 30);
		panel.add(seat);
		JLabel seatLabel = new JLabel("Seats ");
		seatLabel.setBounds(318, 53, 100, 30);
		panel.add(seatLabel);
		seatLabel.setFont(font);
		seatLabel.setForeground(Color.BLACK);
		JLabel priceLabel = new JLabel("Price ");
		priceLabel.setBounds(318, 100, 100, 30);
		panel.add(priceLabel);
		priceLabel.setFont(font);
		priceLabel.setForeground(Color.BLACK);
		
		//price
		price = new JTextField(14);
		price.setBounds(419, 101, 150, 30);
		panel.add(price);
		JLabel penaltyLabel = new JLabel("Penalty ");
		penaltyLabel.setBounds(318, 143, 100, 30);
		panel.add(penaltyLabel);
		penaltyLabel.setFont(font);
		penaltyLabel.setForeground(Color.BLACK);
		
		//penalty
		penalty = new JTextField(14);
		penalty.setBounds(419, 144, 150, 30);
		panel.add(penalty);
		JLabel trunkLabel = new JLabel("Trunk ");
		trunkLabel.setBounds(318, 185, 100, 30);
		panel.add(trunkLabel);
		trunkLabel.setFont(font);
		trunkLabel.setForeground(Color.BLACK);
		
		//trunk
		trunk = new JTextField(14);
		trunk.setBounds(419, 186, 150, 30);
		panel.add(trunk);
		JLabel engineLabel = new JLabel("Engine ");
		engineLabel.setBounds(318, 227, 100, 30);
		panel.add(engineLabel);
		engineLabel.setFont(font);
		engineLabel.setForeground(Color.BLACK);
		
		//engine
		engine = new JTextField(14);
		engine.setBounds(419, 228, 150, 30);
		panel.add(engine);
		JLabel ratingLabel = new JLabel("Rating ");
		ratingLabel.setBounds(318, 269, 100, 30);
		panel.add(ratingLabel);
		ratingLabel.setFont(font);
		ratingLabel.setForeground(Color.BLACK);
		
		//rating
		rating = new JComboBox<Object>(ratings);
		rating.setBounds(419, 271, 150, 30);
		panel.add(rating);
		JLabel availableLabel = new JLabel("Available ");
		availableLabel.setBounds(318, 312, 100, 30);
		panel.add(availableLabel);
		availableLabel.setFont(font);
		availableLabel.setForeground(Color.BLACK);
		
		//availability
		availability = new JComboBox<Object>(available);
		availability.setBounds(419, 314, 150, 30);
		panel.add(availability);
		
		carImgAddNewCar = new JLabel();
		carImgAddNewCar.setBounds(101, 390, 288, 160);
		panel.add(carImgAddNewCar);
		
		panel_1 = new JPanel();
		panel_1.setBounds(628, 50, 376, 153);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		lblNewLabel_1 = new JLabel("Delete Car");
		lblNewLabel_1.setFont(new Font("Serif", Font.BOLD, 16));
		lblNewLabel_1.setBounds(145, 11, 117, 16);
		panel_1.add(lblNewLabel_1);
		
		carPlate = new JTextField();
		carPlate.setBounds(29, 63, 341, 26);
		panel_1.add(carPlate);
		carPlate.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Insert Car Plate Number:");
		lblNewLabel_2.setBounds(29, 39, 177, 16);
		panel_1.add(lblNewLabel_2);
		
		JButton deleteCar = new JButton("Delete Car Record");
		deleteCar.setBounds(126, 101, 156, 29);
		panel_1.add(deleteCar);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(628, 217, 376, 390);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Update Car Status");
		lblNewLabel_3.setFont(new Font("Serif", Font.BOLD, 16));
		lblNewLabel_3.setBounds(129, 6, 143, 16);
		panel_2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Insert Car Plate Number:");
		lblNewLabel_4.setBounds(33, 34, 183, 16);
		panel_2.add(lblNewLabel_4);
		
		carPlateUpdate = new JTextField();
		carPlateUpdate.setBounds(33, 54, 176, 26);
		panel_2.add(carPlateUpdate);
		carPlateUpdate.setColumns(10);
		
		JButton searchPlateUpdate = new JButton("Search");
		searchPlateUpdate.setBounds(221, 54, 117, 29);
		panel_2.add(searchPlateUpdate);
		
		JLabel lblNewLabel_5 = new JLabel("Make: ");
		lblNewLabel_5.setBounds(33, 117, 56, 16);
		panel_2.add(lblNewLabel_5);
		
		updateMake = new JTextField();
		updateMake.setBackground(Color.LIGHT_GRAY);
		updateMake.setEditable(false);
		updateMake.setBounds(82, 113, 256, 26);
		panel_2.add(updateMake);
		updateMake.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Model:");
		lblNewLabel_6.setBounds(33, 153, 61, 16);
		panel_2.add(lblNewLabel_6);
		
		updateModel = new JTextField();
		updateModel.setBackground(Color.LIGHT_GRAY);
		updateModel.setEditable(false);
		updateModel.setBounds(82, 149, 256, 26);
		panel_2.add(updateModel);
		updateModel.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Update The Car");
		lblNewLabel_7.setFont(new Font("Serif", Font.BOLD, 16));
		lblNewLabel_7.setBounds(117, 224, 133, 16);
		panel_2.add(lblNewLabel_7);
		
		updateAvailableCb = new JComboBox(available);
		updateAvailableCb.setBounds(195, 270, 143, 27);
		panel_2.add(updateAvailableCb);
		
		JLabel lblNewLabel_8 = new JLabel("Is The Car Available?");
		lblNewLabel_8.setBounds(33, 274, 151, 16);
		panel_2.add(lblNewLabel_8);
		
		JButton updateSave = new JButton("Save");
		updateSave.setBounds(33, 342, 305, 29);
		panel_2.add(updateSave);
		
		// handler class for buttons
		HandlerClass handler = new HandlerClass();
				
		submit.addActionListener(handler);
		back.addActionListener(handler);
		deleteCar.addActionListener(handler);
		browse.addActionListener(handler);
		searchPlateUpdate.addActionListener(handler);
		updateSave.addActionListener(handler);
		
		transmission.addItemListener(new ItemClass());
		fuel.addItemListener(new ItemClass());
		type.addItemListener(new ItemClass());
		seat.addItemListener(new ItemClass());
		rating.addItemListener(new ItemClass());
		availability.addItemListener(new ItemClass());
		updateAvailableCb.addItemListener(new ItemClass());
				
	}		
				
			
	//method to check if value is boolean
	public boolean isDouble(String str) {
				
		try {
					
			Double.parseDouble(str);
			return true;
					
		} catch (NumberFormatException e) {
					
			return false;
		}
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
			
			//class to handle events with buttons
			private class HandlerClass implements ActionListener {
				//listens for event to occur
				public void actionPerformed(ActionEvent event) {
					//browse for image
					if (event.getActionCommand() == "Browse") {
						
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("Select Image");
						fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
						FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "gif", "png");
						fileChooser.addChoosableFileFilter(filter);
						
						int result = fileChooser.showSaveDialog(null);
						
						if (result == JFileChooser.APPROVE_OPTION) {
							
							File selectedFile = fileChooser.getSelectedFile();
							try {
								fis=new FileInputStream(selectedFile);
								fileLength = (int)selectedFile.length();

							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String path = selectedFile.getAbsolutePath();
							imageLocation.setText(path);
							browseImg = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(280, 160, Image.SCALE_SMOOTH));
							carImgAddNewCar.setIcon(browseImg);
							
						}else if (result == JFileChooser.CANCEL_OPTION) {
							
							JOptionPane.showMessageDialog(null, "No Image Selected !", "ERROR", JOptionPane.ERROR_MESSAGE);
							
						}
					}
					
					//performs validations if Register button is clicked
					if (event.getActionCommand() == "Register") {
					
							String message = "Invalid";
							
							//validate car plate no
							if ( (plateNo.getText().length() == 0) || (plateNo.getText().length() > 10) ) {
								
								message += " (Plate No)";
			//					JOptionPane.showMessageDialog(null, "Invalid Car Plate No !");
								plateNo.setText(null);
								
							}else {
								
								carPlateNo = plateNo.getText();
							}
							
							//validate car make
							if ( (make.getText().length() == 0) || (make.getText().length() > 15) ) {
								
								message += " (Make)";
			//					JOptionPane.showMessageDialog(null, "Invalid Car Make !");
								make.setText(null);
								
							}else {
								
								carMake = make.getText();
							}
							
							//validate car model
							if ( (model.getText().length() == 0) || (model.getText().length() > 15) ) {
								
								message += " (Model)";
			//					JOptionPane.showMessageDialog(null, "Invalid Car Model !");
								model.setText(null);
								
							}else {
								
								carModel = model.getText();
							}
							
							//validate year
							if ( (year.getText().length() == 0) || (year.getText().length() > 4) || (!isInteger(year.getText())) ) {
								
								message += " (Year)";
			//					JOptionPane.showMessageDialog(null, "Invalid Car Year !");
								year.setText(null);
								
							}else {
								
								carYear = Integer.valueOf(year.getText());
							}
							
							//validate price
							if ( (price.getText().length() == 0) || (!isDouble(price.getText())) ) {
								
								message += " (Price)";
			//					JOptionPane.showMessageDialog(null, "Price per Day is empty !");
								price.setText(null);
								
							}else {
								
								carPricePerDay = Double.valueOf(price.getText());
							}
							
							//validate penalty fee
							if ( (penalty.getText().length() == 0) || (!isDouble(penalty.getText())) ) {
								
								message += " (Penalty)";
			//					JOptionPane.showMessageDialog(null, "Penalty Fee is empty !");
								penalty.setText(null);
								
							}else {
								
								carPenalty = Double.valueOf(penalty.getText());
							}
							
							//validate trunk size
							if ( (trunk.getText().length() == 0) || (!isDouble(trunk.getText()))) {
								
								message += " (Trunk Size)";
			//					JOptionPane.showMessageDialog(null, "Trunk Size is empty !");
								trunk.setText(null);
								
							}else {
								
								carTrunkSize = Double.valueOf(trunk.getText());
							}
							
							//validate engine size
							if ( (engine.getText().length() == 0) || (!isInteger(engine.getText())) ) {
								
								message += " (Engine Size)";
			//					JOptionPane.showMessageDialog(null, "Engine Size is empty !");
								engine.setText(null);
								
							}else {
								
								carEngineSize = Integer.valueOf(engine.getText());
							}
							
							//capture id of manager currently logged in
							empId = LoginSession.userId;
							
			//				//validate image location
			//				if ( (image.getText().length() == 0) ) {
			//					
			//					message += " (Image URL)";
			////					JOptionPane.showMessageDialog(null, "Image Field is empty !");
			//					image.setText(null);
			//					
			//				}else {
			//					
			//					carImage = image.getText();
			//				}
							
							//validate registered by
			//				if ( (registeredBy.getText().length() == 0) ) {
			//					
			//					message += " (ID)";
			////					JOptionPane.showMessageDialog(null, "Manager ID is empty !");
			//					registeredBy.setText(null);
			//					
			//				}else {
			//					
			//					empId = Integer.valueOf(registeredBy.getText());
			//				}
			//				
							if (message.equalsIgnoreCase("Invalid")) {
									
								int confirm = JOptionPane.showConfirmDialog(null, String.format("Confirm registration of %s",carPlateNo));
								
								if (confirm == 0) {
									
									DbConnect connect = new DbConnect();
								
									ArrayList <Car> allcars = connect.getCar(carPlateNo, -1);
								
									if (allcars.isEmpty()) {	
									
										
										//checks if query is successful
										if  (connect.insertCar(carPlateNo, carMake, carModel, carType, carYear, carGear, carFuel, fis,fileLength, carSeats, carPricePerDay, carPenalty, carTrunkSize, carEngineSize, carRating, empId, carAvailability)) {
										
											JOptionPane.showMessageDialog(null, "Records saved in Database");
										
											plateNo.setText(null);
											make.setText(null);
											model.setText(null);
											type.setSelectedIndex(0);
											year.setText(null);
											price.setText(null);
											penalty.setText(null);
											trunk.setText(null);
											engine.setText(null);
											registeredBy.setText(null);
											transmission.setSelectedIndex(0);
											fuel.setSelectedIndex(0);
											seat.setSelectedIndex(0);
											rating.setSelectedIndex(0);
											
											dispose();
											ManagerGui manGui = new ManagerGui();
											manGui.setVisible(true);
											
										}else {
											//if query fails
											JOptionPane.showMessageDialog(null, "Error in saving file to Database", "ERROR", JOptionPane.ERROR_MESSAGE);
										
										}
										
									}else {
									
										JOptionPane.showMessageDialog(null, String.format("%s already exists in database !", carPlateNo),"ERROR", JOptionPane.ERROR_MESSAGE);
									}
								}	
									
							}else if (!message.equalsIgnoreCase("Invalid")) {
									
									JOptionPane.showMessageDialog(null, message);
									
							}
						}
					
					
					//check if search button was pressed
					if (event.getActionCommand() == "Search") {
						
						if (carPlateUpdate.getText().length() == 0) {
							
							JOptionPane.showMessageDialog(null, "Car Plate Number is empty !", "ERROR", JOptionPane.ERROR_MESSAGE );
							
						}else {
							
							//capture value of carplateNo
							updatePlateNo = carPlateUpdate.getText();
							
							DbConnect connect = new DbConnect();
							
							//capture car with the plate number
							ArrayList<Car> checkCar = connect.getCar(updatePlateNo, -1);
							
							//check if there is no car with the plate number in database
							if (checkCar.isEmpty()) {
								
								JOptionPane.showMessageDialog(null, String.format("%s not found in database !", updatePlateNo), "ERROR", JOptionPane.ERROR_MESSAGE);
								
							}else if (!checkCar.isEmpty()) {
								//output make and model of car in textfields
								updateMake.setText(checkCar.get(0).getCarMake());
								updateModel.setText(checkCar.get(0).getCarModel());
								
							}
						}
						
					}
					
					//check if save button was pressed
					if (event.getActionCommand() == "Save") {
						
						if(carPlateUpdate.getText().length() == 0) {
							JOptionPane.showMessageDialog(null, "Car Plate Number is empty !", "ERROR", JOptionPane.ERROR_MESSAGE );
						}
						else {
							int confirm = JOptionPane.showConfirmDialog(null, String.format("Are you sure you want to change the availability status of %s", updatePlateNo));

							if (confirm == 0 ) {
									DbConnect connect = new DbConnect();
									
									if (connect.updateCarAvailability(updatePlateNo, updateCarAvailable)) {
										
										JOptionPane.showMessageDialog(null, String.format("Availability status of %s has successfully been updated", updatePlateNo));
										updateMake.setText(null);
										updateModel.setText(null);
										carPlateUpdate.setText(null);
									}else {
										
										JOptionPane.showMessageDialog(null, "Error in updating status !", "ERROR", JOptionPane.ERROR_MESSAGE);
									}
								}
						}
					}
					
					
					//checks if Back button was pressed
					if (event.getActionCommand() == "Back") {
						//dispose current gui
						dispose();
						ManagerGui gui = new ManagerGui();
						gui.setVisible(true);
					
					}
					
					//checks if delete button was pressed
					if (event.getActionCommand() == "Delete Car Record") {
						
						if (carPlate.getText().length() == 0) {
							
							JOptionPane.showMessageDialog(null, "Car Plate No is empty !", "ERROR", JOptionPane.ERROR_MESSAGE );
							
						}else {
							//capture value from text field
							String carPlateNum = carPlate.getText();
							int confirm = JOptionPane.showConfirmDialog(null, String.format("Are you sure you want to delete %s", carPlateNum));
						
							if (confirm == 0) {
								
								//instantiates new database connection
								DbConnect connect = new DbConnect();
								
								//get car with the plate number entered
								ArrayList<Car> cars = connect.getCar(carPlateNum, -1);
								
								//if car exists in the database, delete it
								if (!cars.isEmpty()) {
									
									if (connect.deleteCar(carPlateNum)) {
										
										JOptionPane.showMessageDialog(null,String.format("%s has been deleted", carPlateNum));
										
										dispose();
										ManagerGui gui = new ManagerGui();
										gui.setVisible(true);
										
									}else {
										
										JOptionPane.showMessageDialog(null, String.format("Error in deleting %s", carPlateNum), "ERROR", JOptionPane.ERROR_MESSAGE );
									}
									
								}else {
									
									JOptionPane.showMessageDialog(null, String.format("%s not found in database", carPlateNum), "ERROR", JOptionPane.ERROR_MESSAGE );
								}
							
							}else if (confirm == 2) {
								
								carPlate.setText(null);
							}
						}
					}
				}
				

			}
			
			//class to handle events with combo boxes
			private class ItemClass implements ItemListener{
				//listens for event to occur with combo boxes
				public void itemStateChanged(ItemEvent event) {

					
					carGear = gears[transmission.getSelectedIndex()];
					carFuel = fuels[fuel.getSelectedIndex()];
					carSeats = Integer.valueOf(seats[seat.getSelectedIndex()]);	
					carRating = Integer.valueOf(ratings[rating.getSelectedIndex()]);
					carType = types[type.getSelectedIndex()];
					
					if (available[availability.getSelectedIndex()].equalsIgnoreCase("true")) {
							
						carAvailability = 1;
							
					}else if (available[availability.getSelectedIndex()].equalsIgnoreCase("false"))  {
							
						carAvailability = 0;
					}
					
					if (available[updateAvailableCb.getSelectedIndex()].equalsIgnoreCase("true")) {
						
						updateCarAvailable = 1;
						
					}else if (available[updateAvailableCb.getSelectedIndex()].equalsIgnoreCase("false")) {
						
						updateCarAvailable = 0;
					}
					

					
				}
				
			}
}