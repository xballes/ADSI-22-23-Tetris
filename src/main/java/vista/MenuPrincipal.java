package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")

public class MenuPrincipal extends JFrame {
	
	private static MenuPrincipal puntero;
	private JPanel contenido;
	private JLabel titulo;

	
	private JPanel filas;
	private JButton[] botones; // Registrar, Iniciar Sesion, Recupero cont, Ranking

	
	public static void visibilizar() {
		MenuPrincipal.puntero = new MenuPrincipal();
		
		
	}
	
	private MenuPrincipal () {
		// Crear panel principal
		
		this.contenido = new JPanel();
		super.setContentPane(this.contenido);
		this.contenido.setLayout(new BorderLayout(50,50));
		
		// Crear titulo
		
		this.titulo = new JLabel("Menu Principal", SwingConstants.CENTER);
		this.titulo.setFont(new Font(Font.SANS_SERIF, 1, 30));
		this.contenido.add(this.titulo, BorderLayout.NORTH);
		
		// Crear estructura para botones
		
		this.filas = new JPanel();
		this.filas.setLayout(new GridLayout(5,1,5,50));
		this.botones = new JButton[5];
		
		for (int i = 0; i != 5; i++) {
			String val = null;
			switch (i) {
			case 0: val = "Registrarse"; break;
			case 1: val = "Iniciar Sesión"; break;	
			case 2: val = "Recuperar Contraseña"; break;	
			case 3: val = "Ver Ranking General"; break;
			case 4: val = "Cargar Partida"; break;	

			}
			
			this.botones[i] = new JButton(val);
			this.filas.add(this.botones[i]);
		}
		this.botones[0].addActionListener(new Accion1());
		this.botones[1].addActionListener(new Accion2());
		this.botones[2].addActionListener(new Accion3());
		this.botones[3].addActionListener(new Accion4());
		this.botones[4].addActionListener(new Accion5());


		
		this.contenido.add(this.filas, BorderLayout.CENTER);
		this.contenido.add(new JPanel(), BorderLayout.WEST);
		this.contenido.add(new JPanel(), BorderLayout.SOUTH);
		this.contenido.add(new JPanel(), BorderLayout.EAST);

		super.setBounds(100, 100, 700, 600);
		this.setResizable(false);
		this.setVisible(true);
		
		
	}
	
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			Registro.visibilizar();
		}
		
		
		
	}
	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			InicioSesion.visibilizar();
		}
		
		
	}
	
	private class Accion3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			RecuperoContraseña.visibilizar();
		}
		
		
	}
	
	private class Accion4 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// LLAMADA A RANKING GENERAL AQUI
		}
		
		
	}
	private class Accion5 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// ACCIÓN CARGAR PARTIDA
			puntero.dispose();
			CargarPartida.visibilizar();
		}
		
		
	}
	
	
}
	
	

