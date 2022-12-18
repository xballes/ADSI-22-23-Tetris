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
		// Pre: Usuario en BD, color valor [0,4]
		// Post: Se ha cambiado en BD el valor del set del usuario
		
		SGBD.getInstancia().execSQLVoid("UPDATE USUARIO SET SETLADRILLO="+idSetColores+" WHERE NOMBRE='"+pNombre+"'");
		
		
	}
	
	
	public int obtenerSet(String pNombre) {
		// Pre: Usuario en BD
		// Post: El valor del código del color del set de colores del usuario, para poder pintarlo después en Board
		
		ResultSet r = SGBD.getInstancia().execSQL("SELECT SETLADRILLO FROM USUARIO WHERE NOMBRE='"+pNombre+"'");
		int res = -1;
		
		
		try {
			r.next();
			res = r.getInt("setLadrillo");
			r.close();
			
			
		} catch (Exception e) {}
		
		return res;
	}

}