package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.zetcode.Tetris;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class BotonNiveles extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JPanel panel_1;
	private JButton btnNewButton_3;
	private static BotonNiveles puntero;
	
	private String usuario;
	/**
	 * Launch the application.
	 */
	 
	

	/**
	 * Create the frame.
	 */
	private BotonNiveles(String usuario) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 588, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		contentPane.add(getPanel_1(), BorderLayout.SOUTH);
		super.setVisible(true);
		super.setResizable(false);
		this.usuario = usuario;
	}
	
	public static BotonNiveles visibilizar(String usuario) {
		BotonNiveles.puntero = new BotonNiveles(usuario);
		return BotonNiveles.puntero;
		
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0, 0, 0));
			panel.add(getBtnNewButton());
			panel.add(getBtnNewButton_1());
			panel.add(getBtnNewButton_2());
		}
		return panel;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Fácil");
			btnNewButton.addActionListener(new Accion1());
		}
		return btnNewButton;
	}
	private JButton getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JButton("Medio");
			btnNewButton_1.addActionListener(new Accion2());

		}
		return btnNewButton_1;
	}
	private JButton getBtnNewButton_2() {
		if (btnNewButton_2 == null) {
			btnNewButton_2 = new JButton("Difícil");
			btnNewButton_2.addActionListener(new Accion3());

		}
		return btnNewButton_2;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.add(getBtnNewButton_3());
		}
		return panel_1;
	}
	private JButton getBtnNewButton_3() {
		if (btnNewButton_3 == null) {
			btnNewButton_3 = new JButton("Volver");
			btnNewButton_3.addActionListener(new Accion4());

		}
		return btnNewButton_3;
	}
	
	
	private class Accion1 implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			Tetris.getInstancia(usuario, null, 1, null, 0);
		}
		
		
	}
	
	private class Accion2 implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			Tetris.getInstancia(usuario, null, 2, null, 0);
		}
		
		
	}
	
	private class Accion3 implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			Tetris.getInstancia(usuario, null, 3, null, 0);
		}
		
		
	}
	
	private class Accion4 implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			puntero.dispose();
			MenuDeUsuario.visibilizar(usuario);
		
			
		}
		
		
	}
}
