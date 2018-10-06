package edu.gatech.saad.p3.model;

import java.util.Map;

public class Demand {
	Map<Integer, Integer> demand ;

	public Map<Integer, Integer> getDemand() {
		return demand;
	}

	public void setDemand(Map<Integer, Integer> demand) {
		this.demand = demand;
	}

	@Override
	public String toString() {
		return "Demand [demand=" + demand + "]";
	}
	
	
	

}
