package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Users;
import model.dao.UserDAO;
import util.AuthUtil;
import util.DefineUtil;

public class AdminIndexUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();   
    public AdminIndexUserController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
		int numberOfUsers = userDAO.numberOfUsers();
		int numberOfPages = (int)Math.ceil((float)numberOfUsers / DefineUtil.NUMBER_PER_PAGE);
		int currentPage = 1;
		try {
				currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}
		if(currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}
		int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE; 
		ArrayList<Users> userList = userDAO.getItemsPagination(offset);
		
		request.setAttribute("numberOfUsers", numberOfUsers);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("offset", offset);
		request.setAttribute("userList", userList);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/users/users.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
