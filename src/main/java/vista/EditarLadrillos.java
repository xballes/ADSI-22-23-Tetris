package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
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

public class EditarLadrillos extends JFrame implements Ventana {

	private JPanel contentPane;
	private String nombreUsuario;
	private static EditarLadrillos puntero;
	
	@Override
	public void redirigir() {
		EditarLadrillos.visibilizar(nombreUsuario);
		
	}

	public static void visibilizar(String pNombre) {
		EditarLadrillos.puntero = new EditarLadrillos(pNombre);
	}
	private EditarLadrillos(String nombreusuartio) {
		this.nombreUsuario= nombreusuartio;
		Color[][] matrizLadrillos = new Color[5][7];
		//CASE 0
		matrizLadrillos[1][0]= new Color(211,142,220);
		matrizLadrillos[0][0] = new Color(204, 102, 102);
    	matrizLadrillos[0][1] = new Color(102, 204, 102);
    	matrizLadrillos[0][2] = new Color(102, 102, 204);
    	matrizLadrillos[0][3] = new Color(204, 204, 102);
    	matrizLadrillos[0][4] = new Color(204, 102, 204);
    	matrizLadrillos[0][5] = new Color(102, 204, 204);
    	matrizLadrillos[0][6] = new Color(218, 170, 0);
    	
    	
    	matrizLadrillos[1][0] = new Color(204, 102, 102);
    	matrizLadrillos[1][1] = new Color(102, 204, 102);
    	matrizLadrillos[1][2] = new Color(102, 102, 204);
    	matrizLadrillos[1][3] = new Color(204, 204, 102);
    	matrizLadrillos[1][4] = new Color(204, 102, 204);
    	matrizLadrillos[1][5] = new Color(102, 204, 204);
    	matrizLadrillos[1][6] = new Color(218, 170, 0);
    	
    	
    	matrizLadrillos[2][0] = new Color(204, 102, 102);
    	matrizLadrillos[2][1] = new Color(102, 204, 102);
    	matrizLadrillos[2][2] = new Color(102, 102, 204);
    	matrizLadrillos[2][3] = new Color(204, 204, 102);
    	matrizLadrillos[2][4] = new Color(204, 102, 204);
    	matrizLadrillos[2][5] = new Color(102, 204, 204);
    	matrizLadrillos[2][6] = new Color(218, 170, 0);
    	
    	matrizLadrillos[3][0] = new Color(204, 102, 102);
    	matrizLadrillos[3][1] = new Color(102, 204, 102);
    	matrizLadrillos[3][2] = new Color(102, 102, 204);
    	matrizLadrillos[3][3] = new Color(204, 204, 102);
    	matrizLadrillos[3][4] = new Color(204, 102, 204);
    	matrizLadrillos[3][5] = new Color(102, 204, 204);
    	matrizLadrillos[3][6] = new Color(218, 170, 0);
    	
    	matrizLadrillos[4][0] = new Color(204, 102, 102);
    	matrizLadrillos[4][1] = new Color(102, 204, 102);
    	matrizLadrillos[4][2] = new Color(102, 102, 204);
    	matrizLadrillos[4][3] = new Color(204, 204, 102);
    	matrizLadrillos[4][4] = new Color(204, 102, 204);
    	matrizLadrillos[4][5] = new Color(102, 204, 204);
    	matrizLadrillos[4][6] = new Color(218, 170, 0);
		
    	
    	
    	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(20, 20));
		
		JLabel lblNewLabel = new JLabel("Editar Ladrillo");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Guardar Cambios");
		btnNewButton.addActionListener(new Accion2());
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(6, 8, 10, 10));
		panel.add(new JLabel(" Z",SwingConstants.CENTER));
		panel.add(new JLabel(" S",SwingConstants.CENTER));
		panel.add(new JLabel("-", SwingConstants.CENTER));
		panel.add(new JLabel(" T", SwingConstants.CENTER));
		panel.add(new JLabel(" ❏",SwingConstants.CENTER));
		panel.add(new JLabel(" L",SwingConstants.CENTER));
		panel.add(new JLabel("L INV",SwingConstants.CENTER));
		panel.add(new JLabel("BTN",SwingConstants.CENTER));
		for (int i=0; i<5; i++) {
			for(int j=0; j<7; j++){
				JLabel color = new JLabel();
				color.setBackground(matrizLadrillos[i][j]);
				color.setOpaque(true);
				panel.add(color);
			}
			
			Boton boton = new Boton(i,"OK");
			boton.addActionListener(new Accion1());
			panel.add(boton);
		}
		this.contentPane.add(new JPanel(), BorderLayout.WEST);
		this.contentPane.add(new JPanel(), BorderLayout.EAST);
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
            Gestor.getInstancia().editarLadrillos(nombreUsuario, id);
            puntero.dispose();
            PopUp.visibilizar("Se ha adjudicado el SET de colores", puntero);


        }
    }
	private class Accion2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			Personalizar.visibilizar(nombreUsuario);
		}
	}
	
	
}
