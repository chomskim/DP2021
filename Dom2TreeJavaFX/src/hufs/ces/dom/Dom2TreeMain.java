package hufs.ces.dom;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Dom2TreeMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("XML Tree");
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("dom2tree.fxml"));
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
