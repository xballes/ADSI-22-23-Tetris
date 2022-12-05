package vista;

import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MenuRankingPublico extends JFrame {

	private JPanel contentPane;
	private static MenuRankingPublico puntero;
	private String nombreUsuario;
	
	
	public static void visibilizar(String pNombreUsuario) {
		MenuRankingPublico.puntero = new MenuRankingPublico(pNombreUsuario);
		
		
	}

	public MenuRankingPublico(String pNombreUsuario) {
		
		nombreUsuario = pNombreUsuario;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(50, 50));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Menu Ranking General");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(5, 1, 0, 40));
		
		JButton btnNewButton = new JButton("Visualizar ranking de todos los niveles");
		btnNewButton.addActionListener(new Accion1());
		panel.add(btnNewButton);
		
		JButton btnVerRankingDel = new JButton("Ver ranking del nivel 1");
		btnVerRankingDel.addActionListener(new Accion2());
		panel.add(btnVerRankingDel);
		
		JButton btnNewButton_1 = new JButton("Ver ranking del nivel 2");
		btnNewButton_1.addActionListener(new Accion3());
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Ver ranking del nivel 3");
		btnNewButton_2.addActionListener(new Accion4());
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Volver");
		btnNewButton_3.addActionListener(new Accion5());
		panel.add(btnNewButton_3);
		
		super.setVisible(true);
		super.setResizable(false);
		
		
		contentPane.add(new JPanel(), BorderLayout.WEST);
		contentPane.add(new JPanel(), BorderLayout.SOUTH);
		contentPane.add(new JPanel(), BorderLayout.EAST);
		
		
	}
	
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			TablaTodosGeneral.visibilizar(nombreUsuario);
		}
	}
	
	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			TablaNivelGeneral.visibilizar(nombreUsuario, 1);
		}
	}
	
	private class Accion3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			TablaNivelGeneral.visibilizar(nombreUsuario, 2);
		}
	}
	
	
	private class Accion4 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			TablaNivelGeneral.visibilizar(nombreUsuario, 3);
		}
	}
	
	
	private class Accion5 implements ActionListener {

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
