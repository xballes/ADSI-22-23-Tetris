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
		
		/* Pre: id = [0, 3], identifica el tipo de premio que se busca en el array de la clase
		 *      User en BD
		 * Post: True -> El premio lo tiene el user | False -> no lo tiene
		  
		  
		  
		 */
		
		ResultSet r = SGBD.getInstancia().execSQL("SELECT * FROM USUARIOPREMIO WHERE NOMBREUSUARIO = '"+pUser+"' AND NOMBREPREMIO = '"+this.premiosDisponibles[id]+"'");
		boolean tiene = false;
		
		try {
			tiene = r.next();
			r.close();
			return tiene;
		} catch (Exception e) {return false;}
	}
	
	
	public void darPremio(String pUser, int id, Timestamp pFecha) {
		
		/* Pre:  id = [0, 3], identifica el tipo de premio que se busca en el array de la clase
		         User en BD.     pFecha NO NULL.  El usuario no posee el premio identificado
		   Post: Se ha insertado la relación Premio-Usuario      
		  
		  
		 */
		
		SGBD.getInstancia().execSQLVoid("INSERT INTO USUARIOPREMIO VALUES('"+pUser+"','"+this.premiosDisponibles[id]+"', '"+pFecha+"')");
	}
	
	
	
}
