package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;

import model.bean.Users;
import model.dao.UserDAO;
import util.StringUtil;

public class AuthLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AuthLoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/auth/login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDAO = new UserDAO();
		String username = Jsoup.parse(request.getParameter("username")).text();
		String password = StringUtil.md5(Jsoup.parse(request.getParameter("password")).text());
		Users userInfo = userDAO.getItemByUsernamePassword(username, password);
		if( userInfo != null) {
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", userInfo);
			response.sendRedirect(request.getContextPath() + "/admin/index");
		} else {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=2");
		}
	}

}
