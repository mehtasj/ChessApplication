package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;

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
		
		
		
		return moves;
	}
	
	

}
