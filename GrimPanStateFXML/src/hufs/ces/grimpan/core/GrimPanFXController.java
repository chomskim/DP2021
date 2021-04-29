package hufs.ces.grimpan.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import hufs.ces.grimpan.state.EditState;
import hufs.ces.grimpan.svg.SVGGrimShape;
import hufs.ces.grimpan.svg.SaxSVGPathParseHandler;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/grimpanstate.fxml"));
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
		
		model.shapeList.addListener((ListChangeListener<SVGGrimShape>) c-> {
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
    private ProgressBar progressBar;

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
    	changeEditState(model.STATE_DELETE);
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
    	changeEditState(model.STATE_LINE);
		drawPane.redraw();
    }

    @FXML
	void handleMenuUndo(ActionEvent event) {
		model.undoAction();
		drawPane.redraw();
	}

    @FXML
    void handleMenuMove(ActionEvent event) {
    	changeEditState(model.STATE_MOVE);
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
	void handleMenuOpen(ActionEvent event) {
		openAction();
	}

    @FXML
	void handleMenuSave(ActionEvent event) {
		saveAction();
	}

    @FXML
	void handleMenuSaveAs(ActionEvent event) {
		saveAsAction();
	}

    @FXML
    void handleMenuPencil(ActionEvent event) {
    	changeEditState(model.STATE_PENCIL);
		drawPane.redraw();
    }

    @FXML
	void handleMenuOval(ActionEvent event) {
    	changeEditState(model.STATE_OVAL);
		drawPane.redraw();
	}
    
    @FXML
	void handleMenuRegular(ActionEvent event) {
    	changeEditState(model.STATE_REGULAR);
		String[] possibleValues = { 
				"3", "4", "5", "6", "7",
				"8", "9", "10", "11", "12"
		};		
		ChoiceDialog<String> dialog = new ChoiceDialog<String>("3", possibleValues);
		dialog.initOwner(parentStage);
		dialog.setTitle("Select Number for Regular Polygon");
		dialog.setHeaderText("N Regular Polygon");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String inputVal = result.get();
			model.setNPolygon(Integer.parseInt(inputVal));
		}
		drawPane.redraw();
	}

    @FXML
	void handleMenuPolygon(ActionEvent event) {
    	changeEditState(model.STATE_POLYGON);
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
			model.editState.performMousePressed(event);
		}		
		drawPane.redraw();

	}    

    void handleMouseReleased(MouseEvent event) {

		if (event.getButton()==MouseButton.PRIMARY){
			model.editState.performMouseReleased(event);
		}
		drawPane.redraw();
		
	}

	void handleMouseDragged(MouseEvent event) {

		if (event.getButton()==MouseButton.PRIMARY){
			model.editState.performMouseDragged(event);
		}
		drawPane.redraw();
	}
	
	void changeEditState(EditState state) {
		// Todo lblEditState Setting
		System.out.format("editState=%s\n", state);
		model.setEditState(state);
	}
	void openAction() {

		FileChooser fileChooser1 = new FileChooser();
		fileChooser1.setTitle("Open Saved GrimPan");
		fileChooser1.setInitialDirectory(new File(Utils.DEFAULT_DIR));
		fileChooser1.getExtensionFilters().add(new ExtensionFilter("SVG File (*.svg)", "*.svg", "*.SVG"));
		File selFile = fileChooser1.showOpenDialog(parentStage);

		if (selFile == null) return;

		model.setSaveFile(selFile);
		readShapeFromSVGSaveFile();
	}
	int countPathNode(){

		Scanner inscan = null;
		int pathNodeCount = 0;
		try {
			inscan = new Scanner(model.getSaveFile());
			String inline = "";
			while (inscan.hasNext()){
				inline = inscan.nextLine();
				if (inline.indexOf("path")>0)
					pathNodeCount++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println("path node count="+pathNodeCount);
		inscan.close();
		return pathNodeCount;
	}

	void readShapeFromSVGSaveFile() {


		SVGParseTask parseTask = new SVGParseTask();
		progressBar.progressProperty().unbind();
		progressBar.progressProperty().bind(parseTask.progressProperty());

		parseTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
				new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent t) {
				ObservableList<SVGGrimShape> parseShapeList = parseTask.getValue();

				for (SVGGrimShape gsh:parseShapeList) {
					model.shapeList.add(gsh);
				}

				String fileName = model.getSaveFile().getName();
				parentStage.setTitle("GrimPan - "+fileName);
				drawPane.redraw();

			}
		});

		initDrawPane();

		// Start the Task.
		new Thread(parseTask).start();


	}
	void saveAction() {
		if (model.getSaveFile()==null){
			model.setSaveFile(new File(Utils.DEFAULT_DIR+"noname.svg"));
			parentStage.setTitle("GrimPan - "+Utils.DEFAULT_DIR+"noname.svg");
		}
		File selFile = model.getSaveFile();
		saveGrimPanSVGShapes(selFile);	
	}

	void saveAsAction() {
		FileChooser fileChooser2 = new FileChooser();
		fileChooser2.setTitle("Save As ...");
		fileChooser2.setInitialDirectory(new File(Utils.DEFAULT_DIR));
		fileChooser2.getExtensionFilters().add(new ExtensionFilter("SVG File (*.svg)", "*.svg", "*.SVG"));
		File selFile = fileChooser2.showSaveDialog(parentStage);

		model.setSaveFile(selFile);
		parentStage.setTitle("GrimPan - "+selFile.getName());

		saveGrimPanSVGShapes(selFile);	

	}

	void saveGrimPanSVGShapes(File saveFile){
		PrintWriter svgout = null;
		try {
			svgout = new PrintWriter(saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		svgout.println("<?xml version='1.0' encoding='utf-8' standalone='no'?>");
		//svgout.println("<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.0//EN' 'http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd'>");
		svgout.print("<svg xmlns:svg='http://www.w3.org/2000/svg' ");
		svgout.println("     xmlns='http://www.w3.org/2000/svg' ");
		svgout.print("width='"+widthProp.getValue()+"' ");
		svgout.print("height='"+heightProp.getValue()+"' ");
		svgout.println("overflow='visible' xml:space='preserve'>");
		for (SVGGrimShape gs:model.shapeList){
			svgout.println("    "+gs.getSVGShapeString());
		}
		svgout.println("</svg>");
		svgout.close();
	}
	public class SVGParseTask extends Task<ObservableList<SVGGrimShape>> {

		long totPathCount = 0;
		ObservableList<SVGGrimShape> gshapeList = null;

		SVGParseTask thisTask = this;
		
		public SVGParseTask() {
			totPathCount = countPathNode();
		}
		@Override
		protected ObservableList<SVGGrimShape> call() throws Exception {

			SaxSVGPathParseHandler saxTreeHandler = new SaxSVGPathParseHandler(this); 
			this.gshapeList = saxTreeHandler.gshapeList;
			this.gshapeList.addListener((ListChangeListener.Change<? extends SVGGrimShape> change) ->{
				while(change.next()){
					if(change.wasAdded()){
						System.out.println(gshapeList.size()+" "+ totPathCount);
						thisTask.updateProgress(gshapeList.size(), totPathCount);
					}
				}			
			});
			try {
				SAXParserFactory saxf = SAXParserFactory.newInstance();

				SAXParser saxParser = saxf.newSAXParser();
				saxParser.parse(new InputSource(new FileInputStream(model.getSaveFile())), saxTreeHandler);
			}
			catch(Exception e){
				e.printStackTrace();
			}

			return saxTreeHandler.gshapeList;
		}

	}

}
