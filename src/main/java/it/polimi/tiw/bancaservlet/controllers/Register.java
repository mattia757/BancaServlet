package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.io.PrintWriter;
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

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private UserDAO userDAO;
       
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
    public void init(ServletConfig config) throws ServletException {		
		final String DB_URL = "jdbc:mysql://localhost:3306/banca?serverTimezone=UTC";
		final String USER = "root";
		final String PASS = "root";
		Connection connection = null;
		
		System.out.println("Connectiong database...");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(DB_URL , USER , PASS);
			System.out.println("Database connected");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		userDAO = new UserDAO(connection);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/register.html").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
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
			//TODO Controllo messaggi errore password
			//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Inserisci la stessa password");
			//request.getSession().setAttribute("ServletMessage", "Inserisci la stessa password");
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
		if (userDAO.insert(user) == 0) {
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
