package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.bancaservlet.beans.Transazione;
import it.polimi.tiw.bancaservlet.dao.TransazioneDAO;

public class doTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TransazioneDAO tDAO;
       
    public doTransaction() {
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
		
		TransazioneDAO tDAO= new TransazioneDAO(connection);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*tDAO.insert(new Transazione(
				0,
				Float.parseFloat(request.getParameter("importo")),
				Date.valueOf(request.getParameter("data")),
				,
				Integer.parseInt(request.getParameter("id_dest")),
				request.getParameter("causale")
		));*/
		
		System.out.println(request.getAttributeNames());
	}
}
