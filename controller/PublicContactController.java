package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;

import model.bean.Contacts;
import model.dao.ContactDAO;

public class PublicContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PublicContactController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/public/contact.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		ContactDAO contactDAO = new ContactDAO(); 
		String name = Jsoup.parse(request.getParameter("name")).text();
		String email = Jsoup.parse(request.getParameter("email")).text();
		String website = Jsoup.parse(request.getParameter("website")).text();
		String content = request.getParameter("editor");
		Contacts objCon = new Contacts(0, name, email, website, content);
		if(contactDAO.addItem(objCon) > 0) {
			response.sendRedirect(request.getContextPath()+"/contact?msg=1");
		} else {
			response.sendRedirect(request.getContextPath()+"/contact?msg=0");
		}
	}

}
