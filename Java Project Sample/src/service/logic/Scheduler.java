package edu.gatech.saad.p3.service.logic;

import edu.gatech.saad.p3.dao.DAOFacade;
import edu.gatech.saad.p3.model.Course;
import edu.gatech.saad.p3.model.Semester;
import edu.gatech.saad.p3.model.StudentDemand;
import edu.gatech.saad.p3.model.StudentRecoData;
import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Class that provides the logic for student-course pairing after applying the
 * given constraints
 * 
 */
public class Scheduler {
	DAOFacade dao = DAOFacade.getInstance();

	private GRBModel model;
	private GRBVar[][][] grbVar;
	private List<Course> courses;
	private GRBVar X;

	private int totalNumberOfStudents;
	private int totalNumberOfCourses;
	private int totalNumberOfSemesters;

	private Map<Integer, StudentDemand> demand;
	private List<Semester> semesters;
	private Map<Integer, Vector<Integer>> allCoursePreReqs;
	private int nextSemId;
	private int nextTermId;

	private void initialize() {

		
		Vector<Integer> cids = dao.getCourseIds();
		allCoursePreReqs = dao.getAllPrerequisites();

		nextSemId = dao.getNextSemester();
		nextTermId = dao.getNextTerm();
		demand = dao.getStudentsPreferredSchedules();
		courses = new ArrayList<Course>();

		// Assumption : we are trying to allocate optimum courses for the next
		// term only

		totalNumberOfStudents = demand.size();
		totalNumberOfSemesters = 1;
		totalNumberOfCourses = cids.size();

		// initialize semesters info
		// Semester semester = new Semester(1);
		// semesters.add(semester);

		// initialize the course info
		for (Integer courseId : cids) {
			Course course = new Course(courseId);
			course.setPreRequisites(allCoursePreReqs.get(courseId));
			courses.add(course);
		}

		printAll();
	}

	private void printAll() {
		System.out.println(getNextSemId());
		System.out.println(getNextTermId());
		System.out.println(getAllCoursePreReqs());
		System.out.println(getCourses());
		System.out.println(getDemand());

	}

	public void calculateSchedule() {
		initialize();

		GRBEnv env;
		try {
			env = new GRBEnv("mip1.log");

			if (!Config.debugMode)
				env.set(GRB.IntParam.LogToConsole, 0);

			// we will follow this convention create the variable
			// i -> counter used for students
			// j -> counter used for courses
			// k-> counter for semesters

			model = new GRBModel(env);
			grbVar = new GRBVar[totalNumberOfStudents][totalNumberOfCourses][totalNumberOfSemesters];
			for (int i = 0; i < totalNumberOfStudents; i++) {
				for (int j = 0; j < totalNumberOfCourses; j++) {
					for (int k = 0; k < totalNumberOfSemesters; k++) {
						grbVar[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, i
								+ "." + j + "." + k);
					}
				}
			}
			// Integrate model variables
			model.update();

			// create and Integrate variable that needs to be optimized
			X = model.addVar(0, totalNumberOfStudents, 1, GRB.INTEGER, "X");
			model.update();

			// Set the objective
			GRBLinExpr optimization = new GRBLinExpr();
			optimization.addTerm(1, X);
			// trying to minimize the objective variable
			model.setObjective(optimization, GRB.MINIMIZE);
			model.update();

			// add all required constraints now
			ConstraintUtil.addConstraints(this);
			model.update();

			// Optimize the model
			model.optimize();

			printResults(this);

			// fetch the optimized value from the model
			double objectiveValue = model.get(GRB.DoubleAttr.ObjVal);
			if (Config.debugMode)
				System.out.printf("Calculated Ojective value = %f\n",
						objectiveValue);

			// print the result for the student-course pairing assignment
			System.out.println("X: " + X.get(GRB.DoubleAttr.X));
			
			Map<Integer, List<StudentRecoData>> recos = getRecommendations();
			
			dao.saveRecommendation(recos);
			

		} catch (GRBException e) {
			System.out
					.println("Model encountered exception - Please investigate!");
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Its a helper method to print the results of student-course pairing as a
	 * result of model objective optimization
	 * 
	 */
	private void printResults(Scheduler sch) {
		GRBVar[][][] grbVar = getGrbVar();
		// List<Semester> semesters = sch.getSemesters();
		List<Course> courses = sch.getCourses();

		// print the result after optimization
		for (Integer s : sch.getDemand().keySet()) {
			for (int k = 0; k < sch.getTotalNumberOfSemesters(); k++) {
				for (Course c : courses) {
					try {
						if (grbVar[s - 1][c.getId() - 1][k]
								.get(GRB.DoubleAttr.X) == 1) {
							if (Config.debugMode)
								System.out.println("Student: " + s
										+ " course: " + (c.getId() + 1)
										+ " semester: " + k + 1);
						}
					} catch (GRBException e) {

						e.printStackTrace();
					}
				}
			}
		}

	}

	public Map<Integer, List<StudentRecoData>> getRecommendations() {
		
		GRBVar[][][] grbVar = getGrbVar();
		// List<Semester> semesters = sch.getSemesters();
		List<Course> courses = getCourses();

		Map<Integer, List<StudentRecoData>> result = new HashMap<Integer, List<StudentRecoData>>();

		for (Integer s : getDemand().keySet()) {
			List<StudentRecoData> data = new ArrayList<StudentRecoData>();
			result.put(s, data);
			for (int k = 0; k < getTotalNumberOfSemesters(); k++) {

				ArrayList<Integer> courselistforThisSem = new ArrayList<Integer>();
				for (Course c : courses) {
					try {
						if (grbVar[s - 1][c.getId() - 1][k]
								.get(GRB.DoubleAttr.X) == 1) {
							if (Config.debugMode)
								System.out.println("Student: " + s
										+ " course: " + (c.getId() + 1)
										+ " semester: " + k + 1);

							courselistforThisSem.add(c.getId());

						}
					} catch (GRBException e) {

						e.printStackTrace();
					}
				}
				StudentRecoData reco = new StudentRecoData();
				reco.setUserId(s);
				reco.setSemId(getNextSemId());
				reco.setCourseIdList(courselistforThisSem);
				data.add(reco);
			}
		}
		
		return result;

	}

	public double getObjectiveValue() {
		double objectiveValue;
		try {
			if (model != null) {
				objectiveValue = model.get(GRB.DoubleAttr.ObjVal);
				return objectiveValue;
			}
		} catch (GRBException e) {
			e.printStackTrace();
		}
		return 0.0;
	}

	public Vector<String> getCoursesForStudentSemester(String student,
			String semester) {

		return null;
	}

	public Vector<String> getStudentsForCourseSemester(String course,
			String semester) {

		return null;
	}

	public GRBModel getModel() {
		return model;
	}

	public GRBVar[][][] getGrbVar() {
		return grbVar;
	}

	// public List<Semester> getSemesters() {
	// return semesters;
	// }

	public List<Course> getCourses() {
		return courses;
	}

	public GRBVar getX() {
		return X;
	}

	public int getTotalNumberOfStudents() {
		return totalNumberOfStudents;
	}

	public int getTotalNumberOfCourses() {
		return totalNumberOfCourses;
	}

	public int getTotalNumberOfSemesters() {
		return totalNumberOfSemesters;
	}

	public Map<Integer, StudentDemand> getDemand() {
		return demand;
	}

	public Map<Integer, Vector<Integer>> getAllCoursePreReqs() {
		return allCoursePreReqs;
	}

	public int getNextSemId() {
		return nextSemId;
	}

	public int getNextTermId() {
		return nextTermId;
	}

}
