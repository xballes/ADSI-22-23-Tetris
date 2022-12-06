package vista;

import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.GestorRanking;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablaTodosGeneral extends JFrame {

	private JPanel contentPane;
	private static TablaTodosGeneral puntero;
	private String nombreUsuario;
	
	public static  void visibilizar(String pNombreUsuario) {
		TablaTodosGeneral.puntero = new TablaTodosGeneral(pNombreUsuario);		
		
	}
	
	private TablaTodosGeneral(String pNombreUsuario) {
		
		nombreUsuario = pNombreUsuario;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(50, 50));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Ranking general de todos los niveles");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.addActionListener(new Accion1());
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		
		
		String resul = GestorRanking.getInstancia().obtenerRankingTodosNivelesPublico();
		
		String[] array = resul.split("}");
		String[] puntos = new String [array.length-1];
		String[] nivel = new String [array.length-1];
		String[] nombre = new String [array.length-1];
		
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
		panel.setLayout(new GridLayout(puntos.length+1, 3, 10, 10));
		
		panel.add(new JLabel("Puntuación"));
		panel.add(new JLabel("Nivel"));
		panel.add(new JLabel("Nombre"));
		
		for(int i=0; i < puntos.length; i++) {
			panel.add(new JLabel(puntos[i]));
			panel.add(new JLabel(nivel[i]));
			panel.add(new JLabel(nombre[i]));
		}
		
		contentPane.add(new JPanel(), BorderLayout.WEST);
		
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
