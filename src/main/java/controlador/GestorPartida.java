package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.google.gson.Gson;
import java.util.ArrayList;


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
	
	public void guardarPartida(int[][] matriz, String usuario, int numCols, Timestamp fecha, int puntuacion, boolean tetris) {
		
		/* Pre: matriz --> Matriz de ints 22x(10,12,14. dificultad respectiva) con valores 0-7 representando que hay en cada celda
		 *      usuario --> nombre del usuario jugando, está en BD
		 *      fecha --> Fecha del ultimo guardado de la partida cargada, NULL si se quiere guardar nueva partida. Si existe, sus nanos seran 0.
		 *      puntu --> Puntuacion obtenida hasta ahota
		 *      tetris --> ¿Performó al menos un tetris durante el tiempo que ha estado jugando desde que cargo/inicio partida?
		  
		  
		  
		 */
		
		
		
		Timestamp fechaAct = new Timestamp(System.currentTimeMillis());
		int nivel = 0;
		
		// Calcular el nivel, deducible por número de columnas de la matriz (más columnas --> más dificil)
		
		switch (numCols) {
		case 10: nivel = 1; break;
		case 12: nivel = 2; break;
		case 14: nivel = 3; break;
		}
		
		
		
		fechaAct.setNanos(0); // Para encontrar la partida despues con un posible SELECT en el futuro, los nanos siempre dejarlas a 0
		
		
		// Mirar si ya se hizo una save antes de esta partida, si es el caso sobrescribir la anterior
		
		if (fecha != null) {
			SGBD.getInstancia().execSQLVoid("DELETE FROM columna WHERE fechaPartida='"+fecha+"' AND nombreUsuario='"+usuario+"'"); // Borrar la matriz anterior
			SGBD.getInstancia().execSQLVoid("UPDATE partida SET fechaPartida='"+fechaAct+"' WHERE fechaPartida='"+fecha+"' AND nombreUsuario='"+usuario+"'"); // Actualizar la fecha de ultimo guardado
		} else {
			SGBD.getInstancia().execSQLVoid("INSERT INTO partida VALUES ('"+usuario+"','"+fechaAct+"','"+puntuacion+"',"+nivel+")"); // Crear la save porque no existia

		}
		
		// En el caso que se hiciera un tetris en esta partida, mirar si tiene el logro o no para darselo ahora, ya que las saves no almacenan este valor
		
		if (tetris && !GestorPremios.getInstancia().tieneElPremio(usuario, 3)) {
			GestorPremios.getInstancia().darPremio(usuario, 3, fechaAct);

		}
	

		
		
		int i = 0;
		
		// Guardar el estado de la matriz en BD, cada relacion "columna" es una columna del juego original. Todas se relacionan con la save creada
		
		while(i  <numCols) {
			
			String sentenciaSQL = "INSERT INTO columna VALUES('"+i+"','"+matriz[0][i]+"','"+matriz[1][i]+"','"+matriz[2][i]+"','"+matriz[3][i]+"','"+matriz[4][i]+"','"+matriz[5][i]+"','"+matriz[6][i]+"','"+matriz[7][i]+"','"+matriz[8][i]+"','"+matriz[9][i]+"','"+matriz[10][i]+"','"+matriz[11][i]+"','"+matriz[12][i]+"','"+matriz[13][i]+"','"+matriz[14][i]+"','"+matriz[15][i]+"','"+matriz[16][i]+"','"+matriz[17][i]+"','"+matriz[18][i]+"','"+matriz[19][i]+"','"+matriz[20][i]+"','"+matriz[21][i]+"','"+fechaAct+"','"+usuario+"')";
			SGBD.getInstancia().execSQLVoid(sentenciaSQL);                                                                                                                                                                                             
			i++;	
		}	
	}
	
	public String mostrarPartidas(String pNombreUsuario) {
		
		/* Pre: Usuario en BD
		 * Post: String en formato JSON con la información de las partidas sin acabar del jugador, esta es:
		        puntuacion de la partida guardada, nivel de esta y la fecha de ultimo guardado
		  
		  
		  
		 */
		
		
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

	
	public int[][] cargarPartida(String pNombreUsuario, String pFecha, int pNivel){
		
		// Pre: User y fecha apuntan a una save en BD. pNivel es el valor del nivel de esa save
		// Post: Se ha devuelto la matriz de ints para poder cargarla en Board
		
		
		String fechaFormato=transformarFormato(pFecha); // Timestamp no puede ser generado por el formato de la String que tiene el JSON, hay que convertirlo al formato que acepta
		
		Timestamp fechaCorrecta=Timestamp.valueOf(fechaFormato); // Crear el timestamp
		
		int numcolumnas; // Deducir el numero de columnas basado en el nivel
		if(pNivel == 1) {
			numcolumnas = 10;
		}else if(pNivel == 2) {
			numcolumnas = 12;
		}else {
			numcolumnas = 14;
		}
		int [][]matriz= new int [22][numcolumnas]; // r.getFetchSize() o 22
		
		// Obtener la matriz de BD
		
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
			String sentenciaSQL2 = "SELECT alt1,alt2,alt3,alt4,alt5,alt6,alt7,alt8,alt9,alt10,alt11,alt12,alt13,alt14,alt15,alt16,alt17,alt18,alt19,alt20,alt21,alt22 FROM columna WHERE(nombreUsuario='"+pNombreUsuario+"' AND fechaPartida='"+fechaCorrecta+"' AND numColumna='"+x+"')"; //Devuelve cada fila de la matriz
			ResultSet r2 = SGBD.getInstancia().execSQL(sentenciaSQL2);
			try {
				if (r2.next()) {
					for(int j=0;j!=22;j++) {matriz[j][x]=r2.getInt(j+1);}			
				
				}
				r2.close();

			} catch (SQLException e) {}	
		}

		
		return matriz;


	}

	
	
	
	public void borrarPartida(String pUser, Timestamp fecha) {
		SGBD.getInstancia().execSQLVoid("DELETE FROM partida WHERE nombreUsuario = '"+pUser+"' AND fechaPartida = '"+fecha+"'");
		
	}
	

	
	public String transformarFormato(String entrada) {
		
		// Pre: MMM DD, YYYY, HH:MM:SS (A/P)M
		// Post: YYY-MM-DD HH:MM:SS.000000
		
		
		// El método sirve para que basado en el formato de fecha dado por JSON se pueda generar despues en un objeto Timestamp

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
	
	// Tuplas para poder almacenar varios valores a la vez y convertirlos a JSON
	
	// @SuppressWarnings --> Los atributos flageados como unused se usan para fabricar el JSON, pero el IDE no es capaz de deducirlo
	
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