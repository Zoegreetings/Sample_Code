package edu.gatech.saad.p3.dao;

import java.util.Vector;


public class FacultyDAO extends DAO {
	
	public Vector<Integer> getTAIds() {
		return getIntegerVector("SELECT userId FROM Project3.UserRoles WHERE roleId = " + TA_ROLE);
	}
	
	public Vector<Integer> getStudentIds() {
		return getIntegerVector("SELECT userId FROM Project3.UserRoles WHERE roleId = " + STUDENT_ROLE);
	}
	
	public Vector<Integer> getProfessorIds() {
		return getIntegerVector("SELECT userId FROM Project3.UserRoles WHERE roleId = " + PROFESSOR_ROLE);
	}
	
	public Vector<Integer> getTeachingCompetences(int userId) {
		return getIntegerVector("SELECT courseId FROM Project3.TeachingCompetences WHERE userId = " + userId);
	}
	
	public Vector<Integer> getTAAssignments(int userId) {
		return getIntegerVector("SELECT courseId FROM Project3.CourseAssignment WHERE userId = " + 
	                        userId + " AND roleId = " + TA_ROLE);
	}
	
	public Vector<Integer> getProfessorAssignments(int userId) {
		return getIntegerVector("SELECT courseId FROM Project3.CourseAssignment WHERE userId = " + 
	                        userId + " AND roleId = " + PROFESSOR_ROLE);
	}
	
	public boolean setTAAssignment(int userId, int courseId) {
		return setAssignment(userId, courseId, TA_ROLE);
	}
	
	public boolean setProfessorAssignment(int userId, int courseId) {
		return setAssignment(userId, courseId, PROFESSOR_ROLE);
	}
	
	private boolean setAssignment(int userId, int courseId, int role) {
		boolean result = false;
		if (getInteger("SELECT roleId FROM Project3.UserRoles WHERE userId = " + userId +
				       " AND roleId = " + role) == role) {
			if (getInteger("SELECT courseId FROM Project3.Courses WHERE courseId = " + courseId) == courseId) {
				result = update("INSERT INTO Project3.CourseAssignment VALUES (" + userId +
						        ", " + courseId + ", " + role + ")");				
			}
		}
		return result;
	}
	
	public boolean clearTAAssignment(int userId, int courseId) {
		return clearAssignment(userId, courseId, TA_ROLE);
	}
	
	public boolean clearProfessorAssignment(int userId, int courseId) {
		return clearAssignment(userId, courseId, PROFESSOR_ROLE);
	}
	
	private boolean clearAssignment(int userId, int courseId, int role) {
		return update("DELETE FROM Project3.CourseAssignment WHERE userId = " + userId +
				        " AND courseId = " + courseId + " AND roleId = " + role);
	}	

	private static final int TA_ROLE = 1;
	private static final int PROFESSOR_ROLE = 3;
	private static final int STUDENT_ROLE = 5;
}
