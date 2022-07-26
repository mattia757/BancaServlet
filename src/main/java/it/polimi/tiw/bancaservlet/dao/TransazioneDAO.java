package it.polimi.tiw.bancaservlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.bancaservlet.beans.Conto;
import it.polimi.tiw.bancaservlet.beans.Transazione;

public class TransazioneDAO {
	private Connection connection;
	
	public TransazioneDAO(Connection connection) {
		this.connection = connection;
	}
	
	public List<Transazione> getTransazioniById(Integer id_conto) {
		String query = "SELECT * FROM trasferimento t WHERE t.Id_mit = ? OR t.Id_dest = ? Order By t.Data DESC";
		PreparedStatement ps;
		List<Transazione> transazioni = new ArrayList<>();
			
		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, id_conto);
			ps.setInt(2, id_conto);
			
			try (ResultSet resultSet = ps.executeQuery()) {
				while (resultSet.next()) {
					Transazione t = new Transazione();
					
					t.setId(resultSet.getInt(1));
					t.setImporto(resultSet.getFloat(2));
					t.setData(resultSet.getDate(3));
					t.setId_Mitt(resultSet.getInt(4));
					t.setId_Dest(resultSet.getInt(5));
					t.setCausale(resultSet.getString(6));
					
					transazioni.add(t);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transazioni;
	}
	
	public Conto FillContoById(Integer id_conto) {
		String query = "SELECT * FROM conto c WHERE c.Id = ?";
		PreparedStatement ps;
		Conto c = new Conto();
			
		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, id_conto);
			
			try (ResultSet resultSet = ps.executeQuery()) {
				while (resultSet.next()) {
					
					c.setId(resultSet.getInt(1));
					c.setSaldo(resultSet.getFloat(2));
					c.setId_utente(resultSet.getInt(3));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;

	}
	
	public int insert(Transazione t, Conto c) {
		String SQL_INSERT = "START TRANSACTION;"
				+ "UPDATE conto c SET Saldo = Saldo - ? WHERE c.Id = ?;"
				+ "UPDATE conto c SET Saldo = Saldo + ? WHERE c.Id = ?;"
				+ "Insert Into trasferimento (Importo, Data, Id_mit, Id_dest, Causale) values (?, ?, ?, ?, ?);"
				+ "COMMIT;";

		int result = 0;
		PreparedStatement ps;
		
		try {
			ps = connection.prepareStatement(SQL_INSERT);
			ps.setFloat(1, t.getImporto());
			ps.setInt(2, c.getId());
			ps.setFloat(3, t.getImporto());
			ps.setInt(4, c.getId());			
			ps.setFloat(5, t.getImporto());
			ps.setDate(6, t.getData());
			ps.setInt(7, t.getId_Mitt());
			ps.setInt(8, t.getId_Dest());
			ps.setString(9, t.getCausale());
			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
