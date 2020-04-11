package customer;

public class FidelityCard {
	//attributes of fidelity card
	private static int idCount = 0;
	private int cardId;
	private int custPoints;
	private int custId;
	private String custFirstName;
	private String custLastName;
	private String custEmail;

	public FidelityCard(int custId, String custFirstName, String custLastName, String custEmail) {
		idCount++;
		this.cardId = idCount;
		this.custPoints = 0;
		this.custId = custId;
		this.custFirstName = custFirstName;
		this.custLastName = custLastName;
		this.custEmail = custEmail;
	}

	public FidelityCard(int custId, String custFirstName, String custLastName, String custEmail, int customerPoints) {

		this.custPoints = customerPoints;
		this.custId = custId;
		this.custFirstName = custFirstName;
		this.custLastName = custLastName;
		this.custEmail = custEmail;
	}

	//method to get card id
	public int getCardId() {

		return this.cardId;
	}

	//method to get points
	public int getCardPoints() {

		return this.custPoints;
	}

	//method to set points
	public void setPoints(int points) {
		this.custPoints = points;
	}

	public void incrementPoints() {
		if (this.custPoints == 110) {
			this.custPoints = 10;
		} else {
			this.custPoints = this.custPoints + 10;
		}
	}

	public String toString() {
		return "FidelityCard [cardId=" + cardId + ", points=" + custPoints + ", custId=" + custId + ", custFirstName=" +
			custFirstName + ", custLastName=" + custLastName + ", custEmail=" + custEmail + "]";
	}

}