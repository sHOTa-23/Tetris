import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardTest {
	Board b,c,d,e;
	Piece pyr1, pyr2, pyr3, pyr4, s,sRotated,st,l,sq,L,S;

	// This shows how to build things in setUp() to re-use
	// across tests.

	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	@BeforeAll
	protected void setUp() throws Exception {
		b = new Board(3, 6);
		c = new Board(10,10);
		d = new Board(6,3);
		e = new Board(0,0);

		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();


		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		st = new Piece(Piece.STICK_STR);
		l = new Piece(Piece.L1_STR);
		L = new Piece(Piece.S2_STR);
		S = new Piece(Piece.S2_STR);
		sq = new Piece(Piece.SQUARE_STR);
	}
	@Test
	// Check the basic width/height/max after the one placement
	public void testSample1() {
		b.place(pyr1, 0, 0);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	@Test
	// Place sRotated into the board, then check some measures
	public void testSample2() {

		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
		assertTrue(b.getGrid(2,-5));
		assertTrue(b.getGrid(-2,-5));
		assertTrue(b.getGrid(2,100));
		assertTrue(b.getGrid(120,6));
	}
	@Test
	void testConstructor(){
		assertThrows(Throwable.class, () -> {
			new Board(-10, 5); });
		assertThrows(Throwable.class, () -> {
			new Board(11, -8); });
		Board f = new Board(4,5);
		assertEquals(4,f.getWidth());
		assertEquals(5,f.getHeight());
	}
	@Test
	public void testInvalidInputs() {
		assertThrows(ArrayIndexOutOfBoundsException.class, ()-> {
			e.getColumnHeight(23); });
		assertThrows(ArrayIndexOutOfBoundsException.class, ()-> {
			e.getColumnHeight(-2); });
		assertThrows(ArrayIndexOutOfBoundsException.class, ()-> {
			e.getRowWidth(-2); });
		assertThrows(ArrayIndexOutOfBoundsException.class, ()-> {
			e.getRowWidth(21); });
		assertThrows(ArrayIndexOutOfBoundsException.class, ()-> {
			e.dropHeight(sq,21); });
		assertThrows(ArrayIndexOutOfBoundsException.class, ()-> {
			e.dropHeight(sq,-1); });
		Board h = new Board(6,6);
		assertThrows(NullPointerException.class, () -> {
			b.place(null, 2, 1); });
		h.place(st,0,0);
		assertThrows(RuntimeException.class, () -> {
			h.place(st, 2, 1); });
	}
	@Test
	public void testOutOfBound() {
		e.commit();
		d.commit();
		int result = c.place(pyr1,-2,2);
		assertEquals(Board.PLACE_OUT_BOUNDS,result);
		result = c.place(l,0,-1);
		assertEquals(Board.PLACE_OUT_BOUNDS,result);
		result = c.place(l,12,0);
		assertEquals(Board.PLACE_OUT_BOUNDS,result);
		result = c.place(st,7,13);
		assertEquals(Board.PLACE_OUT_BOUNDS,result);

	}

	@Test
	public void testPlaceOKAndFilledAndBad() {
		c.commit();
		int result = c.place(pyr1,0,0);
		c.commit();
		System.out.println(c.toString());
		assertEquals(Board.PLACE_OK,result);
		result = c.place(sq,3,0);
		c.commit();
		assertEquals(Board.PLACE_OK,result);
		result = c.place(sq.computeNextRotation(),5,0);
		c.commit();
		assertEquals(Board.PLACE_OK,result);
		result = c.place(sq,7,0);
		c.commit();
		assertEquals(Board.PLACE_OK,result);
		result = c.place(st,9,0);
		c.commit();
		assertEquals(Board.PLACE_ROW_FILLED,result);
		result = c.place(st,0,1);
		c.commit();
		assertEquals(Board.PLACE_OK,result);
		result = c.place(st,2,1);
		c.commit();
		assertEquals(Board.PLACE_ROW_FILLED,result);
		result = c.place(sq,3,0);
		c.commit();
		assertEquals(Board.PLACE_BAD,result);
		result = c.place(sq.computeNextRotation(),5,0);
		c.commit();
		assertEquals(Board.PLACE_BAD,result);
		result = c.place(sq,7,0);
		c.commit();
		assertEquals(Board.PLACE_BAD,result);
		result = c.place(st,9,0);
		c.commit();
		assertEquals(Board.PLACE_BAD,result);
		result = c.place(st,0,1);
		c.commit();
		assertEquals(Board.PLACE_BAD,result);
		result = c.place(st,2,1);
		c.commit();
		assertEquals(Board.PLACE_BAD,result);;
	}
	@Test
	public void testClearRows0() {
		Board f = new Board(3,3);
		f.commit();
		f.place(pyr1,0,0);
		f.clearRows();
	}
	@Test
	public void testClearRows() {
		Board C = new Board(10,10);
		C.commit();
		C.place(pyr1,0,0);
		C.commit();
		C.place(sq,3,0);
		C.commit();
		C.place(sq.computeNextRotation(),5,0);
		C.commit();
		C.place(sq,7,0);
		C.commit();
		C.place(st,9,0);
		C.commit();
		C.place(st,0,1);
		C.commit();
		C.place(st,2,1);
		C.commit();
		C.place(st,1,2);
		C.commit();
		C.place(sq,3,3);
		C.commit();
		C.place(sq,5,2);
		C.commit();
		C.place(sq,7,2);
		C.commit();
		C.clearRows();
		C.commit();
		boolean[][] gridd = new boolean[10][10];
		gridd[0][0]=true;
		gridd[1][0]=true;
		gridd[2][0]=true;
		gridd[5][0]=true;
		gridd[6][0]=true;
		gridd[7][0]=true;
		gridd[8][0]=true;
		gridd[9][0]=true;
		gridd[0][1]=true;
		gridd[1][1]=true;
		gridd[2][1]=true;
		gridd[3][1]=true;
		gridd[4][1]=true;
		gridd[1][2]= true;
		for(int i = 0; i <= 9; i++) {
			for(int j = 0; j <= 9; j++) {
				assertEquals(C.getGrid(i,j),gridd[i][j]);
			}
		}
		assertEquals(2,C.getColumnHeight(0));
		assertEquals(3,C.getColumnHeight(1));
		assertEquals(2,C.getColumnHeight(2));
		assertEquals(2,C.getColumnHeight(3));
		assertEquals(2,C.getColumnHeight(4));
		assertEquals(8,C.getRowWidth(0));
		assertEquals(5,C.getRowWidth(1));
		assertEquals(1,C.getRowWidth(2));
		assertEquals(0,C.getRowWidth(9));
	}
	@Test
	public void testDropHeight0() {
		d.place(pyr1,0, 0);
		d.commit();
		assertEquals(2, d.dropHeight(pyr3, 0));
		assertEquals(1, d.dropHeight(s.computeNextRotation(), 1));
		assertEquals(2, d.dropHeight(sq.computeNextRotation(), 0));
		assertEquals(2,d.dropHeight(st.computeNextRotation(),0));
		d.undo();
		d.place(sq,0,0);
		assertEquals(2, d.dropHeight(pyr3, 0));
		assertEquals(1, d.dropHeight(s.computeNextRotation(), 1));
		assertEquals(2, d.dropHeight(sq.computeNextRotation(), 0));
		assertEquals(2,d.dropHeight(st.computeNextRotation(),0));
	}
	@Test
	public void testDropHeight1() {
		c.place(st, 2, 1);
		c.commit();
		assertEquals(5, c.dropHeight(st.computeNextRotation(), 0));
		assertEquals(2, c.dropHeight(L.computeNextRotation(), 3));
	}
	@Test
	public void testUndo() {
		Board bo = new Board(1,5);
		bo.place(sq,0,0);
		bo.undo();
		assertEquals(Board.PLACE_ROW_FILLED, bo.place(st,0,0));
		bo.commit();
		assertEquals(Board.PLACE_BAD, bo.place(st,0,0));
		bo.undo();
		bo.clearRows();
		bo.undo();
		assertEquals(Board.PLACE_BAD,bo.place(st,0,0));
	}
	@Test
	public void testSanity() {
		Board bo = new Board(4,4);
		bo.setDebugCondition(false);
		bo.place(sq,0,0);
		bo.commit();
		bo.setDebugCondition(true);
		bo.clearRows();
		bo.commit();
		int[] arr = new int[4];
		Arrays.fill(arr,1);
		assertThrows(RuntimeException.class, ()-> {
			bo.checkHeight(arr);});
		assertThrows(RuntimeException.class, ()-> {
			bo.checkWidth(arr);});
		assertThrows(RuntimeException.class, ()-> {
			bo.checkMaxHeight(23);});
	}
}
