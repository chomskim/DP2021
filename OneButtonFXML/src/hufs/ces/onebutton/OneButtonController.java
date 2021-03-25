package hufs.ces.onebutton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OneButtonController {
	
    @FXML
    private Button oneButton;

    @FXML
    void oneButtonAction(ActionEvent event) {
    	oneButton.setText("Hello World!!");
    }
}

