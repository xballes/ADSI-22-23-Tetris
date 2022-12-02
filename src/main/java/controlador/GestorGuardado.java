package controlador;

public class GestorGuardado {

	private static GestorGuardado puntero;
	
	
	
	private GestorGuardado() {}
	
	public static GestorGuardado getInstancia() {
		if (GestorGuardado.puntero == null){
			GestorGuardado.puntero = new GestorGuardado();
		}
			return GestorGuardado.puntero;
	}
	
	/*public void guardarPartida(Partida pPartida) {
		return GestorPartida.getInstancia().verificarUsuario(pPartida);
	}
	*/
	
	
	}