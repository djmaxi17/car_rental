package files;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class LogFile {
	//log files
	private String login = "LogIn.txt";
	private String carRegistration = "CarRegistration.txt";
	private String rentRegistration = "RentRegistration.txt";
	private String customerRegistration = "CustomerRegistration.txt";
	private String settleRent = "RentSettled.txt";
	private String technician = "TechnicianLog.txt";
	
	
	//date to string
	DateFormat dateformat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	Date today = Calendar.getInstance().getTime();
	String logDate = dateformat.format(today);
	
	//method to record login in login log file
	public void recordLogIn(int userId, String username, String userType) {
		
		try {
			//set output file
			PrintWriter pw = new PrintWriter(new FileOutputStream(login,true));
			//formatter
			Formatter output = new Formatter(pw);
			//output to file
			output.format("%s with id %d logged in as %s at %s \t", username, userId, userType, logDate);
			output.close();
			
		}catch (FileNotFoundException e) {
			
			System.out.println(login + " Not Found");
			
		}catch(SecurityException se) {
			
			System.out.println("No Permission !");
		}
	}
	
	//method to record car registration in carRegistration log file
	public void recordCar(int userId, String username, String userType, String carPlateNo) {
		
		try {
			
			//set output file
			PrintWriter pw = new PrintWriter(new FileOutputStream(carRegistration,true));
			//formatter
			Formatter output = new Formatter(pw);
			//output to file
			output.format("%s %s with id %d registered Car %s at %s \t", userType, username, userId, carPlateNo, logDate);
			output.close();
			
			
		}catch(FileNotFoundException e) {
			
			System.out.println(carRegistration + " Not Found");
			
		}catch(SecurityException se) {
			
			System.out.println("No Permission !");
		}
	}
	
	//method to record customer registration in customerRegistration log file
	public void recordCustomer(int userId, String username, String userType, String customer) {
		
		try {
			
			//set output file
			PrintWriter pw = new PrintWriter(new FileOutputStream(customerRegistration,true));
			//formatter
			Formatter output = new Formatter(pw);
			//output to file
			output.format("%s %s with id %d registered Customer %s at %s \t", userType, username, userId, customer, logDate);
			output.close();
			
			
		}catch(FileNotFoundException e) {
			
			System.out.println(customerRegistration + " Not Found");
			
		}catch(SecurityException se) {
			
			System.out.println("No Permission !");
		}
	}
	
	//method to record rent registration in rentRegistration log file
	public void recordRent(int userId, String username, String userType, String customer, String car) {
		
		try {
			
			//set output file
			PrintWriter pw = new PrintWriter(new FileOutputStream(rentRegistration,true));
			//formatter
			Formatter output = new Formatter(pw);
			//output to file
			output.format("%s %s with id %d has registered rent of Car %s to Customer %s at %s \t", userType, username, userId, car, customer, logDate);
			output.close();
			
			
		}catch(FileNotFoundException e) {
			
			System.out.println(rentRegistration + " Not Found");
			
		}catch(SecurityException se) {
			
			System.out.println("No Permission !");
		}
	}
	
	//method to record settle rent in settleRent log file
	public void settleRent(int userId, String username, String userType, String customer, String car) {
		
		try {
			
			//set output file
			PrintWriter pw = new PrintWriter(new FileOutputStream(settleRent,true));
			//formatter
			Formatter output = new Formatter(pw);
			//output to file
			output.format("%s %s with id %d has settled rent of Car %s to Customer %s at %s \t", userType, username, userId, car, customer, logDate);
			output.close();
			
			
		}catch(FileNotFoundException e) {
			
			System.out.println(settleRent + " Not Found");
			
		}catch(SecurityException se) {
			
			System.out.println("No Permission !");
		}
	}
	
	//method to record technician activity in technicianLog log file
	public void recordTechnician(int userId, String username, String userType, String operation, String created) {
			
		try {
				
			//set output file
			PrintWriter pw = new PrintWriter(new FileOutputStream(technician,true));
			//formatter
			Formatter output = new Formatter(pw);
			//output to file
			output.format("%s %s with id %d has %s %s at %s \t", userType, username, userId, operation, created, logDate);
			output.close();
				
				
		}catch(FileNotFoundException e) {
				
			System.out.println(technician + " Not Found");
				
		}catch(SecurityException se) {
				
			System.out.println("No Permission !");
		}
	}
}
