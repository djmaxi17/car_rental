package customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer {

	private static int count = 0;
	private int custId;
	private String custFirstName;
	private String custLastName;
	private String custEmail;
	private String custDob;
	private String custLicenseNo;
	private String custAddress;
	private int custPhoneNumber;
	private String custGender;
	@SuppressWarnings("unused")
	private Date cusDob;
	@SuppressWarnings("unused")
	private int regBy;
	@SuppressWarnings("unused")
	private int rentStatus;
	//Fidelity Card part attribute
	private final FidelityCard custCard;

	public Customer(String custFirstName, String custLastName, String custEmail, String custDob, String custLicenseNo,
		String custAddress, int custPhoneNumber, String custGender) throws ParseException {
		count++;
		this.custId = count;
		this.custFirstName = custFirstName;
		this.custLastName = custLastName;
		this.custEmail = custEmail;

		SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
		Date custDobDate = formatter.parse(custDob);
		this.custDob = formatter.format(custDobDate);
		this.custLicenseNo = custLicenseNo;
		this.custAddress = custAddress;
		this.custPhoneNumber = custPhoneNumber;
		this.custGender = custGender;
		//composition
		this.custCard = new FidelityCard(this.custId, this.custFirstName, this.custLastName, this.custEmail);
	}

	public Customer(int theCustomerId, String custFirstName2, String customerLastName, String customerEmail,
		Date customerDob, int customerPhone, String customerAddress, String cusLicenseNum, String customerGender,
		int registeredByEmp, int theIsRented, FidelityCard fidele) {
		this.custId = theCustomerId;
		this.custFirstName = custFirstName2;
		this.custLastName = customerLastName;
		this.custEmail = customerEmail;
		this.cusDob = customerDob;
		this.custPhoneNumber = customerPhone;
		this.custAddress = customerAddress;
		this.custLicenseNo = cusLicenseNum;
		this.custGender = customerGender;
		this.regBy = registeredByEmp;
		this.rentStatus = theIsRented;
		this.custCard = fidele;

	}

	public String toString() {
		return "Customer [custId=" + custId + ", custName=" + getFullName() + ", custEmail=" + custEmail + ", custDob=" + custDob + ", custLicenseNo=" + custLicenseNo +
			", custAddress=" + custAddress + ", \ncustPhoneNumber=" + custPhoneNumber + ", custGender=" + custGender +
			", custCardID=" + getCustCard().getCardId() + ", custCardPoints=" + getCustCard().getCardPoints() + "]\n";
	}

	public String getCustFirstName() {
		return this.custFirstName;
	}

	public String getCustLastName() {
		return this.custLastName;
	}

	public String getFullName() {
		return this.getCustFirstName() + " " + this.getCustLastName();
	}
	public String getEmail() {
		return this.custEmail;
	}
	public String getLiscenceNo() {
		return this.custLicenseNo;
	}
	//get Customer Card
	public FidelityCard getCustCard() {
		return custCard;
	}

	public int getCustId() {
		// TODO Auto-generated method stub
		return this.custId;
	}
}