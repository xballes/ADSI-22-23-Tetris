package com.zetcode;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Timestamp;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
Java Tetris game clone
Author: Jan Bodnar
Website: https://zetcode.com
 */
@SuppressWarnings("serial")
public class Tetris extends JFrame {
	
	private static final Logger logger = LogManager.getLogger(Tetris.class);

	private static Tetris puntero;
	
	private String nombreUsuario;
	private Timestamp fechaDeSave;
	
	
    private JLabel statusbar; // Puntuación

    
    
    private Tetris(String pUser, Timestamp fechaSave, int pNivel) {
    	this.nombreUsuario = pUser;
    	this.fechaDeSave = fechaSave;
        initUI(pNivel);
    }
    
    public static Tetris getInstancia(String pUser, Timestamp fechaSave, int pNivel) {
    	Tetris.puntero = new Tetris(pUser, fechaSave, pNivel);
    	return Tetris.puntero;
    }

    private void initUI(int pNivel) {
    	
    	// Inicializar la ventana de juego

        statusbar = new JLabel(" 0");
        add(statusbar, BorderLayout.SOUTH);

        var board = new Board(this);
        add(board);
        board.setDif(pNivel);
        board.start();

        setTitle("Tetris");
        setSize(200, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    
    public String getNombreUsuario() {
    	return this.nombreUsuario;
    }
    
    public Timestamp getFechaSave() {
    	return this.fechaDeSave;
    }

    JLabel getStatusBar() {

        return statusbar;
    }

  
}