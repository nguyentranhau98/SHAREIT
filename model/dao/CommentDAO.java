package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Categories;
import model.bean.Comments;
import model.bean.Comments;
import model.bean.News;
import util.DBConnectionUtil;
import util.DefineUtil;

public class CommentDAO {
	private Connection conn;
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;

	public ArrayList<Comments> getComments(int nid) {
		ArrayList<Comments> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT id_com, text, user , date_create"+
				" FROM comments"+
				" WHERE id_new = ? AND status = 1 AND id_parent = 0"+
				" ORDER BY date_create ASC";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, nid);
			rs = pst.executeQuery();
			while(rs.next()) {
				Comments objItem = new Comments(rs.getInt("id_com"), nid, rs.getString("user"), rs.getString("text"), 1, rs.getTimestamp("date_create"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}
	public ArrayList<Comments> getSubComments(int nid, int cid) {
		ArrayList<Comments> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT id_com, text, user , date_create"+
				" FROM comments"+
				" WHERE id_new = ? AND status = 1 AND id_parent = ?"+
				" ORDER BY date_create ASC";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, nid);
			pst.setInt(2, cid);
			rs = pst.executeQuery();
			while(rs.next()) {
				Comments objItem = new Comments(rs.getInt("id_com"), 0, rs.getString("user"), rs.getString("text"), 1, rs.getTimestamp("date_create"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}

	public int numberOfComments(int nid) {
		int num = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) as count"+
				" FROM comments"+
				" WHERE id_new = ?"+
				" ORDER BY date_create ASC";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, nid);
			rs = pst.executeQuery();
			if(rs.next()) {
				num = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return num;
	}

	public int addComment(Comments comments) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO comments(text, id_new, user, status, id_parent) VALUES(?,?,?,?,0)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, comments.getText());
			pst.setInt(2, comments.getId_new());
			pst.setString(3, comments.getUser());
			pst.setInt(4, comments.getStatus());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}
	public int addSubComment(Comments comments, int cid) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO comments(text, id_new, user, status, id_parent) VALUES(?,?,?,?,?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, comments.getText());
			pst.setInt(2, comments.getId_new());
			pst.setString(3, comments.getUser());
			pst.setInt(4, comments.getStatus());
			pst.setInt(5, cid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public int numberOfComments() {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM comments";
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

	public ArrayList<Comments> getItems(int num) {
		ArrayList<Comments> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT id_com, text, id_new, user , date_create"+
				" FROM comments"+
				" ORDER BY date_create ASC"+
				" LIMIT ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, num);
			rs = pst.executeQuery();
			while(rs.next()) {
				Comments objItem = new Comments(rs.getInt("id_com"), rs.getInt("id_new"), rs.getString("user"), rs.getString("text"), 1, rs.getTimestamp("date_create"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}

	public ArrayList<Comments> getItemsPagination(int offset) {
		ArrayList<Comments> commentList = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM comments"
					+ " ORDER BY id_com DESC LIMIT ?,?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()) {
				Comments objComment = new Comments(rs.getInt("id_com"), rs.getInt("id_new"), rs.getString("user"), rs.getString("text"), rs.getInt("status"), rs.getTimestamp("date_create"));
				commentList.add(objComment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return commentList;
	}

	public int updateStatus(int stt, int id_com) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE comments SET status = ? WHERE id_com = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, stt);
			pst.setInt(2, id_com);
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
		String sql = "DELETE FROM comments WHERE id_com = ?";
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
