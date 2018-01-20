package pieces;

import java.util.ArrayList;
import chessboard.BoardSimulator;
import chessboard.Tile;

/** Represents a king */
public class King extends AbstractPiece {

	/** 
	 * Respectively keeps track of whether this king 
	 * can castle and if it has already castled
	 */
	private boolean canCastle, isAlreadyCastled;
	
	/** Keeps track of whether this king is in Check */
	private boolean inCheck;
	
	/** Constructs a king */
	public King(BoardSimulator bSim, PieceColor color) { 
		super(bSim, color);
		this.canCastle = true;
		this.isAlreadyCastled = false;
		this.inCheck = false;
	}

	@Override
	public ArrayList<Integer[]> getValidMoves() {
		ArrayList<Integer[]> moves = new ArrayList<>();
		BoardSimulator board = this.getBoard();
		int col = this.getCol();
		int row = this.getRow();
		int moveNumber = this.getMoveNumber();
		int rookCol;
		PieceColor kColor = this.getColor();
		
		checkMove(MoveDir.LEFT, board, moves, col - 1, row);
		checkMove(MoveDir.RIGHT, board, moves, col + 1, row);
		checkMove(MoveDir.FORWARD, board, moves, col, row - 1);
		checkMove(MoveDir.BACKWARD, board, moves, col, row + 1);
		
		checkMove(MoveDir.DIAGONALLY_LEFT_FORWARD, board, moves, col - 1, row - 1);
		checkMove(MoveDir.DIAGONALLY_RIGHT_FORWARD, board, moves, col + 1, row - 1);
		checkMove(MoveDir.DIAGONALLY_LEFT_BACKWARD, board, moves, col - 1, row + 1);
		checkMove(MoveDir.DIAGONALLY_RIGHT_BACKWARD, board, moves, col + 1, row + 1);
		
		if (!this.inCheck) {
			rookCol = (this.isWhite()) ? 7 : 0;
			Tile t = board.getTile(rookCol, 7);
			Piece p = t.getPiece();
			
			if (rookCol == 7) 
				checkKingsideCastle(board, p, moves, 
					moveNumber, kColor, rookCol - 2, rookCol - 1);
			else if (rookCol == 0)
				checkKingsideCastle(board, p, moves, 
					moveNumber, kColor, rookCol + 2, rookCol + 1);
		}
		
		if (!this.inCheck) {
			rookCol = (this.isWhite()) ? 0 : 7;
			Tile t = board.getTile(rookCol, 7);
			Piece p = t.getPiece();
			
			if (rookCol == 7) 
				checkQueensideCastle(board, p, moves, 
					moveNumber, kColor, rookCol - 3, rookCol - 2, rookCol - 1);
			else if (rookCol == 0)
				checkQueensideCastle(board, p, moves,
					moveNumber, kColor, rookCol + 3, rookCol + 2, rookCol + 1);
		}
		
		return moves;
	}
	
	/**
	 * Insert comment
	 * @param board
	 * @param p
	 * @param moves
	 * @param moveNumber
	 * @param kColor
	 * @param kCol
	 * @param rCol
	 */
	public void checkKingsideCastle(BoardSimulator board, Piece p, ArrayList<Integer[]> moves, 
				int moveNumber, PieceColor kColor, int kCol, int rCol) 
	{
		if (moveNumber == 0 && p instanceof Rook && p.getColor().equals(kColor) 
			&& p.getMoveNumber() == 0) 
		{
			if (this.canMoveToEmptySpaceAt(board, kCol, 7) 
					&& p.canMoveToEmptySpaceAt(board, rCol, 7))
				moves.add(storeMoveTo(rCol, 7));
		}
	}
	
	/**
	 * Insert comment
	 * @param board
	 * @param p
	 * @param moves
	 * @param moveNumber
	 * @param kColor
	 * @param kCol
	 * @param rCol1
	 * @param rCol2
	 */
	public void checkQueensideCastle(BoardSimulator board, Piece p, ArrayList<Integer[]> moves, 
				int moveNumber, PieceColor kColor, int kCol, int rCol1, int rCol2)
	{
		if (moveNumber == 0 && p instanceof Rook && p.getColor().equals(kColor) 
			&& p.getMoveNumber() == 0) 
		{
			if (this.canMoveToEmptySpaceAt(board, kCol, 7) 
					&& p.canMoveToEmptySpaceAt(board, rCol1, 7)
					&& p.canMoveToEmptySpaceAt(board, rCol2, 7))
				moves.add(storeMoveTo(rCol1, 7));
		}
	}
	
	/** 
	 * Checks if this king castled when it moves for the first time; 
	 * if true, then the appropriate rook is moved to its new position
	 * @param prevTile
	 * 		The previous tile this king was on
	 * @param currTile
	 * 		The current tile this king is on
	 */ 
	public void checkIfCastle(Tile prevTile, Tile currTile) {
		int prevCol = prevTile.getCol();
		int prevRow = prevTile.getRow();
		int currCol = currTile.getCol();
		int currRow = currTile.getRow();
		
		if (this.isWhite()) {
			if (prevCol == 4 && prevRow == 7 && currCol == 6 && currRow == 7)
				moveAppropriateRook(this.getBoard(), 7, currCol - 1);
			else if (prevCol == 4 && prevRow == 7 && currCol == 2 && currRow == 7)
				moveAppropriateRook(this.getBoard(), 0, currCol + 1);
			else { this.canCastle = false; }
		}
		else {
			if (prevCol == 3 && prevRow == 7 && currCol == 1 && currRow == 7)
				moveAppropriateRook(this.getBoard(), 0, currCol + 1);
			else if (prevCol == 3 && prevRow == 7 && currCol == 5 && currRow == 7)
				moveAppropriateRook(this.getBoard(), 7, currCol - 1);
			else { this.canCastle = false; }
		}
	}
	
	/** 
	 * Moves the correct rook to its new position on the board during the castle 
	 * @param board
	 * 		The board on which the pieces are located
	 * @param oldCol
	 * 		The current column of the appropriate rook
	 * @param newCol
	 * 		The column to which the appropriate rook will move
	 */
	public void moveAppropriateRook(BoardSimulator board, int oldCol, int newCol) {
		Tile t = board.getTile(oldCol, 7);
		Piece p = t.getPiece();
		
		if (p instanceof Rook && p.getMoveNumber() == 0) {
			p.moveTo(board.getTile(newCol, 7));
			p.incrementMoveNumber();
			this.isAlreadyCastled = true;
			this.canCastle = false;
		}
	}
	
	/**
	 * Assigns a boolean value to the Check state of this king
	 * @param inCheck
	 * 		true if this king is in Check
	 */
	public void setCheckState(boolean inCheck) { this.inCheck = inCheck; }
	
	/** @return true if this king can still castle */
	public boolean canCastle() { return canCastle; }
	
	/** @return true if this king is already castled */
	public boolean isAlreadyCastled() { return isAlreadyCastled; }
}
