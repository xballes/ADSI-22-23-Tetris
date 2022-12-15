package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.Gestor;
import vista.MenuDeUsuario.Accion1;

@SuppressWarnings("serial")

public class ConsultarPremios extends JFrame
{
	
	
	private static ConsultarPremios puntero;
	private JPanel contenido;
	private JLabel titulo;
	private JButton[] botones;
	private JButton botonVolver;
	
	private String usuario;

	
	
	public void redirigir() {
		ConsultarPremios.visibilizar(usuario);
		
	}
	
	public static void visibilizar(String pNombre) {
		ConsultarPremios.puntero = new ConsultarPremios(pNombre);
		
	}
	
	private ConsultarPremios (String pNombre) {
		// Crear panel principal
		
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
		
		String datos = Gestor.getInstancia().obtenerPremios(pNombre);
		
		String[] array = datos.split("{");
		String[] premios = new String [array.length];
		
		for (int i = 0; i != array.length; i++) {
			int ind = 0;
			
			while(array[i].charAt(ind)!=':') {
				ind++;
			}
			String aux = "";
			ind++;
			
			while(array[i].charAt(ind)!='}') {
				aux = aux + array[i].charAt(ind);
				ind++;
			}
			
			premios[i] =aux;
		}
		
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
		int pos=0;
		for(int i = 0; i != premios.length; i++)
		{
			pos = pos+100;
			JLabel premio = new JLabel(premios[i]);
			premio.setBounds(70, pos, 70, 15);
			contenido.add(premio);
			
			JButton detalles = new JButton("Detalles");
			detalles.setBounds(336, pos, 117, 25);
			contenido.add(detalles);
			botones[i].add(detalles);
		}
		
		JButton botonVolver = new JButton("Volver");
		botonVolver.setBounds(518, 460, 117, 25);
		contenido.add(botonVolver);

		super.setBounds(100, 100, 700, 600);
		this.setResizable(false);
		this.setVisible(true);
		
		//Añadir actionlisteners a cada boton
		this.botonVolver.addActionListener(new Accion1());
		for(int i=0; i!=botones.length; i++)
		{
			botones[i].addActionListener(new Accion2());
		}
		
	}
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuDeUsuario.visibilizar(usuario);
		}	
		
	}
	
	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			DetallesPremio.visibilizar(usuario, pNombrePremio);
		}
		
	}
}
	
	

