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

import model.bean.Contacts;
import model.bean.Users;
import model.dao.ContactDAO;
import util.AuthUtil;
import util.DefineUtil;

public class AdminIndexContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ContactDAO contactDAO = new ContactDAO();   
    public AdminIndexContactController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
		HttpSession session = request.getSession();
   		Users objUser = (Users)session.getAttribute("userInfo");
   		if(objUser.getRole().equals("Người dùng")) {
   			response.sendRedirect(request.getContextPath() + "/404");
			return;
   		}
		int numberOfContacts = contactDAO.numberOfItems();
		int numberOfPages = (int)Math.ceil((float)numberOfContacts / DefineUtil.NUMBER_PER_PAGE);
		int currentPage = 1;
		try {
				currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}
		if(currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}
		int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE; 
		ArrayList<Contacts> contactLists = contactDAO.getItemsPagination(offset);
		
		request.setAttribute("numberOfContacts", numberOfContacts);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("offset", offset);
		request.setAttribute("contactLists", contactLists);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/contacts/contacts.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
