package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.zetcode.Board;

import controlador.Gestor;
import controlador.GestorPartida;

@SuppressWarnings("serial")

public class PausaGuardado extends JFrame {
	
	// Pantalla que se abre cuando un usuario pausa la partida de tetris (sobrescribe el pausado predefinido en 
	// el software original)
	
	
	private static PausaGuardado puntero;
	private JPanel contenido;
	private JLabel titulo;
	private Board board;

	
	private JPanel filas;
	private JButton[] botones; // Guardar partida, volver a la partida.

	
	public static void visibilizar(Board pPuntero) {
		PausaGuardado.puntero = new PausaGuardado(pPuntero);
		
		
	}
	
	private PausaGuardado(Board pPuntero) {
		// Crear panel principal
		board=pPuntero;
		this.contenido = new JPanel();
		super.setContentPane(this.contenido);
		this.contenido.setLayout(new BorderLayout(50,50));
		
		// Crear titulo
		
		this.titulo = new JLabel("Menú de guardar partida", SwingConstants.CENTER);
		this.titulo.setFont(new Font(Font.SANS_SERIF, 1, 30));
		this.contenido.add(this.titulo, BorderLayout.NORTH);
		
		// Crear estructura para botones
		
		this.filas = new JPanel();
		this.filas.setLayout(new GridLayout(2,1,5,50));
		this.botones = new JButton[2];
		
		for (int i = 0; i != 2; i++) {
			String val = null;
			switch (i) {
			case 0: val = "Guardar partida"; break;
			case 1: val = "Volver a la partida"; break;	

			}
			
			this.botones[i] = new JButton(val);
			this.filas.add(this.botones[i]);
		}
		this.botones[0].addActionListener(new Accion1());
		this.botones[1].addActionListener(new Accion2());
		
		this.contenido.add(this.filas, BorderLayout.CENTER);
		this.contenido.add(new JPanel(), BorderLayout.WEST);
		this.contenido.add(new JPanel(), BorderLayout.SOUTH);
		this.contenido.add(new JPanel(), BorderLayout.EAST);

		super.setBounds(100, 100, 700, 600);
		this.setResizable(false);
		this.setVisible(true);
		
		// Pausar musica
		Gestor.getInstancia().pausaMusica();
		
		
	}
	
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) { //Botón guardar partida
			puntero.dispose();
			Gestor.getInstancia().guardarPartida(board.calcularMatriz(), board.getNombreUsuario(), board.getBOARD_WIDTH(), board.getFechaSave(), board.getNumLinesRemoved(), board.getTetrisRealizado());
			board.forzarCerrado();
			Gestor.getInstancia().acabarMusica(false);
			MenuDeUsuario.visibilizar(board.getNombreUsuario());
		}
		
		
		
	}
	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) { //Botón volver
			puntero.dispose();
			Gestor.getInstancia().despausaMusica();
			board.despausar();
			
			
		}
		
		
	}	
}
	