package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;

/** Represents a knight */
public class Knight extends AbstractPiece {

	/** Constructs a knight */
	public Knight(BoardSimulator bSim, PieceColor color) {
		super(bSim, color);
	}

	@Override
	public ArrayList<Integer[]> getValidMoves() {
		ArrayList<Integer[]> moves = new ArrayList<>();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		
		// Checks if this knight can move to / capture at two spaces ahead and one space to the right of it
		if (this.canMoveToEmptySpaceAt(board, col + 1, row - 2) || this.canCaptureAt(board, col + 1, row - 2))
			moves.add(storeMoveTo(col + 1, row - 2));
		
		// Checks if this knight can move to / capture at two spaces ahead and one space to the left of it
		if (this.canMoveToEmptySpaceAt(board, col - 1, row - 2) || this.canCaptureAt(board, col - 1, row - 2))
			moves.add(storeMoveTo(col - 1, row - 2));
		
		// Checks if this knight can move to / capture at two spaces behind and one space to the right of it
		if (this.canMoveToEmptySpaceAt(board, col + 1, row + 2) || this.canCaptureAt(board, col + 1, row + 2))
			moves.add(storeMoveTo(col + 1, row + 2));
		
		// Checks if this knight can move to / capture at two spaces ahead and one space to the left of it
		if (this.canMoveToEmptySpaceAt(board, col - 1, row + 2) || this.canCaptureAt(board, col - 1, row + 2))
			moves.add(storeMoveTo(col - 1, row + 2));
		
		// Checks if this knight can move to / capture at two spaces to the right and one space above it
		if (this.canCaptureAt(board, col + 2, row - 1) || this.canCaptureAt(board, col + 2, row - 1))
			moves.add(storeMoveTo(col + 2, row - 1));
		
		// Checks if this knight can move to / capture at two spaces to the right and one space below it
		if (this.canCaptureAt(board, col + 2, row + 1) || this.canCaptureAt(board, col + 2, row + 1))
			moves.add(storeMoveTo(col + 2, row + 1));
		
		// Checks if this knight can move to / capture at two spaces to the left and one space above it
		if (this.canCaptureAt(board, col - 2, row - 1) || this.canCaptureAt(board, col - 2, row - 1))
			moves.add(storeMoveTo(col - 2, row - 1));
				
		// Checks if this knight can move to / capture at two spaces to the left and one space below it
		if (this.canCaptureAt(board, col - 2, row + 1) || this.canCaptureAt(board, col - 2, row + 1))
			moves.add(storeMoveTo(col - 2, row + 1));
		
		return moves;
	}

}
