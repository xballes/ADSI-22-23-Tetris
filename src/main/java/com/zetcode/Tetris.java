package com.zetcode;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
Java Tetris game clone

Author: Jan Bodnar
Website: https://zetcode.com
 */
public class Tetris extends JFrame {
	
	private static final Logger logger = LogManager.getLogger(Tetris.class);

    private JLabel statusbar; // Puntuación

    public Tetris() {

        initUI();
    }

    private void initUI() {
    	
    	// Inicializar la ventana de juego

        statusbar = new JLabel(" 0");
        add(statusbar, BorderLayout.SOUTH);

        var board = new Board(this,"NombreUsuario");
        add(board);
        board.start();

        setTitle("Tetris");
        setSize(200, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    JLabel getStatusBar() {

        return statusbar;
    }

    public static void main(String[] args) {

    	logger.info("Playing");
        EventQueue.invokeLater(() -> {

        	
        	// EL JUEGO INICIA AQUI, LA AUTENTIFICACION DEL USUARIO DEBE VENIR ANTES DE ESTO
            var game = new Tetris();
            game.setVisible(true);
            
            
        });
    }
}