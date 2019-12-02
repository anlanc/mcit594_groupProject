package edu.upenn.cit594.ui;
import java.io.File;
import java.util.Scanner;

/*
 * Purpose : The program should then prompt the user to specify the action to be performed.
 *  
 * Note : Error message was return directly to avoid overhead resulted from the creation of unnecessary string or error objectives.   
 * 
 * Error Messages in this class : 
 * rt_e1 = The number of runtime arguments is incorrect. Please try again.";
 * rt_e2 = The first argument is neither “csv” or “json” (case-sensitive).
 * rt_e3 = The specified input files do not exist.
 * rt_e4 = The specified input files cannot be opened for reading (e.g. because of file permissions).
 * a_e1 = Invalid input please input a single digit number range from 0 to 6. Please try again.
 */

public class UserInput {            
    private Scanner in;
    private int currentChoice;
    private int currentZip;
    
/*
 * The program should then prompt the user to specify the action to be performed.
 */
    
    /* 1. Private constructor */
    private UserInput() {
	in = new Scanner(System.in);
    }
    
    /* 2. Singleton instance */
    private static UserInput instance = new UserInput();
    
    /* 3. Singleton access-or method */
    public static UserInput getInstance() {
	return instance;
    }
    
    /* 4. User inputs checking, overloaded with String[] and null; */
    public boolean checkInput(String[] input) {	
	String test ;	
	// Check for run time errors
	test = checkArgs(input);
	if (test.compareTo("Good")!=0) {
	    System.out.println(test);
	    return false;
	}	
	// If all good, print welcome message
	welcomeMessage();
	return true;
    }
    
    public boolean checkInput() {
	return consoleCheck();
    }
    
    /* Section of helper methods */

    /* H1- Runtime arguments check */
    private String checkArgs(String[] runTimeArgs) {	
	/* Number of arguments */ 
	if (runTimeArgs.length != 5) {
	    return "The number of runtime arguments is incorrect. Please try again.";
	}	
	/* Valid file type */
	String fileType = runTimeArgs[0];
	if ((fileType.compareTo("json")!=0)|(fileType.compareTo("csv")!=0)) {
	    return "The first argument is neither “csv” or “json” (case-sensitive).";
	}	
	/* File existence testing and open-able */	
	File f; // TODO : potential optimization area 
	String filename;
	for (int i = 1 ; i < 5 ; i++) {
	    filename = runTimeArgs[i];
	    f = new File(filename);
	    if (!f.exists()) return "The specified input files "+filename+" do not exist.";
	    if (!f.canRead()) return "The specified input files "+filename+" cannot be opened for reading (e.g. because of file permissions).";
	}
	return "Good";
    }
    
    /* H2 - Console input check */ 
    private boolean consoleCheck() {
	System.out.println("Please specify the action to be performed by typing a single digit number range from 0 to 6 :");
	if (in.hasNextInt()) {
	    int choice = in.nextInt();
	    if ((choice<0)|(choice>7)) {
		System.out.println("Invalid input please input a single digit number range from 0 to 6.");
		return false ;
	    } 
	    if (choice == 0) {
		return false;
	    } else {
		currentChoice = choice;
		if ((choice==3)|(choice==4)|(choice==5)){
		    System.out.println("For # "+choice+" , please provide the five digits zip code to look up for :");
		    return validateZipCode(); 
		}
		else return true;
	    }	    
	} else return false;   
    }
	
    /* H3 - Print welcome message */ 
    private void welcomeMessage() {
	System.out.println("Welcome. Please specify the action to be performed by typing a single digit number range from 0 to 6 :");
	System.out.println("\t0 - quit;");
	System.out.println("\t1 - total population for all ZIP Codes;");
	System.out.println("\t2 - total parking fines per capita for each ZIP Code;");
	System.out.println("\t3 - average market value for residences in a specified ZIP Code;");
	System.out.println("\t4 - total livable area for residences in a specified ZIP Code;");
	System.out.println("\t5 - total residential market value per capita for a specified ZIP Code;");
	System.out.println("\t6 - ");
    }
    
    /* H4 - Getter for the current choice */
    public int getCurrentChoice() {
	return currentChoice;
    }

    /* H5 - Getter for the current zip code */
    public int getCurrentZip() {
	return currentZip;
    }
    
    /* H6 - Validate the zipCode */
    // TODO : call processor to test the zip code 
    public boolean validateZipCode() {
	// TODO : to fill in the body
	if (in.hasNextInt()) {
	    currentZip = in.nextInt();
	}
	return true;
    }
}
