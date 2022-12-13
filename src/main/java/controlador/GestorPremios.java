package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorPremios {

private static GestorPremios puntero;	
	
	
	private GestorPremios() {}
	
	public static GestorPremios getInstancia()
	{
		if (GestorPremios.puntero == null) {GestorPremios.puntero = new GestorPremios();}
		return GestorPremios.puntero;
	}
	
	public String obtenerDetalles(String pNombre)
	{
		ResultSet r = SGBD.getInstancia().execSQL("SELECT descripcion FROM premio WHERE nombre='"+pNombre+"'");
		try
		{
			r.next();
			String res = r.getString("descripcion");
			r.close();
			return res;
		}
		catch (SQLException e)
		{
			return null;
		} 
		
	}
	
}
