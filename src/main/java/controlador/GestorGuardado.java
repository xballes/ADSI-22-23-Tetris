package controlador;

import com.zetcode.Board;

public class GestorGuardado {

	private static GestorGuardado puntero;
	
	
	
	
	private GestorGuardado() {}
	
	public static GestorGuardado getInstancia() {
		if (GestorGuardado.puntero == null){
			GestorGuardado.puntero = new GestorGuardado();
		}
			return GestorGuardado.puntero;
	}
	
	public void guardarPartida(Board pPartida) {
		int[][]matriz=pPartida.calcularMatriz();
		SGBD.getInstancia().execSQLVoid("execSQL(\"INSERT INTO PARTIDA (puntuacion,nivel,usuario,fechaPartida) VALUES (%puntuacion%,%nivel%,%usuario%,%fechaPartida%)");
		
		
		
	}
	
	
	
	}