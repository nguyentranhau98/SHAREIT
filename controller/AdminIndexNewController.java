package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.News;
import model.dao.NewDAO;
import util.AuthUtil;
import util.DefineUtil;

public class AdminIndexNewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private NewDAO newDAO = new NewDAO();   
    public AdminIndexNewController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		int numberOfNews = newDAO.numberOfItems();
		int numberOfPages = (int)Math.ceil((float)numberOfNews / DefineUtil.NUMBER_PER_PAGE);
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}
		if(currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}
		int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE; 
		ArrayList<News> listNews = newDAO.getItemsPagination(offset);
		
		request.setAttribute("numberOfNews", numberOfNews);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("offset", offset);
		request.setAttribute("listNews", listNews);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/news/news.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
