/**
 * Created on Nov 20, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.house;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * @author cskim
 *
 */

public class DrawPane extends Pane {
	private HouseModel model = null;
	
	public DrawPane(HouseModel model){
		this.model = model;
	}
	public void clear() {
		this.getChildren().clear();
	}
	public void redraw() {
		//System.out.println("Shape Count="+model.shapeList.size());
		for (Shape sh:model.houseList){
			this.getChildren().add(sh);
		}
	}
}
