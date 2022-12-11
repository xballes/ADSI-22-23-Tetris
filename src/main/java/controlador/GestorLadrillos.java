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
		
		SGBD.getInstancia().execSQLVoid("UPDATE  Usuario SET setLadrillo="+idSetColores+" WHERE nombre='"+pNombre+"'");
		
		
	}

}
