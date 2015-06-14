package edu.paymentus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String CONNSTRING = "jdbc:oracle:thin:@localhost:1521:mkyong";
	public static final String USERNAME = "user";
	public static final String PASSWORD = "pass";
	
	public static Connection getConnection(){
		Connection conn = null;
		
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(CONNSTRING, USERNAME,PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
}
