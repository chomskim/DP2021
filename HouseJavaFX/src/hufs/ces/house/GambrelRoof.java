/**
 * Created on Nov 20, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.house;


import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * @author cskim
 *
 */
public class GambrelRoof extends Roof {

	public GambrelRoof(HouseModel model, DrawableShape parent){
		super(model, parent);
	}
	public GambrelRoof(HouseModel model, DrawableShape parent, int x, int y, int w, int h){
		super(model, parent, x, y, w, h);
	}

	@Override
	public void draw() {
		double ax = absX();
		double ay = absY();
		
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(new Double[]{
				ax, ay+height,
				ax+width/6, ay+height/2,
				ax+width/2, ay,
				ax+width*5/6, ay+height/2,
				ax+width, ay+height });
		
		polygon.setFill(Color.WHITE);
		polygon.setStroke(Color.BLACK);
		polygon.setStrokeWidth(1);
		model.houseList.add(polygon);		
	}

}
