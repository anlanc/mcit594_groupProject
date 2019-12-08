package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class Logger {
	//Singleton
	private static String filename;
	private PrintWriter out;
	
	// 1.private constructor
	private Logger() {
		
		try {
			out = new PrintWriter(new File(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 2.singleton instance
	//private static Logger instance = new Logger("log1.txt");
	private static Logger instance;
	
	// setter method 
	public static void setArg(String arg) {
		filename = arg;
	}
	
	// singleton accessor method
	public static Logger getInstance(String arg) {
		setArg(arg);
		instance = new Logger();
		return instance;
	}
	
	// 3.public log method
	// argument TBD
	public void log() {
		// TBD
		out.println("This is just me testing");
	
		out.flush();
	}
}
