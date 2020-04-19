// Board.java

import javax.swing.tree.FixedHeightLayoutCache;
import java.util.Arrays;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int[] widths;
	private int[] heights;
	private int[] backUpWidths;
	private int[] backUpHeights;
	private int height;
	private boolean[][] grid;
	private boolean[][] backUpGrid;
	private boolean DEBUG = true;
	private boolean backUpDebug = true;
	boolean committed;


	// Here a few trivial methods are provided:

	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;
		widths = new int[height];
		heights = new int[width];

	}
	private void makeCopy() {
		backUpGrid = new boolean[width][height];
		for(int i = 0; i < width; i++) {
			System.arraycopy(grid[i],0,backUpGrid[i],0,height);
		}
		backUpHeights = new int[width];
		System.arraycopy(heights, 0, backUpHeights, 0, width);
		backUpWidths = new int[height];
		System.arraycopy(widths, 0, backUpWidths, 0, height);
	}
	private void undoCopied() {
		for(int i = 0; i < width; i++) {
			boolean[] tmp = backUpGrid[i];
			backUpGrid[i] = grid[i];
			grid[i] = tmp;
		}
		int[] tmp1 = backUpHeights;
		backUpHeights = heights;
		heights = tmp1;
		int[] tmp2 = backUpWidths;
		backUpWidths = widths;
		widths = tmp2;
	}


	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}


	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}


	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {
		int max = 0;
		for (int value : heights) {
			max = Math.max(max, value);
		}
		return max;
	}
	protected void checkHeight(int[] myHeights) {
		if(!Arrays.equals(myHeights,heights)) throw new RuntimeException("wrong heights array");
	}
	protected void checkWidth(int[] myWidths) {
		if(!Arrays.equals(myWidths,widths)) throw new RuntimeException("wrong widths array");
	}
	protected void checkMaxHeight(int myMaxHeight) {
		if(myMaxHeight != getMaxHeight()) throw new RuntimeException("wrong maxHeight");
	}
	protected  void setDebugCondition(boolean bool) {
		DEBUG = bool;
	}


	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			int maxHeight = -1;
			int[] myHeights = new int[heights.length];
			int[] myWidths = new int[widths.length];
			for(int i = 0; i < width; i++) {
				int h = 0;
				for(int j = 0; j < height; j++) {
					if(grid[i][j]) {
						h = j+1;
					}
				}
				myHeights[i] = h;
				maxHeight  = Math.max(maxHeight,h);
			}
			for(int i = 0; i < height; i++) {
				int w = 0;
				for (int j = 0; j < width; j++) {
					if (grid[j][i]) {
						w++;
					}
				}
				myWidths[i] = w;
			}
			checkMaxHeight(maxHeight);
			checkWidth(myWidths);
			checkHeight(myHeights);
		}
	}

	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.

	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		if(x >= width || x < 0 ) throw new ArrayIndexOutOfBoundsException("wrong size");
		int maxy = 0;
		for(int i=x; i< x+piece.getSkirt().length; i++) {
			maxy = Math.max(maxy,heights[i]-piece.getSkirt()[i-x]);
		}
		return maxy;
	}


	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		if(x >= width || x < 0) throw new ArrayIndexOutOfBoundsException("wrong size");
		return heights[x];
	}


	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		if(y >= height || y < 0) throw new ArrayIndexOutOfBoundsException("wrong size");
		return widths[y];
	}


	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(x>=width || y>=height || x<0 || y<0) return true;
		return grid[x][y];
	}


	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.

	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	private boolean boundCheck(int x, int y, int i, int j) {
		return x >= 0 && x < i && y >= 0 && y < j;

	}
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		if(piece == null) throw new NullPointerException("null input");
		for(int i = 0; i < piece.getBody().length; i++) {
			int a = piece.getBody()[i].x;
			int b = piece.getBody()[i].y;
			if(!boundCheck(a+x,b+y,width,height)) return PLACE_OUT_BOUNDS;
			if(getGrid(a+x,b+y)) return PLACE_BAD;
		}
		makeCopy();
		int result = PLACE_OK;
		for(int i = 0; i < piece.getBody().length; i++) {
			int a = piece.getBody()[i].x;
			int b = piece.getBody()[i].y;
			grid[a+x][b+y] = true;
			widths[b+y]++;
			heights[a+x] = Math.max(heights[a+x],b+y+1);
			if(widths[b+y]==heights.length) result = PLACE_ROW_FILLED;
		}
		committed = false;
		sanityCheck();
		return result;
	}


	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	//copies i indexed collumn into i-1-th collumn
	private void copyCollumn(int i) {
		for(int j = 0; j < this.grid.length; j++) {
			this.grid[j][i-1] = this.grid[j][i];
			widths[i-1] = widths[i];
		}
	}
	//fills the last(first depend on point of view) row of the grid
	private void fillLast() {
		for(int i = 0; i < this.grid.length; i++) {
			this.grid[i][this.grid[0].length-1] = false;
		}
		widths[this.grid[0].length-1] = 0;
	}
	public int clearRows() {
		int rowsCleared = 0;
		if(committed) makeCopy();
		for(int i = this.grid[0].length-1; i >= 0;i--) {
			if(widths[i] == width) {
				int index = i + 1 ;
				while(index <= this.grid[0].length-1) {
					copyCollumn(index);
					index++;
				}
				fillLast();
				rowsCleared++;
			}
		}
		for(int i = 0; i < width; i++) {
			boolean hadOne = false;
			for(int j = 0; j < height; j++) {
				if(grid[i][j]){
					hadOne = true;
					heights[i] = j+1;
				}
			}
			if(!hadOne) {
				heights[i] = 0;
			}
		}
		sanityCheck();
		committed = false;
		return rowsCleared;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		if(!committed) {
			undoCopied();
			sanityCheck();
		}

		committed = true;
	}


	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}



	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility)
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


