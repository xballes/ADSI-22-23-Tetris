package com.zetcode;

import java.util.Random;

public class Shape {

    public enum Tetrominoe { NoShape, ZShape, SShape, LineShape,
        TShape, SquareShape, LShape, MirroredLShape } // Listado de constantes

    private Tetrominoe pieceShape;
    private int coords[][];
    private int[][][] coordsTable;


    public Shape() {

        initShape();
    }

    private void initShape() {

        coords = new int[4][2];

        /* Para conocer la posici�n de una pieza esta clase NO almacena la posici�n de las 4 casillas de la pieza,
           en su lugar, coge como base una de las 4 piezas y luego muestra la distancia que hay entre las casillas de ellas.
           Dos variables en BOARD almacenan la posici�n de la casilla base, y a partir de ahi se puede calcular la posicion de las otras 3.
           Ejemplo: En la pieza que es una linea recta (LineShape) guarda como base la segunda casilla empezando por la izquierda y 
           a partir de ah� saca el resto de posiciones mediante {0, -1} {0, 0} {0, 1} {0, 2}.
          
         
          
          
         */
        
        coordsTable = new int[][][] {  
                { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
                { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
                { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
                { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
                { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
                { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
                { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
                { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
        };

        setShape(Tetrominoe.NoShape);
    }

    public void setShape(Tetrominoe shape) {

        for (int i = 0; i < 4 ; i++) {

            for (int j = 0; j < 2; ++j) {

                coords[i][j] = coordsTable[shape.ordinal()][i][j];
            }
        }

        pieceShape = shape;
    }

    public void setX(int index, int x) { coords[index][0] = x; }
    public void setY(int index, int y) { coords[index][1] = y; }
    public int x(int index) { return coords[index][0]; }
    public int y(int index) { return coords[index][1]; }
    public Tetrominoe getShape()  { return pieceShape; }

    public void setRandomShape() {

        var r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;

        Tetrominoe[] values = Tetrominoe.values();
        setShape(values[x]);
    }

    public int minX() {

        int m = coords[0][0];

        for (int i=0; i < 4; i++) {

            m = Math.min(m, coords[i][0]);
        }

        return m;
    }


    public int minY() {

        int m = coords[0][1];

        for (int i=0; i < 4; i++) {

            m = Math.min(m, coords[i][1]);
        }

        return m;
    }

    public Shape rotateLeft() {

        if (pieceShape == Tetrominoe.SquareShape) {

            return this;
        }

        var result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {

            result.setX(i, y(i));
            result.setY(i, -x(i));
        }

        return result;
    }

    public Shape rotateRight() {

        if (pieceShape == Tetrominoe.SquareShape) {

            return this;
        }

        var result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {

            result.setX(i, -y(i));
            result.setY(i, x(i));
        }

        return result;
    }
}
