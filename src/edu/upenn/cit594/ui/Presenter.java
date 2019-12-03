package edu.upenn.cit594.ui;
import java.util.ArrayList;
import java.util.TreeMap;


import edu.upenn.cit594.processor.Processor;

/* 1. Handle printing the output to the screen 
 * 
 */
public class Presenter {
    ArrayList<String> impactSummary;  
    UserInput ui;
    Processor p;
    
    /*Singleton Pattern*/ 
    private Presenter() {
    }    

    private static Presenter instance = new Presenter();
     
    public static Presenter getInstance() {
	return instance;
    }
    
    /* Q1 Present the answer */
    public void answer(int question, int zipcode) {
	ui = UserInput.getInstance();
	p = Processor.getInstance();
	int zip = ui.getCurrentZip();
	int q = ui.getCurrentChoice();
	
	switch (q) {
	case 1:	    
	    System.out.println(p.getSumPop());
	    break;
	case 2:	    
	    
	    String a2 = ""+zip+" "+p.getTotalFinesperCap();
	    a2 = a2.substring(0, a2.indexOf('.')+5);
	    break;
	case 3:
	    System.out.println(p.getAveResMV(zip));
	case 4:
	    System.out.println(p.getAveResTLA(zip));
	case 5:
	    System.out.println(p.getTotalResMVperCap(zip));
	case 6:
	    System.out.println(p.get_FineperTicket_to_HousingAffordability_Ratio(zip));	
	}
    }   

}
