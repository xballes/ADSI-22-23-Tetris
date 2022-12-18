package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;

import org.junit.Test;

import controlador.GestorPremios;
import controlador.GestorUsuarios;

public class GestorPremiosTest {

	
	private GestorPremios gestor;
	
	public GestorPremiosTest () {
		this.gestor = GestorPremios.getInstancia();
		
	}
	
	
	private void reset() {
		GestorUsuarios.getInstancia().borrarTodosUsers()
	}
	
	
	@Test
	public void testConsultarPremios() {

		this.reset();
		SGBD.execSQL("INSERT INTO usuario VALUES (pepe, hsh, hshs, 2,2,2,0)");
		SGBD.execSQL("INSERT INTO usuario VALUES (pablo, hsh, hshs, 2,2,2,1)");
		
		
		// Caso 1: No hay premios otorgados
		assertTrue(this.gestor.ConsultarPremios(pepe).length==0);
		assertTrue(this.gestor.ConsultarPremios(pablo).length==0);
		
		// Caso 2: Un premio otrogado pero no al user pepe
		SGBD.execSQL("INSERT INTO usuariopremio VALUES(pablo, Gana 1 partida, )");
		
		assertTrue(this.gestor.ConsultarPremios(pepe).length==0);
		
		// Caso 3: Un premio otorgado y es al user pepe
		
		
		
		// Caso 4: Registrar username existente
		
		assertTrue(this.gestor.registrar("Per1", "Pass4", "Mail2") == 2);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per1") == 1);
		assertTrue(this.gestor.obtContrase�aDe("Per1").contentEquals("Pass"));
		
		// Caso 5: Registrar mail existente
		
		assertTrue(this.gestor.registrar("Per5", "Pass4", "Mail1") == 3);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per5") == 0);

		// Caso 6: Caracteres especiales

		assertTrue(this.gestor.registrar("Per6", "Pass@##@@#dsfs", "Mail1") == 4);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per6") == 0);
		
		assertTrue(this.gestor.registrar("32423�$%$�%$�EWR", "Psdd", "Mail1") == 4);
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("Per6") == 0);
		
		assertTrue(this.gestor.registrar("32423�$%$�%$�EWR", "Psdd", "@#@#dsfsdk%%%$$%�%�%") == 4);
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
		// Post del m�todo: True si correcto, false si incorrecto
		
	
		
		this.reset();
		
		// Caso 1 y 2: Credenciales correctas
		
		this.gestor.registrar("Per1", "Pass", "Mail1");
		
		assertTrue(this.gestor.verificarUsuario("Per1", "Pass"));
		assertTrue(this.gestor.verificarUsuario("administrador", "123456"));

		// Caso 3: No se llenan los campos
		
		assertTrue(!this.gestor.verificarUsuario("", ""));
		
		// Caso 4: Un campo sin llenar
		
		assertTrue(!this.gestor.verificarUsuario("Per1", ""));
		assertTrue(!this.gestor.verificarUsuario("", "Pass"));

		// Caso 5: Campos llenados pero no en BD
		
		assertTrue(!this.gestor.verificarUsuario("Per2", "Pass2"));
		
		// Caso 6: Contrase�a invalida
		
		assertTrue(!this.gestor.verificarUsuario("Per1", "Pass2"));

		// Caso 7: Username invalido
		
		assertTrue(!this.gestor.verificarUsuario("Per2", "Pass"));
		
		// Caso 8: nombre de un user y contrase�a de otro
		
		this.gestor.registrar("Per2", "Pass2", "Mail2");
		
		assertTrue(!this.gestor.verificarUsuario("Per1", "Pass2"));

		// Caso 9: Varios users con misma contrase�a, uno de ellos se loggea
		
		this.gestor.registrar("Per3", "Pass", "Mail3");
		this.gestor.registrar("Per4", "Pass", "Mail4");

		assertTrue(this.gestor.verificarUsuario("Per1", "Pass"));
		
		// Caso 10: Chars especiales incorrectos
		
		assertTrue(!this.gestor.verificarUsuario("Pe�$$�%�$%$�%�$r1", "Pass"));


	}
	@Test
	public void testObtContrase�a() {
		
		// Post del m�todo: Si coinicide, la string con la contrase�a, else, null

		this.reset();
		
		// Caso 1: Todo correcto
		
		this.gestor.registrar("Per1", "Pass", "Mail1");
		
		assertTrue(this.gestor.obtContrase�a("Mail1").contentEquals("Pass"));
		
		// Caso 2: Campo vac�o


		assertTrue(this.gestor.obtContrase�a("") == null);

		// Caso 3: Mail no en BD
		
		assertTrue(this.gestor.obtContrase�a("Mail2") == null);

		
		// Caso 4: Caracteres especiales
		
		assertTrue(this.gestor.obtContrase�a("�$�%�$%�$%�$�$�$") == null);
		
		// Caso 5: String MUY larga
		
		assertTrue(this.gestor.obtContrase�a("dskfujidfdopijfdespijfsdpifsdjipfdsjipfdjpfidjfdpijdfspifdjspifdjfpidjfpdijfdspijdfsipfdjpifdjfdspijfsdpifdsjpifdsjpfidjfdpijfdspijfdipfsdjpidjfpdijfdspifdjspifdjfpdij") == null);


	}
	
	
	@Test
	public void testCambiarCont() {
		// Del m�todo a testear:
		// Pre: pNombre es �nico en BD y est�
		// Post: Se cambia la contrase�a si se encuentra el user y se returneo true. 
		//       Return true si contrase�a nueva alfanum�rica y tiene 1-30 chars
		this.reset();
		
		this.gestor.registrar("Per1", "Pass", "Mail1");

		// Caso 1: Todo correcto
		
		assertTrue(this.gestor.cambiarCont("Per1", "Pass3"));
		assertTrue(this.gestor.obtContrase�aDe("Per1").contentEquals("Pass3"));
		
		// Caso 2: Contrase�a vac�a
		
		assertTrue(!this.gestor.cambiarCont("Per1", ""));
		assertTrue(this.gestor.obtContrase�aDe("Per1").contentEquals("Pass3")); // Se mantiene como Pass3
		
		// Caso 3: Chars especiales
		
		assertTrue(!this.gestor.cambiarCont("Per1", "ssadasdasd$$%$%%$"));
		assertTrue(this.gestor.obtContrase�aDe("Per1").contentEquals("Pass3")); // Se mantiene como Pass3

		// Caso 4: Cambiar contrase�a a la de otro user

		this.gestor.registrar("Per2", "Pass2", "Mail2");

		assertTrue(this.gestor.cambiarCont("Per1", "Pass2"));
		assertTrue(this.gestor.obtContrase�aDe("Per1").contentEquals("Pass2")); 

		
	}
	
	
	@Test
	public void testBorrarUsuario() {
		// Post del m�todo: True si se borr� alguien, false si no. Nota: El usuario administrador tiene inmunidad al borrado

		
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

		
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("administrador") == 1);
		assertTrue(!this.gestor.borrarUsuario("administrador"));
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("administrador") == 1);

		// Caso 5: Borrar vac�o

		assertTrue(this.gestor.contarUsuariosConEseNomnbre("") == 0);
		assertTrue(!this.gestor.borrarUsuario(""));
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("") == 0);
		
		// Caso 6: Chars especiales

		assertTrue(this.gestor.contarUsuariosConEseNomnbre("�$$�$�~#@#~@") == 0);
		assertTrue(!this.gestor.borrarUsuario("�$$�$�~#@#~@"));
		assertTrue(this.gestor.contarUsuariosConEseNomnbre("�$$�$�~#@#~@") == 0);
		
		

	}
	
}
