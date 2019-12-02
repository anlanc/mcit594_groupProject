package edu.upenn.cit594.ui;

import java.io.IOException;
import java.util.Scanner;

import edu.upenn.cit594.logging.Logger;

public class UserInput {
            
    private Scanner in;
    // Potential runtime errors 
    String rt_e1 = "The number of runtime arguments is incorrect. Please try again.";
    String rt_e2 = "The first argument is neither “csv” or “json” (case-sensitive)";
    String rt_e3 = "The specified input files do not exist or cannot be opened for reading (e.g. because of file permissions)";     
     
    String welcome = "Welcome. Please specify the action to be performed by typing a single digit number range from 0 to 6 :";
    String action_e1 = "Invalid inpu,t please input a single digit number range from 0 to 6.";
    
/*
 * The program should then prompt the user to specify the action to be performed: 
 * 0 = quit
 * 1 = total population for all ZIP Codes. 
 * 2 = total parking fines per capita for each ZIP Code
 * 3 = average market value for residences in a specified ZIP Code
 * 4 = total livable area for residences in a specified ZIP Code
 * 5 = the total residential market value per capita for a specified ZIP Code
 * 6 = the results of your custom feature
 * Other = show an error message and terminate : such as: ● “1 2” ● “ 1” ● “4dog” ● “1.0”
 */
    
    // 1. Private constructor
    private UserInput() {
	in = new Scanner(System.in);
    }

    // 2. Singleton instance
    private static UserInput instance = new UserInput();

    // 3. Singleton accessor method
    public static UserInput getInstance() {
	return instance;
    }
    
    // 4. Read String[] and return whether the input is valid or not
    public boolean checkInput(String[] input) {	
	try {
	    // Check for e1 and e2
	    if (input.length != 4) {
		throw new IOException(e1);
	    }

	    // Check for e2
	    String tweetFileFormat = input[0].toLowerCase();
	    if ((tweetFileFormat.equals("json")) && (tweetFileFormat.equals("text"))) {
		throw new IOException(e2);
	    }
	    
	} catch (IOException e) {
	    System.out.println("Error: " + e);
	    System.exit(0);
	}
		
	// Read state file first;
	String stateFileName = input[2];
	boolean stateFileCheck = r.read("csv", stateFileName);
		
	String tweetFileFormat = input[0].toLowerCase();
	String tweetFileName = input[1];
	boolean tweetFileCheck = r.read(tweetFileFormat, tweetFileName);
	
	String logFileName = input[3];
	l.setFileName(logFileName);

	if (stateFileCheck && tweetFileCheck)
	    return true;

	return false;
    }

}
