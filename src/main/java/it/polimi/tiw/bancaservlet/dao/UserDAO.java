package it.polimi.tiw.bancaservlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.xml.transform.Result;

import com.mysql.cj.xdevapi.Statement;

import it.polimi.tiw.bancaservlet.beans.User;

public class UserDAO {
	private Connection connection;
	
	public UserDAO(Connection connection) {
		this.connection = connection;
	}
	
	public User checkCredenziali(String username, String password) throws SQLException {
		String query = "SELECT Id, Username, Email, Password, Nome, Cognome FROM utente WHERE Username = ? AND Password = ?";
		
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setString(1, username);
			pstatement.setString(2, password);
			
			try (ResultSet result = pstatement.executeQuery();){
				if (!result.isBeforeFirst()) { // Se il result set non è nullo dà errore
					return null;
				}
				else {
					result.next();
					User user = new User();
					
					user.setId(result.getInt("id"));
					user.setUsername(result.getString("username"));
					user.setEmail(result.getString("email"));
					user.setPassword(result.getString("password"));
					user.setNome(result.getString("nome"));
					user.setCognome(result.getString("cognome"));
					
					return user;
				}
			}
		}
	}
	
	public Optional<User> getByUsername(String username) {
		String SQL_GET_BY_USERNAME ="select Id, Username, Email, Password, Nome, Cognome From utente where Username = ?";
		try (PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_USERNAME)) {
			statement.setString(1, username);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Integer id = resultSet.getInt(1);
					String email = resultSet.getString(2);
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
	
	public Optional<User> getById(Integer id) {
		String SQL_GET_BY_ID = "Select Id, Username, Email, Password, Nome, Cognome from utente Where Id = ?";
		try (PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_ID)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String email = resultSet.getString(1);
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
		
	public Optional<User> getByEmail(String email) {
		String SQL_GET_BY_EMAIL ="select Id, Username, Email, Password, Nome, Cognome From utente where Email = ?";
		try (PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_EMAIL)) {
			statement.setString(1, email);
			try (ResultSet resultSet = statement.executeQuery()) {
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
	
	public boolean insert(User user) {
		String SQL_INSERT = "Insert Into utente (Username, Email, Password, Nome, Cognome) values (?, ?, ?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getNome());
			statement.setString(5, user.getCognome());
			if (statement.executeUpdate() == 1) {
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						user.setId(resultSet.getInt(1));
						return true;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
