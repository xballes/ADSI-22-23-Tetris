package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.GestorRanking;


public class TablaTodosPersonal extends JFrame {

	private JPanel contentPane;
	private static TablaTodosPersonal puntero;
	private String nombreUsuario;
	
	public static  void visibilizar(String pNombreUsuario) {
		TablaTodosPersonal.puntero = new TablaTodosPersonal(pNombreUsuario);
		
	}
	

	private TablaTodosPersonal(String pNombreUsuario) {
		
		nombreUsuario = pNombreUsuario;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(50, 50));
		setContentPane(contentPane);
		
		
		JLabel lblNewLabel = new JLabel("Ranking general de todos los niveles de " + nombreUsuario);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.addActionListener(new Accion1());
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		String resultado = GestorRanking.getInstancia().obtenerRankingTodosNivelPersonal(nombreUsuario);
		
		String[] array = resultado.split("}");
		String[] puntos = new String [array.length-1];
		String[] nivel = new String [array.length-1];
		
		
		
		for (int i = 0; i != array.length-1; i++) {
			int ind = 0;
			
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
			
			nivel[i] = array[i].charAt(ind)+"";
		}
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(puntos.length+1, 3, 10, 10));
		
		panel.add(new JLabel("Puntuación"));
		panel.add(new JLabel("Nivel"));
		
		for(int i=0; i < puntos.length; i++) {
			panel.add(new JLabel(puntos[i]));
			panel.add(new JLabel(nivel[i]));
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
