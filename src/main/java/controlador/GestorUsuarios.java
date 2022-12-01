package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorUsuarios {

	private static GestorUsuarios puntero;
	
	
	private GestorUsuarios() {}
	
	public static GestorUsuarios getInstancia() {
		if (GestorUsuarios.puntero == null) {GestorUsuarios.puntero = new GestorUsuarios();}
		return GestorUsuarios.puntero;
	}
	
	public boolean verificarUsuario(String pNombre, String pCont) {

		ResultSet r = SGBD.getInstancia().execSQL("SELECT * FROM Usuario WHERE nombre='"+pNombre+"' && contraseña = '"+pCont+"'");
		try {
			return r.next();
		} catch (SQLException e) {
			return false;
		} 
	}
	
	
	public int registrar(String pNombre, String pCont, String pMail) {
		
		// Pre: null
		// Post: 0 si correcto, 1 si demasiado largos, 2 si username ya existente, 3 si mail ya existente
		
		
		if (pNombre.length() > 30 || pCont.length() > 30 || pMail.length() > 30) {return 1;}
		if (pNombre.length() == 0 || pCont.length() == 0 || pMail.length() == 0) {return 1;}

		ResultSet r = SGBD.getInstancia().execSQL("SELECT * FROM Usuario WHERE nombre='"+pNombre+"'");
		try {
			 if (r.next()) {return 2;}
			 
			
				 
			ResultSet r2 = SGBD.getInstancia().execSQL("SELECT * FROM Usuario WHERE nombre='"+pMail+"'");

			if (r2.next()) {return 3;}
			else {
				
				SGBD.getInstancia().execSQLVoid("INSERT INTO USUARIO VALUES('"+pNombre+"', '"+pCont+"', '"+pMail+"', 1, 1, 1)");
				return 0;
			
			}
			
			
			
			
		} catch (SQLException e) {
			return 2;
		} 

	}
	
	
	public String obtContraseña(String pMail) {
		
		ResultSet r = SGBD.getInstancia().execSQL("SELECT contraseña FROM Usuario WHERE email='"+pMail+"'");
		try {
			if (r.next()) {
				return r.getString(1);
				
			} else {return null;}
			
		} catch (SQLException e) {
			return null;
		} 
		
	}
	
	
	
	public boolean cambiarCont(String pNombre, String pCont) {
		if (pCont.length() > 30) {return false;}
		else {
			SGBD.getInstancia().execSQLVoid("UPDATE Usuario SET contraseña = '"+pCont+"' WHERE nombre = '"+pNombre+"'");
			return true;
		}
		
	}
	
	public boolean borrarUsuario(String pNombre) {
		if (pNombre.contentEquals("administrador")) {return false;}
		else {
			
			ResultSet r = SGBD.getInstancia().execSQL("SELECT nombre FROM Usuario WHERE nombre = '"+pNombre+"'");
			boolean enc;
			try {
				enc = r.next();
			} catch (SQLException e) {
				return false;
			}
			
			if (enc) {
				SGBD.getInstancia().execSQLVoid("DELETE FROM Usuario WHERE nombre = '"+pNombre+"'");
			} 
			
			return enc;
			
			
		}
		
	}
	

}
