package pieces;

import java.util.HashMap;

import chessboard.BoardSimulator;

/** Contains common implementations among all pieces */ 
public abstract class AbstractPiece implements Piece {

	/** The piece's color */
	private PieceColor pc;
	
	/** The piece's column and row location */
	private int col, row;
	
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
	public void moveTo(int col, int row) {
		this.setPosition(col, row);
	}
	
	@Override
	public Integer[] storeMoveTo(int c, int r) {
		Integer[] coordinate = new Integer[2];
		coordinate[0] = c;
		coordinate[1] = r;
		return coordinate;
	}

	@Override
	public void setPosition(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	@Override 
	public boolean isWhite() {
		if (pc == PieceColor.WHITE)
			return true;
		return false;
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
	public BoardSimulator getBoard() {
		return pSim;
	}
	
	@Override 
	public PieceColor getColor() {
		return pc;
	}

}
