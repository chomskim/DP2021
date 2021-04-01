package hufs.ces.grimpan0;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class GrimPan0FXController extends AnchorPane {

	public Stage parentStage;
	private GrimPanModel model;
	private ShapeFactory sf;

	ColorPicker fcolorPicker = new ColorPicker(Color.WHITE);
	ColorPicker scolorPicker = new ColorPicker(Color.BLACK);
	
	public GrimPan0FXController() {

		model = GrimPanModel.getInstance();
		sf = new ShapeFactory(model);

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("grimpan0.fxml"));
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
	
		initDrawPane();
	}
	
	void initDrawPane() {
		model.shapeList.clear();
		model.curDrawShape = null;
		clearDrawPane();
		
		System.out.format("drawPane w=%s h=%s\n", drawPane.getWidth(), drawPane.getHeight());
	}
	
	void clearDrawPane() {
		drawPane.getChildren().clear();
	}
	void redrawDrawPane() {
		clearDrawPane();
		//System.out.println("Shape Count="+model.shapeList.size());
		for (Shape sh:model.shapeList){
			drawPane.getChildren().add(sh);
		}
		if (model.curDrawShape!=null) {
			drawPane.getChildren().add(model.curDrawShape);
		}
	}
	
    @FXML
    private AnchorPane root;

    @FXML
    private Pane drawPane;

    @FXML
    private MenuItem menuNew;

    @FXML
    private MenuItem menuExit;

    @FXML
    private MenuItem menuAbout;
    
    @FXML
    private ToggleGroup toggleGroup1;

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
    void handleMenuAbout(ActionEvent event) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(parentStage);   
		alert.setTitle("About");
		alert.setHeaderText("GrimPan Ver 0.0.1");
		alert.setContentText("Programmed by cskim, ces, hufs.ac.kr");

		alert.showAndWait();
		
    }
    
    @FXML
    void handleMenuCheckFill(ActionEvent event) {

    }

    @FXML
    void handleMenuCheckStroke(ActionEvent event) {

    }
    
    @FXML
    void handleMenuExit(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    void handleMenuFillColor(ActionEvent event) {

    }

    @FXML
    void handleMenuNew(ActionEvent event) {

    }

    @FXML
    void handleMenuStrokeColor(ActionEvent event) {

    }

    @FXML
    void handleMenuStrokeWidth(ActionEvent event) {

    }

    @FXML
    void handleMouseDragged(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));

		if (event.getButton()==MouseButton.PRIMARY){
			model.setPrevMousePosition(model.getCurrMousePosition());
			model.setCurrMousePosition(p1);

			switch (model.getEditState()){
			case Utils.SHAPE_LINE:
				model.curDrawShape = sf.createMousePointedLine();
				break;
			case Utils.SHAPE_PENCIL:
				((Path)model.curDrawShape).getElements().add(new LineTo(p1.getX(), p1.getY()));
				break;
			case Utils.EDIT_MOVE:
				break;

			}
		}
		redrawDrawPane();
    }

    @FXML
    void handleMousePressed(MouseEvent event) {
		//System.out.format("drawPane w=%s h=%s\n", drawPane.getWidth(), drawPane.getHeight());
		//System.out.format("drawPane Property w=%s h=%s\n", widthProp.get(), heightProp.get());
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));

		if (event.getButton()==MouseButton.PRIMARY){
			model.setStartMousePosition(p1);
			model.setCurrMousePosition(p1);
			model.setPrevMousePosition(p1);				
			switch (model.getEditState()){
			case Utils.SHAPE_LINE:
				model.curDrawShape = sf.createMousePointedLine();
				break;
			case Utils.SHAPE_PENCIL:
				model.curDrawShape = sf.createPaintedShape(new Path(new MoveTo(p1.getX(), p1.getY())));
				break;
			case Utils.EDIT_MOVE:
				break;
			}
		}
		redrawDrawPane();
    }

    @FXML
    void handleMouseReleased(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		//System.out.println("Mouse Released at "+p1);

		if (event.getButton()==MouseButton.PRIMARY){
			model.setPrevMousePosition(model.getCurrMousePosition());
			model.setCurrMousePosition(p1);

			switch (model.getEditState()){
			case Utils.SHAPE_LINE:
				model.curDrawShape = sf.createMousePointedLine();
				if (model.curDrawShape != null){
					model.shapeList.add(model.curDrawShape);
					model.curDrawShape = null;
				}
				break;
			case Utils.SHAPE_PENCIL:
				((Path)model.curDrawShape).getElements().add(new LineTo(p1.getX(), p1.getY()));
				if (model.curDrawShape != null){
					model.shapeList.add(model.curDrawShape);
					model.curDrawShape = null;
				}
				break;
			case Utils.EDIT_MOVE:
				break;

			}
		}
    }


}
