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
public class AdminEditUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminEditUserController() {
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
		
		UserDAO userDAO = new UserDAO();
		int uid = Integer.parseInt(request.getParameter("id"));
		if("Admin".equals(userInfo.getRole()) || (uid == userInfo.getId())) {
		Users objUser = userDAO.getItem(uid);
		if(objUser != null) {
			request.setAttribute("objUser", objUser);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/users/edit.jsp");
			rd.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath()+"/admin/user/index?msg=0");
			return;
			}
		} else {
			response.sendRedirect(request.getContextPath()+"/admin/user/index?msg=4");
			return;
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login");
			return;
		}
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		UserDAO userDAO = new UserDAO();
		
		//lấy dữ liệu mới
		String username = Jsoup.parse(request.getParameter("text-input")).text();
		String password = StringUtil.md5(Jsoup.parse(request.getParameter("password-input")).text());
		String fullname = Jsoup.parse(request.getParameter("fullname-input")).text();
		String role = Jsoup.parse(request.getParameter("select")).text();
		//lấy dữ liệu cũ
		int uid = Integer.parseInt(Jsoup.parse(request.getParameter("id")).text());
		Users OldUser = userDAO.getItem(uid);
		if(request.getParameter("password-input").equals("")) {
			password = OldUser.getPassword();
		}
		//lấy thông tin file
		Part filePart = request.getPart("file-input");
		String fileName = filePart.getSubmittedFileName();
		//Kiểm tra xem đã có dữ liệu trong Database với id lấy từ trang edit
		if (OldUser == null) {
			response.sendRedirect(request.getContextPath()+"/admin/user/edit?msg=0");
			return;
		}
		if("".equals(fileName)) {
			fileName = OldUser.getAvatar();
		} else {
			//Đổi tên file nếu không phải file cũ
			fileName = FileUtil.rename(fileName);
		}
		//Tạo thư mục lưu ảnh
		String dirPath = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD;
		File createDir = new File(dirPath);
		if(!createDir.exists()) {
			createDir.mkdir();
		}
		//Đường dẫn file
		String filePath = dirPath + File.separator + fileName;
		if(!fileName.equals(OldUser.getAvatar())) {
			//Xóa file cũ
			String oldFilePathName = dirPath + File.separator + OldUser.getAvatar();
			File oldFile = new File(oldFilePathName);
			if(oldFile.exists()) oldFile.delete();
			//Ghi file
			filePart.write(filePath);
		}
		int page = 0;
		if(request.getParameter("page") == null) page = 1;
		else page = Integer.valueOf(request.getParameter("page"));
		HttpSession session = request.getSession();
		Users userInfo = (Users)session.getAttribute("userInfo");
		if("Admin".equals(userInfo.getRole()) || (uid == userInfo.getId())) {
		Users objUser = new Users(uid, username, password, fullname, role, fileName);
			if(userDAO.editItem(objUser) > 0) {
				response.sendRedirect(request.getContextPath()+"/admin/user/index?page=" +page+ "&msg=2");
				return;
			} else {
				response.sendRedirect(request.getContextPath()+"/admin/user/edit?msg=0&id=" + uid);
				return;
			}
		} else {
			response.sendRedirect(request.getContextPath()+"/admin/user/index?page=" +page+ "&msg=4");
			return;
		}
	}

}
