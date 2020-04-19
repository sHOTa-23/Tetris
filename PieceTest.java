import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/*
  Unit test for Piece class -- starter shell.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PieceTest {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4,pyr5;
	private Piece l1,l2,l3,l4,l5;
	private Piece L1,L2,L3,L4,L5;
	private Piece s1,s2,s3,s4,s5;
	private Piece S1,S2,S3,S4,S5;
	private Piece st1,st2,st3,st4,st5;
	private Piece sq1,sq2,sq3,sq4,sq5;
	private Piece c11,c12,c13,c14,c15,c21,c22,c23,c24,c25,c31,c32,c33,c34,c35;
	private Piece[] pics;


	@BeforeAll
	protected void setUp() throws Exception {
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		pyr5 = pyr4.computeNextRotation();

		s1 = new Piece(Piece.S1_STR);
		s2 = s1.computeNextRotation();
		s3 = s2.computeNextRotation();
		s4 = s3.computeNextRotation();
		s5 = s4.computeNextRotation();

		S1 = new Piece(Piece.S2_STR);
		S2 = S1.computeNextRotation();
		S3 = S2.computeNextRotation();
		S4 = S3.computeNextRotation();
		S5 = S4.computeNextRotation();

		L1 = new Piece(Piece.L2_STR);
		L2 = L1.computeNextRotation();
		L3 = L2.computeNextRotation();
		L4 = L3.computeNextRotation();
		L5 = L4.computeNextRotation();

		l1 = new Piece(Piece.L1_STR);
		l2 = l1.computeNextRotation();
		l3 = l2.computeNextRotation();
		l4 = l3.computeNextRotation();
		l5 = l4.computeNextRotation();

		st1 = new Piece(Piece.STICK_STR);
		st2 = st1.computeNextRotation();
		st3 = st2.computeNextRotation();
		st4 = st3.computeNextRotation();
		st5 = st4.computeNextRotation();

		sq1 = new Piece(Piece.SQUARE_STR);
		sq2 = sq1.computeNextRotation();
		sq3 = sq2.computeNextRotation();
		sq4 = sq3.computeNextRotation();
		sq5 = sq4.computeNextRotation();
		String str1 = "0 0";
		String str2 = "0 0 0 1 0 2 1 0 1 1 2 0";
		String str3 = "0 2 1  1 1 2 2 1 3 0 3 1";
		c11 = new Piece(str1);
		c12 = c11.computeNextRotation();
		c13 = c12.computeNextRotation();
		c14 = c13.computeNextRotation();
		c15 = c14.computeNextRotation();
		c21 = new Piece(str2);
		c22 = c21.computeNextRotation();
		c23 = c22.computeNextRotation();
		c24 = c23.computeNextRotation();
		c25 = c24.computeNextRotation();
		c31 = new Piece(str3);
		c32 = c31.computeNextRotation();
		c33 = c32.computeNextRotation();
		c34 = c33.computeNextRotation();
		c35 = c34.computeNextRotation();

		pics = Piece.getPieces();
		pics = Piece.getPieces();
		//System.out.println(pics[Piece.L1].fastRotation());
	}
	@Test
	public void testConstructor() {
		TPoint[] arr = new TPoint[2];
		Piece p = new Piece(arr);
		TPoint[] arr1 = new TPoint[0];
		Piece p1 = new Piece(arr1);
		Piece p2 = new Piece("");
		assertThrows(RuntimeException.class, () -> { new Piece("2 f t 23 21");});
	}
	// Here are some sample tests to get you started
	@Test
	public void testSampleSize() {

		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());

		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());

		assertEquals(3,pyr3.getWidth());
		assertEquals(2,pyr3.getHeight());

		assertEquals(2,pyr4.getWidth());
		assertEquals(3,pyr4.getHeight());

		assertEquals(3, pyr5.getWidth());
		assertEquals(2, pyr5.getHeight());



		//check square sizes
		assertEquals(2, sq1.getWidth());
		assertEquals(2, sq1.getHeight());

		assertEquals(2, sq2.getWidth());
		assertEquals(2, sq2.getHeight());

		assertEquals(2,sq3.getWidth());
		assertEquals(2,sq3.getHeight());

		assertEquals(2,sq4.getWidth());
		assertEquals(2,sq4.getHeight());

		assertEquals(2, sq5.getWidth());
		assertEquals(2, sq5.getHeight());

		//check sticks

		assertEquals(1, st1.getWidth());
		assertEquals(4, st1.getHeight());

		assertEquals(4, st2.getWidth());
		assertEquals(1, st2.getHeight());

		assertEquals(1,st3.getWidth());
		assertEquals(4,st3.getHeight());

		assertEquals(4,st4.getWidth());
		assertEquals(1,st4.getHeight());

		assertEquals(1, st5.getWidth());
		assertEquals(4, st5.getHeight());

		//check   L1
		assertEquals(2, l1.getWidth());
		assertEquals(3, l1.getHeight());

		assertEquals(3, l2.getWidth());
		assertEquals(2, l2.getHeight());

		assertEquals(2,l3.getWidth());
		assertEquals(3,l3.getHeight());

		assertEquals(3,l4.getWidth());
		assertEquals(2,l4.getHeight());

		assertEquals(2, l5.getWidth());
		assertEquals(3, l5.getHeight());

		//check L2
		assertEquals(2, L1.getWidth());
		assertEquals(3, L1.getHeight());

		assertEquals(3, L2.getWidth());
		assertEquals(2, L2.getHeight());

		assertEquals(2,L3.getWidth());
		assertEquals(3,L3.getHeight());

		assertEquals(3,L4.getWidth());
		assertEquals(2,L4.getHeight());

		assertEquals(2, L5.getWidth());
		assertEquals(3, L5.getHeight());

		//check S1
		assertEquals(3, S1.getWidth());
		assertEquals(2, S1.getHeight());

		assertEquals(2, S2.getWidth());
		assertEquals(3, S2.getHeight());

		assertEquals(3,S3.getWidth());
		assertEquals(2,S3.getHeight());

		assertEquals(2,S4.getWidth());
		assertEquals(3,S4.getHeight());

		assertEquals(3, S5.getWidth());
		assertEquals(2, S5.getHeight());

		//check s2
		assertEquals(3, s1.getWidth());
		assertEquals(2, s1.getHeight());

		assertEquals(2, s2.getWidth());
		assertEquals(3, s2.getHeight());

		assertEquals(3,s3.getWidth());
		assertEquals(2,s3.getHeight());

		assertEquals(2,s4.getWidth());
		assertEquals(3,s4.getHeight());

		assertEquals(3, s5.getWidth());
		assertEquals(2, s5.getHeight());

		//check custom 1
		assertEquals(1, c11.getWidth());
		assertEquals(1, c11.getHeight());

		assertEquals(1, c12.getWidth());
		assertEquals(1, c12.getHeight());

		assertEquals(1, c13.getWidth());
		assertEquals(1, c13.getHeight());

		assertEquals(1, c14.getWidth());
		assertEquals(1, c14.getHeight());

		assertEquals(1, c15.getWidth());
		assertEquals(1, c15.getHeight());

		//check custom 2
		assertEquals(3, c21.getWidth());
		assertEquals(3, c21.getHeight());

		assertEquals(3, c22.getWidth());
		assertEquals(3, c22.getHeight());

		assertEquals(3, c23.getWidth());
		assertEquals(3, c23.getHeight());

		assertEquals(3, c24.getWidth());
		assertEquals(3, c24.getHeight());

		assertEquals(3, c25.getWidth());
		assertEquals(3, c25.getHeight());

		//check custom 3
		assertEquals(4, c31.getWidth());
		assertEquals(3, c31.getHeight());

		assertEquals(3, c32.getWidth());
		assertEquals(4, c32.getHeight());

		assertEquals(4, c33.getWidth());
		assertEquals(3, c33.getHeight());

		assertEquals(3, c34.getWidth());
		assertEquals(4, c34.getHeight());

		assertEquals(4, c35.getWidth());
		assertEquals(3, c35.getHeight());
	}


	@Test
	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.

		//test pyr
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, pyr2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1,0,1}, pyr3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, pyr4.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr5.getSkirt()));

		//test s1
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, s2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0,1}, s3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1,0}, s4.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s5.getSkirt()));

		//test s2
		assertTrue(Arrays.equals(new int[] {1, 0, 0}, S1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, S2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1,0,0}, S3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,1}, S4.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 0}, S5.getSkirt()));

		//test stick
		assertTrue(Arrays.equals(new int[] {0}, st1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0,0,0}, st2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0}, st3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0,0,0}, st4.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0}, st5.getSkirt()));

		//test l1
		assertTrue(Arrays.equals(new int[] {0,0}, l1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0,0}, l2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {2,0}, l3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,1,1}, l4.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0}, l5.getSkirt()));

		//test l2
		assertTrue(Arrays.equals(new int[] {0,0}, L1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1,1,0}, L2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,2}, L3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0,0}, L4.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0}, L5.getSkirt()));

		//test square
		assertTrue(Arrays.equals(new int[] {0,0}, sq1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0}, sq2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0}, sq3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0}, sq4.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0}, sq5.getSkirt()));

		//test custom 1
		assertTrue(Arrays.equals(new int[] {0}, c11.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0}, c12.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0}, c13.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0}, c14.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0}, c15.getSkirt()));

		//test custom 2
		assertTrue(Arrays.equals(new int[] {0,0,0}, c21.getSkirt()));
		//System.out.println(c24.getSkirt()[0] + " "+ c24.getSkirt()[1]+" " + c24.getSkirt()[2]);
		assertTrue(Arrays.equals(new int[] {0,0,0}, c22.getSkirt()));
		assertTrue(Arrays.equals(new int[] {2,1,0}, c23.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,1,2}, c24.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0,0}, c25.getSkirt()));

		//test custom 3
		assertTrue(Arrays.equals(new int[] {2,1,1,0}, c31.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,1,3}, c32.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1,1,0,0}, c33.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0,0,2}, c34.getSkirt()));
		assertTrue(Arrays.equals(new int[] {2,1,1,0}, c35.getSkirt()));
	}
	@Test
	public void testSampleEquals() {
		assertFalse(pyr1.equals("str"));
		assertFalse(c11.equals(st1.computeNextRotation()));
		assertNotEquals(pyr1, pyr2);
		assertNotEquals(pyr1, pyr3);
		assertEquals(pyr1,pyr5);
		assertEquals(pyr2,pyr5.computeNextRotation());

		assertEquals(sq1, sq2);
		assertEquals(sq1, sq3);
		assertEquals(sq1,sq5);
		assertEquals(sq2,sq5.computeNextRotation());

		assertNotEquals(st1, st2);
		assertEquals(st1, st3);
		assertEquals(st1,st5);
		assertEquals(st2,st5.computeNextRotation());

		assertNotEquals(l1, l2);
		assertNotEquals(l1, l3);
		assertEquals(l1,l5);
		assertEquals(l2,l5.computeNextRotation());

		assertNotEquals(L1, L2);
		assertNotEquals(L1, L3);
		assertEquals(L1,L5);
		assertEquals(L2,L5.computeNextRotation());

		assertNotEquals(s1, s2);
		assertEquals(s1, s3);
		assertEquals(s1,s5);
		assertEquals(s2,s5.computeNextRotation());

		assertNotEquals(S1, S2);
		assertEquals(S1, S3);
		assertEquals(S1,S5);
		assertEquals(S2,S5.computeNextRotation());

		assertEquals(c11, c12);
		assertEquals(c11, c13);
		assertEquals(c11,c15);
		assertEquals(c12,c15.computeNextRotation());

		assertNotEquals(c21, c22);
		assertNotEquals(c21, c23);
		assertEquals(c21,c25);
		assertEquals(c22,c25.computeNextRotation());

		assertNotEquals(c31, c32);
		assertNotEquals(c31, c33);
		assertEquals(c31,c35);
		assertEquals(c32,c35.computeNextRotation());

		assertNotEquals(pyr1,c11);
		assertNotEquals(s1,st3);
		assertNotEquals(l4,c21);
		assertNotEquals(L3,sq3);
		assertNotEquals(null,l3);
	}

	private Piece recFunction(Piece p, int depth) {
		if(depth == 0) return p;
		return recFunction(p.fastRotation(),depth-1);
	}
	@Test
	public void testFastRotation() {
		for(int i = 0; i<pics.length; i++) {
			for(int j = 0; j < pics[i].getBody().length; j++) {
				assertEquals(pics[i], pics[i].fastRotation().fastRotation().fastRotation().fastRotation());
				if(i != 5) assertNotEquals(pics[i],pics[i].fastRotation());
				else assertEquals(pics[i],pics[i].fastRotation());
			}
		}
		for(int i = 0; i < 100; i++) {
			assertEquals(recFunction(pics[i%7],i),recFunction(pics[i%7],i%4));
		}
	}
}
