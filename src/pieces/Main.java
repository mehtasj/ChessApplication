package pieces;

import chessboard.*;

/** Testing and debugging class */ 
public class Main {
	
	public static void main(String[] args) {
		BoardSimulator b = new BoardSimulator();
		
		for (Piece p : b.getWhitePieces())
			if (p != null) System.out.println(p + " " + p.getCol() + " " + p.getRow());
		
		for (Piece p : b.getBlackPieces())
			if (p != null) System.out.println(p + " " + p.getCol() + " " + p.getRow());
		
		System.out.println();
		
		b.getWhitePieces()[0] = null;
		b.getBlackPieces()[3] = null;
		
		for (Piece p : b.getWhitePieces())
			if (p != null) System.out.println(p + " " + p.getCol() + " " + p.getRow());
		
		for (Piece p : b.getBlackPieces())
			if (p != null) System.out.println(p + " " + p.getCol() + " " + p.getRow());
		
	}
}
