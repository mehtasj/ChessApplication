package chessboard;

import pieces.*;

/** Represents a tile on the board */
public class Tile {
	
	/** The tile's parent simulator (i.e. the board it is on) */
	private BoardSimulator pSim;
	
	/** The piece on this tile */
	private Piece piece;
	
	/** The column and row this tile is on */
	private int col, row;
	
	/**
	 * Constructs a tile with a simulator parent node 
	 * at a specific (col, row) location on the board
	 * @param bSim
	 * 			Board simulator node
	 * @param col
	 * 			The column value
	 * @param row
	 * 			The row value
	 */
	public Tile(BoardSimulator bSim, int col, int row) {
		pSim = bSim;
		this.col = col;
		this.row = row;
	}
	
	/** Checks to see if the tile is open */
	public boolean isEmpty() {
		if (piece == null) { return true; }
		return false;
	}
	
	/** 
	 * Represents a piece moving to this tile 
	 * or null if piece is removed from tile
	 */
	public void setPiece(Piece p) { piece = p; }
	
	/** Returns the piece occupying the tile */
	public Piece getPiece() {
		if (piece == null) { return null; }
		return piece;
	}
	
	/** @return The row this tile is located in */
	public int getRow() { return row; }

	/** @return The column this tile is is in */
	public int getCol() { return col; }
	
	// Deprecated
	/**
	 * Constructs a tile with a simulator parent node 
	 * and a potential piece on the tile
	 * @param bSim
	 * 			Board simulator node
	 * @param piece
	 * 			The current piece on the tile
	 */
	public Tile(BoardSimulator bSim, Piece p) {
		pSim = bSim;
		piece = p;
	}
}
