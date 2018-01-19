package chessboard;


import java.util.ArrayList;
import pieces.*;

/** Keeps track of the state of the board and its pieces */
public class BoardSimulator {

	/** Represents an n x n board of tiles */
	private Tile[][] board;
	
	/** 
	 * Stores the 16 white and 16 black pieces in their 
	 * respective colored piece arrays 
	 */
	private Piece[] whitePieces, blackPieces;
	
	/**
	 * Stores the captured white and black pieces in their
	 * respective colored lists
	 */
	private ArrayList<Piece> captWhitePieces, captBlackPieces;
	
	/** Constructs a simulator of a chess board */
	public BoardSimulator() {
		board = new Tile[8][8];
		initializeTiles();
		initializeBlackPieces();
		initializeWhitePieces();
		captWhitePieces = new ArrayList<>();
		captBlackPieces = new ArrayList<>();
	}
	
	/**
	 * @param c
	 * 		The column the requested tile is in
	 * @param r
	 * 		The row the requested tile is in
	 * @return
	 * 		The tile at (c, r)
	 */
	public Tile getTile(int c, int r) {
		if (board[r][c] == null)
			board[r][c] = new Tile(this, c, r);
		return board[r][c];
	}
	
	/** 
	 * Rotates the board 180 degrees by moving pieces
	 * to allow both players to play from their POV
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
	
	/** Initializes all the tiles on the chess board */
	public void initializeTiles() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++)
				board[r][c] = new Tile(this, c, r);
		}
	}
	
	/** Initializing all black pieces and setting their default tile positions */
	public void initializeBlackPieces() {
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
		
		bRook1.setTile(board[0][0]); bKnight1.setTile(board[0][1]);
		bBishop1.setTile(board[0][2]); bQueen.setTile(board[0][3]);
		bKing.setTile(board[0][4]); bBishop2.setTile(board[0][5]);
		bKnight2.setTile(board[0][6]); bRook2.setTile(board[0][7]);
	    bPawn1.setTile(board[1][0]); bPawn2.setTile(board[1][1]);
		bPawn3.setTile(board[1][2]); bPawn4.setTile(board[1][3]);
		bPawn5.setTile(board[1][4]); bPawn6.setTile(board[1][5]);
		bPawn7.setTile(board[1][6]); bPawn8.setTile(board[1][7]);
		
		blackPieces = new Piece[16];
		
		blackPieces[0] = bPawn1; blackPieces[1] = bPawn2; blackPieces[2] = bPawn3; blackPieces[3] = bPawn4;
		blackPieces[4] = bPawn5; blackPieces[5] = bPawn6; blackPieces[6] = bPawn7; blackPieces[7] = bPawn8;
		blackPieces[8] = bRook1; blackPieces[9] = bRook2; blackPieces[10] = bKnight1; blackPieces[11] = bKnight2;
		blackPieces[12] = bBishop1; blackPieces[13] = bBishop2; blackPieces[14] = bQueen; blackPieces[15] = bKing;
	}
	
	/** Initializing all white pieces and setting their default tile positions */
	public void initializeWhitePieces() {
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
		
		wRook1.setTile(board[7][0]); wKnight1.setTile(board[7][1]);
		wBishop1.setTile(board[7][2]); wQueen.setTile(board[7][3]);
		wKing.setTile(board[7][4]); wBishop2.setTile(board[7][5]);
		wKnight2.setTile(board[7][6]); wRook2.setTile(board[7][7]);
	    wPawn1.setTile(board[6][0]); wPawn2.setTile(board[6][1]);
		wPawn3.setTile(board[6][2]); wPawn4.setTile(board[6][3]);
		wPawn5.setTile(board[6][4]); wPawn6.setTile(board[6][5]);
		wPawn7.setTile(board[6][6]); wPawn8.setTile(board[6][7]);
		
		whitePieces = new Piece[16];
		
		whitePieces[0] = wPawn1; whitePieces[1] = wPawn2; whitePieces[2] = wPawn3; whitePieces[3] = wPawn4;
		whitePieces[4] = wPawn5; whitePieces[5] = wPawn6; whitePieces[6] = wPawn7; whitePieces[7] = wPawn8;
		whitePieces[8] = wRook1; whitePieces[9] = wRook2; whitePieces[10] = wKnight1; whitePieces[11] = wKnight2;
		whitePieces[12] = wBishop1; whitePieces[13] = wBishop2; whitePieces[14] = wQueen; whitePieces[15] = wKing;
	}
	
	/** @return the board of tiles */
	public Tile[][] getBoard() { return board; }
	
	/** @return the white pieces */
	public Piece[] getWhitePieces() { return whitePieces; }
	
	/** @return the black pieces */
	public Piece[] getBlackPieces() { return blackPieces; }
	
	/** @return the captured white pieces */
	public ArrayList<Piece> getCaptWhitePieces() { return captWhitePieces; }
	
	/** @return the captured black pieces */
	public ArrayList<Piece> getCaptBlackPieces() { return captBlackPieces; }
	
}
