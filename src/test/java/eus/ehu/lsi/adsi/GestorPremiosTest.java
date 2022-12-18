package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;

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
		gestorU.registrar(pepe, hdh, hdhd);
		gestorU.registrar(pablo, hdh, hdhd);
		
		
		// Caso 1: No hay premios otorgados
		assertTrue(this.gestor.ConsultarPremios(pepe).length==0);
		assertTrue(this.gestor.ConsultarPremios(pablo).length==0);
		
		// Caso 2: Un premio otorgado pero no al user pepe
		Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
		gestorP.darPremios(pablo, 1, timestamp);
		
		assertTrue(this.gestor.ConsultarPremios(pepe).length==0);
		
		// Caso 3: Varios premios otorgados pero no al user pepe
		gestorP.darPremios(pablo, 2, timestamp);
		gestorP.darPremios(pablo, 3, timestamp);
		assertTrue(this.gestor.ConsultarPremios(pepe).length==0);
		
		// Caso 4: Un premio otorgado y es al user pepe
		this.reset();
		gestorU.registrar(pepe, hdh, hdhd);
		gestorU.registrar(pablo, hdh, hdhd);
		gestorP.darPremios(pepe, 1, timestamp);
		assertTrue(this.gestor.ConsultarPremios(pepe).length==1);
		
		
		// Caso 5: Varios premios otorgados y es al user pepe
		
		gestorP.darPremios(pepe, 2, timestamp);
		gestorP.darPremios(pepe, 0, timestamp);
		assertTrue(this.gestor.ConsultarPremios(pepe).length==3);
		
}
