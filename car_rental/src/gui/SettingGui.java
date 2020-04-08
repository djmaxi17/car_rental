package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import database.DbConnect;

public class SettingGui extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4069629493029939607L;
	private final JPanel contentPanel = new JPanel();
	private JTextField emailTF;
	private JPasswordField cpTF;
	private JPasswordField npTF;
	private JButton btnNewButton;
	DbConnect connect = new DbConnect();

	/**
	 * Create the dialog.
	 */
	public SettingGui() {
		this.setTitle("Change Password");
		this.setModal(true);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\icon.png"));
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		
		
		
		JLabel titleLabel = new JLabel("Change Your Password");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		titleLabel.setBounds(123, 20, 213, 22);
		contentPanel.add(titleLabel);
		
		JLabel subTitleLabel = new JLabel("You are required to do so every month");
		subTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		subTitleLabel.setBounds(128, 41, 244, 13);
		contentPanel.add(subTitleLabel);
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		emailLabel.setBounds(74, 90, 45, 13);
		contentPanel.add(emailLabel);
		
		JLabel cpLabel = new JLabel("Current Password:");
		cpLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cpLabel.setBounds(74, 126, 126, 13);
		contentPanel.add(cpLabel);
		
		JLabel npLabel = new JLabel("New Password:");
		npLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		npLabel.setBounds(74, 163, 112, 13);
		contentPanel.add(npLabel);
		
		btnNewButton = new JButton("Change Password");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				String email = emailTF.getText();
				String cp = cpTF.getText();
				String np = npTF.getText();
				
				if(email.length()==0) {
					JOptionPane.showMessageDialog(null, "Email field is blank!","ERROR",JOptionPane.ERROR_MESSAGE);
					emailTF.requestFocus();
				}
				
				else if(cp.length()==0) {
					JOptionPane.showMessageDialog(null, "Current Password field is blank!","ERROR",JOptionPane.ERROR_MESSAGE);
					cpTF.requestFocus();
				}
				
				else if(np.length()==0) {
					JOptionPane.showMessageDialog(null, "New Password field is blank!","ERROR",JOptionPane.ERROR_MESSAGE);
					npTF.requestFocus();
				}
				
				else if(isValidEmailAddress(email)==false) {
					JOptionPane.showMessageDialog(null, "Please enter a valid email!","ERROR",JOptionPane.ERROR_MESSAGE);
					emailTF.requestFocus();
				}
				else {
					int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to change your password!","Confirmation",JOptionPane.YES_NO_OPTION);
					if(confirm==0) {
						boolean response = connect.changePassword(email, cp, np);
						if(response) {
						dispose();
						}
					}
				}
			}
		});
		btnNewButton.setBounds(146, 209, 153, 28);
		contentPanel.add(btnNewButton);
		JRootPane rootPane = SwingUtilities.getRootPane(btnNewButton); 
		rootPane.setDefaultButton(btnNewButton);
		emailTF = new JTextField();
		emailTF.setBounds(128, 83, 244, 28);
		contentPanel.add(emailTF);
		emailTF.setColumns(10);
		
		cpTF = new JPasswordField();
		cpTF.setBounds(197, 119, 175, 28);
		contentPanel.add(cpTF);
		cpTF.setColumns(10);
		
		npTF = new JPasswordField();
		npTF.setBounds(197, 156, 175, 28);
		contentPanel.add(npTF);
		npTF.setColumns(10);
	}
	
	public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
}
