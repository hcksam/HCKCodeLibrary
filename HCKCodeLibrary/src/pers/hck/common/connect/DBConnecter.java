package pers.hck.common.connect;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnecter {
	private String host = "127.0.0.1";
	private String port = "3306";
	private String dbName = "test";
	private String enCoding = "utf8";
	private String user = "admin";
	private String password = "admin";
	private String sid = null;

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

	public DBConnecter(String host, String port, String user, String password, String sid) {
		super();
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.sid = sid;
		connectDB();
	}

	public void connectDB() {
		try {
			if (sid == null){
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(
						"jdbc:mysql://"
						+ host + ":"
						+ port + "/"
						+ dbName + "?useUnicode=true&characterEncoding="
						+ enCoding, user, password);
			}else{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection(
						"jdbc:oracle:thin:@"
						+ host + ":"
						+ port + ":"
						+ sid + "?useUnicode=true&characterEncoding="
						+ enCoding, user, password);
			}
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

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
	public static void setTnsAdmin() {
	    String tnsAdmin = System.getenv("TNS_ADMIN");
	    if (tnsAdmin == null) {
	        String oracleHome = System.getenv("ORACLE_HOME");
	        if (oracleHome == null) {
	        	System.out.println("Failed for setting TNS by use env variables");
	            return; //failed to find any useful env variables
	        }
	        tnsAdmin = oracleHome + File.separatorChar + "network" + File.separatorChar + "admin";
	    }
//	    System.setProperty("oracle.net.tns_admin", tnsAdmin);
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
