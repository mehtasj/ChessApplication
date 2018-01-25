package chessApplication;

import chessboard.BoardSimulator;
import chessboard.Tile;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import pieces.*;

/** Includes code corresponding to specific moves on the canvas / chess GUI */
public class chessController {
	
	@FXML private Menu gameMenu;
	@FXML private MenuItem newGameMenuItem, restartMenuItem;
	@FXML private Label player1Label, player2Label;
	@FXML private Label whiteCapturedPiecesLabel, blackCapturedPiecesLabel;
	@FXML private Label player1PointsLabel, player2PointsLabel;
	@FXML private Text text1, text2, text3, text4, text5, text6, text7, text8;
	@FXML private Text textA, textB, textC, textD, textE, textF, textG, textH;
	@FXML private Text textWPQ, textWRQ, textWNQ, textWBQ, textWQQ;
	@FXML private Text textBPQ, textBRQ, textBNQ, textBBQ, textBQQ;
	@FXML private Text textPlayer1Points, textPlayer2Points;
	@FXML private Text textPlayer1Check, textPlayer2Check;
	@FXML private ImageView whitePawnIV, whiteRookIV, whiteKnightIV, whiteBishopIV, whiteQueenIV;
	@FXML private ImageView blackPawnIV, blackRookIV, blackKnightIV, blackBishopIV, blackQueenIV;
	@FXML private Shape leftBorder, rightBorder, topBorder, bottomBorder;
	@FXML private Canvas canvas;
	
	private int captWPCounter, captWRCounter, captWNCounter, captWBCounter, captWQCounter;
	private int captBPCounter, captBRCounter, captBNCounter, captBBCounter, captBQCounter;
	private int whitePoints, blackPoints;
	
	private int timesClicked, turnNumber;
	private int currCol, currRow; 
	
	private Tile clickedTile;
	private Piece clickedPiece;
	private ArrayList<Integer[]> refinedMoves;
	
	private BoardSimulator model;
	private chessGrid chessGrid;
	
	/** Executes immediately after the GUI loads */
	@FXML
	public void initialize() {
		enableOrDisableBorders(false);
		enableOrDisableQuantityAndPointCounters(false);
		enableOrDisableTextNumbersAndLetters(false);
		disableCapturedPieceImages();
		initializeCapturedPieceAndPointCounters();
		
		player1Label.setVisible(true); 
		player2Label.setVisible(true);
		player1Label.setText("Player 1 (White): "); 
		player2Label.setText("Player 2 (Black): ");
		
		textPlayer1Check.setText("");
		textPlayer2Check.setText("");
		
		turnNumber = 1;
		
		equipButtons(); 
	}

	/** Implementing event handlers for all the components in the scene */
	private void equipButtons() {
		
		newGameMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				requestPlayerNames();
				
				model = new BoardSimulator();
				chessGrid = new chessGrid(model, canvas);
				
				enableOrDisableBorders(true);
				enableOrDisableQuantityAndPointCounters(true);
				enableOrDisableTextNumbersAndLetters(true);
				enableCapturedPieceImages();
				initializeCapturedPieceAndPointCounters();
				
				textPlayer1Check.setText("");
				textPlayer2Check.setText("");
				
				turnNumber = 1;
			}
		});
		
		restartMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert restart = new Alert(AlertType.CONFIRMATION);
				restart.setContentText("Are you sure you want to restart the game?");
				Optional<ButtonType> result = restart.showAndWait();
				
				if (result.get().equals(ButtonType.OK)) {
					initialize();
					requestPlayerNames();
					
					model = new BoardSimulator();
					chessGrid = new chessGrid(model, canvas);
					
					enableOrDisableBorders(true);
					enableOrDisableQuantityAndPointCounters(true);
					enableOrDisableTextNumbersAndLetters(true);
					enableCapturedPieceImages();
					
					if (!text1.getText().equals("1")) 
						flipTextNumbersAndLetters();
				}
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
						
					for (int i = 0; i < refinedMoves.size(); i++) {
						if (destCol == refinedMoves.get(i)[0] && destRow == refinedMoves.get(i)[1]) {
							if (!destTile.isEmpty()) { storeCapturedPieceAndUpdatePoints(destTile.getPiece()); }
							
							clickedPiece.moveTo(destTile);
							clickedPiece.incrementMoveNumber();
							moved = true;
							
							// make method
							if (textPlayer1Check.getText().equals("CHECK!")) { 
								textPlayer1Check.setText("");
								King king = (King) model.getWhitePieces().get(0);
								king.setCheckState(false);
							}
							if (textPlayer2Check.getText().equals("CHECK!")) { 
								textPlayer2Check.setText(""); 
								King king = (King) model.getBlackPieces().get(0);
								king.setCheckState(false);
							}
							
							// check if move was a castle so the appropriate rook moves as well
							// make a separate method for clarity and to remove comments
							if (clickedPiece instanceof King) {
								King king = (King) clickedPiece;
								if (!king.isAlreadyCastled() && king.canCastle())
									king.checkIfCastle(clickedTile, destTile);
							} 
							
							// check if pawn reaches the end of the board for promotion
							// make a separate method for clarity and to remove comments
							
							
							// check if moving a piece will put other color's king in check
							// make a method
							if (!clickedPiece.checksOpposingKing(model)) {
								if (clickedPiece.isWhite())
									model.checkIfCheckExists(model.getWhitePieces(), model.getCaptWhitePieces()); 
								else model.checkIfCheckExists(model.getBlackPieces(), model.getCaptBlackPieces());
							}
							else changeTextIfCheck();
							
							updateBoardAppearance();
							delayThenFlip();
							
							turnNumber++;
							timesClicked = 0;
							
							break;
						}
					}
					
					if (!moved) {
						updateBoardAppearance();
						currCol = (int) (event.getX() / 90);
						currRow = (int) (event.getY() / 90);
						highlightTiles(currCol, currRow);
					}
				}
			}
		});
	}
	
	/** 
	 * Highlights the tiles only if a tile with a piece of 
	 * the current turn's color is selected
	 * @param currCol - the selected column
	 * @param currRow - the selected row
	 */
	public void highlightTiles(int currCol, int currRow) {
		clickedTile = model.getTile(currCol, currRow);
		clickedPiece = clickedTile.getPiece();
		
		if (clickedPiece != null) 
			refinedMoves = clickedPiece.getRefinedMoves();
		
		if (refinedMoves.size() == 0) { 
			timesClicked = 0; 
			return; 
		}

		if (turnNumber % 2 == 1) {
			if (clickedPiece != null && clickedPiece.isWhite())
				chessGrid.highlightReachableTiles(clickedPiece, refinedMoves);
			else timesClicked = 0;
		}
		else {
			if (clickedPiece != null && !clickedPiece.isWhite())
				chessGrid.highlightReachableTiles(clickedPiece, refinedMoves);
			else timesClicked = 0;
		}
	}
	
	/** Flips the board after a one second delay */
	public void delayThenFlip() {
		ScheduledExecutorService boardFlip = Executors.newSingleThreadScheduledExecutor();
	    boardFlip.schedule(() -> {
	        Platform.runLater(() -> {
	        		
	        		model.flipBoard();
				updateBoardAppearance();
				flipTextNumbersAndLetters();
				changeTextIfCheckmate();
				
	        });
	    }, 1000, TimeUnit.MILLISECONDS);
	}
	
	/** Says "CHECKMATE!" under the losing player's name */
	public void changeTextIfCheckmate() {
		if (textPlayer1Check.getText().equals("CHECK!")) {
			if (model.isCheckmate(model.getWhitePieces(), model.getCaptWhitePieces()))
				textPlayer1Check.setText("CHECKMATE!");
		}
		else if (textPlayer2Check.getText().equals("CHECK!")) { 
			if (model.isCheckmate(model.getBlackPieces(), model.getCaptBlackPieces()))
				textPlayer2Check.setText("CHECKMATE!");
		}
	}
	
	/** Says "CHECK!" under the threatened player's name */
	public void changeTextIfCheck() {
		if (clickedPiece.isWhite()) 
			textPlayer2Check.setText("CHECK!");
		else textPlayer1Check.setText("CHECK!");
	}
	
	/**
	 * Updates the captured pieces count given a newly captured piece;
	 * Also updates the appropriate point counter
	 * @param captPiece - the piece that was captured
	 */
	public void storeCapturedPieceAndUpdatePoints(Piece captPiece) {
		if (captPiece.isWhite()) {
			if (captPiece instanceof Pawn) { textWPQ.setText(String.valueOf(++captWPCounter)); blackPoints++; }
			else if (captPiece instanceof Rook) { textWRQ.setText(String.valueOf(++captWRCounter)); blackPoints += 5; }
			else if (captPiece instanceof Knight) { textWNQ.setText(String.valueOf(++captWNCounter)); blackPoints += 3; }
			else if (captPiece instanceof Bishop) { textWBQ.setText(String.valueOf(++captWBCounter)); blackPoints += 3; }
			else if (captPiece instanceof Queen) { textWQQ.setText(String.valueOf(++captWQCounter)); blackPoints += 9; }
			
			updatePoints(blackPoints, textPlayer2Points);
			model.getCaptWhitePieces().add(captPiece);
		}
		else {
			if (captPiece instanceof Pawn) { textBPQ.setText(String.valueOf(++captBPCounter)); whitePoints++; }
			else if (captPiece instanceof Rook) { textBRQ.setText(String.valueOf(++captBRCounter)); whitePoints += 5; }
			else if (captPiece instanceof Knight) { textBNQ.setText(String.valueOf(++captBNCounter)); whitePoints += 3; }
			else if (captPiece instanceof Bishop) { textBBQ.setText(String.valueOf(++captBBCounter)); whitePoints += 3; }
			else if (captPiece instanceof Queen) { textBQQ.setText(String.valueOf(++captBQCounter)); whitePoints += 9; }
			
			updatePoints(whitePoints, textPlayer1Points);
			model.getCaptBlackPieces().add(captPiece);
		}
	}
	
	/**
	 * Updates the appropriate player's point counter
	 * @param points - the point counter to update
	 * @param textPlayerPoints - displays the updated score
	 */
	public void updatePoints(int points, Text textPlayerPoints) {
		if (points < 10) 
			textPlayerPoints.setText("0" + points);
		else textPlayerPoints.setText(String.valueOf(points));
	}
	
	/** Initializes all the captured piece counters and the point counters to 0 */
	public void initializeCapturedPieceAndPointCounters() {
		captWPCounter = 0; captWRCounter = 0; captWNCounter = 0; captWBCounter = 0; captWQCounter = 0;
		captBPCounter = 0; captBRCounter = 0; captBNCounter = 0; captBBCounter = 0; captBQCounter = 0;
		
		textWPQ.setText(String.valueOf(captWPCounter)); textWRQ.setText(String.valueOf(captWRCounter));
		textWNQ.setText(String.valueOf(captWNCounter)); textWBQ.setText(String.valueOf(captWBCounter));
		textWQQ.setText(String.valueOf(captWQCounter)); 
		
		textBPQ.setText(String.valueOf(captBPCounter)); textBRQ.setText(String.valueOf(captBRCounter)); 
		textBNQ.setText(String.valueOf(captBNCounter)); textBBQ.setText(String.valueOf(captBBCounter)); 
		textBQQ.setText(String.valueOf(captBQCounter));
		
		whitePoints = 0; 
		blackPoints = 0;
		
		textPlayer1Points.setText("00"); 
		textPlayer2Points.setText("00");
	}
	
	/** Displays the captured piece images under each player's name */
	public void enableCapturedPieceImages() {
		whitePawnIV.setImage(new Image("file:WhitePawn.png", 100, 100, true, true)); 
		whiteRookIV.setImage(new Image("file:WhiteRook.png", 100, 100, true, true));
		whiteKnightIV.setImage(new Image("file:WhiteKnight.png", 100, 100, true, true));
		whiteBishopIV.setImage(new Image("file:WhiteBishop.png", 100, 100, true, true));
		whiteQueenIV.setImage(new Image("file:WhiteQueen.png", 100, 100, true, true));
		
		blackPawnIV.setImage(new Image("file:BlackPawn.png", 100, 100, true, true));
		blackRookIV.setImage(new Image("file:BlackRook.png", 100, 100, true, true));
		blackKnightIV.setImage(new Image("file:BlackKnight.png", 100, 100, true, true));
		blackBishopIV.setImage(new Image("file:BlackBishop.png", 100, 100, true, true));
		blackQueenIV.setImage(new Image("file:BlackQueen.png", 100, 100, true, true));
	}
	
	/** Hides the captured piece images under each player's name */
	public void disableCapturedPieceImages() {
		whitePawnIV.setImage(null); 
		whiteRookIV.setImage(null); 
		whiteKnightIV.setImage(null); 
		whiteBishopIV.setImage(null); 
		whiteQueenIV.setImage(null);
		
		blackPawnIV.setImage(null); 
		blackRookIV.setImage(null); 
		blackKnightIV.setImage(null); 
		blackBishopIV.setImage(null); 
		blackQueenIV.setImage(null);
	}
	
	/** 
	 * Controls the visibility of the captured piece quantity counters 
	 * and the point value counters 
	 * @param enable - true if labels should be visible
	 */
	public void enableOrDisableQuantityAndPointCounters(boolean enable) {
		textWPQ.setVisible(enable); textWRQ.setVisible(enable); 
		textWNQ.setVisible(enable); textWBQ.setVisible(enable); 
		textWQQ.setVisible(enable);
		
		textBPQ.setVisible(enable); textBRQ.setVisible(enable); 
		textBNQ.setVisible(enable); textBBQ.setVisible(enable); 
		textBQQ.setVisible(enable);
		
		player1PointsLabel.setVisible(enable); 
		player2PointsLabel.setVisible(enable);
		
		textPlayer1Points.setVisible(enable); 
		textPlayer2Points.setVisible(enable);
	}
	
	/** 
	 * Controls the visibility of the chess board's borders
	 * @param enable - true if borders should be visible
	 */
	public void enableOrDisableBorders(boolean enable) {
		leftBorder.setVisible(enable); 
		rightBorder.setVisible(enable);
		topBorder.setVisible(enable); 
		bottomBorder.setVisible(enable);	
	}
	
	/** 
	 * Controls the visibility of the numbers 1 - 8 on the left side of the chess board 
	 * and the letters A - H on the bottom of the chess board
	 * @param enable
	 * 		true if numbers and letters should be visible and false otherwise
	 */
	public void enableOrDisableTextNumbersAndLetters(boolean enable) {
		text1.setVisible(enable); text2.setVisible(enable); 
		text3.setVisible(enable); text4.setVisible(enable);
		text5.setVisible(enable); text6.setVisible(enable); 
		text7.setVisible(enable); text8.setVisible(enable);
		
		textA.setVisible(enable); textB.setVisible(enable); 
		textC.setVisible(enable); textD.setVisible(enable);
		textE.setVisible(enable); textF.setVisible(enable); 
		textG.setVisible(enable); textH.setVisible(enable);
	}
	
	/** Rearranges the numbers 1 - 8 and the letters A - H when the chess board flips */
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
	
	/** 
	 * Prompts the players to enter their names for 
	 * Player 1 (White) and Player 2 (Black) 
	 */
	public void requestPlayerNames() {
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
	}
	
	/** 
	 * Redraws the chess board to depict its most 
	 * updated appearance based on the moves made 
	 */
	public void updateBoardAppearance() { chessGrid.drawGridAndPieces(); }
}
