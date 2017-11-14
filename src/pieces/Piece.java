package pieces;

import java.util.List;

/** Represents a piece on the chess board */
public interface Piece {

	/** Moves the piece to a new position on the board
	 * Requires: decision is a valid move
	 */
	public void move(int col, int row);
	
	/** Sets the position of a piece on the board 
	 * @param col
	 * 		The column the piece is now in
	 * @param row
	 * 		The row the piece is now in
	 */
	public void setPosition(int col, int row);
	
	/**
	 * @return
	 * 		The row the piece is currently in
	 */
	public int getRow();
	
	/**
	 * @return
	 * 		The column the piece is currently in
	 */
	public int getCol();
	
	/** 
	 * @return
	 * 		A list of valid moves that a given piece can make
	 */
	public List getValidMoves();
}
