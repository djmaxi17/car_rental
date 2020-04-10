  
package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import car.Car;
import database.DbConnect;
import main.LogoutSession;

public class ManagerGui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	//Connect to Database
	DbConnect connect = new DbConnect();
	private ArrayList<Car> cars = connect.getCar(null, 1);  
	private JButton next;
	private JTextField searchText;
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
	JTable availabilityTable;
	private Color textC = new Color(214,217,220);
	private Color buttonCol = new Color(79,99,116);
//	private Color c = new Color(0,0,0,180);
	//previously selected car in rent registration and back
	private ArrayList<Car> car;
	private Car selCar;
	private String getPlate;
	private int currentRow;
	DefaultTableModel dm;

public ManagerGui(String revertPlate) {
	super("Manager Interface");
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
//	this.getContentPane().setBackground(c);
	this.getContentPane().setBackground(Color.DARK_GRAY);
	setIconImage(new ImageIcon(LoginGui.class.getResource("icon.png")).getImage());
	
	//logo
	ImageIcon logoimg = new ImageIcon(this.getClass().getResource("companyName.png"));
	JLabel logoLbl = new JLabel(logoimg);
	logoLbl.setBounds(387,18,225,30);
	this.getContentPane().add(logoLbl);
	
	searchText = new JTextField(20);
	searchText.setBackground(new Color(35,35,36));
	searchText.setFont(new Font("SansSerif", Font.PLAIN, 15));
//	searchText.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
	searchText.setForeground(Color.WHITE);
	searchText.setBounds(70,38,232,30);
	
	searchText.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			
			String searchValue = searchText.getText().toLowerCase();
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
    	 	dataFromDb[i][0] = cars.get(i).getCarPlateNumber().toLowerCase();
    	 	dataFromDb[i][1] = cars.get(i).getFullCarName().toLowerCase();
			dataFromDb[i][2] = cars.get(i).getSeats();
			dataFromDb[i][3] = cars.get(i).getGear().toLowerCase();
			dataFromDb[i][4] = cars.get(i).getCarRate();
    }
 
    //creates table with data from dataFromDb and columnName
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
//    availabilityTable.setFocusable(false);
    availabilityTable.addFocusListener(new FocusAdapter() {
    	@Override
    	public void focusGained(FocusEvent e) {
    		
    	}
    });
    availabilityTable.setRowHeight(25);
    availabilityTable.setForeground(textC);
    availabilityTable.setBorder(null);
    Color colortest = new Color(87,90,92);
    availabilityTable.setBackground(colortest);
    availabilityTable.setShowGrid(false);
    Color col = new Color(61,67,72);
    availabilityTable.setSelectionBackground(col);
	dm = (DefaultTableModel) availabilityTable.getModel();
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
			int rowSelected = availabilityTable.convertRowIndexToModel(availabilityTable.getSelectedRow());
			if(rowSelected !=-1) {
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
			
			ImageIcon image = new ImageIcon(new ImageIcon(selCar.getCarImage()).getImage().getScaledInstance(402, 210, Image.SCALE_SMOOTH));  
			imageComponent.setIcon(image);
			getPlate = cpnField.getText();
			}
		}
	});
	
	//some adjustments to the table and adding it to and jscrollpane which is then added to another jpanel sp
	availabilityTable.getColumnModel().getColumn(0).setResizable(false);
	availabilityTable.getColumnModel().getColumn(0).setPreferredWidth(100);
	
	availabilityTable.getColumnModel().getColumn(1).setResizable(false);
	availabilityTable.getColumnModel().getColumn(1).setPreferredWidth(200);
	availabilityTable.getColumnModel().getColumn(2).setResizable(false);
	availabilityTable.getColumnModel().getColumn(2).setMaxWidth(60);
	availabilityTable.getColumnModel().getColumn(3).setResizable(false);
	availabilityTable.getColumnModel().getColumn(3).setPreferredWidth(100);

	availabilityTable.getColumnModel().getColumn(4).setResizable(false);
	availabilityTable.getColumnModel().getColumn(4).setPreferredWidth(100);
	
	availabilityTable.getTableHeader().setReorderingAllowed(false);
	JScrollPane sp = new JScrollPane(availabilityTable);
	sp.setBorder(null);
	Color colorSp = new Color(87,90,92); 
	sp.getViewport().setBackground(colorSp);
	sp.setBounds(10,80, 568, 507);
	sp.setVisible(true);
	
	
	//defines the logout button and what to do what it is clicked
	logout = new JButton("Logout");
	logout.setFont(new Font("SansSerif", Font.BOLD, 12));
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
	logout.setBackground(buttonCol);
	//logout.setForeground(textCB);
	logout.setBounds(843,38,100,30);
	getContentPane().add(searchText);
	getContentPane().add(logout);
	getContentPane().add(sp);
	
	
	//defines the text fields on the right for the details of the selected car
	imageComponent = new JLabel();
	imageComponent.setBounds(581, 80, 402, 210);
	getContentPane().add(imageComponent);
	
	JLabel model = new JLabel("Model:");
	model.setForeground(textC);
	model.setFont(new Font("SansSerif", Font.BOLD, 12));
	model.setBounds(590, 309, 50, 16);
	getContentPane().add(model);
	
	JLabel make = new JLabel("Make:");
	make.setForeground(textC);
	make.setFont(new Font("SansSerif", Font.BOLD, 12));
	make.setBounds(590, 343, 50, 16);
	getContentPane().add(make);
	
	modelField = new JTextField();
	modelField.setForeground(new Color(51, 51, 51));
	modelField.setBackground(new Color(153, 153, 153));
	modelField.setHorizontalAlignment(SwingConstants.CENTER);
	modelField.setEditable(false);
	modelField.setBounds(635, 303, 112, 28);
	getContentPane().add(modelField);
	modelField.setColumns(10);
	
	makeField = new JTextField();
	makeField.setForeground(new Color(51, 51, 51));
	makeField.setBackground(new Color(153, 153, 153));
	makeField.setHorizontalAlignment(SwingConstants.CENTER);
	makeField.setEditable(false);
	makeField.setBounds(635, 337, 112, 28);
	getContentPane().add(makeField);
	makeField.setColumns(10);
	
	cpnField = new JTextField();
	cpnField.setForeground(new Color(51, 51, 51));
	cpnField.setBackground(new Color(153, 153, 153));
	cpnField.setHorizontalAlignment(SwingConstants.CENTER);
	cpnField.setEditable(false);
	cpnField.setBounds(635, 371, 112, 28);
	getContentPane().add(cpnField);
	cpnField.setColumns(10);
	
	cpn = new JLabel("CPN:");
	cpn.setForeground(textC);
	cpn.setFont(new Font("SansSerif", Font.BOLD, 12));
	cpn.setBounds(590, 380, 50, 16);
	getContentPane().add(cpn);
	
	Detailpane = new JPanel();
	Color colorDp = new Color(87,90,92);
	Detailpane.setBackground(colorDp);
	Detailpane.setForeground(Color.LIGHT_GRAY);
	Detailpane.setBorder(null);
	Detailpane.setBounds(581, 294, 401, 293);
	getContentPane().add(Detailpane);
	
	typeField = new JTextField();
	typeField.setForeground(new Color(51, 51, 51));
	typeField.setBackground(new Color(153, 153, 153));
	typeField.setHorizontalAlignment(SwingConstants.CENTER);
	typeField.setEditable(false);
	typeField.setBounds(55, 131, 112, 28);
	typeField.setColumns(10);
	
	year = new JLabel("Year:");
	year.setForeground(textC);
	year.setFont(new Font("SansSerif", Font.BOLD, 12));
	year.setBounds(6, 177, 50, 16);
	
	type = new JLabel("Type:");
	type.setForeground(textC);
	type.setFont(new Font("SansSerif", Font.BOLD, 12));
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
	fuelField.setForeground(new Color(51, 51, 51));
	fuelField.setBackground(new Color(153, 153, 153));
	fuelField.setHorizontalAlignment(SwingConstants.CENTER);
	fuelField.setBounds(55, 256, 112, 28);
	Detailpane.add(fuelField);
	fuelField.setEditable(false);
	fuelField.setColumns(10);
	fuelField.setText(selCar.getCarFuelType());
	
	fuel = new JLabel("Fuel:");
	fuel.setForeground(textC);
	fuel.setFont(new Font("SansSerif", Font.BOLD, 12));
	fuel.setBounds(6, 262, 50, 16);
	Detailpane.add(fuel);
	
	gear = new JLabel("Gear:");
	gear.setForeground(textC);
	gear.setFont(new Font("SansSerif", Font.BOLD, 12));
	gear.setBounds(6, 222, 50, 16);
	Detailpane.add(gear);
	
	gearField = new JTextField();
	gearField.setForeground(new Color(51, 51, 51));
	gearField.setBackground(new Color(153, 153, 153));
	gearField.setHorizontalAlignment(SwingConstants.CENTER);
	gearField.setBounds(55, 216, 112, 28);
	Detailpane.add(gearField);
	gearField.setEditable(false);
	gearField.setColumns(10);
	gearField.setText(selCar.getGear());
	
	yearField = new JTextField();
	yearField.setForeground(new Color(51, 51, 51));
	yearField.setBackground(new Color(153, 153, 153));
	yearField.setHorizontalAlignment(SwingConstants.CENTER);
	yearField.setBounds(55, 171, 50, 28);
	Detailpane.add(yearField);
	yearField.setEditable(false);
	yearField.setColumns(10);
	yearField.setText(String.valueOf(selCar.getCarYear()));
	
	available = new JLabel("Available:");
	available.setForeground(textC);
	available.setFont(new Font("SansSerif", Font.BOLD, 12));
	available.setBounds(225, 262, 66, 16);
	Detailpane.add(available);
	
	availableField = new JTextField();
	availableField.setForeground(new Color(51, 51, 51));
	availableField.setBackground(new Color(153, 153, 153));
	availableField.setHorizontalAlignment(SwingConstants.CENTER);
	availableField.setBounds(309, 256, 41, 28);
	Detailpane.add(availableField);
	availableField.setEditable(false);
	availableField.setColumns(10);
	
	penalty = new JLabel("Penalty fee:");
	penalty.setForeground(textC);
	penalty.setFont(new Font("SansSerif", Font.BOLD, 12));
	penalty.setBounds(225, 177, 96, 16);
	Detailpane.add(penalty);
	
	rate = new JLabel("Rate per day:");
	rate.setForeground(textC);
	rate.setFont(new Font("SansSerif", Font.BOLD, 12));
	rate.setBounds(225, 137, 94, 16);
	Detailpane.add(rate);
	
	penaltyField = new JTextField();
	penaltyField.setForeground(new Color(51, 51, 51));
	penaltyField.setBackground(new Color(153, 153, 153));
	penaltyField.setHorizontalAlignment(SwingConstants.CENTER);
	penaltyField.setBounds(309, 171, 86, 28);
	Detailpane.add(penaltyField);
	penaltyField.setEditable(false);
	penaltyField.setColumns(10);
	penaltyField.setText("Rs "+String.valueOf(selCar.getPenaltyPrice()));
	
	rateField = new JTextField();
	rateField.setForeground(new Color(51, 51, 51));
	rateField.setBackground(new Color(153, 153, 153));
	rateField.setHorizontalAlignment(SwingConstants.CENTER);
	rateField.setBounds(309, 131, 86, 28);
	Detailpane.add(rateField);
	rateField.setEditable(false);
	rateField.setColumns(10);
	rateField.setText("Rs "+String.valueOf(selCar.getCarRate()));
	engineField = new JTextField();
	engineField.setForeground(new Color(51, 51, 51));
	engineField.setBackground(new Color(153, 153, 153));
	engineField.setBounds(319, 6, 76, 28);
	Detailpane.add(engineField);
	engineField.setHorizontalAlignment(SwingConstants.CENTER);
	engineField.setEditable(false);
	engineField.setColumns(10);
	engineField.setText(String.valueOf(selCar.getEngineSize()) +"L");
	trunkField = new JTextField();
	trunkField.setForeground(new Color(51, 51, 51));
	trunkField.setBackground(new Color(153, 153, 153));
	trunkField.setBounds(319, 46, 76, 28);
	Detailpane.add(trunkField);
	trunkField.setHorizontalAlignment(SwingConstants.CENTER);
	trunkField.setEditable(false);
	trunkField.setColumns(10);
	trunkField.setText(String.valueOf(selCar.getCarTrunkSize()) + "L");
	
	noSeatJL = new JLabel("No of seats:");
	noSeatJL.setForeground(textC);
	noSeatJL.setFont(new Font("SansSerif", Font.BOLD, 12));
	noSeatJL.setBounds(225, 78, 96, 28);
	Detailpane.add(noSeatJL);
	
	noSeatJT = new JTextField();
	noSeatJT.setForeground(new Color(51, 51, 51));
	noSeatJT.setBackground(new Color(153, 153, 153));
	noSeatJT.setBounds(319, 78, 28, 28);
	Detailpane.add(noSeatJT);
	noSeatJT.setHorizontalAlignment(SwingConstants.CENTER);
	noSeatJT.setEditable(false);
	noSeatJT.setText(String.valueOf(selCar.getCarSeatNo()));
	
	engineSize = new JLabel("Engine Size:");
	engineSize.setForeground(textC);
	engineSize.setFont(new Font("SansSerif", Font.BOLD, 12));
	engineSize.setBounds(225, 12, 94, 16);
	Detailpane.add(engineSize);
	
	trunkSize = new JLabel("Trunk Size:");
	trunkSize.setForeground(textC);
	trunkSize.setFont(new Font("SansSerif", Font.BOLD, 12));
	trunkSize.setBounds(225, 52, 94, 16);
	Detailpane.add(trunkSize);
	
	JLabel ratingLabel = new JLabel("Rating: ");
	ratingLabel.setForeground(textC);
	ratingLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
	ratingLabel.setBounds(225, 222, 50, 16);
	
	Detailpane.add(ratingLabel);
	
	ratingField = new JTextField();
	ratingField.setForeground(new Color(51, 51, 51));
	ratingField.setBackground(new Color(153, 153, 153));
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
	
	ImageIcon image = new ImageIcon(new ImageIcon(selCar.getCarImage()).getImage().getScaledInstance(402, 210, Image.SCALE_SMOOTH));  
	imageComponent.setIcon(image);
	
	
	getPlate = cpnField.getText();
	
	
		//defines the next button and send the selected car plate number to the rentRegistrationGui
		next = new JButton("Next");
		next.setFont(new Font("SansSerif", Font.BOLD, 12));
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RentRegistrationGui regCus = new RentRegistrationGui(getPlate);
				regCus.setVisible(true);
				regCus.getNoDaysTf().requestFocus();
				dispose();
			}
		});
		next.setBounds(883,600,100,30);
		next.setBackground(buttonCol);
		//next.setForeground(textCB);
		getContentPane().add(next);

		//setting button for the employee to change their password
		JButton settingButton = new JButton("New button");
		settingButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		settingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingGui gui = new SettingGui();
				gui.setVisible(true);
			}
		});

		settingButton.setBackground(buttonCol);
		//settingButton.setForeground(textCB);
		settingButton.setBounds(945, 39, 38, 28);
		getContentPane().add(settingButton);
		
		JLabel searchLabel = new JLabel("Search");
		//searchLabel.setForeground(textCB);
		searchLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		searchLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		searchLabel.setBounds(10, 45, 67, 16);
		getContentPane().add(searchLabel);
		
		JButton manageCarBtn = new JButton("Manage Car");
		manageCarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageCarGui manCar = new ManageCarGui();
				manCar.setVisible(true);
				dispose();
			}
		});
		manageCarBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		manageCarBtn.setBackground(buttonCol);
		//manageCarBtn.setForeground(textCB);
		manageCarBtn.setBounds(451, 600, 127, 30);
		getContentPane().add(manageCarBtn);
		
		JButton settleRent = new JButton("Settle Rent");
		settleRent.setFont(new Font("SansSerif", Font.BOLD, 12));
		settleRent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettleRentGui settle = new SettleRentGui();
				settle.setVisible(true);
				dispose();
			}
		});
		settleRent.setBackground(buttonCol);
		settleRent.setBounds(6, 600, 117, 30);
		getContentPane().add(settleRent);
		
	}

}









