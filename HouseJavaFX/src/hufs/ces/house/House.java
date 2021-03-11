/**
 * Created on Nov 20, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.house;

import java.awt.Graphics2D;

import javafx.scene.layout.Pane;

/**
 * @author cskim
 *
 */
public class House extends DrawableShape {

	private Wall wall = null;
	private Roof roof = null;

	public House(HouseModel model, DrawableShape parent){
		super(model, parent);
	}
	public House(HouseModel model, DrawableShape parent, int x, int y, int w, int h){
		super(model, parent, x, y, w, h);
	}
	/* (non-Javadoc)
	 * @see hufs.cse.house.Shape#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw() {
		wall.draw();
		roof.draw();
		System.out.println("Shape Count="+model.houseList.size());
	}
	/**
	 * @return the wall
	 */
	public Wall getWall() {
		return wall;
	}
	/**
	 * @param wall the wall to set
	 */
	public void setWall(Wall wall) {
		this.wall = wall;
	}
	/**
	 * @return the roof
	 */
	public Roof getRoof() {
		return roof;
	}
	/**
	 * @param roof the roof to set
	 */
	public void setRoof(Roof roof) {
		this.roof = roof;
	}

}
