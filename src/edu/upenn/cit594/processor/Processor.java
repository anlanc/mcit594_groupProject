package edu.upenn.cit594.processor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.json.simple.parser.ParseException;

import apple.laf.JRSUIConstants.Size;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Population;
import edu.upenn.cit594.data.PropertyValue;
import edu.upenn.cit594.datamanagement.ParkingViolationCSVReader;
import edu.upenn.cit594.datamanagement.ParkingViolationFileReader;
import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.datamanagement.PropertyValueFileReader;

public class Processor {

	public PopulationFileReader popFileReader;
	public ParkingViolationFileReader prkFileReader;
	public PropertyValueFileReader pptFileReader;
	
	private ArrayList<Population> pops;
	private ArrayList<ParkingViolation> violations;
	private ArrayList<PropertyValue> values;
	
	public Map<Integer, Integer> popMap;
	public Map<Integer, List<ParkingViolation>> prkMap;
	public Map<Integer, List<PropertyValue>> pptMap;
	
	/* memorization map */
	public Map<Integer, Double> tempRecordMap;
	public Map<Integer, Double> mvRecordMap;
	
	/* Rank */
	public Map<Integer, Double> prkFineTotal;
	public String[] rankedZip;
	
	public Processor(PopulationFileReader popFileReader, ParkingViolationFileReader prkFileReader,
			PropertyValueFileReader pptFileReader) throws FileNotFoundException, ParseException, IOException {
		// parsing the reader
		this.popFileReader = popFileReader;
		this.prkFileReader = prkFileReader;
		this.pptFileReader = pptFileReader;
		
		// invoke reader read data and store in private lists
		pops = popFileReader.ReadFromFile();
		violations = prkFileReader.ReadFromFile();
		values = pptFileReader.ReadFromFile();

		// initialize the map for data process/filtering
		popMap = new HashMap<Integer, Integer>();
		prkMap = new HashMap<Integer, List<ParkingViolation>>();
		pptMap = new HashMap<Integer, List<PropertyValue>>();
		
		/* memorization map and list */
		tempRecordMap = new HashMap<>();
		mvRecordMap = new HashMap<>();
		prkFineTotal = new HashMap<>();
				
		/* process */	
		processPop(pops);
		processPpt(values);
		processPrk(violations);
		rankedZip = new String[11];
		rank();
	}
	
	/* Processing/Filtering data sore in MAP */

	/** This method process the List of Population 
	 ** Store in/Update the popMap w/ key:zipcode, values:population
	 * @param pops
	 * @return 
	 */
	private void processPop(ArrayList<Population> pops){		
		if(pops == null || pops.isEmpty()) {
			System.out.println("No raw data in the list to process, popMap not udpated!");
			return;
		}
		for(Population pop : pops) {
			//suppose no duplicate zipcode in raw data
			popMap.put(pop.getZipCode(), pop.getPopulation());
		}
	}
	
	/** This method process the List of ParkingViolation 
	 ** Store in/Update the prkMap w/ key:zipcode, values:List<ParkingViolation>
	 * @param violations
	 * @return 
	 */
	private void processPrk(ArrayList<ParkingViolation> violations){		
		if(violations == null || violations.isEmpty()) {
			System.out.println("No raw data in the list to process, prkMap not udpated!");
			return;
		}
		
		// loop over each ParkingViolation 
		for(ParkingViolation prk : violations) {
			// Note: deal with duplicate zipcode
			// Note: ignore the # of digits as long as it's an int
			
			// key: zip
			// value: violationsInZip
			int zip = prk.getZipCode();			
			if(!prkMap.containsKey(zip)) {// if key not found
				// create a list to store violations in the zipCode
				ArrayList<ParkingViolation> violationsInZip = new ArrayList<>();
				violationsInZip.add(prk);
				prkMap.put(zip, violationsInZip);
				prkFineTotal.put(zip, prk.getFine()*1.0);
			}
			else { // if key exist
				// update the corresponding list
				prkMap.get(zip).add(prk);			
				double newSubTotal = prkFineTotal.get(zip) + prk.getFine();
				prkFineTotal.put(zip, newSubTotal);
			}						
		}
	}
	
	
	// * Note: code copied almost exact same as processPrk 
	// * Note: possible Refactor per use later
	/** This method process the List of PropertyValue 
	 ** Store in/Update the pptMap w/ key:zipcode, values:List<PropertyValue>
	 * @param values
	 * @return 
	 */
	private void processPpt(ArrayList<PropertyValue> values){	
		
		if(values == null || values.isEmpty()) {
			System.out.println("No raw data in the list to process, PptMap not udpated!");
			return;
		}
		
		// loop over each PropertyValue 
		for(PropertyValue ppt : values) {
			// Note: deal with duplicate zipcode
			// Note: ignore the # of digits as long as it's an int
			
			// key: zip
			// value: valuesInZip
			int zip = ppt.getZipCode();	
			if(!pptMap.containsKey(zip)) {// if key not found
				// create a list to store values in this zipCode
				ArrayList<PropertyValue> valuesInZip = new ArrayList<>();
				valuesInZip.add(ppt);
				pptMap.put(zip, valuesInZip);
			}
			else { // if key exist
				// update the correspond list
				pptMap.get(zip).add(ppt);			
				
			}						
		}		
	}	
	
	
	// Q1-Q5
//	+ getSumPop(): int
//	+ getTotalFinesperCap(): Map<zipCode, double> // TreeMap for order
//	    // truncated result 4 digits
//	+ getAveResMV(zipCode) : int    // truncated integer (not rounded)
//	+ getAveResTLA(zipCode) : int   // truncated integer (not rounded)
//	+ getTotalResMVperCap()(zipCode) : int  // truncated integer (not rounded)	

	/**
	 * Solution to Q1
	 * 
	 * @param 
	 * @return the total populations
	 * return -1 if popMap is null
	 */
	public int getSumPop() {
		// populate the popMap if haven't
		if(popMap == null || popMap.isEmpty()) {
			processPop(pops);					
		}
		int sum = 0;
		if(popMap.size() != 0) {
			for(Integer v : popMap.values()) {
				sum += v;
			}
		}
		return sum;
	}
	
	/**
	 * Solution to Q2
	 * if the prkMap is empty populate the map first
	 * @param 
	 * @return the a map, key: zip code; value: total fines at this zip/populations at this zip
	 * @return null if either of the map is empty after processing data
	 */
	public TreeMap<Integer, Double> getTotalFinesperCap(){
		
		// populate the popMap if haven't
		if(popMap == null || popMap.isEmpty()) {
			processPop(pops);					
		}
		// populate the prkMap if haven't
		if(prkMap == null || prkMap.isEmpty()) {
			processPrk(violations);
		}
		
//		System.out.println("popMap.size() = " + popMap.size());
//		System.out.println("prkMap.size() = " + prkMap.size());
		if(popMap.size() == 0 || prkMap.size() == 0) return null;
		
		Map<Integer, Double> totalFinesperCap = new HashMap<>();
		// loop over prkMap.keySet()
		for(Integer zip: prkMap.keySet()) {
			// get total fines in this zipCode area
			int totalFineAtZip = getTotalFinesAtZip(zip, prkMap.get(zip));
			// proceed when the fines larger than 0	
			if(totalFineAtZip > 0) {			
				// skip this zip code, if no population data can be found or the population is 0
				if(popMap.get(zip) == null ||popMap.get(zip) == 0) {
					continue;				
				}
				else {
					int population = popMap.get(zip);					
//					totalFinesperCap.put(zip,(Double.valueOf(totalFineAtZip))/population);
					double d = truncFourDigits((Double.valueOf(totalFineAtZip))/population);
					totalFinesperCap.put(zip,d);
				}				
			}
		}
		return new TreeMap<Integer, Double>(totalFinesperCap);
	}
	
	/**
	 * helper method for Q2, getTotalFinesperCap()
	 * ignore state that is not in PA
	 * @param zipCode
	 * @param prkMap
	 * @return total fines at the given zip, return -1 if zipCode is invalid
	 *  
	 */
	private int getTotalFinesAtZip(int zipCode,  List<ParkingViolation> violationsAtZip) {
		if(zipCode < 9999) { // have to be 5 digits to be considered valid
			System.out.println("Error in getTotalFinesAtZip(): invalid zip code");
			return -1 ;
		}
		if(violationsAtZip == null || violationsAtZip.isEmpty()) {
			System.out.println("Error in getTotalFinesAtZip(): invalid list violationsAtZip");
			return -1;
		}
		int totalFines = 0;		
		for(ParkingViolation pv : violationsAtZip ) {
			if(pv.getState().equals("PA")) {
				totalFines += pv.getFine();
			}
//			else {// assume zip code is correctly corresponds to state, 
//				// if one is outside of PA, the rest of the violations (at this zip), in the list can be discarded				
//				break;
//			}
		}				
		return totalFines;
	}
	
	/**
	 * helper method for Q2, truncate(not rounded) 
	 * @param d
	 * @return a double with precision of 4 digits, add trailing zeros if needed
	 */
	private double truncFourDigits(double d) {
		int temp = (int) (d * 10000);
		return (temp * 1.0)/10000;
	}

	/**
	 * Solution to Q3
	 * @param zipCode
	 * @return average residential market value
	 */
	public int getAveResMV(int zipCode) {
		mvRecordMap.putAll(tempRecordMap); // memorization implemented, total market values store for Q5
		tempRecordMap.clear(); // clear temp map for future use
		return calAveRes(zipCode, new MVRetriever());
	}
	
	
	/**
	 * Solution to Q4
	 * @param zipCode
	 * @return average residential total livable area
	 */
	public int getAveResTLA(int zipCode) {	
		return calAveRes(zipCode, new TLARetriever());
	}
	/**
	 * helper method for Q3, Q4: wrapper of calSumRes(); 
	 * calculate average residential data per zipcode, 
	 * @param zipCode
	 * @param dataRetriever
	 * @return average residential data (market value/total livable area) at given zip code
	 * @return 0 if zipcode is invalid or pptMap is null (where no propertyValue parsed in)
	 */
	private int calAveRes(int zipCode, DATARetriever dataRetriever) {
		double sum = calSumRes(zipCode, dataRetriever);
		// truncated to an int
		return (int)(sum/pptMap.get(zipCode).size());
		
	}
	
	/**
	 * helper method for Q3, Q4, Q5: Strategy Design Pattern implemented
	 * calculate total residential data per zipcode
	 * @param zipCode
	 * @param dataRetriever
	 * @return sum of residential data (market value/total livable area) at given zip code
	 * @return 0 if zipcode is invalid or pptMap is null (where no propertyValue parsed in)
	 */
	private double calSumRes(int zipCode, DATARetriever dataRetriever) {
		
		// populate the pptMap if haven't
		if(pptMap == null || pptMap.isEmpty()) {
			processPpt(values);
		}
		// not valid zip code
		if(!pptMap.containsKey(zipCode)) {
			return 0;		}
		// no property values under this zipcode
		if(pptMap.get(zipCode) == null || pptMap.get(zipCode).size() == 0) {
			System.out.println("Error: pptMap is null or pptList is empty");
			return 0 ;		}
		
		List<PropertyValue> pptList = pptMap.get(zipCode);	
//		System.out.println( "pptList.size()= " +  pptList.size());
		
		double sum = 0;
		/* memorization */
		// keep an key: zipcode - value:sum RecordMap
		tempRecordMap.clear();
		if(tempRecordMap.containsKey(zipCode)) {
			sum = tempRecordMap.get(zipCode);
		}
		else {
			for(PropertyValue ppt : pptList) {
				if(dataRetriever.getData(ppt) < 0) continue;
				sum += dataRetriever.getData(ppt);
			}
			tempRecordMap.put(zipCode, sum);
		}
		return sum;
		
	}
	
	/**
	 * Solution to Q5
	 * @param zipCode
	 * @return total res market value per cap at this zipcode
	 */
	public int getTotalResMVperCap(int zipCode) {
		double sum = 0;
		if(mvRecordMap.containsKey(zipCode)){
			sum = mvRecordMap.get(zipCode);
		}
		else {
			sum = calSumRes(zipCode, new MVRetriever());
			mvRecordMap.put(zipCode, sum);
		}
		if(popMap == null || popMap.isEmpty()) {
			processPop(pops);
		}
		if((!popMap.containsKey(zipCode)) || popMap.get(zipCode) == 0) {
			return 0;
		}
		
		return (int)(sum/popMap.get(zipCode));
	}

	
	/**
	 * Solution to Q6
	 * @return Rank of total fines per cap vs Rank of AveResMV   
	 */

	public String[] getRank() {
	    rank();
	    return rankedZip;
	}
	
	/**
	 * Helper method for Q6;
	 */
	private void rank() {	
	    ArrayList<Integer> sortByHousingAffordability = new ArrayList<Integer>(pptMap.keySet());
	    ArrayList<Integer> sortByAvgFine = new ArrayList<Integer>(pptMap.keySet());
	    
	    Collections.sort(sortByHousingAffordability, new HousingAffordanilityCompare());
	    Collections.sort(sortByAvgFine, new AverageFineCompare());
	    
	    rankedZip[0]="Housing Affordability Rank   "+"Zip Code"+"\t"+"Avg Fine Rank";
	    for (int i = 1; i < 11 ; i++) {
		int zipcode = sortByHousingAffordability.get(i-1);
		rankedZip[i] = ""+i+"\t\t\t     "+zipcode+"\t"+(1+sortByAvgFine.indexOf(zipcode));
	    }	
	}

	    /* 	q6 helper, used to sort zipcode by house affordability */	
	    class HousingAffordanilityCompare implements Comparator<Integer> {
		    public int compare(Integer zip1, Integer zip2) {
			int ha1 = 0, ha2 = 0;
			if (getAveResTLA(zip1)!=0) ha1 = getAveResMV(zip1)/getAveResTLA(zip1);
			if (getAveResTLA(zip2)!=0) ha2 = getAveResMV(zip2)/getAveResTLA(zip2);
			return ha1-ha2;
		    }
		}
	    
	    /* 	q6 helper, used to sort zipcode by average fine*/
	    class AverageFineCompare implements Comparator<Integer> {
		    public int compare(Integer zip1, Integer zip2) {
			double fpp1 = 0, fpp2 = 0;			
			
			if ((prkMap.containsKey(zip1)==false) | (prkFineTotal.containsKey(zip1)==false)) fpp1 = 0;
			else if (prkMap.get(zip1).size()!=0) fpp1 = 0;
			else fpp1 = prkFineTotal.get(zip1)/prkMap.get(zip1).size(); 			
			
			if ((prkMap.containsKey(zip1)==false) | (prkFineTotal.containsKey(zip1)==false)) fpp1 = 0;
			else if (prkMap.get(zip1).size()!=0) fpp1 = 0;
			else fpp1 = prkFineTotal.get(zip1)/prkMap.get(zip1).size(); 	
			
		        return (int) (fpp1 - fpp2);
		    }
	    }
	    
	/* Helper for ui */
	public boolean zipcodePA(int zipcode) {
		return popMap.keySet().contains((Integer)zipcode);		    
	}
	//	test
//	public static void main(String[] args) throws FileNotFoundException, ParseException, IOException {
//		// TODO Auto-generated method stub
//		
//		PopulationFileReader popRd = new PopulationFileReader("population.txt");
//		ParkingViolationFileReader prkRd = new ParkingViolationCSVReader("parking.csv");
//		//ParkingViolationFileReader prkvrd = new ParkingViolationJSONReader("parking.json");
//		PropertyValueFileReader pptRd = new PropertyValueFileReader("properties.csv");
//		
//		
//		Processor processor = new Processor(popRd,prkRd,pptRd);
//		// print Q1
//		System.out.println(processor.getSumPop());
//	
//		// print Q2
//		TreeMap<Integer, Double> sortedQ2Map= processor.getTotalFinesperCap();
//		System.out.println("Q2: sortedQ2Map.size()= " + sortedQ2Map.size());
////		for(Integer zip  : sortedQ2Map.keySet()) {			
////			System.out.println(zip + " " + sortedQ2Map.get(zip));			
////		}
//
//		// print Q3: takes in zip code : 19131
//		System.out.println("Q3: getAveResMV(19131) --- " + processor.getAveResMV(19131));
//		// print Q4: takes in zip code : 19131
//		System.out.println("Q4: getAveResTLA(19131) --- " + processor.getAveResTLA(19131));
//		// print Q5: takes in zip code : 19131
//		System.out.println("Q5: getTotalResMVperCap(19131) --- " + processor.getTotalResMVperCap(19131));
//		
//		
//	}

}
