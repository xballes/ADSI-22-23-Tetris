package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Personalizar extends JFrame {

	private JPanel contentPane;
	private String nombreUsuario;
	private static Personalizar puntero;

	public static void visibilizar(String pNombre) {
		Personalizar.puntero = new Personalizar(pNombre);
	}
	
	private Personalizar(String pNombre) {
		
		nombreUsuario = pNombre;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Menu de personalización");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 1, 10, 10));
		
		JButton btnNewButton = new JButton("Editar Color");
		btnNewButton.addActionListener(new Accion1());
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Editar Ladrillos");
		btnNewButton_1.addActionListener(new Accion2());
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Cambiar Sonido");
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Salir");
		btnNewButton_3.addActionListener(new Accion4());
		panel.add(btnNewButton_3);
		
		super.setResizable(false);
		super.setVisible(true);
	}

	private class Accion4 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuDeUsuario.visibilizar(nombreUsuario);
		}
	}
	
	private class Accion3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuDeUsuario.visibilizar(nombreUsuario);
		}
	}
	
	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			EditarLadrillos.visibilizar(nombreUsuario);
		}
	}
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			EditarColor.visibilizar(nombreUsuario);
		}
	}
	
	
	
}
