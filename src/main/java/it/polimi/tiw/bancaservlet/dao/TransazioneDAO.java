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
	
	public int insert(Transazione t, Conto c, Integer id_conto_dest) throws SQLException {	
		String prelievo = "UPDATE conto c SET Saldo = Saldo - ? WHERE c.Id = ?";
		String versamento = "UPDATE conto c SET Saldo = Saldo + ? WHERE c.Id = ?";
		String registra = "Insert Into trasferimento (Importo, Data, Id_mit, Id_dest, Causale) values (?, ?, ?, ?, ?)";

		int result = 0;
		//PreparedStatement ps;
		
		try{
			connection.setAutoCommit(false); // No commit per statement
			//ps = connection.prepareStatement(prelievo);
	
			try (PreparedStatement stmt1 = connection.prepareStatement(prelievo)) { // Automatic close.
				stmt1.setFloat(1, t.getImporto());
				stmt1.setInt(2, c.getId());
				
				if (stmt1.executeUpdate() == 1) {
	                try (ResultSet resultSet = stmt1.getGeneratedKeys()) {
	                    if (resultSet.next()) {
	                        c.setSaldo(resultSet.getFloat(1));  
	                    } 	                
	                }
	            } 
			}
			    try (PreparedStatement stmt2 = connection.prepareStatement(versamento)){
			    	stmt2.setFloat(3, t.getImporto());
			    	stmt2.setInt(4, id_conto_dest);
			    	
			    	if (stmt2.executeUpdate() == 1) {
		                try (ResultSet resultSet = stmt2.getGeneratedKeys()) {
		                    if (resultSet.next()) {
		                        c.setSaldo(resultSet.getFloat(1));  
		                    } 	                
		                }
		            }
			    	
			        try (PreparedStatement stmt3 = connection.prepareStatement(registra)){
			        	stmt3.setFloat(5, t.getImporto());
						stmt3.setDate(6, t.getData());
						stmt3.setInt(7, t.getId_Mitt());
						stmt3.setInt(8, t.getId_Dest());
						stmt3.setString(9, t.getCausale());
						
			        	connection.commit();
					}
			    }
			} catch (SQLException ex) {
			    connection.rollback();
			}
			
			//result = ps.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
		}
		
		return result;
	}
}
