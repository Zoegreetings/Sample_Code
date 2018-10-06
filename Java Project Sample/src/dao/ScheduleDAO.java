package edu.gatech.saad.p3.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import edu.gatech.saad.p3.model.StudentDemand;

public class ScheduleDAO extends DAO {

	public Vector<Integer> getPreferredSchedule(int userId) {
		Vector<Integer> result = null;
		String schedule = getString("SELECT preferredSchedule FROM Project3.PreferredSchedule WHERE userId = " + userId);
		if (schedule != null) {
			
			// Translate schedule into vector
			result = new Vector<Integer>();
		    for (String token: schedule.split(",")) {
		    	result.add(new Integer(token));
		    }			
		}
		return result;
	}
	
	public boolean setPreferredSchedule(int userId, Vector<Integer> aSchedule) {
		boolean result = false;
		if (aSchedule != null && aSchedule.size() >= 2) {
		
			// Translate schedule into string
			String schedule = "";
			for (int i = 0 ; i < aSchedule.size() ; i++) {
				if (schedule.length() > 0) schedule = schedule + ',';
				schedule += aSchedule.get(i).toString();
			}
			
			String query = "SELECT userId FROM Project3.PreferredSchedule WHERE userId = " + userId;
			
			// Store history and update
			if ( getInteger(query) == userId ) {
				query = "INSERT INTO Project3.PreferredScheduleHistory SELECT " +
						"userId, preferredSchedule, timestamp FROM Project3.PreferredSchedule " +
			            "WHERE userId = " + userId;
				if (update(query)) {
					query = "UPDATE Project3.PreferredSchedule SET preferredSchedule = '" +
				            schedule + "' WHERE userId = " + userId;
					result = update(query);
				}
			} 
			
			// Just insert
			else {
				query = "INSERT INTO Project3.PreferredSchedule VALUES (" +
						 userId +", '" + schedule + "', NOW(), 1)";
				result = update(query);
			}			
		}		
		
		
		return result;
	}


	public HashMap<Integer,String> getDirtyPreferredSchedules() {
		return getIntegerStringHashMap("SELECT userId, preferredSchedule FROM Project3.PreferredSchedule WHERE dirty = 1");		
	}
	
	public boolean clearDirtyPreferredSchedule(int userId, String schedule) {
		boolean result = false;		
		if (schedule != null) {
			String update_statement = "UPDATE Project3.PreferredSchedule SET dirty = 0 WHERE userId = " +
		                              userId + " AND preferredSchedule = '" + schedule + "' AND dirty = 1";			
			result = update(update_statement);			
		}
		return result;
	}
	
	public Map<Integer,StudentDemand> getStudentsPreferredSchedules(int semesterId) {
		
		String query = Sql.SqlData.get("STUDENT_SCHEDULE");		
		Map<Integer, StudentDemand> result = new HashMap<Integer, StudentDemand>();
		
		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				ResultSet rs = stmt.executeQuery(query);				
				
				while (rs.next()) {
					Integer uid = rs.getInt("userId");
					String preference = rs.getString("preferredSchedule");
					String[] arr = preference.split(",");
					StudentDemand demand = new StudentDemand();
					demand.setUserId(uid);
					demand.setNumDesiredCourses(Integer.valueOf(arr[0]));
					demand.setSemesterId(semesterId);
					List<Integer> cidList = new ArrayList<Integer>();
					for (int i = 1; i < arr.length; i++) {
						cidList.add(Integer.valueOf(arr[i]));
					}
					demand.setCourseIdList(cidList);
					result.put(uid,demand);					
				}				
			} catch (SQLException e) {
				System.out.println("[ERR] ScheduleDAO:getStudentsPreferredSchedules - " + e.getMessage());
			}
		}
		return result;
		
	}

	public Map<Integer, String> getStudentsPreferredSchedulesHist(
			int nextSemester) {
		String query = "SELECT userId, preferredSchedule FROM Project3.PreferredScheduleHistory";		
		return getIntegerStringHashMap(query);
	}
}
