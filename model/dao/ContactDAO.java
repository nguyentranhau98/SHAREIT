package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Contacts;
import util.DBConnectionUtil;
import util.DefineUtil;

public class ContactDAO {
	private Connection conn;
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;
	public int addItem(Contacts objContact) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO contacts(name, email, website, message) VALUES (?,?,?,?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, objContact.getGroup_name());
			pst.setString(2, objContact.getEmail());
			pst.setString(3, objContact.getWebsite());
			pst.setString(4, objContact.getMessage());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}
	public int numberOfItems() {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM contacts";
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
	public ArrayList<Contacts> getItemsPagination(int offset) {
		ArrayList<Contacts> contactList = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM contacts"
					+ " ORDER BY id DESC LIMIT ?,?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()) {
				Contacts objContact = new Contacts(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("website"), rs.getString("message"));
				contactList.add(objContact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return contactList;
	}
	public int delItem(int cid) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "DELETE FROM contacts WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, cid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

}
