package com.hari.billing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectivity {

	public static final String DATABASE_NAME="billingsystem";
	public static final String DRIVER_NAME="com.mysql.jdbc.Driver";
	public static final String URL="jdbc:mysql://localhost/";
	public static final String USERNAME= "root";
	public static final String PASSWORD="";
	
	public static Connection getConnection () throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER_NAME);
		return DriverManager.getConnection(URL+DATABASE_NAME,USERNAME,PASSWORD);
	}
}
