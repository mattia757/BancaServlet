package it.polimi.tiw.bancaservlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.polimi.tiw.bancaservlet.beans.Conto;
import it.polimi.tiw.bancaservlet.beans.User;

public class ContoDAO {
private Connection connection;
	
	public ContoDAO(Connection connection) {
		this.connection = connection;
	}
	
	public List<Conto> getByID(Integer id) throws SQLException { // Dato l'id dell'utente estrae tutti i conti
		List<Conto> conti = new ArrayList<Conto>();
		String query = "SELECT c.Id, c.Id_Utente, c.Saldo FROM conto c, utente u WHERE u.Id = ? && u.Id = c.Id_Utente";
		
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
		} catch (SQLException e) {
			throw new SQLException(e);

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
	
	public Conto getById(Integer id) throws SQLException {
		String query = "SELECT * FROM conto c WHERE c.Id = ?";
		Conto c = new Conto();
		
		ResultSet result = null;
		PreparedStatement pstatement = null;		
		try {
			pstatement = connection.prepareStatement(query);
			pstatement.setInt(1, id);
			result = pstatement.executeQuery();
			while (result.next()) {
				
				c.setId(result.getInt("Id"));
				c.setId_utente(result.getInt("Id_Utente"));
				c.setSaldo(result.getFloat("Saldo"));
			}
		} catch (SQLException e) {
			throw new SQLException(e);

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
		
		return c;	
	}
	
		
	public Optional<User> getByEmail(String email) {

		String SQL_GET_BY_EMAIL ="select Id, Username, Email, Password, Nome, Cognome From utente where Email = ?";
		try (PreparedStatement ps = connection.prepareStatement(SQL_GET_BY_EMAIL)) {
			ps.setString(1, email);
			try (ResultSet resultSet = ps.executeQuery()) {
				if (resultSet.next()) {
					Integer id = resultSet.getInt(1);
					String username = resultSet.getString(2);
					String password = resultSet.getString(3);
					String nome = resultSet.getString(4);
					String cognome = resultSet.getString(5);
					return Optional.of(new User(id, username, email, password, nome, cognome));
				} else {
					return Optional.empty();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public Boolean isVerified(int id_utente, int id_conto) { // Datu un utente e un conto verifica se l'utente ha il permesso
		String query = "select null From conto c Where c.Id = ? && c.Id_Utente = ?";
		boolean isVerified = false;
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, id_conto);
			ps.setInt(2, id_utente);
			try (ResultSet resultSet = ps.executeQuery()) {
				if (resultSet.next()) {
					isVerified = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isVerified = false;
		}
		return isVerified;
		
	}

	public int insert(Conto conto) throws SQLException {
		String SQL_INSERT = "Insert Into conto (Saldo, Id_Utente) values (?, ?)";
		int result = 0;
		PreparedStatement ps;
		
		try {
			ps = connection.prepareStatement(SQL_INSERT);
			ps.setFloat(1, conto.getSaldo());
			ps.setInt(2, conto.getId_utente());
			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
