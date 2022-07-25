package it.polimi.tiw.bancaservlet.beans;

import java.lang.annotation.AnnotationTypeMismatchException;
import java.util.Objects;

import javax.swing.table.TableStringConverter;

public class User {
	private int id;
	private String Username;
	private String email;
	private String password;
	private String nome;
	private String cognome;
	
	public User() {}
	
	public User(Integer id, String username, String email, String password, String nome, String cognome){
		this.id = id;
		this.email = email;
		this.Username = username;
		this.password = password;
		this.nome = nome;
		this.cognome = cognome;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", Username=" + Username + ", email=" + email + ", password=" + password + ", nome="
				+ nome + ", cognome=" + cognome + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(Username, cognome, email, id, nome, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(Username, other.Username) && Objects.equals(cognome, other.cognome)
				&& Objects.equals(email, other.email) && id == other.id && Objects.equals(nome, other.nome)
				&& Objects.equals(password, other.password);
	}
	
	
}
