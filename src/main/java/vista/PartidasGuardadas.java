package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controlador.Gestor;

@SuppressWarnings("serial")

public class PartidasGuardadas extends JFrame implements Ventana {
	
	private static PartidasGuardadas puntero;
	private JPanel contenido;
	private JPanel filas;
	private JLabel titulo;
	private JTextField campo;
	private JPanel par;
	private JPanel par2;
	private JButton[] botones;
	private String usuario;
	private JPanel contentPane;

	
	@Override
	public void redirigir() {
		PartidasGuardadas.visibilizar(this.usuario);
		
	}
	
	
	public static void visibilizar(String pUser) {
		PartidasGuardadas.puntero = new PartidasGuardadas(pUser);
		
	}
	
	private PartidasGuardadas (String pUser) {
		
		this.usuario = pUser;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(50, 50));
		setContentPane(contentPane);
		
		this.contenido = new JPanel();
		super.setContentPane(this.contenido);
		this.contenido.setLayout(new BorderLayout(50,50));
		
		
		// Crear titulo
		
		this.titulo = new JLabel("Mostrar partidas Guardadas", SwingConstants.CENTER);
		this.titulo.setFont(new Font(Font.SANS_SERIF, 1, 30));
		this.contenido.add(this.titulo, BorderLayout.NORTH);
		
		// Crear estructura para botones
		
		this.filas = new JPanel();
		this.filas.setLayout(new GridLayout(1, 1, 0 ,70));
		
		
		this.campo = new JTextField();
		this.par2 = new JPanel();
		this.par2.setLayout(new GridLayout(1,1,50,0));
		this.filas.add(this.par2);

		
		
		this.par = new JPanel();
		this.par.setLayout(new GridLayout(1,2,10,10));
		this.botones = new JButton[1];
		
		for (int i = 0; i != 1; i++) {
			String val = null;
			switch (i) {
			case 0: val = "Volver"; break;	
			}
			
			this.botones[i] = new JButton(val);
			this.par.add(this.botones[i]);
		}
		this.botones[0].addActionListener(new Accion1());

		this.filas.add(this.par); 
		this.contenido.add(new JPanel(), BorderLayout.SOUTH);
		JButton botonVolver= new JButton("Volver");
		botonVolver.addActionListener(new Accion1());
		this.contenido.add(botonVolver,BorderLayout.SOUTH);
		this.contenido.add(new JPanel(), BorderLayout.EAST);
		this.contenido.add(new JPanel(), BorderLayout.WEST);
		super.setBounds(200, 100, 700, 500);
		this.setResizable(false);
		this.setVisible(true);
		String partidas=Gestor.getInstancia().mostrarPartidas(usuario);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		String[]p=partidas.split("}");
		int i=0;
		String[]puntuacion=new String[p.length-1];
		String[]nivel=new String[p.length-1];
		String[]fecha=new String[p.length-1];
		String aux;
		i = 0;
		while (i <= p.length-2) {
			int j = 0;
			while (p[i].charAt(j) != ':'){j++;}
			j++;
			aux = "";
			while (p[i].charAt(j) != ',') {
				aux = aux + p[i].charAt(j);
				j++;

			}
			puntuacion[i] = aux;

			while (p[i].charAt(j) != ':'){j++;}
			j++;
			
			aux = "";
			while (p[i].charAt(j) != ',') {
				aux = aux + p[i].charAt(j);
				j++;

			}
			nivel[i] = aux;

			aux = "";

			while (p[i].charAt(j) != ':'){j++;}
			j = j + 2;
			
			while (p[i].charAt(j) != '"') {
				aux = aux + p[i].charAt(j);
				j++;

			}
			
			fecha[i] = aux;

			i++;

		}
		panel.setLayout(new GridLayout(puntuacion.length+1, 4, 7, 10)); //
		panel.add(new JLabel("Puntuación"));
		panel.add(new JLabel("Nivel"));
		panel.add(new JLabel("Fecha de la Partida"));
		panel.add(new JLabel("Botones"));
		for(int z=0; z < puntuacion.length; z++) {
			panel.add(new JLabel(puntuacion[z]));
			//System.out.println(puntuacion[z]);
			panel.add(new JLabel(nivel[z]));
			//System.out.println(nivel[z]);
			panel.add(new JLabel(fecha[z]));
			//System.out.println(fecha[z]);
			panel.add(new JButton("Consultar Partida"));
		}
		System.out.println(partidas);
		this.contenido.add(panel, BorderLayout.CENTER);
		System.out.println(fecha[0]);
	}
	
	
	private class Accion1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuDeUsuario.visibilizar(usuario);
			}	
	}	
}
	