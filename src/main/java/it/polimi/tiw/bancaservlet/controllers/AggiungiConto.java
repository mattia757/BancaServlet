package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.bancaservlet.dao.UserDAO;
import it.polimi.tiw.bancaservlet.dao.ContoDAO;
import it.polimi.tiw.bancaservlet.beans.Conto;


public class AggiungiConto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContoDAO contoDAO;
	private UserDAO userDAO;
	
    public AggiungiConto() {
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
		contoDAO = new ContoDAO(connection);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String currentUser = (String) request.getSession().getAttribute("user");
		
		userDAO.getByUsername(currentUser);	
		
		Integer id_current_userInteger = userDAO
		
		Float saldo = (Float) request.getParameter(saldo);
				
		
		
		System.out.println(saldo);
		
		Conto conto = new Conto();
		conto.setSaldo(saldo);
		conto.setId_utente(id_utente);

	}

}
