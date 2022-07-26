package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.bancaservlet.beans.Conto;
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
		
		tDAO = new TransazioneDAO(connection);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		Integer id_conto = Integer.parseInt(request.getParameter("idconto")); // Mittente
		
		Integer id_conto_dest = Integer.parseInt(request.getParameter("id_dest")); //Detinatario
		
		Conto c = tDAO.FillContoById(id_conto);
	    
		if (Float.parseFloat(request.getParameter("importo")) <= c.getSaldo()) {
			try {
				tDAO.insert(new Transazione(
					0,
					Float.parseFloat(request.getParameter("importo")),
					Date.valueOf(LocalDate.now()),
					id_conto,
					id_conto_dest,
					request.getParameter("causale")
				), c, id_conto_dest);
				
				response.sendRedirect("home");
			} catch (SQLException e) {
				response.sendError(406, "Errore nell inserimento della transazione al DB, operazioni nulle!");
			}
		}
		else {
			response.sendError(406, "Il saldo non è sufficiente!");
		}
	}
}
