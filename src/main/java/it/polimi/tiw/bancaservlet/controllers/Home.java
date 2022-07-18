package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.bancaservlet.beans.User;

@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		User u = (User) request.getSession().getAttribute("Username");
		
		if (u == null) {
			request.getSession().setAttribute("ServletMessages",
					"Utente non in sessione");
			response.sendRedirect("index.html");
			return;
		}
		else {
			request.getRequestDispatcher("WEB-INF/Home.html").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
