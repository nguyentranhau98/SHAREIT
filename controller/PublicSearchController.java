package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;

import model.bean.News;
import model.dao.NewDAO;
import util.DefineUtil;

public class PublicSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private NewDAO newDAO = new NewDAO();   
    public PublicSearchController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String searchInfo = Jsoup.parse(request.getParameter("name")).text();
		int numberOfItems = newDAO.numberOfItems(searchInfo);
		int numberOfPages = (int)Math.ceil((float)numberOfItems / DefineUtil.NUMBER_PER_PAGE);
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
		}
		if(currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}
		int offset = (currentPage - 1)*DefineUtil.NUMBER_PER_PAGE;
		ArrayList<News> searchNews = newDAO.getItemsPagination(offset, searchInfo);
		request.setAttribute("searchInfo", searchInfo);
		request.setAttribute("numberOfItems", numberOfItems);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		
		request.setAttribute("searchNews", searchNews);
		RequestDispatcher rd = request.getRequestDispatcher("/public/search.jsp");
		rd.forward(request, response);
	}

}
