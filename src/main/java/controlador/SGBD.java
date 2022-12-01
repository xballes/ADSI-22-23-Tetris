package controlador;
import java.sql.*;


public class SGBD {

	
	

	
	private static SGBD puntero;
	
	
	private SGBD () {
		

		
	}
	
	public ResultSet execSQL(String pStr) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost:3306/adsi", "root", "");
			PreparedStatement ps = conexion.prepareStatement(pStr);
			ResultSet r = ps.executeQuery();
			conexion.close();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void execSQLVoid(String pStr) {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost:3306/adsi", "root", "");
			PreparedStatement ps = conexion.prepareStatement(pStr);
			ps.executeQuery();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static SGBD getInstancia() {
		if (SGBD.puntero == null) {SGBD.puntero = new SGBD();}
		return SGBD.puntero;
		
	}
	
	
}
