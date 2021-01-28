package com.techmojo.twitter.utils;

import java.util.Comparator;
import java.util.Map.Entry;

public class MapEntryValueComparator implements Comparator<Entry<String, Long>> {
	private String order = "desc";
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	
	@Override
	public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
		if("desc".equals(order))
			return o2.getValue().compareTo(o1.getValue());
		else
			return o1.getValue().compareTo(o2.getValue());
	}

}
