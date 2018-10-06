package edu.gatech.saad.p3.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.fabric.xmlrpc.base.Array;

import edu.gatech.saad.p3.model.Demand;
import edu.gatech.saad.p3.model.DemandHistory;
import edu.gatech.saad.p3.model.PrefferedSchedule;

public class UsersDAO extends DAO {

	public String getName(int userId) {
		return getString("SELECT CONCAT(firstName,' ',lastName) FROM Project3.Users "
				+ "WHERE userId = " + userId);
	}

	public int getUserId(String username, String pwHash) {
		int result = -1;
		if (username != null && username.length() > 0 && pwHash != null
				&& pwHash.length() > 0) {
			result = getInteger("SELECT userId FROM Project3.Users WHERE username = '"
					+ username + "' AND pwHash = '" + pwHash + "'");
		}
		return result;
	}

	public boolean isAdmin(int userId) {
		boolean result = false;
		if (getInteger("SELECT userId FROM Project3.UserRoles WHERE userId = "
				+ userId + " and roleId = " + ADMIN_ROLE) == userId)
			result = true;
		return result;
	}

	private static final int ADMIN_ROLE = 9;

	public Map<Integer, List<PrefferedSchedule>> getStuDemandsHist() {
		String aQuery = "Select userId, PreferredSchedule from Project3.PreferredScheduleHistory";

		Map<Integer, List<PrefferedSchedule>> result = null;
		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				ResultSet rs = stmt.executeQuery(aQuery);
				result = new HashMap<Integer, List<PrefferedSchedule>>();

				while (rs.next()) {
					int uid = rs.getInt("userId");
					String preferredSchedule = rs
							.getString("PreferredSchedule");

					List<PrefferedSchedule> data = result.get(uid);
					if (data == null) {
						data = new ArrayList<PrefferedSchedule>();
						result.put(uid, data);

					}
					PrefferedSchedule dem = new PrefferedSchedule();
					dem.setUserId(uid);
					dem.setPrefferedSchedule(preferredSchedule);

					data.add(dem);

				}
				if (result.size() == 0)
					result = null;
			} catch (SQLException e) {
				System.out.println("[ERR] getStuDemandsHist - " + e.getMessage());
			}
		}
		return result;

	}

}
