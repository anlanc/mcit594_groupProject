package edu.upenn.cit594.ui;
import java.io.File;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

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
    private int choice;
    private int zip;
    private Processor p;
    public String outputName;
/*
 * The program should then prompt the user to specify the action to be performed.
 */
    
    /* 1. Private constructor */
    public UserInput() {
	in = new Scanner(System.in);
    }
    
    public void setProcessor(Processor p) {
	this.p = p;
    }
    
    /* 2. User inputs checking, overloaded with String[] and null; */
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
	consoleCheck();
	return true;
    }
    
    /* 3. Answer questions asked by user */
    public void answer() {
	int zip = getZip();
	int q = getChoice();
	
	switch (q) {
	case 1:	    
	    System.out.println(p.getSumPop());
	    break;
	case 2:	    
	    TreeMap<Integer, Double> totalFinesPerCap = p.getTotalFinesperCap();
	    Set<Integer> zipcodes = totalFinesPerCap.keySet();
	    for (Iterator<Integer> i = zipcodes.iterator(); i.hasNext();) {
		Integer zipC = (Integer) i.next();
		System.out.printf("%s %.4f\n",zipC,totalFinesPerCap.get(zipC));
	    }
	    break;
	case 3:
	    System.out.println(p.getAveResMV(zip));
	    break;
	case 4:
	    System.out.println(p.getAveResTLA(zip));
	    break;
	case 5:
	    System.out.println(p.getTotalResMVperCap(zip));
	    break;	    
	case 6:
	    String[] rank = p.getRank();
	    for (int i = 0 ; i < rank.length ; i++) {
		System.out.println(rank[i]);
	    }
	    break;
	}
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
	if ((fileType.compareTo("json")!=0)&&(fileType.compareTo("csv")!=0)) {
	    return "The first argument is neither “csv” or “json” (case-sensitive).";
	}	
	/* File existence testing and open-able */	
	File f; // TODO : potential optimization area 
	String filename = runTimeArgs[1];
	for (int i = 1 ; i < 4 ; i++) {
	    filename = runTimeArgs[i];
	    f = new File(filename);
	    if (!f.exists()) return "The specified input files "+filename+" do not exist.";
	    if (!f.canRead()) return "The specified input files "+filename+" cannot be opened for reading (e.g. because of file permissions).";
	}	
	String logType = runTimeArgs[4];
	logType = logType.substring(logType.indexOf('.'),logType.length());
	if (logType.toLowerCase().compareTo(".txt")!=0) return "The specified output file type is invalid.\n";
	return "Good";
    }
    
    /* H2 - Console input check */ 
    private void consoleCheck() {
	Logger l = Logger.getInstance();
	boolean running = true;
	do {
	    System.out.println("\nPlease specify the action to be performed by typing a single digit number range from 0 to 6 :");
	    if (in.hasNextInt()) {	    
    		choice = in.nextInt();
    		
    		if (choice == 0) {
    		    l.trackUI("0");
    		    break;
    		}
    		
    		else if ((choice<0)|(choice>7)) {
    		    System.out.println("Invalid input please input a single digit number range from 0 to 6.");
    		} 
    		
    		// Where zip code needed 
    		
    		else if ((choice>=3)&(choice<6)){
    		    boolean zipValid = false;
    		    do {
    			System.out.println("For #"+choice+" , please provide the five digits zip code in PA to look up for :");
    			if (in.hasNextInt()) {
    			    zip = in.nextInt();
    			    if (p.zipcodePA(zip)) {
    				l.trackUI(choice, zip);
    				answer();
    				break;
    			    }
    			    else {
    				if (choice!=6) l.trackUI(choice, zip);
    				System.out.print("Invalid zipcode. ");
    			    }
    			} else {
    			    l.trackUI(in.next());
    			    System.out.print("Invalid zipcode. ");
    			}    			
    		    } while (!zipValid);
    	    	}    		
    		else answer();
    	    } else {
    		System.out.print("Invalid input. ");
    		l.trackUI(in.next());
    	    }
	} while (running) ;
    }
	
    /* H3 - Print welcome message */ 
    private void welcomeMessage() {
	System.out.println("Welcome! Please specify the action to be performed by typing a single digit number range from 0 to 6 :");
	System.out.println("\t0 - exit;");
	System.out.println("\t1 - total population for all ZIP Codes;");
	System.out.println("\t2 - total parking fines per capita for each ZIP Code;");
	System.out.println("\t3 - average market value for residences in a specified ZIP Code;");
	System.out.println("\t4 - total livable area for residences in a specified ZIP Code;");
	System.out.println("\t5 - total residential market value per capita for a specified ZIP Code;");
	System.out.println("\t6 - rank of parking fines per capita and rank of avergae market value of a specified ZIP Code;");
    }
    
    /* H4 - Getter for the current choice */
    public int getChoice() {
	return choice;
    }

    /* H5 - Getter for the current zip code */
    public int getZip() {
	return zip;
    }

}
