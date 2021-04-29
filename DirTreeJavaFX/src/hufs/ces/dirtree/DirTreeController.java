package hufs.ces.dirtree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class DirTreeController  extends AnchorPane {

	private String defaultDir = "C:/";
	private final String FILE_SEP = System.getProperty("file.separator");

	public DirTreeController() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dirtree.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void initialize() {

		treeView.prefWidthProperty().bind(apLeft.widthProperty());
		treeView.prefHeightProperty().bind(apLeft.heightProperty());

	}

	@FXML
	private BorderPane  root;

	@FXML
	private MenuItem mnOpen;

	@FXML
	private MenuItem mnExit;

	@FXML
	private MenuItem mnAbout;

	@FXML
	private TreeView<File> treeView;

	@FXML
	private AnchorPane apLeft;

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
		alert.setHeaderText("File Explorer Ver 0.2.2");
		alert.setContentText("Programmed by cskim, ces, hufs.ac.kr");

		alert.showAndWait();
	}

	@FXML
	void handleExitAction(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void handleOpenAction(ActionEvent event) {
		//Node source = (Node) event.getSource();
		//Window parentStage = source.getScene().getWindow();

		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Dir/File");

		directoryChooser.setInitialDirectory(new File(defaultDir));
		File selFile = directoryChooser.showDialog(null);

		if (selFile == null) return;
		lblFilePath.setText(" File: "+selFile.getPath());

		if (selFile.isDirectory()) {
			buildDirTree(selFile);
		}
		else {
			readFile2View(selFile);
		}
	}
	void buildDirTree(File dir){

		TreeItem<File> rootNode = createNode(dir);
		//System.out.println(rootNode);
		treeView.setRoot(rootNode);

	}
	private TreeItem<File> createNode(File f) {
		return new FileTreeItem(f) ;
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
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            File file = (File) ((TreeItem<File>)treeView.getSelectionModel().getSelectedItem()).getValue();
            readFile2View(file);
            //System.out.println("Node click: " + file);
        }
    }

}
