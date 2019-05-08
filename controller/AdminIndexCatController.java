package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Categories;
import model.dao.CategoryDAO;
import util.AuthUtil;

public class AdminIndexCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryDAO catDAO = new CategoryDAO();
       
    public AdminIndexCatController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login");
			return;
		}
		ArrayList<Categories> listCats = catDAO.getItems();
		request.setAttribute("listCats", listCats);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/cats/categories.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
