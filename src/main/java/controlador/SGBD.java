package controlador;
import java.io.File;
import java.sql.*;


public class SGBD {

	
	

	
	private static SGBD puntero;
	private Connection conexion;
	
	private SGBD () {
		
		// Mirar si la BD está generada ya en el PC actual
		
		
		try {
			Class.forName("org.h2.Driver");
			this.conexion = DriverManager.getConnection("jdbc:h2:"+System.getProperty("user.dir")+File.separator+"base"+File.separator+"adsi", "usu", "");

			
			
			PreparedStatement ps = conexion.prepareStatement("SHOW TABLES"); // Mirar si tablas existen
			ResultSet r = ps.executeQuery();
			r.next();
			


			
			if (!r.next()) { // SI no existen, se crean

				ps = conexion.prepareStatement("CREATE TABLE USUARIO(\r\n"
						+ "NOMBRE varchar(30),\r\n"
						+ "CONTRASEÑA varchar(30),\r\n"
						+ "EMAIL varchar(30),\r\n"
						+ "SETLADRILLO int,\r\n"
						+ "SETCOLOR int,\r\n"
						+ "SETSONIDO int,\r\n"
						+ "VICTORIAS int,\r\n"
						+ "PRIMARY KEY(NOMBRE)\r\n"
						+ "\r\n"
						+ ")");
				ps.executeUpdate();
				
				ps = conexion.prepareStatement("CREATE TABLE PREMIO(\r\n"
						+ "NOMBRE varchar(30),\r\n"
						+ "DESCRIPCION varchar(30),\r\n"
						+ "PRIMARY KEY(NOMBRE)\r\n"
						+ "\r\n"
						+ ")");
				
				ps.executeUpdate();
				
				ps = conexion.prepareStatement("CREATE TABLE USUARIOPREMIO(\r\n"
						+ "NOMBREUSUARIO varchar(30),\r\n"
						+ "NOMBREPREMIO varchar(30),\r\n"
						+ "FECHAOBTENCION timestamp(6),\r\n"
						+ "PRIMARY KEY(NOMBREUSUARIO, NOMBREPREMIO),\r\n"
						+ "FOREIGN KEY(NOMBREUSUARIO) REFERENCES USUARIO(NOMBRE) ON UPDATE CASCADE ON DELETE CASCADE,\r\n"
						+ "FOREIGN KEY(NOMBREPREMIO) REFERENCES PREMIO(NOMBRE) ON UPDATE CASCADE ON DELETE CASCADE\r\n"
						+ ")\r\n"
						+ "");
				
				ps.executeUpdate();
				
				ps = conexion.prepareStatement("CREATE TABLE PUNTUACION(\r\n"
						+ "NIVEL int,\r\n"
						+ "PUNTOSACTUALES int,\r\n"
						+ "ID int AUTO_INCREMENT,\r\n"
						+ "USUARIO varchar(30),\r\n"
						+ "PRIMARY KEY(ID),\r\n"
						+ "FOREIGN KEY(USUARIO) REFERENCES USUARIO(NOMBRE) ON UPDATE CASCADE ON DELETE CASCADE\r\n"
						+ ")\r\n"
						+ "");
				
				ps.executeUpdate();
				
				ps = conexion.prepareStatement("CREATE TABLE PARTIDA(\r\n"
						+ "NOMBREUSUARIO varchar(30),\r\n"
						+ "FECHAPARTIDA timestamp(6),\r\n"
						+ "PUNTUACION int,\r\n"
						+ "NIVEL int,\r\n"
						+ "PRIMARY KEY(NOMBREUSUARIO, FECHAPARTIDA),\r\n"
						+ "FOREIGN KEY(NOMBREUSUARIO) REFERENCES USUARIO(NOMBRE) ON DELETE CASCADE ON UPDATE CASCADE\r\n"
						+ ")\r\n"
						+ "");
				
				ps.executeUpdate();
				
				ps = conexion.prepareStatement("CREATE TABLE COLUMNA(\r\n"
						+ "NUMCOLUMNA int,\r\n"
						+ "ALT1 int,\r\n"
						+ "ALT2 int,\r\n"
						+ "ALT3 int,\r\n"
						+ "ALT4 int,\r\n"
						+ "ALT5 int,\r\n"
						+ "ALT6 int,\r\n"
						+ "ALT7 int,\r\n"
						+ "ALT8 int,\r\n"
						+ "ALT9 int,\r\n"
						+ "ALT10 int,\r\n"
						+ "ALT11 int,\r\n"
						+ "ALT12 int,\r\n"
						+ "ALT13 int,\r\n"
						+ "ALT14 int,\r\n"
						+ "ALT15 int,\r\n"
						+ "ALT16 int,\r\n"
						+ "ALT17 int,\r\n"
						+ "ALT18 int,\r\n"
						+ "ALT19 int,\r\n"
						+ "ALT20 int,\r\n"
						+ "ALT21 int,\r\n"
						+ "ALT22 int,\r\n"
						+ "FECHAPARTIDA timestamp(6),\r\n"
						+ "NOMBREUSUARIO varchar(20),\r\n"
						+ "PRIMARY KEY(NUMCOLUMNA, FECHAPARTIDA, NOMBREUSUARIO),\r\n"
						+ "FOREIGN KEY(NOMBREUSUARIO, FECHAPARTIDA) REFERENCES PARTIDA(NOMBREUSUARIO, FECHAPARTIDA)\r\n"
						+ ")");
				
				ps.executeUpdate();
				
				ps = conexion.prepareStatement("INSERT INTO USUARIO VALUES('administrador', '123456', 'email', 0,0,0,0)");
				ps.executeUpdate();
				ps = conexion.prepareStatement("INSERT INTO PREMIO VALUES('Gana 1 partida', 'Gana 1 partida')");
				ps.executeUpdate();
				ps = conexion.prepareStatement("INSERT INTO PREMIO VALUES('Gana 10 partidas', 'Gana 10 partidas')");
				ps.executeUpdate();
				ps = conexion.prepareStatement("INSERT INTO PREMIO VALUES('Gana 25 partidas', 'Gana 25 partidas')");
				ps.executeUpdate();
				ps = conexion.prepareStatement("INSERT INTO PREMIO VALUES('Tetris!', 'Limpia 4 filas con una pieza')");
				ps.executeUpdate();
				
			}


		} catch (Exception  e) {
			e.printStackTrace();
		}
		
	}
	
	public ResultSet execSQL(String pStr) {
		
		// Pre: String no null, contiene una consulta SELECT
		// Post: El objeto ResultadoSQL correspondiente, en Java se llama ResultSet
		
		try {
			Class.forName("org.h2.Driver");
			conexion.close();
			conexion = DriverManager.getConnection("jdbc:h2:"+System.getProperty("user.dir")+File.separator+"base"+File.separator+"adsi", "usu", "");
			PreparedStatement ps = conexion.prepareStatement(pStr);
			ResultSet r = ps.executeQuery();
		//	conexion.close();
			return r;
		} catch (Exception  e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void execSQLVoid(String pStr) {
		
		// Pre: String no null, contiene una consulta que NO es SELECT
		// Post: El objeto ResultadoSQL correspondiente, en Java se llama ResultSet
		
		try {
			Class.forName("org.h2.Driver");
			conexion.close();
			conexion = DriverManager.getConnection("jdbc:h2:"+System.getProperty("user.dir")+File.separator+"base"+File.separator+"adsi", "usu", "");
			PreparedStatement ps = conexion.prepareStatement(pStr);
			ps.executeUpdate();
			conexion.close();
		} catch (Exception e) {
			System.out.println("Error al conectar con BD. Asegúrate que Apache y MariaDB estén activados en XAMPP y hayas importado el .sql en la BD 'adsi'");
		}
		
	}
	
	
	public static SGBD getInstancia() {
		if (SGBD.puntero == null) {SGBD.puntero = new SGBD();}
		return SGBD.puntero;
		
	}
	
	
}
