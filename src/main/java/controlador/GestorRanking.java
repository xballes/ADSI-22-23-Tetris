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
	
	public String obtenerRankingTodosNivelesPublico() {
		
		Gson json1 = new Gson();
		
		
		ResultSet resul = SGBD.getInstancia().execSQL("SELECT usuario, nivel, puntosActuales FROM PUNTUACION ORDER BY puntosActuales DESC");
		
		ArrayList<Tripleta> objeto = new ArrayList<Tripleta>();
		
		try {
		
			int cont = 0;
			while (resul.next() && cont < 10){
			 String nombre  = resul.getString("usuario");
			 int  nivel = resul.getInt("nivel");
			 int puntos = resul.getInt("puntosActuales");
			 Tripleta tri = new Tripleta(nivel, puntos, nombre);
			 objeto.add (tri);
			 cont++;
			}
			resul.close();
		}catch (SQLException e) {
				
		}
	
		String res = json1.toJson(objeto);
		
		System.out.println(objeto.size());
		System.out.println(res);
		
		return res;
		
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
	
	
	//clase privada
	
	private class Tripleta {
		int punt;
		int nivel;
		String nombre;
		
		public Tripleta (int val1, int val2, String val3) {
			punt = val1;
			nivel = val2;
			nombre = val3;
			}
	}
	

}
