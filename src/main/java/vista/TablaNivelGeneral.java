package vista;

import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.GestorRanking;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.GridLayout;

public class TablaNivelGeneral extends JFrame {

	// Muestra la tabla del top 10 puntuaciones, filtrando por un nivel concreto

	
	private JPanel contentPane;
	private static TablaNivelGeneral puntero;
	private String nombreUsuario;
	
	
	public static  void visibilizar(String pNombreUsuario, int pNivel) {	
		TablaNivelGeneral.puntero = new TablaNivelGeneral(pNombreUsuario, pNivel);	
	}
	
	private TablaNivelGeneral(String pNombreUsuario, int pNivel) {
		
		nombreUsuario = pNombreUsuario;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(50, 50));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Menu de nivel" + " " + pNivel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.addActionListener(new Accion1());
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		
		String resul = GestorRanking.getInstancia().obtenerRankingNivelPublico(pNivel);
		
		String[] array = resul.split("}");
		String[] puntos = new String [array.length-1];
		String[] nombre = new String [array.length-1];
		
		
		for(int i=0; i != array.length-1;i++) {
			
			int ind=0;
			
			while(array[i].charAt(ind)!=':') {
				ind++;
			}
			String aux = "";
			ind++;
			
			while(array[i].charAt(ind)!=',') {
				aux = aux + array[i].charAt(ind);
				ind++;
			}
			
			puntos[i] =aux;

			while(array[i].charAt(ind)!=':') {
				ind++;
			}
			
			ind++;
			ind++;
			
			aux = "";
			
			while(array[i].charAt(ind)!='"') {
				aux = aux + array[i].charAt(ind);
				ind++;
			}
			
			nombre[i]=aux;
		}
			

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(puntos.length+1, 2, 10, 10));
		
		panel.add(new JLabel("Puntuación"));
		panel.add(new JLabel("Nombre"));
		
		for(int i=0; i < puntos.length; i++) {
			panel.add(new JLabel(puntos[i]));
			panel.add(new JLabel(nombre[i]));
		}
		
		contentPane.add(new JPanel(), BorderLayout.WEST);
		contentPane.add(new JPanel(), BorderLayout.EAST);
		
		super.setResizable(false);
		super.setVisible(true);
	}

	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuRankingPublico.visibilizar(nombreUsuario);
		}
	}
	

	
	
	
	
}