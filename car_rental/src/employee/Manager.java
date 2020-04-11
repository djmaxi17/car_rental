package employee;

import java.text.ParseException;
import java.util.ArrayList;

import car.Car;
import customer.Customer;
import rentRegistration.RentRegistration;

public class Manager extends Employee implements Rentable {

	//defining constructor
	public Manager(String empFirstName, String empLastName) {
		super(empFirstName, empLastName);
	}

	public Manager(int id, String firstName, String lastName, String email, String password) {
		super(id, firstName, lastName, email, password);
	}

	public Car addCar(ArrayList<Car> cars, String carPlateNumber, String carMake, String carModel, String carType, int carYear, float engineSize,
		int carSeatNo, String carTransmission, String carFuelType, byte[] carImage, float carTrunkSize, float carRate, float penaltyPrice, int carRating) {
		Car mycar = new Car(carPlateNumber, carMake, carModel, carType, carYear, engineSize, carSeatNo, carTransmission, carFuelType, carImage, carTrunkSize, carRate, penaltyPrice, carRating);
		cars.add(mycar);
		return mycar;
	}

	//method to delete car
	public boolean deleteCar(ArrayList<Car> cars, String carPlateNumberString) {
		//set technician reference to null, garbage collector will automatically delete object no reference
		for (Car car: cars) {

			if (car.getPlateNumber().equalsIgnoreCase(carPlateNumberString)) {
				car = null;
				cars.remove(car);
				return true;
			}
		}
		return false;
	}

	/*Customer Related*/
	//MANAGER adds a customer -done
	@Override
	public Customer registerCustomer(ArrayList<Customer> customers, String custFirstName, String custLastName, String custEmail, String custDOB, String custLicenseNo, String custAddress, int custPhoneNumber, String custGender) throws ParseException {
		Customer customer = new Customer(custFirstName, custLastName, custEmail, custDOB, custLicenseNo, custAddress, custPhoneNumber, custGender);
		customers.add(customer);
		return customer;
	}

	//MANAGER search a customer or select customer -done
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

	//MANAGER views available cars - return type will be updated
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

	//MANAGER registerRent
	@Override
	public RentRegistration registerRent(ArrayList<RentRegistration> rents, Customer customer, Car rentCar, String dateRented, int numDays) throws ParseException {
		RentRegistration rent = new RentRegistration(customer, rentCar, dateRented, numDays);
		rents.add(rent);
		return rent;
	}

	//MANAGER check pending  rent - return type will be updated
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

	//MANAGER set car date returned
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

	public String toString() {
		return "Manager [Id=" + getEmpId() + ", FirstName=" + getEmpFirstName() + ", LastName=" +
			getEmpLastName() + ", Email=" + getEmpEmail() + "]";
	}

}