package hufs.ces.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OneButtonMain extends Application {

	private BorderPane root;
	private Button bigButton;

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("OneButton");

		root = new BorderPane();
		root.setPrefHeight(400);
		root.setPrefWidth(600);
		
		bigButton = new Button("Push Me!!");
		bigButton.setMaxHeight(1600);
		bigButton.setMaxWidth(2400);
		
		//MyEventHandler handler = new MyEventHandler(); // Strategy Pattern
		//bigButton.setOnAction(handler);
		/*
		bigButton.setOnAction(new EventHandler() { // anonymous class -- instance
			public void handle(Event event) {
				//handleBigButton((ActionEvent)event);				
				bigButton.setText("4.You Pushed Me.");
			}
		});
		//*/
		//bigButton.setOnAction(e->bigButton.setText("3.You Pushed Me."));
		bigButton.setOnAction(e->handleBigButton(e));// Java 8 Lambda Expression 
		
		root.setCenter(bigButton);
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();

	}
    void handleBigButton(ActionEvent event) {
    	bigButton.setText("1.You Pushed Me.");
    }

	public static void main(String[] args) {
		launch(args);
	}
	//*
	class MyEventHandler implements EventHandler {
		@Override
		public void handle(Event event) {
			//handleBigButton((ActionEvent)event);	
			bigButton.setText("2.You Pushed Me.");
		}
		
	}
	//*/
}
