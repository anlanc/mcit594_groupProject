package edu.upenn.cit594;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import edu.upenn.cit594.datamanagement.ParkingViolationCSVReader;
import edu.upenn.cit594.datamanagement.ParkingViolationFileReader;
import edu.upenn.cit594.datamanagement.ParkingViolationJSONReader;
import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.datamanagement.PropertyValueFileReader;
/* 1. reading the runtime arguments / handle input through command
 * 2. creating all the objects, arranging the dependencies between modules
 */
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UserInput;

public class Main {
    
    public static void main(String[] args) throws FileNotFoundException, ParseException, IOException {
	
	/* Note : For test purpose + copy and paste this to command / program arguments 
	String[] args1 = {"csv","parking.csv","properties.csv","population.txt","log.txt"};  
	args = args1;
	String[] args2 = {"json","parking.json","properties.csv","population.txt","log.txt"};
	args = args2;
	*/
	
	String[] args1 = {"csv","parking.csv","properties_s.csv","population.txt","log.txt"};  
	args = args1;
	
	// initialize ui and valid run time arguments	
	UserInput ui = new UserInput();
	Boolean running = ui.checkInput(args);	
	if (!running) return;	
	
	// initialize logger
	Logger l = Logger.getInstance();
	l.setFileName(args[4]);		
	l.generateFile(args);
	
	// initialize readers
	l.trackRead(args[3]);
	PopulationFileReader popRd = new PopulationFileReader(args[3]);	
	
	l.trackRead(args[2]);
	PropertyValueFileReader pptRd = new PropertyValueFileReader(args[2]);
	
	l.trackRead(args[1]);
	ParkingViolationFileReader prkRd;
	if (args[0].compareTo("csv")==0) {
	    prkRd = new ParkingViolationCSVReader(args[1]);
	} else {
	    prkRd = new ParkingViolationJSONReader(args[1]);
	}
	
	// initialize processor and pass back to ui
	Processor processor = new Processor(popRd,prkRd,pptRd);	
	ui.setProcessor(processor);
	ui.checkInput();
	
	// close logger before exist
	l.done();
    }
}
