package it.polimi.tiw.bancaservlet.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.polimi.tiw.bancaservlet.beans.Transazione;
import it.polimi.tiw.bancaservlet.beans.User;

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
	
	public int insert(Transazione t) {
		String SQL_INSERT = "Insert Into trasferimento (Importo, Data, Id_mit, Id_dest, Causale) values (?, ?, ?, ?, ?)";
		int result = 0;
		PreparedStatement ps;
		
		try {
			ps = connection.prepareStatement(SQL_INSERT);
			ps.setFloat(1, t.getImporto());
			ps.setDate(2, t.getData());
			ps.setInt(3, t.getId_Mitt());
			ps.setInt(4, t.getId_Dest());
			ps.setString(5, t.getCausale());
			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
