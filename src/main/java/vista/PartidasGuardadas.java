package vista;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.zetcode.Tetris;

import controlador.Gestor;

@SuppressWarnings("serial")

public class PartidasGuardadas extends JFrame implements Ventana {
	
	// Pantalla que se abre cuando un usuario decide cargar una partida
	// Muestra todos los saves que tiene, y le da la opcion de elegir uno de ellos para jugar.
	
	private static PartidasGuardadas puntero;
	private JPanel contenido;
	private JPanel filas;
	private JLabel titulo;
	private JPanel par;
	private JPanel par2;
	private JButton[] botones;
	private String []puntos;
	private String []niveles;
	private String []fechas;
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
		super.setBounds(100, 100, 700, 500);
		this.setResizable(false);
		this.setVisible(true);
		String partidas=Gestor.getInstancia().mostrarPartidas(usuario);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		String[]p=partidas.split("}");
		int i=0;
		puntos=new String[p.length-1];
		niveles=new String[p.length-1];
		fechas=new String[p.length-1];
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
			puntos[i] = aux;

			while (p[i].charAt(j) != ':'){j++;}
			j++;
			
			aux = "";
			while (p[i].charAt(j) != ',') {
				aux = aux + p[i].charAt(j);
				j++;

			}
			niveles[i] = aux;

			aux = "";

			while (p[i].charAt(j) != ':'){j++;}
			j = j + 2;
			
			while (p[i].charAt(j) != '"') {
				aux = aux + p[i].charAt(j);
				j++;

			}
			
			fechas[i] = aux;

			i++;

		}
		panel.setLayout(new GridLayout(puntos.length+1, 4, 5, 10)); //
		panel.add(new JLabel("Puntuación"));
		panel.add(new JLabel("Nivel"));
		panel.add(new JLabel("Fecha de la Partida"));
		panel.add(new JLabel("Botones"));
		Boton[]botonesPartida=new Boton[puntos.length];
		for(int z=0; z < puntos.length; z++) {
			panel.add(new JLabel(puntos[z]));
			panel.add(new JLabel(niveles[z]));
			panel.add(new JLabel(fechas[z]));
			botonesPartida[z]=new Boton(z,"Cargar Partida "+(z+1));
			panel.add(botonesPartida[z]);
			botonesPartida[z].addActionListener(new Accion2());
			
		}
		this.contenido.add(panel, BorderLayout.CENTER);
	}
	
	private class Accion1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuDeUsuario.visibilizar(usuario);
			
			}	
	}	
	
	private class Accion2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int id=(((Boton)e.getSource()).id);
			Gestor.getInstancia().cargarPartida(usuario, fechas[id], Integer.parseInt(niveles[id]));
			puntero.dispose();
			Tetris.getInstancia(usuario,Timestamp.valueOf(Gestor.getInstancia().mapearFecha(fechas[id])),Integer.parseInt(niveles[id]),Gestor.getInstancia().cargarPartida(usuario, fechas[id], Integer.parseInt(niveles[id])));
			
		}
	}
	private class Boton extends JButton{
		private int id;
		public Boton(int id,String string) {
			super(string);
			this.id = id;
		}
	}
}