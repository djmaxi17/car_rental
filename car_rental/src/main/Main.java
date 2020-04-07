package main;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.ArrayList;

import car.Car;
import customer.Customer;
import employee.Clerk;
import employee.Manager;
import employee.Technician;
import rentRegistration.RentRegistration;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws ParseException, NoSuchAlgorithmException, InvalidKeySpecException {
		//FIRST TECHNICIAN
		Technician admin = new Technician("admin","admin");
		//testing comment 2
		
		ArrayList<Clerk> clerks = new ArrayList<Clerk>();
		ArrayList<Manager> managers = new ArrayList<Manager>();
		ArrayList<Car> cars = new ArrayList<Car>();
		ArrayList<RentRegistration> rents = new ArrayList<RentRegistration>();
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		//Technician creates a clerk davisen and manager yanick
		Clerk davisen = admin.createClerk(clerks,"Davisen", "Permall", 1);	
		Manager yanick = admin.createManager(managers,"Yanick", "Levy", 1);
		
		
		//Manager yanick adds a new car porshe
//		Car porsche =  yanick.addCar(cars,"abcd2020","Porsche","Panamera","wagen",2020,3800,4,"automatic/manual","hybrid",380,1500);
//		System.out.println(porsche);
		//Clerk davisen registers a new customer tom
		Customer tom = davisen.registerCustomer(customers,"Tom", "Sawyer", "t.cestlamerique@mail.com", "12-01-2018" , "L69", "Royal Road", 57696969, "Male");
		System.out.println(tom.toString());
		
		
//		//Clerk davisen registers rent
//		RentRegistration rent = davisen.registerRent(rents,tom, porsche, "05-03-2020", "10-03-2020");
//		RentRegistration rent1 = davisen.registerRent(rents,tom, porsche, "04-04-2020", "10-04-2020");
//		RentRegistration rent2 = davisen.registerRent(rents,tom, porsche, "05-05-2020", "10-05-2020");
//		RentRegistration rent3 = davisen.registerRent(rents,tom, porsche, "05-06-2020", "10-06-2020");
//		
//		
//		
//		System.out.println("****************Rent Before Returned****************");
//		System.out.println(davisen.registerRent(rents,tom, porsche, "05-11-2020", "10-11-2020"));
		
		
//		Customer tom returns car and Clerk davisen records it
		RentRegistration ccrent = davisen.carReturned(rents,"Tom", "Sawyer","05-11-2020","15-11-2020");
		
		System.out.println("****************Rent After Returned****************");
		//testing using the current car returned variable
		System.out.println(ccrent.toString());
		
		//testing by looping the arraylist
		for(RentRegistration rentsearch:rents) {
			
			if (rentsearch.getCustomer().getCustFirstName().equalsIgnoreCase("Tom") && (rentsearch.getCustomer().getCustLastName().equalsIgnoreCase("Sawyer") &&rentsearch.getDateRented().equalsIgnoreCase("05-11-2020"))) {
				System.out.println(rentsearch);
			}	
		}
		
	}

}