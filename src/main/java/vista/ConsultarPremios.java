package vista;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.Gestor;

@SuppressWarnings("serial")

public class ConsultarPremios extends JFrame
{
	// Menu que se abre para ver los premios obtenidos del jugador

	
	private static ConsultarPremios puntero;
	private JPanel contenido;
	private JLabel titulo;
	private JButton[] botones;
	private JButton botonVolver;
	
	private String usuario;

	
	

	
	public static void visibilizar(String pNombre) {
		ConsultarPremios.puntero = new ConsultarPremios(pNombre);

	}
	
	private ConsultarPremios (String pNombre) {
		// Crear panel principal
		
		
		
		this.usuario = pNombre;
		
		this.contenido = new JPanel();
		super.setContentPane(this.contenido);
		contenido.setLayout(null);
		
		// Crear titulo
		
		this.titulo = new JLabel("PREMIOS", SwingConstants.CENTER);
		titulo.setBounds(0, 0, 700, 36);
		this.titulo.setFont(new Font(Font.SANS_SERIF, 1, 30));
		this.contenido.add(this.titulo);
		
		//Crear contenido 
		
		//Obtener premios del usuario
		
		String[] premios = Gestor.getInstancia().obtenerPremios(pNombre);
		
		

		this.botones = new JButton[premios.length];
		
		//Crear etiquetas y botones para cada premio
		
		
		
		
		int pos=0;
		for(int i = 0; i != premios.length; i++)
		{
			pos = pos+100;
			JLabel premio = new JLabel(premios[i]);
			premio.setBounds(70, pos, 300, 15);
			contenido.add(premio);
			
			JButton detalles = new JButton("Detalles");
			detalles.setBounds(500, pos, 117, 25);
			contenido.add(detalles);
			botones[i] = detalles;
		}
		
		botonVolver = new JButton("Volver");
		botonVolver.setBounds(290, 480, 117, 25);
		contenido.add(botonVolver);


		
		//Añadir actionlisteners a cada boton
		this.botonVolver.addActionListener(new Accion1());
		for(int i=0; i!=botones.length; i++)
		{
			if(premios[i].contentEquals("Gana 1 partida"))
			{
				botones[i].addActionListener(new Accion2());
			}
			else if(premios[i].contentEquals("Gana 10 partidas"))
			{
				botones[i].addActionListener(new Accion3());
			}
			else if(premios[i].contentEquals("Gana 25 partidas"))
			{
				botones[i].addActionListener(new Accion4());
			}
			else
			{
				botones[i].addActionListener(new Accion5());
			}
		}
		
		super.setBounds(100, 100, 700, 600);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	// Acciones: 1 volver, 2-5 mostrar premios respectivos (si un premio no fue obtenido, el boton con su accion correspondiente no es instanciado)
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuDeUsuario.visibilizar(usuario);
		}	
		
	}
	
	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			DetallesPremio.visibilizar(usuario, "Gana 1 partida");
		}
		
	}
	
	private class Accion3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			DetallesPremio.visibilizar(usuario, "Gana 10 partidas");
		}
		
	}
	
	private class Accion4 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			DetallesPremio.visibilizar(usuario, "Gana 25 partidas");
		}
		
	}
	
	private class Accion5 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			DetallesPremio.visibilizar(usuario, "Tetris!");
			
		}
		
	}
	
}