package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Categories;
import model.bean.News;
import util.DBConnectionUtil;
import util.DefineUtil;

public class NewDAO {
	private Connection conn;
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;
	public ArrayList<News> getItems() {
		ArrayList<News> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT n.id, cat_id , user_id, n.name,"+
				" description, content, date_create, picture, counter, c.id_parent, c.cat_name AS catName, u.avatar, u.fullname"+
				" FROM news AS n"+
				" INNER JOIN categories AS c ON c.id = cat_id"+
				" INNER JOIN users AS u ON u.id = user_id";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				News objItem = new News(rs.getInt("id"), new Categories(rs.getInt("cat_id"), rs.getString("catName"), rs.getInt("id_parent")), 
						rs.getString("fullname"), rs.getString("avatar"), rs.getString("name"), 
						rs.getString("description"), rs.getString("content"), rs.getTimestamp("date_create"), 
						rs.getString("picture"), rs.getInt("counter"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(st, conn, rs);
		}
		return listItem;
	}
	public ArrayList<News> getItems(int num) {
		ArrayList<News> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT n.id, cat_id , user_id, n.name,"+
				" description, content, date_create, picture, counter, c.id_parent, c.cat_name AS catName, u.avatar, u.fullname"+
				" FROM news AS n"+
				" INNER JOIN categories AS c ON c.id = cat_id"+
				" INNER JOIN users AS u ON u.id = user_id ORDER BY date_create DESC LIMIT ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, num);
			rs = pst.executeQuery();
			while(rs.next()) {
				News objItem = new News(rs.getInt("id"), new Categories(rs.getInt("cat_id"), rs.getString("catName"), rs.getInt("id_parent")), 
						rs.getString("fullname"), rs.getString("avatar"), rs.getString("name"), 
						rs.getString("description"), rs.getString("content"), rs.getTimestamp("date_create"), 
						rs.getString("picture"), rs.getInt("counter"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}
	public ArrayList<News> getPopularItems() {
		ArrayList<News> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT n.id, cat_id , user_id, n.name,"+
				" description, content, date_create, picture, counter, c.id_parent, c.cat_name AS catName, u.avatar, u.fullname"+
				" FROM news AS n"+
				" INNER JOIN categories AS c ON c.id = cat_id"+
				" INNER JOIN users AS u ON u.id = user_id ORDER BY counter DESC LIMIT 4";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				News objItem = new News(rs.getInt("id"), new Categories(rs.getInt("cat_id"), rs.getString("catName"), rs.getInt("id_parent")), 
						rs.getString("fullname"), rs.getString("avatar"), rs.getString("name"), 
						rs.getString("description"), rs.getString("content"), rs.getTimestamp("date_create"), 
						rs.getString("picture"), rs.getInt("counter"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(st, conn, rs);
		}
		return listItem;
	}
	public ArrayList<News> getNewItems(int num) {
		ArrayList<News> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT n.id, cat_id , user_id, n.name,"+
				" description, content, date_create, picture, counter, c.id_parent, c.cat_name AS catName, u.avatar, u.fullname"+
				" FROM news AS n"+
				" INNER JOIN categories AS c ON c.id = cat_id"+
				" INNER JOIN users AS u ON u.id = user_id ORDER BY date_create DESC LIMIT ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, num);
			rs = pst.executeQuery();
			while(rs.next()) {
				News objItem = new News(rs.getInt("id"), new Categories(rs.getInt("cat_id"), rs.getString("catName"), rs.getInt("id_parent")), 
						rs.getString("fullname"), rs.getString("avatar"), rs.getString("name"), 
						rs.getString("description"), rs.getString("content"), rs.getTimestamp("date_create"), 
						rs.getString("picture"), rs.getInt("counter"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}
	public ArrayList<News> getRelatedItems(int id, int catid) {
		ArrayList<News> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT n.id, cat_id , user_id, n.name,"+
				" description, content, date_create, picture, counter, c.id_parent, c.cat_name AS catName, u.avatar, u.fullname"+
				" FROM news AS n"+
				" INNER JOIN categories AS c ON c.id = cat_id"+
				" INNER JOIN users AS u ON u.id = user_id WHERE cat_id = ? AND n.id != ? LIMIT 3";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, catid);
			pst.setInt(2, id);
			rs = pst.executeQuery();
			while(rs.next()) {
				News objItem = new News(rs.getInt("id"), new Categories(rs.getInt("cat_id"), rs.getString("catName"), rs.getInt("id_parent")), 
						rs.getString("fullname"), rs.getString("avatar"), rs.getString("name"), 
						rs.getString("description"), rs.getString("content"), rs.getTimestamp("date_create"), 
						rs.getString("picture"), rs.getInt("counter"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}
	public ArrayList<News> getCatItems(int catid) {
		ArrayList<News> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT n.id, cat_id , user_id, n.name,"+
				" description, content, date_create, picture, counter, c.id_parent, c.cat_name AS catName, u.avatar, u.fullname"+
				" FROM news AS n"+
				" INNER JOIN categories AS c ON c.id = cat_id"+
				" INNER JOIN users AS u ON u.id = user_id WHERE cat_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, catid);
			rs = pst.executeQuery();
			while(rs.next()) {
				News objItem = new News(rs.getInt("id"), new Categories(rs.getInt("cat_id"), rs.getString("catName"), rs.getInt("id_parent")), 
						rs.getString("fullname"), rs.getString("avatar"), rs.getString("name"), 
						rs.getString("description"), rs.getString("content"), rs.getTimestamp("date_create"), 
						rs.getString("picture"), rs.getInt("counter"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}
	public int numberOfItems() {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM news";
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
	public ArrayList<News> getItemsPagination(int offset) {
		ArrayList<News> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT n.id, cat_id , user_id, n.name,"+
				" description, content, date_create, picture, counter, c.id_parent, c.cat_name AS catName, u.avatar, u.fullname"+
				" FROM news AS n"+
				" INNER JOIN categories AS c ON c.id = cat_id"+
				" INNER JOIN users AS u ON u.id = user_id ORDER BY n.id DESC LIMIT ?,?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()) {
				News objItem = new News(rs.getInt("id"), new Categories(rs.getInt("cat_id"), rs.getString("catName"), rs.getInt("id_parent")), 
						rs.getString("fullname"), rs.getString("avatar"), rs.getString("name"), 
						rs.getString("description"), rs.getString("content"), rs.getTimestamp("date_create"), 
						rs.getString("picture"), rs.getInt("counter"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}
	public ArrayList<News> getItemsPagination(int offset, int cat_id) {
		ArrayList<News> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT n.id, cat_id , user_id, n.name,"+
				" description, content, date_create, picture, counter, c.id_parent, c.cat_name AS catName, u.avatar, u.fullname"+
				" FROM news AS n"+
				" INNER JOIN categories AS c ON c.id = cat_id"+
				" INNER JOIN users AS u ON u.id = user_id WHERE cat_id = ? ORDER BY n.id DESC LIMIT ?,?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, cat_id);
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()) {
				News objItem = new News(rs.getInt("id"), new Categories(rs.getInt("cat_id"), rs.getString("catName"), rs.getInt("id_parent")), 
						rs.getString("fullname"), rs.getString("avatar"), rs.getString("name"), 
						rs.getString("description"), rs.getString("content"), rs.getTimestamp("date_create"), 
						rs.getString("picture"), rs.getInt("counter"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}
	public int numberOfItems(int cid) {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM news WHERE cat_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, cid);
			rs = pst.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return count;
	}
	public void increaseView(int nid) {
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE news SET counter = counter + 1 WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, nid);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
	}
	public News getItem(int nid) {
		News newItem = new News();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT n.id, cat_id , user_id, n.name,"+
				" description, content, date_create, picture, counter, c.id_parent, c.cat_name AS catName, u.avatar, u.fullname"+
				" FROM news AS n"+
				" INNER JOIN categories AS c ON c.id = cat_id"+
				" INNER JOIN users AS u ON u.id = user_id WHERE n.id = ? LIMIT 1";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, nid);
			rs = pst.executeQuery();
			if(rs.next()) {
				newItem = new News(rs.getInt("id"), new Categories(rs.getInt("cat_id"), rs.getString("catName"), rs.getInt("id_parent")), 
						rs.getString("fullname"), rs.getString("avatar"), rs.getString("name"), 
						rs.getString("description"), rs.getString("content"), rs.getTimestamp("date_create"), 
						rs.getString("picture"), rs.getInt("counter"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return newItem;
	}
	public int numberOfItems(String searchInfo) {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM news WHERE name LIKE ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+searchInfo+"%");
			rs = pst.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return count;
	}
	public ArrayList<News> getItemsPagination(int offset, String searchInfo) {
		ArrayList<News> listItem = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT n.id, cat_id , user_id, n.name,"+
				" description, content, date_create, picture, counter, c.id_parent, c.cat_name AS catName, u.avatar, u.fullname"+
				" FROM news AS n"+
				" INNER JOIN categories AS c ON c.id = cat_id"+
				" INNER JOIN users AS u ON u.id = user_id WHERE name LIKE ? LIMIT ?,?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+searchInfo+"%");
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while(rs.next()) {
				News objItem = new News(rs.getInt("id"), new Categories(rs.getInt("cat_id"), rs.getString("catName"), rs.getInt("id_parent")), 
						rs.getString("fullname"), rs.getString("avatar"), rs.getString("name"), 
						rs.getString("description"), rs.getString("content"), rs.getTimestamp("date_create"), 
						rs.getString("picture"), rs.getInt("counter"));
				listItem.add(objItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return listItem;
	}
	public int addItem(News objNew, int uid) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO news(cat_id, user_id, name, description, content, picture, counter) VALUES(?,?,?,?,?,?,?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, objNew.getCat().getId());
			pst.setInt(2, uid);
			pst.setString(3, objNew.getName());
			pst.setString(4, objNew.getDesc());
			pst.setString(5, objNew.getContent());
			pst.setString(6, objNew.getPicture());
			pst.setInt(7, objNew.getCounter());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}
	public int editItem(News objNew, int uid) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE news SET cat_id = ?, user_id = ?, name = ?, description = ?, content = ?, picture = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, objNew.getCat().getId());
			pst.setInt(2, uid);
			pst.setString(3, objNew.getName());
			pst.setString(4, objNew.getDesc());
			pst.setString(5, objNew.getContent());
			pst.setString(6, objNew.getPicture());
			pst.setInt(7, objNew.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}
	public int delItem(int nid) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "DELETE FROM news WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, nid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}
	public int numberOfSearchItems(String searchInfo) {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM news WHERE name LIKE ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%"+searchInfo+"%");
			rs = pst.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return count;
	}
	public String getName(int nid) {
		String name = "";
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT name FROM news WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, nid);
			rs = pst.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn, rs);
		}
		return name;
	}
}
