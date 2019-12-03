package edu.upenn.cit594.processor;

import java.awt.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.sun.nio.zipfs.ZipDirectoryStream;
import com.sun.org.glassfish.external.statistics.AverageRangeStatistic;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.PropertyValue;

public class Processor {
    
    Map<Integer, ArrayList<ParkingViolation>> prkMap = new Map<Integer, ArrayList<ParkingViolation>>();
    Map<Integer, ArrayList<PropertyValue>> pptMap = new Map<Integer, ArrayList<PropertyValue>>();
    Map<Integer, ArrayList<Population>> popMap = new Map<Integer, ArrayList<Population>>();
    //	Q6 : 
    Map<Integer, Double> prkFine = new Map<Integer, Double>();
    Map<Integer, Double> prkFine = new Map<Integer, Double>();
    String[] rankedZip = new String[11];
    
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
    
    public String[] getRank() {
	return rankedZip;
    }
    
    private void rank() {	
	ArrayList<Integer> sortByHousingAffordability = new ArrayList<Integer>(pptMap.keySet());
	ArrayList<Integer> sortByAvgFine = new ArrayList<Integer>(pptMap.keySet());
	sortByHousingAffordability.sort(
		(zip1, zip2) -> {
		    int ha1 = getAveResMV(zip1)/getAveResTLA(zip1);
		    int ha2 = getAveResMV(zip2)/getAveResTLA(zip2);
	                return ha1-ha2;
	        }
	);	
	sortByAvgFine.sort(
		(zip1, zip2) -> {
		    double fpp1 = prkFine.get(zip1)/prkMap.get(zip1).size();
		    double fpp2 = prkFine.get(zip2)/prkMap.get(zip2).size();
	            return (int) (fpp1 - fpp2);
	        }
	);	
	rankedZip[0]="Rank of Housing Affordability"+"\t"+"Zip Code"+"\t"+"Rank of Avg Fine";
	for (int i = 1; i < 12 ; i++) {
	    int zipcode = sortByHousingAffordability.get(i);
	    rankedZip[0] = ""+i+"\t\t\t\t"+zipcode+"\t\t"+sortByAvgFine.indexOf(zipcode);
	}	
    }
}    
    
