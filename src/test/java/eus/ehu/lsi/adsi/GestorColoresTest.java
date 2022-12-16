package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;

import org.junit.Test;

import controlador.Gestor;
import controlador.GestorColores;
import controlador.GestorUsuarios;

public class GestorColoresTest {
	private GestorColores gestor;
	private GestorUsuarios gestor1;
	
	public GestorColoresTest() {
		this.gestor=GestorColores.getInstancia();
		this.gestor1=GestorUsuarios.getInstancia();
	}
	

	@Test
	public void testColores() {
		//Caso 1: Seleccionar  1er color
	
		this.gestor1.registrar("Per1", "Hola1", "gg");	
		gestor.cambiarColor("Per1", 0);
		int id=	gestor.obtenerColor("Per1");
		assertTrue(id==0);
		//Caso 2: Seleccionar  2o color
		gestor.cambiarColor("Per1", 1);
		 id=	gestor.obtenerColor("Per1");
		assertTrue(id==1);
		//Caso 3: Seleccionar  3er color
		gestor.cambiarColor("Per1", 2);
		 id=	gestor.obtenerColor("Per1");
		assertTrue(id==2);
		//Caso 4: Seleccionar  4o color
		
		gestor.cambiarColor("Per1", 3);
	 id=	gestor.obtenerColor("Per1");
		assertTrue(id==3);
		
		//Caso 5: Seleccionar un color que ya estaba en la BD
		
		gestor.cambiarColor("Per1", 3);
		 id=	gestor.obtenerColor("Per1");
			assertTrue(id==3);
		
	}
	
	

}
