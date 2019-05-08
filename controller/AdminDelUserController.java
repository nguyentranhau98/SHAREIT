package controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Users;
import model.dao.UserDAO;
import util.AuthUtil;
import util.DefineUtil;

public class AdminDelUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();   
    public AdminDelUserController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
		HttpSession session = request.getSession();
		Users userInfo = (Users)session.getAttribute("userInfo");
   		if(userInfo.getRole().equals("Người dùng")) {
   			response.sendRedirect(request.getContextPath() + "/404");
			return;
   		}
   		if(userInfo.getRole().equals("Nhân viên")) {
   			response.sendRedirect(request.getContextPath() + "/404");
			return;
   		}
		int uid = Integer.parseInt(request.getParameter("id"));
		Users objUser = userDAO.getItem(uid);
		int page = 0;
		if(request.getParameter("page") == null) page = 1;
		else page = Integer.valueOf(request.getParameter("page"));
		if("Admin".equals(objUser.getRole())) {
			response.sendRedirect(request.getContextPath() + "/admin/user/index?msg=4");
			return;
		} else if ("Admin".equals(userInfo.getRole())) {
			//lấy tên file cũ
			String fileName = objUser.getAvatar();
			if (!"".equals(fileName)) {
				String filePath = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD
									+ File.separator + fileName;
				File file = new File(filePath);
				file.delete();
			}
			if(userDAO.delItem(uid) > 0) {
				response.sendRedirect(request.getContextPath() + "/admin/user/index?page="+page+"&msg=3");
			} else {
				response.sendRedirect(request.getContextPath() + "/admin/user/index?page="+page+"&msg=0");
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/user/index?page="+page+"&msg=4");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
