package hufs.ces.grimpan0;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class GrimPanFXController extends AnchorPane {
	public GrimPanFXController() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("grimpan0.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
