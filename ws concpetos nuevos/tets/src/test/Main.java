package test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Main {

	/* LO RELATIVO A ARCHIVOS JSON, SE REQUIEREN LIBRERIAS EXTRA QUE ECLIPSE NO INCLUYE POR DEFECTO, PARA ELLO:
	 *  
	 * IMPORTAR EL JAR GSON-2.2.2.JAR AL PROYECTO PARA QUE PUEDA OPERAR CON LAS LIBRERIAS, PARA ESTO:
	 *       HAZ CLICK DERECHO EN LA CARPETA DEL PROYECTO EN PACKAGE EXPLORER (EN ESTE CASO "TETS") --> PROPERTIES
	 *       EN EL MENU ELEGIR JAVA BUILD PATH, MARCAR COLUMNA LIBRARIES, CLICKAR EN CLASS PATH --> ADD EXTERNAL JARS...
	 *       SELECIONAR EL JAR DE GSON AQUI
	 *       AHORA EN LAS CLASES A USAR LA LIBRERIA AÑADIR LOS SIGUIENTES IMPORTS:
	 *       import com.google.gson.Gson;
             import com.google.gson.reflect.TypeToken;
	 * 
	 *  YA PUEDES OPERAR CON JSON, USANDO LAS OPERACIONES DETALLADAS ABAJO
	 */
	
	public static void main (String arg[]) {
		
		// Crear convertidor JSON (EL JSON EN SI SE GUARDA COMO STRING)
		
		Gson json = new Gson();
		
		
		// OBJETO INDIVIDUAL
		
			Objeto a = new Objeto();
		
			// OBJETO --> JSON
		
			String re = json.toJson(a);
		
			// JSON --> OBJETO
		
			a = json.fromJson(re, Objeto.class);
	
		

		
		// CON LISTAS
		
			// LISTA --> JSON
		
			ArrayList<Objeto> al = new ArrayList<Objeto>();
			al.add(new Objeto());
			al.add(new Objeto());
			al.add(new Objeto());
			String res = json.toJson(al);
		
			// JSON --> LISTA
		
			al = json.fromJson(res, new TypeToken<ArrayList<Objeto>>(){}.getType());
	

	}
	

}

