package controlador;
import java.sql.*;


public class SGBD {

	
	

	
	private static SGBD puntero;
	
	
	private SGBD () {
		

		
	}
	
	public ResultSet execSQL(String pStr) {
		
		// Pre: String no null, contiene una consulta SELECT
		// Post: El objeto ResultadoSQL correspondiente, en Java se llama ResultSet
		
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost:3306/adsi", "root", "");
			PreparedStatement ps = conexion.prepareStatement(pStr);
			ResultSet r = ps.executeQuery();
			conexion.close();
			return r;
		} catch (SQLException e) {
			System.out.println("Error al conectar con BD. Asegúrate que Apache y MariaDB estén activados en XAMPP y hayas importado el .sql en la BD 'adsi'");
			return null;
		}
		
	}
	
	public void execSQLVoid(String pStr) {
		
		// Pre: String no null, contiene una consulta que NO es SELECT
		// Post: El objeto ResultadoSQL correspondiente, en Java se llama ResultSet
		
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost:3306/adsi", "root", "");
			PreparedStatement ps = conexion.prepareStatement(pStr);
			ps.executeQuery();
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error al conectar con BD. Asegúrate que Apache y MariaDB estén activados en XAMPP y hayas importado el .sql en la BD 'adsi'");
		}
		
	}
	
	
	public static SGBD getInstancia() {
		if (SGBD.puntero == null) {SGBD.puntero = new SGBD();}
		return SGBD.puntero;
		
	}
	
	
}
