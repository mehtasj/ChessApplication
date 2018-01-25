package pieces;

import java.util.ArrayList;
import chessboard.*;
import pieces.AbstractPiece.MoveDir;

/** Represents a piece on the chess board */
public interface Piece {
	
	/** @return a list of valid tile coordinates to which this piece can move */
	public ArrayList<Integer[]> getValidMoves();
	
	/** 
	 * @return a revised list of valid tile coordinates to which this piece can move;
	 * Removes moves that would both result in a Check against the current player's king
	 * and that would fail to take the current player's king out of a Check
	 */
	public ArrayList<Integer[]> getRefinedMoves();
	
	/**
	 * Calculates this piece's refined moves
	 * @param board - the board this piece is on
	 * @param refinedMoves - the initial list of all this piece's valid moves
	 * @param currTile - this piece's current tile coordinate
	 * @param opposingPieces - the pieces that have a different color than this piece
	 * @param capturedPieces - the list of captured pieces that have a different color than this piece
	 */
	public void calculateRefinedMoves(BoardSimulator board, ArrayList<Integer[]> refinedMoves, 
				Tile currTile, ArrayList<Piece> opposingPieces, ArrayList<Piece> capturedPieces);
	
	/**
	 * Checks to see if a given piece's valid moves threaten the opposite king
	 * @param p - the piece whose valid moves are to be checked
	 * @return true if the given piece does threaten the opposing king (Check)
	 */
	public boolean checksOpposingKing(BoardSimulator b);
	
	/** 
	 * @param b - the board this piece is on
	 * @return the king equal in color to this piece
	 */
	public King calculateSameColoredKing(BoardSimulator b);
	
	/** 
	 * @param b - the board this piece is on
	 * @return the king opposite in color to this piece
	 */
	public King calculateOppositeColoredKing(BoardSimulator b);
	
	/**
	 * Checks if a move to (c, r) is valid
	 * @param dir - this piece's potential move direction
	 * @param board - the board this piece is on
	 * @param moves - this piece's list of valid moves
	 * @param c - the column coordinate of the tile in question
	 * @param r - the row coordinate of the tile in question
	 */
	public void checkMove(MoveDir dir, BoardSimulator board, 
				ArrayList<Integer[]> moves, int c, int r);
	
	/** 
	 * @param b - the board this piece is on
	 * @param c - the column coordinate of the tile in question
	 * @param r - the row coordinate of the tile in question
	 * @return true if this piece can move to the empty tile
	 */
	public boolean canMoveToEmptySpaceAt(BoardSimulator b, int c, int r);
	
	/** 
	 * @param b - the board this piece is on
	 * @param c - the column coordinate of the tile in question
	 * @param r - The row coordinate of the tile in question
	 * @return true if this piece can capture at the specified tile
	 */
	public boolean canCaptureAt(BoardSimulator b, int c, int r);
	
	/**
	 * Stores a valid move
	 * @param c - the column coordinate
	 * @param r - the row coordinate
	 * @return an array representing the move to (c, r)
	 */
	public Integer[] storeMoveTo(int c, int r);
	
	/** 
	 * Moves this piece to a new tile on the board
	 * and removes it from its previous tile;
	 * Requires that the move is valid
	 * @param t - this piece's new tile
	 */
	public void moveTo(Tile t);
	
	/** 
	 * Sets this piece's tile on the board 
	 * @param t - the tile on which to set this piece
	 */
	public void setTile(Tile t);
	
	/** @return true if this piece is white */
	public boolean isWhite();
	
	/** Adds 1 to this piece's current move number */
	public void incrementMoveNumber();
	
	/** @return the board this piece is on */
	public BoardSimulator getBoard();
	
	/** @return this piece's color */
	public PieceColor getColor();
	
	/** @return this piece's move number */
	public int getMoveNumber();
	
	/** @return this piece's column coordinate */
	public int getCol();
	
	/** @return this piece's row coordinate */
	public int getRow();
}
