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

import model.bean.News;
import model.dao.NewDAO;
import util.DefineUtil;

public class PublicIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewDAO newDAO = new NewDAO();
       
    public PublicIndexController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			currentPage = 1;
		}
		int numberOfItems = 0;
		int numberOfPages = 0;
		ArrayList<News> listNews = new ArrayList<News>();
		int cid = 0;
		if(request.getParameter("cid") == null || Integer.parseInt(request.getParameter("cid")) == 0){
			numberOfItems = newDAO.numberOfItems();
			numberOfPages = (int)Math.ceil((float)numberOfItems / DefineUtil.NUMBER_PER_PAGE);
			if(currentPage > numberOfPages || currentPage < 1) {
				currentPage = 1;
			}
			int offset = (currentPage - 1)*DefineUtil.NUMBER_PER_PAGE;
			
			listNews = newDAO.getItemsPagination(offset); 
		} else {
			cid = Integer.valueOf(request.getParameter("cid"));
			numberOfItems = newDAO.numberOfItems(cid);
			numberOfPages = (int)Math.ceil((float)numberOfItems / DefineUtil.NUMBER_PER_PAGE);
			if(currentPage > numberOfPages || currentPage < 1) {
				currentPage = 1;
			}
			int offset = (currentPage - 1)*DefineUtil.NUMBER_PER_PAGE;
			
			listNews = newDAO.getItemsPagination(offset, cid); 
		}
		request.setAttribute("cid", cid);
		request.setAttribute("numberOfItems", numberOfItems);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		
		request.setAttribute("listNews", listNews);
		RequestDispatcher rd = request.getRequestDispatcher("/public/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
