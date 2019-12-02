package edu.upenn.cit594.processor;

public class Processor {
    // 1. Private constructor 
    private Processor() {	
    }
    
    // 2. Singleton instance
    private static Processor instance = new Processor();
    
    // 3. Singleton accessor method 
    public static Processor getInstance() {
	return instance;
    }
    
    public void Answer(int choice, int zipCode) {
	// call respective methods 
	// if no zipCOde required, will provide 99999
    }
    
    
        
}
