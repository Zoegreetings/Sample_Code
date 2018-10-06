package edu.gatech.saad.p3.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.aop.framework.DefaultAopProxyFactory;

import edu.gatech.saad.p3.dao.DAOFacade;
import edu.gatech.saad.p3.model.Demand;
import edu.gatech.saad.p3.model.DemandHistory;
import edu.gatech.saad.p3.model.PrefferedSchedule;
import edu.gatech.saad.p3.model.StudentDemand;
import edu.gatech.saad.p3.model.StudentRecoData;
import edu.gatech.saad.p3.service.logic.Scheduler;

public class MainServiceManager {
	DAOFacade dao = DAOFacade.getInstance();

	public List<StudentRecoData> getRecs(Integer userId) {
		System.out.println("beena: I raeched to getRecs");
		Scheduler scheduler = new Scheduler();
		scheduler.calculateSchedule();
		Map<Integer, List<StudentRecoData>> allRecs = scheduler
				.getRecommendations();
		
		System.out.println("all recos are "+ allRecs);

		return allRecs.get(userId);
	}

	public Vector<Integer> getStudentDemand(Integer studentId) {

		
		return dao.getPreferredSchedule(studentId);

	}

	public void saveClassSizeSettings(String inputArr) {
		
		//inputArr= inputArr.substring(1,inputArr.length()-1);
		System.out.println(inputArr);

		String[] classSizeData = inputArr.split(",");

		
		for (int i = 0; i < classSizeData.length; i++) {
			System.out.println(classSizeData[i]);
			if(classSizeData[i].isEmpty() )
				continue;
			Integer csData = Integer.parseInt(classSizeData[i]);
			if (csData != null && csData > 0)
				dao.setCapacity(i + 1, csData);

		}

	}

	public Map<Integer, StudentDemand> getAllStudentDemand() {
		return dao.getStudentsPreferredSchedules();
	}

	public Map<Integer, StudentRecoData> getAllStudentRec() {
		return dao.getAllStudentRec();
	}

	public Demand getALLCourseDemands() {
		// TODO Auto-generated method stub
		HashMap<Integer, Integer> res = dao.getAllCourseDemand();
		
		Demand obj = new Demand();
		obj.setDemand(res);
		
		return obj;
	}

	public Map<Integer, List<PrefferedSchedule>> getStuDemandsHist() {
		// TODO Auto-generated method stub
		return dao.getStuDemandsHist();
	}

	// savedb

}
