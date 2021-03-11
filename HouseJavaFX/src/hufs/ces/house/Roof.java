/**
 * Created on Nov 20, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.house;

import javafx.scene.layout.Pane;

/**
 * @author cskim
 *
 */
public abstract class Roof extends DrawableShape {

	public Roof(HouseModel model, DrawableShape parent){
		super(model, parent);
	}
	public Roof(HouseModel model, DrawableShape parent, int x, int y, int w, int h){
		super(model, parent, x, y, w, h);
	}
}
