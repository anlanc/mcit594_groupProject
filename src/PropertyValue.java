
public class PropertyValue{
	public int zipCode;
	public double marketValue;
	public double totalLivableArea;
	
	public PropertyValue(int zipCode, double marketValue, double totalLivableArea) {
		this.zipCode = zipCode;
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		
	}



	public int getZipCode() {
		return zipCode;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public double getTotalLivableArea() {
		return totalLivableArea;
	}
	
	@Override
	public String toString() {
		return  zipCode + "," + marketValue + ", "
				+ totalLivableArea ;
	}

}
