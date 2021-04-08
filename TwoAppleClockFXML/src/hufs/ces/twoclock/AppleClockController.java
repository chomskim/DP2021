package hufs.ces.twoclock;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AppleClockController extends AnchorPane {

	public Stage parentStage;
	
	ClockPane leftClock = null;
	ClockPane rightClock = null;
	
	public AppleClockController() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("twoappleclock.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initClocks();
	}
	
    public void initClocks() {
    	
    	leftClock = new AppleClockPane();
    	rightClock = new AppleClockPane();
    	
		// Create a handler for animation
		EventHandler<ActionEvent> eventHandler = e -> {
			leftClock.setCurrentTime(); // Set a new clock time
			rightClock.setCurrentTime(); // Set a new clock time
		};

		// Create an animation for a running clock
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play(); // Start animation

		paneClockLeft.setCenter(leftClock);
		paneClockRight.setCenter(rightClock);
		    	
    }

    @FXML
    private Label lblCityLeft;

    @FXML
    private Label lblCityRight;

    @FXML
    private BorderPane paneClockLeft;

    @FXML
    private BorderPane paneClockRight;

}
