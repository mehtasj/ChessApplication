package pieces;

import chessboard.*;

/** Testing and debugging method */ 
public class Main {
	
	public static void main(String[] args) {
		BoardSimulator b = new BoardSimulator();
		
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				Piece p = b.getTile(c, r).getPiece();
				System.out.println(p);
				if (p != null) {
					System.out.println(p.getColor());
					System.out.println(p.getCol());
					System.out.println(p.getRow());
				}
			}
		}

		b.flipBoard();
		
		System.out.println();
		
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				Piece p = b.getTile(c, r).getPiece();
				System.out.println(p);
				if (p != null) {
					System.out.println(p.getColor());
					System.out.println(p.getCol());
					System.out.println(p.getRow());
				}
			}
		}
		
	}
}
