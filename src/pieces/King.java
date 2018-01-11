package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;
import chessboard.Tile;

/** Represents a king */
public class King extends AbstractPiece {

	/** 
	 * Keeps track of whether this king can castle 
	 * and if it has already castled
	 */
	private boolean canCastle, isAlreadyCastled;
	
	/** Constructs a king */
	public King(BoardSimulator bSim, PieceColor color) { 
		super(bSim, color);
		this.canCastle = true;
		this.isAlreadyCastled = false;
	}

	@Override
	public ArrayList<Integer[]> getValidMoves() {
		ArrayList<Integer[]> moves = new ArrayList<>();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		int moveNumber = this.getMoveNumber();
		
		// Checks if this king can move to / capture at one space ahead of it
		if (this.canMoveToEmptySpaceAt(board, col, row - 1) || this.canCaptureAt(board, col, row - 1))
			moves.add(storeMoveTo(col, row - 1));
		
		// Checks if this king can move to / capture at one space behind it
		if (this.canMoveToEmptySpaceAt(board, col, row + 1) || this.canCaptureAt(board, col, row + 1))
			moves.add(storeMoveTo(col, row + 1));
		
		// Checks if this king can move to / capture at one space to its right
		if (this.canMoveToEmptySpaceAt(board, col + 1, row) || this.canCaptureAt(board, col + 1, row))
			moves.add(storeMoveTo(col + 1, row));
		
		// Checks if this king can move to / capture at one space to its left
		if (this.canMoveToEmptySpaceAt(board, col - 1, row) || this.canCaptureAt(board, col - 1, row))
			moves.add(storeMoveTo(col - 1, row));
		
		// Checks if this king can move to / capture at one space diagonally right and above it
		if (this.canMoveToEmptySpaceAt(board, col + 1, row - 1) || this.canCaptureAt(board, col + 1, row - 1))
			moves.add(storeMoveTo(col + 1, row - 1));
		
		// Checks if this king can move to / capture at one space diagonally left and above it
		if (this.canMoveToEmptySpaceAt(board, col - 1, row - 1) || this.canCaptureAt(board, col - 1, row - 1))
			moves.add(storeMoveTo(col - 1, row - 1));
		
		// Checks if this king can move to / capture at one space diagonally right and below it
		if (this.canMoveToEmptySpaceAt(board, col + 1, row + 1) || this.canCaptureAt(board, col + 1, row + 1))
			moves.add(storeMoveTo(col + 1, row + 1));
				
		// Checks if this king can move to / capture at one space diagonally left and below it
		if (this.canMoveToEmptySpaceAt(board, col - 1, row + 1) || this.canCaptureAt(board, col - 1, row + 1))
			moves.add(storeMoveTo(col - 1, row + 1));
		
		// Checks if this king can castle king's side if it is white
		if (this.isWhite()) {
			Tile t = board.getTile(7, 7);
			Piece p = t.getPiece();
			
			if (moveNumber == 0 && p instanceof Rook && p.isWhite() && p.getMoveNumber() == 0) {
				if (this.canMoveToEmptySpaceAt(board, 5, 7) && p.canMoveToEmptySpaceAt(board, 6, 7))
					moves.add(storeMoveTo(6, 7));
			}
		}
		
		// Checks if this king can castle king's side if it is black 
		if (!this.isWhite()) {
			Tile t = board.getTile(0, 7);
			Piece p = t.getPiece();
			
			if (moveNumber == 0 && p instanceof Rook && !p.isWhite() && p.getMoveNumber() == 0) {
				if (this.canMoveToEmptySpaceAt(board, 2, 7) && p.canMoveToEmptySpaceAt(board, 1, 7))
					moves.add(storeMoveTo(1, 7));
			}
		}
		
		// Checks if this king can castle queen's side if it is white
		if (this.isWhite()) {
			Tile t = board.getTile(0, 7);
			Piece p = t.getPiece();
			
			if (moveNumber == 0 && p instanceof Rook && p.isWhite() && p.getMoveNumber() == 0) {
				if (this.canMoveToEmptySpaceAt(board, 3, 7) && p.canMoveToEmptySpaceAt(board, 1, 7)
						&& p.canMoveToEmptySpaceAt(board, 2, 7))
					moves.add(storeMoveTo(2, 7));
			}
		}
		
		// Checks if this king can castle queen's side if it is black
		if (!this.isWhite()) {
			Tile t = board.getTile(7, 7);
			Piece p = t.getPiece();
			
			if (moveNumber == 0 && p instanceof Rook && !p.isWhite() && p.getMoveNumber() == 0) {
				if (this.canMoveToEmptySpaceAt(board, 4, 7) && p.canMoveToEmptySpaceAt(board, 5, 7)
						&& p.canMoveToEmptySpaceAt(board, 6, 7))
					moves.add(storeMoveTo(5, 7));
			}
		}
		
		return moves;
	}
	
	/** 
	 * Checks if the king castled; 
	 * if true, then the appropriate rook is moved to its new position
	 * @param k
	 * 		The king that castles
	 * @param prevTile
	 * 		The previous tile the king was on
	 * @param currTile
	 * 		The current tile the king is on
	 */
	public void checkIfCastle(Tile prevTile, Tile currTile) {
		int prevCol = prevTile.getCol();
		int prevRow = prevTile.getRow();
		int currCol = currTile.getCol();
		int currRow = currTile.getRow();
		
		if (this.isWhite()) {
			if (prevCol == 4 && prevRow == 7 && currCol == 6 && currRow == 7)
				moveAppropriateRook(this.getBoard(), 7, 5);
			else if (prevCol == 4 && prevRow == 7 && currCol == 2 && currRow == 7)
				moveAppropriateRook(this.getBoard(), 0, 3);
			else { this.canCastle = false; }
		}
		else {
			if (prevCol == 3 && prevRow == 7 && currCol == 1 && currRow == 7)
				moveAppropriateRook(this.getBoard(), 0, 2);
			else if (prevCol == 3 && prevRow == 7 && currCol == 5 && currRow == 7)
				moveAppropriateRook(this.getBoard(), 7, 4);
			else { this.canCastle = false; }
		}
	}
	
	/** 
	 * Moves the correct rook to its new position on the board during the castle 
	 * @param board
	 * 		The board Simulator on which the pieces are located
	 * @param oldCol
	 * 		The current column of the appropriate rook
	 * @param newCol
	 * 		The column to which the appropriate rook will move
	 */
	public void moveAppropriateRook(BoardSimulator board, int oldCol, int newCol) {
		Tile t = board.getTile(oldCol, 7);
		Piece p = t.getPiece();
		if (p instanceof Rook && p.getMoveNumber() == 0) {
			p.moveTo(board.getTile(newCol, 7));
			p.incrementMoveNumber();
			this.isAlreadyCastled = true;
			this.canCastle = false;
		}
	}
	
	/** @return true if this king can still castle */
	public boolean canCastle() { return canCastle; }
	
	/** @return true if this king is already castled */
	public boolean isAlreadyCastled() { return isAlreadyCastled; }
}
