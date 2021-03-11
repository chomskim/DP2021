/**
 * Created on Nov 20, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.house;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;

/**
 * @author cskim
 *
 */
public class DomeRoof extends Roof {

	public DomeRoof(HouseModel model, DrawableShape parent){
		super(model, parent);
	}
	public DomeRoof(HouseModel model, DrawableShape parent, int x, int y, int w, int h){
		super(model, parent, x, y, w, h);
	}

	@Override
	public void draw() {
		double ax = absX();
		double ay = absY();
		
		Arc arc = new Arc();
		arc.setCenterX(ax+width/2);
		arc.setCenterY(ay+height);
		arc.setRadiusX(width/2);
		arc.setRadiusY(width/2);
		arc.setStartAngle(0);
		arc.setLength(180);
		arc.setType(ArcType.ROUND);
		arc.setFill(Color.WHITE);
		arc.setStroke(Color.BLACK);
		arc.setStrokeWidth(1);
		model.houseList.add(arc);		
		
	}

}
