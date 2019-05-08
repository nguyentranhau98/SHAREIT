package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Comments;
import model.dao.CommentDAO;

public class PublicCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CommentDAO comDAO = new CommentDAO(); 
    
    public PublicCommentController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String msg = "";
		
		int nid = Integer.valueOf(request.getParameter("nid"));
		if(request.getParameter("scomId") == null) {
			String user = request.getParameter("sname");
			String text = request.getParameter("scomment");
			if(comDAO.addComment(new Comments(0, nid, user, text, 1, null)) <= 0){
				msg = "Bình luận thất bại";
				request.setAttribute("msg", msg);
			}
		} else {
			String user = request.getParameter("aname");
			String text = request.getParameter("acomment");
			int comId = Integer.valueOf(request.getParameter("scomId"));
			if(comDAO.addSubComment(new Comments(0, nid, user, text, 1, null), comId) <= 0){
				msg = "Bình luận thất bại";
				request.setAttribute("msg", msg);
			}
		}
		
		ArrayList<Comments> alComments = comDAO.getComments(nid);
		int count = comDAO.numberOfComments(nid);
		
		out.println("<div class=\"comments_title\">Số lượng Comments <span>(" + count + ")</span></div> ");
		out.println("<div class=\"row\">");
			out.println("<div class=\"col-xl-8\">");
					out.println("<div class=\"comments_container\">");
						out.println("<ul class=\"comment_list\">");
							for (Comments alComment : alComments) {
							out.println("<li class=\"comment\">");
								out.println("<div class=\"comment_body\">");
									out.println("<div class=\"comment_panel d-flex flex-row align-items-center justify-content-start\">");
										String dateComment = new SimpleDateFormat("dd/MM/YYYY HH:MM").format(alComment.getDate_create().getTime());
										out.println("<small class=\"post_meta\">" + alComment.getUser() + "<span>" + dateComment + "</span></small>");
										out.println("</div>");
										out.println("<div class=\"comment_content\">");
										out.println("<p>" + alComment.getText() + "</p>");
										out.println("</div>");
										out.println("</div>");
										ArrayList<Comments> subComments = comDAO.getSubComments(nid, alComment.getId_com());
										out.println("<ul class=\"comment_list\" style=\"width:400px;float:right;list-style-type: none;\">");
											if(subComments != null && subComments.size() > 0) {
											for(Comments subComment : subComments) {
												out.println("<li class=\"comment\">");
												out.println("<div class=\"comment_body\">");
										out.println("<div class=\"comment_panel d-flex flex-row align-items-center justify-content-start\">");
											String dateSubComment = new SimpleDateFormat("dd/MM/YYYY HH:MM").format(subComment.getDate_create().getTime());
												out.println("<small class=\"post_meta\">"+subComment.getUser()+"<span>"+dateSubComment+"</span></small>");
										out.println("</div>");
										out.println("<div class=\"comment_content\">");
											out.println("<p>"+subComment.getText()+"</p>");
										out.println("</div>");
									out.println("</div>");
								out.println("</li>");
								}} 
								out.println("<div class=\"post_comment_form_container\" style=\"padding-left: 10px;padding-right: 10px;padding-top: 10px;padding-bottom: 10px;margin-bottom: 20px;margin-top:10px;width:400px;float:right;\">");
									out.println("<form id=\"myForm\" action=\"javascript:void(0)\">");
										out.println("<input type=\"text\" id=\"name_sub" + alComment.getId_com() + "\" name=\"name_sub\" class=\"comment_input comment_input_name\" placeholder=\"Tên Người Bình Luận\" required=\"required\">");
										out.println("<textarea id=\"comment_text_sub" + alComment.getId_com() + "\" name=\"comment_text_sub\" class=\"comment_text\" placeholder=\"Nội Dung Bình Luận\" style=\"width:300px;\" required=\"required\"></textarea>");
										out.println("<div style=\"clear:both\"></div>");
										out.println("<button type=\"submit\" class=\"comment_button\" onclick=\"return myFunc("+alComment.getId_com()+")\">Bình Luận</button>");
									out.println("</form>");
								out.println("</div>");
								out.println("</ul>");
							out.println("</li>");
							out.println("<div style=\"clear:both\"></div>");
							}
						out.println("</ul>");
					out.println("</div>");
				out.println("</div>");
			out.println("</div>");
	}

}
