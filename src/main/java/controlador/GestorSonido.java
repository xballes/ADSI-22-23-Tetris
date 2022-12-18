package controlador;

import java.sql.ResultSet;

import modelo.Reproductor;

public class GestorSonido {
	
	private Reproductor r;
	private static GestorSonido puntero;
	
	private GestorSonido() {
		
		this.r = new Reproductor();
	}
	
	public static GestorSonido getInstancia() {
		if (GestorSonido.puntero == null){
			GestorSonido.puntero = new GestorSonido();
		}
			return GestorSonido.puntero;
			
	}
	
	
	public void cambiarSonido(String pNombre, int idSonido ) {
		/* Pre: User en BD, sonido 0-2
		 * Post: Audio por defecto cambiado para ese user en BD
		  
		 */
		
		
		SGBD.getInstancia().execSQLVoid("UPDATE USUARIO SET SETSONIDO="+idSonido+" WHERE NOMBRE='"+pNombre+"'");
		
		
	}
	
	
	public int obtenerSonido(String pNombre) {
		
		/* Pre: User en BD, es único
		 * Post: Audio por defecto obtenido de BD
		  
		 */
		
		ResultSet r = SGBD.getInstancia().execSQL("SELECT SETSONIDO FROM USUARIO WHERE NOMBRE='"+pNombre+"'");
		int res = -1;
		
		
		try {
			r.next();
			res = r.getInt("SETSONIDO");
			r.close();
			
			
		} catch (Exception e) {}
		
		return res;
	}
	
	
	// Métodos para cominicarse con el reproductor, documentados en reproductor (en package Modelo)
	
	
	public void tocarMusica(int pId) {this.r.empezarMusica(pId);}
	
	public void acabarMusica(boolean pGameOver) {this.r.acabarMusica(pGameOver);}
	
	public void pausa() {this.r.pausarMusica();}

	public void despausa() {this.r.despausarMusica();}

	public void sonarLineaLimpia() {this.r.sonarLineaLimpia();}
	
	public void sonarTetris() {this.r.sonarTetris();}

	
}