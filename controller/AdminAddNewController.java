package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

import model.bean.Categories;
import model.bean.News;
import model.bean.Users;
import model.dao.CategoryDAO;
import model.dao.NewDAO;
import util.AuthUtil;
import util.DefineUtil;
import util.FileUtil;

@MultipartConfig
public class AdminAddNewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminAddNewController() {
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
		ArrayList<Categories> listCats = catDAO.getItems();
		request.setAttribute("listCats", listCats);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/news/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		NewDAO newDAO = new NewDAO(); 
		//
		String name = Jsoup.parse(request.getParameter("text-input")).text();
		int catId = Integer.valueOf(request.getParameter("select"));
		String preview = Jsoup.parse(request.getParameter("desc-input")).text();
		String detail = request.getParameter("content-input");
		HttpSession session = request.getSession();
		Users userInfo = (Users)session.getAttribute("userInfo");
		//lấy thông tin file
		Part filePart = request.getPart("picture");
		String fileName = filePart.getSubmittedFileName();
		if("".equals(fileName)) fileName = "noPicture";
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
			News objNew = new News(0, new Categories(catId, null, 0), userInfo.getFullname(), "", name, preview, detail, null, fileName, 0);
			if(newDAO.addItem(objNew, userInfo.getId()) > 0) {
				response.sendRedirect(request.getContextPath()+"/admin/new/index?msg=1");
			} else {
				response.sendRedirect(request.getContextPath()+"/admin/new/add?msg=0");
			}
		}
	}
}
