package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

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

public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	UserDAO userDAO = new UserDAO(connection);
       
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
		request.getRequestDispatcher("index.html").forward(request, response);
	}
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Richiesta parametri per Login
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	
    	// Controlli parametri
    	if (username == null || username.isEmpty()) {
    		request.getSession().setAttribute("ServletMessage", "Inserisci lo username");
			response.sendRedirect("login");
			return;
		}
		if (password == null || password.isEmpty()) {
			request.getSession().setAttribute("ServletMessage", "Insert la password");
			response.sendRedirect("login");
			return;
		}
		
		// Check if the user exists
		Optional<User> maybeUser = userDAO.getByUsername(username);
		if (maybeUser.isEmpty()) {
			request.getSession().setAttribute("ServletMessage", "Utente non esiste");
			response.sendRedirect("login");
			return;
		}
		
		// Check if the password matches
		User user = maybeUser.get();
		if (!user.getPassword().equals(password)) {
			request.getSession().setAttribute("ServletMessage", "Password errata");
			response.sendRedirect("login");
			return;
		}
		
		// Set session User attribute
		request.getSession().setAttribute("User", user);
		response.sendRedirect("Home");
	}
        
	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {}
	}
}
