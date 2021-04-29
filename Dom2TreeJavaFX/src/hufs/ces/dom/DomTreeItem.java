package hufs.ces.dom;

import java.util.regex.Pattern;

import org.w3c.dom.NodeList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class DomTreeItem extends TreeItem<NamedNode> {

	private boolean isLeaf;
	private boolean isFirstTimeChildren = true;
	private boolean isFirstTimeLeaf = true;

	public DomTreeItem(NamedNode node) {
		super(node);
	}
	@Override 
	public ObservableList<TreeItem<NamedNode>> getChildren() {
		if (isFirstTimeChildren) {
			isFirstTimeChildren = false;

			// First getChildren() call, so we actually go off and
			// determine the children of the File contained in this TreeItem.
			super.getChildren().setAll(buildChildren(this));
		}
		return super.getChildren();
	}

	@Override 
	public boolean isLeaf() {
		if (isFirstTimeLeaf) {
			isFirstTimeLeaf = false;
			NamedNode node = (NamedNode) getValue();
			isLeaf = !node.hasChildNodes();
		}

		return isLeaf;
	}

	private ObservableList<TreeItem<NamedNode>> buildChildren(TreeItem<NamedNode> treeItem) {
		NamedNode node = treeItem.getValue();
		if (node != null && node.hasChildNodes()) {
			NodeList nodes = node.getChildNodes();
			if (nodes != null) {
				ObservableList<TreeItem<NamedNode>> children = FXCollections.observableArrayList();

				for (int i=0; i<nodes.getLength(); ++i) {
					NamedNode chnode = new NamedNode(nodes.item(i));
					if (chnode.isText() && Pattern.matches("(\\t|\\s)*",chnode.getNodeValue()))
						continue;
					children.add(new DomTreeItem(chnode));
				}

				return children;
			}
		}

		return FXCollections.emptyObservableList();
	}
}
