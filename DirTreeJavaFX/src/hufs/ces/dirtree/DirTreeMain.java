package hufs.ces.dirtree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class DirTreeMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Dir Tree");
		DirTreeController root = new DirTreeController();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
