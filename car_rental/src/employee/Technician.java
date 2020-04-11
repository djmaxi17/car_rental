package employee;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import database.DbConnect;

public class Technician extends Employee {

	//defining constructor for class Technician
	public Technician(String empFirstName, String empLastName) {
		//inherits attributes in parameter from super class Employee
		super(empFirstName, empLastName);

	}

	public Technician(int id, String firstName, String lastName, String email, String password) {
		super(id, firstName, lastName, email, password);
	}

	public String toString() {
		return "Technician [Id=" + getEmpId() + ", FirstName=" + getEmpFirstName() +
			", LastName)=" + getEmpLastName() + ", EmpEmail=" + getEmpEmail() + "]";
	}

	//method to create new Technician
	public Technician createTechnician(ArrayList<Technician> technicians, String empFirstName, String empLastName, int registeredBy) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Technician technician = new Technician(empFirstName, empLastName);
		technicians.add(technician);
		DbConnect connect = new DbConnect();

		boolean check = connect.insertEmployee(technician.getEmpFirstName(), technician.getEmpLastName(), technician.getEmpEmail(), technician.getEmpPassword(), "technician", registeredBy);
		if (check == true) {
			technician.setEmpId(connect.getEmployeeId(empFirstName, empLastName, "technician"));
			return technician;
		}

		return null;
	}

	//method to delete Technician
	public boolean deleteTechnician(ArrayList<Technician> technicians, String empFirstName, String empLastName) {
		//set technician reference to null, garbage collector will automatically delete object no reference
		for (Technician technician: technicians) {

			if (technician.getEmpFirstName().equalsIgnoreCase(empFirstName) && (technician.getEmpLastName().equalsIgnoreCase(empLastName))) {
				DbConnect connect = new DbConnect();
				boolean check = connect.deleteEmployee(empFirstName, empLastName, "technician");
				if (check == true) {
					technician = null;
					technicians.remove(technician);
					return true;
				} else {
					return false;
				}
			}

		}
		return false;
	}

	//method to delete a manager
	public boolean deleteManager(ArrayList<Manager> managers, String empFirstName, String empLastName) {
		//set technician reference to null, garbage collector will automatically delete object no reference
		for (Manager manager: managers) {

			if (manager.getEmpFirstName().equalsIgnoreCase(empFirstName) && (manager.getEmpLastName().equalsIgnoreCase(empLastName))) {
				DbConnect connect = new DbConnect();
				boolean check = connect.deleteEmployee(empFirstName, empLastName, "manager");
				if (check == true) {
					manager = null;
					managers.remove(manager);
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	//method to create new Manager
	public Manager createManager(ArrayList<Manager> managers, String empFirstName, String empLastName, int registeredBy) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Manager manager = new Manager(empFirstName, empLastName);
		managers.add(manager);
		DbConnect connect = new DbConnect();
		boolean check = connect.insertEmployee(manager.getEmpFirstName(), manager.getEmpLastName(), manager.getEmpEmail(), manager.getEmpPassword(), "manager", registeredBy);
		if (check == true) {
			manager.setEmpId(connect.getEmployeeId(empFirstName, empLastName, "manager"));
			return manager;
		}
		return null;

	}

	//method to delete a Clerk
	public boolean deleteClerk(ArrayList<Clerk> clerks, String empFirstName, String empLastName) {
		//set technician reference to null, garbage collector will automatically delete object no reference
		for (Clerk clerk: clerks) {

			if (clerk.getEmpFirstName().equalsIgnoreCase(empFirstName) && (clerk.getEmpLastName().equalsIgnoreCase(empLastName))) {
				DbConnect connect = new DbConnect();
				boolean check = connect.deleteEmployee(empFirstName, empLastName, "clerk");
				if (check == true) {
					clerk = null;
					clerks.remove(clerk);
					return true;
				}
			}

		}
		return false;
	}

	//method to create new Clerk
	public Clerk createClerk(ArrayList<Clerk> clerks, String empFirstName, String empLastName, int registeredBy) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Clerk clerk = new Clerk(empFirstName, empLastName);
		clerks.add(clerk);
		DbConnect connect = new DbConnect();
		boolean check = connect.insertEmployee(clerk.getEmpFirstName(), clerk.getEmpLastName(), clerk.getEmpEmail(), clerk.getEmpPassword(), "clerk", registeredBy);
		if (check == true) {
			clerk.setEmpId(connect.getEmployeeId(empFirstName, empLastName, "clerk"));
			return clerk;
		}
		return null;
	}
}