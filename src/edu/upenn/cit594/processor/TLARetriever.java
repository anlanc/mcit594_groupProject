package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.PropertyValue;

public class TLARetriever implements DATARetriever{

	@Override
	public double getData(PropertyValue ppt) {
		if(ppt == null) return -1;
		return ppt.getTotalLivableArea();
	}

}
