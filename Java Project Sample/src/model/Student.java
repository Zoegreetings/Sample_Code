package edu.gatech.saad.p3.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {

	private Integer seniority;
	private Double gpa;

	Map<Semester, List<Course>> demand = new HashMap<Semester, List<Course>>();

	private ArrayList<Integer> allCourses;

	private ArrayList<Integer> desiredCourses;

	public Student(Integer studentId) {
		super(studentId);
	}

	public Integer getSeniority() {
		return seniority;
	}

	public void setSeniority(Integer seniority) {
		this.seniority = seniority;
	}

	public Double getGpa() {
		return gpa;
	}

	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	Integer getStudendId() {
		return id;
	}

	void updateDemand(Semester sem, Course course) {
		List<Course> semData = demand.get(sem);
		if (semData == null) {
			semData = new ArrayList<Course>();
			demand.put(sem, semData);
		}
		semData.add(course);
	}

	@Override
	public String toString() {
		return "Student [seniority=" + seniority + ", gpa=" + gpa + ", demand="
				+ getDemandInfo() + "]";
	}

	private String getDemandInfo() {
		StringBuilder sb = new StringBuilder();
		for (Semester sem : demand.keySet()) {
			sb.append("{sem" + sem.getSemId() + ": ");
			List<Course> courses = demand.get(sem);
			int len = courses.size();
			for (int i = 0; i < len; ++i) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append("course" + courses.get(i).getId());

			}

			sb.append("}");

		}
		return sb.toString();
	}

	public Map<Semester, List<Course>> getDemand() {
		return demand;
	}

	public ArrayList<Integer> getAllCourses() {
		allCourses = new ArrayList<Integer>();
		for (Semester sem : demand.keySet()) {
			List<Course> courses = demand.get(sem);
			for (Course c : courses) {
				allCourses.add(c.getId());
				allCourses.addAll(c.getPreRequisites());
				allCourses.addAll(c.getCoRequisites());
			}
		}

		return allCourses;

	}

	public ArrayList<Integer> getDesiredCourses() {
		desiredCourses = new ArrayList<Integer>();
		for (Semester sem : demand.keySet()) {
			List<Course> courses = demand.get(sem);
			for (Course c : courses) {
				desiredCourses.add(c.getId());
			}
		}
		return desiredCourses;
	}

}