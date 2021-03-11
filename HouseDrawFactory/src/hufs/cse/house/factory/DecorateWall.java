package hufs.cse.house.factory;
/**
 * Created on 2015. 3. 17.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;

/**
 * @author cskim
 *
 */
public class DecorateWall extends Wall {

	Wall wall;
	Paint paint;
	
	public DecorateWall(Wall wall, Paint paint){
		super(wall.parent, wall.x, wall.y, wall.width, wall.height);
		
		this.wall = wall;
		this.paint = paint;
	}
	
	@Override
	public void draw(Graphics2D g2) {
		int ax = absX();
		int ay = absY();
		
		g2.setPaint(paint);
		g2.fillRect(ax, ay, width, height);
		//g2.setColor(Color.BLACK);
		//g2.drawRect(ax, ay, width, height);

		if (wall.getDoor()!=null)
			wall.getDoor().draw(g2);
		
		for (Window w:wall.windows){
			w.draw(g2);
		}

	}

}
