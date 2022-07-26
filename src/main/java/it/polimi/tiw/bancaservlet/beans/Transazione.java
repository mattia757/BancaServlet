package it.polimi.tiw.bancaservlet.beans;

import java.sql.Date;

public class Transazione {
	int id;
	float importo;
	Date data;
	int id_Mitt;
	int id_Dest;
	String causale;
	
	public Transazione() {}
	
	public Transazione(int id, float importo, Date data, int id_Mitt, int id_Dest, String causale) {
		super();
		this.id = id;
		this.importo = importo;
		this.data = data;
		this.id_Mitt = id_Mitt;
		this.id_Dest = id_Dest;
		this.causale = causale;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public float getImporto() {
		return importo;
	}
	
	public void setImporto(float importo) {
		this.importo = importo;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public int getId_Mitt() {
		return id_Mitt;
	}
	
	public void setId_Mitt(int id_Mitt) {
		this.id_Mitt = id_Mitt;
	}
	
	public int getId_Dest() {
		return id_Dest;
	}
	
	public void setId_Dest(int id_Dest) {
		this.id_Dest = id_Dest;
	}
	
	public String getCausale() {
		return causale;
	}
	
	public void setCausale(String causale) {
		this.causale = causale;
	}

	@Override
	public String toString() {
		return "Transazione [id=" + id + ", importo=" + importo + ", data=" + data + ", id_Mitt=" + id_Mitt
				+ ", id_Dest=" + id_Dest + ", causale=" + causale + "]";
	}	
}
