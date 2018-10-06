package edu.gatech.saad.p3.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CourseDAO extends DAO {

	public Vector<Integer> getCourseIds() {
		return getIntegerVector("SELECT courseId FROM Project3.Courses");
	}

	public Vector<Integer> getPrerequisites(int courseId) {
		String query = "SELECT requires FROM Project3.Requisites WHERE type = "
				+ PREREQUISITE_TYPE + " and courseId = " + courseId;
		return getIntegerVector(query);
	}

	public Map<Integer, Vector<Integer>> getAllPrerequisites() {
		String aQuery = "SELECT courseId, requires FROM Project3.Requisites WHERE type = "
				+ PREREQUISITE_TYPE;

		Map<Integer, Vector<Integer>> result = null;
		System.out.println(aQuery);
		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				ResultSet rs = stmt.executeQuery(aQuery);
				result = new HashMap<Integer, Vector<Integer>>();
				while (rs.next()) {
					int cid = rs.getInt("courseId");
					Vector<Integer> prereqs = result.get(cid);
					if (prereqs == null) {
						prereqs = new Vector<Integer>();
						result.put(cid, prereqs);
					}
					prereqs.add(rs.getInt("requires"));
					result.put(cid, prereqs);
				}
			} catch (SQLException e) {
				System.out.println("[ERR] CourseDAO:getAllPrerequisites - "
						+ e.getMessage());
			}
		}
		return result;

	}

	public String getCourseCode(int courseId) {
		return getString("SELECT courseCode FROM Project3.Courses WHERE courseId = "
				+ courseId);
	}

	public String getCourseName(int courseId) {
		return getString("SELECT courseName FROM Project3.Courses WHERE courseId = "
				+ courseId);
	}

	public boolean isAvailable(int courseId, int termId) {
		boolean available = false;
		String query = "SELECT courseId FROM Project3.CourseAvailability WHERE courseId = "
				+ courseId + " and termId = " + termId;
		if (getInteger(query) == courseId)
			available = true;
		return available;
	}

	public int getCapacity(int courseId) {
		return getInteger("SELECT capacity FROM Project3.CourseCapacity WHERE courseId = "
				+ courseId);
	}

	public HashMap<Integer, Integer> getDirtyCapacity() {
		return getIntegerHashMap("SELECT courseId, capacity FROM Project3.CourseCapacity WHERE dirty = 1");
	}

	public boolean clearDirtyCapacity(int courseId, int capacity) {
		boolean result = false;
		if (capacity >= 0) {
			String update_statement = "UPDATE Project3.CourseCapacity SET dirty = 0 WHERE "
					+ "courseId = "
					+ courseId
					+ " AND capacity = "
					+ capacity
					+ " AND dirty = 1";
			result = update(update_statement);
		}
		return result;
	}

	public boolean setCapacity(int courseId, int capacity) {
		boolean result = false;
		if (capacity >= 0) {
			String update_statement = "INSERT INTO Project3.CourseCapacity VALUES ("
					+ courseId
					+ ", "
					+ capacity
					+ ", 1) ON DUPLICATE KEY UPDATE capacity = " + capacity;
			result = update(update_statement);
		}
		return result;
	}

	public boolean setAvailability(int courseId, int termId, boolean available) {
		boolean result = false;

		// Check if input is valid
		if ((getInteger("SELECT termId FROM Project3.Terms WHERE termId = "
				+ termId) == termId)
				&& (getInteger("SELECT courseId FROM Project3.Courses WHERE courseId = "
						+ courseId) == courseId)) {

			String upd_stmt = "";

			// Add availability
			if (available) {
				upd_stmt = "INSERT INTO Project3.CourseAvailability VALUES ("
						+ courseId + ", " + termId + ")";
				// Remove availability
			} else {
				upd_stmt = "DELETE FROM Project3.CourseAvailability WHERE courseId = "
						+ courseId + " AND termId = " + termId;
			}

			result = update(upd_stmt);
		}
		return result;
	}

	public boolean setCourseDemand(int courseId, int count) {
		boolean result = false;

		String query = "SELECT courseId FROM Project3.Demand WHERE courseId = "
				+ courseId;

		// Store history and update
		if (getInteger(query) == courseId) {
			query = "INSERT INTO Project3.DemandHistory SELECT "
					+ "courseId, demand, timestamp FROM Project3.DemandHistory "
					+ "WHERE courseId = " + courseId;
			if (update(query)) {
				query = "UPDATE Project3.Demand SET demand = '" + count
						+ "' WHERE courseId = " + courseId;
				result = update(query);
			}
		}

		// Just insert
		else {
			query = "INSERT INTO Project3.Demand VALUES (" + courseId + ", '"
					+ count + "', NOW())";
			result = update(query);
		}

		return result;
	}

	public HashMap<Integer, Integer> getCourseDemand() {	

		String query = "SELECT courseId,demand FROM Project3.Demand";

		return getIntegerIntegerHashMap(query);

	}

	private static final int PREREQUISITE_TYPE = 1;
}
