package edu.gatech.saad.p3.model;

/**
 * Model class for Semester
 * 
 */
public class Semester extends Identified {

	public Integer getSemId() {
		return id;
	}

	public Semester(Integer semId) {
		super(semId);

	}

	@Override
	public String toString() {
		return "Semester [id=" + id + "]";
	}

	public String getSemesterName() {
		// as per the project requirement document order is :Fall/Spring/Summer
		if (id % 3 == 0) {
			// like 0,3,6 ..semid
			return "Fall";
		}
		if (id % 3 == 1) {
			// like 1,4,7..semid
			return "Spring";
		}
		return "Summer";

	}

}
