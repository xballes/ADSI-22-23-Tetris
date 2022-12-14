package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import com.google.gson.Gson;

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
		ResultSet r = SGBD.getInstancia().execSQL("SELECT descripcion, fechaObtencion FROM premio WHERE nombre='"+pNombre+"'");
		try
		{
			Gson json6 = new Gson();
			r.next();
			String descr = r.getString("descripcion");
			Date fecha = r.getDate("fechaObtencion");
			//Añadir a json
			r.close();
			return res;
		}
		catch (SQLException e)
		{
			return null;
		} 
		
	}
	
}
