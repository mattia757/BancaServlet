package it.polimi.tiw.bancaservlet.beans;

import java.util.Objects;

public class Conto {
	int id;
	float saldo;
	int id_utente;
	
	public Conto() {}
	
	public Conto(Integer id, Float saldo, Integer id_utente){
		this.id = id;
		this.saldo = saldo;
		this.id_utente = id_utente;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public float getSaldo() {
		return saldo;
	}
	
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	
	public int getId_utente() {
		return id_utente;
	}
	
	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}

	@Override
	public String toString() {
		return "Conto [id=" + id + ", saldo=" + saldo + ", id_utente=" + id_utente + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, id_utente, saldo);
	}	
	
}
