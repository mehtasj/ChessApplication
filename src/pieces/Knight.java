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
		
		checkMove(MoveDir.LEFT, board, moves, col - 2, row - 1);
		checkMove(MoveDir.LEFT, board, moves, col - 2, row + 1);
		
		checkMove(MoveDir.RIGHT, board, moves, col + 2, row - 1);
		checkMove(MoveDir.RIGHT, board, moves, col + 2, row + 1);
		
		checkMove(MoveDir.FORWARD, board, moves, col + 1, row - 2);
		checkMove(MoveDir.FORWARD, board, moves, col - 1, row - 2);
		
		checkMove(MoveDir.BACKWARD, board, moves, col + 1, row + 2);
		checkMove(MoveDir.BACKWARD, board, moves, col - 1, row + 2);
		
		return moves;
	}
}
