package edu.upenn.cit594;
/* 1. reading the runtime arguments / handle input through command
 * 2. creating all the objects, arranging the dependencies between modules
 */
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
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
	
	// get an instance of UserInput, Reader, Processor, Presenter and logger 
	UserInput uI = UserInput.getInstance();
	Reader read = Reader.getInstance(); 
	Processor ps = Processor.getInstance();	
	Logger l = Logger.getInstance();
	Presenter p = Presenter.getInstance();
	
	// While loop to receive instructions and act upon.	
	Boolean running = uI.checkInput(args);
	int currentChoice = uI.getCurrentChoice();
	int currentZip = uI.getCurrentZip();
	while(running) {
	    ps.Answer(currentChoice, currentZip);
	    running = uI.checkInput();
	}
	
	// Outputs
	l.generateFile(); // TODO : update to what this HW is 	
	p.printSummary(); // TODO : update to what this HW is
	 
	
    }

}
