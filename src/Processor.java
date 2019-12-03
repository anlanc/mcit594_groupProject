import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.json.simple.parser.ParseException;
/**
 * @author anlanchen
 *
 */
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
	
	
	public Processor(PopulationFileReader popFileReader, ParkingViolationFileReader prkFileReader,
			PropertyValueFileReader pptFileReader) throws FileNotFoundException, ParseException, IOException {
		
		this.popFileReader = popFileReader;
		this.prkFileReader = prkFileReader;
		this.pptFileReader = pptFileReader;
		
		// read data store in private lists
		pops = popFileReader.ReadFromFile();
		violations = prkFileReader.ReadFromFile();
		values = pptFileReader.ReadFromFile();
		
		popMap = new HashMap<Integer, Integer>();
		prkMap = new HashMap<Integer, List<ParkingViolation>>();
		pptMap = new HashMap<Integer, List<PropertyValue>>();
		
	}
	
	/** This method process the List of Population 
	 ** Store in/Update the popMap
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
	 ** Store in/Update the prkMap
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
			}
			else { // if key exist
				// update the correspond list
				prkMap.get(zip).add(prk);			
				
			}						
		}
	}
	
	
	// * Note: code copied almost exact same as processPrk 
	// * Note: possible Refactor later 
	/** This method process the List of PropertyValue 
	 ** Store in/Update the pptMap
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
	 * if the popMap is empty populate the map first
	 * @param 
	 * @return the total populations
	 * return -1 if popMap is null
	 */
	public int getSumPop() {
		if(popMap == null) {
			System.out.println("Error: popMap is null");
			return -1;
		}
		// populate the popMap first
		if(popMap.isEmpty()) {
			processPop(pops);					
		}
		int sum = 0;
		for(Integer v : popMap.values()) {
			sum += v;
		}
		return sum;
	}
	
	/**
	 * Solution to Q2
	 * if the prkMap is empty populate the map first
	 * @param 
	 * @return the total populations
	 * return -1 if popMap is null
	 */
	public TreeMap<Integer, Double> getTotalFinesperCap(){
		Map<Integer, Double> totalFinesperCapinZip = new HashMap<>();
		if(prkMap == null) {
			System.out.println("Error: prkMap is null");
			return null;//return null
		}
		// populate the popMap if haven't
		if(popMap.isEmpty()) {
			processPop(pops);					
		}
		// populate the prkMap if haven't
		if(prkMap.isEmpty()) {
			processPrk(violations);
		}
		
		// step1 getTotalFines in one zipCode area
		// loop over prkMap.keySet()
		for(Integer zip: prkMap.keySet()) {
			//int zip;
			int totalFineAtZip = getTotalFinesAtZip(zip, prkMap.get(zip));
			if(totalFineAtZip > 0) {
				
				if(popMap.get(zip) == null) {
					continue;
					
				}
				else {
					int popAtZip = popMap.get(zip);
					totalFinesperCapinZip.put(zip,(Double.valueOf(totalFineAtZip))/popAtZip);
				}
				
				
			}
		}
		// call getTotalFinesAtZip(), if > 0, consider valid, passed into map: totalFinesperCapinZip
		return new TreeMap<Integer, Double>(totalFinesperCapinZip);
	}
	
	/**
	 * helper method for Q2, getTotalFinesperCap()
	 * @param zipCode
	 * @param prkMap
	 * @return total fines at the given zip
	 */
	private int getTotalFinesAtZip(int zipCode,  List<ParkingViolation> violationsAtZip) {
		if(zipCode < 9999) { // have to be 5 digits
			System.out.println("Error in getTotalFinesAtZip(): invalid zip code");
			return -1 ;
		}
		if(violationsAtZip == null || violationsAtZip.isEmpty()) {
			System.out.println("Error in getTotalFinesAtZip(): invalid list violationsAtZip");
			return -1;
		}
		int totalFines = 0;		
		for(ParkingViolation pv : violationsAtZip ) {
			totalFines += pv.getFine();
		}
				
		return totalFines;
	}

//	test
	public static void main(String[] args) throws FileNotFoundException, ParseException, IOException {
		// TODO Auto-generated method stub
		
		PopulationFileReader popRd = new PopulationFileReader("population.txt");
		ParkingViolationFileReader prkRd = new ParkingViolationCSVReader("parking.csv");
		//ParkingViolationFileReader prkvrd = new ParkingViolationJSONReader("parking.json");
		PropertyValueFileReader pptRd = new PropertyValueFileReader("properties.csv");
		
		
		Processor processor = new Processor(popRd,prkRd,pptRd);
		// print Q1
		System.out.println(processor.getSumPop());
		
		// print Q2
		TreeMap<Integer, Double> sortedQ2Map= processor.getTotalFinesperCap();
		for(Integer zip  : sortedQ2Map.keySet()) {			
			System.out.println(zip + " " + sortedQ2Map.get(zip));			
		}
		
		
	}

}
