package it.polimi.tiw.bancaservlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.bancaservlet.beans.Conto;

public class ContoDAO {
private Connection connection;
	
	public ContoDAO(Connection connection) {
		this.connection = connection;
	}
	
	public List<Conto> checkConti(Integer id) throws SQLException {
		List<Conto> conti = new ArrayList<Conto>();
		String query = "SELECT conto.Id FROM conto, utente WHERE utente.Id = ? && utente.Id = conto.Id_Utente";
		
		ResultSet result = null;
		PreparedStatement pstatement = null;		
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, id);
			result = pstatement.executeQuery();
			while (result.next()) {
				Conto c = new Conto();
				c.setId(result.getInt("Id"));
				c.setId_utente(result.getInt("Id_Utente"));
				c.setSaldo(result.getFloat("Saldo"));
				
				conti.add(c);
			}
		} catch (SQLException e1) {
			throw new SQLException(e1);

		} finally {
			try {
				if (result != null) {
					result.close();
				}
			} catch (Exception e2) {
				throw new SQLException("Impossibile chudere il resultset");
			}
			
			try {
				if (pstatement != null) {
					pstatement.close();
				}
			} catch (Exception e2) {
				throw new SQLException("Impossibile chudere lo statement");
			}
		}
		
		return conti;	
	}
}
