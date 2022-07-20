package it.polimi.tiw.bancaservlet.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.polimi.tiw.bancaservlet.beans.User;
import it.polimi.tiw.bancaservlet.dao.ContoDAO;
import it.polimi.tiw.bancaservlet.beans.Conto;

@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

    public Home() {
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

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ContoDAO cDAO = new ContoDAO(connection);
		List<Conto> conti;
		
		User u = (User) request.getSession().getAttribute("user");
		
		if (u == null) {
			request.getSession().setAttribute("ServletMessages",
					"Utente non in sessione");
			response.sendRedirect("index.html");
			return;
		}
		else {
			
			String query = "SELECT * FROM utente WHERE Username = ?";
			
			ResultSet result = null;
			PreparedStatement pstatement = null;
			
			try {
				pstatement = connection.prepareStatement(query);
				pstatement.setInt(1, u.getId());
				result = pstatement.executeQuery();
				while (result.next()) {
					u.setId(result.getInt("id"));
				}
			} catch (SQLException e) {}
			
			try {
				conti = cDAO.checkConti(u.getId());
				String path = "/WEB-INF/Home.jsp";
				request.setAttribute("conti", conti);
				RequestDispatcher dispatcher = request.getRequestDispatcher(path);
				dispatcher.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Accesso al DB fallito");
			}
				
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqle) {
		}
	}

}
