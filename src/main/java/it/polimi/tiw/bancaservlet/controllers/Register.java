package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.bancaservlet.beans.User;
import it.polimi.tiw.bancaservlet.dao.UserDAO;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private UserDAO userDAO;
       
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init(ServletConfig config) throws ServletException {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/register.html").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Richiesta parametri per Registrazione
		String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String re_password = request.getParameter("re_password");
    	String email = request.getParameter("email");
    	String nome= request.getParameter("nome");
    	String cognome = request.getParameter("cognome");
    	
    	//Controlli parametri
    	if (username == null || username.isEmpty()
    			|| password == null || password.isEmpty() 
    			|| email == null || email.isEmpty()
    			|| nome == null || nome .isEmpty()
    			|| cognome == null || cognome.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parametri incompleti");
			return;
		}
    	
    	// Controllo password e re_password
		if (!password.equals(re_password)) {
			request.getSession().setAttribute("ServletMessage", "Inserisci la stessa password");
			response.sendRedirect("register");
			return;
		}
		
		// Password > 8
		if (password.length() < 8) {
			request.getSession().setAttribute("ServletMessage", "Password must be at least 8 characters long");
			response.sendRedirect("register");
			return;
		}
		
		// Check if user already exists
		if (userDAO.getByEmail(email).isPresent()) {
			request.getSession().setAttribute("ServletMessage", "Email is already registered");
			response.sendRedirect("register");
			return;
		}
		
		// Check if user already exists
		if (userDAO.getByUsername(username).isPresent()) {
			request.getSession().setAttribute("ServletMessage", "Username is already registered");
			response.sendRedirect("register");
			return;
		}
		
		// Create the user
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setNome(nome);
		user.setCognome(cognome);
		
		// Try to insert
		if (!userDAO.insert(user)) {
			request.getSession().setAttribute("ServletMessage", "Failed to create the user. Try later");
			response.sendRedirect("register");
			return;
		}
		// Success: go to login page
		request.getSession().setAttribute("ServletMessage", "User registered");
		response.sendRedirect("login");
	}
	
	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {}
	}
}
