package it.polimi.tiw.bancaservlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.transform.Result;

import it.polimi.tiw.bancaservlet.beans.User;

public class UserDAO {
	private Connection connection;
	
	public UserDAO(Connection connection) {
		this.connection = connection;
	}
	
	public User checkCredenziali(String username, String password) throws SQLException {
		String query = "SELECT id, username, email, password, nome, cognome FROM utente WHERE utente = ? AND password = ?";
		
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
}
