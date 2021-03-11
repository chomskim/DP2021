/**
 * Created on Nov 20, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.house;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author cskim
 *
 */
public class Wall extends DrawableShape {

	private Door door = null;
	public ArrayList<Window> windows = null;
	
	public Wall(HouseModel model, DrawableShape parent){
		super(model, parent);
		windows = new ArrayList<Window>();
	}
	public Wall(HouseModel model, DrawableShape parent, int x, int y, int w, int h){
		super(model, parent, x, y, w, h);
		windows = new ArrayList<Window>();
	}
	/* (non-Javadoc)
	 * @see hufs.cse.house.Shape#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw() {
		
		double ax = absX();
		double ay = absY();
		
		Rectangle r = new Rectangle();
		r.setX(ax);
		r.setY(ay);
		r.setWidth(width);
		r.setHeight(height);
		
		r.setFill(Color.WHITE);
		r.setStroke(Color.BLACK);
		r.setStrokeWidth(1);
		model.houseList.add(r);

		if (door!=null)
			door.draw();
		
		for (Window w:windows){
			w.draw();
		}

	}
	/**
	 * @return the door
	 */
	public Door getDoor() {
		return door;
	}
	/**
	 * @param door the door to set
	 */
	public void setDoor(Door door) {
		this.door = door;
	}

}
