package pieces;

import java.util.ArrayList;
import chessboard.*;

/** Contains common implementations among most or all pieces */ 
public abstract class AbstractPiece implements Piece {

	/** This piece's board parent */
	private BoardSimulator pSim;
	
	/** This piece's color */
	private PieceColor pc;
	
	/** The number of times this piece has moved */
	private int moveNumber;
	
	/** This piece's column and row coordinates */
	private int col, row;
	
	/**
	 * Constructs a piece with a board parent and a color;
	 * Initializes number of moves to 0
	 * @param bSim - the board this piece is on
	 * @param color - this piece's color (black or white)
	 */
	public AbstractPiece(BoardSimulator bSim, PieceColor color) {
		this.pSim = bSim;
		this.pc = color;
		this.moveNumber = 0;
	}
	
	/** 
	 * Labels to identify this piece's move direction;
	 * Each piece moves differently
	 */
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
		
		ArrayList<Piece> opposingPieces = (this.isWhite()) ? 
			board.getBlackPieces():
			board.getWhitePieces();
				
		ArrayList<Piece> capturedPieces = (this.isWhite()) ? 
			board.getCaptBlackPieces(): 
			board.getCaptWhitePieces();
			
		calculateRefinedMoves(board, refinedMoves, 
			currTile, opposingPieces, capturedPieces);
			
		return refinedMoves;
	}
	
	@Override
	public void calculateRefinedMoves(BoardSimulator board, ArrayList<Integer[]> refinedMoves, 
				Tile currTile, ArrayList<Piece> opposingPieces, ArrayList<Piece> capturedPieces) 
	{
		for (int i = refinedMoves.size() - 1; i >= 0; i--) {
			int destCol = refinedMoves.get(i)[0];
			int destRow = refinedMoves.get(i)[1];
			Tile destTile = board.getTile(destCol, destRow);
			Piece destTilePiece = destTile.getPiece();

			boolean leadsToCheck = false;
			currTile.setPiece(null);
			destTile.setPiece(this);
			capturedPieces.add(destTilePiece);
			
			King king = calculateSameColoredKing(board);
			int kingCol = (this instanceof King) ? destCol : king.getCol();
			int kingRow = (this instanceof King) ? destRow : king.getRow();
					
			for (Piece p : opposingPieces) {
				if (p != null && !capturedPieces.contains(p)) {
					ArrayList<Integer[]> pMoves = p.getValidMoves();

					temporarilyReviseValidMovesIfPawn(board, p, pMoves);
						
					for (int j = 0; j < pMoves.size(); j++) { 
						int pDestCol = pMoves.get(j)[0];
						int pDestRow = pMoves.get(j)[1];
								
						if (pDestCol == kingCol && pDestRow == kingRow) {
							leadsToCheck = true;
							refinedMoves.remove(i);
							
							currTile.setPiece(this);
							destTile.setPiece(destTilePiece);
							capturedPieces.remove(destTilePiece);
							
							break;
						}
					}
				}
				if (leadsToCheck) break;
			}
			currTile.setPiece(this);
			destTile.setPiece(destTilePiece);
			capturedPieces.remove(destTilePiece);
		}
	}
	
	@Override
	public King calculateSameColoredKing(BoardSimulator b) {
		if (this.isWhite()) 
			return (King) b.getWhitePieces().get(0);
		else return (King) b.getBlackPieces().get(0);
	}
	
	@Override
	public King calculateOppositeColoredKing(BoardSimulator b) {
		if (this.isWhite()) 
			return (King) b.getBlackPieces().get(0);
		else return (King) b.getWhitePieces().get(0);
	}
	
	@Override
	public boolean checksOpposingKing(BoardSimulator b) {
		ArrayList<Integer[]> validMoves = this.getValidMoves();
		King king = this.calculateOppositeColoredKing(b);
		
		for (int i = 0; i < validMoves.size(); i++) {
			int destCol = validMoves.get(i)[0];
			int destRow = validMoves.get(i)[1];
			
			if (destCol == king.getCol() && destRow == king.getRow()) {
				king.setCheckState(true);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Allows the opposing pawn to capture diagonally below its position
	 * to account for not flipping the board while calculating this piece's refined moves
	 * @param board - the board this piece is on
	 * @param p - the opposing piece
	 * @param moves - the opposing piece's valid moves
	 */
	public void temporarilyReviseValidMovesIfPawn(BoardSimulator board, Piece p,
				ArrayList<Integer[]> moves) 
	{
		if (p instanceof Pawn) {
			moves = new ArrayList<>();
			
			if (p.canCaptureAt(board, p.getCol() + 1, p.getRow() + 1)) 
				moves.add(this.storeMoveTo(p.getCol() + 1, p.getRow() + 1));
			
			if (p.canCaptureAt(board, p.getCol() - 1, p.getRow() + 1))
				moves.add(this.storeMoveTo(p.getCol() - 1, p.getRow() + 1));
		}
	}
	
	@Override
	public void checkMove(MoveDir dir, BoardSimulator board,
				ArrayList<Integer[]> moves, int c, int r) 
	{
		if (this.canMoveToEmptySpaceAt(board, c, r) 
				|| this.canCaptureAt(board, c, r))
			moves.add(storeMoveTo(c, r));
	}
	
	/**
	 * Checks if a move to (c, r) is valid
	 * @param dir - this piece's potential move direction
	 * @param board - the board this piece is on
	 * @param moves - this piece's list of valid moves
	 * @param c - the column coordinate of the tile in question
	 * @param r - the row coordinate of the tile in question
	 * @return true if the tile at (c, r) is empty (continue to check tiles in that direction)
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
	 * @param b - the board this piece is on
	 * @param c - the column coordinate of the tile in question
	 * @param r - the row coordinate of the tile in question
	 * @return true if the piece at (c, r) is the same color as this piece (cannot move piece)
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
	public Integer[] storeMoveTo(int c, int r) {
		Integer[] coordinate = new Integer[2];
		coordinate[0] = c; 
		coordinate[1] = r;
		return coordinate;
	}
	
	@Override
	public void moveTo(Tile t) {
		pSim.getTile(this.col, this.row).setPiece(null);
		this.setTile(t);
	}
	
	@Override
	public void setTile(Tile t) {
		if (t != null) {
			this.col = t.getCol();
			this.row = t.getRow();
			t.setPiece(this);
		}
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
