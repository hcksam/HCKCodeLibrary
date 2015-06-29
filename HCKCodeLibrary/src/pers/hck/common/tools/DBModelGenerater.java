package pers.hck.common.tools;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import pers.hck.common.connect.DBConnecter;

public class DBModelGenerater {
	private String dbName = "test";
	private ArrayList<String> tableNames = new ArrayList<String>();
	private HashMap<String, ArrayList<String>> tableColumnMap = new HashMap<String, ArrayList<String>>();

	public DBModelGenerater() {
		setVariable();
	}

	public DBModelGenerater(String dbName) {
		this.dbName = dbName;
		setVariable();
	}

	public void setVariable() {
		DBConnecter db = new DBConnecter(dbName);

		String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='"
				+ dbName + "' ";
		System.out.println(query);

		try {
//			ResultSet rs = db.getStatement().executeQuery(query);
			ResultSet rs = db.executeQuery(query);
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				tableNames.add(tableName);
				System.out.println(tableName);
				
				//DBConnecter db2 = new DBConnecter(dbName);
				String query2 = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='"
						+ dbName + "' AND TABLE_NAME='" + tableName + "'";
//				ResultSet rs2 = db.getStatement().executeQuery(query2);
				ResultSet rs2 = db.executeQuery(query2);
				ArrayList<String> columnNames = new ArrayList<String>();
				while (rs2.next()) {
					columnNames.add(rs2.getString("COLUMN_NAME"));
					System.out.print(rs2.getString("COLUMN_NAME") + "|");
				}
				tableColumnMap.put(tableName, columnNames);
				System.out.println();
				//db2.closeDB();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		db.closeDB();
	}
	
	public void generateDBModelHandler(){
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DBModelGenerater("phoneartshop");
	}

}
