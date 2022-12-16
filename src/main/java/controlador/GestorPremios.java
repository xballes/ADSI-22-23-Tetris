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
	
	public String obtenerDetalles(String pNombre, String pPremio)
	{
		Gson json6 = new Gson();
		ResultSet r = SGBD.getInstancia().execSQL("SELECT descripcion, fechaObtencion FROM usuariopremio INNER JOIN premio ON nombrepremio=nombre WHERE nombreusuario='"+pNombre+"' AND nombrepremio='"+pPremio+"'");
		try
		{
			r.next();
			String descr = r.getString("descripcion");
			Date fecha = r.getDate("fechaObtencion");
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
	
	private class DatosPremio {
		String descr;
		Date fecha;
		
		public DatosPremio (String pDescr, Date pFecha) {
			descr = pDescr;
			fecha = pFecha;
			}
	}

	
}
