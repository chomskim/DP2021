package hufs.ces.grimpan0;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class GrimPan0Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("GrimPan0");
		
		GrimPan0FXController root = new GrimPan0FXController();
		root.parentStage = primaryStage;
		
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
