  
package gui;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import car.Car;
import database.DbConnect;
import main.LogoutSession;

public class ManagerGui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//attributes for ManagerGui
	private JButton settleRent;
	private JButton next;
	private JButton addCar;
	private JTextField searchText;
	private JButton searchBtn;
	private JButton logout;
	@SuppressWarnings("unused")
	private JList<?> availableCars;
	private JLabel companyLogo;
	@SuppressWarnings("unused")
	private JLabel listName;
	private ImageIcon logo;
	DbConnect connect = new DbConnect();
	private ArrayList<Car> cars = connect.getCar(null, 1);  
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
	private String plateValue;
	private ArrayList<Car> car;
	private Car selectedCar;
	private JLabel imageComponent;
	private JTextField ratingField;
	
	//defining constructor
	public ManagerGui(){
		//title
		super("Manager Interface");
		getContentPane().setLayout(null);
		this.setSize(1010,678);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		setIconImage(new ImageIcon(LoginGui.class.getResource("icon.png")).getImage());
		//instantiate attributes
		settleRent = new JButton("Settle Rent");
		settleRent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettleRentGui settleRent = new SettleRentGui();
				settleRent.setVisible(true);
				dispose();
			}
		});
		settleRent.setBounds(10, 600, 100, 30);
	
		
		next = new JButton("Next");
		next.setBounds(883,600,100,30);
		
		
		addCar = new JButton("Manage Cars");
		addCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				ManageCarGui ac = new ManageCarGui();
				ac.setVisible(true);
				dispose();
			}
		});
		addCar.setBounds(458,600,120,30);
		
		searchBtn = new JButton("Search");
		searchBtn.setBounds(149,38, 100,30);
		
		searchText = new JTextField(20);
		searchText.setBounds(10,38,140,30);
		
		//instantiate new database connection
		DbConnect connect = new DbConnect();
		cars = connect.bindTable(null, 1);
		//cars = connect.getCarI(null, 1);
		//create array to store names of cars available
		//loop through array list of available cars objects
		String columnName [] = {"Plate Number","Name","Seats", "Gear", "Price (MUR)"};
		Object [][] dataFromDb = new Object[cars.size()][5];
		
        for(int i = 0; i < cars.size(); i++){
        	
//        	 if(cars.get(i).getCarImage() != null){
//                 ImageIcon image = new ImageIcon(new ImageIcon(cars.get(i).getCarImage()).getImage()
//                 .getScaledInstance(150, 120, Image.SCALE_SMOOTH) );   
//                    
//                dataFromDb[i][0] = image;
//                }
//                else{
//                    dataFromDb[i][0] = null;
//                }
        	 	dataFromDb[i][0] = cars.get(i).getCarPlateNumber();
        	 	dataFromDb[i][1] = cars.get(i).getFullCarName();
				dataFromDb[i][2] = cars.get(i).getSeats();
				dataFromDb[i][3] = cars.get(i).getGear();
				dataFromDb[i][4] = cars.get(i).getCarRate();
        }
        
        
        
		final JTable availabilityTable = new JTable(dataFromDb,columnName);
		availabilityTable.setShowGrid(false);
		availabilityTable.setRowSelectionAllowed(true);
		availabilityTable.setDragEnabled(true);
		availabilityTable.setRowSelectionInterval(0, 0);
		ListSelectionModel select = availabilityTable.getSelectionModel();
		select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		select.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				int rowSelected = availabilityTable.getSelectedRow();
				plateValue = availabilityTable.getModel().getValueAt(rowSelected, 0).toString();
				car = connect.getCar(plateValue, -1);
				selectedCar = car.get(0);
				modelField.setText(selectedCar.getCarModel());
				makeField.setText(selectedCar.getCarMake());
				typeField.setText(selectedCar.getCarType());
				yearField.setText(String.valueOf(selectedCar.getCarYear()));
				gearField.setText(selectedCar.getGear());
				fuelField.setText(selectedCar.getCarFuelType());
				engineField.setText(String.valueOf(selectedCar.getEngineSize()) + "L");
				trunkField.setText(String.valueOf(selectedCar.getCarTrunkSize()) + "L");
				rateField.setText("Rs "+String.valueOf(selectedCar.getCarRate()));
				penaltyField.setText("Rs "+String.valueOf(selectedCar.getPenaltyPrice()));
				noSeatJT.setText(String.valueOf(selectedCar.getCarSeatNo()));
				ratingField.setText(String.valueOf(selectedCar.getCarRating()));
				if(selectedCar.isCarAvailability()) {
				availableField.setText("Yes");
				}
				else{
					availableField.setText("No");
				}
				
				cpnField.setText(selectedCar.getCarPlateNumber());
				
				ImageIcon image = new ImageIcon(new ImageIcon(selectedCar.getCarImage()).getImage().getScaledInstance(401, 198, Image.SCALE_SMOOTH));  
				imageComponent.setIcon(image);
			}
		});
		
		
//		DisplayImage modell = new DisplayImage(dataFromDb,columnName);
//		availabilityTable.setModel(modell);
		
//		availabilityTable.setRowHeight(120);
		
		availabilityTable.getColumnModel().getColumn(0).setResizable(false);
		availabilityTable.getColumnModel().getColumn(1).setResizable(false);
		availabilityTable.getColumnModel().getColumn(2).setResizable(false);
		availabilityTable.getColumnModel().getColumn(3).setResizable(false);
		availabilityTable.getColumnModel().getColumn(4).setResizable(false);
//		availabilityTable.getColumnModel().getColumn(5).setResizable(false);
		availabilityTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane sp = new JScrollPane(availabilityTable);
		sp.setBounds(10,80, 568, 510);
		sp.setVisible(true);
		
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
			
		logo = new ImageIcon(this.getClass().getResource("companyName.png"));
		companyLogo = new JLabel(logo);
		companyLogo.setBounds(404,16,200,30);
		//add to layout
		getContentPane().add(settleRent);
		getContentPane().add(next);
		getContentPane().add(next);
		getContentPane().add(addCar);

		getContentPane().add(searchText);
		getContentPane().add(searchBtn);
		getContentPane().add(companyLogo);
		getContentPane().add(logout);
		getContentPane().add(sp);
		
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
		modelField.setHorizontalAlignment(SwingConstants.CENTER);
		modelField.setEditable(false);
		modelField.setBounds(635, 303, 112, 28);
		getContentPane().add(modelField);
		modelField.setColumns(10);
		
		makeField = new JTextField();
		makeField.setHorizontalAlignment(SwingConstants.CENTER);
		makeField.setEditable(false);
		makeField.setBounds(635, 337, 112, 28);
		getContentPane().add(makeField);
		makeField.setColumns(10);
		
		cpnField = new JTextField();
		cpnField.setHorizontalAlignment(SwingConstants.CENTER);
		cpnField.setEditable(false);
		cpnField.setBounds(635, 371, 112, 28);
		getContentPane().add(cpnField);
		cpnField.setColumns(10);
		
		cpn = new JLabel("CPN:");
		cpn.setBounds(590, 380, 50, 16);
		getContentPane().add(cpn);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(945, 39, 38, 28);
		getContentPane().add(btnNewButton);
		
		Detailpane = new JPanel();
		Detailpane.setForeground(Color.LIGHT_GRAY);
		Detailpane.setBorder(new LineBorder(Color.GRAY));
		Detailpane.setBounds(581, 297, 401, 290);
		getContentPane().add(Detailpane);
		
		typeField = new JTextField();
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
		
		selectedCar = cars.get(0);
		modelField.setText(selectedCar.getCarModel());
		makeField.setText(selectedCar.getCarMake());
		typeField.setText(selectedCar.getCarType());
		
		fuelField = new JTextField();
		fuelField.setHorizontalAlignment(SwingConstants.CENTER);
		fuelField.setBounds(55, 256, 112, 28);
		Detailpane.add(fuelField);
		fuelField.setEditable(false);
		fuelField.setColumns(10);
		fuelField.setText(selectedCar.getCarFuelType());
		
		fuel = new JLabel("Fuel:");
		fuel.setBounds(6, 262, 50, 16);
		Detailpane.add(fuel);
		
		gear = new JLabel("Gear:");
		gear.setBounds(6, 222, 50, 16);
		Detailpane.add(gear);
		
		gearField = new JTextField();
		gearField.setHorizontalAlignment(SwingConstants.CENTER);
		gearField.setBounds(55, 216, 112, 28);
		Detailpane.add(gearField);
		gearField.setEditable(false);
		gearField.setColumns(10);
		gearField.setText(selectedCar.getGear());
		
		yearField = new JTextField();
		yearField.setHorizontalAlignment(SwingConstants.CENTER);
		yearField.setBounds(55, 171, 50, 28);
		Detailpane.add(yearField);
		yearField.setEditable(false);
		yearField.setColumns(10);
		yearField.setText(String.valueOf(selectedCar.getCarYear()));
		
		available = new JLabel("Available:");
		available.setBounds(225, 262, 66, 16);
		Detailpane.add(available);
		
		availableField = new JTextField();
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
		penaltyField.setHorizontalAlignment(SwingConstants.CENTER);
		penaltyField.setBounds(309, 171, 86, 28);
		Detailpane.add(penaltyField);
		penaltyField.setEditable(false);
		penaltyField.setColumns(10);
		penaltyField.setText("Rs "+String.valueOf(selectedCar.getPenaltyPrice()));
		
		rateField = new JTextField();
		rateField.setHorizontalAlignment(SwingConstants.CENTER);
		rateField.setBounds(309, 131, 86, 28);
		Detailpane.add(rateField);
		rateField.setEditable(false);
		rateField.setColumns(10);
		rateField.setText("Rs "+String.valueOf(selectedCar.getCarRate()));
		
		engineField = new JTextField();
		engineField.setBounds(319, 6, 76, 28);
		Detailpane.add(engineField);
		engineField.setHorizontalAlignment(SwingConstants.CENTER);
		engineField.setEditable(false);
		engineField.setColumns(10);
		engineField.setText(String.valueOf(selectedCar.getEngineSize()) + "L");
		
		trunkField = new JTextField();
		trunkField.setBounds(319, 46, 76, 28);
		Detailpane.add(trunkField);
		trunkField.setHorizontalAlignment(SwingConstants.CENTER);
		trunkField.setEditable(false);
		trunkField.setColumns(10);
		trunkField.setText(String.valueOf(selectedCar.getCarTrunkSize()) + "L");
		
		noSeatJL = new JLabel("No of seats:");
		noSeatJL.setBounds(225, 78, 96, 28);
		Detailpane.add(noSeatJL);
		
		noSeatJT = new JTextField();
		noSeatJT.setBounds(319, 78, 28, 28);
		Detailpane.add(noSeatJT);
		noSeatJT.setHorizontalAlignment(SwingConstants.CENTER);
		noSeatJT.setEditable(false);
		noSeatJT.setText(String.valueOf(selectedCar.getCarSeatNo()));
		
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
		ratingField.setHorizontalAlignment(SwingConstants.CENTER);
		ratingField.setBounds(309, 216, 28, 28);
		ratingField.setEditable(false);
		Detailpane.add(ratingField);
		ratingField.setColumns(10);
		ratingField.setText(String.valueOf(selectedCar.getCarRating()));
		if(selectedCar.isCarAvailability()) {
		availableField.setText("Yes");
		}
		else{
			availableField.setText("No");
		}
		
		cpnField.setText(selectedCar.getCarPlateNumber());
		
		ImageIcon image = new ImageIcon(new ImageIcon(selectedCar.getCarImage()).getImage().getScaledInstance(401, 198, Image.SCALE_SMOOTH));  
		imageComponent.setIcon(image);
		//
		
	}

public static void main(String []args) throws UnsupportedLookAndFeelException {
	UIManager.setLookAndFeel(new NimbusLookAndFeel());
	ManagerGui man = new ManagerGui();
	man.setVisible(true);
	
}
}