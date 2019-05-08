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

public class PublicDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    NewDAO newDAO = new NewDAO();   
    public PublicDetailController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nid = 0;
		try {
			nid = Integer.valueOf(request.getParameter("nid"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		News objNew = newDAO.getItem(nid);
		if(objNew == null) {
			response.sendRedirect(request.getContextPath() + "/404");
			return;
		}
		
		// tăng view
		HttpSession session = request.getSession();
		String hasVisited = (String) session.getAttribute("hasVisited: " + nid);
		if (hasVisited == null) {
			session.setAttribute("hasVisited: " + nid, "yes");
			session.setMaxInactiveInterval(3600);
			newDAO.increaseView(nid);
		}
		
		ArrayList<News> relatedNews = newDAO.getRelatedItems(objNew.getId(), objNew.getCat().getId());
		request.setAttribute("objNew", objNew);
		request.setAttribute("relatedNews", relatedNews);
		RequestDispatcher rd = request.getRequestDispatcher("/public/detail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
