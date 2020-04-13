package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

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

import database.DbConnect;
import employee.Clerk;
import employee.Employee;
import employee.Manager;
import employee.Technician;
import main.LoginSession;
import main.LogoutSession;

public class TechnicianMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private String[] empType = {
		"Type", "Clerk", "Technician", "Manager"
	};
	private JTable empTable;
	private JTextField addEmpFname;
	private JTextField addEmpLname;
	private JTextField rEmpFname;
	private JTextField rEmpLname;
	private JTextField rEmpPosition;
	private JLabel email;

	private Color colorDp = new Color(87, 90, 92);
	private DbConnect connect = new DbConnect();
	private ArrayList<Employee> employ = new ArrayList<Employee> ();
	private DefaultTableModel dm;
	public static int empyId;
	public Employee employee;
	private Color textC = Color.WHITE;
	private JTable table;
	private int selectedType;

	private ArrayList<Technician> technicians;
	private ArrayList<Manager> managers;
	private ArrayList<Clerk> clerks;

	private Technician loginTechnician;
	/**
	 * Create the frame.
	 */
	public TechnicianMain() {
		super("Technician Interface");

		//display login on closing
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				LoginGui login = null;
				login = new LoginGui();
				login.setVisible(true);
			}
		});
		technicians = connect.getTechnicians();
		managers = connect.getManagers();
		clerks = connect.getClerks();
		loginTechnician = getTechnician(technicians, "admin", "admin");
		if (LoginSession.isLoggedIn) {
			loginTechnician = getTechnician(technicians, LoginSession.userFirstName, LoginSession.username);
		}
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(830, 474);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(LoginGui.class.getResource("icon.png")).getImage());
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//logo
		ImageIcon logoimg = new ImageIcon(this.getClass().getResource("companyName.png"));
		JLabel logoLbl = new JLabel(logoimg);
		logoLbl.setBounds(228, 6, 225, 30);
		this.getContentPane().add(logoLbl);

		JButton optionBtn = new JButton("...");
		optionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingGui gui = new SettingGui();
				gui.setVisible(true);
			}
		});
		optionBtn.setBounds(768, 19, 42, 29);
		contentPane.add(optionBtn);

		JLabel TitleLabel = new JLabel("Welcome back " + loginTechnician.getFullName());

		TitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
		TitleLabel.setBounds(6, 42, 375, 16);
		TitleLabel.setForeground(textC);
		contentPane.add(TitleLabel);

		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(colorDp);
		searchPanel.setBounds(6, 60, 426, 378);
		contentPane.add(searchPanel);
		searchPanel.setLayout(null);

		JLabel searchLbl = new JLabel("Search:");
		searchLbl.setForeground(textC);
		searchLbl.setFont(new Font("SansSerif", Font.BOLD, 16));
		searchLbl.setBounds(6, 35, 61, 16);
		searchPanel.add(searchLbl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 63, 414, 309);
		scrollPane.setBorder(null);
		searchPanel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JScrollPane scrollDefault = new JScrollPane();
		scrollPane.setBounds(6, 63, 414, 309);
		scrollPane.setBorder(null);
		searchPanel.add(scrollPane);

		//table start

		employ = connect.BindTableEmployee(0, -1);
		//create array list to store names of employ available
		//loop through array list of available employee objects
		String columnName[] = {
			"Id", "Full Name", "Position"
		};
		Object[][] dataFromDb = new Object[employ.size()][3];

		for (int i = 0; i<employ.size(); i++) {

			//populates cars data in an object 2D array dataFromDb
			dataFromDb[i][0] = employ.get(i).getEmpId();
			dataFromDb[i][1] = employ.get(i).getFullName().toLowerCase();
			dataFromDb[i][2] = employ.get(i).getPosition().toLowerCase();
		}

		//creates table with data from dataFromDb and columnName
		DefaultTableModel tableModel = new DefaultTableModel(dataFromDb, columnName) {
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
		Color colortest = new Color(87, 90, 92);
		Color col = new Color(61, 67, 72);

		JLabel lblNewLabel_4 = new JLabel("Search An Employee");
		lblNewLabel_4.setFont(new Font("Serif", Font.BOLD, 16));
		lblNewLabel_4.setBounds(148, 6, 152, 16);
		searchPanel.add(lblNewLabel_4);

		scrollPane.setBounds(6, 63, 414, 309);
		searchPanel.add(scrollPane);
		empTable = new JTable(tableModel);
		scrollPane.setViewportView(empTable);
		//	    availabilityTable.setFocusable(false);
		empTable.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {

			}
		});
		empTable.setRowHeight(25);
		empTable.setBorder(null);
		empTable.setBackground(colortest);
		empTable.setShowGrid(false);
		empTable.setBounds(6, 63, 414, 309);
		empTable.setForeground(textC);
		empTable.setSelectionBackground(col);
		dm = (DefaultTableModel) empTable.getModel();
		empTable.setRowSelectionAllowed(true);
		empTable.setDragEnabled(true);
		empTable.setVisible(false);

		// creates and displays blank table on boot up -start
		DefaultTableModel blankModel = new DefaultTableModel();
		blankModel.addColumn("Id");
		blankModel.addColumn("Full Name");
		blankModel.addColumn("Position");

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
		blankcsp.setBounds(6, 63, 414, 309);
		scrollDefault.add(blankcsp);
		blankcsp.setVisible(true);
		// creates and displays blank table on boot up - end

		final ListSelectionModel select = empTable.getSelectionModel();

		//defines a complete row selection
		select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		select.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//fills the autofill fields
				int rowSelected = empTable.convertRowIndexToModel(empTable.getSelectedRow());
				if (rowSelected != -1) {

					String employeeIdStr = empTable.getModel().getValueAt(rowSelected, 0).toString();
					employ = connect.BindTableEmployee(Integer.valueOf(employeeIdStr), -1);
					employee = employ.get(0);
					rEmpFname.setText(employee.getEmpFirstName());
					rEmpLname.setText(employee.getEmpLastName());
					rEmpPosition.setText(employee.getPosition());
					email.setText(employee.getEmpEmail());
				}
			}
		});
		empTable.getColumnModel().getColumn(0).setResizable(false);
		empTable.getColumnModel().getColumn(0).setPreferredWidth(5);
		empTable.getColumnModel().getColumn(1).setResizable(false);
		empTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		empTable.getColumnModel().getColumn(2).setResizable(false);
		empTable.getColumnModel().getColumn(2).setWidth(200);
		empTable.getTableHeader().setReorderingAllowed(false);
		//end of table 

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				String searchValue = textField.getText().toLowerCase();
				String removeEscapeChar = searchValue.replaceAll("[^a-z -.A-Z0-9]", "");
				TableRowSorter<DefaultTableModel> max = new TableRowSorter<DefaultTableModel> (dm);
				empTable.setRowSorter(max);
				if (removeEscapeChar.length() != 0) {
					max.setRowFilter(RowFilter.regexFilter(removeEscapeChar));
					empTable.setVisible(true);
				} else {
					max.setRowFilter(null);
					empTable.setVisible(false);
				}

			}
		});
		textField.setBounds(70, 30, 350, 26);
		searchPanel.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int value = JOptionPane.showConfirmDialog(getParent(), "Are you sure to Log Out?", "Confirmation", JOptionPane.YES_NO_OPTION);
				if (value == JOptionPane.YES_OPTION) {
					LoginGui login = new LoginGui();
					LogoutSession.logoutH(login);
					dispose();
				}
			}
		});
		btnNewButton.setBounds(643, 19, 117, 29);
		contentPane.add(btnNewButton);

		JPanel addEmpPanel = new JPanel();
		addEmpPanel.setBackground(colorDp);
		addEmpPanel.setBounds(435, 60, 375, 182);
		contentPane.add(addEmpPanel);
		addEmpPanel.setLayout(null);

		JLabel addEmpJl = new JLabel("Add An Employee");
		addEmpJl.setFont(new Font("Serif", Font.BOLD, 16));
		addEmpJl.setBounds(117, 6, 160, 16);
		addEmpPanel.add(addEmpJl);

		JLabel fnameLbl = new JLabel("First Name:");
		fnameLbl.setForeground(textC);
		fnameLbl.setBounds(6, 42, 78, 16);
		addEmpPanel.add(fnameLbl);

		addEmpFname = new JTextField();
		addEmpFname.setHorizontalAlignment(SwingConstants.CENTER);
		addEmpFname.setForeground(Color.BLACK);
		addEmpFname.setBounds(6, 60, 190, 26);
		addEmpFname.setBackground(new Color(153, 153, 153));
		addEmpPanel.add(addEmpFname);
		addEmpFname.setColumns(10);

		JLabel lnameLbl = new JLabel("Last Name:");
		lnameLbl.setForeground(textC);
		lnameLbl.setBounds(6, 98, 78, 16);
		addEmpPanel.add(lnameLbl);

		addEmpLname = new JTextField();
		addEmpLname.setHorizontalAlignment(SwingConstants.CENTER);
		addEmpLname.setForeground(Color.BLACK);
		addEmpLname.setBounds(6, 114, 190, 26);
		addEmpLname.setBackground(new Color(153, 153, 153));
		addEmpPanel.add(addEmpLname);
		addEmpLname.setColumns(10);

		@SuppressWarnings({
			"rawtypes", "unchecked"
		})
		JComboBox addEmpCB = new JComboBox(empType);
		addEmpCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedType = addEmpCB.getSelectedIndex();
				if (selectedType == 0) {
					System.out.println("0 selected");
				} else if (selectedType == 1) {
					System.out.println("1 selected");
				} else if (selectedType == 2) {
					System.out.println("2 selected");
				} else if (selectedType == 3) {
					System.out.println("3 selected");
				}
			}
		});

		addEmpCB.setForeground(textC);
		addEmpCB.setBounds(208, 61, 161, 27);
		addEmpPanel.add(addEmpCB);

		JButton addEmpBtn = new JButton("Add Employee");
		addEmpBtn.addActionListener(new ActionListener() {
			//addEmpFname, addEmpLname
			public void actionPerformed(ActionEvent e) {

				if (selectedType == 1) {
					if (addEmpFname.getText().equalsIgnoreCase("") && addEmpLname.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null, "Please Do Not Leave Any Field Blank!", "ERROR", JOptionPane.ERROR_MESSAGE);
					} else if (addEmpFname.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null, "Please Do Not Let The First Name Field Blank!", "ERROR", JOptionPane.ERROR_MESSAGE);
					} else if (addEmpLname.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null, "Please Do Not Let The Last Name Field Blank!", "ERROR", JOptionPane.ERROR_MESSAGE);
					} else {
						int value = JOptionPane.showConfirmDialog(getParent(), "Are you sure to add this new clerk?", "Confirmation", JOptionPane.YES_NO_OPTION);
						if (value == JOptionPane.YES_OPTION) {

							String fname = addEmpFname.getText();
							String lname = addEmpLname.getText();
							DbConnect connect = new DbConnect();
							int check = connect.getEmployeeId(fname, lname, "clerk");
							if (check == 0) {

								Clerk newClerk;
								try {
									newClerk = loginTechnician.createClerk(clerks, fname, lname, loginTechnician.getEmpId());

									if (newClerk != null) {
										JOptionPane.showMessageDialog(null, "Name: " + newClerk.getFullName() + "\nEmail: " + newClerk.getEmpEmail() +
											"\nPassword: " + newClerk.getEmpPassword(), "New Clerk Details", JOptionPane.PLAIN_MESSAGE);

										addEmpFname.setText("");
										addEmpLname.setText("");
										dispose();
										TechnicianMain reload = new TechnicianMain();
										reload.setVisible(true);
									} else {
										JOptionPane.showMessageDialog(null, "Error in adding new clerk! Try Again", "Error", JOptionPane.ERROR_MESSAGE);
									}
								} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {

									e1.printStackTrace();
								}
							} else {
								JOptionPane.showMessageDialog(null, "Clerk with same name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}

					}
				} else if (selectedType == 3) {
					if (addEmpFname.getText().equalsIgnoreCase("") && addEmpLname.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null, "Please don't leave any field blank!", "Error", JOptionPane.ERROR_MESSAGE);
					} else if (addEmpFname.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null, "Please don't leave the First Name field blank!", "Error", JOptionPane.ERROR_MESSAGE);
					} else if (addEmpLname.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null, "Please don't leave the Last Name field blank!", "Error", JOptionPane.ERROR_MESSAGE);
					} else {

						int value = JOptionPane.showConfirmDialog(getParent(), "Are you sure to add this new manager?", "Confirmation", JOptionPane.YES_NO_OPTION);
						if (value == JOptionPane.YES_OPTION) {

							String fname = addEmpFname.getText();
							String lname = addEmpLname.getText();
							DbConnect connect = new DbConnect();
							int check = connect.getEmployeeId(fname, lname, "manager");
							if (check == 0) {

								try {
									Manager newManager = loginTechnician.createManager(managers, fname, lname, loginTechnician.getEmpId());
									if (newManager != null) {
										JOptionPane.showMessageDialog(null, "Name: " + newManager.getFullName() + "\nEmail: " + newManager.getEmpEmail() +
											"\nPassword: " + newManager.getEmpPassword(), "New Manager Details", JOptionPane.PLAIN_MESSAGE);
										addEmpFname.setText("");
										addEmpLname.setText("");
										dispose();
										TechnicianMain reload = new TechnicianMain();
										reload.setVisible(true);
									} else {
										JOptionPane.showMessageDialog(null, "Error in adding new manager! Try Again", "Error", JOptionPane.ERROR_MESSAGE);
									}
								} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {

									e1.printStackTrace();
								}
							} else {
								JOptionPane.showMessageDialog(null, "Manager with same name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				} else if (selectedType == 2) {
					if (addEmpFname.getText().equalsIgnoreCase("") && addEmpLname.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null, "Please don't leave any field blank!", "Error", JOptionPane.ERROR_MESSAGE);
					} else if (addEmpFname.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null, "Please don't leave the First Name field blank!", "Error", JOptionPane.ERROR_MESSAGE);
					} else if (addEmpLname.getText().equalsIgnoreCase("")) {
						JOptionPane.showMessageDialog(null, "Please don't leave the Last Name field blank!", "Error", JOptionPane.ERROR_MESSAGE);
					} else {

						int value = JOptionPane.showConfirmDialog(getParent(), "Are you sure to add this new technician?", "Confirmation", JOptionPane.YES_NO_OPTION);
						if (value == JOptionPane.YES_OPTION) {

							String fname = addEmpFname.getText();
							String lname = addEmpLname.getText();
							DbConnect connect = new DbConnect();
							int check = connect.getEmployeeId(fname, lname, "technician");
							if (check == 0) {
								Technician newTechnician;
								try {
									newTechnician = loginTechnician.createTechnician(technicians, fname, lname, loginTechnician.getEmpId());

									if (newTechnician != null) {
										JOptionPane.showMessageDialog(null, "Name: " + newTechnician.getFullName() + "\nEmail: " + newTechnician.getEmpEmail() +
											"\nPassword: " + newTechnician.getEmpPassword(), "New Technician Details", JOptionPane.PLAIN_MESSAGE);
										addEmpFname.setText("");
										addEmpLname.setText("");
										dispose();
										TechnicianMain reload = new TechnicianMain();
										reload.setVisible(true);
									} else {
										JOptionPane.showMessageDialog(null, "Error in adding new technician! Try Again", "Error", JOptionPane.ERROR_MESSAGE);
									}
								} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {

									e1.printStackTrace();
								}
							} else {
								JOptionPane.showMessageDialog(null, "Technician with same name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a type!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		addEmpBtn.setBounds(208, 114, 161, 29);
		addEmpPanel.add(addEmpBtn);

		JLabel positionlbladd = new JLabel("Position:");
		positionlbladd.setForeground(textC);
		positionlbladd.setBounds(216, 42, 122, 16);
		addEmpPanel.add(positionlbladd);

		JPanel removeEmpPanel = new JPanel();
		removeEmpPanel.setBounds(435, 244, 375, 194);
		contentPane.add(removeEmpPanel);
		removeEmpPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Remove An Employee");
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 16));
		lblNewLabel.setBounds(114, 6, 182, 16);
		removeEmpPanel.add(lblNewLabel);

		JLabel fnameRemoveE = new JLabel("First Name:");
		fnameRemoveE.setForeground(textC);
		fnameRemoveE.setBounds(6, 48, 104, 16);
		removeEmpPanel.add(fnameRemoveE);

		rEmpFname = new JTextField();
		rEmpFname.setEditable(false);
		rEmpFname.setForeground(Color.WHITE);
		rEmpFname.setHorizontalAlignment(SwingConstants.CENTER);
		rEmpFname.setBackground(Color.DARK_GRAY);
		rEmpFname.setBounds(6, 65, 193, 26);
		removeEmpPanel.add(rEmpFname);
		rEmpFname.setColumns(10);

		JLabel lnameRemove = new JLabel("Last Name:");
		lnameRemove.setForeground(textC);
		lnameRemove.setBounds(6, 103, 104, 16);
		removeEmpPanel.add(lnameRemove);

		rEmpLname = new JTextField();
		rEmpLname.setEditable(false);
		rEmpLname.setForeground(Color.WHITE);
		rEmpLname.setHorizontalAlignment(SwingConstants.CENTER);
		rEmpLname.setBounds(6, 121, 193, 26);
		rEmpLname.setBackground(Color.DARK_GRAY);
		removeEmpPanel.add(rEmpLname);
		rEmpLname.setColumns(10);

		JButton removeEmpBtn = new JButton("Remove Employee");
		removeEmpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String pos = rEmpPosition.getText();
				if (pos.equalsIgnoreCase("clerk")) {
					//clerk
					int value = JOptionPane.showConfirmDialog(getParent(), "Are you sure to remove this clerk?", "Confirmation", JOptionPane.YES_NO_OPTION);
					if (value == JOptionPane.YES_OPTION) {
						String fname = rEmpFname.getText();
						String lname = rEmpLname.getText();

						if (!fname.equals("c") && !lname.equals("t")) {
							boolean check = loginTechnician.deleteClerk(clerks, fname, lname);
							if (check == true) {
								JOptionPane.showMessageDialog(null, "Clerk Deleted!", "Done", JOptionPane.INFORMATION_MESSAGE);
								dispose();
								TechnicianMain reload = new TechnicianMain();
								reload.setVisible(true);
							} else {
								JOptionPane.showMessageDialog(null, "Couldn't delete clerk! Try-Again", "Error", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Cannot delete this account!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if (pos.equalsIgnoreCase("manager")) {
					//manager
					int value = JOptionPane.showConfirmDialog(getParent(), "Are you sure to remove this manager?", "Confirmation", JOptionPane.YES_NO_OPTION);
					if (value == JOptionPane.YES_OPTION) {
						String fname = rEmpFname.getText();
						String lname = rEmpLname.getText();
						if (!fname.equals("m") && !lname.equals("t")) {
							boolean check = loginTechnician.deleteManager(managers, fname, lname);
							if (check == true) {
								JOptionPane.showMessageDialog(null, "Manager Deleted!", "Done", JOptionPane.INFORMATION_MESSAGE);
								dispose();
								TechnicianMain reload = new TechnicianMain();
								reload.setVisible(true);
							} else {
								JOptionPane.showMessageDialog(null, "Couldn't delete manager! Try-Again", "Error", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Cannot delete this account!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if (pos.equalsIgnoreCase("technician")) {
					//technician
					int value = JOptionPane.showConfirmDialog(getParent(), "Are you sure to remove this technician?", "Confirmation", JOptionPane.YES_NO_OPTION);
					if (value == JOptionPane.YES_OPTION) {
						String fname = rEmpFname.getText();
						String lname = rEmpLname.getText();
						if (!fname.equals("t") && !lname.equals("t")) {
							boolean check = loginTechnician.deleteTechnician(technicians, fname, lname);

							if (check == true) {
								JOptionPane.showMessageDialog(null, "Technician Deleted!", "Done", JOptionPane.INFORMATION_MESSAGE);
								dispose();
								TechnicianMain reload = new TechnicianMain();
								reload.setVisible(true);
							} else {
								JOptionPane.showMessageDialog(null, "Couldn't delete technician! Try-Again", "Error", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Cannot delete this account!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if (pos.length() == 0) {
					JOptionPane.showMessageDialog(null, "No employee selected!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Error - Try Again!", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		removeEmpBtn.setBounds(211, 121, 158, 29);
		removeEmpPanel.add(removeEmpBtn);

		rEmpPosition = new JTextField();
		rEmpPosition.setDisabledTextColor(Color.WHITE);
		rEmpPosition.setHorizontalAlignment(SwingConstants.CENTER);
		rEmpPosition.setForeground(Color.WHITE);;
		rEmpPosition.setBackground(Color.DARK_GRAY);
		rEmpPosition.setEditable(false);
		rEmpPosition.setBounds(210, 65, 159, 26);
		removeEmpPanel.add(rEmpPosition);
		rEmpPosition.setColumns(10);

		JLabel positionRemobeLbl = new JLabel("Position:");
		positionRemobeLbl.setForeground(textC);
		positionRemobeLbl.setBounds(210, 48, 61, 16);
		removeEmpPanel.add(positionRemobeLbl);

		email = new JLabel("");
		email.setHorizontalAlignment(SwingConstants.CENTER);
		email.setBounds(6, 165, 363, 16);
		removeEmpPanel.add(email);

	}

	//method to get a Technician from arrayList
	public Technician getTechnician(ArrayList<Technician> technicians, String empFirstName, String empLastName) {
		Technician technician = null;
		for (Technician tech: technicians) {
			if (tech.getEmpFirstName().equalsIgnoreCase(empFirstName) && tech.getEmpLastName().equalsIgnoreCase(empLastName)) {
				technician = tech;
			}
		}
		return technician;
	}
}