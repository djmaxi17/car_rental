package database;

import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import car.Car;
import customer.Customer;
import customer.FidelityCard;
import employee.Clerk;
import employee.Manager;
import employee.Technician;
import hash.Hash;
import main.LoginSession;
import rentRegistration.RentRegistration;

public class DbConnect {
	//online database 
	//private static final String username = "I6TiCsakrM";
	//private static final String password = "qXmZLkzjQ8";
	//private static final String connection = "jdbc:mysql://remotemysql.com:3306/I6TiCsakrM?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private static final String username = "carrentaladmin";
	private static final String password = "Bonjour12345";
	private static final String connection = "jdbc:mysql://localhost/car_rental?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private static Connection con;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement ps;

	public DbConnect() {
		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(connection, username, password);
			st = con.createStatement();

		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
	}

	//generates a array list of technicians from database
	public ArrayList<Technician> getTechnicians() {
		ArrayList<Technician> technicians = new ArrayList<Technician> ();
		try {

			String query = "SELECT * FROM employees WHERE employeeRole=?";
			ps = con.prepareStatement(query);
			ps.setString(1, "technician");

			rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("employeeId");
				String firstName = rs.getString("employeeFirstName");
				String lastName = rs.getString("employeeLastName");
				String email = rs.getString("employeeEmail");
				String password = rs.getString("employeePassword");
				technicians.add(new Technician(id, firstName, lastName, email, password));
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("SQL Error: " + ex);
		}
		return technicians;
	}

	//generates a array list of managers from database
	public ArrayList<Manager> getManagers() {
		ArrayList<Manager> managers = new ArrayList<Manager> ();
		try {

			String query = "SELECT * FROM employees WHERE employeeRole=?";
			ps = con.prepareStatement(query);
			ps.setString(1, "manager");

			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("employeeId");
				String firstName = rs.getString("employeeFirstName");
				String lastName = rs.getString("employeeLastName");
				String email = rs.getString("employeeEmail");
				String password = rs.getString("employeePassword");
				managers.add(new Manager(id, firstName, lastName, email, password));
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("SQL Error: " + ex);
		}
		return managers;
	}

	//generates a array list of clerks from database
	public ArrayList<Clerk> getClerks() {
		ArrayList<Clerk> clerks = new ArrayList<Clerk> ();
		try {

			String query = "SELECT * FROM employees WHERE employeeRole=?";
			ps = con.prepareStatement(query);
			ps.setString(1, "clerk");
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("employeeId");
				String firstName = rs.getString("employeeFirstName");
				String lastName = rs.getString("employeeLastName");
				String email = rs.getString("employeeEmail");
				String password = rs.getString("employeePassword");
				clerks.add(new Clerk(id, firstName, lastName, email, password));
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("SQL Error: " + ex);
		}
		return clerks;
	}

	//Method to get id of employee
	public int getEmployeeId(String firstName, String lastName, String employeeRole) {
		int result = 0;
		try {
			String query = "SELECT * FROM employees WHERE employeeFirstName=? AND employeeLastName=? AND employeeRole=?";
			ps = con.prepareStatement(query);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, employeeRole);
			rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("employeeId");
				result = id;
			} else {
				result = 0;
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("SQL Error: " + ex);
		}
		return result;
	}

	//Method to insert details of a new employee in db
	public boolean insertEmployee(String employeeFirstName, String employeeLastName, String employeeEmail, String employeePassword, String employeeRole, int registeredByEmp) throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			String disablefkCheck = "SET FOREIGN_KEY_CHECKS=0;";
			st.executeUpdate(disablefkCheck);

			String hashPassword = Hash.generateStorngPasswordHash(employeePassword);
			String query = "INSERT INTO employees (employeeFirstName,employeeLastName,employeeEmail,employeePassword,employeeRole,registeredByEmp) VALUES(?,?,?,?,?,?)";
			ps = con.prepareStatement(query);
			ps.setString(1, employeeFirstName);
			ps.setString(2, employeeLastName);
			ps.setString(3, employeeEmail);
			ps.setString(4, hashPassword);
			ps.setString(5, employeeRole);
			ps.setInt(6, registeredByEmp);
			ps.executeUpdate();
			System.out.println(employeeRole.toUpperCase() + " Record Inserted!");

			String enablefkCheck = "SET FOREIGN_KEY_CHECKS=1;";
			st.executeUpdate(enablefkCheck);
			return true;

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("SQL Error: " + ex);
			return false;
		}
	}

	//method for employee password
	public boolean changePassword(String email, String cp, String np) {

		try {
			String query = "SELECT employeePassword FROM employees WHERE employeeEmail=?;";

			ps = con.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();

			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Invalid Email!", "ERROR", JOptionPane.ERROR_MESSAGE);
				return false;
			} else {
				String password = rs.getString("employeePassword");

				boolean response = Hash.validatePassword(cp, password);
				if (!response) {
					JOptionPane.showMessageDialog(null, "Invalid Current Password!", "ERROR", JOptionPane.ERROR_MESSAGE);
					return false;
				} else {
					String newHash = Hash.generateStorngPasswordHash(np);
					String queryUpdate = "UPDATE employees SET employeePassword =? WHERE employeeEmail=?;";
					ps = con.prepareStatement(queryUpdate);
					ps.setString(1, newHash);
					ps.setString(2, email);
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Password has changed!");
					return true;
				}
			}

		} catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException e) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	//Method to delete an employee with name
	public boolean deleteEmployee(String firstName, String lastName, String employeeRole) {
		try {
			String query = "DELETE FROM employees WHERE employeeFirstName=? AND employeeLastName=? AND employeeRole=?";
			ps = con.prepareStatement(query);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, employeeRole);
			ps.executeUpdate();
			System.out.println(employeeRole.toUpperCase() + " Record deleted successfully!");
			return true;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("SQL Error: " + ex);
			return false;
		}
	}

	//retrieve login hash password form db
	public boolean employeePasswordCheck(String empEmail, String empPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String hashPassword;
		try {
			String query = "SELECT employeePassword FROM employees WHERE employeeEmail=?";
			ps = con.prepareStatement(query);
			ps.setString(1, empEmail);
			rs = ps.executeQuery();
			if (rs.next()) {
				hashPassword = rs.getString("employeePassword");
				boolean response = Hash.validatePassword(empPassword, hashPassword);
				if (response == true) {
					return true;
				} else return false;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("SQL Error: " + ex);
		}
		return false;
	}

	// login function
	public boolean isLogin(String userEmail, String password, JFrame frame) throws NoSuchAlgorithmException, InvalidKeySpecException {
		boolean result = false;
		try {
			DbConnect cons = new DbConnect();
			boolean validation = cons.employeePasswordCheck(userEmail, password);
			if (validation == true) {
				String sqlQuery = "SELECT * FROM employees WHERE employeeEmail = '" + userEmail + "';";
				PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					LoginSession.userId = resultSet.getInt("employeeId");
					LoginSession.userFirstName = resultSet.getString("employeeFirstName");
					LoginSession.username = resultSet.getString("employeeLastName");
					LoginSession.usertype = resultSet.getString("employeeRole");
					LoginSession.isLoggedIn = true;
					result = true;
				}
			} else if (validation == false) {
				result = false;
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("SQL Error1: " + ex);
			return false;
		}
		return result;
	}

	//method to insert a car in system/database
	public boolean insertCar(String carPlateNum, String carMake, String carModel, String carType, int carYear, String carTransmission, String carFuelType,
		FileInputStream fis, int fileLength, int carNumOfSeat, double carPricePerDay, double carPenaltyPrice, double carTrunkSize, int carEngineSize, int carRating,
		int registeredByEmp, int carAvailability) {

		try {

			String disablefkCheck = "SET FOREIGN_KEY_CHECKS=0;";
			st.executeUpdate(disablefkCheck);

			String query = "INSERT INTO cars (carPlateNum,carMake,carModel,carType,carYear,carTransmission," +
				"carFuelType,carImage,carNumOfSeat,carPricePerDay,carPenaltyPrice,carTrunkSize," +
				"carEngineSize,carRating,registeredByEmp,carAvailability) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(query);
			ps.setString(1, carPlateNum);
			ps.setString(2, carMake);
			ps.setString(3, carModel);
			ps.setString(4, carType);
			ps.setInt(5, carYear);
			ps.setString(6, carTransmission);
			ps.setString(7, carFuelType);
			ps.setBinaryStream(8, fis, fileLength);
			ps.setInt(9, carNumOfSeat);
			ps.setDouble(10, carPricePerDay);
			ps.setDouble(11, carPenaltyPrice);
			ps.setDouble(12, carTrunkSize);
			ps.setInt(13, carEngineSize);
			ps.setInt(14, carRating);
			ps.setInt(15, registeredByEmp);
			ps.setInt(16, carAvailability);
			ps.executeUpdate();
			System.out.println("Records for Car inserted");

			String enablefkCheck = "SET FOREIGN_KEY_CHECKS=1;";
			st.executeUpdate(enablefkCheck);
			return true;

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error : " + ex);
			return false;
		}
	}

	//method to delete Car
	public boolean deleteCar(String carPlateNum) {

		try {

			String disablefkCheck = "SET FOREIGN_KEY_CHECKS=0;";
			st.executeUpdate(disablefkCheck);

			String query = "DELETE FROM cars WHERE cars.carPlateNum = ?";
			ps = con.prepareStatement(query);
			ps.setString(1, carPlateNum);
			ps.executeUpdate();

			String enablefkCheck = "SET FOREIGN_KEY_CHECKS=1;";
			st.executeUpdate(enablefkCheck);
			return true;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	//method to get all cars from database
	@SuppressWarnings("unused")
	//unused since bindTable is used instead
	public ArrayList<Car> getCar(String carNum, int carAvailable) {

		ArrayList<Car> cars = new ArrayList<Car> ();

		try {

			String query = "";

			//query for all cars
			if ((carNum == null) && (carAvailable == -1)) {

				query = "SELECT * FROM cars";
				ps = con.prepareStatement(query);
				//query for available cars
			} else if ((carNum == null) && (carAvailable == 1)) {

				query = "SELECT * FROM cars WHERE carAvailability = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1, 1);
				//query for specific car based  on car plate number

			} else if ((carNum != null) && (carAvailable == -1)) {

				query = "SELECT * FROM cars WHERE carPlateNum =?";
				ps = con.prepareStatement(query);
				ps.setString(1, carNum);

				//query for unavailable cars
			} else if ((carNum == null) && (carAvailable == 0)) {

				query = "SELECT * FROM cars WHERE carAvailability = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1, 0);
			}

			rs = ps.executeQuery();

			while (rs.next()) {

				String carPlateNum = rs.getString("carPlateNum");
				String carMake = rs.getString("carMake");
				String carModel = rs.getString("carModel");
				String carType = rs.getString("carType");
				int carYear = rs.getInt("carYear");
				String carTransmission = rs.getString("carTransmission");
				String carFuelType = rs.getString("carFuelType");
				byte[] carImage = rs.getBytes("carImage");
				int carNumOfSeat = rs.getInt("carNumOfSeat");
				float carPricePerDay = rs.getFloat("carPricePerDay");
				float carPenaltyPrice = rs.getFloat("carPenaltyPrice");
				float carTrunkSize = rs.getFloat("carTrunkSize");
				float carEngineSize = Float.valueOf(rs.getInt("carEngineSize"));
				int carRating = rs.getInt("carRating");
				int registeredByEmp = rs.getInt("registeredByEmp");
				int carAvailability = rs.getInt("carAvailability");

				cars.add(new Car(carPlateNum, carMake, carModel, carType, carYear, carEngineSize, carNumOfSeat, carTransmission, carFuelType, carImage, carTrunkSize, carPricePerDay, carPenaltyPrice, carRating));

			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error : " + ex);
		}

		return cars;
	}

	//method to get all cars from database
	public ArrayList<Car> bindTable(String carNum, int carAvailable) {

		ArrayList<Car> cars = new ArrayList<Car> ();

		try {

			String query = "";

			//query for all cars
			if ((carNum == null) && (carAvailable == -1)) {

				query = "SELECT * FROM cars";
				ps = con.prepareStatement(query);
				//query for available cars
			} else if ((carNum == null) && (carAvailable == 1)) {

				query = "SELECT * FROM cars WHERE carAvailability = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1, 1);
				//query for specific car based  on car plate number

			} else if ((carNum != null) && (carAvailable == -1)) {

				query = "SELECT * FROM cars WHERE carPlateNum =?";
				ps = con.prepareStatement(query);
				ps.setString(1, carNum);

				//query for unavailable cars
			} else if ((carNum == null) && (carAvailable == 0)) {

				query = "SELECT * FROM cars WHERE carAvailability = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1, 0);
			}

			rs = ps.executeQuery();

			while (rs.next()) {

				String carPlateNum = rs.getString("carPlateNum");
				String carMake = rs.getString("carMake");
				String carModel = rs.getString("carModel");
				String carType = rs.getString("carType");
				int carYear = rs.getInt("carYear");
				String carTransmission = rs.getString("carTransmission");
				String carFuelType = rs.getString("carFuelType");
				byte[] carImage = rs.getBytes("carImage");
				int carNumOfSeat = rs.getInt("carNumOfSeat");
				float carPricePerDay = rs.getFloat("carPricePerDay");
				float carPenaltyPrice = rs.getFloat("carPenaltyPrice");
				float carTrunkSize = rs.getFloat("carTrunkSize");
				int carRating = rs.getInt("carRating");
				float carEngineSize = Float.valueOf(rs.getInt("carEngineSize"));
				//					int carRating = rs.getInt("carRating");
				//					int registeredByEmp = rs.getInt("registeredByEmp"); 
				//					int carAvailability = rs.getInt("carAvailability");
				//					
				cars.add(new Car(carPlateNum, carMake, carModel, carType, carYear, carEngineSize, carNumOfSeat, carTransmission, carFuelType, carImage, carTrunkSize, carPricePerDay, carPenaltyPrice, carRating));
				//System.out.println("the zafer = "+carImage);
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error : " + ex);
		}

		return cars;
	}

	//method to get all customer from database
	public ArrayList<Customer> BindTableCustomer(int customerId, int isRented) {

		ArrayList<Customer> customers = new ArrayList<Customer> ();

		try {

			String query = "";

			//query for all Customers
			if ((customerId == 0) && (isRented == -1)) {

				query = "SELECT * FROM customers";
				ps = con.prepareStatement(query);

				//query for available Customers
			} else if ((customerId == 0) && (isRented == 0)) {

				query = "SELECT * FROM customers WHERE isRented = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1, 0);

				//query for specific customers based  on customer id
			} else if ((customerId != 0) && (isRented == -1)) {

				query = "SELECT * FROM customers WHERE customerId =?";
				ps = con.prepareStatement(query);
				ps.setInt(1, customerId);

				//query for renting customers
			} else if ((customerId == 0) && (isRented == 1)) {

				query = "SELECT * FROM customers WHERE isRented = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1, 1);
			}

			rs = ps.executeQuery();

			while (rs.next()) {

				int theCustomerId = rs.getInt("customerId");
				String custFirstName = rs.getString("customerFirstName");
				String customerLastName = rs.getString("customerLastName");
				String customerEmail = rs.getString("customerEmail");
				Date customerDob = rs.getDate("customerDob");
				int customerPhone = rs.getInt("customerPhone");
				String customerAddress = rs.getString("customerAddress");
				String cusLicenseNum = rs.getString("cusLicenseNum");
				String customerGender = rs.getString("customerGender");
				int registeredByEmp = rs.getInt("registeredByEmp");
				int theIsRented = rs.getInt("isRented");
				int fidelityPt = rs.getInt("fidelityPoints");

				FidelityCard currentFidelity = new FidelityCard(theCustomerId, custFirstName, customerLastName, customerEmail, fidelityPt);
				customers.add(new Customer(theCustomerId, custFirstName, customerLastName, customerEmail, customerDob, customerPhone, customerAddress, cusLicenseNum, customerGender, registeredByEmp, theIsRented, currentFidelity));
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error : " + ex);
		}

		return customers;
	}

	public RentRegistration insertRent(Car car, Customer customer, String dateRented, int numDays, int empId) throws ParseException {
		RentRegistration rent = new RentRegistration(customer, car, dateRented, numDays);

		try {

			String disablefkCheck = "SET FOREIGN_KEY_CHECKS=0;";
			st.executeUpdate(disablefkCheck);

			String query = "INSERT INTO rents (customerId,carPlateNum,registeredBy,dateRented,dateDue,numOfDaysDefault," +
				"rentCost,customerPoints) VALUES (?,?,?,?,?,?,?,?)";

			String updatePoints = "UPDATE customers SET fidelityPoints = ? WHERE customerId = ?";

			ps = con.prepareStatement(query);
			ps.setInt(1, rent.getCustomerId());
			ps.setString(2, rent.getCar().getCarPlateNumber());
			ps.setInt(3, empId);
			ps.setString(4, dateRented);
			ps.setString(5, rent.getDateDueString());
			ps.setInt(6, numDays);
			ps.setDouble(7, rent.getRentCost());
			ps.setInt(8, rent.getCustomerPoints());

			ps.executeUpdate();
			System.out.println("Records for Rent inserted");

			ps = con.prepareStatement(updatePoints);

			int points = rent.getCustomerPoints();

			if (points == 100) {
				ps.setInt(1, 0);

			} else {
				ps.setInt(1, points);
			}

			ps.setInt(2, rent.getCustomerId());
			ps.executeUpdate();

			String carPlate = rent.getCar().getCarPlateNumber();
			int custId = rent.getCustomerId();

			String querycar = "UPDATE cars SET carAvailability = '0' WHERE cars.carPlateNum = '" + carPlate + "'";
			String querycustomer = "UPDATE customers SET isRented = '1' WHERE customers.customerId = '" + custId + "';";

			st.executeUpdate(querycar);
			st.executeUpdate(querycustomer);
			String enablefkCheck = "SET FOREIGN_KEY_CHECKS=1;";
			st.executeUpdate(enablefkCheck);
			return rent;

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error : " + ex);
			return null;
		}

	}

	//method to convert util date to sql date
	private java.sql.Date convertUtilToSql(java.util.Date uDate) {

		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}

	//method to get all rent from database
	public ArrayList<RentRegistration> BindTableRent(int rentId, int rentStatus) {

		ArrayList<RentRegistration> rents = new ArrayList<RentRegistration> ();

		try {

			String query = "";

			//query for all rents
			if ((rentId == 0) && (rentStatus == -1)) {

				query = "SELECT * FROM rents;";
				ps = con.prepareStatement(query);

				//query for pending rents
			} else if ((rentId == 0) && (rentStatus == 1)) {

				query = "SELECT * FROM rents WHERE rentStatus = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1, 1);

				//query for specific rent based  on rent id
			} else if ((rentId != 0) && (rentStatus == -1)) {

				query = "SELECT * FROM rents WHERE rentId =?";
				ps = con.prepareStatement(query);
				ps.setInt(1, rentId);

				//query for closed rent
			} else if ((rentId == 0) && (rentStatus == 0)) {

				query = "SELECT * FROM rents WHERE rentStatus = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1, 0);
			}

			rs = ps.executeQuery();

			while (rs.next()) {

				int rentId1 = rs.getInt("rentId");
				int customerId = rs.getInt("customerId");
				String carPlateNum = rs.getString("carPlateNum");
				Date dateRented = rs.getDate("dateRented");
				Date dateDue = rs.getDate("dateDue");
				Date dateReturned = rs.getDate("dateReturned");
				int daysTaken = rs.getInt("numOfDaysTaken");
				float penalty = rs.getFloat("rentPenalty");
				float total = rs.getFloat("rentCost");
				int numDaysDefault = rs.getInt("numOfDaysDefault");
				int rentStatus1 = rs.getInt("rentStatus");
				rents.add(new RentRegistration(rentId1, customerId, carPlateNum, dateRented, dateDue, total, numDaysDefault, rentStatus1, dateReturned, daysTaken, penalty));

			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error : " + ex);
		}

		return rents;
	}

	//method to insert Customer in system/db
	public boolean insertCustomer(String customer_firstname, String customer_lastname, String customer_email,
		Date selectedDob, int customer_phone, String customer_address, String customer_licensenum,
		String customer_gender, int userId) {
		try {

			String disablefkCheck = "SET FOREIGN_KEY_CHECKS=0";
			st.executeUpdate(disablefkCheck);

			String insertCustomerQuery = "INSERT INTO customers (customerFirstName, customerLastName, customerEmail," +
				"customerDob, customerPhone, customerAddress, cusLicenseNum, customerGender, registeredByEmp," +
				"isRented, fidelityPoints) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

			ps = con.prepareStatement(insertCustomerQuery);
			ps.setString(1, customer_firstname);
			ps.setString(2, customer_lastname);
			ps.setString(3, customer_email);
			ps.setDate(4, this.convertUtilToSql(selectedDob));
			ps.setInt(5, customer_phone);
			ps.setString(6, customer_address);
			ps.setString(7, customer_licensenum);
			ps.setString(8, customer_gender);
			ps.setInt(9, userId);
			ps.setInt(10, 0);
			ps.setInt(11, 0);

			ps.executeUpdate();
			String enablefkCheck = "SET FOREIGN_KEY_CHECKS=1;";
			st.executeUpdate(enablefkCheck);

			return true;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("sql error: " + ex);
			return false;
		}
	}

	//method to get customer id with liscencenumber
	public int getCustomerId(String LiscenceNumber) {
		try {
			String query = "SELECT customerId FROM customers WHERE cusLicenseNum = ?";
			ps = con.prepareStatement(query);
			ps.setString(1, LiscenceNumber);
			rs = ps.executeQuery();

			int id = 0;
			if (rs.next()) {
				id = rs.getInt("customerId");
			}
			return id;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error: " + e);
			return 0;
		}
	}

	//method to update car availability status
	public boolean updateCarAvailability(String carPlateNum, int status) {

		try {

			String query = "UPDATE cars SET carAvailability = ? WHERE cars.carPlateNum = ?";

			ps = con.prepareStatement(query);
			ps.setInt(1, status);
			ps.setString(2, carPlateNum);

			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	//method to update customer rented status
	public boolean updateCustomerIsRented(int custId, int status) {

		try {

			String query = "UPDATE customers SET isRented = ? WHERE customers.customerId = ?";

			ps = con.prepareStatement(query);
			ps.setInt(1, status);
			ps.setInt(2, custId);

			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	//method to update Rent
	public boolean updateRentInfo(int rentId, int numDaysTaken, double penalty, String dateReturned, int status) {

		try {

			String query = "UPDATE rents SET rentStatus = ?, numOfDaysTaken =?, rentPenalty=?, dateReturned=? " +
				"WHERE rents.rentId = ?";

			ps = con.prepareStatement(query);
			ps.setInt(1, status);
			ps.setInt(2, numDaysTaken);
			ps.setDouble(3, penalty);
			ps.setString(4, dateReturned);
			ps.setInt(5, rentId);

			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	//method to update rent when rent is settled
	public boolean updateRent(RentRegistration rent) {
		updateRentInfo(rent.getRentId(), rent.getNumOfDaysTaken(), rent.getPenalty(), rent.getDateReturned(), 0);
		updateCustomerIsRented(rent.getCustomer().getCustId(), 0);
		updateCarAvailability(rent.getCar().getCarPlateNumber(), 1);
		return true;
	}

	//method to close db connection
	public static void close() {
		try {
			con.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database Problem!", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	//ALL THE ABOVE SQL QUERIES HAVE BEEN TESTED WITH PREPARED STATEMENTS
	//PLEASE DON'T MODIFY THESE ABOVE CODES. THANKS - DAVISEN

}