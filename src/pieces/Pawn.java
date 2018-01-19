package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;

/** Represents a pawn */
public class Pawn extends AbstractPiece {

	/** Constructs a pawn */
	public Pawn(BoardSimulator bSim, PieceColor color) {
		super(bSim, color);
	}
	
	@Override
	public ArrayList<Integer[]> getValidMoves() {
		ArrayList<Integer[]> moves = new ArrayList<>();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		
		// special case: if this pawn can move forward 2 spaces
		if (row == 6 && board.getTile(col, 4).isEmpty() 
				&& board.getTile(col, 5).isEmpty())
			moves.add(storeMoveTo(col, 4));
		
		if (this.canMoveToEmptySpaceAt(board, col, row - 1))
			moves.add(storeMoveTo(col, row - 1));
		
		if (this.canCaptureAt(board, col + 1, row - 1))
			moves.add(storeMoveTo(col + 1, row - 1));
		
		if (this.canCaptureAt(board, col - 1, row - 1))
			moves.add(storeMoveTo(col - 1, row - 1));
		
		return moves;
	}
}
