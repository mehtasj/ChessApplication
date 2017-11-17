package pieces;

import java.util.ArrayList;
import chessboard.*;

/** Represents a piece on the chess board */
public interface Piece {

	/** Moves the piece to a new position on the board
	 * Requires: move is valid
	 */
	public void moveTo(int col, int row);
	
	/** Sets the position of a piece on the board 
	 * @param col
	 * 		The column the piece is now in
	 * @param row
	 * 		The row the piece is now in
	 */
	public void setPosition(int col, int row);
	
	/**
	 * Checks if the piece's color is white
	 * @return
	 * 		true if the piece is white
	 * 		false if the piece is black 
	 */
	public boolean isWhite();
	
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
	 * 
	 * @return
	 * 		The board the piece is on (parent)
	 */
	public BoardSimulator getBoard();
	
	/**
	 * 
	 * @return
	 * 		The color of the piece
	 */
	public PieceColor getColor();
	
	/** 
	 * @return
	 * 		A list of valid moves that a given piece can make
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
	 * Checks if this pawn can move to an empty tile
	 * @param b
	 * 		The board this pawn is on
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
