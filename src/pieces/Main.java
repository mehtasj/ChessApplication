package pieces;

import chessboard.*;

/** Testing and debugging method */ 
public class Main {
	
	public static void main(String[] args) {
		BoardSimulator b = new BoardSimulator();
		Piece pawn = new Pawn(b, PieceColor.BLACK);
		pawn.setPosition(1, 1);
		Piece bishop = new Bishop(b, PieceColor.WHITE);
		bishop.setPosition(13,  7);
		System.out.println(pawn.getCol());
		System.out.println(bishop.getCol());
		System.out.println(pawn.getCol());
		System.out.println(bishop.getRow());
	}
}
