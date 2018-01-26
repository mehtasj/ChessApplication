package chessboard;

import java.util.ArrayList;
import pieces.*;

/** Keeps track of the state of the board and its pieces */
public class BoardSimulator {

	/** Represents an 8 x 8 chess board of tiles */
	private Tile[][] board;
	
	/** Stores the white and black pieces in their respective lists */
	private ArrayList<Piece> whitePieces, blackPieces;
	
	/** Stores the captured white and black pieces in their respective lists */
	private ArrayList<Piece> captWhitePieces, captBlackPieces;
	
	/** Constructs a chess board */
	public BoardSimulator() {
		board = new Tile[8][8];
		initializeTiles();
		initializeWhitePieces();
		initializeBlackPieces();
		captWhitePieces = new ArrayList<>();
		captBlackPieces = new ArrayList<>();
	}
	
	/**
	 * @param c - The requested tile's column coordinate
	 * @param r - the requested tile's row coordinate
	 * @return the tile at (c, r)
	 */
	public Tile getTile(int c, int r) {
		if (board[r][c] == null)
			board[r][c] = new Tile(this, c, r);
		return board[r][c];
	}
	
	/** 
	 * Rotates the board 180 degrees to allow 
	 * both players to play from their POV 
	 */
	public void flipBoard() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length / 2; c++) {
				Piece p = board[r][c].getPiece();
				Piece p2 = board[7-r][7-c].getPiece();
				
				if (p != null) 
					p.setTile(board[7-r][7-c]);
				else board[7-r][7-c].setPiece(null);
				
				if (p2 != null) 
					p2.setTile(board[r][c]);
				else board[r][c].setPiece(null); 
			}
		}
	}
	
	/**
	 * Checks if there is a Checkmate, which means the game is over
	 * @param coloredPieces - the pieces of the threatened king's color
	 * @param captColoredPieces - the pieces that were captured of the threatened king's color
	 * @return true if there are no more possible moves for the current player (CHECKMATE)
	 */
	public boolean isCheckmate(ArrayList<Piece> coloredPieces, ArrayList<Piece> captColoredPieces) {
		int possibleMoves = 0;
			
		for (Piece p : coloredPieces) {
			if (p != null && !captColoredPieces.contains(p)) {
				ArrayList<Integer[]> pRefinedMoves = p.getRefinedMoves();
				possibleMoves += pRefinedMoves.size();
			}
		}
		return (possibleMoves == 0);
	}
		
	/**
	 * Checks if a rook, bishop, and/or queen of the clicked piece's color
	 * threaten(s) the opposite color's king after the clicked piece has moved;
	 * Do not need to check pawns or knights as they can only Check 
	 * once they move (i.e. if they are the clicked piece moving into an updated position)
	 * @param coloredPieces - the array of pieces with the same color as the clicked piece
	 * @param captColoredPieces - the pieces that were captured of the same color as the clicked piece
	 */
	public boolean checkExists(ArrayList<Piece> coloredPieces, ArrayList<Piece> captColoredPieces) {
		boolean checkFound = false;
			
		for (Piece p : coloredPieces) {
			if (p != null && !captColoredPieces.contains(p) 
					&& (p instanceof Rook || p instanceof Bishop || p instanceof Queen))
				checkFound = p.checksOpposingKing(p.getBoard());
			
			if (checkFound) return true;
		}
		return false;
	}

	/** Initializes all the tiles on the chess board */
	public void initializeTiles() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++)
				board[r][c] = new Tile(this, c, r);
		}
	}
	
	/** Initializes all white pieces and sets their default tile positions */
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
		
		whitePieces = new ArrayList<>();
		
		whitePieces.add(wKing); 
		whitePieces.add(wPawn1); 
		whitePieces.add(wPawn2);
		whitePieces.add(wPawn3);
		whitePieces.add(wPawn4);
		whitePieces.add(wPawn5);
		whitePieces.add(wPawn6);
		whitePieces.add(wPawn7);
		whitePieces.add(wPawn8);
		whitePieces.add(wRook1);
		whitePieces.add(wRook2);
		whitePieces.add(wKnight1);
		whitePieces.add(wKnight2);
		whitePieces.add(wBishop1);
		whitePieces.add(wBishop2);
		whitePieces.add(wQueen);
	}
	
	/** Initializes all black pieces and sets their default tile positions */
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
		
		blackPieces = new ArrayList<>();
		
		blackPieces.add(bKing); 
		blackPieces.add(bPawn1); 
		blackPieces.add(bPawn2);
		blackPieces.add(bPawn3);
		blackPieces.add(bPawn4);
		blackPieces.add(bPawn5);
		blackPieces.add(bPawn6);
		blackPieces.add(bPawn7);
		blackPieces.add(bPawn8);
		blackPieces.add(bRook1);
		blackPieces.add(bRook2);
		blackPieces.add(bKnight1);
		blackPieces.add(bKnight2);
		blackPieces.add(bBishop1);
		blackPieces.add(bBishop2);
		blackPieces.add(bQueen);
	}
	
	/** @return the board of tiles */
	public Tile[][] getBoard() { return board; }
	
	/** @return the white pieces */
	public ArrayList<Piece> getWhitePieces() { return whitePieces; }
	
	/** @return the black pieces */
	public ArrayList<Piece> getBlackPieces() { return blackPieces; }
	
	/** @return the captured white pieces */
	public ArrayList<Piece> getCaptWhitePieces() { return captWhitePieces; }
	
	/** @return the captured black pieces */
	public ArrayList<Piece> getCaptBlackPieces() { return captBlackPieces; }
	
}
