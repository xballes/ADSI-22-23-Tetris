package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.GestorRanking;



@SuppressWarnings("serial")
public class TablaNivelPersonal extends JFrame {

	// Muestra la tabla del top 10 puntuaciones, filtrando por un nivel y usuario concreto

	
	private JPanel contentPane;
	private static TablaNivelPersonal puntero;
	private String nombreUsuario;
	private ArrayList<Integer> res;
	
	public static  void visibilizar(String pNombreUsuario, int pNivel) {	
		TablaNivelPersonal.puntero = new TablaNivelPersonal(pNombreUsuario, pNivel);
	}

	private TablaNivelPersonal(String pNombreUsuario, int pNivel) {
		
		nombreUsuario = pNombreUsuario;
		
		res = new ArrayList<Integer>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(50, 50));
		setContentPane(contentPane);
		
		
		JLabel lblNewLabel = new JLabel("Menu de nivel" + " " + pNivel + " y del usuario: " + nombreUsuario);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.addActionListener(new Accion1());
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		
		res = GestorRanking.getInstancia().obtenerRankingNivelPriv(nombreUsuario,pNivel);
		
	
		if (res.size() == 0) {
			contentPane.add(new JLabel("No hay rankings", SwingConstants.CENTER), BorderLayout.CENTER);

		} else {
			JPanel panel = new JPanel();
			contentPane.add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(11, 1, 0, 0));
			JLabel lab = new JLabel("Puntuación",SwingConstants.CENTER);
			lab.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(lab);


			for(int i=0; i < res.size(); i++) {
				lab = new JLabel(Integer.toString(res.get(i)),SwingConstants.CENTER);
				lab.setBorder(BorderFactory.createLineBorder(Color.black));
				panel.add(lab);
			}
			
			
			for (int i=res.size(); i != 10; i++) {
				lab = new JLabel("-", SwingConstants.CENTER);
				lab.setBorder(BorderFactory.createLineBorder(Color.black));
				panel.add(lab);
				
			}
		}


		
		contentPane.add(new JPanel(), BorderLayout.WEST);
		contentPane.add(new JPanel(), BorderLayout.EAST);
		
		super.setResizable(false);
		super.setVisible(true);
	}
	
	// Accion: 1 volver

	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuRankingPersonal.visibilizar(nombreUsuario);
		}
	}

}