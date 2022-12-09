package controlador;


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
		
		SGBD.getInstancia().execSQLVoid("UPDATE  usuario SET setColor="+idColor+" WHERE usuario='"+pNombre+"'");
		
		
	}

}