package hufs.cse.house.factory;
/**
 * Created on Nov 20, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */


import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author cskim
 *
 */
public class QuadWindow extends Window {

	public QuadWindow(Shape parent){
		super(parent);
	}
	public QuadWindow(Shape parent, int x, int y, int w, int h){
		super(parent, x, y, w, h);
	}
	@Override
	public void draw(Graphics2D g2) {
		// Draw Default Single Window
		int px = absX();
		int py = absY();
		g2.setPaint(paint);
		g2.fillRect(px, py, width, height);
		g2.setColor(Color.white);
		g2.drawRect(px, py, width, height);
		if (sash){
			g2.drawRect(px+SASHWIDTH, py+SASHWIDTH, width-2*SASHWIDTH, height-2*SASHWIDTH);
		}
		g2.drawLine(px, py+height/2, px+width, py+height/2);
		g2.drawLine(px+width/2, py, px+width/2, py+height);
	}

}
