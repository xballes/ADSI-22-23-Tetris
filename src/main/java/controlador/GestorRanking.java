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
		
		/* Pre: NULL
		 * Post: String en formato JSON con el Top 10 puntuaciones de todos los tiempos 
		         identificado por user, nivel y puntuacion
		  
		  
		 */
		
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
		
		/* Pre: Nivel 1-3
		 * Post: String en formato JSON con el Top 10 puntuaciones de ese nivel
		        identificado por user y puntuacion
		  
		 */
	
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
	
	
	public String obtenerRankingTodosNivelPersonal(String pNombre) {
		
		/* Pre: Nombre de user en BD
		 * Post: String en formato JSON con el Top 10 puntuaciones de ese user
		        identificado por nivel y puntuacion
		  
		 */
		
	
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
		
		/* Pre: User en BD, Nivel 1-3
		 * Post: String en formato JSON con el Top 10 puntuaciones de ese nivel por ese user
		        identificado por puntuacion
		  
		 */
		
		
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
	
	
	public void publicarPuntuacion(String pUser, int pPuntos, int pNivel) {
		
		/* Pre: User en BD, nivel 1-3
		 * Post: Puntuación insertada en BD
		  
		  
		  
		 */
		
		
		SGBD.getInstancia().execSQLVoid("INSERT INTO puntuacion(nivel, puntosActuales, usuario) VALUES ("+pNivel+", "+pPuntos+",'"+pUser+"')");
	}
	
	//Metodo exclusivo para las pruebas JUnit
	
	public void borrarRankings() {
		SGBD.getInstancia().execSQLVoid("DELETE FROM PUNTUACION");
	}
	
	// Tuplas para poder almacenar varios valores a la vez y convertirlos a JSON
	
	// @SuppressWarnings --> Los atributos flageados como unused se usan para fabricar el JSON, pero el IDE no es capaz de deducirlo
	
	private class Tripleta {
		@SuppressWarnings("unused")  
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