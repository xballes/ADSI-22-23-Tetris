package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;

import controlador.GestorLadrillos;
import controlador.GestorUsuarios;

import org.junit.Test;

public class GestorLadrillosTest {
	private GestorLadrillos gestor;
	private GestorUsuarios gestor1;
	
	public GestorLadrillosTest() {
		this.gestor=GestorLadrillos.getInstancia();
		this.gestor1=GestorUsuarios.getInstancia();
	}
	@Test
	public void testLadrillos() {
		//Caso 1:Adjudicar 1er Set Colores
		this.gestor1.registrar("Per1", "Hola1", "gg");	
		gestor.editarLadrillos("Per1", 0);
		int id=	gestor.obtenerSet("Per1");
		assertTrue(id==0);
		//Caso 2:Adjudicar 2o Set Colores
		this.gestor1.registrar("Per1", "Hola1", "gg");		
		gestor.editarLadrillos("Per1", 1);
		id=	gestor.obtenerSet("Per1");
		assertTrue(id==1);
		//Caso 3:Adjudicar 3er Set Colores
		this.gestor1.registrar("Per1", "Hola1", "gg");		
		gestor.editarLadrillos("Per1", 2);
		id=	gestor.obtenerSet("Per1");
		assertTrue(id==2);
		//Caso 4:Adjudicar 4o Set Colores
		this.gestor1.registrar("Per1", "Hola1", "gg");		
		gestor.editarLadrillos("Per1", 3);
		id=	gestor.obtenerSet("Per1");
		assertTrue(id==3);
		
		//Caso 5:Adjudicar 5o Set Colores
		this.gestor1.registrar("Per1", "Hola1", "gg");		
		gestor.editarLadrillos("Per1", 4);
		id=	gestor.obtenerSet("Per1");
		assertTrue(id==4);
		
	}

}