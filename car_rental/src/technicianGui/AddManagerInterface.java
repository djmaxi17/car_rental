package technicianGui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import database.DbConnect;
import employee.Manager;
import employee.Technician;
import gui.LoginGui;

public class AddManagerInterface extends JDialog  {
	private JPanel contentPane;
	
	public AddManagerInterface(TechnicianInterfaces techFrame) {
		super(techFrame, "Add a new Manager",true); 
		setIconImage(new ImageIcon(LoginGui.class.getResource("icon.png")).getImage());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 335, 223);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setForeground(new Color(255, 255, 255));
		firstNameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		
		firstNameField = new JTextField();
		firstNameField.setColumns(10);
		
		JLabel defaultLabel = new JLabel("Enter the following details for the new manager:");
		defaultLabel.setForeground(Color.ORANGE);
		defaultLabel.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 13));
		
		lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setForeground(new Color(255, 255, 255));
		lastNameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		
		lastNameField = new JTextField();
		lastNameField.setColumns(10);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(firstNameField.getText().equalsIgnoreCase("") && lastNameField.getText().equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, "Please don't leave any field blank!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(firstNameField.getText().equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, "Please don't leave the First Name field blank!","Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(lastNameField.getText().equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, "Please don't leave the Last Name field blank!","Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
						
						int value=JOptionPane.showConfirmDialog(getParent(), "Are you sure to add this new manager?", "Confirmation", JOptionPane.YES_NO_OPTION);
						if(value==JOptionPane.YES_OPTION) {
						
						String fname = firstNameField.getText();
						String lname = lastNameField.getText();
						DbConnect connect = new DbConnect();
						int check= connect.getEmployeeId(fname,lname,"manager");
						if(check==0) {
							Technician currentTechnician = techFrame.loginTechnician;	
							try {
						Manager newManager = currentTechnician.createManager(techFrame.managers, fname, lname,techFrame.loginTechnician.getEmpId());
						if(newManager!=null) {
						JOptionPane.showMessageDialog(null,"Name: "+ newManager.getFullName() + "\nEmail: " + newManager.getEmpEmail() +
								"\nPassword: "+newManager.getEmpPassword(),"New Manager Details", JOptionPane.PLAIN_MESSAGE);
						firstNameField.setText("");
						lastNameField.setText("");
						dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "Error in adding new manager! Try Again","Error",JOptionPane.ERROR_MESSAGE);
							dispose();
						}
							} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Manager with same name already exists!","Error",JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(66)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lastNameLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(firstNameLabel, Alignment.LEADING))
					.addGap(24)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lastNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(firstNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(72))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(125)
					.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(149, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(defaultLabel, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
					.addGap(14))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(defaultLabel)
					.addGap(22)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(firstNameLabel)
						.addComponent(firstNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lastNameLabel)
						.addComponent(lastNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(4, Short.MAX_VALUE))
		);
		JRootPane rootPane = SwingUtilities.getRootPane(addButton); 
		rootPane.setDefaultButton(addButton);
		contentPane.setLayout(gl_contentPane);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField firstNameField;
	private JLabel lastNameLabel;
	private JTextField lastNameField;

}
