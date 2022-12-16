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
		//Caso 1: Seleccionar color
	
		this.gestor1.registrar("Per1", "Hola1", "gg");	
		gestor.cambiarColor("Per1", 1);
		int id=	gestor.obtenerColor("Per1");
		assertTrue(id==1);
		
		
	}

}