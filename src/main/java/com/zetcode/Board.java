package com.zetcode;

import com.zetcode.Shape.Tetrominoe;

import vista.PausaGuardado;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel {
    private final int BOARD_WIDTH = 10;   // Cuadrados de largo
    private final int BOARD_HEIGHT = 22;  // Cuadrados de alto
    private final int PERIOD_INTERVAL = 300;
    private Timer timer;
    private boolean isFallingFinished = false;
    private boolean isPaused = false;     // Juego en pausa si true, despausa si false
    private int numLinesRemoved = 0;      // Lineas eliminadas
    private int curX = 0;
    private int curY = 0;
    private JLabel statusbar;             // Imagen del numero de puntuacion de abajo en la pantalla
    private Shape curPiece;               // Puntero a la pieza actual en movimiento
    private Tetrominoe[] board;           // Lista de las piezas en pantalla??
    
    
    private Tetris puntero;
    private String nombreUsuario;

    public Board(Tetris parent,String nombreUsuario) {
        initBoard(parent,nombreUsuario);
    }

    private void initBoard(Tetris parent,String nombreUsuario) {

        setFocusable(true); // Dar focus para poder meter inputs sin tener que pinchar en la ventana
        statusbar = parent.getStatusBar(); // Pointer al label de los puntos de la clase Tetris
        addKeyListener(new TAdapter());  // KeyListener, la clase privada, que maneja que acciones se hacen tras hacer ciertos inputs.
        puntero=parent;
    }

    private int squareWidth() {

        return (int) getSize().getWidth() / BOARD_WIDTH; // Cuantos pixeles ocupa de largo (obtiene las dimensiones del panel, y luego coge la anchura)
    }

    private int squareHeight() {

        return (int) getSize().getHeight() / BOARD_HEIGHT; // Cuantos pixeles ocupa de largo (obtiene las dimensiones del panel, y luego coge la altura)
    }

    private Tetrominoe shapeAt(int x, int y) {

        return board[(y * BOARD_WIDTH) + x];
    }

    void start() {

        curPiece = new Shape();
        board = new Tetrominoe[BOARD_WIDTH * BOARD_HEIGHT];

        clearBoard();
        newPiece();

        timer = new Timer(PERIOD_INTERVAL, new GameCycle());
        timer.start();
    }

    private void pause() {
    
        isPaused = true; // Si pausa, despausa. Si jugando, pausa
        puntero.setVisible(false);
        PausaGuardado.visibilizar(this);
        repaint();
    }
    public void despausar() {
    	isPaused = false;
    	 puntero.setVisible(true);
    	 repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g); // a paintComponent le ha metido un metodo extra
    }

    private void doDrawing(Graphics g) {

        var size = getSize();
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();

        for (int i = 0; i < BOARD_HEIGHT; i++) {

            for (int j = 0; j < BOARD_WIDTH; j++) {

                Tetrominoe shape = shapeAt(j, BOARD_HEIGHT - i - 1);

                if (shape != Tetrominoe.NoShape) {

                    drawSquare(g, j * squareWidth(),
                            boardTop + i * squareHeight(), shape);
                }
            }
        }

        if (curPiece.getShape() != Tetrominoe.NoShape) {

            for (int i = 0; i < 4; i++) {

                int x = curX + curPiece.x(i);
                int y = curY - curPiece.y(i);

                drawSquare(g, x * squareWidth(),
                        boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(),
                        curPiece.getShape());
            }
        }
    }

    private void dropDown() {

    	
    	// Bajar la pieza de golpe, sigue bajandola hasta que colapsa con otra
    	
        int newY = curY;

        while (newY > 0) {

            if (!tryMove(curPiece, curX, newY - 1)) {

                break;
            }

            newY--;
        }

        pieceDropped();
    }

    private void oneLineDown() {

        if (!tryMove(curPiece, curX, curY - 1)) {

            pieceDropped();
        }
    }

    private void clearBoard() {

        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {

            board[i] = Tetrominoe.NoShape;
        }
    }

    private void pieceDropped() {

        for (int i = 0; i < 4; i++) {

            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            board[(y * BOARD_WIDTH) + x] = curPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished) {

            newPiece();
        }
    }

    private void newPiece() {

        curPiece.setRandomShape();
        curX = BOARD_WIDTH / 2 + 1;
        curY = BOARD_HEIGHT - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {

            curPiece.setShape(Tetrominoe.NoShape);
            timer.stop();

            var msg = String.format("Game over. Score: %d", numLinesRemoved);
            statusbar.setText(msg);
        }
    }

    private boolean tryMove(Shape newPiece, int newX, int newY) {

        for (int i = 0; i < 4; i++) {

            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);

            if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {

                return false;
            }

            if (shapeAt(x, y) != Tetrominoe.NoShape) {

                return false;
            }
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;

        repaint();

        return true;
    }

    private void removeFullLines() {

        int numFullLines = 0;

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {

            boolean lineIsFull = true;

            for (int j = 0; j < BOARD_WIDTH; j++) {

                if (shapeAt(j, i) == Tetrominoe.NoShape) {

                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {

                numFullLines++;

                for (int k = i; k < BOARD_HEIGHT - 1; k++) {
                    for (int j = 0; j < BOARD_WIDTH; j++) {
                        board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {

            numLinesRemoved += numFullLines;

            statusbar.setText(String.valueOf(numLinesRemoved));
            isFallingFinished = true;
            curPiece.setShape(Tetrominoe.NoShape);
        }
    }

    private void drawSquare(Graphics g, int x, int y, Tetrominoe shape) {

        Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(102, 204, 102), new Color(102, 102, 204),
                new Color(204, 204, 102), new Color(204, 102, 204),
                new Color(102, 204, 204), new Color(218, 170, 0)
        };

        var color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }
    
    public int[][] calcularMatriz(){
    	int[][] matriz=new int[BOARD_HEIGHT][BOARD_WIDTH];
    	
    	for (int f = BOARD_HEIGHT-1; f != -1; f--) {
    		
    		for (int c = 0; c != BOARD_WIDTH; c++) {
    			
    			Tetrominoe p = this.shapeAt(c, f);
    			int val;
    	        if (p == Tetrominoe.NoShape) {val = 0;}
    	        else if (p == Tetrominoe.ZShape) {val = 1;}
    	        else if (p == Tetrominoe.SShape) {val = 2;}
    	        else if (p == Tetrominoe.LineShape) {val = 3;}
    	        else if (p == Tetrominoe.TShape) {val = 4;}
    	        else if (p == Tetrominoe.SquareShape) {val = 5;}
    	        else if (p == Tetrominoe.LShape) {val = 6;}
    	        else /* if p == Tetrominoe.LaPiezaQueFalta*/{val = 7;}
    	        matriz[f][c] = val;
    			
    		}
    	}
    	
    	for (int f = 0; f != BOARD_HEIGHT; f++) {
    		
    		for (int c = 0; c != BOARD_WIDTH; c++) {
    			
    			System.out.print(matriz[f][c]);
    			System.out.print(" ");
    			
    		}
    		System.out.println();
    	}
    	
    	
    	

    	return matriz;
    	
    }
    

    public int getBOARD_WIDTH() {
		return BOARD_WIDTH;
	}

	public int getBOARD_HEIGHT() {
		return BOARD_HEIGHT;
	}
	
	public int getNumLinesRemoved() {
		return numLinesRemoved;
	}
	
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private void doGameCycle() {

        update();
        repaint();
    }

    private void update() {

        if (isPaused) {

            return;
        }

        if (isFallingFinished) {

            isFallingFinished = false;
            newPiece();
        } else {

            oneLineDown();
        }
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

        	// Se pulsa algo en el teclado
        	
            if (curPiece.getShape() == Tetrominoe.NoShape) {
            	// Si se hace input sin pieza en mano, no se hace nada
                return;
            }

            int keycode = e.getKeyCode(); // Saber que tecla se pulso

            // Java 12 switch expressions
            switch (keycode) {

                case KeyEvent.VK_P -> pause();                                                  // La P es pausar/despausar
                case KeyEvent.VK_LEFT -> tryMove(curPiece, curX - 1, curY);                     // Izq es mover
                case KeyEvent.VK_RIGHT -> tryMove(curPiece, curX + 1, curY);                    // Dch es mover
                case KeyEvent.VK_DOWN -> tryMove(curPiece.rotateRight(), curX, curY);           // Abo es rotar
                case KeyEvent.VK_UP -> tryMove(curPiece.rotateLeft(), curX, curY);              // Arr es rotar
                case KeyEvent.VK_SPACE -> dropDown();                                           // Espacio es dejar caer
                case KeyEvent.VK_D -> oneLineDown();                                            // D es bajar una casilla
            }
        }
    }
}