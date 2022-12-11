package vista;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.Gestor;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CambiarSonido extends JFrame implements Ventana {
	
	private JPanel contentPane;
	private String nombreUsuario;
	private static CambiarSonido puntero;

	
	
	@Override
	public void redirigir() {
		puntero.dispose();
		CambiarSonido.visibilizar(nombreUsuario);
		
	}

	public static void visibilizar(String pNombre) {
		CambiarSonido.puntero = new CambiarSonido(pNombre);
	}


	private CambiarSonido(String pNombre) {
		this.nombreUsuario= pNombre;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(20, 20));
		
		JLabel lblNewLabel = new JLabel("Cambiar sonido");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Guardar Cambios");
		btnNewButton.addActionListener(new Accion2());
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 2, 0, 0));
		
		panel.add(new JLabel("Pista",SwingConstants.CENTER));
		panel.add(new JLabel("Elegir",SwingConstants.CENTER));
		panel.add(new JLabel("KorobeinikiKorobeiniki (ost original tetris)",SwingConstants.CENTER));
		Boton boton = new Boton(0,"Elegir");
		boton.addActionListener(new Accion1());
		panel.add(boton);
		
		
		panel.add(new JLabel("X",SwingConstants.CENTER));
		 boton = new Boton(1,"Elegir");
		boton.addActionListener(new Accion1());
		panel.add(boton);
		
		
		panel.add(new JLabel("Y",SwingConstants.CENTER));
		 boton = new Boton(2,"Elegir");
		boton.addActionListener(new Accion1());
		panel.add(boton);
		
		
		contentPane.add(new JPanel(), BorderLayout.EAST);
		contentPane.add(new JPanel(), BorderLayout.WEST);

		super.setResizable(false);
		super.setVisible(true);
	}

	
	private class Boton extends JButton{
        private int id;
        public Boton(int id,String string) {
            super(string);
            this.id = id;
        }
	}
	private class Accion1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int id=(((Boton)e.getSource()).id);
            Gestor.getInstancia().cambiarSonido(nombreUsuario, id);
            puntero.dispose();
            PopUp.visibilizar("Canción asignada", puntero);


        }
    }
	private class Accion2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            puntero.dispose();
            Personalizar.visibilizar(nombreUsuario);


        }
    }

}