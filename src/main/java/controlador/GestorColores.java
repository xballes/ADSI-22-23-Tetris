package controlador;

import java.sql.ResultSet;

public class GestorColores {
	private static GestorColores puntero;
	
	private GestorColores() {}
	
	public static GestorColores getInstancia() {
		if (GestorColores.puntero == null){
			GestorColores.puntero = new GestorColores();
		}
			return GestorColores.puntero;
			
	}
	
	
	public void cambiarColor(String pNombre, int idColor ) {
		
		SGBD.getInstancia().execSQLVoid("UPDATE  usuario SET setColor="+idColor+" WHERE nombre='"+pNombre+"'");
		
		
	}
	
	public int obtenerColor(String pNombre) {
		
		ResultSet r = SGBD.getInstancia().execSQL("SELECT setColor FROM usuario WHERE nombre='"+pNombre+"'");
		int res = -1;
		
		
		try {
			r.next();
			res = r.getInt("setColor");
			r.close();
			
			
		} catch (Exception e) {}
		
		return res;
	}

}