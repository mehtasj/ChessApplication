package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;

/** Represents a rook */
public class Rook extends AbstractPiece {

	/** Constructs a rook */
	public Rook(BoardSimulator bSim, PieceColor color) {
		super(bSim, color);
	}

	@Override
	public ArrayList<Integer[]> getValidMoves() {
		ArrayList<Integer[]> moves = new ArrayList<>();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		
		// Checks if this rook can move to / capture at any space ahead of it
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
		
		// Checks if this rook can move to / capture at any space behind it
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
		
		// Checks if this rook can move to / capture at any space to its right
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
		
		// Checks if this rook can move to / capture at any space to its left
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
		
		return moves;
	}
}
