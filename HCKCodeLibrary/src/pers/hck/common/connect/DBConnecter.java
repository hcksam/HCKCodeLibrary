package pers.hck.common.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnecter {
	private String host = "127.0.0.1";
	private String dbName = "test";
	private String enCoding = "utf8";
	private String user = "admin";
	private String password = "admin";

	private Connection connection = null;

	public DBConnecter() {
		connectDB();
	}

	public DBConnecter(String dbName) {
		this.dbName = dbName;
		connectDB();
	}

	public DBConnecter(String host, String dbName) {
		this.host = host;
		this.dbName = dbName;
		connectDB();
	}

	public void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + host
					+ "/" + dbName + "?useUnicode=true&characterEncoding="
					+ enCoding, user, password);
		} catch (ClassNotFoundException ce) {
			System.out.println("JDBC Driver Class Not Found: " + ce.toString());
		} catch (Exception e) {
			System.out.println("Exception for connect DB: " + e.toString());
		}
	}
	
	public boolean execute(String query){
		try {
			Statement statement = connection.createStatement();
			return statement.execute(query);
			
		} catch (SQLException se) {
			System.out.println("Execute SQL ERROR!");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int executeUpdate(String query){
		try {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(query);
			
		} catch (SQLException se) {
			System.out.println("Execute SQL ERROR!");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ResultSet executeQuery(String query){
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery(query);
			
		} catch (SQLException se) {
			System.out.println("Execute SQL ERROR!");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void closeDB() {
		try {
			connection.close();
		} catch (Exception e) {
			System.out.println("Exception for close DB: " + e.toString());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getEnCoding() {
		return enCoding;
	}

	public void setEnCoding(String enCoding) {
		this.enCoding = enCoding;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void main(String[] args) {
		String dbName = "phoneartshop";
		DBConnecter db = new DBConnecter(dbName);

		String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='"
				+ dbName + "' ";
		System.out.println(query);
		
		try{
		ResultSet rs = db.executeQuery(query);
		while (rs.next()){
			System.out.println(rs.getString("TABLE_NAME"));
		}
		}catch (Exception e){
			e.printStackTrace();
		}

		db.closeDB();
	}
}
