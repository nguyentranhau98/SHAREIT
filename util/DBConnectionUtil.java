package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnectionUtil {
	private static Connection conn = null;
	private static String USERNAME;
	private static String URL;
	private static String PASSWORD;
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Properties properties = PropertiesUtil.readProperties();
			URL = properties.getProperty("url");
			USERNAME = properties.getProperty("user");
			PASSWORD = properties.getProperty("password");
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(PreparedStatement pst) {
		if(pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(Statement st, Connection conn, ResultSet rs) {
		DBConnectionUtil.close(rs);
		DBConnectionUtil.close(st); 
		DBConnectionUtil.close(conn);
	}
	public static void close(PreparedStatement pst, Connection conn, ResultSet rs) {
		DBConnectionUtil.close(rs);
		DBConnectionUtil.close(pst); 
		DBConnectionUtil.close(conn);
	}
	public static void close(PreparedStatement pst, Connection conn) {
		DBConnectionUtil.close(pst); 
		DBConnectionUtil.close(conn);
	}
	public static void main(String[] args) {
		System.out.println(DBConnectionUtil.getConnection());
	}
}
