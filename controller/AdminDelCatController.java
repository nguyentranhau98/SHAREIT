package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Categories;
import model.bean.Users;
import model.dao.CategoryDAO;
import util.AuthUtil;

public class AdminDelCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminDelCatController() {
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
   		if(objUser.getRole().equals("Nhân viên")) {
   			response.sendRedirect(request.getContextPath() + "/404");
			return;
   		}
		CategoryDAO catDAO = new CategoryDAO();
		int cid = Integer.parseInt(request.getParameter("id"));
		ArrayList<Categories> subCats = catDAO.getSubItems(cid);
		for (Categories categories : subCats) {
			if(catDAO.delItem(categories.getId()) > 0) {
				subCats.remove(categories);
			}
		}
		if(catDAO.delItem(cid) > 0 && subCats.size() == 0) {
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=3");
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/cat/index?msg=0");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
