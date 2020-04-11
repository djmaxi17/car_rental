package employee;

import java.text.ParseException;
import java.util.ArrayList;

import car.Car;
import customer.Customer;
import rentRegistration.RentRegistration;

public class Clerk extends Employee implements Rentable {

	public Clerk(String empFirstName, String empLastName) {
		super(empFirstName, empLastName);
	}

	public Clerk(int id, String firstName, String lastName, String email, String password) {
		super(id, firstName, lastName, email, password);
	}

	/*Customer Related*/
	//Clerk adds a customer -done
	@Override
	public Customer registerCustomer(ArrayList<Customer> customers, String custFirstName, String custLastName, String custEmail, String custDOB, String custLicenseNo, String custAddress, int custPhoneNumber, String custGender) throws ParseException {
		Customer customer = new Customer(custFirstName, custLastName, custEmail, custDOB, custLicenseNo, custAddress, custPhoneNumber, custGender);
		customers.add(customer);
		return customer;
	}

	//Clerk search a customer or select customer -done
	@Override
	public Customer searchCustomer(ArrayList<Customer> customers, String custFirstName, String custLastName) {
		for (Customer customer: customers) {
			if (customer.getCustFirstName().equalsIgnoreCase(custFirstName) && customer.getCustLastName().equalsIgnoreCase(custLastName)) {
				return customer;
			}
		}
		return null;
	}

	/*Car Related*/

	//Clerk views available cars - return type will be updated
	@Override
	public String viewCars(ArrayList<Car> cars) {
		String result = "";

		for (Car car: cars) {
			if (car.isCarAvailability() == true) {
				result += car.toString() + "\n";
			}
		}
		return result;
	}

	//Clerk registerRent
	@Override
	public RentRegistration registerRent(ArrayList<RentRegistration> rents, Customer customer, Car rentCar, String dateRented, int numDays) throws ParseException {
		RentRegistration rent = new RentRegistration(customer, rentCar, dateRented, numDays);
		rents.add(rent);
		return rent;
	}

	//Clerk check pending  rent - return type will be updated
	@Override
	public String viewRents(ArrayList<RentRegistration> rents) {
		String result = "";

		for (RentRegistration rent: rents) {

			if (rent.getRentStatus() == 1) {

				result += rent.toString() + "\n";
			}

		}

		return result;
	}

	//Clerk set car date returned
	@Override
	public RentRegistration carReturned(ArrayList<RentRegistration> rents, String custFirstName, String custLastName, String dateRented, String dateReturn) throws ParseException {
		RentRegistration rentDetails = null;
		for (RentRegistration rent: rents) {

			if (rent.getCustomer().getCustFirstName().equalsIgnoreCase(custFirstName) && (rent.getCustomer().getCustLastName().equalsIgnoreCase(custLastName)) && rent.getDateRented().equalsIgnoreCase(dateRented)) {

				rent.setDateReturned(dateReturn);
				rentDetails = rent;
			}

		}
		return rentDetails;
	}

	//get rent by id
	public RentRegistration getRent(ArrayList<RentRegistration> rents, int rentId) {
		for (RentRegistration rent: rents) {
			if (rent.getRentId() == rentId) {
				return rent;
			}
		}
		return null;
	}

	public String toString() {
		return "Clerk [Id=" + getEmpId() + ", FirstName=" + getEmpFirstName() + ", LastName=" +
			getEmpLastName() + ", Email=" + getEmpEmail() + "]";
	}

}