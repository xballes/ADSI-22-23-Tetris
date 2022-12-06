package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.GestorRanking;



public class TablaNivelPersonal extends JFrame {

	private JPanel contentPane;
	private static TablaNivelPersonal puntero;
	private String nombreUsuario;
	private int nivel;
	private ArrayList<Integer> res;
	
	public static  void visibilizar(String pNombreUsuario, int pNivel) {	
		TablaNivelPersonal.puntero = new TablaNivelPersonal(pNombreUsuario, pNivel);
	}

	private TablaNivelPersonal(String pNombreUsuario, int pNivel) {
		
		nombreUsuario = pNombreUsuario;
		nivel = pNivel;
		
		res = new ArrayList<Integer>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(50, 50));
		setContentPane(contentPane);
		
		
		JLabel lblNewLabel = new JLabel("Menu de nivel" + " " + nivel + " y del usuario: " + nombreUsuario);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.addActionListener(new Accion1());
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		
		res = GestorRanking.getInstancia().obtenerRankingNivelPriv(nombreUsuario,nivel);
		
	
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(res.size()+1, 2, 10, 10));
		
		panel.add(new JLabel("Puntuación"));
		
		for(int i=0; i < res.size(); i++) {
			
			String aux = "";
			String rs =  res.get(i) + aux;
			panel.add(new JLabel(rs));
			
		}
		
		contentPane.add(new JPanel(), BorderLayout.WEST);
		contentPane.add(new JPanel(), BorderLayout.EAST);
		
		super.setResizable(false);
		super.setVisible(true);
	}
	
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuRankingPersonal.visibilizar(nombreUsuario);
		}
	}

}
