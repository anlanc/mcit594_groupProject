import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PopulationFileReader {
	public String filename;
	public PopulationFileReader(String filename) {
		this.filename = filename;
	}
	
	public ArrayList<Population> ReadFromFile() throws FileNotFoundException, IOException {
		ArrayList<Population> pops =  new ArrayList<Population>();
		Scanner s = new Scanner(new File(filename));
		while(s.hasNext()) {
			String line = s.nextLine();
			String[] array = line.split(" ");
			if(array.length == 2 && array[0].length() == 5 && array[1].length() > 1) {
				if(isParsable(array[0]) && isParsable(array[1])) {
					int zipCode = Integer.parseInt(array[0]);
					int population = Integer.parseInt(array[1]);					
					Population pop = new Population(zipCode, population);
					pops.add(pop);								
				}									
			}			
		}		
		s.close();
		return pops;
	}
	
	// helper method
	private boolean isParsable(String input) {
		try {
			Integer.parseInt(input);
			return true;
		}
		catch(NumberFormatException e){
			return false;
			
		}
	}
	
	
	//test
//	public static void main(String[] args) throws FileNotFoundException, IOException{
//		int sum = 0;
//		PopulationFileReader pr = new PopulationFileReader("population.txt");
//		ArrayList<Population> pops = pr.ReadFromFile();
//		for(Population pop : pops) {
//			sum += pop.getPopulation();
//		}
//		System.out.println(sum);
//		
//	}
}
