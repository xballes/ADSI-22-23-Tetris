package eus.ehu.lsi.adsi;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.zetcode.Shape;
import com.zetcode.Shape.Tetrominoe;

public class ShapeTests {

	private static final Logger logger = LogManager.getLogger(ShapeTests.class);
	
	@Test
	public void testShape() {
		logger.info("Test Shape creation");
		Shape shape = new Shape();
		assertTrue(shape.minX() == 0);
	}

	@Test
	public void testSetShape() {
		logger.info("Test Shape creation 2");
		Shape shape = new Shape();
		shape.setShape(Tetrominoe.LShape);
		assertTrue(shape.getShape() == Tetrominoe.LShape);
		shape.setShape(Tetrominoe.SquareShape);
		assertTrue(shape.getShape() == Tetrominoe.SquareShape);
		shape.setShape(Tetrominoe.ZShape);
		assertTrue(shape.getShape() == Tetrominoe.ZShape);
	}

	@Test
	public void testX() {
		logger.info("Test Shape x");
		Shape shape = new Shape();
		shape.setX(0, 5);
		assertTrue(shape.x(0) == 5);
	}

	@Test
	public void testY() {
		logger.info("Test Shape y");
		Shape shape = new Shape();
		shape.setY(0, 5);
		assertTrue(shape.y(0) == 5);}

	@Test
	public void testGetShape() {
		logger.info("Test Shape getShape");
		Shape shape = new Shape();
		shape.setShape(Tetrominoe.SShape);
		assertTrue(shape.getShape() == Tetrominoe.SShape);
		shape.setShape(Tetrominoe.MirroredLShape);
		assertTrue(shape.getShape() == Tetrominoe.MirroredLShape);
		shape.setShape(Tetrominoe.ZShape);
		assertTrue(shape.getShape() == Tetrominoe.ZShape);
	}

	@Test
	public void testSetRandomShape() {
		logger.info("Test Shape set random shape");
		Shape shape = new Shape();
		shape.setRandomShape();
		assertTrue(shape.getShape() != Tetrominoe.NoShape);
	}

	@Test
	public void testMinX() {
		logger.info("Test Shape min X");
		Shape shape = new Shape();	
		shape.setShape(Tetrominoe.ZShape);
		assertTrue(shape.minX() == -1);
		shape.setShape(Tetrominoe.SShape);
		assertTrue(shape.minX() == 0);
		
		
	}

	@Test
	public void testMinY() {
		logger.info("Test Shape min Y");
		Shape shape = new Shape();	
		shape.setShape(Tetrominoe.LShape);
		assertTrue(shape.minX() == -1);
		shape.setShape(Tetrominoe.SquareShape);
		assertTrue(shape.minX() == 0);	}

	@Test
	public void testRotateLeft() {
		logger.info("Test rotate left");
		Shape shape = new Shape();	
		shape.setShape(Tetrominoe.SquareShape);
		int[][] list = new int[2][4];
		
		for (int i = 0; i != 4; i++) {
			list[0][i] = shape.x(i);
			list[1][i] = shape.y(i);
		}
		
		shape = shape.rotateLeft();
		
		for (int i = 0; i != 4; i++) {
			assertTrue(shape.x(i) == list[0][i]);	
			assertTrue(shape.y(i) == list[1][i]);	
			
		}
		
		
		
		shape = new Shape();	
		shape.setShape(Tetrominoe.SShape);
		
		list[0][0] = -1;
		list[0][1] = 0;
		list[0][2] = 0;
		list[0][3] = 1;
		list[1][0] = 0;
		list[1][1] = 0;
		list[1][2] = -1;
		list[1][3] = -1;
		
		
		shape = shape.rotateLeft();
		
		for (int i = 0; i != 4; i++) {
			assertTrue(shape.x(i) == list[0][i]);	
			assertTrue(shape.y(i) == list[1][i]);	
			
		}

		


	}

	@Test
	public void testRotateRight() {
		logger.info("Test rotate right");
		Shape shape = new Shape();	
		shape.setShape(Tetrominoe.SquareShape);
		int[][] list = new int[2][4];
		
		for (int i = 0; i != 4; i++) {
			list[0][i] = shape.x(i);
			list[1][i] = shape.y(i);
		}
		
		shape = shape.rotateRight();
		
		for (int i = 0; i != 4; i++) {
			assertTrue(shape.x(i) == list[0][i]);	
			assertTrue(shape.y(i) == list[1][i]);	

			
		}	
		
		shape = new Shape();	
		shape.setShape(Tetrominoe.LShape);
		
		list[0][0] = 1;
		list[0][1] = 1;
		list[0][2] = 0;
		list[0][3] = -1;
		list[1][0] = -1;
		list[1][1] = 0;
		list[1][2] = 0;
		list[1][3] = 0;
		
		
		shape = shape.rotateRight();
		
		for (int i = 0; i != 4; i++) {
			assertTrue(shape.x(i) == list[0][i]);	
			assertTrue(shape.y(i) == list[1][i]);	
			
		}
		

	
	
	}

}
