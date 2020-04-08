package technicianGui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import database.DbConnect;
import employee.Clerk;
import employee.Manager;
import employee.Technician;
import gui.LoginGui;
import gui.SettingGui;
import main.LoginSession;
import main.LogoutSession;

public class TechnicianInterfaces extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected Technician loginTechnician;
	protected ArrayList<Technician> technicians;
	protected ArrayList<Manager> managers;
	protected ArrayList<Clerk> clerks;
	static TechnicianInterfaces technicianFrame = new TechnicianInterfaces();
						
	public TechnicianInterfaces() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				LoginGui login = null;
				login = new LoginGui();
				login.setVisible(true);
			}
		});
		
		//CONNECT TO DATABASE 
		DbConnect connect = new DbConnect();
		//BUILD ARRAYLIST WITH DB DATA
		technicians = connect.getTechnicians();
		managers = connect.getManagers();
		clerks = connect.getClerks();
				
		setIconImage(new ImageIcon(LoginGui.class.getResource("icon.png")).getImage());
		setType(Type.POPUP);
		setResizable(false);
		setTitle("Technician Interface");
		setBackground(new Color(0, 51, 51));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.windowText);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel technicianLabel = new JLabel("Technician");
		technicianLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		technicianLabel.setForeground(Color.WHITE);
		
		JButton technicianDel = new JButton("Remove");
		technicianDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteTechnicianInterface deleteModel = new DeleteTechnicianInterface(technicianFrame);
				deleteModel.setVisible(true);
			}
		});
		technicianDel.setToolTipText("Remove an existing technician");
		technicianDel.setFont(new Font("SansSerif", Font.BOLD, 12));
		technicianDel.setBackground(Color.RED);
		technicianDel.setForeground(Color.WHITE);
		
		JButton technicianNew = new JButton("Add");
		technicianNew.setToolTipText("Add a new technician");
		technicianNew.setFont(new Font("SansSerif", Font.BOLD, 12));
		technicianNew.setBackground(Color.ORANGE);
		technicianNew.setForeground(Color.BLACK);
		technicianNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddTechnicianInterface addModel = new AddTechnicianInterface(technicianFrame);
				addModel.setVisible(true);
			}
		});
		
		JLabel managerLabel = new JLabel("Manager");
		managerLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		managerLabel.setForeground(Color.WHITE);
		
		JButton managerNew = new JButton("Add");
		managerNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddManagerInterface addModel = new AddManagerInterface(technicianFrame);
				addModel.setVisible(true);
			}
		});
		managerNew.setToolTipText("Add a new manager");
		managerNew.setFont(new Font("SansSerif", Font.BOLD, 12));
		managerNew.setBackground(Color.ORANGE);
		
		JButton ManagerRemove = new JButton("Remove");
		ManagerRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteManagerInterface deleteModel1 = new DeleteManagerInterface(technicianFrame);
				deleteModel1.setVisible(true);
			}
		});
		ManagerRemove.setToolTipText("Remove an existing manager\r\n");
		ManagerRemove.setFont(new Font("SansSerif", Font.BOLD, 12));
		ManagerRemove.setForeground(Color.WHITE);
		ManagerRemove.setBackground(Color.RED);
		
		JLabel clerkLabel = new JLabel("Clerk");
		clerkLabel.setForeground(Color.WHITE);
		clerkLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		JToggleButton clerkAdd = new JToggleButton("Add");
		clerkAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddClerkInterface addModel2 = new AddClerkInterface(technicianFrame);
				addModel2.setVisible(true);
			}
		});
		clerkAdd.setToolTipText("Add a new clerk");
		clerkAdd.setFont(new Font("SansSerif", Font.BOLD, 12));
		clerkAdd.setBackground(Color.ORANGE);
		
		JButton clerkDel = new JButton("Remove");
		clerkDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteClerkInterface deleteModel2 = new DeleteClerkInterface(technicianFrame);
				deleteModel2.setVisible(true);
			}
		});
		clerkDel.setToolTipText("Remove an existing clerk\r\n");
		clerkDel.setFont(new Font("SansSerif", Font.BOLD, 12));
		clerkDel.setForeground(Color.WHITE);
		clerkDel.setBackground(Color.RED);
		
		
		
		
		loginTechnician = getTechnician(technicians,"admin","admin");
		if(LoginSession.isLoggedIn) {
			loginTechnician = getTechnician(technicians, LoginSession.userFirstName, LoginSession.username);
		}
		JLabel TitleLabel = new JLabel("Welcome back "+ loginTechnician.getFullName());
		
		TitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
		TitleLabel.setForeground(Color.WHITE);
		
		JLabel operationsLabel = new JLabel("You can perform these operations!");
		operationsLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
		operationsLabel.setForeground(Color.WHITE);
		
		JButton LogOutButton = new JButton("Log Out");
		LogOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int value=JOptionPane.showConfirmDialog(getParent(), "Are you sure to Log Out?", "Confirmation", JOptionPane.YES_NO_OPTION);
				if(value==JOptionPane.YES_OPTION) {
				LoginGui login = new LoginGui();
				LogoutSession.logoutH(login);
				dispose();
				}
			}
		});
		LogOutButton.setBackground(new Color(0, 153, 255));
		LogOutButton.setForeground(new Color(0, 0, 0));
		LogOutButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		
		JButton settingButton = new JButton("New button");
		settingButton.setBackground(Color.DARK_GRAY);
		settingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingGui gui = new SettingGui();
				gui.setVisible(true);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(71)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(technicianLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(clerkLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(managerLabel))
					.addGap(24)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(technicianNew, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(managerNew, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addComponent(clerkAdd, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(clerkDel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(ManagerRemove, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
						.addComponent(technicianDel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(71))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(362, Short.MAX_VALUE)
					.addComponent(LogOutButton))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(136, Short.MAX_VALUE)
					.addComponent(operationsLabel, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
					.addGap(56))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(146)
					.addComponent(TitleLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
					.addComponent(settingButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(TitleLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(settingButton))
					.addGap(18)
					.addComponent(operationsLabel)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(technicianLabel)
						.addComponent(technicianNew)
						.addComponent(technicianDel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(managerLabel)
						.addComponent(ManagerRemove)
						.addComponent(managerNew))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(clerkLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
							.addComponent(LogOutButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(clerkAdd)
								.addComponent(clerkDel))
							.addContainerGap())))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	//method to get a Technician from arrayList
	public Technician getTechnician(ArrayList<Technician> technicians, String empFirstName, String empLastName) {
		Technician technician = null;
		for(Technician tech:technicians) {
			if(tech.getEmpFirstName().equalsIgnoreCase(empFirstName) && tech.getEmpLastName().equalsIgnoreCase(empLastName)) {
				technician = tech;
			}
		}
		return technician;
	}
}
