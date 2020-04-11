package car;

public class Car {

	private static int count = 0;
	private int carId;
	private String carPlateNumber;
	private String carMake;
	private String carModel;
	private String carType;
	private int carYear;
	private float engineSize;
	private int carSeatNo;
	private String carTransmission;
	private String carFuelType;
	private float carTrunkSize;
	private float carRate;
	private boolean carAvailability;
	private byte[] carImage;
	private float carPenaltyPrice;
	private int carRating;

	public Car(String carPlateNumber, String carMake, String carModel, String carType, int carYear, float engineSize,
		int carSeatNo, String carTransmission, String carFuelType, byte[] carImage, float carTrunkSize, float carRate, float penaltyPrice, int carRating) {
		count++;
		this.carId = count;
		this.carPlateNumber = carPlateNumber;
		this.carMake = carMake;
		this.carModel = carModel;
		this.carType = carType;
		this.carYear = carYear;
		this.engineSize = engineSize;
		this.carSeatNo = carSeatNo;
		this.carTransmission = carTransmission;
		this.carFuelType = carFuelType;
		this.carImage = carImage;
		this.carTrunkSize = carTrunkSize;
		this.carRate = carRate;
		this.carPenaltyPrice = penaltyPrice;
		this.carAvailability = true;
		this.carRating = carRating;
	}

	public boolean isCarAvailability() {
		return carAvailability;
	}

	public void setCarAvailability(boolean carAvailability) {
		this.carAvailability = carAvailability;
	}

	public float getCarRate() {

		return this.carRate;
	}

	public String getFullCarName() {
		return this.carMake + " " + this.carModel;
	}

	public float getEngineSize() {

		return this.engineSize;
	}

	public int getSeats() {

		return this.carSeatNo;
	}

	public String getGear() {

		return this.carTransmission;
	}

	public String toString() {
		return "Car [carId=" + carId + ", carPlateNumber=" + carPlateNumber + ", carName=" + getFullCarName() + ", carType=" + carType + ", carYear=" + carYear + ", engineSize=" + engineSize +
			", carSeatNo=" + carSeatNo + ",\n carTransmission=" + carTransmission + ", carFuelType=" + carFuelType +
			", carTrunkSize=" + carTrunkSize + ", carRate=" + carRate + ", carAvailability=" + carAvailability + "]\n";
	}

	public String getPlateNumber() {
		return this.carPlateNumber;
	}

	public String getMake() {
		// TODO Auto-generated method stub
		return this.carMake;
	}
	public String getModel() {
		// TODO Auto-generated method stub
		return this.carModel;
	}

	public String getCarMake() {
		return carMake;
	}

	public String getCarModel() {
		return carModel;
	}

	/**
	 * @return the carImage
	 */
	public byte[] getCarImage() {
		return carImage;
	}

	public static int getCount() {
		return count;
	}

	public int getCarId() {
		return carId;
	}

	public String getCarPlateNumber() {
		return carPlateNumber;
	}

	public String getCarType() {
		return carType;
	}

	public int getCarYear() {
		return carYear;
	}

	public int getCarSeatNo() {
		return carSeatNo;
	}

	public String getCarFuelType() {
		return carFuelType;
	}

	public float getCarTrunkSize() {
		return carTrunkSize;
	}
	public float getPenaltyPrice() {
		return this.carPenaltyPrice;
	}
	public int getCarRating() {
		return this.carRating;
	}

}