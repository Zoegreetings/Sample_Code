package edu.gatech.saad.p3.service.logic;

import java.util.HashMap;

import edu.gatech.saad.p3.dao.DAOFacade;

/**
 * Main class to run the model optimization
 * 
 */
public class ProjectOne {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler();
		scheduler.calculateSchedule();
		System.out.println(scheduler.getRecommendations());
		
//		HashMap<Integer, Integer> res = DAOFacade.getInstance().getAllCourseDemand();
//		System.out.println(res);

	}

}
