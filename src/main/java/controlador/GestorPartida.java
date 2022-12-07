package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.google.gson.Gson;
import java.util.ArrayList;


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
		
		Timestamp fecha = pPartida.getFechaSave();
		Timestamp fechaAct = new Timestamp(System.currentTimeMillis());
		String usuario = pPartida.getNombreUsuario();
		int nivel = 0;
		int numeroCols = pPartida.getBOARD_WIDTH();
		
		switch (numeroCols) {
		case 10: nivel = 1; break;
		case 12: nivel = 2; break;
		case 14: nivel = 3; break;
		}
		
		
		
		fechaAct.setNanos(0);
		
		int puntuacion=pPartida.getNumLinesRemoved();
		
		// Mirar si ya se hizo una save antes de esta partida, si es el caso sobrescribir la anterior
		
		if (fecha != null) {
			SGBD.getInstancia().execSQLVoid("DELETE FROM columna WHERE fechaPartida='"+fecha+"' AND nombreUsuario='"+usuario+"'");
			SGBD.getInstancia().execSQLVoid("UPDATE partida SET fechaPartida='"+fechaAct+"' WHERE fechaPartida='"+fecha+"' AND nombreUsuario='"+usuario+"'");
		} else {
			SGBD.getInstancia().execSQLVoid("INSERT INTO partida VALUES ('"+usuario+"','"+fechaAct+"','"+puntuacion+"',"+nivel+")");

		}
		
		// En el caso que se hiciera un tetris en esta partida, mirar si tiene el logro o no para darselo ahora
		
		if (pPartida.getTetrisRealizado()) {
			boolean tieneLogro = false;
			ResultSet r = SGBD.getInstancia().execSQL("SELECT * FROM usuariopremio WHERE nombreUsuario = '"+usuario+"' AND nombrePremio = 'Tetris!'");
			try {
				tieneLogro = r.next();
			} catch (Exception e) {}
			
			if (!tieneLogro) {
				SGBD.getInstancia().execSQLVoid("INSERT INTO usuariopremio VALUES ('"+usuario+"','Tetris!','"+fechaAct+"')");

			}
			
		}
	

		
		
		int numcolumnas=pPartida.getBOARD_WIDTH(); //i -> numcolumna
		int i =0;
		
		while(i<numcolumnas) {
			
			String sentenciaSQL = "INSERT INTO columna VALUES('"+i+"','"+matriz[0][i]+"','"+matriz[1][i]+"','"+matriz[2][i]+"','"+matriz[3][i]+"','"+matriz[4][i]+"','"+matriz[5][i]+"','"+matriz[6][i]+"','"+matriz[7][i]+"','"+matriz[8][i]+"','"+matriz[9][i]+"','"+matriz[10][i]+"','"+matriz[11][i]+"','"+matriz[12][i]+"','"+matriz[13][i]+"','"+matriz[14][i]+"','"+matriz[15][i]+"','"+matriz[16][i]+"','"+matriz[17][i]+"','"+matriz[18][i]+"','"+matriz[19][i]+"','"+matriz[20][i]+"','"+matriz[21][i]+"','"+fechaAct+"','"+usuario+"')";
			SGBD.getInstancia().execSQLVoid(sentenciaSQL);                                                                                                                                                                                             
		//	System.out.println(matriz[0][i]);
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

	
	public int[][] cargarPartida(String pNombreUsuario, String pFecha, String pPuntos){
		String fechaFormato=transformarFormato(pFecha);
		
		Timestamp fechaCorrecta=Timestamp.valueOf(fechaFormato);
		
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
		for(int x =0;x!=numcolumnas;x++) { 
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
			String sentenciaSQL2 = "SELECT alt1,alt2,alt3,alt4,alt5,alt6,alt7,alt8,alt9,alt10,alt11,alt12,alt13,alt14,alt15,alt16,alt17,alt18,alt19,alt20,alt21,alt22 FROM columna WHERE(nombreUsuario='"+pNombreUsuario+"' AND fechaPartida='"+fechaCorrecta+"' AND numcolumna='"+x+"')"; //Devuelve cada fila de la matriz
			ResultSet r2 = SGBD.getInstancia().execSQL(sentenciaSQL2);
			try {
				if (r2.next()) {
					for(int j=0;j!=22;j++) {matriz[x][j]=r2.getInt(j);}			
				
					r2.close();
				}
			} catch (SQLException e) {}	
		}
		
		return matriz;

		/*
		for (int f = 0; f != numcolumnas; f++) {
			
    		for (int c = 0; c != 22; c++) {
    			System.out.print(matriz[f][c]);
    			System.out.print(" ");
    		}
    		System.out.println();
    	} */
	}
	public int nivelPartida(String pNombreUsuario,String pFecha,String pPuntos){
		String fechaFormato=this.transformarFormato(pFecha);
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
	
	
	
	public void borrarPartida(String pUser, Timestamp fecha) {
		SGBD.getInstancia().execSQLVoid("DELETE FROM partida WHERE nombreUsuario = '"+pUser+"' AND fechaPartida = '"+fecha+"'");
		
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
	
	private class PartidaTripleta {
		@SuppressWarnings("unused")
		int puntuacion;
		@SuppressWarnings("unused")
		int nivel;
		@SuppressWarnings("unused")
		Timestamp fecha;
		
		public PartidaTripleta (int pPuntuacion, int pNivel, Timestamp pFecha) {
		puntuacion = pPuntuacion;
		nivel = pNivel;
		fecha = pFecha;
	}
} 
}