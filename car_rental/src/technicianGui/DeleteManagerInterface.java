package technicianGui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;

import database.DbConnect;

public class DeleteManagerInterface extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JLabel resultLabel;
	private JButton removeButton;
	private JLabel IdLabel;
	
	 //Create the dialog.
	public DeleteManagerInterface(TechnicianInterfaces techFrame) {
		super(techFrame, "Remove a Manager",true);
		getContentPane().setBackground(Color.BLACK);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\icon.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 269);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JLabel defaultLabel = new JLabel("Search the manager you want to delete");
		defaultLabel.setForeground(Color.RED);
		defaultLabel.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 14));
		
		firstNameField = new JTextField();
		firstNameField.setColumns(10);
		
		lastNameField = new JTextField();
		lastNameField.setColumns(10);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {

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
						
						String fname = firstNameField.getText();
						String lname = lastNameField.getText();
						DbConnect connect = new DbConnect();
						int check= connect.getEmployeeId(fname,lname,"manager");
						if(check!=0) {
							IdLabel.setText("ID: "+ check);
							resultLabel.setText("Manager Exist");
							resultLabel.setForeground(Color.ORANGE);
							removeButton.setVisible(true);
						}
						else {
							IdLabel.setText("");
							resultLabel.setText("Manager doesn't found!");
							resultLabel.setForeground(Color.RED);
							removeButton.setVisible(false);
						}
			}
			}
		});
		searchButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		firstNameLabel.setForeground(Color.WHITE);
		
		JLabel lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		lastNameLabel.setForeground(Color.WHITE);
		
		resultLabel = new JLabel("");
		resultLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		resultLabel.setForeground(Color.RED);
		
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int value=JOptionPane.showConfirmDialog(getParent(), "Are you sure to remove this manager?", "Confirmation", JOptionPane.YES_NO_OPTION);
				if(value==JOptionPane.YES_OPTION) {
					String fname = firstNameField.getText();
					String lname = lastNameField.getText();
					boolean check =techFrame.loginTechnician.deleteManager(techFrame.managers, fname, lname);
					if(check==true) {
					JOptionPane.showMessageDialog(null, "Manager Deleted!","Done", JOptionPane.INFORMATION_MESSAGE);
					dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "Couldn't delete manager! Try-Again","Error",JOptionPane.ERROR_MESSAGE);
						dispose();
					}
				}

			}
		});
		removeButton.setBackground(Color.RED);
		removeButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		removeButton.setForeground(Color.WHITE);
		removeButton.setVisible(false);
		
		IdLabel = new JLabel("");
		IdLabel.setBackground(Color.ORANGE);
		IdLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		IdLabel.setForeground(Color.RED);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(61)
							.addComponent(defaultLabel, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(92)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(IdLabel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(resultLabel, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(removeButton)
									.addGap(12))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lastNameLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
										.addComponent(firstNameLabel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(firstNameField)
										.addComponent(lastNameField, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
										.addComponent(searchButton, Alignment.TRAILING))))
							.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)))
					.addGap(147))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addComponent(defaultLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(firstNameLabel)
						.addComponent(firstNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lastNameLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(lastNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(IdLabel)
						.addComponent(searchButton))
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(resultLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(removeButton))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		JRootPane rootPane = SwingUtilities.getRootPane(searchButton); 
		rootPane.setDefaultButton(searchButton);
		getContentPane().setLayout(groupLayout);
	}
}
