package chessApplication;

import java.util.ArrayList;

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
	
	/** 
	 * Constructs a chess board
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
	
	/** 
	 * Colors the tiles to which the selected piece can move
	 * and outlines the currently selected tile on which the piece currently sits
	 * @param selectedPiece
	 * 		The currently selected piece
	 * @param moves
	 * 		A list of valid tile positions to which the selectedPiece can move
	 */
	public void highlightReachableTiles(Piece selectedPiece, ArrayList<Integer[]> moves) {
		// outlines the currently selected tile
		gc.setLineWidth(3.0);
		gc.setStroke(Color.color(1.0, 0.933, 0.259));
		gc.strokeRect(selectedPiece.getCol() * 90, selectedPiece.getRow() * 90, 90, 90);
		
		// colors the potential destination tiles
		for (int i = 0; i < moves.size(); i++) {
			int col = moves.get(i)[0];
			int row = moves.get(i)[1];
			Tile t = selectedPiece.getBoard().getTile(col, row);
			gc.setLineWidth(1.0);
			
			// tiles with pieces that can be captured are colored differently
			if (!t.isEmpty()) { gc.setFill(Color.color(0.969, 0.443, 0.443, 0.55)); }
			else { gc.setFill(Color.color(1.0, 0.933, 0.259, 0.55)); }
			
			gc.fillRect(90 * col, 90 * row, 90, 90);
		}
	}
	
	/** 
	 * Draws the chess board and the pieces on the board
	 * in their appropriate locations based on the game play 
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
				else { gc.setFill(Color.color(0.788, 0.529, 0.275)); }
				gc.fillRect(90 * c, 90 * r, 90, 90);
			}
		}
	}
	
	/** 
	 * Stores the piece images in their corresponding color arrays 
	 * for quick retrieval
	 * @param color
	 * 		The color of the piece being stored
	 * @param pieces
	 * 		The image array in which to store the images
	 */
	public void storePieces(String color, Image[] pieces) {
		pieces[0] = new Image("file:" + color + "Pawn.png", 100, 100, true, true);
		pieces[1] = new Image("file:" + color + "Rook.png", 100, 100, true, true);
		pieces[2] = new Image("file:" + color + "Knight.png", 100, 100, true, true);
		pieces[3] = new Image("file:" + color + "Bishop.png", 100, 100, true, true);
		pieces[4] = new Image("file:" + color + "Queen.png", 100, 100, true, true);
		pieces[5] = new Image("file:" + color + "King.png", 100, 100, true, true);
	}
	
	/**
	 * Draws a piece on its respective location on the chess board
	 * @param p
	 * 		The piece to be drawn
	 * @param pieces
	 * 		The image array to which the piece corresponds (white or black)
	 */
	public void drawPiece(Piece p, Image[] pieces) {
		int pieceIndex = 0;
		
		if (p instanceof Pawn) { pieceIndex = 0; }
		else if (p instanceof Rook) { pieceIndex = 1; }
		else if (p instanceof Knight) { pieceIndex = 2; }
		else if (p instanceof Bishop) { pieceIndex = 3; }
		else if (p instanceof Queen) { pieceIndex = 4; }
		else if (p instanceof King) { pieceIndex = 5; }
		
		gc.drawImage(pieces[pieceIndex], p.getCol() * 90 + 5, p.getRow() * 90 + 5, 80, 80);
	}

	/** Draws pieces at their most up-to-date locations */
	public void drawPieces() {
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
