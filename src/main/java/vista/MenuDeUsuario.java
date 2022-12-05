package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.Gestor;

@SuppressWarnings("serial")

public class MenuDeUsuario extends JFrame implements Ventana {
	
	private static MenuDeUsuario puntero;
	private JPanel contenido;
	private JLabel titulo;

	
	private JPanel filas;
	private JButton[] botones; // Registrar, Iniciar Sesion, Recupero cont, Ranking
	
	
	
	// Variables internas de IU
	
	
	private String usuario;
	private String fecha;	
	
	public void redirigir() {
		MenuDeUsuario.visibilizar(usuario);
		
	}


	
	public static void visibilizar(String pUser) {
		MenuDeUsuario.puntero = new MenuDeUsuario(pUser);
		
		
	}
	
	private MenuDeUsuario (String pUser) {
		
		this.usuario = pUser.toLowerCase();
		
		// Crear panel principal
		
		this.contenido = new JPanel();
		super.setContentPane(this.contenido);
		this.contenido.setLayout(new BorderLayout(50,50));
		
		// Crear titulo
		
		this.titulo = new JLabel("Bienvenido "+this.usuario, SwingConstants.CENTER);
		this.titulo.setFont(new Font(Font.SANS_SERIF, 1, 30));
		this.contenido.add(this.titulo, BorderLayout.NORTH);
		
		// Crear estructura para botones
		
		this.filas = new JPanel();
		
		int filas;
		
		if (this.usuario.contentEquals("administrador")) {
			filas = 9;
		} else {
			filas = 8;
		}
		
		
		this.filas.setLayout(new GridLayout(filas,1,5,5));
		this.botones = new JButton[filas];
		
		for (int i = 0; i != filas; i++) {
			String val = null;
			switch (i) {
			case 0: val = "Partida Nueva"; break;
			case 1: val = "Cargar Partida"; break;	
			case 2: val = "Personalizar"; break;	
			case 3: val = "Ver Ranking General"; break;	
			case 4: val = "Ver Ranking Personal"; break;	
			case 5: val = "Ver Premios Obtenidos"; break;	
			case 6: val = "Cambiar Contraseña"; break;	
			case 7: val = "Cerrar Sesión"; break;	
			case 8: val = "Admin: Eliminar Usuarios"; break;	

			}
			
			this.botones[i] = new JButton(val);
			this.filas.add(this.botones[i]);
		}
		this.botones[0].addActionListener(new Accion1());
		this.botones[1].addActionListener(new Accion2());
		this.botones[2].addActionListener(new Accion3());
		this.botones[3].addActionListener(new Accion4());
		this.botones[4].addActionListener(new Accion5());
		this.botones[5].addActionListener(new Accion6());
		this.botones[6].addActionListener(new Accion7());
		this.botones[7].addActionListener(new Accion8());
		if (filas == 9) {this.botones[8].addActionListener(new Accion9());}
		


		
		this.contenido.add(this.filas, BorderLayout.CENTER);
		this.contenido.add(new JPanel(), BorderLayout.WEST);
		this.contenido.add(new JPanel(), BorderLayout.SOUTH);
		this.contenido.add(new JPanel(), BorderLayout.EAST);

		super.setBounds(500, 50, 700, 900);
		this.setResizable(false);
		this.setVisible(true);
		
		
	}
	
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// LLAMADA A NIVELES

		}
		
		
		
	}
	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			//Comprobar saves...
			puntero.dispose();
			PartidasGuardadas.visibilizar(usuario);
			//boolean Resultado=Gestor.getInstancia().cargarPartida(usuario);		
			//if(Resultado!=false) {
				//PopUp.visibilizar("La partida se ha cargado correctamente", puntero);	
			//}
			//else {
				//PopUp.visibilizar("La partida no se ha cargado correctamente", puntero);
				
			//}
		}
	}
		
	
	private class Accion3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// LLAMADA A PERSONALIZAR
		}
		
		
	}
	
	private class Accion4 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// VER RANKING GENERAL
		}
		
		
	}
	
	private class Accion5 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// VER RANKING PERSONAL
		}
		
		
	}
	
	private class Accion6 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// VER PREMIOS OBTENIDOS
		}
		
		
	}
	
	private class Accion7 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			CambioContraseña.visibilizar(usuario);
		}
		
		
	}
	
	private class Accion8 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuPrincipal.visibilizar();		
			
		}
		
		
	}
	
	private class Accion9 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			EliminarUsuario.visibilizar();
		}
		
		
	}


	
}
	
	

