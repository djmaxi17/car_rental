package employee;

public abstract class Employee {
	private int empId;
	private String empFirstName;
	private String empLastName;
	private String empEmail;
	private String empPassword;

	public Employee(String empFirstName, String empLastName) {
		this.empFirstName = empFirstName;
		this.empLastName = empLastName;
		this.setEmpEmail();
		this.setEmpPassword();
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public Employee(int empId, String empFirstName, String empLastName, String empEmail, String empPassword) {
		this.empId = empId;
		this.empFirstName = empFirstName;
		this.empLastName = empLastName;
		this.empEmail = empEmail;
		this.empPassword = empPassword;
	}

	//Generates Email
	public void setEmpEmail() {
		this.empEmail = this.empFirstName.toLowerCase() + "." + this.empLastName.toLowerCase() + "@maxiautomobile.com";
	}

	//Generates Password
	public void setEmpPassword() {
		this.empPassword = "maxi" + Math.round(Math.random() * 10) + this.empFirstName.toLowerCase();
	}

	//Change Password
	public boolean changePassword(String oldPassword, String newPassword) {
		if (this.empPassword == oldPassword) {
			this.empPassword = newPassword;
			return true;
		} else {
			return false;
		}
	}

	//Login
	//	public boolean login(String empEmail,String empPassword) {
	//		if(this.empEmail ==empEmail && this.empPassword==empPassword) {
	//			return true;
	//		}
	//		else {
	//			return false;
	//		}
	//	}

	//Abstract toString that needs to be implemented for each sub classes
	public abstract String toString();

	//get
	public int getEmpId() {
		return empId;
	}

	public String getEmpFirstName() {
		return empFirstName;
	}

	public String getEmpLastName() {
		return empLastName;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public String getEmpPassword() {
		return empPassword;
	}

	public String getFullName() {
		if (empFirstName.equalsIgnoreCase("admin")) {
			return "admin";
		} else {
			return empFirstName + " " + empLastName;
		}
	}

}