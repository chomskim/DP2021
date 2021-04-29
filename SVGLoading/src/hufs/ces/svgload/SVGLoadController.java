package hufs.ces.svgload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import hufs.ces.grimpan.svg.SVGGrimPath;
import hufs.ces.grimpan.svg.SVGGrimShape;
import hufs.ces.grimpan.svg.SVGUtils;
import hufs.ces.grimpan.svg.SaxSVGPathParseHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SVGLoadController extends AnchorPane {

	public Stage parentStage = null;

	private final String defaultDir = "C:/";
	private String currDir = defaultDir;

	DrawPane drawPane = null;
	File selFile = null;
	long totPathCount = 0;

	public SVGLoadController() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/svgload.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initialize();
	}

	private void initialize() {

		treeView.prefWidthProperty().bind(apLeft.widthProperty());
		treeView.prefHeightProperty().bind(apLeft.heightProperty());

		drawPane = new DrawPane();
		drawPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		apRight.getChildren().add(drawPane);
	}
	void initDrawPane() {
		textArea.clear();
		drawPane.shapeList = FXCollections.observableArrayList();
		drawPane.shapeList.addListener((ListChangeListener.Change<? extends SVGGrimShape> change) ->{
			Platform.runLater(() -> {
				while(change.next()){
					if(change.wasAdded()){
						for (SVGGrimShape gsh:change.getAddedSubList()){
							drawPane.getChildren().add(gsh.getShape());
						}
					}
				}			
			}
			);
		});
		//drawPane.redraw();
	}

	@FXML
	private MenuItem mnOpen;

	@FXML
	private MenuItem mnLoad;

	@FXML
	private MenuItem mnExit;

	@FXML
	private MenuItem mnAbout;

	@FXML
	private AnchorPane apLeft;

	@FXML
	private TreeView<NamedNode> treeView;

	@FXML
	private AnchorPane apRight;

	@FXML
	private TextArea textArea;

	@FXML
	private Label lblFilePath;

    @FXML
    private ProgressBar progressBar;

    @FXML
	void handleAbout(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("SVG Load Ver 0.2.2");
		alert.setContentText("Programmed by cskim, ces, hufs.ac.kr");
		alert.initOwner(parentStage);

		alert.showAndWait();
	}

	@FXML
	void handleExit(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void handleLoad(ActionEvent event) {
		readShapeFromSVGSaveFile();
	}

	@FXML
	void handleOpen(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open XML File");
		fileChooser.setInitialDirectory(new File(currDir));
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("SVG File (*.svg)", "*.svg", "*.SVG"),
				new ExtensionFilter("XML File (*.xml)", "*.xml", "*.XML"));

		selFile = fileChooser.showOpenDialog(parentStage);

		if (selFile == null) return;
		lblFilePath.setText(" File: "+selFile.getPath());

		if (selFile.isFile()) {
			currDir = selFile.getParent();
			buildXMLTree(selFile);
			readFile2View(selFile);
		}
	}

	void buildXMLTree(File file){

		Document doc = null;
		DocumentBuilder builder = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Element root = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(file);
			root = doc.getDocumentElement();
			root.normalize();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		TreeItem<NamedNode> rootNode = createNodeFromDom(new NamedNode(root));
		//System.out.println(rootNode);
		treeView.setRoot(rootNode);

	}
	private TreeItem<NamedNode> createNodeFromDom(NamedNode node) {
		return new DomTreeItem(node);
	}

	public void readFile2View(File f){
		Scanner inscan = null;
		StringBuilder sb = new StringBuilder();
		try {
			inscan = new Scanner(f);
			String inline = "";
			while (inscan.hasNext()){
				inline = inscan.nextLine();
				sb.append(inline+"\n");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		drawPane.clear();
		textArea.setText(sb.toString());
	}
	@FXML
	void handleTreeViewClicked(MouseEvent event) {
		Node node = event.getPickResult().getIntersectedNode();
		//System.out.println(node);
		if (node instanceof Text) {
			NamedNode selnode = (NamedNode) ((TreeItem<NamedNode>)treeView.getSelectionModel().getSelectedItem()).getValue();
			System.out.println("Node click: " + selnode);
			String pathDef = selnode.attrMap.get("d");
			String styleDef = selnode.attrMap.get("style");
			SVGPath svgPath = new SVGPath();
			SVGUtils.paintShapeWithSVGStyle(svgPath, styleDef);
			svgPath.setContent(pathDef);
			drawPane.shapeList.add(new SVGGrimPath(SVGUtils.convertSVGPathToPath(svgPath)));
			//drawPane.redraw();
		}
	}

	void readShapeFromSVGSaveFile() {


		SVGParseTask parseTask = new SVGParseTask();
		progressBar.progressProperty().bind(parseTask.progressProperty());

		parseTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
			new EventHandler<WorkerStateEvent>() {

				@Override
				public void handle(WorkerStateEvent t) {
				/*
				ObservableList<SVGGrimShape> parseShapeList = parseTask.getValue();

				for (SVGGrimShape gsh:parseShapeList) {
					drawPane.shapeList.add(gsh);
				}

				drawPane.redraw();
				*/

			}
		});

		String fileName = selFile.getName();
		parentStage.setTitle("SVG Load - "+fileName);
		this.initDrawPane();

		// Start the Task.
		new Thread(parseTask).start();


	}
	public class SVGParseTask extends Task<ObservableList<SVGGrimShape>> {

		SVGParseTask thisTask = this;

		public ObservableList<SVGGrimShape> gshapeList = FXCollections.observableArrayList();

		public SVGParseTask() {
			totPathCount = countPathNode();
			gshapeList.addListener((ListChangeListener.Change<? extends SVGGrimShape> change) ->{
				while(change.next()){
					if(change.wasAdded()){
						//System.out.println(drawPane.shapeList.size()+" "+ totPathCount);
						thisTask.updateProgress(drawPane.shapeList.size()+1, totPathCount);
						drawPane.shapeList.addAll(change.getAddedSubList());
					}
				}			
			});
		}
		@Override
		protected ObservableList<SVGGrimShape> call() throws Exception {

			SaxSVGPathParseHandler saxTreeHandler = new SaxSVGPathParseHandler(this); 
			try {
				SAXParserFactory saxf = SAXParserFactory.newInstance();

				SAXParser saxParser = saxf.newSAXParser();
				saxParser.parse(new InputSource(new FileInputStream(selFile)), saxTreeHandler);
			}
			catch(Exception e){
				e.printStackTrace();
			}

			return gshapeList;
		}

	}
	int countPathNode(){

		Scanner inscan = null;
		int pathNodeCount = 0;
		try {
			inscan = new Scanner(selFile);
			String inline = "";
			while (inscan.hasNext()){
				inline = inscan.nextLine();
				if (inline.indexOf("path")>0)
					pathNodeCount++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("path node count="+pathNodeCount);
		inscan.close();
		return pathNodeCount;
	}

}
