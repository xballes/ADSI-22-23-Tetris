package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class GestorRanking {

	private static GestorRanking puntero;
	
	public static GestorRanking getInstancia() {
		if (GestorRanking.puntero == null) {
			GestorRanking.puntero = new GestorRanking(); 
		}
		return GestorRanking.puntero;
	}
	
	
	//metodos para ver ranking general
	
	public Gson obtenerRankingTodosNivelesPublico() {
		
		Gson json1 = new Gson();
		
		
		ResultSet resul = SGBD.getInstancia().execSQL("SELECT nombreUsu, nivel, puntosAct FROM PUNTUACION ORDER BY puntosAct DESC");
		
		boolean enc = false;
		
		try {
			enc = resul.next();
			resul.close();
		} catch (SQLException e) {
			
		}
		
		
		//prueba22
		
		return json1;
		
	}
	
	
	public Gson obtenerRankingNivelPublico(int pNivel) {
	
		Gson json2 = new Gson();
		
		return json2;
	}
	
	//metodos para ver ranking privado
	
	public Gson obtenerRankingTodosNivelesPriv() {
	
		Gson json3 = new Gson();
		
		
		
		
		return json3;
	}
	
	
	
	
	public Gson obtenerRankingNivelPriv(int pNivel) {
		
		Gson json4 = new Gson();
		
		ArrayList<Integer> al = new ArrayList<>();
		
		String res = json4.toJson(al);
		
		
		return json4;
	}
	
	
	//prueba
}
