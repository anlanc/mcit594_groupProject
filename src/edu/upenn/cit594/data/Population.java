package edu.upenn.cit594.data;

public class Population {
	public int zipCode;
	public int population;
	public Population(int zipCode, int population) {
		this.zipCode = zipCode;
		this.population = population;
	}
	public int getZipCode() {
		return zipCode;
	}
	public int getPopulation() {
		return population;
	}
	@Override
	public String toString() {
		return zipCode + " " + population;
	}
	
}
