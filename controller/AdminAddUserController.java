package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.jsoup.Jsoup;

import model.bean.Users;
import model.dao.UserDAO;
import util.AuthUtil;
import util.DefineUtil;
import util.FileUtil;
import util.StringUtil;

@MultipartConfig
public class AdminAddUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminAddUserController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
		HttpSession session = request.getSession();
		Users userInfo = (Users)session.getAttribute("userInfo");
		//chi admin moi duoc them nguoi dung
		if(!"Admin".equals(userInfo.getRole())) {
			//khong duoc phep
			response.sendRedirect(request.getContextPath()+"/admin/users/index?msg=4");
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/admin/users/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
		HttpSession session = request.getSession();
		Users userInfo = (Users)session.getAttribute("userInfo");
		//chi admin moi duoc them nguoi dung
		if(!"Admin".equals(userInfo.getRole())) {
			//khong duoc phep
			response.sendRedirect(request.getContextPath()+"/admin/users/index?msg=4");
			return;
		}

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		UserDAO userDAO = new UserDAO();
		String username = Jsoup.parse(request.getParameter("text-input")).text();
		String password = StringUtil.md5(Jsoup.parse(request.getParameter("password-input")).text());
		String fullname = Jsoup.parse(request.getParameter("fullname-input")).text();
		String role = Jsoup.parse(request.getParameter("select")).text();
		
		Part filePart = request.getPart("file-input");
		String fileName = filePart.getSubmittedFileName();
		if("".equals(fileName)) fileName = "noAvatar";
		if(!"".equals(fileName)) {
			//Đổi tên file
			fileName = FileUtil.rename(fileName);
			String dirPath = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD;
			File createDir = new File(dirPath);
			if(!createDir.exists()) {
				createDir.mkdir();
			}
			String filePath = dirPath + File.separator + fileName;
			filePart.write(filePath);
			System.out.println(filePath);
			Users objUser = new Users(0, username, password, fullname, role, fileName);
			if(userDAO.addItem(objUser) > 0) {
				response.sendRedirect(request.getContextPath()+"/admin/user/index?msg=1");
			} else {
				response.sendRedirect(request.getContextPath()+"/admin/user/add?msg=0");
			}
		}
	}

}
