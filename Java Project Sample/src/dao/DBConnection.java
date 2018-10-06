package edu.gatech.saad.p3.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
	
	private static DBConnection instance = null;
	private Connection conn = null;
	
	static final String USERNAME = "root";
	static final String PASSWORD = "root";
	static final String HOSTNAME = "localhost";
	static final String PORT 	 = "3306";
	
	private DBConnection() {
	    
		Properties connectionProps = new Properties();
	    connectionProps.put("user", USERNAME);
	    connectionProps.put("password", PASSWORD);
	    
	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://" + HOSTNAME + ":" + PORT + "/", connectionProps);
	    } catch (Exception ex) {
	    	System.out.println("[ERR] DBUtils:DBUtils - " + ex.getMessage());
	    }	    	    
	    
	    if (conn == null) System.out.println("[ERR] DBUtils:DBUtils - Could not get DB connection");
	}
	
	public static DBConnection getInstance() {
		if ( instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}
	
	public Connection getDBConnection() {
		return conn;
	}
}

