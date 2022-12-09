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
		
		ResultSet resul = SGBD.getInstancia().execSQL("UPDATE  Usuario SET setColor="+idColor+" WHERE usuario='"+pNombre+"'");
		
		
	}

}
