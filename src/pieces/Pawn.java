package pieces;

import java.util.ArrayList;
import chessboard.*;

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
		
		// Special Case: Checks if this pawn can move two spaces forward from its home tile
		if (row == 6 && board.getTile(col, 4).isEmpty() && board.getTile(col, 5).isEmpty())
			moves.add(storeMoveTo(col, 4));
		
		// Checks if this pawn can move one space forward
		if (this.canMoveToEmptySpaceAt(board, col, row - 1))
			moves.add(storeMoveTo(col, row - 1));
		
		// Checks if this pawn can capture another piece diagonally right forward
		if (this.canCaptureAt(board, col + 1, row - 1))
			moves.add(storeMoveTo(col + 1, row - 1));
		
		// Checks if this pawn can capture another piece diagonally left forward
		if (this.canCaptureAt(board, col - 1, row - 1))
			moves.add(storeMoveTo(col - 1, row - 1));
		
		return moves;
	}
}
