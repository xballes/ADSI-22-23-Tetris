package controlador;

import java.sql.ResultSet;

import com.zetcode.Board;

public class GestorPartida {

	private static GestorPartida puntero;
	//Board b;
	//puntuacion=;
	//nivel=;
	//matriz=;
	
	
	
	private GestorPartida() {}
	
	public static GestorPartida getInstancia() {
		if (GestorPartida.puntero == null){
			GestorPartida.puntero = new GestorPartida();
		}
			return GestorPartida.puntero;
	}
	
	/*public void guardarPartida(Partida pPartida) {
		
		ResultSet r = SGBD.getInstancia().execSQL("");
		
		
	}
	*/
	
	
	}