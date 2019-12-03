package edu.upenn.cit594.ui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import edu.upenn.cit594.processor.Processor;

/* 1. Handle printing the output to the screen */

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
	    TreeMap<Integer, Double> totalFinesPerCap = p.getTotalFinesperCap();
	    Set<Integer> zipcodes = totalFinesPerCap.keySet();
	    for (Iterator<Integer> i = zipcodes.iterator(); i.hasNext();) {
		Integer zipC = (Integer) i.next();
		double finesPerCap = ((int) (totalFinesPerCap.get(zipC)*10000))/10000;
		System.out.println(zipC + " = " + finesPerCap);
	    }
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
