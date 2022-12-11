package controlador;

import java.sql.ResultSet;

public class GestorSonido {
	private static GestorSonido puntero;
	
	private GestorSonido() {}
	
	public static GestorSonido getInstancia() {
		if (GestorSonido.puntero == null){
			GestorSonido.puntero = new GestorSonido();
		}
			return GestorSonido.puntero;
			
	}
	
	
	public void cambiarSonido(String pNombre, int idSonido ) {
		
		SGBD.getInstancia().execSQLVoid("UPDATE  Usuario SET setSonido="+idSonido+" WHERE nombre='"+pNombre+"'");
		
		
	}

}
