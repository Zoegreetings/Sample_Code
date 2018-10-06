package edu.gatech.saad.p3.model;

import java.util.List;

public class StudentRecoData {
	
	private Integer userId;

	private Integer semId;

	private List<Integer> courseIdList;

	public List<Integer> getCourseIdList() {
		return courseIdList;
	}

	public void setCourseIdList(List<Integer> courseIdList) {
		this.courseIdList = courseIdList;
	}

	public Integer getSemId() {
		return semId;
	}

	public void setSemId(Integer semId) {
		this.semId = semId;
	}

	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "StudentRecoData [userId=" + userId + ", semId=" + semId
				+ ", courseIdList=" + courseIdList + "]";
	}

	

}
