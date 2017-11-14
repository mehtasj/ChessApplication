package pieces;

import java.util.List;

import chessboard.BoardSimulator;

/** Contains common implementations among all pieces */ 
public abstract class AbstractPiece implements Piece {

	/** The piece's color */
	private PieceColor pc;
	
	/** The piece's row and column location */
	private int row, col;
	
	/** The piece's parent simulator (i.e. the board it is on) */
	private BoardSimulator pSim;
	
	/**
	 * Constructs a piece with a simulator parent node 
	 * and a color
	 * @param bSim
	 * 			Board simulator node
	 * @param color
	 * 			Color of the piece (black or white)
	 */
	public AbstractPiece(BoardSimulator bSim, PieceColor color) {
		pSim = bSim;
		pc = color;
	}

	@Override
	public void setPosition(int col, int row) {
		this.col = col;
		this.row = row;
	}

	@Override
	public int getRow() { 
		return row; 
	}

	@Override
	public int getCol() { 
		return col; 
	}

	@Override
	public List getValidMoves() {
		// TODO Auto-generated method stub
		return null;
	}

}
