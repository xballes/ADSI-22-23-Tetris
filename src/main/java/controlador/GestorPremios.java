package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.google.gson.Gson;

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
		
		/* Pre: id = [0, 3], identifica el tipo de premio que se busca en el array de la clase (0 es ganar una partida, 1 es ganar 10 etc)
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
	
	
	
	public String obtenerDetalles(String pNombre, String pPremio)
	
	/* Pre: id = [0, 3], identifica el tipo de premio que se busca en el array de la clase (0 es ganar una partida, 1 es ganar 10 etc)
	 *      User en BD
	 * Post: Los detalles del premio obtenido
	  
	  
	  
	 */
	
	
	{
		Gson json6 = new Gson();
		ResultSet r = SGBD.getInstancia().execSQL("SELECT descripcion, fechaObtencion FROM usuariopremio INNER JOIN premio ON nombrepremio=nombre WHERE nombreusuario='"+pNombre+"' AND nombrepremio='"+pPremio+"'");
		try
		{
			r.next();
			String descr = r.getString("descripcion");
			Timestamp fecha = r.getTimestamp("fechaObtencion");
			DatosPremio datos = new DatosPremio(descr, fecha);
			r.close();
			String res = json6.toJson(datos);
			return res;
		}
		catch (SQLException e)
		{
			return null;
		} 
		
	}
	
	// Tuplas para poder almacenar varios valores a la vez y convertirlos a JSON
	
	// @SuppressWarnings --> Los atributos flageados como unused se usan para fabricar el JSON, pero el IDE no es capaz de deducirlo
	
	@SuppressWarnings("unused")

	private class DatosPremio {
		String descr;
		Timestamp fecha;
		
		public DatosPremio (String pDescr, Timestamp pFecha) {
			descr = pDescr;
			fecha = pFecha;
			}
	}
	
	
}
