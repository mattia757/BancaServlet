package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.bancaservlet.dao.UserDAO;
import it.polimi.tiw.bancaservlet.dao.ContoDAO;
import it.polimi.tiw.bancaservlet.beans.Conto;
import it.polimi.tiw.bancaservlet.beans.User;


public class AggiungiConto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
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
		
		System.out.println("Connecting database...");
		
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
		request.getRequestDispatcher("WEB-INF/AggiungiConto.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User) request.getSession().getAttribute("user");
		
		Float saldo = Float.parseFloat(request.getParameter("saldo"));
		
		contoDAO.insert(new Conto(
			0,
			saldo,
			user.getId()
		));
	}

}