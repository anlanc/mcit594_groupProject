package edu.upenn.cit594.ui;
import java.util.ArrayList;

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
	    break;
	case 4:
	    System.out.println(p.getAveResTLA(zip));
	    break;
	case 5:
	    System.out.println(p.getTotalResMVperCap(zip));
	    break;	    
	case 6:
	    String[] rank = p.getRank();
	    for (int i = 0 ; i < rank.length ; i++) {
		System.out.println(rank[i]);
	    }
	    break;
	}
    }   

}
