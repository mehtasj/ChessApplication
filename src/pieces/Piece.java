package pieces;

import java.util.ArrayList;
import chessboard.*;

/** Represents a piece on the chess board */
public interface Piece {
	
	/** 
	 * @return
	 * 		a list of valid moves that this piece can make
	 * 		The ArrayList contains Integer arrays of length 2 
	 * 		to represent a (col, row) coordinate;
	 * 		Assumes the board can be flipped 
	 */
	public ArrayList<Integer[]> getValidMoves();
	
	/** 
	 * @return
	 * 		a subset of valid moves that this piece can make
	 * 		The ArrayList contains Integer arrays of length 2 
	 * 		to represent a (col, row) coordinate;
	 * 		Takes into account if a usually valid move is 
	 * 		invalid if the king is currently in Check or 
	 * 		if it would be placed in Check (illegal move)
	 */
	public ArrayList<Integer[]> getRefinedMoves();
	
	/**
	 * Calculates the refined moves for a specific piece
	 * @param board
	 * 		The board this piece is on
	 * @param refinedMoves
	 * 		The whole list of valid moves this piece can make;
	 * 		Will potentially remove moves inside this method
	 * @param currTile
	 * 		The current tile this piece is on
	 * @param oppositeColoredPieces
	 * 		The pieces that differ in color from this piece
	 * 		in order to check if any of them threaten this piece's king,
	 * 		which would result in an illegal move
	 * @param capturedPieces
	 * 		The list of captured pieces of the opposite color from this piece;
	 * 		Pieces will be placed into this list if this piece's valid move would capture
	 * 		the piece; however, they will later be removed from the list to maintain a 
	 * 		list of pieces that were actually captured
	 */
	public void calculateRefinedMoves(BoardSimulator board, ArrayList<Integer[]> refinedMoves, Tile currTile,
			Piece[] oppositeColoredPieces, ArrayList<Piece> capturedPieces);
	
	/** 
	 * Moves this piece to a new tile on the board
	 * and removes it from its previous tile
	 * Requires: move is valid
	 */
	public void moveTo(Tile t);
	
	/** 
	 *  Increments the number of times this piece has moved
	 *  if it successfully moves to a new tile
	 */
	public void incrementMoveNumber();
	
	/**
	 * Stores a valid move coordinate inside an array
	 * which will be added to a List of valid coordinates
	 * @param c
	 * 		The column coordinate
	 * @param r
	 * 		The row coordinate
	 * @return 
	 * 		an array with 2 elements: column and row in that order
	 */
	public Integer[] storeMoveTo(int c, int r);
	
	/** 
	 * Checks if this piece can move to an empty tile
	 * @param b 
	 * 		The board this piece is on
	 * @param c
	 * 		The column coordinate
	 * @param r
	 * 		The row coordinate
	 * @return
	 * 		true if this piece can move to the specified tile
	 */
	public boolean canMoveToEmptySpaceAt(BoardSimulator b, int c, int r);
	
	/** 
	 * Checks if this piece can capture at the specified tile
	 * @param b
	 * 		The board this piece is on
	 * @param c
	 * 		The column coordinate
	 * @param r
	 * 		The row coordinate
	 * @return
	 * 		true if this piece can capture at the specified tile
	 */
	public boolean canCaptureAt(BoardSimulator b, int c, int r);
	
	/** 
	 * Sets the tile of a piece on the board 
	 * @param t
	 * 		The tile on which to set this piece
	 */
	public void setTile(Tile t);
	
	/**
	 * Checks if this piece's color is white
	 * @return true if this piece is white
	 */
	public boolean isWhite();
	
	/** @return The row this piece is currently in */
	public int getRow();
	
	/** @return The column this piece is currently in */
	public int getCol();
	
	/** @return The move number of this piece */
	public int getMoveNumber();
	
	/** @return The board this piece is on (parent) */
	public BoardSimulator getBoard();
	
	/** @return The color of this piece */
	public PieceColor getColor();
	
	// Deprecated
	/** 
	 * Moves this piece to a new position on the board
	 * Requires: move is valid
	 */
	public void moveTo(int col, int row);
	
	// Deprecated
	/** 
	 * Sets the position of a piece on the board 
	 * @param col
	 * 		The column this piece is now in
	 * @param row
	 * 		The row this piece is now in
	 */
	public void setPosition(int col, int row);
}
