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
		
		/* Pre: Strings no null
		 * Post: True si user con esa pass existe en BD, false si no
		  
		  
		 */

		ResultSet r = SGBD.getInstancia().execSQL("SELECT * FROM Usuario WHERE nombre='"+pNombre+"' && contraseña = '"+pCont+"'");
		boolean val;
		try {
			val = r.next();
			r.close();
		} catch (SQLException e) {return false;}
		
		return val;
	}
	
	
	public int registrar(String pNombre, String pCont, String pMail) {
		
		// Pre: strings no null
		// Post: 0 si correcto, 1 si demasiado largos o corto, 2 si username ya existente, 3 si mail ya existente, 4 si caracteres invalidos
	
		if (!this.alfanumerico(pNombre) || !this.alfanumerico(pCont) || !this.mailvValido(pMail)) {
			return 4;
		}
		
		if (pNombre.length() > 30 || pCont.length() > 30 || pMail.length() > 30) {return 1;}
		if (pNombre.length() == 0 || pCont.length() == 0 || pMail.length() == 0) {return 1;}

		ResultSet r = SGBD.getInstancia().execSQL("SELECT * FROM Usuario WHERE nombre='"+pNombre+"'");
		try {
			 if (r.next()) {r.close(); return 2;}
			 
			
				 
			ResultSet r2 = SGBD.getInstancia().execSQL("SELECT * FROM Usuario WHERE email='"+pMail+"'");

			if (r2.next()) {r.close(); r2.close(); return 3;}
			else {
				
				SGBD.getInstancia().execSQLVoid("INSERT INTO USUARIO VALUES('"+pNombre+"', '"+pCont+"', '"+pMail+"', 1, 1, 1, 0)");
				r.close(); r2.close();
				return 0;
			
			}
			
			
			
			
		} catch (SQLException e) {
			return 2;
		} 

	}
	
	
	public String obtContraseña(String pMail) {
		
		// Pre: No hay dos mails repetidos en BD
		// Post: Si coinicide, la string con la contraseña, else, null
		
		ResultSet r = SGBD.getInstancia().execSQL("SELECT contraseña FROM Usuario WHERE email='"+pMail+"'");
		try {
			if (r.next()) {
				String res = r.getString(1);
				r.close();
				return res;
				
			} else {return null;}
			
		} catch (SQLException e) {
			return null;
		} 
		
	}
	
	
	
	public boolean cambiarCont(String pNombre, String pCont) {
		
		// Pre: pNombre es ñnico en BD y estñ
		// Post: Se cambia la contraseña si se encuentra el user y se returneo true. 
		//       Return true si contraseña nueva alfanumñrica y tiene 1-30 chars
		
		if (pCont.length() > 30 || pCont.length() == 0) {return false;}
		else if (!this.alfanumerico(pCont)) {return false;}
		else {
			SGBD.getInstancia().execSQLVoid("UPDATE Usuario SET contraseña = '"+pCont+"' WHERE nombre = '"+pNombre+"'");
			return true;
		}
		
	}
	
	public boolean borrarUsuario(String pNombre) {
		
		// Pre: String no null
		// Post: True si se borrñ alguien, false si no. Nota: El usuario administrador tiene inmunidad al borrado
		
		if (pNombre.toLowerCase().contentEquals("administrador")) {return false;}
		else if (pNombre.length() == 0) {return false;}
		else {
			
			ResultSet r = SGBD.getInstancia().execSQL("SELECT nombre FROM Usuario WHERE nombre = '"+pNombre+"'");
			boolean enc;
			try {
				enc = r.next();
				r.close();
			} catch (SQLException e) {
				return false;
			}
			
			if (enc) {
				SGBD.getInstancia().execSQLVoid("DELETE FROM Usuario WHERE nombre = '"+pNombre+"'");
			} 
			
			return enc;
			
			
		}
		
	}
	
	
	private boolean alfanumerico(String pS) {
		
		// Pre: String no null
		// Post: return true <---> pS contiene caracteres alfanumericos incluyendo espacio y no estñ compuesta por solo espacios
		
		boolean val = true;
		boolean soloEspacios = true;
		int i = 0;
		String s = pS.toLowerCase();
		
		if (s.length() == 0) {return true;}
		
		while (i != s.length() && val) {
			char c =  s.charAt(i);
			val = (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == 'ñ' || c == 'ñ' || c == 'ñ' || c == 'ñ' || c == 'ñ' | c == ' ';
			
			if (c != ' ' && soloEspacios) {soloEspacios = false;}
			
			i++;
		}
		
		
		return val && !soloEspacios;
	}
	
	private boolean mailvValido(String pS) {
		
		// Pre: String no null
		// Post: return true <---> pS contiene caracteres alfanumericos incluyendo @ y .
		
		boolean val = true;
		int i = 0;
		String s = pS.toLowerCase();
		
		while (i != s.length() && val) {
			char c =  s.charAt(i);
			val = (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == 'ñ' || c == 'ñ' || c == 'ñ' || c == 'ñ' || c == 'ñ' || c == '@' || c == '.'; 
			i++;
		}
		
		
		return val;
	}
	
	
	public void sumarVictoriaA(String pUser) {
		
		// Pre: User en BD (ñnico)
		// Post: al user en BD -->  victoriasDeUser = victoriasDeUser + 1
		
		SGBD.getInstancia().execSQLVoid("UPDATE usuario SET victorias = victorias + 1 WHERE nombre = '"+pUser+"'");
		
	}
	
	
	public int obtenerNumVictoriasDe(String pUser) {
		
		
		// Pre: User en BD (ñnico)
		// Post: Num de victorias del user
		
		ResultSet r = SGBD.getInstancia().execSQL("SELECT victorias FROM usuario WHERE nombre = '"+pUser+"'");
		
		try {
			r.next();
			int val = r.getInt("victorias");
			r.close();
			return val;
		} catch (Exception e) {return -1;}
	}
	
	
	
	// EXCLUSIVO PARA JUNIT
	
	public int contarUsuariosConEseNomnbre(String pUser) {
		
		int i = 0;
		
		ResultSet r = SGBD.getInstancia().execSQL("SELECT nombre FROM Usuario WHERE nombre = '"+pUser+"'");
		
		try {
			while (r.next()) {i++;}
			r.close();
		} catch (SQLException e) {
			return 0;
		}
		return i;
	}
	
	public void borrarTodosLosUsers() {
		
		SGBD.getInstancia().execSQLVoid("DELETE FROM USUARIO");
		this.registrar("Administrador", "123456", "email");
		
	}
	
	public String obtContraseñaDe(String pUser) {
		
		
		ResultSet r = SGBD.getInstancia().execSQL("SELECT contraseña FROM Usuario WHERE nombre = '"+pUser+"'");
		
		try {
			String res = null;
			if (r.next()) {
				res = r.getString(1);
			} 
			r.close();
			return res;

		} catch (SQLException e) {
			return null;
		}
	}
	


}
