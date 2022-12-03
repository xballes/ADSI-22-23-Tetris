package controlador;

import java.sql.ResultSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.time.LocalDate;

import com.zetcode.Board;

public class GestorPartida {

	private static GestorPartida puntero;

	
	private GestorPartida() {}
	
	public static GestorPartida getInstancia() {
		if (GestorPartida.puntero == null){
			GestorPartida.puntero = new GestorPartida();
		}
			return GestorPartida.puntero;
	}
	
	public void guardarPartida(Board pPartida) {
		int[][]matriz=pPartida.calcularMatriz(); 
		LocalDate now = LocalDate.now();
		String fecha=now.toString();
		System.out.println(fecha);
		int puntuacion=pPartida.getNumLinesRemoved();
		String nombreUsuario=pPartida.getNombreUsuario();
		//SGBD.getInstancia().execSQLVoid("INSERT INTO partida_guardada VALUES (%fecha%,%puntuacion%,%nivel%)");
		int numcolumnas=pPartida.getBOARD_WIDTH(); //i -> numcolumna
		int numaltura=pPartida.getBOARD_HEIGHT(); //j-> numaltura
		int i =0;
		int[]alturas=new int[numaltura*numcolumnas];
		while(i<numcolumnas) {
			for(int j=0;j<numaltura;j++) {
				int alt=(matriz[j][i]); //va a recorrer cada altura de la primera columna,luego de la segunda columna...
				alturas[j]=alt;
				System.out.println(alturas[j]);
			}	
			//String sentenciaSQL = "INSERT INTO COLUMNA(usuario,fechaPartida,numcolumna,alt1,alt2,alt3,alt4,alt5,alt6,alt7,alt8,alt9,alt10,alt11,alt12,alt13,alt14,alt15,alt16,alt17,alt18,alt19,alt20,alt21,alt22) VALUES(%nombreUsuario%,%fecha%,%i%,%alt1%,%alt1%,%alt2%,%alt3%,%alt4%,%alt5%,%alt6%,%alt7%,%alt8%,%alt9%,%alt10%,%alt11%,%alt12%,%alt13%,%alt14%,%alt15%,%alt16%,%alt17%,%alt18%,%alt19%,%alt20%,%alt21%,%alt22%";
			//SGBD.getInstancia().execSQLVoid(sentenciaSQL);
			i++;	
		}	
	}
}

