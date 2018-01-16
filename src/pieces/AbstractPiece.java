package pieces;

import java.util.ArrayList;

import chessboard.*;

/** Contains common implementations among all pieces */ 
public abstract class AbstractPiece implements Piece {

	/** This piece's parent simulator (i.e. the board it is on) */
	private BoardSimulator pSim;
	
	/** This piece's color */
	private PieceColor pc;
	
	/** Keeps track of how many times this piece has successfully moved */
	private int moveNumber;
	
	/** This piece's column and row location */
	private int col, row;
	
	/**
	 * Constructs a piece with a simulator parent node and a color
	 * @param bSim
	 * 		Board simulator node
	 * @param color
	 * 		Color of the piece (black or white)
	 */
	public AbstractPiece(BoardSimulator bSim, PieceColor color) {
		this.pSim = bSim;
		this.pc = color;
		this.moveNumber = 0;
	}
	
	@Override
	public ArrayList<Integer[]> getRefinedMoves() {
		ArrayList<Integer[]> refinedMoves = this.getValidMoves();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		Tile currTile = board.getTile(col, row);
				
		if (this.isWhite()) {
			Piece[] oppositeColoredPieces = board.getBlackPieces();
			ArrayList<Piece> blackCapturedPieces = board.getCaptBlackPieces();
			calculateRefinedMoves(board, refinedMoves, currTile, oppositeColoredPieces, blackCapturedPieces); 
		}
		else {
			Piece[] oppositeColoredPieces = board.getWhitePieces();
			ArrayList<Piece> whiteCapturedPieces = board.getCaptWhitePieces();
			calculateRefinedMoves(board, refinedMoves, currTile, oppositeColoredPieces, whiteCapturedPieces); 
		}
			
		return refinedMoves;
	}
	
	@Override
	public void calculateRefinedMoves(BoardSimulator board, ArrayList<Integer[]> refinedMoves, 
			Tile currTile, Piece[] oppositeColoredPieces, ArrayList<Piece> capturedPieces) {
		
		for (int i = refinedMoves.size() - 1; i >= 0; i--) {
			int destCol = refinedMoves.get(i)[0];
			int destRow = refinedMoves.get(i)[1];
			Tile destTile = board.getTile(destCol, destRow);
			Piece destTilePiece = destTile.getPiece();
			currTile.setPiece(null);
			destTile.setPiece(this); // temporarily move this piece to allow access to current tile
			capturedPieces.add(destTilePiece); // temporarily add piece to captured list
					
			for (Piece p : oppositeColoredPieces) {
				if (p != null && !capturedPieces.contains(p)) {
					ArrayList<Integer[]> pMoves = p.getValidMoves();
					Piece king;
					if (this.isWhite()) { king = board.getWhitePieces()[15]; }
					else { king = board.getBlackPieces()[15]; }
							
					for (int j = 0; j < pMoves.size(); j++) { 
						int pDestCol = pMoves.get(j)[0];
						int pDestRow = pMoves.get(j)[1];
								
						if (pDestCol == king.getCol() && pDestRow == king.getRow()) {
							refinedMoves.remove(i);
							currTile.setPiece(this); // move back to current tile
							destTile.setPiece(destTilePiece); // revert tile to original state
							capturedPieces.remove(destTilePiece); // remove from captured list
							break;
						}
					}
				}
			}
			currTile.setPiece(this);
			destTile.setPiece(destTilePiece);
			capturedPieces.remove(destTilePiece);
		}
	}
	
	@Override
	public void moveTo(Tile t) {
		pSim.getTile(this.col, this.row).setPiece(null);
		this.setTile(t);
	}
	
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
	public void incrementMoveNumber() { this.moveNumber++; }

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
