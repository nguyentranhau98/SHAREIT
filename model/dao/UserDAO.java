package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Users;
import util.DBConnectionUtil;
import util.DefineUtil;

public class UserDAO {
	private Connection conn;
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;

	public int numberOfUsers() {
		
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM users";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(st, conn, rs);
		}
		return count;
	}

	public ArrayList<Users> getItemsPagination(int offset) {
		ArrayList<Users> userList = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM users "
					+ " ORDER BY id DESC LIMIT ?,?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()){
				Users obj = new Users(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("fullname"), rs.getString("role"), rs.getString("avatar"));
				userList.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBConnectionUtil.close(pst, conn, rs);;
		}
		return userList;
	}

	public int addItem(Users objUser) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO users(username, password, fullname, role, avatar) VALUES(?,?,?,?,?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, objUser.getUsername());
			pst.setString(2, objUser.getPassword());
			pst.setString(3, objUser.getFullname());
			pst.setString(4, objUser.getRole());
			pst.setString(5, objUser.getAvatar());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public Users getItem(int uid) {
		Users objUser = null;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM users WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			if(rs.next()) {
				objUser = new Users(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("fullname"), rs.getString("role"), rs.getString("avatar"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return objUser;
	}

	public int editItem(Users objUser) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE users SET username = ?, password = ?, fullname = ?, role = ?, avatar = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, objUser.getUsername());
			pst.setString(2, objUser.getPassword());
			pst.setString(3, objUser.getFullname());
			pst.setString(4, objUser.getRole());
			pst.setString(5, objUser.getAvatar());
			pst.setInt(6, objUser.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public int delItem(int uid) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "DELETE FROM users WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public Users getItemByUsernamePassword(String username, String password) {
		Users objUser = null;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if(rs.next()) {
				objUser = new Users(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("fullname"), rs.getString("role"), rs.getString("avatar"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return objUser;
	}

}
