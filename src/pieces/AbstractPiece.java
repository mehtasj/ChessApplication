package pieces;

import java.util.ArrayList;
import chessboard.*;

/** Contains common implementations among most or all pieces */ 
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
	 * Constructs a piece with a board simulator parent and a color
	 * @param bSim - the board this piece is on
	 * @param color - the color of the piece (black or white)
	 */
	public AbstractPiece(BoardSimulator bSim, PieceColor color) {
		this.pSim = bSim;
		this.pc = color;
		this.moveNumber = 0;
	}
	
	/** Identifiers for the types of moves different pieces can make */
	public enum MoveDir { 
		LEFT, RIGHT, 
		FORWARD, BACKWARD, 
		DIAGONALLY_LEFT_FORWARD, 
		DIAGONALLY_RIGHT_FORWARD, 
		DIAGONALLY_LEFT_BACKWARD, 
		DIAGONALLY_RIGHT_BACKWARD;
	}
	
	@Override
	public ArrayList<Integer[]> getRefinedMoves() {
		ArrayList<Integer[]> refinedMoves = this.getValidMoves();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		Tile currTile = board.getTile(col, row);
		
		Piece[] opposingPieces = (this.isWhite()) ? 
			board.getBlackPieces() :
			board.getWhitePieces();
				
		ArrayList<Piece> capturedPieces = (this.isWhite()) ? 
			board.getCaptBlackPieces() : 
			board.getCaptWhitePieces();
			
		calculateRefinedMoves(board, refinedMoves, 
			currTile, opposingPieces, capturedPieces);
			
		return refinedMoves;
	}
	
	@Override
	public void calculateRefinedMoves(BoardSimulator board, ArrayList<Integer[]> refinedMoves, 
				Tile currTile, Piece[] oppositeColoredPieces, ArrayList<Piece> capturedPieces) 
	{
		for (int i = refinedMoves.size() - 1; i >= 0; i--) {
			int destCol = refinedMoves.get(i)[0];
			int destRow = refinedMoves.get(i)[1];
			Tile destTile = board.getTile(destCol, destRow);
			Piece destTilePiece = destTile.getPiece();
			currTile.setPiece(null);
			destTile.setPiece(this);
			capturedPieces.add(destTilePiece);
			boolean checkedMove = false;
			
			Piece king;
			// make a return method
			if (this.isWhite()) { king = board.getWhitePieces()[15]; }
			else { king = board.getBlackPieces()[15]; }
			
			// calculate king's row and col values
			int kingCol, kingRow;
			if (this instanceof King) { kingCol = destCol; kingRow = destRow; }
			else { kingCol = king.getCol(); kingRow = king.getRow(); }
					
			for (Piece p : oppositeColoredPieces) {
				if (p != null && !capturedPieces.contains(p)) {
					ArrayList<Integer[]> pMoves = p.getValidMoves();
					
					// corrections for pawn moves because the board was not flipped
					// separate method
					if (p instanceof Pawn) {
						pMoves = new ArrayList<>();
						if (p.canCaptureAt(board, p.getCol() + 1, p.getRow() + 1))
							pMoves.add(this.storeMoveTo(p.getCol() + 1, p.getRow() + 1));
						if (p.canCaptureAt(board, p.getCol() - 1, p.getRow() + 1))
							pMoves.add(this.storeMoveTo(p.getCol() - 1, p.getRow() + 1));
					}
							
					// separate method
					for (int j = 0; j < pMoves.size(); j++) { 
						int pDestCol = pMoves.get(j)[0];
						int pDestRow = pMoves.get(j)[1];
								
						if (pDestCol == kingCol && pDestRow == kingRow) {
							checkedMove = true;
							refinedMoves.remove(i);
							currTile.setPiece(this);
							destTile.setPiece(destTilePiece);
							capturedPieces.remove(destTilePiece);
							break;
						}
					}
				}
				if (checkedMove) { break; }
			}
			currTile.setPiece(this);
			destTile.setPiece(destTilePiece);
			capturedPieces.remove(destTilePiece);
		}
	}
	
	/**
	 * Insert comment
	 * @param dir
	 * @param board
	 * @param moves
	 * @param c
	 * @param r
	 * @return true if tile at (c, r) is empty (continue to check tiles in that direction)
	 */
	public boolean checkMoveAndIfMoreEmptySpace(MoveDir dir, BoardSimulator board, 
				   ArrayList<Integer[]> moves, int c, int r) 
	{
		if (this.isBlockedByOwnColorAt(board, c, r))
			return false;
		else if (this.canCaptureAt(board, c, r)) {
			moves.add(storeMoveTo(c, r)); 
			return false;
		}
		else if (this.canMoveToEmptySpaceAt(board, c, r))
			moves.add(storeMoveTo(c, r));
		return true;
	}
	
	@Override
	public void checkMove(MoveDir dir, BoardSimulator board,
				ArrayList<Integer[]> moves, int c, int r) 
	{
		if (this.canMoveToEmptySpaceAt(board, c, r) || this.canCaptureAt(board, c, r))
			moves.add(storeMoveTo(c, r));
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
		return (r >= 0 && r <= 7 && c >= 0 && c <= 7 
			    && b.getTile(c, r).isEmpty());
	}
	
	@Override
	public boolean canCaptureAt(BoardSimulator b, int c, int r) {
		if (r >= 0 && r <= 7 && c >= 0 && c <= 7 
			&& (!b.getTile(c, r).isEmpty())) 
		{
			Piece p = b.getTile(c, r).getPiece();
			if (!this.getColor().equals(p.getColor())) 
				return true;
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
		if (r >= 0 && r <= 7 && c >= 0 && c <= 7 
			&& (!b.getTile(c, r).isEmpty())) 
		{
			Piece p = b.getTile(c, r).getPiece();
			if (this.getColor().equals(p.getColor())) 
				return true;
		}
		return false;
	}
	
	@Override
	public void setTile(Tile t) {
		this.col = t.getCol();
		this.row = t.getRow();
		t.setPiece(this);
	}
	
	@Override 
	public boolean isWhite() { return (pc == PieceColor.WHITE); }
	
	@Override
	public void incrementMoveNumber() { this.moveNumber++; }
	
	@Override 
	public BoardSimulator getBoard() { return this.pSim; }
	
	@Override 
	public PieceColor getColor() { return this.pc; }
	
	@Override
	public int getMoveNumber() { return this.moveNumber; }

	@Override
	public int getCol() { return this.col; }
	
	@Override
	public int getRow() { return this.row; }
}
