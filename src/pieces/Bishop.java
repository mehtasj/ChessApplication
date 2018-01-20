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
		boolean keepChecking = true;
		
		rowTracker = row;
		for (int c = col - 1; c >= 0; c--) {
			rowTracker -= 1;
			keepChecking = checkMove(MoveDir.DIAGONALLY_LEFT, board, moves, c, rowTracker);
			if (!keepChecking) break;
		}
		
		rowTracker = row;
		for (int c = col - 1; c >= 0; c--) {
			rowTracker += 1;
			keepChecking = checkMove(MoveDir.DIAGONALLY_LEFT, board, moves, c, rowTracker);	
			if (!keepChecking) break;
		}
		
		rowTracker = row;
		for (int c = col + 1; c <= 7; c++) {
			rowTracker -= 1;
			keepChecking = checkMove(MoveDir.DIAGONALLY_RIGHT, board, moves, c, rowTracker);
			if (!keepChecking) break;
		}
		
		rowTracker = row;
		for (int c = col + 1; c <= 7; c++) {
			rowTracker += 1;
			keepChecking = checkMove(MoveDir.DIAGONALLY_RIGHT, board, moves, c, rowTracker);
			if (!keepChecking) break;
		}
		
		return moves;
	}
}
