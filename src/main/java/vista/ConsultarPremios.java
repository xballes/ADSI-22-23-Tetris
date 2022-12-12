package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.Gestor;

@SuppressWarnings("serial")

public class ConsultarPremios extends JFrame
{
	
	private static ConsultarPremios puntero;
	private JPanel contenido;
	private JPanel filas;
	private JLabel titulo;
	
	private JPanel[] pares;
	private JTextField[] campos;
	
	
	private JPanel par;
	private JButton boton;
	
	private String usuario;

	
	
	public void redirigir() {
		ConsultarPremios.visibilizar();
		
	}
	
	public static void visibilizar() {
		ConsultarPremios.puntero = new ConsultarPremios();
		
	}
	
	private ConsultarPremios () {
		// Crear panel principal
		
		this.contenido = new JPanel();
		super.setContentPane(this.contenido);
		this.contenido.setLayout(new BorderLayout(50,50));
		
		// Crear titulo
		
		this.titulo = new JLabel("PREMIOS", SwingConstants.CENTER);
		this.titulo.setFont(new Font(Font.SANS_SERIF, 1, 30));
		this.contenido.add(this.titulo, BorderLayout.NORTH);
		
		// Crear estructura para botones
		
		this.filas = new JPanel();
		this.filas.setLayout(new GridLayout(3, 1, 0 ,70));
		
		this.pares = new JPanel[3];
		this.campos = new JTextField[3];
		
		for (int i = 0; i !=2; i++) {
			this.campos[i] = new JTextField();
			this.pares[i] = new JPanel();
			this.pares[i].setLayout(new GridLayout(2,2,50,0));

			
			String val = null;
			switch (i) {
			case 0: val = "Nombre"; break;
			case 1: val = "Detalles"; break;
	
			}
			
			this.pares[i].add(new JLabel(val));
			this.pares[i].add(this.campos[i]);
			
			
			for (int j = 0; j != 2; j++) {this.pares[i].add(new JPanel());}


			this.filas.add(this.pares[i]);

		}
		
		this.par = new JPanel();
		this.par.setLayout(new GridLayout(1,2,10,10));
		this.boton = new JButton();
		
		String val = null;
		val = "Volver";
		this.boton = new JButton(val);
		this.par.add(this.boton);
		
		this.boton.addActionListener(new Accion1());

		this.filas.add(this.par);
		
		this.contenido.add(this.filas, BorderLayout.CENTER);
		this.contenido.add(new JPanel(), BorderLayout.SOUTH);
		this.contenido.add(new JPanel(), BorderLayout.EAST);
		this.contenido.add(new JPanel(), BorderLayout.WEST);

		super.setBounds(100, 100, 700, 600);
		this.setResizable(false);
		this.setVisible(true);
		
		
	}
	
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			puntero.dispose();
			MenuDeUsuario.visibilizar(usuario);
			
		}
		
		
	}
	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			//MOstrar ventana de detalles premio
		}
		
		
	}

	
	
}
	
	

