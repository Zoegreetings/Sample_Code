package edu.gatech.saad.p3.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.saad.p3.model.StudentDemand;
import edu.gatech.saad.p3.model.StudentRecoData;

public class RecoDao extends DAO {

	public Map<Integer, StudentRecoData> getRecos() {

		String query = Sql.SqlData.get("STUDENT_RECO_DATA_ALL");

		Map<Integer, StudentRecoData> result = new HashMap<Integer, StudentRecoData>();

		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				ResultSet rs = stmt.executeQuery(query);

				while (rs.next()) {
					Integer uid = rs.getInt("userId");
					String preference = rs.getString("courseList");
					String[] arr = preference.split(",");
					StudentRecoData reco = new StudentRecoData();
					reco.setSemId(DAOFacade.getInstance().getNextSemester());
					reco.setUserId(uid);

					List<Integer> cidList = new ArrayList<Integer>();
					for (int i = 0; i < arr.length; i++) {
						cidList.add(Integer.valueOf(arr[i]));
					}
					reco.setCourseIdList(cidList);
					result.put(uid, reco);
				}
			} catch (SQLException e) {
				System.out.println("[ERR] getRecos - " + e.getMessage());
			}
		}
		return result;

	}

	public boolean insertReco(int userId, String courseList) {

		boolean result = false;
		String query = "INSERT INTO Project3.Recommendations VALUES ("
				+ userId + "," + "'" + courseList + "', NOW())";
		
		System.out.println(query);

		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				stmt.addBatch(query);
				int[] updateCounts = stmt.executeBatch();
				if (updateCounts.length > 0 && updateCounts[0] > 0)
					result = true;
			} catch (SQLException e) {
				System.out.println("[ERR] insertReco - " + e.getMessage());
			}
		}
		return result;
	}

	public boolean updateReco(int userId, String courseList) {

		boolean result = false;
		String query = "update Project3.Recommendations set courseList = '"
				+ courseList + "' WHERE userId = " + userId;
		System.out.println(query);

		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				stmt.addBatch(query);
				int[] updateCounts = stmt.executeBatch();
				if (updateCounts.length > 0 && updateCounts[0] > 0)
					result = true;
			} catch (SQLException e) {
				System.out.println("[ERR] updateReco - " + e.getMessage());
			}
		}
		return result;

	}

}
