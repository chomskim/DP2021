/**
 * Created on 2015. 3. 24.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.house.factory;

import java.awt.Paint;
import java.awt.image.BufferedImage;

/**
 * @author cskim
 *
 */
public class Utils {
	
	public static final Paint BLUE_WALL = TexturePaintFactory.createTexturePaint("/images/blue-brick-wall-50x36.jpg");
	public static final Paint COLOR_WALL = TexturePaintFactory.createTexturePaint("/images/color-brick-wall-50x30.jpg");
	public static final Paint RED_WALL1 = TexturePaintFactory.createTexturePaint("/images/red-brick-wall-44x29.jpg");
	public static final Paint RED_WALL2 = TexturePaintFactory.createTexturePaint("/images/red-brick-wall-48x33.jpg");
	public static final Paint RED_WALL3 = TexturePaintFactory.createTexturePaint("/images/red-brick-wall-50x27.jpg");
	public static final Paint RED_WALL4 = TexturePaintFactory.createTexturePaint("/images/red-brick-wall-50x33.jpg");
	public static final Paint WHITE_WALL = TexturePaintFactory.createTexturePaint("/images/white-brick-wall-50x30.jpg");
	public static final Paint YELLOW_WALL = TexturePaintFactory.createTexturePaint("/images/yellow-brick-wall-50x35.jpg");
	public static final Paint TEAL_WALL = TexturePaintFactory.createTexturePaint("/images/teal-brick-wall-50x32.jpg");
	public static final Paint ROOF1 = TexturePaintFactory.createTexturePaint("/images/shingle1-50x36.jpg");
	public static final Paint ROOF2 = TexturePaintFactory.createTexturePaint("/images/shingle2-50x29.jpg");
	public static final Paint ROOF3 = TexturePaintFactory.createTexturePaint("/images/shingle3-50x31.jpg");
	public static final Paint ROOF4 = TexturePaintFactory.createTexturePaint("/images/shingle4-50x35.jpg");
	public static final BufferedImage BROWN_DOOR_IMAGE = TexturePaintFactory.getBufferedImageFromFile("/images/brown-door.jpg");
	public static final BufferedImage WHITE_DOOR_IMAGE = TexturePaintFactory.getBufferedImageFromFile("/images/white-door.jpg");
	public static final BufferedImage WOOD_DOOR1_IMAGE = TexturePaintFactory.getBufferedImageFromFile("/images/wood-door1.jpg");

	public static int dice(int ifrom, int ito){
		return (int)(Math.random()*(ito-ifrom+1))+ifrom;
	}
	public static void main(String[] args) {
		// Test dice
		int DICE_MIN = -2;
		int DICE_MAX = 7;
		int[] freq = new int[DICE_MAX-DICE_MIN+1];
		for (int i=0; i<(DICE_MAX-DICE_MIN+1)*100; ++i){
			//System.out.println("i="+i+" dice="+dice(DICE_MIN,DICE_MAX));
			freq[dice(DICE_MIN,DICE_MAX)-DICE_MIN]++;
		}
		for (int i=DICE_MIN; i<=DICE_MAX; ++i){
			System.out.print("  freq["+i+"]="+freq[i-DICE_MIN]);
		}
	}	
}
