package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SysexMessage;

import it.polimi.tiw.bancaservlet.beans.Conto;
import it.polimi.tiw.bancaservlet.beans.Transazione;
import it.polimi.tiw.bancaservlet.beans.User;
import it.polimi.tiw.bancaservlet.dao.ContoDAO;
import it.polimi.tiw.bancaservlet.dao.TransazioneDAO;
import it.polimi.tiw.bancaservlet.dao.UserDAO;


public class DettagliConto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	TransazioneDAO transazioneDAO;
	ContoDAO contoDAO;
       
    public DettagliConto() {
        super();
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
		
		transazioneDAO = new TransazioneDAO(connection);
		contoDAO = new ContoDAO(connection);
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	Integer conto_id = Integer.parseInt(request.getParameter("idConto"));
    	User user = (User) request.getSession().getAttribute("user");
    	    	    	
    	if (contoDAO.isVerified(user.getId(), conto_id)) {
    		List<Transazione> transazioni = transazioneDAO.getTransazioniById(conto_id);
        	request.setAttribute("transazioni", transazioni);
        	request.getRequestDispatcher("/WEB-INF/StatoConto.jsp").forward(request, response);
		}
    	else {
    		response.sendError(406, "Non hai il permesso!");
    	}
    	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}