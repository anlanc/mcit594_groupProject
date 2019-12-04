package edu.upenn.cit594.data;

public class ParkingViolation {
	public String time;
	public int fine;
	public String description;
	public String vehicleID;
	public String state;
	public long violationID;
	public int zipCode;
	
	public ParkingViolation(String time, int fine, String description, String vehicleID, String state, 
			long violationID, int zipCode) {
		this.time = time;
		this.fine = fine;
		this.description = description;
		this.vehicleID = vehicleID;
		this.state = state;
		this.violationID = violationID;
		this.zipCode = zipCode;		
		
	}

	public String getTime() {
		return time;
	}

	public int getFine() {
		return fine;
	}

	public String getDescription() {
		return description;
	}


	public String getVehicleID() {
		return vehicleID;
	}


	public String getState() {
		return state;
	}

	public long getViolationID() {
		return violationID;
	}


	public int getZipCode() {
		return zipCode;
	}


	@Override
	public String toString() {
		return  time + ", " + fine + ", " + description + ", "
				+ vehicleID + ", " + state + ", " + violationID + ", " + zipCode;
	}
	
	
	
	
}
