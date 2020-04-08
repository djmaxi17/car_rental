package gui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

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
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import database.DbConnect;
import main.LoginSession;
import technicianGui.TechnicianInterfaces;

public class LoginGui extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container container = getContentPane();
	JLabel EmailLabel = new JLabel("Email:");
	JLabel passwordLabel = new JLabel("Password:");
	JTextField emailTextField = new JTextField();
	JPasswordField pwdField = new JPasswordField();
	JButton loginButton = new JButton("LOG IN");
	JCheckBox showPassword = new JCheckBox("Show Password");
	ImageIcon logoImg;
	JLabel labelLogo;
	
	
	public static void main(String[] args) throws ParseException {
		try {
			UIManager.setLookAndFeel( new NimbusLookAndFeel() );
//=			UIManager.setLookAndFeel( new SyntheticaDarkLookAndFeel() );
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//instantiate login gui only if connection has been established with database
//		if (connect.DbConnect()) {
//			
		LoginGui login = new LoginGui();
		login.setVisible(true);
		
//		}else if (!connect.DbConnect()) {
//			
//			JOptionPane.showMessageDialog(null, "Failed to connect to database !", "ERROR", JOptionPane.ERROR_MESSAGE);
//		}
	}
	
	//constructor of the login page
	public LoginGui() {
		super("Log In");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(10,10,400,450);	//interface dimension
		this.setResizable(false);	//prevent the screen from being resized
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
		labelLogo.setBounds(50, 10,300,150);
		
		//email label
		EmailLabel.setBounds(50,150,100,40);
		EmailLabel.setFont(new Font("Tehoma", Font.PLAIN, 17));
		EmailLabel.setForeground(Color.WHITE); //here
		
		//user email input
		emailTextField.setBounds(50,180,300,40);
		//emailTextField.setForeground(Color.BLACK);
		emailTextField.setBackground(null);
		emailTextField.setFont(new Font("Tehoma", Font.PLAIN, 17));
		emailTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
		emailTextField.setForeground(Color.WHITE); //here
		//password label
		passwordLabel.setBounds(50,230,100,40);
		passwordLabel.setFont(new Font("Tehoma", Font.PLAIN, 17));
		passwordLabel.setForeground(Color.WHITE); //here
		
		//user password input
		pwdField.setBounds(50,260,300,40);
		pwdField.setBackground(null);
		pwdField.setFont(new Font("Tehoma", Font.PLAIN, 17));
		pwdField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
		pwdField.setForeground(Color.WHITE); //here
		
		showPassword.setBounds(50,300,300,30);
		showPassword.setForeground(Color.WHITE); //here
		// login btn
		loginButton.setBounds(50,350,300,40);
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
	public void actionPerformed(ActionEvent e){

	if(e.getSource() == loginButton) {
		String userText = emailTextField.getText();
		String passwordText = pwdField.getText();
		if(userText.equalsIgnoreCase("admin") && passwordText.equalsIgnoreCase("admin")) {
			TechnicianInterfaces technicianInterface = new TechnicianInterfaces();
			technicianInterface.setVisible(true);
			this.dispose();
		}
		else {
		try {			
			if(DbConnect.isLogin(userText, passwordText, this)) {
				// display interfaces according to the type of employee
				if(LoginSession.usertype.equals("technician")) {
					//technician interface
					TechnicianInterfaces technicianInterface = new TechnicianInterfaces();
					technicianInterface.setVisible(true);
					this.dispose();
				}
				else if (LoginSession.usertype.equals("clerk")) {
					//clerk interface
					ClerkMainGui mainInterfaceClerk = new ClerkMainGui(null);
					mainInterfaceClerk.setVisible(true);
					this.dispose();
				}
				else if(LoginSession.usertype.equals("manager")) {
					// manager interface
					ManagerGui managerGui = new ManagerGui();
					managerGui.setVisible(true);
					this.dispose();
				}
				else {
					System.out.println("This account does not exist");
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "Invalid user OR Password");
			}
		}
		catch(Exception ex) {
			System.out.println("ERROR Check: "+ex);
		}
	}
	}
	if(e.getSource() == showPassword) {
		if(showPassword.isSelected()) {
			pwdField.setEchoChar((char)0);
		}
		else {
			pwdField.setEchoChar('*');
		}
	}

	}
}
