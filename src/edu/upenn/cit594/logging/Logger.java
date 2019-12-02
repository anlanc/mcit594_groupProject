package edu.upenn.cit594.logging;

/* 1. Write to a log file : In the Singleton pattern, you shouldn't be able to pass arguments directly to the constructor. 
 * A few ways around it include 1) initializing a default value and calling a set method to update the default before 
 * calling getInstance() where the new Logger object is created if it hasn't already  
 */

public class Logger {
    String fileName;

    // 1. Private constructor
    private Logger() {	
    }

    // 2. Singleton instance
    private static Logger instance = new Logger();

    // 3. Singleton accessor method
    public static Logger getInstance() {
	return instance;
    }

    // 4. set fileName
    public void setFileName(String fileNmae) {
	this.fileName = fileNmae;
    }
    
    // 5. generate log
    public void generateFile() {

    }
    
   
}
