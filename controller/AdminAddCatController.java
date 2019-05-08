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

import model.bean.Categories;
import model.bean.Users;
import model.dao.CategoryDAO;
import util.AuthUtil;

public class AdminAddCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CategoryDAO catDAO = new CategoryDAO();   
    public AdminAddCatController() {
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
		ArrayList<Categories> listCats = catDAO.getItems();
		request.setAttribute("listCats", listCats);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/cats/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		String cname = Jsoup.parse(request.getParameter("text-input")).text();
		int id_parent = Integer.valueOf(request.getParameter("select"));
		Categories objCat = new Categories(0, cname, id_parent);
		if(catDAO.addItem(objCat) > 0) {
			response.sendRedirect(request.getContextPath()+"/admin/cat/index?msg=1");
		} else {
			response.sendRedirect(request.getContextPath()+"/admin/cat/add?msg=0");
		}
	}

}
