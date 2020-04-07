package gui;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import database.DbConnect;
import car.Car;


public class DeleteCarGui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//attributes of deleteCar
	private JTextField carPlate;
	private JButton deleteCar;
	
	//defining constructor
	public DeleteCarGui() {
		//title
		super("Delete Car");
		getContentPane().setLayout(null);
		this.setSize(500,500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\icon.png"));

		//text field
		carPlate = new JTextField(14);
		JLabel carPlateLabel = new JLabel("Enter Car Plate No ");
		carPlate.setBounds(200,140,150,30);
		carPlateLabel.setFont(new Font("Sans Serif", Font.BOLD,14));
		carPlateLabel.setForeground(Color.WHITE);
		carPlateLabel.setBounds(50,140,150,30);
		
		//delete button
		deleteCar = new JButton("Delete Car Record");
		deleteCar.setBounds(145,400,200,30);
		
		HandlerClass handler = new HandlerClass();
		
		deleteCar.addActionListener(handler);
		
		getContentPane().add(carPlate);
		getContentPane().add(carPlateLabel);
		getContentPane().add(deleteCar);
		JRootPane rootPane = SwingUtilities.getRootPane(deleteCar); 
		rootPane.setDefaultButton(deleteCar);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerGui gui = new ManagerGui();
				gui.setVisible(true);
				dispose();
			}
		});
		backButton.setBounds(12, 13, 86, 28);
		getContentPane().add(backButton);
	}
	
	private class HandlerClass implements ActionListener{
		//listens for event to occur
		public void actionPerformed(ActionEvent event) {
			
			//calls delete query if delete button is pressed
			if (event.getActionCommand() == "Delete Car Record") {
				
				if (carPlate.getText().length() == 0) {
					
					JOptionPane.showMessageDialog(null, "Car Plate No is empty !");
					
				}else {
					//capture value from text field
					String carPlateNum = carPlate.getText();
					int confirm = JOptionPane.showConfirmDialog(null, String.format("Are you sure you want to delete %s", carPlateNum));
				
					if (confirm == 0) {
						
						//instantiates new database connection
						DbConnect connect = new DbConnect();
						
						//get car with the plate number entered
						ArrayList<Car> cars = connect.getCar(carPlateNum, -1);
						
						//if car exists in the database, delete it
						if (!cars.isEmpty()) {
							
							if (connect.deleteCar(carPlateNum)) {
								
								JOptionPane.showMessageDialog(null,String.format("%s has been deleted", carPlateNum));
								
								dispose();
								ManagerGui gui = new ManagerGui();
								gui.setVisible(true);
								
							}else {
								
								JOptionPane.showMessageDialog(null, String.format("Error in deleting %s", carPlateNum));
							}
							
						}else {
							
							JOptionPane.showMessageDialog(null, String.format("%s not found in database", carPlateNum));
						}
					
					}else if (confirm == 2) {
						
						carPlate.setText(null);
					}
				}	
			}
		}
	}
}
