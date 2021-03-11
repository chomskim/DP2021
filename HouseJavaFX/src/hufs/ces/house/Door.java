/**
 * Created on Nov 20, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.house;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * @author cskim
 *
 */
public class Door extends DrawableShape {

	private boolean leftKnob = true;
	static final double KNOBGAP = 2;
	static final double KNOBSIZE = 4;

	public Door(HouseModel model, DrawableShape parent){
		super(model, parent);
	}
	public Door(HouseModel model, DrawableShape parent, int x, int y, int w, int h){
		super(model, parent, x, y, w, h);
	}

	@Override
	public void draw() {
		double ax = absX();
		double ay = absY();

		Rectangle r = new Rectangle();
		r.setX(ax);
		r.setY(ay);
		r.setWidth(width);
		r.setHeight(height);
		
		r.setStroke(Color.BLACK);
		r.setStrokeWidth(1);		
		r.setFill(Color.TRANSPARENT);
		
		Circle c = new Circle();
		c.setRadius(KNOBSIZE/2);
		c.setCenterY(ay+height/2);
		
		if (leftKnob){
			c.setCenterX(ax+KNOBGAP+KNOBSIZE/2);
		}
		else {
			c.setCenterX(ax+width-KNOBGAP-KNOBSIZE/2);
		}
		model.houseList.add(r);
		model.houseList.add(c);
	}
	/**
	 * @return the leftKnob
	 */
	public boolean isLeftKnob() {
		return leftKnob;
	}
	/**
	 * @param leftKnob the leftKnob to set
	 */
	public void setLeftKnob(boolean leftKnob) {
		this.leftKnob = leftKnob;
	}

}
