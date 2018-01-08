package chessApplication;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Prepares to launch the chess application */
public class chessApplicationLauncher extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
	         URL r = getClass().getResource("ChessApplicationUI.fxml");
	         if (r == null) throw new Exception("No FXML resource found.");
	         FXMLLoader loader = new FXMLLoader(r);
	         loader.setController(new chessController());
	         Scene scene = new Scene(loader.load());
	         primaryStage.setTitle("Chess");
	         primaryStage.setScene(scene);
	         primaryStage.sizeToScene();
	         primaryStage.setResizable(false);
	         primaryStage.show();
	      } catch (Exception e) {
	         System.out.println(e.getMessage());
	         e.printStackTrace();
	      }
	}
}
