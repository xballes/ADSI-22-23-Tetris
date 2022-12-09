package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.GestorRanking;

public class GestorRankingTest {

	private GestorRanking gestor;
	
	public GestorRankingTest() {
		this.gestor = GestorRanking.getInstancia();
		
	}
	
	@Before
	public void setUp() throws Exception {
		this.gestor.borrarRankings();
		
	}

	@After
	public void tearDown() throws Exception {
		
		
		
	}

	@Test
	public void testObtenerRankingTodosNivelesPublico() {
		
	
		//Caso 1
		
		this.gestor.obtenerRankingTodosNivelesPublico()
		
		
	}
	
	private String[][] obtenerDatos(String json1) {
		
		
		String[] array = json1.split("}");
		String[] puntos = new String [array.length-1];
		String[] nivel = new String [array.length-1];
		String[] nombre = new String [array.length-1];
		String[][] resultado = new String[3][array.length-1];
		resultado[0] = puntos;
		resultado[1] = nivel;
		resultado[2] = nombre
				;


		
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
		
	}

}
