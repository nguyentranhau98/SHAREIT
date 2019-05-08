package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;

import model.bean.News;
import model.dao.NewDAO;
import util.AuthUtil;
import util.DefineUtil;

public class AdminSearchNewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private NewDAO newDAO = new NewDAO();   
    public AdminSearchNewController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String searchInfo = request.getParameter("searchInfo");
		if(request.getParameter("searchInfo") != null) {
			searchInfo = Jsoup.parse(request.getParameter("searchInfo")).text();
		} else {
			searchInfo = (String)session.getAttribute("searchInfo");
		}
		int numberOfNews = newDAO.numberOfSearchItems(searchInfo);
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
		ArrayList<News> listNews = newDAO.getItemsPagination(offset, searchInfo);
		
		session.setAttribute("searchInfo", searchInfo);
		request.setAttribute("numberOfNews", numberOfNews);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("offset", offset);
		request.setAttribute("listNews", listNews);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/news/search.jsp");
		rd.forward(request, response);
	}

}
