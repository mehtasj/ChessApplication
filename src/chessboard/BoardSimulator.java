package chessboard;

import java.util.ArrayList;
import pieces.*;

/** Keeps track of the state of the board
 * and its pieces
 */
public class BoardSimulator {

	/** Represents an n x n board of tiles */
	private Tile[][] board;
	
	public BoardSimulator() {
		board = new Tile[8][8];
		
		// Initializing black pieces
		Piece bRook1 = new Rook(this, PieceColor.BLACK);
		Piece bKnight1 = new Knight(this, PieceColor.BLACK);
		Piece bBishop1 = new Bishop(this, PieceColor.BLACK);
		Piece bQueen = new Queen(this, PieceColor.BLACK);
		Piece bKing = new King(this, PieceColor.BLACK);
		Piece bBishop2 = new Bishop(this, PieceColor.BLACK);
		Piece bKnight2 = new Knight(this, PieceColor.BLACK);
		Piece bRook2 = new Rook(this, PieceColor.BLACK);
		Piece bPawn1 = new Pawn(this, PieceColor.BLACK);
		Piece bPawn2 = new Pawn(this, PieceColor.BLACK);
		Piece bPawn3 = new Pawn(this, PieceColor.BLACK);
		Piece bPawn4 = new Pawn(this, PieceColor.BLACK);
		Piece bPawn5 = new Pawn(this, PieceColor.BLACK);
		Piece bPawn6 = new Pawn(this, PieceColor.BLACK);
		Piece bPawn7 = new Pawn(this, PieceColor.BLACK);
		Piece bPawn8 = new Pawn(this, PieceColor.BLACK);
			
		// Intializing white pieces 
		Piece wRook1 = new Rook(this, PieceColor.WHITE);
		Piece wKnight1 = new Knight(this, PieceColor.WHITE);
		Piece wBishop1 = new Bishop(this, PieceColor.WHITE);
		Piece wKing = new King(this, PieceColor.WHITE);
		Piece wQueen = new Queen(this, PieceColor.WHITE);
		Piece wBishop2 = new Bishop(this, PieceColor.WHITE);
		Piece wKnight2 = new Knight(this, PieceColor.WHITE);
		Piece wRook2 = new Rook(this, PieceColor.WHITE);
		Piece wPawn1 = new Pawn(this, PieceColor.WHITE);
		Piece wPawn2 = new Pawn(this, PieceColor.WHITE);
		Piece wPawn3 = new Pawn(this, PieceColor.WHITE);
		Piece wPawn4 = new Pawn(this, PieceColor.WHITE);
		Piece wPawn5 = new Pawn(this, PieceColor.WHITE);
		Piece wPawn6 = new Pawn(this, PieceColor.WHITE);
		Piece wPawn7 = new Pawn(this, PieceColor.WHITE);
		Piece wPawn8 = new Pawn(this, PieceColor.WHITE);
		
		// Initializing tiles 
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++)
				board[r][c] = new Tile(this, c, r);
		}
		
		// Setting default black piece tile positions
		bRook1.setTile(board[0][0]);
		bKnight1.setTile(board[0][1]);
		bBishop1.setTile(board[0][2]);
		bQueen.setTile(board[0][3]);
		bKing.setTile(board[0][4]);
		bBishop2.setTile(board[0][5]);
		bKnight2.setTile(board[0][6]);
		bRook2.setTile(board[0][7]);
	    bPawn1.setTile(board[1][0]);
		bPawn2.setTile(board[1][1]);
		bPawn3.setTile(board[1][2]);
		bPawn4.setTile(board[1][3]);
		bPawn5.setTile(board[1][4]);
		bPawn6.setTile(board[1][5]);
		bPawn7.setTile(board[1][6]);
		bPawn8.setTile(board[1][7]);
		
		// Setting default white piece tile positions
		wRook1.setTile(board[7][0]);
		wKnight1.setTile(board[7][1]);
		wBishop1.setTile(board[7][2]);
		wQueen.setTile(board[7][3]);
		wKing.setTile(board[7][4]);
		wBishop2.setTile(board[7][5]);
		wKnight2.setTile(board[7][6]);
		wRook2.setTile(board[7][7]);
	    wPawn1.setTile(board[6][0]);
		wPawn2.setTile(board[6][1]);
		wPawn3.setTile(board[6][2]);
		wPawn4.setTile(board[6][3]);
		wPawn5.setTile(board[6][4]);
		wPawn6.setTile(board[6][5]);
		wPawn7.setTile(board[6][6]);
		wPawn8.setTile(board[6][7]);
	}
	
	/**
	 * @param col
	 * 		The column the requested tile is in
	 * @param row
	 * 		The row the requested tile is in
	 * @return
	 * 		The tile at (col, row)
	 */
	public Tile getTile(int col, int row) {
		if (board[row][col] == null)
			board[row][col] = new Tile(this, col, row);
		return board[row][col];
	}
	
	/** Rotates the board 180 degrees by moving pieces
	 *  to allow both players to play from their POV
	 */
	public void flipBoard() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length / 2; c++) {
				Piece p = board[r][c].getPiece();
				Piece p2 = board[7-r][7-c].getPiece();
				
				if (p != null) p.setTile(board[7-r][7-c]);
				else { board[7-r][7-c].setPiece(null); } 
				
				if (p2 != null) { p2.setTile(board[r][c]); }
				else { board[r][c].setPiece(null); } 
			}
		}
	}
	
	/** Returns the board of tiles */
	public Tile[][] getBoard() {
		return board;
	}
}
