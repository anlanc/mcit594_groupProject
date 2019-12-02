package edu.upenn.cit594;
/* 1. reading the runtime arguments / handle input through command
 * 2. creating all the objects, arranging the dependencies between modules (“Monolith vs. Modularity”)
 */

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.ui.Presentation;
import edu.upenn.cit594.ui.UserInput;

public class Main {

    public static void main(String[] args) {
	/* Note : For test purpose + copy and paste this to command / program arguments 
	String[] args1 = {"TEXT","flu_tweets.txt","states.csv","log.txt"};
	// Command1 : TEXT flu_tweets.txt states.csv log.txt 
	String[] args2 = {"JSON","flu_tweets.json","states.csv","log.txt"};
	// Command2 : JSON flu_tweets.json states.csv log.txt
	args = args2;
	*/
	
	// validate the inputs and read files at the same time 
	UserInput aV = UserInput.getInstance();
	aV.checkInput(args);

	// get an instance of logger
	Logger l = Logger.getInstance();
	l.generateFile();
	
	// get an instance of final state summary
	Presentation p = Presentation.getInstance();
	p.printStateSummary();
    }

}
