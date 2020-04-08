package main;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DbWork {

	public static void main(String[] arg) throws NoSuchAlgorithmException, InvalidKeySpecException, ParseException {
//		DbConnect connect = new DbConnect();
//		
//		
//		ArrayList<Customer> customers = connect.BindTableCustomer(0, -1);
//		for(Customer c :customers ) {
//			System.out.println(c);
//		}
		
		
//		
//		ArrayList<Customer> customers = connect.BindTableCustomer(0, 1);
//		for(Customer c :customers ) {
//			System.out.println(c);
//		}
////		

//		
//		ArrayList<Customer> customers = connect.BindTableCustomer(4, -1);
//		for(Customer c :customers ) {
//			System.out.println(c);
//		}
//		
		
		
		
		
//		
//		ArrayList<Technician> technicians = connect.getTechnicians();
//		for(Technician technician: technicians) {
//			System.out.println(technician);
//		}
//		
//		ArrayList<Manager> managers = connect.getManagers();
//		for(Manager manager: managers) {
//			System.out.println(manager);
//		}
//		
//		ArrayList<Clerk> clerks = connect.getClerks();
//		for(Clerk clerk: clerks) {
//		System.out.println(clerk);
//		}
//		
//		
		
//		int num = connect.getEmployeeId("c", "t", "clerk");
//		System.out.println(num);
//		System.out.println(connect.employeePasswordCheck("m.t@maxiautomobile.com", "maxi8m"));
		
//		Car oneCar = connect.bindTable("235 JN 18", -1).get(0);
//		Customer oneCustomer = connect.BindTableCustomer(3, -1).get(0);
//		connect.insertRent(oneCar, oneCustomer, "31/03/2020", 20, 88);
//		
		
//		ArrayList<RentRegistration> rent = connect.BindTableRent(0, 0);
//		for(RentRegistration r :rent) {
//		System.out.println(r);
//		}
//	}
		
		   
		        java.util.Date uDate = new java.util.Date();
		        System.out.println("Time in java.util.Date is : " + uDate);
		        java.sql.Date sDate = convertUtilToSql(uDate);
		        System.out.println("Time in java.sql.Date is : " + sDate);
		        DateFormat df = new SimpleDateFormat("dd/MM/YYYY - hh:mm:ss");
		        System.out.println("Using a dateFormat date is : " + df.format(uDate));
	}
		 
		    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
		        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		        return sDate;
		    }
		
	
	
}
