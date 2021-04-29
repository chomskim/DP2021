package hufs.ces.dom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Dom2TreeController {

	private final String defaultDir = "C:/";
	//private final String FILE_SEP = System.getProperty("file.separator");
	private String currDir = defaultDir;

	public Dom2TreeController() {

	}
	
	@FXML
	private void initialize() {

		treeView.prefWidthProperty().bind(apLeft.widthProperty());
		treeView.prefHeightProperty().bind(apLeft.heightProperty());

	}
	
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
    void handleAboutAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("XML Tree Ver 0.2.2");
		alert.setContentText("Programmed by cskim, ces, hufs.ac.kr");

		alert.showAndWait();
	}

	@FXML
	void handleExitAction(ActionEvent event) {
		System.exit(0);
	}

    @FXML
    void handleOpenAction(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open XML File");
		fileChooser.setInitialDirectory(new File(currDir));
		fileChooser.getExtensionFilters().addAll(
			new ExtensionFilter("SVG File, XML File", "*.svg", "*.SVG", "*.xml", "*.XML"));

		File selFile = fileChooser.showOpenDialog(null);

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
		textArea.setText(sb.toString());
	}
	
    @FXML
    void handleTreeViewMouseClicked(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        //System.out.println(node);
        if (node instanceof Text) {
        	NamedNode selnode = (NamedNode) ((TreeItem<NamedNode>)treeView.getSelectionModel().getSelectedItem()).getValue();
            System.out.println("Node click: " + selnode);
        }
    }

}
