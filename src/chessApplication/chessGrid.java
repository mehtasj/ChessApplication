package chessApplication;

import chessboard.BoardSimulator;
import chessboard.Tile;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pieces.*;
import javafx.scene.image.*;

/** Draws objects on the canvas to simulate a chess board game */
public class chessGrid {

	/** Simulates the chess board */
	private BoardSimulator model;
	
	/** Draws elements on the canvas */
	private GraphicsContext gc;
	
	/** Store white and black piece image files respectively */
	private Image[] whitePieces, blackPieces;
	
	/** Constructs a chess board
	 * @param model
	 * 		The BoardSimulator used in the chessController class
	 * @param canvas
	 * 		The canvas on which to draw the board and pieces
	 */
	public chessGrid(BoardSimulator model, Canvas canvas) {
		this.model = model;
		gc = canvas.getGraphicsContext2D();
		whitePieces = new Image[6];
		blackPieces = new Image[6];
		storePieces("White", whitePieces);
		storePieces("Black", blackPieces);
		drawGridAndPieces();
	}
	
	/** Draws the chess board and the pieces on the board
	 *  in their appropriate locations based on the game play 
	 */
	public void drawGridAndPieces() {
		drawGrid();
		drawPieces();
	}

	/** Colors the tiles and creates a grid that looks like a chess board */
	public void drawGrid() {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if ((r % 2 == 0 && c % 2 == 0) || (r % 2 != 0 && c % 2 != 0))
					gc.setFill(Color.color(0.976, 0.804, 0.635));
				else
					gc.setFill(Color.color(0.788, 0.529, 0.275));
				gc.fillRect(90 * r, 90 * c, 90, 90);
			}
		}
	}
	
	/** Stores the piece images in their corresponding color arrays 
	 *  for quick retrieval
	 * @param color
	 * 			The color of the piece being stored
	 * @param pieces
	 * 			The image array in which to store the images
	 */
	public void storePieces(String color, Image[] pieces) {
		pieces[0] = new Image("file:" + color + "Pawn.png", 90, 90, true, true);
		pieces[1] = new Image("file:" + color + "Rook.png", 90, 90, true, true);
		pieces[2] = new Image("file:" + color + "Knight.png", 90, 90, true, true);
		pieces[3] = new Image("file:" + color + "Bishop.png", 90, 90, true, true);
		pieces[4] = new Image("file:" + color + "Queen.png", 90, 90, true, true);
		pieces[5] = new Image("file:" + color + "King.png", 90, 90, true, true);
	}
	
	/** Draws a piece on its respective location on the chess board
	 * @param p
	 * 		The piece to be drawn
	 * @param pieces
	 * 		The image array to which the piece corresponds (white or black)
	 */
	public void drawPiece(Piece p, Image[] pieces) {
		if (p instanceof Pawn)
			gc.drawImage(pieces[0], p.getCol() * 90 + 5, p.getRow() * 90 + 5, 80, 80);
		else if (p instanceof Rook)
			gc.drawImage(pieces[1], p.getCol() * 90 + 5, p.getRow() * 90 + 5, 80, 80);
		else if (p instanceof Knight)
			gc.drawImage(pieces[2], p.getCol() * 90 + 5, p.getRow() * 90 + 5, 80, 80);
		else if (p instanceof Bishop)
			gc.drawImage(pieces[3], p.getCol() * 90 + 5, p.getRow() * 90 + 5, 80, 80);
		else if (p instanceof Queen)
			gc.drawImage(pieces[4], p.getCol() * 90 + 5, p.getRow() * 90 + 5, 80, 80);
		else if (p instanceof King)
			gc.drawImage(pieces[5], p.getCol() * 90 + 5, p.getRow() * 90 + 5, 80, 80);
	}

	/** Iterates through the 64 chess board tiles and draws pieces 
	 *  at their current locations
	 */
	public void drawPieces() {
		// TODO
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				Tile t = model.getTile(c, r);
				if (!t.isEmpty()) {
					Piece p = t.getPiece();
					if (p.isWhite()) { drawPiece(p, whitePieces); }
					else { drawPiece(p, blackPieces); }
				}
			}
		}
	}
}
