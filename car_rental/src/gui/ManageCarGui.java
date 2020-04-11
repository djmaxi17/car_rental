package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import car.Car;
import database.DbConnect;
import main.LoginSession;

public class ManageCarGui extends JFrame {

	private static final long serialVersionUID = -3726549691848934057L;
	
	DbConnect connect = new DbConnect();
	
	//attributes for buttons , text fields, combo boxes and images icons
	private JTextField plateNo;
	private JTextField make;
	private JTextField model;
	private JComboBox<? > type;
	private JTextField year;
	private JComboBox<? > transmission;
	private JComboBox<? > fuel;
	private JComboBox<? > seat;
	private JTextField price;
	private JTextField penalty;
	private JTextField trunk;
	private JTextField engine;
	private JComboBox<? > rating;
	private JTextField registeredBy;
	private JComboBox<? > availability;
	private JComboBox<? > updateAvailableCb;
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

	//colors 
	Color colorSp = new Color(87, 90, 92);
	private Color textCB = new Color(248, 250, 252);
	private Color buttonCol = new Color(79, 99, 116);
	private Color colortxt = new Color(251, 241, 199);
	private Color sideCol = new Color(107, 106, 103);

	//array for gear types
	private static String[] gears = {
		"Manual", "Automatic", "Both"
	};
	//array for fuel types
	private static String[] fuels = {
		"Petrol", "Diesel", "Hybrid"
	};
	//array for number of seats
	private static String[] seats = {
		"2", "3", "4", "5", "6", "7", "8"
	};
	//array for ratings
	private static String[] ratings = {
		"1", "2", "3", "4", "5"
	};
	//array for availability
	private static String[] available = {
		"True", "False"
	};
	//array for car types
	private static String[] types = {
		"Compact", "Sedan", "Convertible", "Sport", "Luxury", "SUV"
	};

	private JPanel delPanel;
	private JLabel lblDelCar;
	private JTextField carPlate;
	private JTextField carPlateUpdate;
	private JTextField updateMake;
	private JTextField updateModel;

	//defining constructor
	public ManageCarGui() {
		//title
		super("Manage Car");

		//display login on closed
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ManagerGui manager = null;
				manager = new ManagerGui(null);
				manager.setVisible(true);
			}
		});

		this.getContentPane().setLayout(null);
		this.setSize(1010, 678);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setBackground(Color.DARK_GRAY);
		this.setIconImage(new ImageIcon(LoginGui.class.getResource("icon.png")).getImage());

		//font for text
		Font font = new Font("Sans Serif", Font.BOLD, 14);

		//registered by 
		registeredBy = new JTextField(14);
		registeredBy.setBounds(500, 380, 150, 30);
		JLabel registerLabel = new JLabel("Manger ID  ");
		registerLabel.setFont(font);
		registerLabel.setForeground(Color.WHITE);
		registerLabel.setBounds(430, 380, 100, 30);

		//back
		back = new JButton("Back");
		back.setFont(new Font("SansSerif", Font.BOLD, 12));
		back.setBackground(buttonCol);
		back.setForeground(textCB);
		back.setBounds(10, 600, 100, 30);

		//logo
		logo = new ImageIcon(this.getClass().getResource("companyName.png"));
		JLabel companyLogo = new JLabel(logo);
		companyLogo.setBounds(387, 18, 225, 30);
		getContentPane().add(back);
		getContentPane().add(companyLogo);

		//add new car panel
		JPanel newPanel = new JPanel();
		newPanel.setBorder(null);
		newPanel.setBounds(10, 58, 594, 530);
		newPanel.setBackground(colorSp);
		getContentPane().add(newPanel);
		newPanel.setLayout(null);

		JLabel plateLabel = new JLabel("Plate No. ");
		plateLabel.setBounds(21, 60, 69, 17);
		newPanel.add(plateLabel);
		plateLabel.setFont(font);
		plateLabel.setForeground(textCB);

		//instantiate attributes
		//plateNo
		plateNo = new JTextField(14);
		plateNo.setBounds(116, 54, 150, 30);
		newPanel.add(plateNo);

		JLabel addCarLbl = new JLabel("Add A New Car");
		addCarLbl.setHorizontalAlignment(SwingConstants.CENTER);
		addCarLbl.setForeground(colortxt);
		addCarLbl.setFont(new Font("Serif", Font.BOLD, 20));
		addCarLbl.setBounds(191, 12, 212, 30);
		newPanel.add(addCarLbl);
		JLabel makeLabel = new JLabel("Make ");
		makeLabel.setBounds(21, 100, 51, 30);
		newPanel.add(makeLabel);
		makeLabel.setFont(font);
		makeLabel.setForeground(textCB);

		//make
		make = new JTextField(14);
		make.setBounds(116, 101, 150, 30);
		newPanel.add(make);
		JLabel modelLabel = new JLabel("Model ");
		modelLabel.setBounds(21, 143, 51, 30);
		newPanel.add(modelLabel);
		modelLabel.setFont(font);
		modelLabel.setForeground(textCB);

		//model
		model = new JTextField(14);
		model.setBounds(116, 143, 150, 30);
		newPanel.add(model);
		JLabel typeLabel = new JLabel("Type ");
		typeLabel.setBounds(21, 185, 51, 30);
		newPanel.add(typeLabel);
		typeLabel.setFont(font);
		typeLabel.setForeground(textCB);

		//type
		type = new JComboBox<Object> (types);
		type.setBounds(116, 185, 150, 30);
		newPanel.add(type);
		JLabel transmissionLabel = new JLabel("Gear ");
		transmissionLabel.setBounds(21, 227, 51, 30);
		newPanel.add(transmissionLabel);
		transmissionLabel.setFont(font);
		transmissionLabel.setForeground(textCB);

		//transmission
		transmission = new JComboBox<Object> (gears);
		transmission.setBounds(116, 227, 150, 30);
		newPanel.add(transmission);
		JLabel fuelLabel = new JLabel("Fuel ");
		fuelLabel.setBounds(21, 268, 100, 30);
		newPanel.add(fuelLabel);
		fuelLabel.setFont(font);
		fuelLabel.setForeground(textCB);

		//fuel
		fuel = new JComboBox<Object> (fuels);
		fuel.setBounds(116, 270, 150, 30);
		newPanel.add(fuel);
		JLabel yearLabel = new JLabel("Year ");
		yearLabel.setBounds(21, 311, 100, 30);
		newPanel.add(yearLabel);
		yearLabel.setFont(font);
		yearLabel.setForeground(textCB);

		//year
		year = new JTextField(14);
		year.setBounds(116, 310, 150, 30);
		newPanel.add(year);

		//image
		JLabel imageLabel = new JLabel("Image ");
		imageLabel.setBounds(21, 351, 100, 30);
		newPanel.add(imageLabel);
		imageLabel.setFont(font);
		imageLabel.setForeground(textCB);
		browse = new JButton("Browse");
		browse.setBounds(116, 353, 100, 30);
		newPanel.add(browse);

		//submit
		submit = new JButton("Register");
		submit.setForeground(new Color(51, 51, 51));
		submit.setBackground(buttonCol);
		submit.setFont(new Font("SansSerif", Font.BOLD, 12));
		submit.setBounds(469, 494, 100, 30);
		newPanel.add(submit);
		JRootPane rootPane = SwingUtilities.getRootPane(submit);
		rootPane.setDefaultButton(submit);

		//image location text
		imageLocation = new JLabel("");
		imageLocation.setBounds(219, 353, 350, 30);
		newPanel.add(imageLocation);
		imageLocation.setFont(new Font("Dialog", Font.PLAIN, 14));
		imageLocation.setForeground(new Color(255, 255, 255));

		//seats
		seat = new JComboBox<Object> (seats);
		seat.setBounds(419, 55, 150, 30);
		newPanel.add(seat);
		JLabel seatLabel = new JLabel("Seats ");
		seatLabel.setBounds(318, 53, 100, 30);
		newPanel.add(seatLabel);
		seatLabel.setFont(font);
		seatLabel.setForeground(textCB);
		JLabel priceLabel = new JLabel("Price ");
		priceLabel.setBounds(318, 100, 100, 30);
		newPanel.add(priceLabel);
		priceLabel.setFont(font);
		priceLabel.setForeground(textCB);

		//price
		price = new JTextField(14);
		price.setBounds(419, 101, 150, 30);
		newPanel.add(price);
		JLabel penaltyLabel = new JLabel("Penalty ");
		penaltyLabel.setBounds(318, 143, 100, 30);
		newPanel.add(penaltyLabel);
		penaltyLabel.setFont(font);
		penaltyLabel.setForeground(textCB);

		//penalty
		penalty = new JTextField(14);
		penalty.setBounds(419, 144, 150, 30);
		newPanel.add(penalty);
		JLabel trunkLabel = new JLabel("Trunk ");
		trunkLabel.setBounds(318, 185, 100, 30);
		newPanel.add(trunkLabel);
		trunkLabel.setFont(font);
		trunkLabel.setForeground(textCB);

		//trunk
		trunk = new JTextField(14);
		trunk.setBounds(419, 186, 150, 30);
		newPanel.add(trunk);
		JLabel engineLabel = new JLabel("Engine ");
		engineLabel.setBounds(318, 227, 100, 30);
		newPanel.add(engineLabel);
		engineLabel.setFont(font);
		engineLabel.setForeground(textCB);

		//engine
		engine = new JTextField(14);
		engine.setBounds(419, 228, 150, 30);
		newPanel.add(engine);
		JLabel ratingLabel = new JLabel("Rating ");
		ratingLabel.setBounds(318, 269, 100, 30);
		newPanel.add(ratingLabel);
		ratingLabel.setFont(font);
		ratingLabel.setForeground(textCB);

		//rating
		rating = new JComboBox<Object> (ratings);
		rating.setBounds(419, 271, 150, 30);
		newPanel.add(rating);
		JLabel availableLabel = new JLabel("Available ");
		availableLabel.setBounds(318, 312, 100, 30);
		newPanel.add(availableLabel);
		availableLabel.setFont(font);
		availableLabel.setForeground(textCB);

		//availability
		availability = new JComboBox<Object> (available);
		availability.setBounds(419, 314, 150, 30);
		newPanel.add(availability);

		//car image preview
		carImgAddNewCar = new JLabel();
		carImgAddNewCar.setBounds(132, 386, 271, 141);
		newPanel.add(carImgAddNewCar);

		//delete car panel
		delPanel = new JPanel();
		delPanel.setBounds(607, 58, 376, 153);
		delPanel.setBackground(sideCol);
		getContentPane().add(delPanel);
		delPanel.setLayout(null);

		carPlate = new JTextField();
		carPlate.setBounds(29, 72, 305, 26);
		delPanel.add(carPlate);
		carPlate.setColumns(10);

		JLabel lblCarPlate = new JLabel("Insert Car Plate Number:");
		lblCarPlate.setForeground(new Color(255, 255, 255));
		lblCarPlate.setBounds(29, 47, 177, 16);
		delPanel.add(lblCarPlate);

		JButton deleteCar = new JButton("Delete Car Record");
		deleteCar.setBackground(buttonCol);
		deleteCar.setForeground(textCB);
		deleteCar.setFont(new Font("SansSerif", Font.BOLD, 12));
		deleteCar.setBounds(109, 110, 156, 29);
		delPanel.add(deleteCar);

		lblDelCar = new JLabel("Delete Car");
		lblDelCar.setHorizontalAlignment(SwingConstants.CENTER);
		lblDelCar.setBounds(126, 19, 139, 20);
		delPanel.add(lblDelCar);
		lblDelCar.setForeground(colortxt);
		lblDelCar.setFont(new Font("Serif", Font.BOLD, 20));

		//update car panel
		JPanel updatePanel = new JPanel();
		updatePanel.setBackground(new Color(128, 128, 128));
		updatePanel.setBounds(607, 214, 376, 374);
		getContentPane().add(updatePanel);
		updatePanel.setLayout(null);

		JLabel lblUpdateCar = new JLabel("Update Car Status");
		lblUpdateCar.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdateCar.setForeground(colortxt);
		lblUpdateCar.setBounds(98, 17, 192, 26);
		lblUpdateCar.setFont(new Font("Serif", Font.BOLD, 20));
		updatePanel.add(lblUpdateCar);

		JLabel lblInsert = new JLabel("Insert Car Plate Number:");
		lblInsert.setForeground(new Color(255, 255, 255));
		lblInsert.setBounds(33, 71, 176, 16);
		updatePanel.add(lblInsert);

		carPlateUpdate = new JTextField();
		carPlateUpdate.setBounds(29, 93, 180, 26);
		updatePanel.add(carPlateUpdate);
		carPlateUpdate.setColumns(10);

		JButton searchPlateUpdate = new JButton("Search");
		searchPlateUpdate.setForeground(textCB);
		searchPlateUpdate.setBackground(buttonCol);
		searchPlateUpdate.setFont(new Font("SansSerif", Font.BOLD, 12));
		searchPlateUpdate.setBounds(221, 92, 117, 29);
		updatePanel.add(searchPlateUpdate);

		JLabel lblMake = new JLabel("Make: ");
		lblMake.setForeground(textCB);
		lblMake.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblMake.setBounds(33, 149, 56, 16);
		updatePanel.add(lblMake);

		updateMake = new JTextField();
		updateMake.setForeground(Color.WHITE);
		updateMake.setBounds(82, 145, 256, 26);
		updateMake.setBackground(SystemColor.windowBorder);
		updateMake.setEditable(false);
		updatePanel.add(updateMake);
		updateMake.setColumns(10);

		JLabel lblModel = new JLabel("Model:");
		lblModel.setForeground(textCB);
		lblModel.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblModel.setBounds(33, 181, 61, 16);
		updatePanel.add(lblModel);

		updateModel = new JTextField();
		updateModel.setForeground(Color.WHITE);
		updateModel.setBounds(82, 177, 256, 26);
		updateModel.setBackground(SystemColor.windowBorder);
		updateModel.setEditable(false);
		updatePanel.add(updateModel);
		updateModel.setColumns(10);

		JLabel lblUpCar = new JLabel("Update The Car");
		lblUpCar.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpCar.setForeground(colortxt);
		lblUpCar.setBounds(108, 239, 176, 26);
		lblUpCar.setFont(new Font("Serif", Font.BOLD, 20));
		updatePanel.add(lblUpCar);

		updateAvailableCb = new JComboBox<Object> (available);
		updateAvailableCb.setBounds(195, 289, 143, 27);
		updatePanel.add(updateAvailableCb);

		JLabel lblAvail = new JLabel("Is The Car Available?");
		lblAvail.setForeground(new Color(255, 255, 255));
		lblAvail.setBounds(33, 294, 151, 16);
		updatePanel.add(lblAvail);

		JButton updateSave = new JButton("Save");
		updateSave.setFont(new Font("SansSerif", Font.BOLD, 12));
		updateSave.setForeground(textCB);
		updateSave.setBackground(buttonCol);
		updateSave.setBounds(33, 336, 305, 29);
		updatePanel.add(updateSave);

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
				fileChooser.setFileFilter(filter);

				int result = fileChooser.showOpenDialog(getManager());

				if (result == JFileChooser.APPROVE_OPTION) {

					File selectedFile = fileChooser.getSelectedFile();
					try {
						fis = new FileInputStream(selectedFile);
						fileLength = (int) selectedFile.length();

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					String path = selectedFile.getAbsolutePath();
					imageLocation.setText(path);
					browseImg = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(280, 160, Image.SCALE_SMOOTH));
					carImgAddNewCar.setIcon(browseImg);

				} else if (result == JFileChooser.CANCEL_OPTION) {

					JOptionPane.showMessageDialog(null, "No Image Selected !", "ERROR", JOptionPane.ERROR_MESSAGE);

				}
			}

			//performs validations if Register button is clicked
			if (event.getActionCommand() == "Register") {

				String message = "Invalid";

				//validate car plate no
				if ((plateNo.getText().length() == 0) || (plateNo.getText().length() > 10)) {

					message += " (Plate No)";
					plateNo.setText(null);

				} else {

					carPlateNo = plateNo.getText();
				}

				//validate car make
				if ((make.getText().length() == 0) || (make.getText().length() > 15)) {

					message += " (Make)";

					make.setText(null);

				} else {

					carMake = make.getText();
				}

				//validate car model
				if ((model.getText().length() == 0) || (model.getText().length() > 15)) {

					message += " (Model)";
					model.setText(null);

				} else {

					carModel = model.getText();
				}

				//validate year
				if ((year.getText().length() == 0) || (year.getText().length() > 4) || (!isInteger(year.getText()))) {

					message += " (Year)";
					year.setText(null);

				} else {

					carYear = Integer.valueOf(year.getText());
				}

				//validate price
				if ((price.getText().length() == 0) || (!isDouble(price.getText()))) {

					message += " (Price)";
					price.setText(null);

				} else {

					carPricePerDay = Double.valueOf(price.getText());
				}

				//validate penalty fee
				if ((penalty.getText().length() == 0) || (!isDouble(penalty.getText()))) {

					message += " (Penalty)";
					penalty.setText(null);

				} else {

					carPenalty = Double.valueOf(penalty.getText());
				}

				//validate trunk size
				if ((trunk.getText().length() == 0) || (!isDouble(trunk.getText()))) {

					message += " (Trunk Size)";
					trunk.setText(null);

				} else {

					carTrunkSize = Double.valueOf(trunk.getText());
				}

				//validate engine size
				if ((engine.getText().length() == 0) || (!isInteger(engine.getText()))) {

					message += " (Engine Size)";
					engine.setText(null);

				} else {

					carEngineSize = Integer.valueOf(engine.getText());
				}

				//capture id of manager currently logged in
				empId = LoginSession.userId;

				if (message.equalsIgnoreCase("Invalid")) {

					int confirm = JOptionPane.showConfirmDialog(null, String.format("Confirm registration of %s", carPlateNo));

					if (confirm == 0) {

						ArrayList<Car> allcars = connect.getCar(carPlateNo, -1);

						if (allcars.isEmpty()) {

							//checks if query is successful
							if (connect.insertCar(carPlateNo, carMake, carModel, carType, carYear, carGear, carFuel, fis, fileLength, carSeats, carPricePerDay, carPenalty, carTrunkSize, carEngineSize, carRating, empId, carAvailability)) {

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
								ManagerGui manGui = new ManagerGui(null);
								manGui.setVisible(true);

							} else {
								//if query fails
								JOptionPane.showMessageDialog(null, "Error in saving file to Database", "ERROR", JOptionPane.ERROR_MESSAGE);

							}

						} else {

							JOptionPane.showMessageDialog(null, String.format("%s already exists in database !", carPlateNo), "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}

				} else if (!message.equalsIgnoreCase("Invalid")) {

					JOptionPane.showMessageDialog(null, message);

				}
			}

			//check if search button was pressed
			if (event.getActionCommand() == "Search") {

				if (carPlateUpdate.getText().length() == 0) {

					JOptionPane.showMessageDialog(null, "Car Plate Number is empty !", "ERROR", JOptionPane.ERROR_MESSAGE);

				} else {

					//capture value of carplateNo
					updatePlateNo = carPlateUpdate.getText();

					//capture car with the plate number
					ArrayList<Car> checkCar = connect.getCar(updatePlateNo, -1);

					//check if there is no car with the plate number in database
					if (checkCar.isEmpty()) {

						JOptionPane.showMessageDialog(null, String.format("%s not found in database !", updatePlateNo), "ERROR", JOptionPane.ERROR_MESSAGE);

					} else if (!checkCar.isEmpty()) {
						//output make and model of car in textfields
						updateMake.setText(checkCar.get(0).getCarMake());
						updateModel.setText(checkCar.get(0).getCarModel());

					}
				}

			}

			//check if save button was pressed
			if (event.getActionCommand() == "Save") {

				if (carPlateUpdate.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Car Plate Number is empty !", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					int confirm = JOptionPane.showConfirmDialog(null, String.format("Are you sure you want to change the availability status of %s", updatePlateNo));

					if (confirm == 0) {

						if (connect.updateCarAvailability(updatePlateNo, updateCarAvailable)) {

							JOptionPane.showMessageDialog(null, String.format("Availability status of %s has successfully been updated", updatePlateNo));
							updateMake.setText(null);
							updateModel.setText(null);
							carPlateUpdate.setText(null);
						} else {

							JOptionPane.showMessageDialog(null, "Error in updating status !", "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}

			//checks if Back button was pressed
			if (event.getActionCommand() == "Back") {
				//dispose current gui
				dispose();
				ManagerGui gui = new ManagerGui(null);
				gui.setVisible(true);

			}

			//checks if delete button was pressed
			if (event.getActionCommand() == "Delete Car Record") {

				if (carPlate.getText().length() == 0) {

					JOptionPane.showMessageDialog(null, "Car Plate No is empty !", "ERROR", JOptionPane.ERROR_MESSAGE);

				} else {
					//capture value from text field
					String carPlateNum = carPlate.getText();
					int confirm = JOptionPane.showConfirmDialog(null, String.format("Are you sure you want to delete %s", carPlateNum));

					if (confirm == 0) {

						//get car with the plate number entered
						ArrayList<Car> cars = connect.getCar(carPlateNum, -1);

						//if car exists in the database, delete it
						if (!cars.isEmpty()) {

							if (connect.deleteCar(carPlateNum)) {

								JOptionPane.showMessageDialog(null, String.format("%s has been deleted", carPlateNum));

								dispose();
								ManagerGui gui = new ManagerGui(null);
								gui.setVisible(true);

							} else {

								JOptionPane.showMessageDialog(null, String.format("Error in deleting %s", carPlateNum), "ERROR", JOptionPane.ERROR_MESSAGE);
							}

						} else {

							JOptionPane.showMessageDialog(null, String.format("%s not found in database", carPlateNum), "ERROR", JOptionPane.ERROR_MESSAGE);
						}

					} else if (confirm == 2) {

						carPlate.setText(null);
					}
				}
			}
		}

	}

	//class to handle events with combo boxes
	private class ItemClass implements ItemListener {
		//listens for event to occur with combo boxes
		public void itemStateChanged(ItemEvent event) {

			carGear = gears[transmission.getSelectedIndex()];
			carFuel = fuels[fuel.getSelectedIndex()];
			carSeats = Integer.valueOf(seats[seat.getSelectedIndex()]);
			carRating = Integer.valueOf(ratings[rating.getSelectedIndex()]);
			carType = types[type.getSelectedIndex()];

			if (available[availability.getSelectedIndex()].equalsIgnoreCase("true")) {

				carAvailability = 1;

			} else if (available[availability.getSelectedIndex()].equalsIgnoreCase("false")) {

				carAvailability = 0;
			}

			if (available[updateAvailableCb.getSelectedIndex()].equalsIgnoreCase("true")) {

				updateCarAvailable = 1;

			} else if (available[updateAvailableCb.getSelectedIndex()].equalsIgnoreCase("false")) {

				updateCarAvailable = 0;
			}

		}

	}

	//method to return this gui used in JFileChooser
	public ManageCarGui getManager() {
		return this;
	}
}