package com.MyAccount.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseUtil extends TestBase {

	public static Connection conn;

	public void DataBase_Open() throws SQLException, ClassNotFoundException {
		String host = properties.getProperty("D_Host");
		String InstanceName = properties.getProperty("D_InstanceName");
		String port = properties.getProperty("D_Port");
		String databaseName = properties.getProperty("D_DatabaseName");
		String userName = properties.getProperty("D_UserName");
		String password = properties.getProperty("D_Password");

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	
			conn = DriverManager.getConnection("jdbc:sqlserver://" + host + ";instanceName=" + InstanceName
					+ ";portNumber="+port+";databaseName="+databaseName, userName, password);

			System.out.println("Database connected successfully.");
		} catch (Exception e) {
			System.out.println("Database not connected.");
			System.out.println(e);
		}
	}

	
	public ResultSet rs;
	public String strQuery1;
	public ResultSet executeQuery(String strQuery) throws SQLException, ClassNotFoundException {
		try {
			// Executing SQL query and fetching the result
			Statement st = conn.createStatement();
			strQuery1 = strQuery;
			rs = st.executeQuery(strQuery1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public String selectData(String column) throws SQLException, ClassNotFoundException {
		executeQuery(strQuery1);
		ResultSet rsNew = rs;
		String result = null;
		try {
			while (rsNew.next()) {
				result = rsNew.getString(column);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	public void executeUpdate(String strQuery) throws SQLException, ClassNotFoundException {
		try {
			// Executing SQL query and fetching the result
			Statement st = conn.createStatement();
		    st = conn.createStatement();
		    String sql = strQuery;
		    st.executeUpdate(sql);
			System.out.println("executed update query.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void DataBase_close() throws SQLException, ClassNotFoundException {
		conn.close();
	}

}
