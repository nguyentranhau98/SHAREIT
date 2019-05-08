package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Categories;
import util.DBConnectionUtil;
import util.DefineUtil;

public class CategoryDAO {
	private Connection conn;
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public ArrayList<Categories> getItems() {
		ArrayList<Categories> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM categories";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				Categories objItem = new Categories(rs.getInt("id"), rs.getString("cat_name"), rs.getInt("id_parent"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(st, conn, rs);
		}
		return listItem;
	}

	public ArrayList<Categories> getSubItems(int cid) {
		ArrayList<Categories> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM categories WHERE id_parent = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, cid);
			rs = pst.executeQuery();
			while(rs.next()) {
				Categories objItem = new Categories(rs.getInt("id"), rs.getString("cat_name"), rs.getInt("id_parent"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}

	public Categories getItem(int cid) {
		Categories currentCat = new Categories();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM categories WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, cid);
			rs = pst.executeQuery();
			if(rs.next()) {
				currentCat = new Categories(rs.getInt("id"), rs.getString("cat_name"), rs.getInt("id_parent"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return currentCat;
	}

	public int numberOfCats() {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM categories";
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

	public int addItem(Categories objCat) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO categories(cat_name, id_parent) VALUES(?,?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, objCat.getCat_name());
			pst.setInt(2, objCat.getId_parent());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public int editItem(Categories objCat) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE categories SET cat_name = ?, id_parent = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, objCat.getCat_name());
			pst.setInt(2, objCat.getId_parent());
			pst.setInt(3, objCat.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public int delItem(int cid) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "DELETE FROM categories WHERE id = ?";
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

	public ArrayList<Categories> getItemsPagination(int offset) {
		ArrayList<Categories> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM categories"
					+ " ORDER BY id WHERE id_parent = 0 DESC LIMIT ?,?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()) {
				Categories objItem = new Categories(rs.getInt("id"), rs.getString("cat_name"), rs.getInt("id_parent"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}
}
