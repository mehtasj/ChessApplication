package chessboard;

import pieces.*;

/** Represents a tile on the board */
public class Tile {
	
	/** This tile's board parent */
	private BoardSimulator pSim;
	
	/** This tile's occupying piece */
	private Piece piece;
	
	/** This tile's column and row coordinates */
	private int col, row;
	
	/**
	 * Constructs a tile at (c, r)
	 * @param bSim - the board this piece is on
	 * @param c - this tile's column coordinate
	 * @param r - this tile's row coordinate
	 */
	public Tile(BoardSimulator bSim, int c, int r) {
		pSim = bSim;
		col = c;
		row = r;
	}
	
	/** @return true if this tile is unoccupied */
	public boolean isEmpty() { return (piece == null); }
	
	/**
	 * Sets this tile's piece (null if empty)
	 * @param p - the piece currently on this tile
	 */
	public void setPiece(Piece p) { piece = p; }
	
	/** @return this tile's occupying piece (null if empty) */
	public Piece getPiece() { return piece; }
	
	/** @return this tile's column coordinate */
	public int getCol() { return col; }
	
	/** @return this tile's row coordinate */
	public int getRow() { return row; }
}
