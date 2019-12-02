package edu.upenn.cit594;
/* 1. reading the runtime arguments / handle input through command
 * 2. creating all the objects, arranging the dependencies between modules
 */
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.ui.Presenter;
import edu.upenn.cit594.ui.UserInput;

public class Main {
    
    public static void main(String[] args) {
	
	/* Note : For test purpose + copy and paste this to command / program arguments 
	String[] args1 = {"CSV","parking.csv","properties.csv","population.txt","log.txt"};  
	args = args1;
	String[] args2 = {"JSON","parking.json","properties.csv","population.txt","log.txt"};
	args = args2;
	*/
	
	// get an instance of FileReader 
	Reader r = Reader.getInstance(); 
	
	// get an instance of UserInput  
	UserInput aV = UserInput.getInstance();
	aV.checkInput(args); // Validae
	
	// Initialize the scanner for system input
	Boolean running = true;
	
	// get an instance of Logger
	Logger l = Logger.getInstance();
	l.generateFile(); // TODO : update to what this HW is 
	
	// get an instance of Presenter
	Presenter p = Presenter.getInstance();
	p.printStateSummary();
	
	// Getting user input 
	
    }

}
