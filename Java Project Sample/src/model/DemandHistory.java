package edu.gatech.saad.p3.model;

import java.util.List;

public class DemandHistory {
	Integer userId ;
	List<Demand> demandList;

	public List<Demand> getDemandList() {
		return demandList;
	}

	public void setDemandList(List<Demand> demandList) {
		this.demandList = demandList;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
	
}
