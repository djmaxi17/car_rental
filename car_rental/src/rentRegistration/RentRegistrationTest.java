package rentRegistration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.Test;

import car.Car;
import customer.Customer;
import database.DbConnect;

public class RentRegistrationTest{
	DbConnect connect = new DbConnect();
	 
	//won't work if not in database 
	Customer cust = connect.BindTableCustomer(3, -1).get(0);
	Car car = connect.bindTable("230 DC 09", -1).get(0);
	
	RentRegistration rentTest = new RentRegistration(cust, car, "2020-04-11", 5);
	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test 
	public void testCalDifference() {
		//test RentRegistration calculateNumOfDays method
		String dateDateDue = dateformat.format(rentTest.getDateDue());
		int difference = rentTest.calculateNumOfDays(rentTest.getDateRented(),dateDateDue);
		assertEquals(5,difference);
	}
	
	@Test
		//test RentRegistration setDateReturned method
	public void testSetDateReturned() {
		assertTrue(rentTest.setDateReturned("2020-04-12"));
	}
	
	@Test
		//test RentRegistration setDateDue method
	public void testSetDateDue() {
		String dateDateDue = dateformat.format(rentTest.getDateDue());
		assertEquals("2020-04-16",dateDateDue);
		
	}
	
}