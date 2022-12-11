package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Test;

import com.zetcode.Board;
import com.zetcode.Shape.Tetrominoe;
import com.zetcode.Tetris;

import controlador.GestorPartida;
import controlador.GestorUsuarios;

public class GestorPartidaTest {
	
	private GestorPartida gestor;
	int[][]matrizOrigen;
	int[][]matrizPruebas;
	
	public GestorPartidaTest () {
		this.gestor = GestorPartida.getInstancia();
		this.matrizOrigen=new int[22][10];//nivel 1
		this.matrizPruebas=new int[22][10];//nivel 1
	}
	@Test
	public void testGuardarPartida() {
		this.gestor.getInstancia().resetearBD();
		for(int i=0;i<22;i++) {
			for(int j=0;j<10;j++) {
				matrizOrigen[i][j]=0;
				matrizPruebas[i][j]=0;
			}
		}
		
		matrizOrigen[0][0]=1;
		matrizOrigen[0][1]=1;
		matrizOrigen[1][0]=1;
		matrizOrigen[1][1]=1;
		Timestamp fecha = new Timestamp(System.currentTimeMillis());
		Board b = new Board();
		b.volcarMatriz(matrizOrigen); //Meter los valores en Tetrominoe [] board
		this.gestor.getInstancia().guardarPartida(b.calcularMatriz(), "Prueba1",10,fecha,0, false);
		matrizPruebas=this.gestor.cargarPartida("Prueba1",String.valueOf(fecha),1);
		assertEquals(matrizPruebas[0][1],1);
	}

	@Test
	public void testMostrarPartidas() {
		fail("Not yet implemented");
	}

	@Test
	public void testCargarPartida() {
		this.gestor.getInstancia().resetearBD();
		//Poner a 0 todos los valores de la matriz(Resetear)
		for(int i=0;i!=22;i++) {
			for(int j=0;j!=10;j++) {
				matrizOrigen[i][j]=0;
			}
		}
		matrizOrigen[0][0]=7;
		matrizOrigen[0][1]=7;
		matrizOrigen[1][2]=7;
		//Introducir valores en la matriz 
		Timestamp fecha = new Timestamp(System.currentTimeMillis());
		long fechaString = System.currentTimeMillis();
		this.gestor.getInstancia().guardarPartida(matrizOrigen,"Prueba1", 10,fecha,0, false);
		matrizPruebas=this.gestor.cargarPartida("Prueba1", String.valueOf(fechaString),1);
		assertNotNull(matrizPruebas);
	}

	@Test
	public void testBorrarPartida() {
		this.gestor.getInstancia().resetearBD();
		for(int i=1;i<22;i++) {
			for(int j=0;j<10;j++) {
				matrizOrigen[i][j]=0;
			}
		}
		Timestamp fecha = new Timestamp(System.currentTimeMillis());
		this.gestor.borrarPartida("Prueba1",fecha);
		//¿?
		
	}

	@Test
	public void testTransformarFormato() {
		String fecha1="Dec 6, 2022, 2:27:14 PM";
		String fecha2="Jan 1, 2022, 1:25:29 AM";
		String fecha3="Mar 10, 1999, 12:00:00 AM";
		String fecha4="Jun 1, 2030, 1:00:00 PM";
		assertEquals(this.gestor.transformarFormato(fecha1),"2022-12-06 14:27:14.000000");
		assertEquals(this.gestor.transformarFormato(fecha2),"2022-01-01 01:25:29.000000");
		assertEquals(this.gestor.transformarFormato(fecha3),"1999-03-10 00:00:00.000000");
		assertEquals(this.gestor.transformarFormato(fecha4),"2030-06-01 13:00:00.000000");
	}

}
