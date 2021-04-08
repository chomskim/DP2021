package hufs.ces.twoclock;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class TwoAppleClockMain extends Application {
	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Two Apple Clock");

		AppleClockController root = new AppleClockController();
		root.parentStage = primaryStage;
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
