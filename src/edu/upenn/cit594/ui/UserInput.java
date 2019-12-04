package edu.upenn.cit594.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

public class UserInput {
	protected Processor processor;
	public UserInput(Processor processor) {
		this.processor = processor;
		
	}
	
	/**
	 * This method start the program
	 * write to a log file
	 * and print to the console
	 * 
	 */
	public void start(String logFilename) {	
		Logger l = Logger.getInstance(logFilename);		
		l.log(); //testing, argument TBD
		
		/* Simply testing with processor function */
		// print Q1
		System.out.println(processor.getSumPop());
	
		// print Q2
		TreeMap<Integer, Double> sortedQ2Map= processor.getTotalFinesperCap();
		System.out.println("Q2: sortedQ2Map.size()= " + sortedQ2Map.size());
//		for(Integer zip  : sortedQ2Map.keySet()) {			
//			System.out.println(zip + " " + sortedQ2Map.get(zip));			
//		}

		// print Q3: takes in zip code : 19131
		System.out.println("Q3: getAveResMV(19131) --- " + processor.getAveResMV(19131));
		// print Q4: takes in zip code : 19131
		System.out.println("Q4: getAveResTLA(19131) --- " + processor.getAveResTLA(19131));
		// print Q5: takes in zip code : 19131
		System.out.println("Q5: getTotalResMVperCap(19131) --- " + processor.getTotalResMVperCap(19131));

	}
}
