package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.Gestor;

@SuppressWarnings("serial")

public class Registro extends JFrame implements Ventana {
	
	private static Registro puntero;
	private JPanel contenido;
	private JPanel filas;
	private JLabel titulo;
	
	private JPanel[] pares;
	private JTextField[] campos;
	
	
	private JPanel par;
	private JButton[] botones; // Registrar, Volver (en ese orden)

	
	@Override
	public void redirigir() {
		Registro.visibilizar();		
	}
	
	public static void visibilizar() {
		Registro.puntero = new Registro();
		
	}
	
	private Registro () {
		// Crear panel principal
		
		this.contenido = new JPanel();
		super.setContentPane(this.contenido);
		this.contenido.setLayout(new BorderLayout(50,20));
		
		// Crear titulo
		
		this.titulo = new JLabel("Registro", SwingConstants.CENTER);
		this.titulo.setFont(new Font(Font.SANS_SERIF, 1, 30));
		this.contenido.add(this.titulo, BorderLayout.NORTH);
		
		// Crear estructura para botones
		
		this.filas = new JPanel();
		this.filas.setLayout(new GridLayout(4, 1, 0 ,70));
		
		this.pares = new JPanel[3];
		this.campos = new JTextField[3];
		
		for (int i = 0; i !=3; i++) {
			this.campos[i] = new JTextField();
			this.pares[i] = new JPanel();
			this.pares[i].setLayout(new GridLayout(1,2,50,20));

			String val = null;
			switch (i) {
			case 0: val = "                 Nombre de usuario"; break;
			case 1: val = "                 Contraseña"; break;
			case 2: val = "                 E-mail"; break;
	
			}
			
			this.pares[i].add(new JLabel(val));
						
			this.pares[i].add(this.campos[i]);
			

			this.filas.add(this.pares[i]);

		}
		
		this.par = new JPanel();
		this.par.setLayout(new GridLayout(1,2,10,10));
		
		this.botones = new JButton[2];
		
		for (int i = 0; i != 2; i++) {
			String val = null;
			switch (i) {
			case 0: val = "Registrar"; break;
			case 1: val = "Volver"; break;	
			}
			
			this.botones[i] = new JButton(val);
			this.par.add(this.botones[i]);
		}
		this.botones[0].addActionListener(new Accion1());
		this.botones[1].addActionListener(new Accion2());

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

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			switch (Gestor.getInstancia().registrar(campos[0].getText(), campos[1].getText(), campos[2].getText())) {
			case 0: PopUp.visibilizar("Se ha registrado el usuario", puntero); break;
			case 1: PopUp.visibilizar("Error: campos entre 1 y 30 chars", puntero); break;
			case 2: PopUp.visibilizar("Error: Nombre ya en uso", puntero); break;
			case 3: PopUp.visibilizar("Error: Email ya en uso", puntero); break;

			
			}
		}
		
		
	}
	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuPrincipal.visibilizar();
		}
		
		
	}

	
	
}
	
	

