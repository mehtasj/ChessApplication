package pieces;

import chessboard.*;

/** Contains common implementations among all pieces */ 
public abstract class AbstractPiece implements Piece {

	/** This piece's color */
	private PieceColor pc;
	
	/** This piece's column and row location */
	private int col, row;
	
	/** Keeps track of how many times this piece has successfully moved */
	private int moveNumber;
	
	/** The piece's parent simulator (i.e. the board it is on) */
	private BoardSimulator pSim;
	
	/**
	 * Constructs a piece with a simulator parent node and a color
	 * @param bSim
	 * 			Board simulator node
	 * @param color
	 * 			Color of the piece (black or white)
	 */
	public AbstractPiece(BoardSimulator bSim, PieceColor color) {
		this.pSim = bSim;
		this.pc = color;
		this.moveNumber = 0;
	}
	
	@Override
	public void moveTo(Tile t) {
		pSim.getTile(this.col, this.row).setPiece(null);
		this.setTile(t);
	}
	
	@Override
	public void incrementMoveNumber() { this.moveNumber++; }
	
	@Override
	public Integer[] storeMoveTo(int c, int r) {
		Integer[] coordinate = new Integer[2];
		coordinate[0] = c;
		coordinate[1] = r;
		return coordinate;
	}
	
	@Override
	public boolean canMoveToEmptySpaceAt(BoardSimulator b, int c, int r) {
		if (r >= 0 && r <= 7 && c >= 0 && c <= 7 && b.getTile(c, r).isEmpty()) { return true; }
		return false;
	}
	
	@Override
	public boolean canCaptureAt(BoardSimulator b, int c, int r) {
		if (r >= 0 && r <= 7 && c >= 0 && c <= 7 && (!b.getTile(c, r).isEmpty())) {
			Piece p = b.getTile(c, r).getPiece();
			if (p != null && (!this.getColor().equals(p.getColor()))) { return true; }
		}
		return false;
	}
	
	/** 
	 * Checks if the specified tile contains a piece of the same
	 * color as this piece, which would mean that this piece cannot move to 
	 * that tile (used for all pieces except for pawn)
	 * @param b
	 * 		The board this piece is on
	 * @param c
	 * 		The column coordinate
	 * @param r
	 * 		The row coordinate
	 * @return
	 * 		true if the piece on the specified tile is the same color as this piece
	 */
	public boolean isBlockedByOwnColorAt(BoardSimulator b, int c, int r) {
		if (r >= 0 && r <= 7 && c >= 0 && c <= 7 && (!b.getTile(c, r).isEmpty())) {
			Piece p = b.getTile(c, r).getPiece();
			if (p != null && (this.getColor().equals(p.getColor()))) { return true; }
		}
		return false;
	}
	
	@Override 
	public boolean isWhite() {
		if (pc == PieceColor.WHITE) { return true; }
		return false;
	}
	
	@Override
	public void setTile(Tile t) {
		this.col = t.getCol();
		this.row = t.getRow();
		t.setPiece(this);
	}

	@Override
	public int getRow() { return this.row; }

	@Override
	public int getCol() { return this.col; }
	
	@Override
	public int getMoveNumber() { return this.moveNumber; }
	
	@Override 
	public BoardSimulator getBoard() { return this.pSim; }
	
	@Override 
	public PieceColor getColor() { return this.pc; }
	
	// Deprecated
	@Override
	public void moveTo(int col, int row) { this.setPosition(col, row); }
	
	// Deprecated
	@Override
	public void setPosition(int col, int row) {
		this.col = col;
		this.row = row;
	}
}
