package edu.upenn.cit594.datamanagement;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.ParkingViolation;

public abstract class ParkingViolationFileReader {
	public String filename;
	public ParkingViolationFileReader(String filename) {
		this.filename = filename;
	}
	
	//abstract method
	public abstract ArrayList<ParkingViolation> ReadFromFile() throws ParseException, FileNotFoundException, IOException;
	
	
}
