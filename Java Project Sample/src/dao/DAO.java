package edu.gatech.saad.p3.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

public abstract class DAO {
	
	public DAO() {		
		DBConn = DBConnection.getInstance().getDBConnection();
	}

	protected String getString(String aQuery) {
		String result = null;
		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				ResultSet rs = stmt.executeQuery(aQuery);			
				if (rs.next()) {
					result = rs.getString(1);
				}				
			} catch (SQLException e) {
				System.out.println("[ERR] Query: " + aQuery);
				System.out.println("[ERR] DAO:getString - " + e.getMessage());
			}
		}
		return result;
	}
	
	protected boolean getBoolean(String aQuery) {
		boolean result = false;
		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				ResultSet rs = stmt.executeQuery(aQuery);				
				if (rs.next()) {
					result = rs.getBoolean(1);
				}				
			} catch (SQLException e) {
				System.out.println("[ERR] Query: " + aQuery);
				System.out.println("[ERR] DAO:getBoolean - " + e.getMessage());
			}
		}
		return result;
	}
	
	protected int getInteger(String aQuery) {
		int result = -1;
		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				ResultSet rs = stmt.executeQuery(aQuery);				
				if (rs.next()) {
					result = rs.getInt(1);
				}				
			} catch (SQLException e) {
				System.out.println("[ERR] Query: " + aQuery);
				System.out.println("[ERR] DAO:getInteger - " + e.getMessage());
			}
		}
		return result;
	}
	
	protected Vector<Integer> getIntegerVector(String aQuery) {
		Vector<Integer> result = null;
		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				ResultSet rs = stmt.executeQuery(aQuery);				
				result = new Vector<Integer>();
				while (rs.next()) {
					result.add(new Integer(rs.getInt(1)));
				}
				if (result.size() == 0) result = null;
			} catch (SQLException e) {
				System.out.println("[ERR] DAO:getIntVector - " + e.getMessage());
			}
		}
		return result;		
	}
	
	protected HashMap<Integer,Integer> getIntegerHashMap(String aQuery) {
		HashMap<Integer,Integer> result = null;
		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				ResultSet rs = stmt.executeQuery(aQuery);				
				result = new HashMap<Integer,Integer>();
				while (rs.next()) {
					result.put(new Integer(rs.getInt(1)), new Integer(rs.getInt(2)));
				}
				if (result.size() == 0) result = null;
			} catch (SQLException e) {
				System.out.println("[ERR] DAO:ggetHashMap - " + e.getMessage());
			}
		}
		return result;		
	}
	
	protected HashMap<Integer,String> getIntegerStringHashMap(String aQuery) {
		HashMap<Integer,String> result = null;
		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				ResultSet rs = stmt.executeQuery(aQuery);				
				result = new HashMap<Integer,String>();
				while (rs.next()) {
					result.put(new Integer(rs.getInt(1)), rs.getString(2));
				}
				if (result.size() == 0) result = null;
			} catch (SQLException e) {
				System.out.println("[ERR] DAO:ggetHashMap - " + e.getMessage());
			}
		}
		System.out.println("getIntegerStringHashMap==="+result);
		return result;		
	}
	
	protected HashMap<Integer,Integer> getIntegerIntegerHashMap(String aQuery) {
		HashMap<Integer,Integer> result = null;
		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				ResultSet rs = stmt.executeQuery(aQuery);				
				result = new HashMap<Integer,Integer>();
				while (rs.next()) {
					result.put(new Integer(rs.getInt(1)), rs.getInt(2));
				}
				if (result.size() == 0) result = null;
			} catch (SQLException e) {
				System.out.println("[ERR] DAO:ggetHashMap - " + e.getMessage());
			}
		}
		return result;		
	}
	
	protected boolean update(String aUpdate) {
		boolean result = false;
		if (DBConn != null) {
			try {
				Statement stmt = DBConn.createStatement();
				stmt.addBatch(aUpdate);
				int [] updateCounts = stmt.executeBatch();
				if ( updateCounts.length > 0 && updateCounts[0] > 0 ) result = true;
			} catch (SQLException e) {
				System.out.println("[ERR] DAO:update - " + e.getMessage());
			}
		}
		return result;		
	}
	
	protected Connection DBConn = null;
}
