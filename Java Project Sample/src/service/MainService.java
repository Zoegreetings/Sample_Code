package edu.gatech.saad.p3.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.gatech.saad.p3.dao.DAOFacade;
import edu.gatech.saad.p3.model.Demand;
import edu.gatech.saad.p3.model.DemandHistory;
import edu.gatech.saad.p3.model.LoginDetail;
import edu.gatech.saad.p3.model.PrefferedSchedule;
import edu.gatech.saad.p3.model.StudentDemand;
import edu.gatech.saad.p3.model.StudentDemandHistory;
import edu.gatech.saad.p3.model.StudentRecoData;
import edu.gatech.saad.p3.model.StudentRecommendationHistory;

@Controller
public class MainService {

	@Autowired
	MainServiceManager mainServiceManager;

	@RequestMapping(value = "/getAppInfo")
	public AppInfo getAppInfo() {
		return new AppInfo();
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public LoginDetail login(@RequestParam String userName,
			@RequestParam String password, @RequestParam String role) {
		System.out.println("incoming data is " + userName + password + role);

		// TODO authentication :
		return new LoginDetail(userName, password, role, true);
	}

	@RequestMapping(value = "/cs/class_settings", method = RequestMethod.POST)
	public void saveClassSettings(@RequestParam String inputArr) {
		System.out.println(inputArr);
		mainServiceManager.saveClassSizeSettings(inputArr);

	}
	

	@RequestMapping(value = "/cs/course_settings", method = RequestMethod.POST)
	public void saveCourseSettings() {
		

	}

	@RequestMapping(value = "/result/stu_data", method = RequestMethod.POST)
	public StudentDetails getStudentData(@RequestHeader String userName,
			@RequestHeader String password) {
		return new StudentDetails();
	}

	@RequestMapping(value = "/result/hist_data", method = RequestMethod.POST)
	public Vector<Integer> getStudentHistoryData(@RequestParam Integer studentId) {
		System.out.println("service: student id " + studentId);
		Vector<Integer> result = mainServiceManager.getStudentDemand(studentId);
		System.out.println(result);
		// StringBuilder sb = new StringBuilder();
		return result;

	}

	@RequestMapping(value = "/result/aggregate_pref_hist_data", method = RequestMethod.POST)
	public StudentDemandHistory getAllStudentDemand() {
		StudentDemandHistory hist  = new StudentDemandHistory();
		
		Map<Integer, StudentDemand> result = mainServiceManager
				.getAllStudentDemand();
		hist.setDemandHistory(result);
		System.out.println(hist);
		
		return hist;

	}

	@RequestMapping(value = "/result/aggregate_rec_hist_data", method = RequestMethod.POST)
	public StudentRecommendationHistory getALLStudentRecHistoryData() {
		Map<Integer, StudentRecoData> result = mainServiceManager
				.getAllStudentRec();
		
		Collection<StudentRecoData> all = result.values();
		StudentRecommendationHistory obj = new StudentRecommendationHistory();
		obj.setHistory(all);
		return obj;

	}

	@RequestMapping(value = "/cs/all_course_demands", method = RequestMethod.POST)
	public Demand getALLCourseDemands() {
		System.out.println("beena i am here ");
		Demand result = mainServiceManager.getALLCourseDemands();
		// result=new Demand();
		// HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		// map.put(1, 5);
		// result.setDemand( map);
		//
		System.out.println("beena getALLCourseDemands" + result);
		return result;

	}

	@RequestMapping(value = "/student/stu_demand_pref_hist", method = RequestMethod.POST)
	public List<PrefferedSchedule> getStuDemandsHist(
			@RequestParam String studentId) {

		int id = Integer.valueOf(studentId);

		Map<Integer, List<PrefferedSchedule>> result = mainServiceManager
				.getStuDemandsHist();
		System.out.println("getStuDemandsHist");
		System.out.println(result);

		if (result != null && result.containsKey(id)) {
			return result.get(id);
			// hist.setDemandList(demandList)
		}
		return new ArrayList<PrefferedSchedule>();

	}

	@RequestMapping(value = "/student/stu_pref", method = RequestMethod.POST)
	public void saveStudentDemand(@RequestParam Integer courseCount,
			@RequestParam String desiredcourselist,
			@RequestParam String userName) {
		int uid= Integer.valueOf(userName);
		
		Vector<Integer> sch = new Vector<Integer>();
		sch.add(courseCount);
		
		String[] all = desiredcourselist.split(",");
		for (int i = 0; i < all.length; i++) {
			sch.add(Integer.valueOf(all[i]));
		}
		DAOFacade.getInstance().setPreferredSchedule(uid, sch);
	}

	@RequestMapping(value = "/student/demand", method = RequestMethod.POST)
	public StudentDetails getDemandData(@RequestHeader String userName,
			@RequestHeader String password) {
		return new StudentDetails();
	}

	@RequestMapping(value = "/student/recs", method = RequestMethod.POST)
	public StudentRecoData getRecs(@RequestParam String userId) {
		
		int uid = Integer.valueOf(userId);
		List<StudentRecoData> allRecs = mainServiceManager.getRecs(uid);
		StudentRecoData result ;
		if(allRecs !=null){
			return allRecs.get(0);
		}
		//System.out.println(result);
		return  new StudentRecoData();
	}

	@RequestMapping(value = "/student/getrecs", method = RequestMethod.GET)
	public List<StudentRecoData> getRecs() {
		return mainServiceManager.getRecs(1);
	}

	@RequestMapping(value = "/student/history", method = RequestMethod.POST)
	public List<StudentRecoData> getHistory(@RequestHeader String userName,
			@RequestHeader String password) {
		List<StudentRecoData> result = new ArrayList<StudentRecoData>();
		return result;
	}

	public MainServiceManager getMainServiceManager() {
		return mainServiceManager;
	}

	public void setMainServiceManager(MainServiceManager mainServiceManager) {
		this.mainServiceManager = mainServiceManager;
	}

}
