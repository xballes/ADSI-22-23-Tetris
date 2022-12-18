package vista;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controlador.Gestor;

@SuppressWarnings("serial")

public class DetallesPremio extends JFrame
{
	
	// Menu que se abre para ver los detalles concretos de un premio elegido del menú ConsultarPremios

	
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
		
		
		

		
		//Obtener detalles del premio
		
		String detalles = Gestor.getInstancia().obtenerDetallesPremio(pNombreUsu, pPremio);
		
		String desc = " ";
		String fech = " ";
		
			int ind = 0;
			
			while(detalles.charAt(ind)!=':') {
				ind++;
			}
			String aux = "";
			ind++;
			
			while(detalles.charAt(ind)!=',') {
				aux = aux + detalles.charAt(ind);
				ind++;
			}
			
			desc = aux;
			
			while(detalles.charAt(ind)!=':') {
				ind++;
			}
			String aux2 = "";
			ind++;
			
			while(detalles.charAt(ind)!='}') {
				aux2 = aux2 + detalles.charAt(ind);
				ind++;
			}
			
			fech = aux2;
			
		

		JLabel descr = new JLabel(desc);
		descr.setFont(new Font(Font.SANS_SERIF, 1, 20));
		descr.setBounds(70, 118, 600, 100);
		contenido.add(descr);
		
		JLabel fecha = new JLabel("Fecha de obtención: "+ fech);
		fecha.setFont(new Font(Font.SANS_SERIF, 1, 20));
		fecha.setBounds(70, 291, 600, 15);
		contenido.add(fecha);
		
		botonVolver = new JButton("Volver");
		botonVolver.setBounds(300, 460, 117, 25);
		botonVolver.addActionListener(new Accion1());
		contenido.add(botonVolver);

		super.setBounds(100, 100, 700, 600);
		this.setResizable(false);
		this.setVisible(true);
		
		
	}
	
	// Accion: 1 volver
	
	private class Accion1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			ConsultarPremios.visibilizar(usuario);
		}
		
		
	}
}
	