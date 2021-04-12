package hufs.ces.grimpan.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.JColorChooser;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class GrimPanFXController extends AnchorPane {

	private DrawPane drawPane;
	
	public Stage parentStage;
	private GrimPanModel model;
	
	DoubleProperty widthProp = new SimpleDoubleProperty();
	DoubleProperty heightProp = new SimpleDoubleProperty();
	
	ColorPicker fcolorPicker = new ColorPicker(Color.WHITE);
	ColorPicker scolorPicker = new ColorPicker(Color.BLACK);
	
	public GrimPanFXController() {

		model = GrimPanModel.getInstance();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/grimpancmd.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fcolorPicker.setOnAction(t->{
			Color color = fcolorPicker.getValue();
			model.setShapeFillColor(color);
		});
		Label fcolorLabel =  new Label("", fcolorPicker);
		fcolorLabel.setContentDisplay(ContentDisplay.RIGHT);
		fcolorLabel.setStyle("-fx-padding: 0 0 0 15;");
		menuFillColor.setGraphic(fcolorLabel);

		scolorPicker.setOnAction(t->{
			Color color = scolorPicker.getValue();
			model.setShapeStrokeColor(color);
		});
		Label scolorLabel =  new Label("", scolorPicker);
		scolorLabel.setContentDisplay(ContentDisplay.RIGHT);
		scolorLabel.setStyle("-fx-padding: 0 0 0 15;");
		menuStrokeColor.setGraphic(scolorLabel);
		
		drawPane = new DrawPane();
		drawPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		drawPane.setOnMousePressed(e->handleMousePressed(e));
		drawPane.setOnMouseReleased(e->handleMouseReleased(e));
		drawPane.setOnMouseDragged(e->handleMouseDragged(e));
		drawFramePane.setCenter(drawPane);

		widthProp.bind(drawPane.widthProperty());
		widthProp.addListener((obs, oldVal, newVal) -> {
			double val = ((ObservableDoubleValue)obs).get();
			System.out.format("drawPane w=%s newVal=%s \n", val, newVal);
		});
		heightProp.bind(drawPane.heightProperty());
		heightProp.addListener((obs, oldVal, newVal) -> {
			double val = ((ObservableDoubleValue)obs).get();
			System.out.format("drawPane h=%s newVal=%s \n", val, newVal);
		});
		
		model.shapeList.addListener((ListChangeListener<Shape>) c-> {
	        while (c.next()) {
	            if (c.wasAdded()) {
	            	System.out.println("Shape Count ="+model.shapeList.size());
	            }
	            if (c.wasRemoved()) {
	            	System.out.println("Shape Count ="+model.shapeList.size());
	            }
	        }
		});
		
		initDrawPane();
	}
	void initDrawPane() {		
		model.shapeList.clear();
		model.curDrawShape = null;
		drawPane.redraw();
	}
	
    @FXML
    private AnchorPane root;

    @FXML
    private BorderPane drawFramePane;

	
    @FXML
    private MenuItem menuNew;

    @FXML
    private MenuItem menuExit;

    @FXML
    private RadioMenuItem menuLine;

    @FXML
    private RadioMenuItem menuPencil;

    @FXML
    private MenuItem menuMove;

    @FXML
    private MenuItem menuDelete;

    @FXML
    private MenuItem menuStrokeWidth;

    @FXML
    private MenuItem menuStrokeColor;

    @FXML
    private MenuItem menuFillColor;

    @FXML
    private CheckMenuItem menuCheckStroke;

    @FXML
    private CheckMenuItem menuCheckFill;

    @FXML
    private MenuItem menuAbout;

    
    @FXML
    private Label lblWinSize;

    @FXML
    private Label lblEditState;

    @FXML
    private Label lblShapeCount;

    @FXML
    void handleMenuAbout(ActionEvent event) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(parentStage);   
		alert.setTitle("About");
		alert.setHeaderText("GrimPan Ver 0.1.1");
		alert.setContentText("Programmed by cskim, ces, hufs.ac.kr");

		alert.showAndWait();
    }

    @FXML
    void handleMenuCheckFill(ActionEvent event) {
		CheckMenuItem checkFill = (CheckMenuItem)event.getSource();
		if (checkFill.isSelected())
			model.setShapeFill(true);
		else
			model.setShapeFill(false);
    }

    @FXML
    void handleMenuCheckStroke(ActionEvent event) {
		CheckMenuItem checkStroke = (CheckMenuItem)event.getSource();
		if (checkStroke.isSelected())
			model.setShapeStroke(true);
		else
			model.setShapeStroke(false);
    }

    @FXML
    void handleMenuDelete(ActionEvent event) {
    	changeEditState(Utils.EDIT_DELETE);
		drawPane.redraw();
    }

    @FXML
    void handleMenuExit(ActionEvent event) {
		Platform.exit();
    }

    @FXML
    void handleMenuFillColor(ActionEvent event) {
    }

    @FXML
    void handleMenuLine(ActionEvent event) {
    	changeEditState(Utils.SHAPE_LINE);
		drawPane.redraw();
    }

    @FXML
	void handleMenuUndo(ActionEvent event) {
    	changeEditState(Utils.EDIT_UNDO);
		model.undoAction();
		drawPane.redraw();
	}

    @FXML
    void handleMenuMove(ActionEvent event) {
    	changeEditState(Utils.EDIT_MOVE);
		if (model.curDrawShape != null){
			model.shapeList.add(model.curDrawShape);
			model.curDrawShape = null;
		}
		drawPane.redraw();
    }

    @FXML
    void handleMenuNew(ActionEvent event) {
		initDrawPane();
    }

    @FXML
    void handleMenuPencil(ActionEvent event) {
    	changeEditState(Utils.SHAPE_PENCIL);
		drawPane.redraw();
    }


    @FXML
    void handleMenuStrokeColor(ActionEvent event) {
    }

    @FXML
    void handleMenuStrokeWidth(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("10");
		dialog.initOwner(parentStage);
		dialog.setTitle("Set Stroke Width");
		dialog.setHeaderText("Enter Stroke Width Value");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String inputVal = result.get();
			model.setShapeStrokeWidth(Float.parseFloat(inputVal));
		}

    }

	// Mouse Event Handler
    void handleMouseEntered(MouseEvent event) {
    	//model.setMouseInside(true);
    }

    void handleMouseExited(MouseEvent event) {
    	//model.setMouseInside(false);
    }

    void handleMousePressed(MouseEvent event) {

		if (event.getButton()==MouseButton.PRIMARY){
			model.sb.performMousePressed(event);
		}		
		drawPane.redraw();

	}    

    void handleMouseReleased(MouseEvent event) {

		if (event.getButton()==MouseButton.PRIMARY){
			model.sb.performMouseReleased(event);
		}
		drawPane.redraw();
		
	}

	void handleMouseDragged(MouseEvent event) {

		if (event.getButton()==MouseButton.PRIMARY){
			model.sb.performMouseDragged(event);
		}
		drawPane.redraw();
	}
	
	void changeEditState(int state) {
		// Todo lblEditState Setting
		System.out.format("editState=%s\n", state);
		model.setEditState(state);
	}

}
