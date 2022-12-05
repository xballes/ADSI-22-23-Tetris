package controlador;

import com.zetcode.Board;

public class Gestor {

	private static Gestor puntero;
	
	
	
	private Gestor() {}
	
	public static Gestor getInstancia() {
		if (Gestor.puntero == null) {Gestor.puntero = new Gestor();}
		return Gestor.puntero;
	}
	
	
	
	
	public boolean verificarUsuario(String pNombre, String pCont) {
		return GestorUsuarios.getInstancia().verificarUsuario(pNombre, pCont);
	}
	
	public int registrar(String pNombre, String pCont, String pMail) {
		return GestorUsuarios.getInstancia().registrar(pNombre, pCont, pMail);
	}
	
	
	public String obtContrase�a(String pMail) {
		return GestorUsuarios.getInstancia().obtContrase�a(pMail);
	}
	
	
	public boolean cambiarCont(String pNombre, String pCont) {
		return GestorUsuarios.getInstancia().cambiarCont(pNombre, pCont);
		
	}
	
	public boolean borrarUsuario(String pNombre) {
		return GestorUsuarios.getInstancia().borrarUsuario(pNombre);
	}
	public void guardarPartida(Board pPartida) {
		GestorPartida.getInstancia().guardarPartida(pPartida);
	}
	public boolean cargarPartida(String pNombreUsuario){
		return GestorPartida.getInstancia().cargarPartida(pNombreUsuario);
	}
	public String mostrarPartidas(String pNombreUsuario) {
		return GestorPartida.getInstancia().mostrarPartidas(pNombreUsuario);
	}
	
}
