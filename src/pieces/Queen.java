package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;
import chessboard.Tile;
import pieces.AbstractPiece.MoveDir;

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
		
		rowTracker = row;
		for (int c = col - 1; c >= 0; c--) {
			rowTracker -= 1;
			keepChecking = checkMoveAndIfMoreEmptySpace
				(MoveDir.DIAGONALLY_LEFT_FORWARD, board, moves, c, rowTracker);
			if (!keepChecking) break;
		}
		
		rowTracker = row;
		for (int c = col - 1; c >= 0; c--) {
			rowTracker += 1;
			keepChecking = checkMoveAndIfMoreEmptySpace
				(MoveDir.DIAGONALLY_LEFT_BACKWARD, board, moves, c, rowTracker);	
			if (!keepChecking) break;
		}
		
		rowTracker = row;
		for (int c = col + 1; c <= 7; c++) {
			rowTracker -= 1;
			keepChecking = checkMoveAndIfMoreEmptySpace
				(MoveDir.DIAGONALLY_RIGHT_FORWARD, board, moves, c, rowTracker);
			if (!keepChecking) break;
		}
		
		rowTracker = row;
		for (int c = col + 1; c <= 7; c++) {
			rowTracker += 1;
			keepChecking = checkMoveAndIfMoreEmptySpace
				(MoveDir.DIAGONALLY_RIGHT_BACKWARD, board, moves, c, rowTracker);
			if (!keepChecking) break;
		}
		
		return moves;
	}
}
