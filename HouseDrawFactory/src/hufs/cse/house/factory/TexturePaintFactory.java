/**
 * Created on 2015. 3. 17.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.house.factory;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * @author cskim
 *
 */
public class TexturePaintFactory {

	public static TexturePaint createTexturePaint(String imageFile){
		// build bufferedImage from image file
		BufferedImage mImage = null;
	    try {
	    	InputStream in = TexturePaintFactory.class.getResourceAsStream(imageFile);
		    mImage = ImageIO.read(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    // make TexturePaint
	    return new TexturePaint(mImage, new Rectangle2D.Double(0, 0, mImage.getWidth(), mImage.getHeight()));
	}
		
	public static TexturePaint createDoorPaint(Door door, BufferedImage bimage){
		int width = door.getWidth();
		int height = door.getHeight();
	    BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = scaledImage.createGraphics();
	    g.setComposite(AlphaComposite.Src);
	    g.drawImage(bimage, 0, 0, width, height, null);
	    g.dispose();
	    //System.out.println("Buff Image w="+bimage.getWidth()+" h="+bimage.getHeight());
	    
	    return new TexturePaint(scaledImage, new Rectangle2D.Double(door.absX(), door.absY(), width, height));	    
	}
	
	public static GradientPaint createWindowPaint(Window win){
		int px1 = win.getX()+(int)(win.getWidth()*35./80.);
		int py1 = win.getY()+(int)(win.getWidth()*35./80.);
		int px2 = win.getX()+(int)(win.getWidth()*55./80.);
		int py2 = win.getY()+(int)(win.getWidth()*55./80.);
		return new GradientPaint(px1, py1, Color.white, px2, py2, new Color(102, 178, 255), true);
	}

	public static BufferedImage getBufferedImageFromFile(String imageFile){		
		// build bufferedImage from image file
		BufferedImage buffImage = null;
	    try {
	    	InputStream in = TexturePaintFactory.class.getResourceAsStream(imageFile);
			buffImage = ImageIO.read(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    //System.out.println("Buff Image w="+buffImage.getWidth()+" h="+buffImage.getHeight());
	    return buffImage;
	}

}
