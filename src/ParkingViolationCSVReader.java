import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.parser.ParseException;


public class ParkingViolationCSVReader extends ParkingViolationFileReader {
	
	public ParkingViolationCSVReader(String filename) {
		super(filename);
		
	}

	@Override
	public ArrayList<ParkingViolation> ReadFromFile() throws FileNotFoundException, IOException, ParseException{
		ArrayList<ParkingViolation> violations=  new ArrayList<ParkingViolation>();
		Scanner s = new Scanner(new File(filename));
		while(s.hasNext()) {
			String line = s.nextLine();
			//System.out.println(line);
			String[] array = line.split(",");
			// Error handling : missing field/column
			if(array.length != 7) {
				//System.out.println("incomplete column!");
				continue;
			}
			// Error handling : empty string/white space
			if(isIncomplete(array)){
				//System.out.println("incomplete field!");
				continue;
			}
			
			long violationID;
			String vehicleID;
			String time;
			int zipCode;
			String description;
			int fine;
			String state; 
			
			try {
				time = array[0];
				fine = Integer.parseInt(array[1]);
				description = array[2];
				vehicleID = array[3];
				state = array[4];
				
				violationID = Long.parseLong(array[5]);
				zipCode = Integer.parseInt(array[6]);
				
			}
			catch(ClassCastException e) {
				System.out.println("ClassCastException!");
				continue;
				
			}
			
			catch(NumberFormatException e) {
				System.out.println("NumberFormatException!");
				continue;
			}
			
			ParkingViolation pkv = new ParkingViolation(time, fine, description, vehicleID, state, 
					violationID, zipCode);
			violations.add(pkv);
		
			
		}
		
		s.close();
		return violations;
	}
	
	// helper method: checking if any missing fields in the data
	private boolean isIncomplete(String[] array) {
		for(int i = 0; i< array.length; i++) {
			if(array[i] == null || array[i].length() == 0) {
				return true;
			}
		}
		return false;
	} 
	
	
	//test
	
//	public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
//
//		//parking.json
//		String filename = "parking.csv";
//		ParkingViolationFileReader pr = new ParkingViolationCSVReader(filename);		
//		ArrayList<ParkingViolation>  violations = pr.ReadFromFile();
//		System.out.println("size: "+ violations.size());		
//
//		for(int i = 0 ; i < violations.size(); i++) {
//			System.out.println(violations.get(i));
//		}
//
//	}
	
}
