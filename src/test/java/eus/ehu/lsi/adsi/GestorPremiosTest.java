package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Test;

import controlador.GestorPremios;
import controlador.GestorUsuarios;

public class GestorPremiosTest {

	
	private GestorPremios gestorP;
	private GestorUsuarios gestorU;
	
	public GestorPremiosTest () {
		this.gestorP = GestorPremios.getInstancia();
		this.gestorU = GestorUsuarios.getInstancia();
		
	}
	
	
	private void reset() {
		gestorU.borrarTodosLosUsers();
	}
	
	
	@Test
	public void testConsultarPremios() {

		this.reset();
		gestorU.registrar("pepe", "hdh", "hdhd");
		gestorU.registrar("pablo", "hdh", "hdhd");
		
		
		// Caso 1: No hay premios otorgados
		assertTrue(this.gestorU.obtenerPremios("pepe").length==0);
		assertTrue(this.gestorU.obtenerPremios("pablo").length==0);
		
		// Caso 2: Un premio otorgado pero no al user pepe
		Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
		gestorP.darPremio("pablo", 1, timestamp);
		
		assertTrue(this.gestorU.obtenerPremios("pepe").length==0);
		
		// Caso 3: Varios premios otorgados pero no al user pepe
		gestorP.darPremio("pablo", 2, timestamp);
		gestorP.darPremio("pablo", 3, timestamp);
		assertTrue(this.gestorU.obtenerPremios("pepe").length==0);
		
		// Caso 4: Un premio otorgado y es al user pepe
		this.reset();
		gestorU.registrar("pepe", "hdh", "hdhd");
		gestorU.registrar("pablo", "dh", "hdhd");
		gestorP.darPremio("pepe", 1, timestamp);
		assertTrue(this.gestorU.obtenerPremios("pepe").length==1);
		
		
		// Caso 5: Varios premios otorgados y es al user pepe
		
		gestorP.darPremio("pepe", 2, timestamp);
		gestorP.darPremio("pepe", 0, timestamp);
		assertTrue(this.gestorU.obtenerPremios("pepe").length==3);
		
}
