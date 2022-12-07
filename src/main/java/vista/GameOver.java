package vista;

import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GameOver extends JFrame {

	private JPanel contentPane;
	private String nombreUsuario;
	private static GameOver puntero;
	private int puntos;
	
	public static  void visibilizar(String pNombreUsuario,int pPuntos) {
		GameOver.puntero = new GameOver(pNombreUsuario,pPuntos);		
		
	}
	
	public GameOver(String pNombreUsuario, int pPuntos) {
		
		nombreUsuario = pNombreUsuario;
		puntos = pPuntos;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(20, 20));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Fin de la partida");
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
		
		JLabel lblNewLabel_1 = new JLabel("La puntuación de " + nombreUsuario + " en esta partida ha sido de: " + puntos + " puntos.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(lblNewLabel_1, BorderLayout.CENTER);
		
		
		super.setResizable(false);
		super.setVisible(true);
	}
	
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			if(nombreUsuario == null) {
				MenuPrincipal.visibilizar();
			}else {
				MenuDeUsuario.visibilizar(nombreUsuario);
			}
		}
	}

}
