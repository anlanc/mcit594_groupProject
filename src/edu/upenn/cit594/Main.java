package edu.upenn.cit594;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import edu.upenn.cit594.datamanagement.ParkingViolationCSVReader;
import edu.upenn.cit594.datamanagement.ParkingViolationFileReader;
import edu.upenn.cit594.datamanagement.ParkingViolationJSONReader;
import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.datamanagement.PropertyValueFileReader;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UserInput;


public class Main  {
	public static void main(String[] args) throws FileNotFoundException, ParseException, IOException {
	// simple test 
	PopulationFileReader popRd = new PopulationFileReader("population.txt");	
	ParkingViolationFileReader prkRd;
	/** if... csv, new csvReader....else.. json reader **/
	//prkRd = new ParkingViolationCSVReader("parking.csv");	
	prkRd = new ParkingViolationJSONReader("parking.json");
	PropertyValueFileReader pptRd = new PropertyValueFileReader("properties.csv");
	
	Processor processor = new Processor(popRd,prkRd,pptRd);
	UserInput ui = new UserInput(processor);
	ui.start("log.txt");


	}
}
