/**
 * Created on Nov 20, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.house;

import java.awt.Graphics2D;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author cskim
 *
 */
public class Window extends DrawableShape {

	protected boolean sash = false;
	static final int SASHWIDTH = 2;

	public Window(HouseModel model, DrawableShape parent){
		super(model, parent);
	}
	public Window(HouseModel model, DrawableShape parent, int x, int y, int w, int h){
		super(model, parent, x, y, w, h);
	}

	@Override
	public void draw() {
		// Draw Default Single Window
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
		model.houseList.add(r);
		
		if (sash){
			Rectangle r2 = new Rectangle();
			r2.setX(ax-SASHWIDTH);
			r2.setY(ay-SASHWIDTH);
			r2.setWidth(width+2*SASHWIDTH);
			r2.setHeight(height+2*SASHWIDTH);
			
			r2.setStroke(Color.BLACK);
			r2.setStrokeWidth(1);
			r2.setFill(Color.TRANSPARENT);
			model.houseList.add(r2);
			
		}

	}
	/**
	 * @return the sash
	 */
	public boolean isSash() {
		return sash;
	}
	/**
	 * @param sash the sash to set
	 */
	public void setSash(boolean sash) {
		this.sash = sash;
	}

}
