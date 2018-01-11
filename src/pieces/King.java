package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;

/** Represents a king */
public class King extends AbstractPiece {

	/** Constructs a king */
	public King(BoardSimulator bSim, PieceColor color) { 
		super(bSim, color); 
	}

	@Override
	public ArrayList<Integer[]> getValidMoves() {
		ArrayList<Integer[]> moves = new ArrayList<>();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		
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
		
		return moves;
	}
}
