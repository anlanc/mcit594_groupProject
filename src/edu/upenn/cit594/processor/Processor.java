package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolation;

public class Processor {
    
    Map<Integer, ArrayList<ParkingViolation>> prkMap = new Map<Integer, ArrayList<ParkingViolation>>();
    Map<Integer, ArrayList<PropertyValue>> pptMap = new Map<Integer, ArrayList<ParkingViolation>>();
    Map<Integer, ArrayList<Population>> popMap = new Map<Integer, ArrayList<ParkingViolation>>();
    
    // 1. Private constructor 
    private Processor() {	
    }
    
    // 2. Singleton instance
    private static Processor instance = new Processor();
    
    // 3. Singleton accessor method 
    public static Processor getInstance() {
	return instance;
    }   
    
    public boolean zipcodePA(int zipcode) {
	// if the list of XX contains zip code
	return true;
    }
    
    // Q1-Q5
    public int getSumPop() {
	return 0;
    }
    
    public TreeMap<Integer, Double> getTotalFinesperCap(){
	TreeMap<Integer, Double> answer = new TreeMap<>();
	return answer;
    }
    
    public int getAveResMV(int zipCode) {
	return 0;
    }    
    
    public int getAveResTLA(int zipCode) {
	return 0;
    }
    
    public int getTotalResMVperCap(int zipCode) {
	return 0;
    }
    
    /*
     * Rank of total fines per cap vs Rank of AveResMV  
     * Reason : see if parking fine is positively correlated to housing affordabiity  
     */
    public TreeMap<Double, Integer> getRank() {
	ArrayList<Integer> sortByHousingAffordability;
	ArrayList<Integer> sortByFinePerTicket;
	
	
	
	
	TreeMap<Double, Integer> rankHousingAffordability; // MV per square feet, ZipCode 
	TreeMap<Double, Integer> rankAvgFine; // Avg Fine per ticket, ZipCode
	
	Double key = (Double) rankHousingAffordability.keySet().toArray()[0];
	
	Integer value = map.get(key);
	System.out.println("Key: " + key + "\nValue: " + value);
	
    }

    
    
        
}
