package vista;

import java.awt.EventQueue;

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

public class EditarColor extends JFrame implements Ventana {

	private JPanel contentPane;
	
	
	private String usuario;
	private static EditarColor puntero;
	
	
	@Override
	public void redirigir() {
		EditarColor.visibilizar(usuario);
		
	}

	public static void visibilizar(String pNombre) {
		EditarColor.puntero = new EditarColor(pNombre);
	}

	private EditarColor(String pNombre) {
		
		this.usuario = pNombre;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Editar Color");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Guardar Cambios");
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 4, 0, 0));
		
		JButton lblNewLabel_2 = new JButton();
		lblNewLabel_2.setBackground(Color.white);
		lblNewLabel_2.addActionListener(new Accion1());
		panel.add(lblNewLabel_2);
		
		
	/*	JButton lblNewLabel_3 = new JLabel();
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel();
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_1 = new JLabel(); 
		panel.add(lblNewLabel_1); */
		
		super.setResizable(false);
		super.setVisible(true);
		
	}
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Gestor.getInstancia().cambiarColor(usuario, 0);
			puntero.dispose();
			PopUp.visibilizar("Se ha adjudicado el color blanco", puntero);
		}
	}



}