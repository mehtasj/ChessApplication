package chessApplication;

import chessboard.BoardSimulator;
import chessboard.Tile;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import javafx.animation.Timeline;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pieces.Piece;

/** Includes code corresponding to specific moves on the canvas / chess GUI */
public class chessController {

	// Add javadocs for each of the following 
	
	@FXML private Menu gameMenu;
	@FXML private MenuItem newGameMenuItem, restartMenuItem;
	@FXML private Label player1Label, player2Label;
	@FXML private Text text1, text2, text3, text4, text5, text6, text7, text8;
	@FXML private Text textA, textB, textC, textD, textE, textF, textG, textH;
	@FXML private Canvas canvas;
	
	private BoardSimulator model;
	private chessGrid chessGrid;
	
	private int timesClicked, turnNumber;
	private int currCol, currRow;
	private Tile clickedTile;
	private Piece clickedPiece;
	private ArrayList<Integer[]> validMoves;
	private Piece[] capturedWhitePieces, capturedBlackPieces;
	
	/** Executes immediately after the GUI loads */
	@FXML
	public void initialize() {
		player1Label.setVisible(true);
		player2Label.setVisible(true);
		player1Label.setText("Player 1 (White): ");
		player2Label.setText("Player 2 (Black): ");
		turnNumber = 1;
		
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
				
				showTextNumbersAndLetters();
			}
		});
		
		restartMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Add an Alert that asks if you are sure you want to restart
				// model = new BoardSimulator();
				// chessGrid = new chessGrid(model, canvas);
				initialize();
			}
		});
		
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) { 
				timesClicked++;
				
				if (timesClicked == 1) { 
					currCol = (int) (event.getX() / 90);
					currRow = (int) (event.getY() / 90);
					highlightTiles(currCol, currRow);
				}
				
				if (timesClicked >= 2) {
					int destCol = (int) (event.getX() / 90);
					int destRow = (int) (event.getY() / 90);
					Tile destTile = model.getTile(destCol, destRow);
					boolean moved = false;
						
					for (int i = 0; i < validMoves.size(); i++) {
						if (destCol == validMoves.get(i)[0] && destRow == validMoves.get(i)[1]) {
							clickedPiece.moveTo(destTile);
							moved = true;
							updateBoardAppearance();
							
							// flips the board after a 1.5 second delay 
							ScheduledExecutorService boardFlip = Executors.newSingleThreadScheduledExecutor();
						    boardFlip.schedule(() -> {
						        Platform.runLater(() -> {
						        		model.flipBoard();
									updateBoardAppearance();
									flipTextNumbersAndLetters();
						        });
						    }, 1200, TimeUnit.MILLISECONDS);
					
							turnNumber++;
							timesClicked = 0;
							break;
						}
					}
						
					if (moved == false) {
						updateBoardAppearance(); // gets rid of highlights on any tiles
						currCol = (int) (event.getX() / 90);
						currRow = (int) (event.getY() / 90);
						highlightTiles(currCol, currRow);
					}
				}
			}
		});
	}
	
	/** Highlights the tiles only if a tile with a piece of the current
	 *  turn's color is selected
	 * @param currCol
	 * 			The column selected on the board
	 * @param currRow
	 * 			The row selected on the board
	 */
	public void highlightTiles(int currCol, int currRow) {
		clickedTile = model.getTile(currCol, currRow);
		clickedPiece = clickedTile.getPiece();
		
		if (clickedPiece != null) { validMoves = clickedPiece.getValidMoves(); }

		if (turnNumber % 2 == 1) {
			if (clickedPiece != null && clickedPiece.isWhite())
				chessGrid.highlightReachableTiles(clickedPiece, validMoves);
			else { timesClicked = 0; }
		}
		else {
			if (clickedPiece != null && !clickedPiece.isWhite())
				chessGrid.highlightReachableTiles(clickedPiece, validMoves);
			else { timesClicked = 0; }
		}
	}
	
	/** Redraws the chess board to depict its most updated appearance
	 *  based on the moves made
	 */
	public void updateBoardAppearance() { chessGrid.drawGridAndPieces(); }
	
	/** Shows the numbers 1 - 8 on the left side of the chess board
	 *  and the letters A - H on the bottom of the chess board
	 */
	public void showTextNumbersAndLetters() {
		text1.setVisible(true); text2.setVisible(true);
		text3.setVisible(true); text4.setVisible(true);
		text5.setVisible(true); text6.setVisible(true);
		text7.setVisible(true); text8.setVisible(true);
		
		textA.setVisible(true); textB.setVisible(true);
		textC.setVisible(true); textD.setVisible(true);
		textE.setVisible(true); textF.setVisible(true);
		textG.setVisible(true); textH.setVisible(true);
	}
	
	/** Rearranges the numbers 1 - 8 and the letters A - H
	 *  when the chess board is flipped 
	 */
	public void flipTextNumbersAndLetters() {
		if (text1.getText().equals("1")) { text1.setText("8"); textA.setText("H"); }
		else { text1.setText("1"); textA.setText("A"); }
		
		if (text2.getText().equals("2")) { text2.setText("7"); textB.setText("G"); }
		else { text2.setText("2"); textB.setText("B"); }
		
		if (text3.getText().equals("3")) { text3.setText("6"); textC.setText("F"); }
		else { text3.setText("3"); textC.setText("C"); }
		
		if (text4.getText().equals("4")) { text4.setText("5"); textD.setText("E"); }
		else { text4.setText("4"); textD.setText("D"); }
		
		if (text5.getText().equals("5")) { text5.setText("4"); textE.setText("D"); }
		else { text5.setText("5"); textE.setText("E"); }
		
		if (text6.getText().equals("6")) { text6.setText("3"); textF.setText("C"); }
		else { text6.setText("6"); textF.setText("F"); }
		
		if (text7.getText().equals("7")) { text7.setText("2"); textG.setText("B"); }
		else { text7.setText("7"); textG.setText("G"); }
		
		if (text8.getText().equals("8")) { text8.setText("1"); textH.setText("A"); }
		else { text8.setText("8"); textH.setText("H"); }
	}
}
