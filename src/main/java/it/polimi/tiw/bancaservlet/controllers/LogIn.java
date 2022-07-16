package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.polimi.tiw.bancaservlet.beans.User;
import it.polimi.tiw.bancaservlet.dao.UserDAO;

@WebServlet("/LogIn")
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
       
	public LogIn() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void init() throws ServletException{
    	try{
    		ServletContext context = getServletContext();
    		String driver = context.getInitParameter("dbDriver");
    		String url = context.getInitParameter("dbUrl");
    		String user = context.getInitParameter("dbUser");
    		String password = context.getInitParameter("dbPassword");
    		Class.forName(driver);
    		connection = DriverManager.getConnection(url, user, password);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UnavailableException("Non è possibile caricare il driver del  DB");
		}
    	catch (SQLException e) {
    		e.printStackTrace();
    		throw new UnavailableException("Impossibile stabilire una connessione col DB");
    	}
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	
    	if (username == null || username.isEmpty()|| password == null || password.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri incompleti");
			return;
		}
    	
    	UserDAO userDAO = new UserDAO(connection);
    	User user = null;
    	
    	try {
			user = userDAO.checkCredenziali(username, password);
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "Accesso al DB fallito");
		}
    	
    	String path = getServletContext().getContextPath();
		
		if (user != null){
			request.getSession().setAttribute("user", user);
			request.getSession().setMaxInactiveInterval(30*60);
			path += "/Home";
			response.sendRedirect(path);
		}
		else {
			path = getServletContext().getContextPath() + "/index.html";
			response.sendError(505, "Utente invalido");
		}		
	}
        
	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {}
	}
}
