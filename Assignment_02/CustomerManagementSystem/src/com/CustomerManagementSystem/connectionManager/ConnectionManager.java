package com.CustomerManagementSystem.connectionManager;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	private static String DATABASE_FILE_PATH = "src/database.properties";
	private static Connection connection = null;
	public static Connection getConnection(){
		try {
			Properties properties = new Properties();
			FileInputStream propertyFile = new FileInputStream(DATABASE_FILE_PATH);
			properties.load(propertyFile);
			connection = DriverManager.getConnection(properties.getProperty("url"),properties.getProperty("username"),properties.getProperty("password"));
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}
	public static void closeConnection() {
		try {
			connection.close();
			connection = null;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
