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

public class AdminEditCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminEditCatController() {
        super();
        // TODO Auto-generated constructor stub
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
		Categories objCat = catDAO.getItem(cid);
		ArrayList<Categories> listCats = catDAO.getItems();
		request.setAttribute("listCats", listCats);
		request.setAttribute("objCat", objCat);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/cats/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		CategoryDAO catDAO = new CategoryDAO();
		String cname = Jsoup.parse(request.getParameter("text-input")).text();
		int id_parent = Integer.valueOf(request.getParameter("select"));
		int cid = Integer.parseInt(request.getParameter("id"));
		Categories objCat = new Categories(cid, cname, id_parent);
		if(catDAO.editItem(objCat) > 0) {
			response.sendRedirect(request.getContextPath()+"/admin/cat/index?msg=2");
		} else {
			response.sendRedirect(request.getContextPath()+"/admin/cat/edit?msg=0");
		}
	}

}
