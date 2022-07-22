package it.polimi.tiw.bancaservlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import it.polimi.tiw.bancaservlet.beans.User;

public class UserDAO {
	private Connection connection;
	
	public UserDAO(Connection connection) {
		this.connection = connection;
	}
	
	public Optional<User> getByUsername(String username) {
		String SQL_GET_BY_USERNAME = "select Id, Email, Password, Nome, Cognome From utente where Username = ?";
		try (PreparedStatement ps = connection.prepareStatement(SQL_GET_BY_USERNAME)) {
			ps.setString(1, username);
			try (ResultSet resultSet = ps.executeQuery()) {
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
		try (PreparedStatement ps = connection.prepareStatement(SQL_GET_BY_ID)) {
			ps.setInt(1, id);
			try (ResultSet resultSet = ps.executeQuery()) {
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
	
	public int insert(User user) {
		String SQL_INSERT = "Insert Into utente (Username, Email, Password, Nome, Cognome) values (?, ?, ?, ?, ?)";
		int result = 0;
		PreparedStatement ps;
		
		try {
			ps = connection.prepareStatement(SQL_INSERT);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getNome());
			ps.setString(5, user.getCognome());
			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
