package edu.gatech.saad.p3.model;

import java.util.Vector;

/**
 * Model Class for Course
 *
 */
public class Course extends Identified {

	Vector<Integer> preRequisites;
	Vector<Integer> coRequisites;

	public Integer getCourseId() {
		return id;
	}

	public Course(Integer courseId) {
		super(courseId);

	}

	@Override
	public String toString() {
		return "Course [id=" + id + "]";
	}

	public Vector<Integer> getPreRequisites() {
		return preRequisites;
	}

	public void setPreRequisites(Vector<Integer> preRequisites) {
		this.preRequisites = preRequisites;
	}

	public Vector<Integer> getCoRequisites() {
		return coRequisites;
	}

	public void setCoRequisites(Vector<Integer> coRequisites) {
		this.coRequisites = coRequisites;
	}

	

}
