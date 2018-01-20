package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;
import chessboard.Tile;
import pieces.AbstractPiece.MoveDir;

/** Represents a king */
public class King extends AbstractPiece {

	/** 
	 * Respectively keeps track of whether this king can castle 
	 * and if it has already castled
	 */
	private boolean canCastle, isAlreadyCastled;
	
	private boolean inCheck;
	
	/** Constructs a king */
	public King(BoardSimulator bSim, PieceColor color) { 
		super(bSim, color);
		this.canCastle = true;
		this.isAlreadyCastled = false;
		this.inCheck = false;
	}

	@Override
	public ArrayList<Integer[]> getValidMoves() {
		ArrayList<Integer[]> moves = new ArrayList<>();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		int moveNumber = this.getMoveNumber();
		
		checkMove(MoveDir.LEFT, board, moves, col - 1, row);
		checkMove(MoveDir.RIGHT, board, moves, col + 1, row);
		checkMove(MoveDir.FORWARD, board, moves, col, row - 1);
		checkMove(MoveDir.BACKWARD, board, moves, col, row + 1);
		
		checkMove(MoveDir.DIAGONALLY_LEFT_FORWARD, board, moves, col - 1, row - 1);
		checkMove(MoveDir.DIAGONALLY_RIGHT_FORWARD, board, moves, col + 1, row - 1);
		checkMove(MoveDir.DIAGONALLY_LEFT_BACKWARD, board, moves, col - 1, row + 1);
		checkMove(MoveDir.DIAGONALLY_RIGHT_BACKWARD, board, moves, col + 1, row + 1);
		
		// Checks if this king can castle king's side if it is white
		if (this.isWhite() && !this.inCheck) {
			Tile t = board.getTile(7, 7);
			Piece p = t.getPiece();
			
			if (moveNumber == 0 && p instanceof Rook && p.isWhite() && p.getMoveNumber() == 0) {
				if (this.canMoveToEmptySpaceAt(board, 5, 7) && p.canMoveToEmptySpaceAt(board, 6, 7))
					moves.add(storeMoveTo(6, 7));
			}
		}
		
		// Checks if this king can castle king's side if it is black 
		if (!this.isWhite() && !this.inCheck) {
			Tile t = board.getTile(0, 7);
			Piece p = t.getPiece();
			
			if (moveNumber == 0 && p instanceof Rook && !p.isWhite() && p.getMoveNumber() == 0) {
				if (this.canMoveToEmptySpaceAt(board, 2, 7) && p.canMoveToEmptySpaceAt(board, 1, 7))
					moves.add(storeMoveTo(1, 7));
			}
		}
		
		// Checks if this king can castle queen's side if it is white
		if (this.isWhite() && !this.inCheck) {
			Tile t = board.getTile(0, 7);
			Piece p = t.getPiece();
			
			if (moveNumber == 0 && p instanceof Rook && p.isWhite() && p.getMoveNumber() == 0) {
				if (this.canMoveToEmptySpaceAt(board, 3, 7) && p.canMoveToEmptySpaceAt(board, 1, 7)
						&& p.canMoveToEmptySpaceAt(board, 2, 7))
					moves.add(storeMoveTo(2, 7));
			}
		}
		
		// Checks if this king can castle queen's side if it is black
		if (!this.isWhite() && !this.inCheck) {
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
	 * Checks if this king castled; 
	 * if true, then the appropriate rook is moved to its new position
	 * @param prevTile
	 * 		The previous tile this king was on
	 * @param currTile
	 * 		The current tile this king is on
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
	 * 		The board on which the pieces are located
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
	
	/** Insert comment */
	public void setCheckState(boolean inCheck) { this.inCheck = inCheck; }
}
