package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;

/** Represents a bishop */
public class Bishop extends AbstractPiece {

	/** Constructs a bishop */
	public Bishop(BoardSimulator bSim, PieceColor color) {
		super(bSim, color);
	}

	@Override
	public ArrayList<Integer[]> getValidMoves() {
		ArrayList<Integer[]> moves = new ArrayList<>();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		int rowTracker;
		
		// Checks if this bishop can move to / capture at any space diagonally right and above it
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
		
		// Checks if this bishop can move to / capture at any space diagonally left and above it
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
		
		// Checks if this bishop can move to / capture at any space diagonally right and below it
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
		
		// Checks if this bishop can move to / capture at any space diagonally left and below it
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
