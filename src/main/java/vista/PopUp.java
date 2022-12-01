package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")

public class PopUp extends JFrame {
	
	
	private static PopUp puntero;
	private JPanel contenido;
	private JPanel filas;
	
	private JButton boton;
	

	
	
	// Variables de IU
	
	private Ventana redireccion;

	
	public static void visibilizar(String pTexto, Ventana pRedireccion) {
		PopUp.puntero = new PopUp(pTexto, pRedireccion);
		
	}
	
	private PopUp (String pTexto, Ventana pRedireccion) {
		
		this.redireccion = pRedireccion;
		
		// Crear panel principal
		
		this.contenido = new JPanel();
		super.setContentPane(this.contenido);
		this.contenido.setLayout(new BorderLayout(50,50));
		
		// Crear titulo
		
	
		
		// Crear estructura para botones
		
		this.filas = new JPanel();
		this.filas.setLayout(new GridLayout(2, 1, 0 ,70));
		
		this.filas.add(new JLabel(pTexto));
		this.boton = new JButton("Aceptar");
		this.boton.addActionListener(new Accion1());
		this.filas.add(this.boton);
		
		this.contenido.add(this.filas, BorderLayout.CENTER);
		this.contenido.add(new JPanel(), BorderLayout.SOUTH);
		this.contenido.add(new JPanel(), BorderLayout.EAST);
		this.contenido.add(new JPanel(), BorderLayout.WEST);
		this.contenido.add(new JPanel(), BorderLayout.NORTH);

		super.setBounds(500, 100, 700, 500);
		this.setResizable(false);
		this.setVisible(true);
		
		
	}
	
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			redireccion.redirigir();
			
		}
		
		
	}

	
	
}