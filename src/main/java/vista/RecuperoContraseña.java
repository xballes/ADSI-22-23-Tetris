package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.Gestor;

@SuppressWarnings("serial")

public class RecuperoContraseña extends JFrame implements Ventana {
	
	// Pantalla que permite introducir el email para recuperar la contraseña
	
	private static RecuperoContraseña puntero;
	private JPanel contenido;
	private JPanel filas;
	private JLabel titulo;
	
	private JTextField campo;
	
	
	private JPanel par;
	private JPanel par2;

	private JButton[] botones; // Registrar, Volver (en ese orden)

	
	
	@Override
	public void redirigir(boolean pInfo) {
		
		if (pInfo) {
			MenuPrincipal.visibilizar();
		} else {
			RecuperoContraseña.visibilizar();

		}
		
	}
	
	public static void visibilizar() {
		RecuperoContraseña.puntero = new RecuperoContraseña();
		
	}
	
	private RecuperoContraseña () {
		// Crear panel principal
		
		this.contenido = new JPanel();
		super.setContentPane(this.contenido);
		this.contenido.setLayout(new BorderLayout(50,50));
		
		// Crear titulo
		
		this.titulo = new JLabel("Recupero Contraseña", SwingConstants.CENTER);
		this.titulo.setFont(new Font(Font.SANS_SERIF, 1, 30));
		this.contenido.add(this.titulo, BorderLayout.NORTH);
		
		// Crear estructura para botones
		
		this.filas = new JPanel();
		this.filas.setLayout(new GridLayout(2, 1, 0 ,70));
		
		
		this.campo = new JTextField();
		this.par2 = new JPanel();
		this.par2.setLayout(new GridLayout(2,1,50,0));

			

			
		this.par2.add(new JLabel("Tu E-mail"));
		this.par2.add(this.campo);
			
			


		this.filas.add(this.par2);

		
		
		this.par = new JPanel();
		this.par.setLayout(new GridLayout(1,2,10,10));
		this.botones = new JButton[2];
		
		for (int i = 0; i != 2; i++) {
			String val = null;
			switch (i) {
			case 0: val = "Recuperar Contraseña"; break;
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

		super.setBounds(100, 100, 700, 500);
		this.setResizable(false);
		this.setVisible(true);
		
		
	}
	
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String cont = Gestor.getInstancia().obtContraseña(campo.getText());
			puntero.dispose();
			
			if (cont == null) {
				PopUp.visibilizar("Error: Email incorrecto", puntero, false);
				
			} else {
				PopUp.visibilizar("¡Éxito! Tu contraseña es: "+cont, puntero, true);

				
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
	