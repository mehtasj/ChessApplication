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
		boolean keepChecking = true;
		
		for (int c = col - 1; c >= 0; c--) {
			keepChecking = checkMoveAndIfMoreEmptySpace
					(MoveDir.LEFT, board, moves, c, row);
			if (!keepChecking) break;
		}
		
		for (int c = col + 1; c <= 7; c++) {
			keepChecking = checkMoveAndIfMoreEmptySpace
					(MoveDir.RIGHT, board, moves, c, row);
			if (!keepChecking) break;
		}
		
		for (int r = row - 1; r >= 0; r--) {
			keepChecking = checkMoveAndIfMoreEmptySpace
					(MoveDir.FORWARD, board, moves, col, r);
			if (!keepChecking) break;
		}
		
		for (int r = row + 1; r <= 7; r++) {
			keepChecking = checkMoveAndIfMoreEmptySpace
					(MoveDir.BACKWARD, board, moves, col, r);
			if (!keepChecking) break;
		}
		
		return moves;
	}
}
