package edu.gatech.saad.p3.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import edu.gatech.saad.p3.model.DemandHistory;
import edu.gatech.saad.p3.model.PrefferedSchedule;
import edu.gatech.saad.p3.model.StudentDemand;
import edu.gatech.saad.p3.model.StudentRecoData;

public class DAOFacade {

	private DAOFacade() {
		course_dao = new CourseDAO();
		semester_dao = new SemesterDAO();
		users_dao = new UsersDAO();
		schedule_dao = new ScheduleDAO();
		faculty_dao = new FacultyDAO();
		rec_dao = new RecoDao();
	}

	public static DAOFacade getInstance() {
		if (instance == null) {
			instance = new DAOFacade();
		}
		return instance;
	}

	// Course DAO
	public Vector<Integer> getCourseIds() {
		return course_dao.getCourseIds();
	}

	public Vector<Integer> getPrerequisites(int courseId) {
		return course_dao.getPrerequisites(courseId);
	}

	public Map<Integer, Vector<Integer>> getAllPrerequisites() {
		return course_dao.getAllPrerequisites();
	}

	public String getCourseCode(int courseId) {
		return course_dao.getCourseCode(courseId);
	}

	public String getCourseName(int courseId) {
		return course_dao.getCourseName(courseId);
	}

	public boolean isAvailable(int courseId, int termId) {
		return course_dao.isAvailable(courseId, termId);
	}

	public boolean isAvailableThisTerm(int courseId) {
		return course_dao.isAvailable(courseId, getNextTerm());
	}

	public int getCapacity(int courseId) {
		return course_dao.getCapacity(courseId);
	}

	public boolean setCapacity(int courseId, int capacity) {
		return course_dao.setCapacity(courseId, capacity);
	}

	public HashMap<Integer, Integer> getDirtyCapacity() {
		return course_dao.getDirtyCapacity();
	}

	public boolean clearDirtyCapacity(int courseId, int capacity) {
		return course_dao.clearDirtyCapacity(courseId, capacity);
	}

	public boolean setAvailability(int courseId, int termId, boolean available) {
		return course_dao.setAvailability(courseId, termId, available);
	}

	public boolean setNextTermsAvailability(int courseId, boolean available) {
		return course_dao.setAvailability(courseId, getNextTerm(), available);
	}

	// Semester DAO

	public int getNextTerm() {
		return semester_dao.getNextTerm();
	}

	public int getNextSemester() {
		return semester_dao.getNextSemester();
	}

	// Users DAO

	public String getName(int userId) {
		return users_dao.getName(userId);
	}

	public int getUserId(String username, String pwHash) {
		return users_dao.getUserId(username, pwHash);
	}

	public boolean isAdmin(int userId) {
		return users_dao.isAdmin(userId);
	}

	// Schedule DAO

	public Vector<Integer> getPreferredSchedule(int userId) {
		return schedule_dao.getPreferredSchedule(userId);
	}

	public Map<Integer, StudentDemand> getStudentsPreferredSchedules() {
		return schedule_dao.getStudentsPreferredSchedules(semester_dao
				.getNextSemester());
	}

	
	public Map<Integer, String> getStudentsPreferredSchedulesHist() {
		return schedule_dao.getStudentsPreferredSchedulesHist(semester_dao
				.getNextSemester());
	}
	public boolean setPreferredSchedule(int userId, Vector<Integer> aSchedule) {
		schedule_dao.setPreferredSchedule(userId, aSchedule);

		// update the demand table - as the preferred schedule should tie to
		// demand

		updateDemandTable();

		return true;
	}


	private void updateDemandTable() {
		
		Map<Integer, StudentDemand> allSchedules = getStudentsPreferredSchedules();
		Map<Integer,Integer>  courseDemandCount = new HashMap<Integer, Integer>();
		
		Vector<Integer> cids = getCourseIds();
		for (Integer id : cids) {
			courseDemandCount.put(id, 0);
		}
		for (StudentDemand dem : allSchedules.values()) {
			List<Integer> courseListIndemand = dem.getCourseIdList();
			for (Integer cid : courseListIndemand) {				
				courseDemandCount.put(cid, courseDemandCount.get(cid)+1);
			}
			
		}		
				
		for (Integer cid : courseDemandCount.keySet()) {
			course_dao.setCourseDemand(cid, courseDemandCount.get(cid));
		}
		
	}
	
	
	public HashMap<Integer, String> getDirtyPreferredSchedules() {
		return schedule_dao.getDirtyPreferredSchedules();
	}

	public boolean clearDirtyPreferredSchedule(int userId, String schedule) {
		return schedule_dao.clearDirtyPreferredSchedule(userId, schedule);
	}

	// Faculty DAO

	public Vector<Integer> getTAIds() {
		return faculty_dao.getTAIds();
	}

	public Vector<Integer> getProfessorIds() {
		return faculty_dao.getProfessorIds();
	}

	public Vector<Integer> getStudentIds() {
		return faculty_dao.getStudentIds();
	}

	public Vector<Integer> getTeachingCompetences(int userId) {
		return faculty_dao.getTeachingCompetences(userId);
	}

	public Vector<Integer> getTAAssignments(int userId) {
		return faculty_dao.getTAAssignments(userId);
	}

	public Vector<Integer> getProfessorAssignments(int userId) {
		return faculty_dao.getProfessorAssignments(userId);
	}

	public boolean setTAAssignment(int userId, int courseId) {
		return faculty_dao.setTAAssignment(userId, courseId);
	}

	public boolean setProfessorAssignment(int userId, int courseId) {
		return faculty_dao.setProfessorAssignment(userId, courseId);
	}

	public boolean clearTAAssignment(int userId, int courseId) {
		return faculty_dao.clearTAAssignment(userId, courseId);
	}

	public boolean clearProfessorAssignment(int userId, int courseId) {
		return faculty_dao.clearProfessorAssignment(userId, courseId);
	}

	private CourseDAO course_dao = null;
	private SemesterDAO semester_dao = null;
	private UsersDAO users_dao = null;
	private ScheduleDAO schedule_dao = null;
	private FacultyDAO faculty_dao = null;
	private RecoDao rec_dao = null;

	private static DAOFacade instance = null;

	public void saveRecommendation(Map<Integer, List<StudentRecoData>> recos) {

		// input is : student id-> reco list of diff sems

		// as we are only calculation for next sem - list will have only one
		// element

		Map<Integer, StudentRecoData> allRecos = rec_dao.getRecos();
		List<StudentRecoData> insertdata = new ArrayList<StudentRecoData>();
		List<StudentRecoData> updatedata = new ArrayList<StudentRecoData>();

		for (Integer stuId : recos.keySet()) {

			if (allRecos.containsKey(stuId)) {
				updatedata.add(recos.get(stuId).get(0));
			}else{
				insertdata.add(recos.get(stuId).get(0));
			}

		}
		
		for (StudentRecoData studentRecoData : updatedata) {
			
			int uid = studentRecoData.getUserId();
			List<Integer> courseList = studentRecoData.getCourseIdList();
			StringBuilder courseListSb = new StringBuilder();
			for (int i = 0; i < courseList.size(); i++) {
				if(i>0)
					courseListSb.append(",");
				courseListSb.append(""+courseList.get(i));
			}
			rec_dao.updateReco(uid, courseListSb.toString());
			
			
		}
		
		for (StudentRecoData studentRecoData : insertdata) {
			
			int uid = studentRecoData.getUserId();
			List<Integer> courseList = studentRecoData.getCourseIdList();
			StringBuilder courseListSb = new StringBuilder();
			for (int i = 0; i < courseList.size(); i++) {
				if(i>0)
					courseListSb.append(",");
				courseListSb.append(""+courseList.get(i));
			}
			rec_dao.insertReco(uid, courseListSb.toString());
			
			
		}
		
		
		
	}

	public Map<Integer, StudentRecoData> getAllStudentRec() {
		Map<Integer, StudentRecoData> allRecos = rec_dao.getRecos();
		return allRecos;
	}
	
	
	
	public HashMap<Integer, Integer> getAllCourseDemand(){
		return course_dao.getCourseDemand();
	}

	public Map<Integer, List<PrefferedSchedule>> getStuDemandsHist() {		
		
		return users_dao.getStuDemandsHist();
	}

}
