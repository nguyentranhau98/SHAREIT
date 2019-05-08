package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Comments;
import model.bean.News;
import model.dao.CategoryDAO;
import model.dao.CommentDAO;
import model.dao.NewDAO;
import model.dao.UserDAO;
import util.AuthUtil;

public class AdminIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO = new UserDAO();
	private CategoryDAO catDAO = new CategoryDAO();
	private NewDAO newDAO = new NewDAO();
	private CommentDAO comDAO = new CommentDAO();
       
    public AdminIndexController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
			int nUser = userDAO.numberOfUsers();
			int nCat = catDAO.numberOfCats();
			int nNew = newDAO.numberOfItems();
			int nCom = comDAO.numberOfComments();
			ArrayList<News> latestNews = newDAO.getItems(5);
			ArrayList<Comments> latestComments = comDAO.getItems(5);
			request.setAttribute("nUser", nUser);
			request.setAttribute("nCat", nCat);
			request.setAttribute("nNew", nNew);
			request.setAttribute("nCom", nCom);
			request.setAttribute("latestNews", latestNews);
			request.setAttribute("latestComments", latestComments);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/index/index.jsp");
			rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
