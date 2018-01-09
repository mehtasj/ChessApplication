package chessApplication;

import chessboard.BoardSimulator;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.*;

/** Draws objects on the canvas to simulate a chess board game */
public class chessGrid {

	private BoardSimulator model;
	private GraphicsContext gc;
	
	public chessGrid(BoardSimulator model, Canvas canvas) {
		this.model = model;
		gc = canvas.getGraphicsContext2D();
		drawGrid();
		drawPieces();
	}

	/** Colors the tiles and creates a grid that looks like a chessboard */
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

	public void drawPieces() {
		// TODO
		
	}
}
