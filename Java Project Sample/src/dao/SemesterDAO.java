package edu.gatech.saad.p3.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SemesterDAO extends DAO {

	public int getNextTerm() {
		
		int result = -1;
		
		// Get current date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String curr_date = dateFormat.format(cal.getTime());

		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				String query = "SELECT startDate, termId FROM Project3.Semesters where endDate > " +
						        curr_date + " ORDER BY startDate";
				ResultSet rs = stmt.executeQuery(query);			
				while (rs.next()) {
					if ( rs.getDate(1).after(cal.getTime()) ) { 
						result = rs.getInt(2);
						break;
					}
				}				
			} catch (SQLException e) {
				System.out.println("[ERR] SemesterDAO:getNextTerm - " + e.getMessage());
			}
		}

		return result;
	}
	
	public int getNextSemester() {
		
		int result = -1;
		
		// Get current date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String curr_date = dateFormat.format(cal.getTime());

		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				String query = "SELECT startDate, termId, semesterId FROM Project3.Semesters where endDate > " +
						        curr_date + " ORDER BY startDate";
				ResultSet rs = stmt.executeQuery(query);			
				while (rs.next()) {
					if ( rs.getDate(1).after(cal.getTime()) ) { 
						result = rs.getInt(3);
						break;
					}
				}				
			} catch (SQLException e) {
				System.out.println("[ERR] SemesterDAO:getNextTerm - " + e.getMessage());
			}
		}

		return result;
	}
}
