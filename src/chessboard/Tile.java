package chessboard;

import pieces.*;

/** Represents a tile on the board */
public class Tile {
	
	/** The tile's parent simulator (i.e. the board it is on) */
	private BoardSimulator pSim;
	
	/** The piece on this tile */
	private Piece piece;
	
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
	
	/** Checks to see if the tile is open */
	public boolean isEmpty() {
		if (piece == null)
			return true;
		return false;
	}
	
	/** Returns the piece occupying the tile */
	public Piece getPiece() {
		return piece;
	}
	
}
