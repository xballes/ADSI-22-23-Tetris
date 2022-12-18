package vista;

import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Gestor;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class GameOver extends JFrame {

	
	// Pantalla que se abre al usuario al acabar una partida de Tetris, muestra si gano o no y su puntuación final,
	// entre otras cosas

	
	private JPanel contentPane;
	private String nombreUsuario;
	private static GameOver puntero;
	private int puntos;
	private int nivel;
	private Timestamp fechaSave;
	private boolean victoria;
	private boolean tetris;
	
	public static  void visibilizar(String pNombreUsuario,int pPuntos, int pNivel, Timestamp fechaSave, boolean pTetris) {
		GameOver.puntero = new GameOver(pNombreUsuario,pPuntos, pNivel, fechaSave, pTetris);		
		
	}
	
	public GameOver(String pNombreUsuario, int pPuntos, int pNivel, Timestamp fechaSave, boolean pTetris) {
		

		
		this.tetris = pTetris;
		nombreUsuario = pNombreUsuario;
		puntos = pPuntos;
		this.nivel = pNivel;
		this.fechaSave = fechaSave;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(20, 20));
		setContentPane(contentPane);
		
		
		int puntosNecesariosParaVictoria = 0;
		
		switch (pNivel) {
		case 1: puntosNecesariosParaVictoria = 15; break;
		case 2: puntosNecesariosParaVictoria = 30; break;
		case 3: puntosNecesariosParaVictoria = 45; break;
		}
		
		this.victoria = this.puntos >= puntosNecesariosParaVictoria;
		
		String texto = "";
		
		if (this.victoria) {
			texto = "¡Victoria!";
		} else {
			texto = "Derrota...";
		}
		
		JLabel lblNewLabel = new JLabel(texto);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.addActionListener(new Accion1());
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.EAST);
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new GridLayout(2,1,0,0));
		
		JLabel lblNewLabel_1 = new JLabel("La puntuación de " + nombreUsuario + " en esta partida ha sido de: " + puntos + " puntos.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_1);
		
		lblNewLabel_1 = new JLabel("Puntos necesarios para ganar en esta dificultad: "+puntosNecesariosParaVictoria);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_3.add(lblNewLabel_1);

		
		contentPane.add(panel_3, BorderLayout.CENTER);
		
		
		super.setResizable(false);
		super.setVisible(true);
		
		Gestor.getInstancia().acabarMusica(true);

	}
	
	// Accion: 1 volver
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			// AÑADIR SCORE
			Gestor.getInstancia().publicarPuntuacion(nombreUsuario, puntos, nivel);
			
			// CHECKEAR LOGROS DE VICTORIAS
			Timestamp ahora = new Timestamp(System.currentTimeMillis());

			if (victoria) { // Si pierde no se puede obtener un logro por ganar X partidas, en cuyo caso esta rama es omitible
				
				Gestor.getInstancia().sumarVictoriaA(nombreUsuario);
				int numVics = Gestor.getInstancia().obtenerNumVictoriasDe(nombreUsuario);
				ahora.setNanos(0);
				
				
				
				if (numVics == 1) {
					Gestor.getInstancia().darPremio(nombreUsuario, 0, ahora);
				} else if (numVics == 10) {
					Gestor.getInstancia().darPremio(nombreUsuario, 1, ahora);

				} else if (numVics == 25) {
					Gestor.getInstancia().darPremio(nombreUsuario, 2, ahora);

				}
				
				
			}
			
			// CHECKEAR LOGRO DE TETRIS
			
			if (tetris && !Gestor.getInstancia().tieneElPremio(nombreUsuario, 3)) {
				Gestor.getInstancia().darPremio(nombreUsuario, 3, ahora);
			}

			
			// BORRAR SAVE SI HUBIESE
			
			if (fechaSave != null) {
				Gestor.getInstancia().borrarPartida(nombreUsuario, fechaSave);
			}
			
			
			
			puntero.dispose();
			MenuDeUsuario.visibilizar(nombreUsuario);


		}
	}

}
