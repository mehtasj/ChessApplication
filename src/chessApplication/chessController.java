package chessApplication;

import chessboard.BoardSimulator;
import chessboard.Tile;

import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.paint.Color;
import pieces.Piece;

/** Includes code corresponding to specific moves on the canvas / chess GUI */
public class chessController {

	@FXML private Menu gameMenu;
	@FXML private MenuItem newGameMenuItem, restartMenuItem;
	@FXML private Label player1Label, player2Label;
	@FXML private Canvas canvas;
	
	private BoardSimulator model;
	private chessGrid chessGrid;
	private int timesClicked;
	private int turnNumber;
	
	private int currCol, currRow;
	private Tile clickedTile;
	private Piece clickedPiece;
	private ArrayList<Integer[]> validMoves;
	private Piece[] deadWhitePieces, deadBlackPieces;
	
	/** Executes immediately after the GUI loads */
	@FXML
	public void initialize() {
		
		player1Label.setVisible(true);
		player2Label.setVisible(true);
		player1Label.setText("Player 1 (White): ");
		player2Label.setText("Player 2 (Black): ");
		timesClicked = 0;
		turnNumber = 1; // odd number = white's turn; even number = black's turn
		
		currCol = 0;
		currRow = 0;
		clickedTile = null;
		clickedPiece = null;
		validMoves = null;
		
		equipButtons(); 
	}

	/** Implementing event handlers for all the components in the scene */
	private void equipButtons() {
		
		newGameMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				TextInputDialog player1 = new TextInputDialog();
				player1.setTitle("Player 1 Name");
				player1.setHeaderText("White");
				player1.setContentText("Please enter Player 1's name:");
				Optional<String> p1 = player1.showAndWait();
				
				player1Label.setText("Player 1 (White): " + p1.get());
				
				TextInputDialog player2 = new TextInputDialog();
				player2.setTitle("Player 2 Name");
				player2.setHeaderText("Black");
				player2.setContentText("Please enter Player 2's name:");
				Optional<String> p2 = player2.showAndWait();
				
				player2Label.setText("Player 2 (Black): " + p2.get());
				
				model = new BoardSimulator();
				chessGrid = new chessGrid(model, canvas);
				
			}
		});
		
		restartMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				// Add a TextInputDialog that asks if you are sure you want to restart
				// model = new BoardSimulator();
				// chessGrid = new chessGrid(model, canvas);
				initialize();
			}
		});
		
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) { 
				// TODO
				timesClicked++;
				
				// if it is white's turn or if it is black's turn
				if (timesClicked == 1) { 
					currCol = (int) (event.getX() / 90);
					currRow = (int) (event.getY() / 90);
					
					highlightTiles(currCol, currRow);
					
					//player1Label.setText(clickedTile.getPiece().toString());
					//player2Label.setText(clickedTile.getPiece().getCol() + " " + clickedTile.getPiece().getRow());
				}
				
				if (timesClicked >= 2) {
					if (turnNumber % 2 == 1) {
						int destCol = (int) (event.getX() / 90);
						int destRow = (int) (event.getY() / 90);
						Tile destTile = model.getTile(destCol, destRow);
						boolean moved = false;
						
						for (int i = 0; i < validMoves.size(); i++) {
							if (destCol == validMoves.get(i)[0] && destRow == validMoves.get(i)[1]) {
								clickedPiece.moveTo(destTile);
								moved = true;
								chessGrid.drawGridAndPieces();
							}
						}
						
						if (moved == false) {
							chessGrid.drawGridAndPieces();
							currCol = (int) (event.getX() / 90);
							currRow = (int) (event.getY() / 90);
							
							highlightTiles(currCol, currRow);
							
							//player1Label.setText(clickedTile.getPiece().toString());
							//player2Label.setText(clickedTile.getPiece().getCol() + " " + clickedTile.getPiece().getRow());
						}
						
					}
				}
			}
		});
	}
	
	public void highlightTiles(int currCol, int currRow) {
		clickedTile = model.getTile(currCol, currRow);
		clickedPiece = clickedTile.getPiece();
		
		if (clickedPiece != null) 
			validMoves = clickedPiece.getValidMoves();
		
		if (turnNumber % 2 == 1) {
			if (clickedPiece != null && clickedPiece.isWhite())
				chessGrid.highlightReachableTiles(clickedPiece, validMoves);
			else timesClicked = 0;
		}
		else {
			if (clickedPiece != null && !clickedPiece.isWhite())
				chessGrid.highlightReachableTiles(clickedPiece, validMoves);
			else timesClicked = 0;
		}
	}
}
