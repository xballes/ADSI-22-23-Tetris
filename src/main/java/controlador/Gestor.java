package controlador;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

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
	
	
	public String obtContraseña(String pMail) {
		return GestorUsuarios.getInstancia().obtContraseña(pMail);
	}
	
	
	public boolean cambiarCont(String pNombre, String pCont) {
		return GestorUsuarios.getInstancia().cambiarCont(pNombre, pCont);
		
	}
	
	public boolean borrarUsuario(String pNombre) {
		return GestorUsuarios.getInstancia().borrarUsuario(pNombre);
	}
	
	public void sumarVictoriaA(String pUser) {
		GestorUsuarios.getInstancia().sumarVictoriaA(pUser);
	}
	
	public int obtenerNumVictoriasDe(String pUser) {
		return GestorUsuarios.getInstancia().obtenerNumVictoriasDe(pUser);

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
	
	public void publicarPuntuacion(String pUser, int pPuntos, int pNivel) {
		GestorRanking.getInstancia().publicarPuntuacion(pUser, pPuntos, pNivel);
	}
	
	public void guardarPartida(Board pPartida) {
		GestorPartida.getInstancia().guardarPartida(pPartida);
	}
	public int[][] cargarPartida(String pNombreUsuario,String pFecha,String pPuntos){
		return GestorPartida.getInstancia().cargarPartida(pNombreUsuario,pFecha,pPuntos);
	}
	public String mostrarPartidas(String pNombreUsuario) {
		return GestorPartida.getInstancia().mostrarPartidas(pNombreUsuario);
	}
	
	public void borrarPartida(String pUser, Timestamp fecha) {
		GestorPartida.getInstancia().borrarPartida(pUser, fecha);
	}
	
	public boolean tieneElPremio(String pUser, int id) {
		return GestorPremios.getInstancia().tieneElPremio(pUser, id);
	}
	
	public void darPremio(String pUser, int id, Timestamp pFecha) {
		GestorPremios.getInstancia().darPremio(pUser, id, pFecha);
	}
	

}
