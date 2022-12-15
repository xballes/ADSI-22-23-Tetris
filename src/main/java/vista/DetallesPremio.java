package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.*;

import controlador.Gestor;
import controlador.SGBD;

@SuppressWarnings("serial")

public class DetallesPremio extends JFrame
{
	
	private static DetallesPremio puntero;
	private JPanel contenido;
	private JLabel titulo;
	private JButton botonVolver;
	
	private String usuario;

	
	
	public void redirigir(String pNombre, String pPremio) {
		DetallesPremio.visibilizar(pNombre, pPremio);
		
	}
	
	public static void visibilizar(String pNombreUsu, String pPremio) {
		DetallesPremio.puntero = new DetallesPremio(pNombreUsu, pPremio);
		
	}
	
	private DetallesPremio (String pNombreUsu, String pPremio) {
		
		usuario = pNombreUsu;
		// Crear panel principal
		
		this.contenido = new JPanel();
		super.setContentPane(this.contenido);
		contenido.setLayout(null);
		
		// Crear titulo
		
		this.titulo = new JLabel(pPremio, SwingConstants.CENTER);
		titulo.setBounds(0, 0, 700, 36);
		this.titulo.setFont(new Font(Font.SANS_SERIF, 1, 30));
		this.contenido.add(this.titulo);
		
		//Crear contenido 
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 553, 700, 10);
		this.contenido.add(panel);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(690, 86, 10, 417);
		this.contenido.add(panel_1);
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 86, 10, 417);
		this.contenido.add(panel_2);
		
		//Obtener detalles del premio
		
		String detalles = Gestor.getInstancia().obtenerDetallesPremio(pNombreUsu, pPremio);
		
		String[] array = detalles.split("{");
		String desc = " ";
		String fech = " ";
		
		for (int i = 0; i != array.length; i++) {
			int ind = 0;
			
			while(array[i].charAt(ind)!=':') {
				ind++;
			}
			String aux = "";
			ind++;
			
			while(array[i].charAt(ind)!=',') {
				aux = aux + array[i].charAt(ind);
				ind++;
			}
			
			desc = aux;
			
			while(array[i].charAt(ind)!=':') {
				ind++;
			}
			String aux2 = "";
			ind++;
			
			while(array[i].charAt(ind)!='}') {
				aux2 = aux2 + array[i].charAt(ind);
				ind++;
			}
			
			fech = aux2;
			
		}

		JLabel descr = new JLabel(desc);
		descr.setBounds(70, 118, 70, 15);
		contenido.add(descr);
		
		JLabel fecha = new JLabel("Fecha de obtención: "+ fech);
		fecha.setBounds(70, 191, 70, 15);
		contenido.add(fecha);
		
		JButton botonVolver = new JButton("Volver");
		botonVolver.setBounds(518, 460, 117, 25);
		contenido.add(botonVolver);

		super.setBounds(100, 100, 700, 600);
		this.setResizable(false);
		this.setVisible(true);
		
		this.botonVolver.addActionListener(new Accion1());
		
	}
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			ConsultarPremios.visibilizar(usuario);
		}
		
		
	}
}
	
	

