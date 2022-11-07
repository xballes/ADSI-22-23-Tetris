package testDB;


import java.sql.*;

public class Main {

	/* PARA OPERAR CON DATABASES SE DEBE TENER UNA DB YA ALOJADA EN EL ORDENADOR ACTIVA (SIMILAR A LO QUE HACE DOCKER EN SGSSI)
	 * ASUMO QUE TIENES XAMPP INSTALADO EN EL ORDENADOR (SE USO EN LA ASIGNATURA DE BASES DE DATOS)
	 * PARA ALOJAR LA DB, ACTIVA LA PESTAÑA DE APACHE Y DE MARIADB
	 * TAMBIEN IMPORTAR EL ARCHIVO JAR DE MARIADB, QUE SE HARIA DE LA MISMA FORMA QUE CON EL DE JSON (MIRAR APARTADO DE JSON SI NO SABES)
	 
	  
	  
	  
	  
	  
	 */
	
	
	public static void main (String args[]) {
		
		try {
			
			
			
			// Abrir la conexion root sin contraseña, se aloja en el puerto 3306 en XAMPP por defecto. La ultima string tras el "/" indica
			// con que DB estamos trabajando.
			
			Connection conexion = DriverManager.getConnection("jdbc:mariadb://localhost:3306/BUQUES", "root", "");
			
			// Para hacer consultas, usar conexion.prepareStatement(CONSULTASQL)
			
			
			PreparedStatement ps = conexion.prepareStatement("SELECT * FROM BUQUE;");
			
			// El resultado obtenido se almacena en ResultSet, tras ejecutarlo
			
			ResultSet r = ps.executeQuery();
			
			// ResultSet usa literalmente la misma logica con los metodos que la forma generica que se usaba con
			// ResultadoSQL en clase, de todas formas para refrescar:
			
			// next(): bool --> Apunta a la siguiente fila (por defecto en la fila 0, que no apunta a nada, similar a un iterador). True si hay fila tras el next y false si no la hay
			// getInt(string): int --> Obtener el int del nombre de la col string
			// getString(string): String --> Obtener el string de la col string
			// Ademas, permite ResultSet trabajar con el index de la columna en su lugar si se le da un int, la primera columna es 1.
			
			r.next();
			int a = r.getInt("tipo");
			
			System.out.println(a);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	
	}
}
