package employee;

import java.text.ParseException;
import java.util.ArrayList;

import car.Car;
import customer.Customer;
import rentRegistration.RentRegistration;

public interface Rentable {

	public Customer registerCustomer(ArrayList<Customer> customers, String custFirstName, String custLastName,
			String custEmail, String custDOB, String custLicenseNo, String custAddress, int custPhoneNumber,
			String custGender) throws ParseException;
	
	public Customer searchCustomer(ArrayList<Customer> customers, String custFirstName, String custLastName);
	
	public String viewCars(ArrayList<Car> cars);
	
	public RentRegistration registerRent(ArrayList<RentRegistration> rents, Customer customer, Car rentCar, String dateRented,
			int numDays) throws ParseException;
	
	public String viewRents(ArrayList<RentRegistration> rents);

	public RentRegistration carReturned(ArrayList<RentRegistration> rents, String custFirstName,
			String custLastName, String dateRented, String dateReturn) throws ParseException;
	
	
}
