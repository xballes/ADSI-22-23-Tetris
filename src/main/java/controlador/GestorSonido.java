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
		
		SGBD.getInstancia().execSQLVoid("UPDATE usuario SET setSonido="+idSonido+" WHERE nombre='"+pNombre+"'");
		
		
	}
	
	
	public int obtenerSonido(String pNombre) {
		
		ResultSet r = SGBD.getInstancia().execSQL("SELECT setSonido FROM usuario WHERE nombre='"+pNombre+"'");
		int res = -1;
		
		
		try {
			r.next();
			res = r.getInt("setSonido");
			r.close();
			
			
		} catch (Exception e) {}
		
		return res;
	}
	
	
	
	
	public void tocarMusica(int pId) {this.r.empezarMusica(pId);}
	
	public void acabarMusica(boolean pGameOver) {this.r.acabarMusica(pGameOver);}
	
	public void pausa() {this.r.pausarMusica();}

	public void despausa() {this.r.despausarMusica();}

	public void sonarLineaLimpia() {this.r.sonarLineaLimpia();}
	
	public void sonarTetris() {this.r.sonarTetris();}

	
}