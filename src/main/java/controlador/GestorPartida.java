package controlador;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import org.mariadb.jdbc.client.result.ResultSetMetaData;

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
		Timestamp fecha=new Timestamp(System.currentTimeMillis());
		fecha.setNanos(0);
		int puntuacion=pPartida.getNumLinesRemoved();
		String nombreUsuario=pPartida.getNombreUsuario();
		SGBD.getInstancia().execSQLVoid("INSERT INTO partida VALUES ('"+pPartida.getNombreUsuario()+"','"+fecha+"','"+puntuacion+"',1)");
		int numcolumnas=pPartida.getBOARD_WIDTH(); //i -> numcolumna
		int numaltura=pPartida.getBOARD_HEIGHT(); //j-> numaltura
		int i =0;
		//int[]alturas=new int[numaltura*numcolumnas];
		while(i<numcolumnas) {
			String sentenciaSQL = "INSERT INTO columna VALUES('"+i+"','"+matriz[0][i]+"','"+matriz[1][i]+"','"+matriz[2][i]+"','"+matriz[3][i]+"','"+matriz[4][i]+"','"+matriz[5][i]+"','"+matriz[6][i]+"','"+matriz[7][i]+"','"+matriz[8][i]+"','"+matriz[9][i]+"','"+matriz[10][i]+"','"+matriz[11][i]+"','"+matriz[12][i]+"','"+matriz[13][i]+"','"+matriz[14][i]+"','"+matriz[15][i]+"','"+matriz[16][i]+"','"+matriz[17][i]+"','"+matriz[18][i]+"','"+matriz[19][i]+"','"+matriz[20][i]+"','"+matriz[21][i]+"','"+fecha+"','"+pPartida.getNombreUsuario()+"')";
			SGBD.getInstancia().execSQLVoid(sentenciaSQL);                                                                                                                                                                                             
			System.out.println(matriz[0][i]);
			i++;	
		}	
	}
	
	public String mostrarPartidas(String pNombreUsuario) {
		Gson json1 = new Gson();
		String sentenciaSQL = "SELECT * FROM partida WHERE(nombreUsuario='"+pNombreUsuario+"')";
		ResultSet r = SGBD.getInstancia().execSQL(sentenciaSQL);
		boolean val;
		ArrayList<PartidaTripleta> tripleta = new ArrayList<PartidaTripleta>();
		try {
			val=r.next();
			while(val) {
				int puntuacion=r.getInt("puntuacion");
				int nivel=r.getInt("nivel");
				Timestamp fecha=r.getTimestamp("fechaPartida");
				PartidaTripleta partida=new PartidaTripleta(puntuacion,nivel,fecha);
				tripleta.add(partida);
				val=r.next();
			}
			r.close();
			
			
		}catch(SQLException e) {}
		
		String json = json1.toJson(tripleta);
		return json;
	}
	
	
	public int[][] cargarPartida(String pNombreUsuario,String pFecha,String pPuntos){
		/*BOARD
		private final int BOARD_WIDTH = 10;   // Cuadrados de largo
    	private final int BOARD_HEIGHT = 22;  // Cuadrados de alto
   
		int[][] matriz=new int[BOARD_HEIGHT][BOARD_WIDTH]; 
		 */
		//System.out.println(pFecha); //Dec 6, 2022, 4:37:34 PM -> yyyy-mm-dd hh:mm:ss[.fffffffff]
		String fechaformato;
		String hora="";
		String minuto="";
		String segundos="";
		String anio="";
		String dia="";
		String mes="";
		int i=0;
		while(i<pFecha.length()) {
			while(i<3) { //MES
				mes=mes+pFecha.charAt(i);
				System.out.println("Bucle1");
				i++;
			}
			System.out.println(mes);
			switch(mes) {
			case "Jan":mes="01";
			break;
			case "Feb":mes="02";
			break;
			case "Mar":mes="03";
			break;
			case "Apr":mes="04";
			break;
			case "May":mes="05";
			break;
			case "Jun":mes="06";
			break;
			case "Jul":mes="07";
			break;
			case "Aug":mes="08";
			break;
			case "Sep":mes="09";
			break;
			case "Oct":mes="10";
			break;
			case "Nov":mes="11";
			break;
			case "Dec":mes="12";
			break;	
			
			}
			//Dec 6, 2022, 4:37:34 PM -> yyyy-mm-dd hh:mm:ss[.fffffffff]
			while(pFecha.charAt(i)!=' ') { //AVANZAR
			i++;	
			}
			i++;
			while(pFecha.charAt(i)!=',') { //DIA
				dia=dia+pFecha.charAt(i);
				System.out.println("Bucle2");
				System.out.println(dia);
				i++;
			}
			i++;
			if(dia.length()==1) {
				dia="0"+dia;
			}
			while(pFecha.charAt(i)==' ' || pFecha.charAt(i)==',') { //AVANZAR
				System.out.println("Bucle3");
				
				i++;	
				}
				i++;
			while(pFecha.charAt(i)!=',') { //AÑO
					System.out.println("Bucle4");
					anio=anio+pFecha.charAt(i);
					System.out.println(anio);
					i++;
					}	
			i++;
			while(pFecha.charAt(i)==' ' || pFecha.charAt(i)==',') { //AVANZAR
				System.out.println("Bucle5");
				i++;	
				}
				i++;
			while(pFecha.charAt(i)!=':') { //HORA
				System.out.println("Bucle6");
				System.out.println(hora);
				hora=hora+pFecha.charAt(i);
				i++;
			}
			i++;
			while(pFecha.charAt(i)!=':') { //MINUTOS
				System.out.println("Bucle7");
				System.out.println(minuto);
				minuto=minuto+pFecha.charAt(i);
				i++;
			}
			i++;
			if(minuto.length()==1) {
				minuto="0"+minuto;
			}
			while(pFecha.charAt(i)!=' ') { //SEGUNDOS
				System.out.println("Bucle8");
				System.out.println(segundos);
				segundos=segundos+pFecha.charAt(i);
				i++;
			}
			if(segundos.length()==1) {
				segundos="0"+segundos;
			}
			while(pFecha.charAt(i)!=' ') {
				System.out.println("Bucle9");
				i++;
			}
			i++;
			if(pFecha.charAt(i)=='P') {
				hora=hora+12;
			}
		System.out.println(mes);
		System.out.println(dia);	
		System.out.println(hora);
		System.out.println(minuto);
		System.out.println(segundos);
		}
		Timestamp fechaFormato= Timestamp.valueOf(pFecha);
		String sentenciaSQL = "SELECT alt1,alt2,alt3,alt4,alt5,alt6,alt7,alt8,alt9,alt10,alt11,alt12,alt13,alt14,alt15,alt16,alt17,alt18,alt19,alt20,alt21,alt22 FROM columna WHERE(nombreUsuario='"+pNombreUsuario+"' AND fechaPartida='"+Timestamp.valueOf(pFecha)+"')";
		ResultSet r = SGBD.getInstancia().execSQL(sentenciaSQL);
		int resultado=nivelPartida(pNombreUsuario,pFecha,pPuntos);
		int numcolumnas;
		if(resultado==1) {
			numcolumnas=10;
		}else if(resultado==2) {
			numcolumnas=12;
		}else {
			numcolumnas=14;
		}
		int [][]matriz= new int [numcolumnas][22]; // r.getFetchSize() o 22
		for(int x =0;i<numcolumnas;x++) {
			String sentenciaSQL2 = "SELECT alt1,alt2,alt3,alt4,alt5,alt6,alt7,alt8,alt9,alt10,alt11,alt12,alt13,alt14,alt15,alt16,alt17,alt18,alt19,alt20,alt21,alt22 FROM columna WHERE(nombreUsuario='"+pNombreUsuario+"' AND fechaPartida='"+pFecha+"' AND numcolumna='"+x+"')"; //Devuelve cada fila de la matriz
			ResultSet r2 = SGBD.getInstancia().execSQL(sentenciaSQL2);
		try {
			if (r2.next()) {
				for(int j=0;j<22;j++) {
				matriz[x][j]=r2.getInt(j);
				} 
				r.close();
		}
		} catch (SQLException e) {}	
		}
		return matriz;
	}
	
	public int nivelPartida(String pNombreUsuario,String pFecha,String pPuntos){
		String sentenciaSQL = "SELECT nivel FROM partida WHERE(nombreUsuario='"+pNombreUsuario+"' AND puntuacion='"+Integer.parseInt(pPuntos)+"' AND fechaPartida='"+Timestamp.valueOf(pFecha)+"')";
		ResultSet r = SGBD.getInstancia().execSQL(sentenciaSQL);
		int resultado;
		try {
			if (r.next()) {
				resultado = r.getInt(1);
				r.close();
				return resultado;
			} else {return -1;}
		} catch (SQLException e) {return -1;}
	}	
	
		private class PartidaTripleta {
		int puntuacion;
		int nivel;
		Timestamp fecha=new Timestamp(System.currentTimeMillis());
	public PartidaTripleta (int pPuntuacion, int pNivel, Timestamp pFecha) {
		puntuacion = pPuntuacion;
		nivel = pNivel;
		fecha = pFecha;
		}
}
}
