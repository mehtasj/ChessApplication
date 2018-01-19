package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;

/** Represents a bishop */
public class Bishop extends AbstractPiece {

	/** Constructs a bishop */
	public Bishop(BoardSimulator bSim, PieceColor color) {
		super(bSim, color);
	}
	
	/** Insert comment */
	public enum MoveDir { DIAGONALLY_LEFT, DIAGONALLY_RIGHT; }

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
	
	/**
	 * Insert comment
	 * @param dir
	 * @param board
	 * @param moves
	 * @param c
	 * @param r
	 * @return true if tile at (c, r) is empty (can continue to check tiles on that diagonal)
	 */
	public boolean checkMove(MoveDir dir, BoardSimulator board, 
				   ArrayList<Integer[]> moves, int c, int r) 
	{
		if (this.isBlockedByOwnColorAt(board, c, r))
			return false;
		else if (this.canCaptureAt(board, c, r)) {
			moves.add(storeMoveTo(c, r)); 
			return false;
		}
		else if (this.canMoveToEmptySpaceAt(board, c, r))
			moves.add(storeMoveTo(c, r));
		return true;
	}
}
