package edu.gatech.saad.p3.service.logic;

import edu.gatech.saad.p3.dao.DAOFacade;
import edu.gatech.saad.p3.model.Course;
import edu.gatech.saad.p3.model.StudentDemand;
import gurobi.GRB;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * It is a helper class that provides methods to add the different constraints
 * on the model. Any new constraints should be added here and called from
 * addConstraints(..) method
 * 
 */
public class ConstraintUtil {

	Scheduler scheduler;

	/**
	 * This method adds the constraint on each course such that each course has
	 * max X students in any semester
	 * 
	 * @throws GRBException
	 */
	private static void addCourseMaxCapacityConstraint(Scheduler sch)
			throws GRBException {

		GRBModel model = sch.getModel();
		GRBVar[][][] grbVar = sch.getGrbVar();
		GRBVar X = sch.getX();

		// students in each course are less than X
		// the solver will vary X to make it as small as possible such that this
		// holds true
		for (int j = 0; j < sch.getTotalNumberOfCourses(); j++) {
			for (int k = 0; k < sch.getTotalNumberOfSemesters(); k++) {
				GRBLinExpr constraint = new GRBLinExpr();
				for (int i = 0; i < sch.getTotalNumberOfStudents(); i++) {
					constraint.addTerm(1, grbVar[i][j][k]);
				}
				model.addConstr(constraint, GRB.LESS_EQUAL, X,
						"CourseMaxCapacityConstraint." + j + "." + k);
			}
		}

	}

	/**
	 * This method adds the constraint on each course requested and
	 * prerequisites that each course has max X students in any semester
	 * 
	 * @throws GRBException
	 */
	private static void addConstraintForEachCoursePerStudentInAllSems(
			Scheduler sch) throws GRBException {

		GRBModel model = sch.getModel();
		GRBVar[][][] variables = sch.getGrbVar();
		GRBVar X = sch.getX();

		Map<Integer, Vector<Integer>> preReqs = sch.getAllCoursePreReqs();
		
		for (int i = 0; i < sch.getTotalNumberOfStudents(); i++) {
			StudentDemand s = sch.getDemand().get(i + 1);
			List<Integer> desired = s.getCourseIdList();
			List<Integer> allCourses = new ArrayList<Integer>();// requested+prequisites
			for (Integer cid : desired) {
				allCourses.add(cid);
				Vector<Integer> coursePreReqs = preReqs.get(cid);
				if (coursePreReqs != null)
					allCourses.addAll(coursePreReqs);
			}
			for (Integer courseNumber : allCourses) {
				GRBLinExpr constraint = new GRBLinExpr();
				for (int k = 0; k < sch.getTotalNumberOfSemesters(); k++) {
					constraint.addTerm(1, variables[i][courseNumber - 1][k]);
				}
				// a course can't be taken more than once
				// also a student's demand is definitely met
				model.addConstr(constraint, GRB.EQUAL, 1,
						"ConstraintForEachCoursePerStudentInAllSems." + i + "."
								+ courseNumber);
			}
		}

	}

	private static List<Integer> getAllCoursesForStudent(Scheduler sch,
			Integer stuId) {

		Map<Integer, Vector<Integer>> preReqs = sch.getAllCoursePreReqs();		
		StudentDemand s = sch.getDemand().get(stuId);
		
		
		List<Integer> desired = s.getCourseIdList();
		List<Integer> allCourses = new ArrayList<Integer>();// requested+prequisites
		for (Integer cid : desired) {
			allCourses.add(cid);
			Vector<Integer> coursePreReqs = preReqs.get(cid);
			if (coursePreReqs != null)
				allCourses.addAll(coursePreReqs);
		}

		return allCourses;
		
	}

	/**
	 * Add constraint to restrict the course offering when the course is not
	 * available
	 * 
	 * @throws GRBException
	 */
	private static void addAvailableCourseConstraint(Scheduler sch)
			throws GRBException {

		GRBModel model = sch.getModel();
		GRBVar[][][] variables = sch.getGrbVar();
		GRBVar X = sch.getX();

		Map<Integer, Vector<Integer>> preReqs = sch.getAllCoursePreReqs();

		DAOFacade dao = DAOFacade.getInstance();

		// when the course is not offered for course+semester combination
		for (int k = 0; k < sch.getTotalNumberOfSemesters(); k++) {
			for (Course course : sch.getCourses()) {

				if (!dao.isAvailable(course.getCourseId(), sch.getNextTermId())) {
					GRBLinExpr constraint = new GRBLinExpr();
					for (int i = 0; i < sch.getTotalNumberOfStudents(); i++) {
						constraint.addTerm(1,
								variables[i][course.getId() - 1][k]);
					}
					model.addConstr(constraint, GRB.EQUAL, 0,
							"AvailableCourseConstraint." + course.getId() + "."
									+ sch.getNextSemId());
				}
			}
		}
	}

	/**
	 * add constraint for Max Courses allowed in any semester for a student
	 */
	private static void addMaxCoursePerSemPerStudentConstraint(Scheduler sch)
			throws GRBException {

		GRBModel model = sch.getModel();
		GRBVar[][][] variables = sch.getGrbVar();
		GRBVar X = sch.getX();

		// students take two or less courses per semester- as configured in
		// Config
		for (int i = 0; i < sch.getTotalNumberOfStudents(); i++) {
			StudentDemand s = sch.getDemand().get(i + 1);
			List<Integer> allCoursesForStudent = getAllCoursesForStudent(sch,
					i + 1);
			for (int k = 0; k < sch.getTotalNumberOfSemesters(); k++) {
				GRBLinExpr constraint = new GRBLinExpr();
				for (Integer courseNumber : allCoursesForStudent) {
					constraint.addTerm(1, variables[i][courseNumber - 1][k]);
				}
				model.addConstr(constraint, GRB.LESS_EQUAL,
						Config.maxClassesAllowedPerSemester,
						"MaxCoursePerSemPerStudentConstraint." + i + "." + k);
			}
		}

	}

	/**
	 * add course prequisite constriant such that the pre-requisite course is
	 * always taken before the requested course
	 * 
	 */
	private static void addCoursePrerequisitesConstraint(Scheduler sch)
			throws GRBException {

		GRBModel model = sch.getModel();
		GRBVar[][][] variables = sch.getGrbVar();
		GRBVar X = sch.getX();

		Map<Integer, Vector<Integer>> preReqs = sch.getAllCoursePreReqs();

		Map<Integer, StudentDemand> demand = sch.getDemand();
		// prerequisite course constraint
		for (Integer stuId : demand.keySet()) {
			int i = stuId - 1;
			StudentDemand studentDemand = demand.get(stuId);

			List<Integer> desiredCourseList = studentDemand.getCourseIdList();

			for (Integer courseIdForStudent : desiredCourseList) {
				Vector<Integer> preReqsForCourse = preReqs
						.get(courseIdForStudent);
				if ((preReqsForCourse != null && preReqsForCourse.size() > 0)) {

					GRBLinExpr constraint0 = new GRBLinExpr();
					constraint0.addTerm(1,
							variables[i][courseIdForStudent - 1][0]);
					model.addConstr(constraint0, GRB.EQUAL, 0,
							"CoursePrerequisitesConstraint." + i + "."
									+ courseIdForStudent);
					for (Integer preReqId : preReqsForCourse) {

						for (int k = 0; k < sch.getTotalNumberOfSemesters(); k++) {
							GRBLinExpr constraint1 = new GRBLinExpr();
							GRBLinExpr constraint2 = new GRBLinExpr();
							for (int k1 = 0; k1 < k; k1++) {
								// prerequisite course taken in k1 th sem
								constraint1.addTerm(1,
										variables[i][preReqId - 1][k1]);
								// desired course taken in k1+1 th sem
								constraint2
										.addTerm(
												1,
												variables[i][courseIdForStudent - 1][k1 + 1]);
							}
							model.addConstr(constraint2, GRB.LESS_EQUAL,
									constraint1,
									"CoursePrerequisitesConstraint." + i + "."
											+ courseIdForStudent);
						}

					}

				}
			}
		}
	}

	/**
	 * 
	 * add all required constraints here
	 * 
	 * @throws GRBException
	 */
	public static void addConstraints(Scheduler sch) throws GRBException {

		addCourseMaxCapacityConstraint(sch);
		addConstraintForEachCoursePerStudentInAllSems(sch);
		addAvailableCourseConstraint(sch);
		addMaxCoursePerSemPerStudentConstraint(sch);
		//addCoursePrerequisitesConstraint(sch);

	}

}
