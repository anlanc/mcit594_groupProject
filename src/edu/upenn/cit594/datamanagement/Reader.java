package edu.upenn.cit594.datamanagement;

public class Reader {
    // 1. Private constructor
    private Reader() {
    }

    // 2. Singleton instance
    private static Reader instance = new Reader();

    // 3. Singleton accessor method
    public static Reader getInstance() {
	return instance;
    }
    
}
