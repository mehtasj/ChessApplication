package chessboard;

/** Keeps track of the state of the board
 * and its pieces
 */
public class BoardSimulator {

	private Tile[][] board;
	
	public BoardSimulator() {
		board = new Tile[8][8];
		
		// initial setup 
	}
	
	public Tile getTile(int col, int row) {
		if (board[row][col] == null)
			board[row][col] = new Tile(this, null);
		return board[row][col];
	}
	
	// add flip method
}
