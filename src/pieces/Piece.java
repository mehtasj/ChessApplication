package pieces;

import java.util.ArrayList;
import chessboard.*;

/** Represents a piece on the chess board */
public interface Piece {

	/** Moves this piece to a new position on the board
	 * Requires: move is valid
	 */
	public void moveTo(int col, int row);
	
	/** Moves this piece to a new tile on the board
	 * and removes it from its previous tile
	 * Requires: move is valid
	 */
	public void moveTo(Tile t);
	
	/** Sets the position of a piece on the board 
	 * @param col
	 * 		The column this piece is now in
	 * @param row
	 * 		The row this piece is now in
	 */
	public void setPosition(int col, int row);
	
	/** Sets the tile of a piece on the board 
	 * @param t
	 * 		The tile on which to set this piece
	 */
	public void setTile(Tile t);
	
	/**
	 * Checks if this piece's color is white
	 * @return
	 * 		true if this piece is white
	 */
	public boolean isWhite();
	
	/**
	 * @return
	 * 		The row this piece is currently in
	 */
	public int getRow();
	
	/**
	 * @return
	 * 		The column this piece is currently in
	 */
	public int getCol();
	
	/**
	 * 
	 * @return
	 * 		The board this piece is on (parent)
	 */
	public BoardSimulator getBoard();
	
	/**
	 * 
	 * @return
	 * 		The color of this piece
	 */
	public PieceColor getColor();
	
	/** 
	 * @return
	 * 		A list of valid moves that this piece can make
	 * 		The ArrayList contains Integer arrays of length 2 
	 * 		to represent a (col, row) coordinate; assumes the board can be flipped 
	 */
	public ArrayList<Integer[]> getValidMoves();
	
	/**
	 * Stores a valid move coordinate inside an array
	 * which will be added to a List of valid coordinates
	 * @param c
	 * 		The column coordinate
	 * @param r
	 * 		The row coordinate
	 * @return
	 * 		an array with 2 elements: column and row 
	 * 		in that order
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
}
