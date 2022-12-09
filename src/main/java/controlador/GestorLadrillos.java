package controlador;


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
		
		SGBD.getInstancia().execSQLVoid("UPDATE  usuario SET setLadrillo="+idSetColores+" WHERE usuario='"+pNombre+"'");
		
		
	}

}