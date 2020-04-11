/*
	***THIS IS A CAR RENTAL PROJECT***
	
	Team members:
		Davisen Permall - Team Leader
		Keshav Caleechurn
		Yanick Levy
		Adhit Boodhun
	
	EXTERNAL JAR FILES:
		FLAT LAF L&F: https://www.formdev.com/flatlaf#download
		JCalender: https://toedter.com/jcalendar/
		Joda Time: https://www.joda.org/joda-time/
		Mysql: https://dev.mysql.com/downloads/connector/j/
	

*/
package gui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.IntelliJTheme;

import database.DbConnect;
import main.LoginSession;
import technicianGui.TechnicianInterfaces;

public class LoginGui extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5768505056277174900L;
	
	private Container container = getContentPane();
	private JLabel EmailLabel = new JLabel("Email:");
	private JLabel passwordLabel = new JLabel("Password:");
	private JTextField emailTextField = new JTextField();
	private JPasswordField pwdField = new JPasswordField();
	private JButton loginButton = new JButton("LOG IN");
	private JCheckBox showPassword = new JCheckBox("Show Password");
	private ImageIcon logoImg;
	private JLabel labelLogo;

	DbConnect connect = new DbConnect();
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		try {
			//look and feel FlatIntelliJLaf with theme gruvbox_theme.theme
			//https://www.formdev.com/flatlaf#download
			
			IntelliJTheme.install(LoginGui.class.getResourceAsStream(
				"gruvbox_theme.theme.json"));
			
			//adjustments in the look and feel
			UIManager.put("Component.focusWidth", 0);
			UIManager.put("Button.arc", 10);
			UIManager.put("TextField.border", 0);
			UIManager.put("Component.arrowType", "chevron");

			LoginGui login = new LoginGui();
			login.setVisible(true);
			
		} catch (Exception ex) {
			System.err.println("Failed to initialize LaF");
		}

	}

	//constructor of the login page
	public LoginGui() {
		super("Log In");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(10, 10, 400, 450); //interface dimension
		this.setResizable(false); //prevent the screen from being resized
		this.setLocationRelativeTo(null); //make screen appear in the middle of the screen

		//call functions
		setLayoutManager();
		setDesignAndPosition();
		addComponentsToContainer();
		addActionEvent();
		JRootPane rootPane = SwingUtilities.getRootPane(loginButton);
		rootPane.setDefaultButton(loginButton);
	}

	private void setLayoutManager() {
		container.setLayout(null);
		container.setBackground(Color.BLACK);

		setIconImage(new ImageIcon(LoginGui.class.getResource("icon.png")).getImage());
		logoImg = new ImageIcon(getClass().getResource("logo.png"));
		labelLogo = new JLabel(logoImg);
		container.add(labelLogo);
	}

	private void setDesignAndPosition() {
		//setBounds(x,y,width,height)
		labelLogo.setBounds(50, 10, 300, 150);

		//email label
		EmailLabel.setBounds(50, 150, 100, 40);
		EmailLabel.setFont(new Font("Tehoma", Font.PLAIN, 17));
		EmailLabel.setForeground(Color.WHITE); //here

		//user email input
		emailTextField.setBounds(50, 180, 300, 40);
		//emailTextField.setForeground(Color.BLACK);
		emailTextField.setBackground(null);
		emailTextField.setFont(new Font("Tehoma", Font.PLAIN, 17));
		emailTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
		emailTextField.setForeground(Color.WHITE); //here
		//password label
		passwordLabel.setBounds(50, 230, 100, 40);
		passwordLabel.setFont(new Font("Tehoma", Font.PLAIN, 17));
		passwordLabel.setForeground(Color.WHITE); //here

		//user password input
		pwdField.setBounds(50, 260, 300, 40);
		pwdField.setBackground(null);
		pwdField.setFont(new Font("Tehoma", Font.PLAIN, 17));
		pwdField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
		pwdField.setForeground(Color.WHITE); //here

		showPassword.setBounds(50, 300, 300, 30);
		showPassword.setForeground(Color.WHITE); //here
		// login btn
		loginButton.setBounds(50, 350, 300, 40);
		loginButton.setForeground(Color.WHITE); //here
		loginButton.setBackground(Color.BLACK); //here
	}

	private void addComponentsToContainer() {
		container.add(EmailLabel);
		container.add(passwordLabel);
		container.add(emailTextField);
		container.add(pwdField);
		container.add(loginButton);
		container.add(showPassword);
	}

	private void addActionEvent() {
		loginButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	// override the action listener class

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == loginButton) {
			String userText = emailTextField.getText();
			String passwordText = pwdField.getText();
			if (userText.equalsIgnoreCase("admin") && passwordText.equalsIgnoreCase("admin")) {
				TechnicianInterfaces technicianInterface = new TechnicianInterfaces();
				technicianInterface.setVisible(true);
				this.dispose();
			} else {
				try {
					if (connect.isLogin(userText, passwordText, this)) {
						// display interfaces according to the type of employee
						if (LoginSession.usertype.equals("technician")) {
							//technician interface
							TechnicianInterfaces technicianInterface = new TechnicianInterfaces();
							technicianInterface.setVisible(true);
							this.dispose();
						} else if (LoginSession.usertype.equals("clerk")) {
							//clerk interface
							ClerkMainGui mainInterfaceClerk = new ClerkMainGui(null);
							mainInterfaceClerk.setVisible(true);
							this.dispose();
						} else if (LoginSession.usertype.equals("manager")) {
							// manager interface
							ManagerGui managerGui = new ManagerGui(null);
							managerGui.setVisible(true);
							this.dispose();
						} else {
							JOptionPane.showMessageDialog(this, "Account not registered!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(this, "Invalid user OR Password", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception ex) {
					System.out.println("ERROR Check: " + ex);
					JOptionPane.showMessageDialog(this, "No Database Connection", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		//accounts for tick on box
		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				pwdField.setEchoChar((char) 0);
			} else {
				pwdField.setEchoChar('*');
			}
		}

	}
}