package chessApplication;

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

/** Includes code corresponding to specific moves on the canvas / chess GUI */
public class chessController {

	@FXML private Menu newGameMenu, restartMenu;
	@FXML private Label player1Label, player2Label;
	
	@FXML private Canvas canvas;
	
	/** Executes immediately after the GUI loads */
	public void initialize() {
		
		player1Label.setVisible(true);
		player2Label.setVisible(true);
		player1Label.setText("Player 1: ");
		player2Label.setText("Player 2 ");
		
		equipButtons(); 
	}

	/** Implementing event handlers for all the components in the scene */
	private void equipButtons() {
		
		newGameMenu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TextInputDialog player1 = new TextInputDialog();
				player1.setTitle("Player 1 Name");
				player1.setHeaderText("White");
				player1.setContentText("Please enter Player 1's name:");
				Optional<String> p1 = player1.showAndWait();
				
				player1Label.setText("Player 1 (White): " + p1.get());
				
				TextInputDialog player2 = new TextInputDialog();
				player1.setTitle("Player 2 Name");
				player1.setHeaderText("Black");
				player1.setContentText("Please enter Player 2's name:");
				Optional<String> p2 = player1.showAndWait();
				
				player1Label.setText("Player 2 (Black): " + p2.get());
			}
		});
		
		restartMenu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				initialize();
			}
		});
		
		
	}
}
