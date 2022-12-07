package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;

import org.junit.Test;

import controlador.GestorUsuarios;

public class GestorUsuariosTest {

	
	private GestorUsuarios gestor;
	
	public GestorUsuariosTest () {
		this.gestor = GestorUsuarios.getInstancia();
		
	}
	
	
	private void reset() {
		this.gestor.borrarTodosLosUsers();
	}
	
	
	@Test
	public void testRegistrar() {
		// Post del método: 0 si correcto, 1 si demasiado largos o corto, 2 si username ya existente, 3 si mail ya existente, 4 si caracteres invalidos

		this.reset();
		
		
		// Caso 1: Registrar de forma correcta
		assertTrue(this.gestor.registrar("Per1", "Pass", "Mail1") == 0);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per1") == 1);
		assertTrue(this.gestor.obtContraseñaDe("Per1").contentEquals("Pass"));
		
		// Caso 2: Campos vacios
		
		assertTrue(this.gestor.registrar("", "", "") == 1);
		
		// Caso 3: Alguno vacío
		
		assertTrue(this.gestor.registrar("Per2", "", "") == 1);
		assertTrue(this.gestor.registrar("", "Pass", "") == 1);
		assertTrue(this.gestor.registrar("", "", "Mail1") == 1);
		assertTrue(this.gestor.registrar("Per2", "Pass", "") == 1);
		assertTrue(this.gestor.registrar("", "Pass", "Mail2") == 1);
		assertTrue(this.gestor.registrar("Per2", "", "Mail2") == 1);
		
		// Caso 4: Registrar username existente
		
		assertTrue(this.gestor.registrar("Per1", "Pass4", "Mail2") == 2);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per1") == 1);
		assertTrue(this.gestor.obtContraseñaDe("Per1").contentEquals("Pass"));
		
		// Caso 5: Registrar mail existente
		
		assertTrue(this.gestor.registrar("Per5", "Pass4", "Mail1") == 3);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per5") == 0);

		// Caso 6: Caracteres especiales

		assertTrue(this.gestor.registrar("Per6", "Pass@##@@#dsfs", "Mail1") == 4);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per6") == 0);
		
		assertTrue(this.gestor.registrar("32423·$%$·%$·EWR", "Psdd", "Mail1") == 4);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per6") == 0);
		
		assertTrue(this.gestor.registrar("32423·$%$·%$·EWR", "Psdd", "@#@#dsfsdk%%%$$%·%·%") == 4);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per6") == 0);
		
		// Caso 3: Campos muy largos
	
		assertTrue(this.gestor.registrar("Per7dfskjfsdkfsdjfsdkjfsdlkfsdjklfsdjfsdlkfdsjlkfsdjflsdkjfdslkfdsajklfasdjfsdalkfdsjaklfdsajlfsd", "Pass", "Mail7") == 1);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per7dfskjfsdkfsdjfsdkjfsdlkfsdjklfsdjfsdlkfdsjlkfsdjflsdkjfdslkfdsajklfasdjfsdalkfdsjaklfdsajlfsd") == 0);
		
		assertTrue(this.gestor.registrar("Per7", "Per7dfskjfsdkfsdjfsdkjfsdlkfsdjklfsdjfsdlkfdsjlkfsdjflsdkjfdslkfdsajklfasdjfsdalkfdsjaklfdsajlfsd", "Mail7") == 1);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per7") == 0);
		
		assertTrue(this.gestor.registrar("Per7", "Pass", "Per7dfskjfsdkfsdjfsdkjfsdlkfsdjklfsdjfsdlkfdsjlkfsdjflsdkjfdslkfdsajklfasdjfsdalkfdsjaklfdsajlfsd") == 1);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per7") == 0);
	}

	
	@Test
	public void testVerificarUsuario() {
		// Post del método: True si correcto, false si incorrecto
		
	
		
		this.reset();
		
		// Caso 1 y 2: Credenciales correctas
		
		this.gestor.registrar("Per1", "Pass", "Mail1");
		
		assertTrue(this.gestor.verificarUsuario("Per1", "Pass"));
		assertTrue(this.gestor.verificarUsuario("Administrador", "123456"));

		// Caso 3: No se llenan los campos
		
		assertTrue(!this.gestor.verificarUsuario("", ""));
		
		// Caso 4: Un campo sin llenar
		
		assertTrue(!this.gestor.verificarUsuario("Per1", ""));
		assertTrue(!this.gestor.verificarUsuario("", "Pass"));

		// Caso 5: Campos llenados pero no en BD
		
		assertTrue(!this.gestor.verificarUsuario("Per2", "Pass2"));
		
		// Caso 6: Contraseña invalida
		
		assertTrue(!this.gestor.verificarUsuario("Per1", "Pass2"));

		// Caso 7: Username invalido
		
		assertTrue(!this.gestor.verificarUsuario("Per2", "Pass"));
		
		// Caso 8: nombre de un user y contraseña de otro
		
		this.gestor.registrar("Per2", "Pass2", "Mail2");
		
		assertTrue(!this.gestor.verificarUsuario("Per1", "Pass2"));

		// Caso 9: Varios users con misma contraseña, uno de ellos se loggea
		
		this.gestor.registrar("Per3", "Pass", "Mail3");
		this.gestor.registrar("Per4", "Pass", "Mail4");

		assertTrue(this.gestor.verificarUsuario("Per1", "Pass"));
		
		// Caso 10: Chars especiales incorrectos
		
		assertTrue(!this.gestor.verificarUsuario("Pe·$$·%·$%$·%·$r1", "Pass"));


	}
	@Test
	public void testObtContraseña() {
		
		// Post del método: Si coinicide, la string con la contraseña, else, null

		this.reset();
		
		// Caso 1: Todo correcto
		
		this.gestor.registrar("Per1", "Pass", "Mail1");
		
		assertTrue(this.gestor.obtContraseña("Mail1").contentEquals("Pass"));
		
		// Caso 2: Campo vacío


		assertTrue(this.gestor.obtContraseña("") == null);

		// Caso 3: Mail no en BD
		
		assertTrue(this.gestor.obtContraseña("Mail2") == null);

		
		// Caso 4: Caracteres especiales
		
		assertTrue(this.gestor.obtContraseña("·$·%·$%·$%·$·$·$") == null);
		
		// Caso 5: String MUY larga
		
		assertTrue(this.gestor.obtContraseña("dskfujidfdopijfdespijfsdpifsdjipfdsjipfdjpfidjfdpijdfspifdjspifdjfpidjfpdijfdspijdfsipfdjpifdjfdspijfsdpifdsjpifdsjpfidjfdpijfdspijfdipfsdjpidjfpdijfdspifdjspifdjfpdij") == null);


	}
	
	
	@Test
	public void testCambiarCont() {
		// Del método a testear:
		// Pre: pNombre es único en BD y está
		// Post: Se cambia la contraseña si se encuentra el user y se returneo true. 
		//       Return true si contraseña nueva alfanumérica y tiene 1-30 chars
		this.reset();
		
		this.gestor.registrar("Per1", "Pass", "Mail1");

		// Caso 1: Todo correcto
		
		assertTrue(this.gestor.cambiarCont("Per1", "Pass3"));
		assertTrue(this.gestor.obtContraseñaDe("Per1").contentEquals("Pass3"));
		
		// Caso 2: Contraseña vacía
		
		assertTrue(!this.gestor.cambiarCont("Per1", ""));
		assertTrue(this.gestor.obtContraseñaDe("Per1").contentEquals("Pass3")); // Se mantiene como Pass3
		
		// Caso 3: Chars especiales
		
		assertTrue(!this.gestor.cambiarCont("Per1", "ssadasdasd$$%$%%$"));
		assertTrue(this.gestor.obtContraseñaDe("Per1").contentEquals("Pass3")); // Se mantiene como Pass3

		// Caso 4: Cambiar contraseña a la de otro user

		this.gestor.registrar("Per2", "Pass2", "Mail2");

		assertTrue(this.gestor.cambiarCont("Per1", "Pass2"));
		assertTrue(this.gestor.obtContraseñaDe("Per1").contentEquals("Pass2")); 

		
	}
	
	
	@Test
	public void testBorrarUsuario() {
		// Post del método: True si se borró alguien, false si no. Nota: El usuario administrador tiene inmunidad al borrado

		
		this.reset();
		
		// Caso 1: Todo correcto
		
		this.gestor.registrar("Per1", "Pass", "Mail1");
		
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per1") == 1);
		assertTrue(this.gestor.borrarUsuario("Per1"));
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per1") == 0);
		
		// Caso 2: Borrar alguien que no hay
		
		this.gestor.registrar("Per1", "Pass", "Mail1");

		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per2") == 0);
		assertTrue(!this.gestor.borrarUsuario("Per2"));
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per2") == 0);
		
		// Caso 3: Borrar varios users en una string
		
		this.gestor.registrar("Per2", "Pas2", "Mail2");

		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per1") == 1);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per2") == 1);
		assertTrue(!this.gestor.borrarUsuario("Per1 Per2"));
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per1") == 1);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per2") == 1);
		
		// Caso 4 Borrar administrador

		
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Administrador") == 1);
		assertTrue(!this.gestor.borrarUsuario("Administrador"));
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Administrador") == 1);

		// Caso 5: Borrar vacío

		assertTrue(this.gestor.contarUsuariosConEseNomnbre("") == 0);
		assertTrue(!this.gestor.borrarUsuario(""));
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("") == 0);
		
		// Caso 6: Chars especiales

		assertTrue(this.gestor.contarUsuariosConEseNomnbre("·$$·$·~#@#~@") == 0);
		assertTrue(!this.gestor.borrarUsuario("·$$·$·~#@#~@"));
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("·$$·$·~#@#~@") == 0);
		
		

	}
	
}
