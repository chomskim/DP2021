package hufs.ces.clock;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class AppleClockWithTitle extends BorderPane {

	private Label cityLabel = null;
	private AppleClockPane clock = null;
	
	private String city = null;
	
	public AppleClockWithTitle(String city) {
		this.city = city;
		initialize();
	}
	
	void initialize() {
		cityLabel = new Label(city);
		
		clock = new AppleClockPane();
		
		this.setTop(cityLabel);
		this.setCenter(clock);
		
	}
	
	ClockPane getClock() {
		return clock;
	}
}
