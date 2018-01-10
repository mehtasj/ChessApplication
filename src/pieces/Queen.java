package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;

/** Represents a queen */
public class Queen extends AbstractPiece {

	/** Constructs a queen */
	public Queen(BoardSimulator bSim, PieceColor color) {
		super(bSim, color);
	}

	@Override
	public ArrayList<Integer[]> getValidMoves() {
		ArrayList<Integer[]> moves = new ArrayList<>();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		int rowTracker;
		
		// Movements equivalent to those of a rook
		
		// Checks if this queen can move to / capture at any space ahead of it
		for (int r = row - 1; r >= 0; r--) {
			if (this.isBlockedByOwnColorAt(board, col, r))
				break;
			else if (this.canCaptureAt(board, col, r)) {
				moves.add(storeMoveTo(col, r));
				break;
			}
			else if (this.canMoveToEmptySpaceAt(board, col, r))
				moves.add(storeMoveTo(col, r));
		}
		
		// Checks if this queen can move to / capture at any space behind it
		for (int r = row + 1; r <= 7; r++) {
			if (this.isBlockedByOwnColorAt(board, col, r))
				break;
			else if (this.canCaptureAt(board, col, r)) {
				moves.add(storeMoveTo(col, r));
				break;
			}
			else if (this.canMoveToEmptySpaceAt(board, col, r))
				moves.add(storeMoveTo(col, r));
		}
		
		// Checks if this queen can move to / capture at any space to its right
		for (int c = col + 1; c <= 7; c++) {
			if (this.isBlockedByOwnColorAt(board, c, row))
				break;
			else if (this.canCaptureAt(board, c, row)) {
				moves.add(storeMoveTo(c, row));
				break;
			}
			else if (this.canMoveToEmptySpaceAt(board, c, row))
				moves.add(storeMoveTo(c, row));
		}
		
		// Checks if this queen can move to / capture at any space to its left
		for (int c = col - 1; c >= 0; c--) {
			if (this.isBlockedByOwnColorAt(board, c, row))
				break;
			else if (this.canCaptureAt(board, c, row)) {
				moves.add(storeMoveTo(c, row));
				break;
			}
			else if (this.canMoveToEmptySpaceAt(board, c, row))
				moves.add(storeMoveTo(c, row));
		}
		
		// Movements equivalent to those of a bishop
		
		// Checks if this queen can move to / capture at any space diagonally right and above it
		rowTracker = row;
		for (int c = col + 1; c <= 7; c++) {
			rowTracker -= 1;
			if (this.isBlockedByOwnColorAt(board, c, rowTracker))
				break;
			else if (this.canCaptureAt(board, c, rowTracker)) {
				moves.add(storeMoveTo(c, rowTracker));
				break;
			}
			else if (this.canMoveToEmptySpaceAt(board, c, rowTracker))
				moves.add(storeMoveTo(c, rowTracker));	
		}
		
		// Checks if this queen can move to / capture at any space diagonally left and above it
		rowTracker = row;
		for (int c = col - 1; c >= 0; c--) {
			rowTracker -= 1;
			if (this.isBlockedByOwnColorAt(board, c, rowTracker))
				break;
			else if (this.canCaptureAt(board, c, rowTracker)) {
				moves.add(storeMoveTo(c, rowTracker));
				break;
			}
			else if (this.canMoveToEmptySpaceAt(board, c, rowTracker))
				moves.add(storeMoveTo(c, rowTracker));	
		}
		
		// Checks if this queen can move to / capture at any space diagonally right and below it
		rowTracker = row;
		for (int c = col + 1; c <= 7; c++) {
			rowTracker += 1;
			if (this.isBlockedByOwnColorAt(board, c, rowTracker))
				break;
			else if (this.canCaptureAt(board, c, rowTracker)) {
				moves.add(storeMoveTo(c, rowTracker));
				break;
			}
			else if (this.canMoveToEmptySpaceAt(board, c, rowTracker))
				moves.add(storeMoveTo(c, rowTracker));	
		}
		
		// Checks if this queen can move to / capture at any space diagonally left and below it
		rowTracker = row;
		for (int c = col - 1; c >= 0; c--) {
			rowTracker += 1;
			if (this.isBlockedByOwnColorAt(board, c, rowTracker))
				break;
			else if (this.canCaptureAt(board, c, rowTracker)) {
				moves.add(storeMoveTo(c, rowTracker));
				break;
			}
			else if (this.canMoveToEmptySpaceAt(board, c, rowTracker))
				moves.add(storeMoveTo(c, rowTracker));	
		}
		
		return moves;
	}

}
