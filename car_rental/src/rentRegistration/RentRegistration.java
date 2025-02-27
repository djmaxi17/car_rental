package rentRegistration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import org.joda.time.Days;

import car.Car;
import customer.Customer;
import database.DbConnect;

public class RentRegistration {
	private static int count = 0;
	private int rentId;
	private Customer customer;

	private Car rentCar;
	private String dateRented;
	private Date dateDue;
	private String dateReturned;
	private int numOfDaysDefault;
	private int numOfDaysTaken;
	private float rentCost;
	private float penalty = 0;
	private int customerPoints;
	private int rentStatus;

	public RentRegistration(Customer customer, Car rentCar, String dateRented, int numDaysDefault) {
		count++;
		this.rentId = count;
		this.customer = customer;
		this.customer.getCustCard().incrementPoints();
		this.customerPoints = this.customer.getCustCard().getCardPoints();
		this.rentCar = rentCar;
		this.dateRented = dateRented;
		this.rentStatus = 1;
		this.numOfDaysDefault = numDaysDefault;
		try {
			this.setDateDue(dateRented, numDaysDefault);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.rentCar.setCarAvailability(false);
		this.calculateRentCost();
	}

	public RentRegistration(int rentId1, int cust, String car, Date dateRented2, Date dateDue2, float total, int numDaysDefault, int rentStatus, Date dateReturned2, int daysTaken, float penalty2) {
		DbConnect connect = new DbConnect();
		this.rentId = rentId1;
		this.customer = connect.BindTableCustomer(cust, -1).get(0);
		this.rentCar = connect.bindTable(car, -1).get(0);
		this.dateRented = dateRented2.toString();
		this.numOfDaysDefault = numDaysDefault;
		this.dateDue = dateDue2;
		this.rentCost = total;
		this.rentStatus = rentStatus;
		if (dateReturned2 != null) {
			this.dateReturned = dateReturned2.toString();
		}
		this.numOfDaysTaken = daysTaken;
		this.penalty = penalty2;
	}

	//set a date due based on dateRented and number of Days
	public void setDateDue(String dateRented, int numDays) throws ParseException {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateRented1 = dateformat.parse(this.dateRented);
		// convert date to calendar
		Calendar c = Calendar.getInstance();
		c.setTime(dateRented1);

		// manipulate date
		c.add(Calendar.DATE, numDays); //same with c.add(Calendar.DAY_OF_MONTH, 1);

		// convert calendar to date
		Date dateDueCurrent = c.getTime();
		this.dateDue = dateDueCurrent;
	}

	/*set a date returned that is after the date rented
	reset the rentCar availability to available, rent completion to true
	calculate difference in date rented and date returned
	calculate penalty
	*/
	public boolean setDateReturned(String dateReturned){
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateReturnedDate = null;
		try {
			dateReturnedDate = dateformat.parse(dateReturned);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date dateRented = null;
		try {
			dateRented = dateformat.parse(this.dateRented);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dateReturnedDate.after(dateRented)) {
			this.dateReturned = dateReturned;
			this.rentCar.setCarAvailability(true);
			this.rentStatus = 0;
			this.numOfDaysTaken = calculateNumOfDays(this.dateRented, this.dateReturned);
			this.calculatePenalty();
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Error in the value of date returned! \nThis date must be obviously after the date rented!", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Error in the value of date returned! This date must be obviously after the date rented!");
			return false;
		}
	}

	//get completion status of rent registered
	public int getRentStatus() {
		return this.rentStatus;
	}

	//get Customer object
	public Customer getCustomer() {

		return this.customer;
	}

	//Calculate Normal RentCost
	public void calculateRentCost() {
		//checks if points equals 100 and accounts for discounts
		if (this.customerPoints == 100) {
			this.rentCost = (this.numOfDaysDefault * this.rentCar.getCarRate()) / 2;
		} else {

			this.rentCost = this.numOfDaysDefault * this.rentCar.getCarRate();

		}
	}

	//Calculate Penalty if exceed dateDue
	public void calculatePenalty() {
		if (this.numOfDaysTaken > this.numOfDaysDefault) {
			int extraDays = this.numOfDaysTaken - this.numOfDaysDefault;
			this.penalty = extraDays * (this.getCar().getCarRate() + this.getCar().getPenaltyPrice());
		}
	}

	//function to calculate number of days between two dates
	public int calculateNumOfDays(String dateBefore, String dateAfter) {
		try {

			int days = Days.daysBetween(new org.joda.time.LocalDate(dateBefore), new org.joda.time.LocalDate(dateAfter)).getDays();

			return days;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return 0;
	}

	public String toString() {
		String output;
		output = "RentRegistration [ rentId=" + rentId + ", customerName=" + customer.getFullName() + ", rentCar=" + rentCar.getFullCarName() + ", rentCarAvailabilty=" +
			rentCar.isCarAvailability() +
			", dateRented=" + dateRented + ",\n dateDue=" + dateDue + ", dateReturned=" + dateReturned +
			", numOfDaysTaken=" + numOfDaysTaken + ", numOfDaysDefault=" + numOfDaysDefault + ", Normal Cost=" +
			rentCost + ", Penalty=" + this.penalty + ", RentStatus=" + this.rentStatus + "]\n";

		if (this.customerPoints == 50) {
			output += "The Cost is at half price because you accumulated 50 points on your card!\n";
		}

		return output;
	}

	public int getRentId() {
		return this.rentId;
	}

	public Car getCar() {
		return this.rentCar;
	}

	public String getDateRented() {
		return this.dateRented;
	}

	public Car getRentCar() {
		return rentCar;
	}

	public Date getDateDue() {
		return dateDue;
	}

	public String getDateReturned() {
		return dateReturned;
	}

	public int getNumOfDaysDefault() {
		return numOfDaysDefault;
	}

	public int getNumOfDaysTaken() {
		return numOfDaysTaken;
	}

	public float getRentCost() {
		return rentCost;
	}

	public float getPenalty() {
		return penalty;
	}

	public int getCustomerPoints() {
		return customerPoints;
	}
	public int getCustomerId() {
		return this.customer.getCustId();
	}

	public String getDateDueString() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(this.dateDue);
	}
}