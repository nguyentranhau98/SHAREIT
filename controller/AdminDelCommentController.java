package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Users;
import model.dao.CommentDAO;
import util.AuthUtil;

public class AdminDelCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CommentDAO comDAO = new CommentDAO();   
    public AdminDelCommentController() {
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
		int page = 0;
		if(request.getParameter("page") == null) page = 1;
		else page = Integer.valueOf(request.getParameter("page"));
		int cid = Integer.parseInt(request.getParameter("id"));
		if(comDAO.delItem(cid) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/comment/index?page="+page+"&msg=3");
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/comment/index?page="+page+"&msg=0");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
