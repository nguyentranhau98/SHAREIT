package controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.News;
import model.bean.Users;
import model.dao.NewDAO;
import util.AuthUtil;
import util.DefineUtil;

public class AdminDelNewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminDelNewController() {
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
		NewDAO NewDAO = new NewDAO();
		int nid = Integer.parseInt(request.getParameter("id"));
		News objNew = NewDAO.getItem(nid);
		//lấy tên file cũ
		String fileName = objNew.getPicture();
		if (!"".equals(fileName)) {
			String filePath = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD
								+ File.separator + fileName;
			File file = new File(filePath);
			file.delete();
		}
		int page = 0;
		if(request.getParameter("page") == null) page = 1;
		else page = Integer.valueOf(request.getParameter("page"));
		if(NewDAO.delItem(nid) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/new/index?page="+page+"&msg=3");
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/new/index?page="+page+"&msg=0");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
