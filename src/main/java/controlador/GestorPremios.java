package controlador;

import java.sql.ResultSet;
import java.sql.Timestamp;

public class GestorPremios {

	
	private static GestorPremios puntero;
	
	private String[] premiosDisponibles;
	
	private GestorPremios() {
		this.premiosDisponibles = new String[4];
		this.premiosDisponibles[0] = "Gana 1 partida";
		this.premiosDisponibles[1] = "Gana 10 partidas";
		this.premiosDisponibles[2] = "Gana 25 partidas";
		this.premiosDisponibles[3] = "Tetris!";

	}
	
	public static GestorPremios getInstancia() {
		if (GestorPremios.puntero == null) {GestorPremios.puntero = new GestorPremios();}
		return GestorPremios.puntero;
	}
	
	public boolean tieneElPremio(String pUser, int id) {
		ResultSet r = SGBD.getInstancia().execSQL("SELECT * FROM usuariopremio WHERE nombreUsuario = '"+pUser+"' AND nombrePremio = '"+this.premiosDisponibles[id]+"'");
		boolean tiene = false;
		
		try {
			tiene = r.next();
			r.close();
			return tiene;
		} catch (Exception e) {return false;}
	}
	
	
	public void darPremio(String pUser, int id, Timestamp pFecha) {
		
		SGBD.getInstancia().execSQLVoid("INSERT INTO usuariopremio VALUES('"+pUser+"','"+this.premiosDisponibles[id]+"', '"+pFecha+"')");
	}
	
	
	
}
