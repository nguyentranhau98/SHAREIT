package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Comments;
import model.bean.Users;
import model.dao.CommentDAO;
import util.AuthUtil;
import util.DefineUtil;

public class AdminIndexCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CommentDAO commentDAO = new CommentDAO();    
    public AdminIndexCommentController() {
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
		int numberOfComments = commentDAO.numberOfComments();
		int numberOfPages = (int)Math.ceil((float)numberOfComments / DefineUtil.NUMBER_PER_PAGE);
		int currentPage = 1;
		try {
				currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}
		if(currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}
		int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE; 
		ArrayList<Comments> commentLists = commentDAO.getItemsPagination(offset);
		
		request.setAttribute("numberOfComments", numberOfComments);
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("offset", offset);
		request.setAttribute("commentLists", commentLists);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/comments/comments.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/auth/login?msg=3");
			return;
		}
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String status = request.getParameter("cstatus");
		int id_com = Integer.valueOf(request.getParameter("cid_com"));
		String msg = "";
		int stt = -1;
		if(status.equals("Hiện")) {
			stt = 0;
		} else {
			stt = 1;
		}
		if(commentDAO.updateStatus(stt, id_com) <= 0){
			msg = "Bình luận thất bại";
			request.setAttribute("msg", msg);
		}
		if(status.equals("Hiện")) {
			out.println("<div id='status" + id_com + "' class='status'>Ẩn</div>");
		} else {
			out.println("<div id='status" + id_com + "' class='status'>Hiện</div>");
		}
	}

}
