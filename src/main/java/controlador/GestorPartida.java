package controlador;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.time.LocalDate;

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
		String fecha=now.toString();
		System.out.println(fecha);
		int puntuacion=pPartida.getNumLinesRemoved();
		String nombreUsuario=pPartida.getNombreUsuario();
		SGBD.getInstancia().execSQLVoid("INSERT INTO partida_guardada VALUES ('"+pPartida.getNombreUsuario()+"','"+fecha+"','"+puntuacion+"',1)");
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
	
	public boolean cargarPartida(String pNombreUsuario){
		String sentenciaSQL = "SELECT * FROM partida_guardada WHERE(nombreUsuario='"+pNombreUsuario+"')";
		ResultSet r = SGBD.getInstancia().execSQL(sentenciaSQL);
		boolean val;
		try {
			val=r.next();
			r.close();
			
		}catch(SQLException e) {return false;}
		
		if(val=true) { //si existe una partida, conseguimos los valores del tablero
			String sentenciaSQL2 = "SELECT alt1,alt2,alt3,alt4,alt5,alt6,alt7,alt8,alt9,alt10,alt11,alt12,alt13,alt14,alt15,alt16,alt17,alt18,alt19,alt20,alt21,alt22 FROM columna WHERE(nombreUsuario='"+pNombreUsuario+"')";
			ResultSet r2 = SGBD.getInstancia().execSQL(sentenciaSQL2);
			ResultSetMetaData rsmd;
			boolean val2;
			try {
				rsmd = (ResultSetMetaData) r2.getMetaData();
				int numcolumnas=rsmd.getColumnCount();//22
				while(r2.next()) {
					for (int i = 1; i <= numcolumnas; i++) {
						String valorColumna = r2.getString(i);
				           System.out.print("  "+valorColumna+"  ");
				       }
				       System.out.println("");
					}
				r2.close();
			} catch (SQLException e1) {return false;}
				return true;
		}else {
		return false;
		}
	}	
}
