package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.sql.Timestamp;

import javax.swing.JPanel;

import org.junit.Test;

import com.zetcode.Board;
import com.zetcode.Shape.Tetrominoe;
import com.zetcode.Tetris;

import controlador.Gestor;
import controlador.GestorPartida;
import controlador.GestorUsuarios;

public class GestorPartidaTest {
	
	private GestorPartida gestor;
	int[][]matrizOrigen1;
	int[][]matrizPruebas1;
	int[][]matrizOrigen2;
	int[][]matrizPruebas2;
	int[][]matrizOrigen3;
	int[][]matrizPruebas3;
	
	public GestorPartidaTest () {
		this.gestor = GestorPartida.getInstancia();
		this.matrizOrigen1=new int[22][10];//nivel 1
		this.matrizPruebas1=new int[22][10];//nivel 1
		this.matrizOrigen2=new int[22][12];//nivel 2
		this.matrizPruebas2=new int[22][12];//nivel 2
		this.matrizOrigen3=new int[22][14];//nivel 3
		this.matrizPruebas3=new int[22][14];//nivel 3
		
	}
	@Test
	public void testGuardarPartida() {
		this.gestor.getInstancia().resetearBD();
		for(int i=0;i<22;i++) {
			for(int j=0;j<10;j++) {
				matrizOrigen1[i][j]=0;
				matrizPruebas1[i][j]=0;
			}
		}
		matrizOrigen1[0][0]=1;
		matrizOrigen1[0][1]=1;
		matrizOrigen1[1][0]=1;
		matrizOrigen1[1][1]=1;
		Timestamp fecha1 = new Timestamp(System.currentTimeMillis());
		this.gestor.getInstancia().guardarPartida(matrizOrigen1,"administrador",10,null,1, false);
		String json=this.gestor.getInstancia().mostrarPartidas("administrador");
		matrizPruebas1=this.gestor.getInstancia().cargarPartida("administrador",this.mostrarFechas("administrador")[0],1);
		assertEquals(matrizPruebas1[0][0], 1);
		//------------------NIVEL 2--------------------------------------------------------------------------------------------
		for(int i=0;i<22;i++) {
			for(int j=0;j<12;j++) {
				matrizOrigen2[i][j]=0;
				matrizPruebas2[i][j]=0;
			}
		}
		matrizOrigen2[0][0]=1;
		matrizOrigen2[0][1]=1;
		matrizOrigen2[1][0]=1;
		matrizOrigen2[1][1]=1;
		Timestamp fecha2 = new Timestamp(System.currentTimeMillis());
		this.gestor.getInstancia().guardarPartida(matrizOrigen2,"administrador",12,null,2, false);
		//String json=this.gestor.getInstancia().mostrarPartidas("administrador");
		matrizPruebas2=this.gestor.getInstancia().cargarPartida("administrador",this.mostrarFechas("administrador")[0],2);
		assertEquals(matrizPruebas2[0][0], 1);
		
		//------------------NIVEL 3--------------------------------------------------------------------------------------------
				for(int i=0;i<22;i++) {
					for(int j=0;j<14;j++) {
						matrizOrigen3[i][j]=0;
						matrizPruebas3[i][j]=0;
					}
				}
				matrizOrigen2[0][0]=1;
				matrizOrigen2[0][1]=1;
				matrizOrigen2[1][0]=1;
				matrizOrigen2[1][1]=1;
				Timestamp fecha3 = new Timestamp(System.currentTimeMillis());
				this.gestor.getInstancia().guardarPartida(matrizOrigen3,"administrador",14,null,3, false);
				//String json=this.gestor.getInstancia().mostrarPartidas("administrador");
				matrizPruebas3=this.gestor.getInstancia().cargarPartida("administrador",this.mostrarFechas("administrador")[0],3);
				assertEquals(matrizPruebas3[0][0], 1);
		
		
		
		
	}
	

	private String[] mostrarFechas (String pUsuario) {
		String[]fechas;
		String partidas=Gestor.getInstancia().mostrarPartidas(pUsuario);
		String[]p=partidas.split("}");
		int i=0;
		fechas=new String[p.length-1];
		String aux;
		i = 0;
		while (i <= p.length-2) {
			int j = 0;
			while (p[i].charAt(j) != ':'){j++;}
			j++;
			aux = "";
			while (p[i].charAt(j) != ',') {
				aux = aux + p[i].charAt(j);
				j++;

			}
			//puntos[i] = aux;

			while (p[i].charAt(j) != ':'){j++;}
			j++;
			
			aux = "";
			while (p[i].charAt(j) != ',') {
				aux = aux + p[i].charAt(j);
				j++;

			}
			//niveles[i] = aux;

			aux = "";

			while (p[i].charAt(j) != ':'){j++;}
			j = j + 2;
			
			while (p[i].charAt(j) != '"') {
				aux = aux + p[i].charAt(j);
				j++;

			}
			
			fechas[i] = aux;

			i++;

		}
			return fechas;
	}
	@Test
	public void testMostrarPartidas() {
		this.gestor.getInstancia().resetearBD();
		for(int i=1;i<22;i++) {
			for(int j=0;j<10;j++) {
				matrizOrigen1[i][j]=0;
			}
		}
		matrizOrigen1[0][0]=7;
		matrizOrigen1[0][1]=7;
		matrizOrigen1[1][2]=7;
		Timestamp fechaAct = new Timestamp(System.currentTimeMillis()); //2022-12-15 18:40:13.655
		this.gestor.getInstancia().guardarPartida(matrizOrigen1,"administrador",10,null,1, false);
		matrizPruebas1=this.gestor.getInstancia().cargarPartida("administrador",this.mostrarFechas("administrador")[0],1);
		String fechaAConvertir = this.mostrarFechas("administrador")[0];
		String fechaTransformada = this.gestor.getInstancia().transformarFormato(fechaAConvertir);
		assertEquals(this.mostrarFechas("administrador").length,1);
		
		

	}

	
	public void testCargarPartida() {
		this.gestor.getInstancia().resetearBD();
		//Poner a 0 todos los valores de la matriz(Resetear)
		for(int i=0;i!=22;i++) {
			for(int j=0;j!=10;j++) {
				matrizOrigen1[i][j]=0;
			}
		}
		matrizOrigen1[0][0]=7;
		matrizOrigen1[0][1]=7;
		matrizOrigen1[1][2]=7;
		//Introducir valores en la matriz 
		this.gestor.getInstancia().guardarPartida(matrizOrigen1,"administrador",10,null,1, false);
		matrizPruebas1=this.gestor.getInstancia().cargarPartida("administrador",this.mostrarFechas("administrador")[0],1);
		assertEquals(matrizPruebas1[0][0],7);
		//---------------------NIVEL 2---------------------------------------------------
		for(int i=1;i<22;i++) {
			for(int j=0;j<12;j++) {
				matrizOrigen2[i][j]=0;
			}
		}
		matrizOrigen2[0][11]=7;
		matrizOrigen2[0][12]=7;
		matrizOrigen2[1][9]=7;
		this.gestor.getInstancia().guardarPartida(matrizOrigen2,"administrador",12,null,2, false);
		matrizPruebas2=this.gestor.getInstancia().cargarPartida("administrador",this.mostrarFechas("administrador")[0],2);
		assertEquals(matrizPruebas2[0][11],7);
		
		//---------------------NIVEL 3---------------------------------------------------
		
		for(int i=1;i<22;i++) {
			for(int j=0;j<14;j++) {
				matrizOrigen3[i][j]=0;
			}
		}
		matrizOrigen3[0][13]=4;
		matrizOrigen3[0][10]=4;
		matrizOrigen3[1][9]=4;
		this.gestor.getInstancia().guardarPartida(matrizOrigen3,"administrador",14,null,3, false);
		matrizPruebas3=this.gestor.getInstancia().cargarPartida("administrador",this.mostrarFechas("administrador")[0],3);
		assertEquals(matrizPruebas3[0][13],4);		
	}

	@Test
	public void testBorrarPartida() {
		this.gestor.getInstancia().resetearBD();
		for(int i=1;i<22;i++) {
			for(int j=0;j<10;j++) {
				matrizOrigen1[i][j]=0;
			}
		}
		matrizOrigen1[0][0]=7;
		matrizOrigen1[0][1]=7;
		matrizOrigen1[1][2]=7;
		Timestamp fechaAct = new Timestamp(System.currentTimeMillis()); //2022-12-15 18:40:13.655
		this.gestor.getInstancia().guardarPartida(matrizOrigen1,"administrador",10,null,1, false);
		matrizPruebas1=this.gestor.getInstancia().cargarPartida("administrador",this.mostrarFechas("administrador")[0],1);
		String fechaAConvertir = this.mostrarFechas("administrador")[0];
		String fechaTransformada = this.gestor.getInstancia().transformarFormato(fechaAConvertir);
		this.gestor.getInstancia().borrarPartida("administrador",Timestamp.valueOf(fechaTransformada));
		assertEquals(this.mostrarFechas("administrador").length,0);
		
		//-----------------------------NIVEL 2-------------------------------------------------------------------------------
		for(int i=1;i<22;i++) {
			for(int j=0;j<12;j++) {
				matrizOrigen2[i][j]=0;
			}
		}
		matrizOrigen2[0][0]=7;
		matrizOrigen2[0][1]=7;
		matrizOrigen2[1][2]=7;
		Timestamp fechaAct2 = new Timestamp(System.currentTimeMillis()); //2022-12-15 18:40:13.655
		this.gestor.getInstancia().guardarPartida(matrizOrigen2,"administrador",12,null,2, false);
		matrizPruebas2=this.gestor.getInstancia().cargarPartida("administrador",this.mostrarFechas("administrador")[0],2);
		String fechaAConvertir2 = this.mostrarFechas("administrador")[0];
		String fechaTransformada2 = this.gestor.getInstancia().transformarFormato(fechaAConvertir2);
		this.gestor.getInstancia().borrarPartida("administrador",Timestamp.valueOf(fechaTransformada2));
		assertEquals(this.mostrarFechas("administrador").length,0);
		
		//-----------------------------NIVEL 3-------------------------------------------------------------------------------
				for(int i=1;i<22;i++) {
					for(int j=0;j<12;j++) {
						matrizOrigen3[i][j]=0;
					}
				}
				matrizOrigen3[0][0]=7;
				matrizOrigen3[0][1]=7;
				matrizOrigen3[1][2]=7;
				Timestamp fechaAct3 = new Timestamp(System.currentTimeMillis()); //2022-12-15 18:40:13.655
				this.gestor.getInstancia().guardarPartida(matrizOrigen2,"administrador",12,null,2, false);
				matrizPruebas3=this.gestor.getInstancia().cargarPartida("administrador",this.mostrarFechas("administrador")[0],2);
				String fechaAConvertir3 = this.mostrarFechas("administrador")[0];
				String fechaTransformada3 = this.gestor.getInstancia().transformarFormato(fechaAConvertir3);
				this.gestor.getInstancia().borrarPartida("administrador",Timestamp.valueOf(fechaTransformada3));
				assertEquals(this.mostrarFechas("administrador").length,0);
		
	}

	@Test
	public void testTransformarFormato() {
		String fecha1="Dec 6, 2022, 2:27:14 PM";
		String fecha2="Jan 1, 2022, 1:25:29 AM";
		String fecha3="Mar 10, 1999, 12:00:00 AM";
		String fecha4="Jun 1, 2030, 1:00:00 PM";
		String fecha5="Dec 15, 2022, 6:50:30 PM";
		assertEquals(this.gestor.transformarFormato(fecha1),"2022-12-06 14:27:14.000000");
		assertEquals(this.gestor.transformarFormato(fecha2),"2022-01-01 01:25:29.000000");
		assertEquals(this.gestor.transformarFormato(fecha3),"1999-03-10 00:00:00.000000");
		assertEquals(this.gestor.transformarFormato(fecha4),"2030-06-01 13:00:00.000000");
		assertEquals(this.gestor.transformarFormato(fecha5),"2022-12-15 18:50:30.000000");
	}

}
