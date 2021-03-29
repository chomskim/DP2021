package hufs.ces.grimpanz;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GrimPanZFXMLMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("GrimPan");

		GrimPanFXController root = new GrimPanFXController();
		root.parentStage = primaryStage;
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
