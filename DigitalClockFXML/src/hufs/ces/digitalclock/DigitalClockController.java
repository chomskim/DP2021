package hufs.ces.digitalclock;

import java.util.Calendar;
import java.util.Formatter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class DigitalClockController {
	
    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    void startAction(ActionEvent event) {
		setTimeDisplay();
	    // Create an animation for a running clock
	    Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), e->setTimeDisplay()));
	    animation.setCycleCount(Timeline.INDEFINITE);
	    animation.play(); // Start animation
    }
    @FXML
    void initialize() {
    	setTimeDisplay();
    }
    
	void setTimeDisplay(){
		
		Calendar calendar = Calendar.getInstance();

		StringBuilder timeText = new StringBuilder();
		Formatter buildTime = new Formatter(timeText);
		buildTime.format("%tT", calendar);
		lblTime.setText(timeText.toString());

		StringBuilder dateText = new StringBuilder();
		Formatter buildDate = new Formatter(dateText);
		buildDate.format("%1$tY.%1$tm.%1$td", calendar);
		lblDate.setText(dateText.toString());
	}

}
