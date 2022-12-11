package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;


import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controlador.GestorRanking;
import controlador.GestorUsuarios;

public class GestorRankingTests {

	private GestorRanking gestor;
	private GestorUsuarios gestor1;
	
	public GestorRankingTests() {
		this.gestor = GestorRanking.getInstancia();
		this.gestor1 = GestorUsuarios.getInstancia();
	}
	
	@Before
	public void setUp() throws Exception {
		this.gestor.borrarRankings();
		this.gestor1.borrarTodosLosUsers();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	//Caso de uso: Ver ranking general absoluto 
	@Test
	public void testObtenerRankingTodosNivelesPublico() {
	
	//Creación de usuarios para las pruebas:
		
		this.gestor1.registrar("Per1", "Hola1", "gg");
		this.gestor1.registrar("Per2", "Hola2", "gg1");
		
	//Caso 1: Nunca se ha completado una partida (Tabla vacía)
		
		String json1 =	this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] matriz = this.obtenerDatos(json1);
	
		assertTrue(matriz[0].length==0); //no hay puntos
		assertTrue(matriz[1].length==0); //no hay niveles
		assertTrue(matriz[2].length==0); // no hay usuarios
	
	//Caso 2: Se ha completado una partida (La tabla tiene una fila)
		
		this.gestor.publicarPuntuacion("Per1", 5, 1);
		String json2 =	this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] matriz1 = this.obtenerDatos(json2);
	
		//Solo hay una puntuacion de un nivel y de una persona
		assertTrue(matriz1[0].length==1); 
		assertTrue(matriz1[1].length==1); 
		assertTrue(matriz1[2].length==1);
		assertTrue(matriz1[0][0].contentEquals("5"));
		assertTrue(matriz1[1][0].contentEquals("1"));
		assertTrue(matriz1[2][0].contentEquals("Per1"));
	
	//Caso 3: Se han completado múltiples partidas (La tabla tiene varias filas)
	
		this.gestor.publicarPuntuacion("Per2", 3, 1);
		this.gestor.publicarPuntuacion("Per1", 10, 2);
		String json3 =	this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] matriz2= this.obtenerDatos(json3);
		
		//Existen varias puntuacion en varios niveles y de mas de una persona
		assertTrue(matriz2[0].length>1); 
		assertTrue(matriz2[1].length>1); 
		assertTrue(matriz2[2].length>1);
		assertTrue(matriz2[0][0].contentEquals("10"));
		assertTrue(matriz2[1][0].contentEquals("2"));
		assertTrue(matriz2[2][0].contentEquals("Per1"));
		assertTrue(matriz2[0][1].contentEquals("5"));
		assertTrue(matriz2[1][1].contentEquals("1"));
		assertTrue(matriz2[2][1].contentEquals("Per1"));
		assertTrue(matriz2[0][2].contentEquals("3"));
		assertTrue(matriz2[1][2].contentEquals("1"));
		assertTrue(matriz2[2][2].contentEquals("Per2"));
	}
	
	//Caso de uso: Ver ranking general por nivel
	@Test
	public void testObtenerRankingNivelPublico() {
		
		//Creación de usuarios para las pruebas:
		
		this.gestor1.registrar("Per1", "Hola1", "gg");
		this.gestor1.registrar("Per2", "Hola2", "gg1");
	
		
		//Caso 1: No hay puntuaciones en ningun nivel
		String json2 =	this.gestor.obtenerRankingNivelPublico(1);
		String json3 =	this.gestor.obtenerRankingNivelPublico(2);
		String json4 =	this.gestor.obtenerRankingNivelPublico(3);
		String[][] mat = this.obtenerDatosNivel(json2);
		String[][] mat1 = this.obtenerDatosNivel(json3);
		String[][] mat2 = this.obtenerDatosNivel(json4);
		
		assertTrue(mat[0].length==0); //no hay puntos
		assertTrue(mat[1].length==0); //no hay nombres
		assertTrue(mat1[0].length==0);
		assertTrue(mat1[1].length==0); 
		assertTrue(mat2[0].length==0);
		assertTrue(mat2[1].length==0); 
		
		//Caso 2: En un nivel hay puntuaciones y en otro/otros no
		//Caso 3: Hay puntuacion para un nivel en concreto, aparece en el ranking global y en el del nivel concreto, en este caso el nivel 1
		
		//Hay entradas del nivel 1 en el global pero no del nivel 2 ni del 3
		this.gestor.publicarPuntuacion("Per2", 12, 1);
		String json5 =	this.gestor.obtenerRankingNivelPublico(1);
		String json6 =	this.gestor.obtenerRankingNivelPublico(2);
		String json7 =	this.gestor.obtenerRankingNivelPublico(3);
		String json8 = this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] mat3 = this.obtenerDatosNivel(json5);
		String[][] mat4 = this.obtenerDatosNivel(json6);
		String[][] mat5 = this.obtenerDatosNivel(json7);
		String[][] mat6 = this.obtenerDatos(json8);
		
		//Hay datos para el nivel 1 pero no del nivel 2 ni del 3
		assertTrue(mat6[0][0].contentEquals("12"));
		assertTrue(mat6[1][0].contentEquals("1"));
		assertTrue(mat6[2][0].contentEquals("Per2"));
		
		assertTrue(mat3[0].length>=1); //Hay datos en el nivel 1
		assertTrue(mat4[0].length==0); //No hay datos en el nivel 2
		assertTrue(mat5[0].length==0); //No hay datos en el nivel 3
		
		assertTrue(mat3[0][0].contentEquals("12"));
		assertTrue(mat3[1][0].contentEquals("Per2"));

		//Caso 4: Hay entradas en el global y varias coinciden con un nivel en concreto
		
		this.gestor.publicarPuntuacion("Per1", 100, 3);
		this.gestor.publicarPuntuacion("Per2", 50, 3);
		String json11 =	this.gestor.obtenerRankingNivelPublico(3);
		String json12 = this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] mat9 = this.obtenerDatosNivel(json11);
		String[][] mat10 = this.obtenerDatos(json12);
		
		assertTrue(mat10[0].length>1);
		assertTrue(mat9[0].length>1);
		
		assertTrue(mat10[0][0].contentEquals("100"));
		assertTrue(mat10[1][0].contentEquals("3"));
		assertTrue(mat10[2][0].contentEquals("Per1"));
		
		assertTrue(mat10[0][1].contentEquals("50"));
		assertTrue(mat10[1][1].contentEquals("3"));
		assertTrue(mat10[2][1].contentEquals("Per2"));
		
		assertTrue(mat9[0][0].contentEquals("100"));
		assertTrue(mat9[1][0].contentEquals("Per1"));
		assertTrue(mat9[0][1].contentEquals("50"));
		assertTrue(mat9[1][1].contentEquals("Per2"));
	}
	
	
	//Caso de uso: Ver ranking personal absoluto
	@Test
	public void testObtenerRankingTodosNivelPersonal() {
		
		//Creación de usuarios para las pruebas:
		
		this.gestor1.registrar("Per1", "Hola1", "gg");
		this.gestor1.registrar("Per2", "Hola2", "gg1");
		
		//Caso 1: No hay puntuaciones ni en global ni en personal

		String json1 =	this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] matriz = this.obtenerDatos(json1);
	
		assertTrue(matriz[0].length==0); //no hay puntos
		assertTrue(matriz[1].length==0); //no hay niveles
		assertTrue(matriz[2].length==0); //no hay usuarios
			
		//Caso 2:  Hay una entrada en global pero no de un usuario (Per1)
		
		this.gestor.publicarPuntuacion("Per2", 100, 1);
		
		String json2 = this.gestor.obtenerRankingTodosNivelPersonal("Per1");
		String[][] matriz1 = this.obtenerDatosPriv(json2);
		
		assertTrue(matriz1[0].length==0); //no hay puntuaciones del usuario Per1
		
		//Caso 3: Hay una entrada en global y es de  un usuario que existe (Per2)
		
		String json3 = this.gestor.obtenerRankingTodosNivelPersonal("Per2");
		String[][] matriz2 = this.obtenerDatosPriv(json3);
		
		assertTrue(matriz2[0].length>=1);
		assertTrue(matriz2[0][0].contentEquals("100"));
		
		String json4 =	this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] matriz3 = this.obtenerDatos(json4);
		
		assertTrue(matriz3[2][0].contentEquals("Per2"));
		
		
		//Caso 4: Hay varias entradas en el global y una es de un usuario concreto (Per1)
		
		this.gestor.publicarPuntuacion("Per1", 20, 1);
		
		String json5 =	this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] matriz4 = this.obtenerDatos(json5);
		
		assertTrue(matriz4[2][1].contentEquals("Per1")); //esta en el ranking global
		
		String json6 = this.gestor.obtenerRankingTodosNivelPersonal("Per1");
		String[][] matriz5 = this.obtenerDatosPriv(json6);
		
		assertTrue(matriz5[0][0].contentEquals("20")); //tiene una entrada en su ranking personal
		
		//Caso 5: Hay varias entradas en el global y ninguna de un usuario concreto (Per3)
		
		this.gestor1.registrar("Per3", "Hola", "hh");
		
		String json7 = this.gestor.obtenerRankingTodosNivelPersonal("Per3");
		String[][] matriz6 = this.obtenerDatosPriv(json7);
		
		//No hay puntuaciones para ningun nivel de la Per3
		
		assertTrue(matriz6[0].length==0);  
		assertTrue(matriz6[1].length==0);  
		
		//Caso 6: Hay varias entradas en el global y varias son de un usuario concreto (Per1)
		
		this.gestor.publicarPuntuacion("Per1", 80, 2);
		
		String json9 =	this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] matriz7 = this.obtenerDatos(json9);
		
		assertTrue(matriz7[2][1].contentEquals("Per1"));
		assertTrue(matriz7[2][2].contentEquals("Per1"));
		
		
		String json10 = this.gestor.obtenerRankingTodosNivelPersonal("Per1");
		String[][] matriz8 = this.obtenerDatosPriv(json10);
		
		assertTrue(matriz8[0][0].contentEquals("80"));
		assertTrue(matriz8[0][1].contentEquals("20"));
		
	}
	
	
	//Caso de uso: Ver ranking personal por nivel
	@Test
	public void testObtenerRankingNivelPersonal() {
		
		//Creación de usuarios para las pruebas:
		
		this.gestor1.registrar("Per1", "Hola1", "gg");
		this.gestor1.registrar("Per2", "Hola2", "gg1");
		
		//Caso 1: No hay entradas ni en el global ni en el personal (en ningún nivel)
		String json1 =	this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] matriz = this.obtenerDatos(json1);
	
		assertTrue(matriz[0].length==0); //no hay puntos
		assertTrue(matriz[1].length==0); //no hay niveles
		assertTrue(matriz[2].length==0); // no hay usuarios
		
		ArrayList<Integer> res = this.gestor.obtenerRankingNivelPriv("Per1", 1);
		ArrayList<Integer> res1 = this.gestor.obtenerRankingNivelPriv("Per1", 2);
		ArrayList<Integer> res2 = this.gestor.obtenerRankingNivelPriv("Per1", 3);
		
		//No hay puntuaciones en ningún nivel
		assertTrue(res.size()==0);
		assertTrue(res1.size()==0);
		assertTrue(res2.size()==0);
		
		//Caso 2: Hay entradas en global pero no de este usuario "Per1"
		
		this.gestor.publicarPuntuacion("Per2", 100, 1);
		this.gestor.publicarPuntuacion("Per2", 50, 2);
		
		String json2 =	this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] matriz1 = this.obtenerDatos(json2);
		
		assertTrue(matriz1[0][0].contentEquals("100"));
		assertTrue(matriz1[1][0].contentEquals("1"));
		assertTrue(matriz1[2][0].contentEquals("Per2"));
		
		assertTrue(matriz1[0][1].contentEquals("50"));
		assertTrue(matriz1[1][1].contentEquals("2"));
		assertTrue(matriz1[2][1].contentEquals("Per2"));
		
		ArrayList<Integer> res3 = this.gestor.obtenerRankingNivelPriv("Per1", 1);
		ArrayList<Integer> res4 = this.gestor.obtenerRankingNivelPriv("Per1", 2);
		ArrayList<Integer> res5 = this.gestor.obtenerRankingNivelPriv("Per1", 3);
		
		assertTrue(res3.size()==0);
		assertTrue(res4.size()==0);
		assertTrue(res5.size()==0);
		
		
		//Caso 3: Hay entradas del usuario pero ninguna de un nivel concreto, en este caso el "3"
		
		ArrayList<Integer> res6 = this.gestor.obtenerRankingNivelPriv("Per2", 3);
		assertTrue(res6.size()==0);
		
		//Caso 4: Hay varias entradas del usuario y uno coincide con un nivel en concreto, en este caso el "1"
		ArrayList<Integer> res7 = this.gestor.obtenerRankingNivelPriv("Per2", 1);
		assertTrue(res7.size()>=1);
		assertTrue(res7.get(0)==100);
		
		//Caso 5: Hay varias entradas del usuario y varias coinciden con un nivel en concreto, en este caso el "2"
		this.gestor.publicarPuntuacion("Per2", 10, 2);
		
		
		String json3 =	this.gestor.obtenerRankingTodosNivelesPublico();
		String[][] matriz2 = this.obtenerDatos(json3);
		
		assertTrue(matriz2[0][2].contentEquals("10"));
		assertTrue(matriz2[1][2].contentEquals("2"));
		assertTrue(matriz2[2][2].contentEquals("Per2"));
		
		ArrayList<Integer> res8 = this.gestor.obtenerRankingNivelPriv("Per2", 2);
		assertTrue(res8.size()>=1);
		assertTrue(res8.get(0)==50);
		assertTrue(res8.get(1)==10);
	}
	
	
	//Metodos exclusivos para JUnits
	
	private String[][] obtenerDatos(String json1) {
		
		
		String[] array = json1.split("}");
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
		
		return resultado;
		
	}

	private String[][] obtenerDatosNivel(String json1) {
	
		
		String[] array = json1.split("}");
		String[] puntos = new String [array.length-1];
		String[] nombre = new String [array.length-1];
		String[][] resul = new String[2][array.length-1];
		
		for(int i=0; i != array.length-1;i++) {
			
			int ind=0;
			
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
			ind++;
			
			aux = "";
			
			while(array[i].charAt(ind)!='"') {
				aux = aux + array[i].charAt(ind);
				ind++;
			}
			
			nombre[i]=aux;
		}
		
		resul[0]=puntos;
		resul[1]=nombre;
		
		return resul;
		
		
	}
	
	private String[][] obtenerDatosPriv(String json1) {
		
		String[] array = json1.split("}");
		String[] puntos = new String [array.length-1];
		String[] nivel = new String [array.length-1];
		String[][] resul = new String[2][array.length-1];
	
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
		}
		
		resul[0]=puntos;
		resul[1]=nivel;
		
		return resul;
	}	
	
  

}
