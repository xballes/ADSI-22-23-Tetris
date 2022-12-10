package vista;

import java.sql.ResultSet;
import java.util.ArrayList;

import controlador.GestorRanking;
import controlador.SGBD;


public class Main {
	
	
	// Teoricamente no interfaz, solo hace la llamada a la primera interfaz
	
	public static void main (String args[]) {
		
		//MenuPrincipal.visibilizar();
		
		String resul = GestorRanking.getInstancia().obtenerRankingTodosNivelesPublico();
		
		
		String[] array = resul.split("}");
		String[] puntos = new String [array.length-1];
		String[] nivel = new String [array.length-1];
		String[] nombre = new String [array.length-1];
		String[][] resultado = new String[3][array.length-1];
				
		for (int i = 0; i != array.length-1; i++) {
			int ind = 0;
			
			while(array[i].charAt(ind)!=':') {
				ind++;
			}
			String aux = "";
			ind++;
			
			while(array[i].charAt(ind)!=',') {
				aux = aux + array[i].charAt(ind);
				ind++;
			}
			
			puntos[i] =aux;
			
			while(array[i].charAt(ind)!=':') {
				ind++;
			}
			
			ind++;
			
			nivel[i] = array[i].charAt(ind)+"";
			

			while(array[i].charAt(ind)!=':') {
				ind++;
			}
			
			ind++;
			ind++;
			
			aux = "";
			
			while(array[i].charAt(ind)!='"') {
				aux = aux + array[i].charAt(ind);
				ind++;
			}
			
			nombre[i]=aux;
		}
		
		resultado[0] = puntos;
		resultado[1] = nivel;
		resultado[2] = nombre;
	
		System.out.println(resultado[0].length);
	
		
	}
}

