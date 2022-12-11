package controlador;

import java.sql.ResultSet;

public class GestorLadrillos {
	private static GestorLadrillos puntero;
	
	private GestorLadrillos() {}
	
	public static GestorLadrillos getInstancia() {
		if (GestorLadrillos.puntero == null){
			GestorLadrillos.puntero = new GestorLadrillos();
		}
			return GestorLadrillos.puntero;
			
	}
	
	
	public void editarLadrillos(String pNombre, int idSetColores ) {
		
		SGBD.getInstancia().execSQLVoid("UPDATE  usuario SET setLadrillo="+idSetColores+" WHERE nombre='"+pNombre+"'");
		
		
	}
	
	
	public int obtenerSet(String pNombre) {
		
		ResultSet r = SGBD.getInstancia().execSQL("SELECT setLadrillo FROM usuario WHERE nombre='"+pNombre+"'");
		int res = -1;
		
		
		try {
			r.next();
			res = r.getInt("setLadrillo");
			r.close();
			
			
		} catch (Exception e) {}
		
		return res;
	}

}