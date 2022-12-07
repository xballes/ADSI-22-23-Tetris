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
			 Tripleta tri = new Tripleta(puntos,nivel, nombre);
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
	
	
	public String obtenerRankingNivelPublico(int pNivel) {
	
		Gson json2 = new Gson();
		
		int niv = pNivel;
		
		
		ResultSet resulNivel = SGBD.getInstancia().execSQL("SELECT usuario, puntosActuales FROM PUNTUACION  WHERE nivel = "+niv+"  ORDER BY puntosActuales DESC");
		
		ArrayList<DuplaNivel> obj = new ArrayList<DuplaNivel>();
		
		try {
			
			int cont=0;
			while( resulNivel.next() && cont < 10) {
				String nombre = resulNivel.getString("usuario");
				int puntos = resulNivel.getInt("puntosActuales");
				DuplaNivel dup = new DuplaNivel(puntos, nombre);
				obj.add(dup);
				cont++;
			}
			resulNivel.close();
		} catch (SQLException e) {
			
		}
		
		String res = json2.toJson(obj);
		
		return res;
		
	}
	
	//metodos para ver ranking privado
	
	public String obtenerRankingTodosNivelPersonal(String pNombre) {
	
		Gson json3 = new Gson();
		
		ResultSet resulTodos = SGBD.getInstancia().execSQL("SELECT nivel, puntosActuales FROM PUNTUACION WHERE usuario='"+pNombre+"' ORDER BY puntosActuales DESC");
		
		ArrayList<DuplaTodos> objetos = new ArrayList<DuplaTodos>();
		
		try {
			
			int cont=0;
			while( resulTodos.next() && cont<10) {
				int nivel = resulTodos.getInt("nivel");
				int puntos = resulTodos.getInt("puntosActuales");
				DuplaTodos dupla = new DuplaTodos(puntos,nivel);
				objetos.add(dupla);
				cont++;
			}
			resulTodos.close();
			
		} catch (SQLException e) {
		
		}
		
		String result = json3.toJson(objetos);
		
		return result;
	}
	
	
	public ArrayList<Integer> obtenerRankingNivelPriv(String pNombre,int pNivel) {
		
		ResultSet resulNivel = SGBD.getInstancia().execSQL("SELECT puntosActuales FROM PUNTUACION WHERE nivel="+pNivel+" && usuario='"+pNombre+"'  ORDER BY puntosActuales DESC");
		
		ArrayList<Integer> puntuaciones = new ArrayList<Integer>();
		
		try {
			int cont=0;
			while( resulNivel.next() && cont<10) {
				int puntos = resulNivel.getInt("puntosActuales");
				puntuaciones.add(puntos);
				cont++;
			}
			
		} catch (SQLException e) {
			
		}
		
		return puntuaciones;
	}
	
	
	//clases privadas
	
	private class Tripleta {
		@SuppressWarnings("unused")  // Se usan en el JSON impreso, pero el IDE no es capaz de deducirlo
		int punt;
		@SuppressWarnings("unused")
		int nivel;
		@SuppressWarnings("unused")
		String nombre;
		
		public Tripleta (int val1, int val2, String val3) {
			punt = val1;
			nivel = val2;
			nombre = val3;
			}
	}
	
	
	private class DuplaNivel {
		@SuppressWarnings("unused")
		int puntos;
		@SuppressWarnings("unused")
		String nombre;
		
		public DuplaNivel (int val1, String val2) {
			puntos = val1;
			nombre = val2;
		}
	}
	
	
	private class DuplaTodos {
		@SuppressWarnings("unused")
		int nivel;
		@SuppressWarnings("unused")
		int puntos;
		
		public DuplaTodos(int val1, int val2) {
			nivel = val1;
			puntos = val2;
		}
	}

}