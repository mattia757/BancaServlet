package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.bancaservlet.beans.User;
import it.polimi.tiw.bancaservlet.dao.UserDAO;

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
		request.getRequestDispatcher("index.html").forward(request, response);
	}
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	UserDAO userDAO = new UserDAO(connection);
    	
    	// Richiesta parametri per Login
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	
    	// Controlli parametri
    	if (username == null || username.isEmpty()) {
    		response.sendError(400, "Inserisci lo username");
			return;
		}
		if (password == null || password.isEmpty()) {
			response.sendError(400, "Inserisci la password");
			return;
		}
		
		// Check if the user exists
		Optional<User> maybeUser = userDAO.getByUsername(username);
		if (maybeUser.isEmpty()) {
			response.sendError(400, "L'utente inserito non esiste");
			return;
		}
		
		// Check if the password matches
		User user = maybeUser.get();
		if (!user.getPassword().equals(password)) {
			response.sendError(400, "Inserisci la stessa password");
			return;
		}
		
		// Set session User attribute
		request.getSession().setAttribute("user", user);
		response.sendRedirect("home");
	}
        
	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {}
	}
}
