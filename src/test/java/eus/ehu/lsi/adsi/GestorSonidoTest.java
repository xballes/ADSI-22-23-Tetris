package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;

import org.junit.Test;

import controlador.Gestor;
import controlador.GestorSonido;
import controlador.GestorUsuarios;

public class GestorSonidoTest {
	private GestorSonido gestor;
	private GestorUsuarios gestor1;
	
	public GestorSonidoTest() {
		this.gestor=GestorSonido.getInstancia();
		this.gestor1=GestorUsuarios.getInstancia();
	}
	

	@Test
	public void testColores() {
		//Caso 1: Seleccionar  1er color
		this.gestor1.registrar("Per1", "Hola1", "gg");	
		gestor.cambiarSonido("Per1", 0);
		int id=	gestor.obtenerSonido("Per1");
		assertTrue(id==0);
		//Caso 2: Seleccionar  2o color
		gestor.cambiarSonido("Per1", 1);
		 id=	gestor.obtenerSonido("Per1");
		assertTrue(id==1);
		//Caso 3: Seleccionar  3er color
		gestor.cambiarSonido("Per1", 2);
		 id=	gestor.obtenerSonido("Per1");
		assertTrue(id==2);

		
		//Caso5:  Seleccionar un sonido  ya seleccionado en la BD
		
		gestor.cambiarSonido("Per1", 2);
		 id=	gestor.obtenerSonido("Per1");
			assertTrue(id==2);
		
	}
	
	

}