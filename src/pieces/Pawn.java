package pieces;

import java.util.ArrayList;
import chessboard.*;

/** Represents a pawn */
public class Pawn extends AbstractPiece {

	/** Constructs a pawn */
	public Pawn(BoardSimulator bSim, PieceColor color) {
		super(bSim, color);
	}

	// make more methods to reduce duplications and also check for null (off the board cases)
	@Override
	public ArrayList<Integer[]> getValidMoves() {
		ArrayList<Integer[]> moves = new ArrayList<>();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		
		// Special Case: Checks if this pawn can move two spaces forward from its home tile
		if (col == 6 && board.getTile(col, 4).isEmpty()) {
			Integer[] coordinate = new Integer[2];
			coordinate[0] = col;
			coordinate[1] = 4;
			moves.add(coordinate);
		}
		
		// Checks if this pawn can move one space forward
		if (canMoveToEmptySpaceAt(board, col, row - 1))
			moves.add(storeMoveTo(col, row - 1));
		
		// Checks if this pawn can capture another piece diagonally right forward
		if (canCaptureAt(board, col + 1, row - 1))
			moves.add(storeMoveTo(col + 1, row - 1));
		
		// Checks if this pawn can capture another piece diagonally left forward
		if (canCaptureAt(board, col - 1, row - 1))
			moves.add(storeMoveTo(col - 1, row - 1));
		
		return moves;
	}
	
	/** 
	 * Checks if this pawn can move to an empty tile
	 * @param b
	 * 		The board this pawn is on
	 * @param c
	 * 		The column coordinate
	 * @param r
	 * 		The row coordinate
	 * @return
	 * 		true if this piece can move to the specified tile
	 */
	public boolean canMoveToEmptySpaceAt(BoardSimulator b, int c, int r) {
		if (r >= 0 && r <= 7 && c >= 0 && c <= 7 && b.getTile(c, r).isEmpty())
			return true;
		return false;
	}
	
	/** 
	 * Checks if this pawn can capture at the specified tile
	 * because pawns capture diagonally (different from their typical move style)
	 * @param b
	 * 		The board this pawn is on
	 * @param c
	 * 		The column coordinate
	 * @param r
	 * 		The row coordinate
	 * @return
	 * 		true if this piece can move to the specified tile
	 */
	public boolean canCaptureAt(BoardSimulator b, int c, int r) {
		if (r >= 0 && r <= 7 && c >= 0 && c <= 7 && (!b.getTile(c, r).isEmpty())) {
			Piece p = b.getTile(c, r).getPiece();
			if (p != null && (!this.getColor().equals(p.getColor())))
				return true;
		}
		return false;
	}

}
