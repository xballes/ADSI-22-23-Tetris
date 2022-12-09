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
import com.zetcode.Tetris;

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
		SGBD.getInstancia().execSQLVoid("INSERT INTO partida VALUES ('"+pPartida.getNombreUsuario()+"','"+fecha+"','"+puntuacion+"',1)"); //1 es el nivel(temporal)
		int numcolumnas=pPartida.getBOARD_WIDTH(); //i -> numcolumna
		int numaltura=pPartida.getBOARD_HEIGHT(); //j-> numaltura
		int i =0;
		//int[]alturas=new int[numaltura*numcolumnas];
		while(i<numcolumnas) {
			String sentenciaSQL = "INSERT INTO columna VALUES('"+i+"','"+matriz[0][i]+"','"+matriz[1][i]+"','"+matriz[2][i]+"','"+matriz[3][i]+"','"+matriz[4][i]+"','"+matriz[5][i]+"','"+matriz[6][i]+"','"+matriz[7][i]+"','"+matriz[8][i]+"','"+matriz[9][i]+"','"+matriz[10][i]+"','"+matriz[11][i]+"','"+matriz[12][i]+"','"+matriz[13][i]+"','"+matriz[14][i]+"','"+matriz[15][i]+"','"+matriz[16][i]+"','"+matriz[17][i]+"','"+matriz[18][i]+"','"+matriz[19][i]+"','"+matriz[20][i]+"','"+matriz[21][i]+"','"+fecha+"','"+pPartida.getNombreUsuario()+"')";
			SGBD.getInstancia().execSQLVoid(sentenciaSQL);                                                                                                                                                                                             
			//System.out.println(matriz[0][i]);
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
	private String transformarFormato(String entrada) {
		
		// Pre: MMM DD, YYYY, HH:MM:SS (A/P)M
		// Post: YYY-MM-DD HH:MM:SS.000000

		String año = "";
		String mes = "";
		String dia = "";
		String momento = "";
		
		int i = 0;
		
		while (i < 3) {
			mes = mes + entrada.charAt(i);
			i++;
		}
		
		switch (mes) {
		case "Jan": mes = "01"; break;
		case "Feb": mes = "02"; break;
		case "Mar": mes = "03"; break;
		case "Apr": mes = "04"; break;
		case "May": mes = "05"; break;
		case "Jun": mes = "06"; break;
		case "Jul": mes = "07"; break;
		case "Ago": mes = "08"; break;
		case "Sep": mes = "09"; break;
		case "Oct": mes = "10"; break;
		case "Nov": mes = "11"; break;
		case "Dec": mes = "12"; break;

		}
		
		i++;
		while (entrada.charAt(i) != ',') {
			dia = dia + entrada.charAt(i);
			i++;
		}
		
		if (dia.length() == 1) {dia = "0" + dia;}
		
		i = i+2;
		
		while (entrada.charAt(i) != ',') {
			año = año + entrada.charAt(i);
			i++;
		}
		i++;
		i++;
		
		while (entrada.charAt(i) != ' ') {
			momento = momento + entrada.charAt(i);
			i++;
		}
		
		i++;
		
		String hora = "";
		int j = 0;
		
		while (momento.charAt(j) != ':') {
			hora = hora + momento.charAt(j);
			j++;
		}	
		
		if (entrada.charAt(i) == 'P' && !hora.contentEquals("12") && !hora.contentEquals("0")) {
	
			int h = Integer.parseInt(hora);
			hora =Integer.toString(Integer.parseInt(hora) + 12);
			
			if (h < 10) {
				momento = hora + momento.substring(1, momento.length());

			} else {
				momento = hora + momento.substring(2, momento.length());

			}
			

		} else if (hora.length() == 1) {
			momento = "0" + momento;
		}
		else if (hora.contentEquals("12") && entrada.charAt(i) == 'A') {
			momento = "00" + momento.substring(2, momento.length());
			}
		
		return año+"-"+mes+"-"+dia+" "+momento+".000000";
		
		
	}
	
	public int[][] cargarPartida(String pNombreUsuario,String pFecha,String pPuntos){
		String fechaFormato=transformarFormato(pFecha);
		System.out.println(fechaFormato);
		Timestamp fechaCorrecta=Timestamp.valueOf(fechaFormato);
		int resultado=nivelPartida(pNombreUsuario,pFecha,pPuntos);
		//System.out.println(resultado);
		int numcolumnas;
		if(resultado==1) {
			numcolumnas=10;
		}else if(resultado==2) {
			numcolumnas=12;
		}else {
			numcolumnas=14;
		}
		int [][]matriz= new int [numcolumnas][22]; //22
		for(int x =0;x!=numcolumnas;x++) { 
			String sentenciaSQL2 = "SELECT alt1,alt2,alt3,alt4,alt5,alt6,alt7,alt8,alt9,alt10,alt11,alt12,alt13,alt14,alt15,alt16,alt17,alt18,alt19,alt20,alt21,alt22 FROM columna WHERE(nombreUsuario='"+pNombreUsuario+"' AND fechaPartida='"+fechaCorrecta+"' AND numcolumna='"+x+"')"; //Devuelve cada fila de la matriz
			ResultSet r2 = SGBD.getInstancia().execSQL(sentenciaSQL2);
		try {
			if (r2.next()) {
				for(int j=1;j!=22;j++) {
					matriz[x][j]=r2.getInt(j);		
			}
				r2.close();
		}
	} catch (SQLException e) {}	
		}
		/*for (int f = 0; f != numcolumnas; f++) {
    		for (int c = 1; c != 22; c++) {
    			System.out.print(matriz[f][c]);
    			System.out.print(" ");
    		}
    		System.out.println();
    	}*/
		Tetris t = new Tetris();
		Board b = new Board(t, pNombreUsuario);
		b.volcarMatriz(matriz);
		b.setNumLinesRemoved(Integer.parseInt(pPuntos));
		//falta por poner los puntos de la partida...
		return matriz;
		
		/*
		  - - - - - - - - - - - -
		  1 2 3 4 5 6 7 8 9 10 11 12  ... 22
		  - - - - - - - - - - - -
		  7 7 7 0 0 0 0 0 0 0 0 0 .... (numcolumna=0)
		  0 0 7 0 0 0 0 0 0 0 0 0 .... (numcolumna=1)
		  .
		  .
		  .
		  .............................(numcolumna=10)
		 */
	}
	public int nivelPartida(String pNombreUsuario,String pFecha,String pPuntos){
		String fechaFormato=transformarFormato(pFecha);
		//System.out.println(fechaFormato);
		Timestamp fechaCorrecta=Timestamp.valueOf(fechaFormato);
		String sentenciaSQL = "SELECT nivel FROM partida WHERE(nombreUsuario='"+pNombreUsuario+"' AND puntuacion='"+Integer.parseInt(pPuntos)+"' AND fechaPartida='"+fechaCorrecta+"')";
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
