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
public class AdminEditNewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminEditNewController() {
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
		int nid = Integer.valueOf(request.getParameter("id"));
		News objNew = NewDAO.getItem(nid);
		if (objNew == null) {
			response.sendRedirect(request.getContextPath()+"/admin/New/edit?msg=0");
			return;
		}
		CategoryDAO catDAO = new CategoryDAO();
		ArrayList<Categories> listCats = catDAO.getItems();
		request.setAttribute("objNew", objNew);
		request.setAttribute("listCats", listCats);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/news/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		NewDAO NewDAO = new NewDAO(); 
		//lấy dữ liệu cũ
		int id = Integer.valueOf(request.getParameter("id"));
		News OldNew = NewDAO.getItem(id);
		//lấy dữ liệu mới
		int page = 0;
		if(request.getParameter("page") == null) page = 1;
		else page = Integer.valueOf(request.getParameter("page"));
		String name = Jsoup.parse(request.getParameter("text-input")).text();
		int catId = Integer.valueOf(request.getParameter("select"));
		String preview = Jsoup.parse(request.getParameter("desc-input")).text();
		String detail = request.getParameter("content-input");
		//lấy thông tin file
		Part filePart = request.getPart("picture");
		String fileName = filePart.getSubmittedFileName();
		//Kiểm tra xem đã có dữ liệu trong Database với id lấy từ trang edit
		int uid = 1;
		if (OldNew == null) {
			response.sendRedirect(request.getContextPath()+"/admin/new/edit?msg=0");
			return;
		}
		if("".equals(fileName)) {
			fileName = OldNew.getPicture();
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
		if(!fileName.equals(OldNew.getPicture())) {
			//Xóa file cũ
			String oldFilePathName = dirPath + File.separator + OldNew.getPicture();
			File oldFile = new File(oldFilePathName);
			if(oldFile.exists()) oldFile.delete();
			//Ghi file
			filePart.write(filePath);
		}
		News objNew = new News(id, new Categories(catId, null, 0), "Admin", "", name, preview, detail, null, fileName, 0);
		if(NewDAO.editItem(objNew, uid) > 0) {
			response.sendRedirect(request.getContextPath()+"/admin/new/index?page="+page+"&msg=2");
		} else {
			response.sendRedirect(request.getContextPath()+"/admin/new/edit?msg=0");
		}
	}

}
