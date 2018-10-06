package edu.gatech.saad.p3.model;

/**
 * Model class for Instructor
 * 
 */
public class Instructor extends User {

	public Instructor(Integer facultyId) {
		super(facultyId);
	}

	public Integer getInstructorId() {
		return id;
	}

}
