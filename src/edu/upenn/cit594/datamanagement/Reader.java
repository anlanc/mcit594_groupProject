package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;
import sun.util.logging.resources.logging;

public class Reader {
    Logger l;
    
    public Reader() {
	l = Logger.getInstance();

    }
    
    public void Read(String fName) {
	l.trackRead(fName);
    }
    
}
