package vista;

import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MenuRankingPublico extends JFrame {

	private JPanel contentPane;


	public MenuRankingPublico() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Menu Ranking General");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(5, 1, 0, 10));
		
		JButton btnNewButton = new JButton("Visualizar ranking de todos los niveles");
		panel.add(btnNewButton);
		
		JButton btnVerRankingDel = new JButton("Ver ranking del nivel 1");
		panel.add(btnVerRankingDel);
		
		JButton btnNewButton_1 = new JButton("Ver ranking del nivel 2");
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Ver ranking del nivel 3");
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Volver");
		panel.add(btnNewButton_3);
		
		
		//prueba prueb
	}

}
