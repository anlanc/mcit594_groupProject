package edu.upenn.cit594.ui;

import java.util.ArrayList;

/* 1. Handle printing the output to the screen 
 * 
 */
public class Presenter {
    ArrayList<String> impactSummary;
    
    // 1. Private constructor 
    private Presenter() {
    }
    
    // 2. Singleton instance
    private static Presenter instance = new Presenter();
    
    // 3. Singleton accessor method 
    public static Presenter getInstance() {
	return instance;
    }
    
    // print state summary
    public void printStateSummary() {
	
	for (int i = 0 ; i<impactSummary.size(); i++) {
	    System.out.println(impactSummary.get(i));
	}

    }

}
