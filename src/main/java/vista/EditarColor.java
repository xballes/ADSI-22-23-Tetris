package vista;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Gestor;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class EditarColor extends JFrame implements Ventana {

	
	// Menu que se abre para elegir que música se desearía que sonase de fondo al jugar

	
	private JPanel contentPane;
	
	
	private String usuario;
	private static EditarColor puntero;
	
	
	@Override
	public void redirigir(boolean pInfo) {
		Personalizar.visibilizar(usuario);
		
	}

	public static void visibilizar(String pNombre) {
		EditarColor.puntero = new EditarColor(pNombre);
	}

	private EditarColor(String pNombre) {
		
		this.usuario = pNombre;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(15, 15));
		
		JLabel lblNewLabel = new JLabel("Editar Color");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Volver");
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		btnNewButton.addActionListener(new Accion5());
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 4, 0, 0));
		
		JButton lblNewLabel_2 = new JButton();
		lblNewLabel_2.setBackground(Color.white);
		lblNewLabel_2.addActionListener(new Accion1());
		panel.add(lblNewLabel_2);
		
		
		JButton lblNewLabel_3 = new JButton();
		lblNewLabel_3.setBackground(Color.green);
		lblNewLabel_3.addActionListener(new Accion2());
		panel.add(lblNewLabel_3);
		
		
		JButton lblNewLabel_4 = new JButton();
		lblNewLabel_4.setBackground(Color.yellow);
		lblNewLabel_4.addActionListener(new Accion3());
		panel.add(lblNewLabel_4);
		
		JButton lblNewLabel_1 = new JButton();
		lblNewLabel_1.setBackground(Color.pink);
		lblNewLabel_1.addActionListener(new Accion4());
		panel.add(lblNewLabel_1);
		
		this.contentPane.add(new JPanel(), BorderLayout.WEST);
		this.contentPane.add(new JPanel(), BorderLayout.EAST);
		super.setResizable(false);
		super.setVisible(true);
		
	}
	
	// Acciones: 1-4 adjudicar fondo correspondiente, 5 volver
	
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Gestor.getInstancia().cambiarColor(usuario, 0);
			puntero.dispose();
			PopUp.visibilizar("Se ha adjudicado el color blanco", puntero, true);
		}
	}

	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Gestor.getInstancia().cambiarColor(usuario, 1);
			puntero.dispose();
			PopUp.visibilizar("Se ha adjudicado el color verde", puntero, true);
		}
	}

	private class Accion3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Gestor.getInstancia().cambiarColor(usuario, 2);
			puntero.dispose();
			PopUp.visibilizar("Se ha adjudicado el color amarillo", puntero, true);
		}
	}

	private class Accion4 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Gestor.getInstancia().cambiarColor(usuario, 3);
			puntero.dispose();
			PopUp.visibilizar("Se ha adjudicado el color rosa", puntero, true);
		}
	}

	private class Accion5 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			Personalizar.visibilizar(usuario);
		}
	}


}