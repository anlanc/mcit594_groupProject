package edu.upenn.cit594.datamanagement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.ParkingViolation;


public class ParkingViolationJSONReader extends ParkingViolationFileReader {
	public ParkingViolationJSONReader(String filename) {
		super(filename);
		//this.filename = filename;
	}
	@Override
	public ArrayList<ParkingViolation> ReadFromFile() throws FileNotFoundException, IOException, ParseException {
		ArrayList<ParkingViolation> violations=  new ArrayList<ParkingViolation>();
		JSONParser parser = new JSONParser();
		JSONArray jsViolations = (JSONArray)parser.parse(new FileReader(filename));
		//System.out.println("Total JSONArray size: " + jsViolations.size());
		// use an iterator to iterate over each element of the array
		Iterator<?> iter = jsViolations.iterator();
		// iterate while there are more objects in array
		//int count  = 0;
		while (iter.hasNext()) {
			// get the next JSON object
			JSONObject jo = (JSONObject) iter.next();						
			// use the "get" method to print the value associated with that key
			
			long violationID;
			String vehicleID;
			String time;
			int zipCode;
			String description;
			int fine;
			String state; 
			
			try {
				violationID = (long)jo.get("ticket_number");
				//System.out.println("Read ti " +jo.get("ticket_number"));
				vehicleID = (String)jo.get("plate_id");
				vehicleID = vehicleID.trim();
				time = (String)jo.get("date");				
				
				// Error handling: wrong type
				if(isParsable((String)jo.get("zip_code"))) {
					zipCode = Integer.parseInt((String)jo.get("zip_code"));
				}
				else {
					//System.out.println("----- NOT PARSABLE ------" + jo.size());
					//count++;
					continue;
				}			
				
				description = (String)jo.get("violation");
				fine  = (int)(long)jo.get("fine");
				state = (String)jo.get("state");
				state = state.trim();
			}
			
			// Error handling: wrong type expected
			catch(ClassCastException e) {
				//System.out.println("ClassCastException!");
				continue;
				
			}
			// Error handling: missing fields
			if(vehicleID.length() == 0 || time.length() == 0 || description.length() == 0 || state.length() == 0 ) {
				continue;
			}
							
			ParkingViolation pkv = new ParkingViolation(time, fine, description, vehicleID, state, 
					violationID, zipCode);
			violations.add(pkv);
	
		}//end of while loop
		
		//System.out.println("count: " + count);
		return violations;
		
	}
	
	// helper method
	private boolean isParsable(String input) {
		try {
			Integer.parseInt(input);
			return true;
		}
		catch(NumberFormatException e){
			return false;
			
		}
	}
	

	//test
	
//	public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
//
//		//parking.json
//		String filename = "parking.json";
//		ParkingViolationFileReader pr = new ParkingViolationJSONReader(filename);		
//		ArrayList<ParkingViolation>  violations = pr.ReadFromFile();
//		System.out.println("size: "+ violations.size());		
//
//		for(int i = 0 ; i < violations.size(); i++) {
//			System.out.println(violations.get(i));
//		}
//
//	}
	
}
