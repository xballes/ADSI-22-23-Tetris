package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.Gestor;

@SuppressWarnings("serial")

public class DetallesPremio extends JFrame
{
	
	private static DetallesPremio puntero;
	private JPanel contenido;
	private JLabel titulo;
	
	private JPanel[] pares;
	private JTextField[] campos;
	private JButton boton;
	
	private String usuario;

	
	
	public void redirigir() {
		DetallesPremio.visibilizar();
		
	}
	
	public static void visibilizar(String pNombre) {
		DetallesPremio.puntero = new DetallesPremio(pNombre);
		
	}
	
	private DetallesPremio (String pNombre) {
		// Crear panel principal
		
		this.contenido = new JPanel();
		super.setContentPane(this.contenido);
		contenido.setLayout(null);
		
		// Crear titulo
		
		this.titulo = new JLabel("premio", SwingConstants.CENTER);
		titulo.setBounds(0, 0, 700, 36);
		this.titulo.setFont(new Font(Font.SANS_SERIF, 1, 30));
		this.contenido.add(this.titulo);
		
		//Crear contenido 
		
		//Obtener premios del usuario
		
		String premios = Gestor.getInstancia().obtenerPremios(pNombre);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 553, 700, 10);
		this.contenido.add(panel);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(690, 86, 10, 417);
		this.contenido.add(panel_1);
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 86, 10, 417);
		this.contenido.add(panel_2);
		
		//Crear etiquetas y botones para cada premio
		
		JLabel fecha = new JLabel("New label");
		fecha.setBounds(70, 118, 70, 15);
		contenido.add(fecha);
		
		JButton botonVolver = new JButton("Volver");
		botonVolver.setBounds(518, 460, 117, 25);
		contenido.add(botonVolver);
		
		JLabel descr = new JLabel("New label");
		descr.setBounds(70, 191, 70, 15);
		contenido.add(descr);

		super.setBounds(100, 100, 700, 600);
		this.setResizable(false);
		this.setVisible(true);
		
		
	}
	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			DetallesPremio.visibilizar(usuario);
		}
		
		
	}
}
	
	

