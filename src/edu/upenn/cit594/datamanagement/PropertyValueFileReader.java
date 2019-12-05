package edu.upenn.cit594.datamanagement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.upenn.cit594.data.PropertyValue;

public class PropertyValueFileReader {
	public String filename;
	public PropertyValueFileReader(String filename) {
		this.filename = filename;
	}
	
	// Note: Error handling, currently return empty list instead of null, but TBD
	public ArrayList<PropertyValue> ReadFromFile() throws FileNotFoundException, IOException{
		
		ArrayList<PropertyValue> properties =  new ArrayList<PropertyValue>();
		Scanner s = new Scanner(new File(filename));
		String[] header = null;
		
		// scan in headline
		if(s.hasNextLine()) {
			String headline = s.nextLine();
			header = headline.split(",", -1);
			
		}
//		System.out.println("header.length = " + header.length);
		
		// determine relevant index "market_value", "zip_code", "total_livable_area"
		int zip = getMatchFieldIndex("zip_code", header);
		int mv = getMatchFieldIndex("market_value", header);
		int tla = getMatchFieldIndex("total_livable_area", header);
		
		// Error handling: no expected match column found in the header line
		if(zip == -1 || mv == -1 || tla == -1) {
			System.out.println("no expected fileds in the header line!");
			s.close();
			return properties; // return an empty list
		}
//		System.out.println("zip = " + zip);
//		System.out.println("mv = " + mv);
//		System.out.println("tla = " + tla);
//		int countLine = 1;
		
		// proceed, read line by line 
		// Note: 9 seconds to read in the file currently, could improve by multi-threads later
		while(s.hasNext()) {
//			countLine++;
			String line = s.nextLine();
			
			// get the list of tokens
			ArrayList<String> tokens = properParse(line);
			// trim the extra white space for parsing
			String zip_code = tokens.get(zip).trim() ; 
			String market_value = tokens.get(mv).trim();
			String total_livable_area = tokens.get(tla).trim();
			
			int zipCode; //Attention: extract first 5 digits 
			double marketValue;
			double totalLivableArea;
			
					

			if(isIntParsable(zip_code) && isDoubleParsable(market_value) && isDoubleParsable(total_livable_area)) {
				
				try {					
					// zipCode case
					if(zip_code.length()>5) { // if longer than 5 digits
						zipCode = Integer.parseInt(zip_code.substring(0, 5));
					}
					else {// shorter than 5 digits, 0 
						zipCode = Integer.parseInt(zip_code);
					}
					 marketValue = Double.parseDouble(market_value);
					totalLivableArea = Double.parseDouble(total_livable_area);	
				}
				
				catch(NumberFormatException e) {					
					/** Debug
					System.out.println("countLine ----> " + countLine);
					System.out.println("tokens.size()  ----> " + tokens.size());
					System.out.println("array[zip]= " + tokens.get(zip));
					System.out.println("array[mv]= " + tokens.get(mv));
					System.out.println("array[tla]= " + tokens.get(tla));
					System.out.println("------------------START------------------");
					for(int i = 0 ; i< tokens.size(); i++) {
						System.out.println((i+1) + " ---> " + tokens.get(i));
					}
	
					System.out.println("------------------END------------------");
					**/ 
					continue;
				} 

					
				PropertyValue ppv = new PropertyValue(zipCode, marketValue, totalLivableArea);
				properties.add(ppv);					
					
			}
		}		
		s.close();
		
		
		return properties;
	}
	
	
	
	/** This method takes a comma-separated String (CSV line)
	 *  split token properly with commas but ignoring it when in quotes
	 * @param line
	 * @return List of String tokens
	 */
	private ArrayList<String> properParse(String line){
		ArrayList<String> tokens = new ArrayList<String>();
		boolean quoted = false;
		StringBuilder sb = new StringBuilder();
		for(char c : line.toCharArray()) {
			if(c  == '\"') {
				quoted = !quoted;
			}
			if(c == ',') {
				if(quoted) {
					sb.append(c);
				}
				else {
					tokens.add(sb.toString());
					sb = new StringBuilder();
				}
			}
			else {
				sb.append(c);
			}
		}
		tokens.add(sb.toString());
		return tokens;
		
	} 
	
	
	// helper method
	// return index of the String array, if match the given string input
	// return -1 if not found, 
	// return -1 if the input/array is null or the input array is empty
		
	private int getMatchFieldIndex(String input, String[] array) {
		if(input == null || array == null || array.length == 0) {
			return -1;
		}
				
		for(int i =0; i< array.length; i++) {
			if(input.equals(array[i])) {
				return i;
			}
		}
		return -1;
	}
	
	// helper method
	private boolean isIntParsable(String input) {
		try {
			Integer.parseInt(input);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	
	// helper method
	private boolean isDoubleParsable(String input) {
		try {
			Double.parseDouble(input);
			return true;
		}
		catch(NumberFormatException e){
			return false;
			
		}
	}
	
	
	
	
	// test
//	public static void main(String[] args) throws FileNotFoundException, IOException {
//
//		//parking.json
//		String filename = "properties.csv";
//		PropertyValueFileReader pr = new PropertyValueFileReader(filename);		
//		ArrayList<PropertyValue>  properties = pr.ReadFromFile();
//
//		System.out.println("size: "+ properties.size());		
//
////		for(int i = 0 ; i < properties.size(); i++) {
////			System.out.println(properties.get(i));
////		}
//
//	}

	
}
