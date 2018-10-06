package edu.gatech.saad.p3.model;

import java.util.List;

public class StudentDemand {
	
	private Integer userId;

	private Integer semesterId;
	private Integer numDesiredCourses;
	private List<Integer> courseIdList;

	public Integer getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(Integer semesterId) {
		this.semesterId = semesterId;
	}

	public Integer getNumDesiredCourses() {
		return numDesiredCourses;
	}

	public void setNumDesiredCourses(Integer numDesiredCourses) {
		this.numDesiredCourses = numDesiredCourses;
	}

	public List<Integer> getCourseIdList() {
		return courseIdList;
	}

	public void setCourseIdList(List<Integer> courseIdList) {
		this.courseIdList = courseIdList;
	}	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "StudentDemand [userId=" + userId + ", semesterId=" + semesterId
				+ ", numDesiredCourses=" + numDesiredCourses
				+ ", courseIdList=" + courseIdList + "]";
	}

	
}
