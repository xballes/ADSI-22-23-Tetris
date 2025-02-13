package controlador;

import java.util.ArrayList;

import com.google.gson.Gson;

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
	
	
	public String obtContraseña(String pMail) {
		return GestorUsuarios.getInstancia().obtContraseña(pMail);
	}
	
	
	public boolean cambiarCont(String pNombre, String pCont) {
		return GestorUsuarios.getInstancia().cambiarCont(pNombre, pCont);
		
	}
	
	public boolean borrarUsuario(String pNombre) {
		return GestorUsuarios.getInstancia().borrarUsuario(pNombre);
	}
	
	public String obtenerRankingTodosNivelesPublico() {
		return GestorRanking.getInstancia().obtenerRankingTodosNivelesPublico();
	}
	
	public String obtenerRankingNivelPublico(int pNivel) {
		return GestorRanking.getInstancia().obtenerRankingNivelPublico(pNivel);
	}
	
	public String obtenerRankingTodosNivelPersonal(String pNombre) {
		return GestorRanking.getInstancia().obtenerRankingTodosNivelPersonal(pNombre);
	}
	
	public ArrayList<Integer> obtenerRankingNivelPriv(String pNombre,int pNivel) {
		return GestorRanking.getInstancia().obtenerRankingNivelPriv(pNombre, pNivel);
	}
	
	
}
