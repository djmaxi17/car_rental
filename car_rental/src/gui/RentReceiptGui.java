package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

import rentRegistration.RentRegistration;

public class RentReceiptGui extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5350813534933108725L;
	/**
	 * Launch the application.
	 */
	JTextPane receiptPane;
//	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel(new NimbusLookAndFeel());
//			RentReceiptGui dialog = new RentReceiptGui(null,null);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public RentReceiptGui(RentRegistrationGui previousFrame, RentRegistration rentReg) {
		super(previousFrame,"Rent Receipt",true);
		setBackground(Color.DARK_GRAY);
		setBounds(100, 100, 322, 672);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(new ImageIcon(LoginGui.class.getResource("icon.png")).getImage());
		JPanel contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		Color textCB = new Color(248,250,252);
		Color buttonCol = new Color(79,99,116);
		JButton printBtn = new JButton("Print Receipt");
		printBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
		printBtn.setBackground(buttonCol);
		printBtn.setForeground(textCB);
		printBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			    
				try {
					
					receiptPane.print();
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		printBtn.setBounds(96, 598, 119, 28);
		panel.add(printBtn);
		
		receiptPane = new JTextPane();
		receiptPane.setContentType("text/html");
		receiptPane.setEditable(false);
		
		
		String custName = rentReg.getCustomer().getCustFirstName()+"\n                 "+rentReg.getCustomer().getCustLastName();
		String rentCar = rentReg.getCar().getCarMake()+"\n              "+rentReg.getCar().getCarModel();
		double carRate = rentReg.getCar().getCarRate();
		String dateRented = rentReg.getDateRented().toString();
		int numDays = rentReg.getNumOfDaysDefault();
		String dateDue = rentReg.getDateDueString();
		double total = rentReg.getRentCost();
		
		String textData = "<html><body><pre>\r\n          <b>MAXI AUTOMOBILE</b>\r\n" + 
						"\r\n  * * * * * * * * * * * * * * * *  \r\n" + 
						"\r\n         <b>CAR RENTAL RECEIPT</b>   \r\n" + 
						"\r\n  * * * * * * * * * * * * * * * *  \r\n" +
						"\r\n  Customer Name: "+custName+"\r\n" +
						"\r\n  Rented Car: "+rentCar+"\r\n  @ Rs "+carRate+"/Day\r\n" +
						"\r\n  Date Rented: "+dateRented+"\r\n" +
						"\r\n  Number of Days: "+numDays+"\r\n" +
						"\r\n  Date Due: "+dateDue+"\r\n" +
						"\r\n     <b>***Total = Rs"+total+"***</b>\r\n "+
						"  Thanks for choosing Maxi Auto\r" +
						"\r\n  - - - - - - - - - - - - - - - -  \r\n"+
						"\t  Contact Details:\r\n\t" +
						"Tel: +230 57969910\r\n\tFax: +230 70814603\r\n" +
						"   Email: mail@maxiautomobile.com\r\n</pre></body></html>";
						
		receiptPane.setText(textData);
		receiptPane.setFont(new Font("Consolas", Font.PLAIN, 14));
		receiptPane.setBorder(new LineBorder(Color.GRAY));
		receiptPane.setBounds(0, 0, 318, 590);
		panel.add(receiptPane);
	}
}
