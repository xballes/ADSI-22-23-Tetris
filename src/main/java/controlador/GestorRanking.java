package controlador;

public class GestorRanking {

	private static GestorRanking puntero;
	
	public static GestorRanking getInstancia() {
		if (GestorRanking.puntero == null) {GestorRanking.puntero = new GestorRanking(); }
		return GestorRanking.puntero;
	}
	
	
	//prueba
}
