package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UserInput;

/* 1. Write to a log file : In the Singleton pattern, you shouldn't be able to pass arguments directly to the constructor. 
 * A few ways around it include 1) initializing a default value and calling a set method to update the default before 
 * calling getInstance() where the new Logger object is created if it hasn't already  
 */

public class Logger {
    String fileName;
    File log;
    FileWriter fw;
    PrintWriter pw;
    UserInput ui;
    Processor pr;
    
    /* Singleton */
    private Logger() {
    }

    private static Logger instance = new Logger();

    public static Logger getInstance() {
	return instance;
    }

    /* Set fileName */
    public void setFileName(String fileName) {
	this.fileName = fileName;

    }

    /*
     * Record the user inputs and activities by writing to the log file that was
     * specified as a runtime argument to the program.
     *  
     * If the log file does not exist, the program should create a new file; if it
     * already exists, the program should append new data to it as described below.
     */

    public void generateFile(String[] args) {
	try {
	    log = new File(fileName);	    
	    if (log.exists()) {
		fw = new FileWriter(log, true);
	    } else {
		fw = new FileWriter(log);
	    }
	    pw = new PrintWriter(fw);
	    pw.print(System.currentTimeMillis());
	    for (int i = 0 ; i < args.length ; i++ ) {
		pw.print(" "+args[i]);
	    }
	    pw.println();
	} catch (IOException e) {
	    e.printStackTrace();
	    System.out.println("Could not write the File out. Please check system permissions."); //TODO: don't seem to be robust enough
	}
    }

    /*     
     * Whenever an input file is opened for reading, the program should write the
     * current time and the name of the file to the log file.
     */
    public void trackRead(String fName) {
	 pw.print(System.currentTimeMillis());
	 pw.println(" "+fName);
    }
     
    /*
     * When the user makes a choice from the prompt in Step #0, the program should
     * write the current time and the user’s selection to the log file. If the user
     * enters a ZIP Code in Step #3, 4, or 5, the program should write the current
     * time and the specified ZIP Code to the log file.
     */
    public void trackUI(int choice, int zip) {
	pw.print(System.currentTimeMillis()+" ");
	pw.print(choice+" ");
	if (zip!=99999) {
	    pw.println(zip+"");
	}
    }
    
    public void trackUI(String invalidInput) {
	pw.print(System.currentTimeMillis()+" ");
	pw.println(invalidInput+" \n");
    }

    /* close the file writer and print writer */
    public void done() {
	pw.close();
	try {
	    fw.close();
	} catch (IOException e) {
	    e.printStackTrace(); //TODO : need error handling better than this
	}
    }

    /* close the file writer and print writer */
    public void set_Processor_UI(Processor p, UserInput ui ) {
	this.pr = p;
	this.ui = ui;
    }


}
