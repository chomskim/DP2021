/**
 * Created on 2014. 12. 3.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */

package hufs.ces.svggrim;

import java.awt.Color;
import java.util.HashMap;

/**
 * @author cskim
 *
 */
public class ParseAttribute {
	
	// SVG��s default style is a black fill with no stroke color
	// stroke-width default == 1
	
	private Color fillColor = Color.black;
	private boolean fill = true;
	private boolean stroke = false;
	private float strokeWidth = 1f;
	private Color strokeColor = Color.black;
	
	public ParseAttribute(HashMap<String, String> attsmap){
		String styleDefString = attsmap.get("style");
		if (styleDefString!=null && !styleDefString.equals("")){
			String[] styleAttrs = styleDefString.split(";");
			
			fill = true;
			fillColor = getColorFromRGBString(styleAttrs[0]);
			if (fillColor == null) fill = false;
			
			if (styleAttrs.length >= 2){ // has stroke
				stroke = true;
				strokeColor = getColorFromRGBString(styleAttrs[1]);
				strokeWidth = getStrokeWidth(styleAttrs[2]); 
			}
		}
		styleDefString = attsmap.get("fill");
		//System.out.println("fill val="+styleDefString);
		if (styleDefString!=null && styleDefString.length()>0 && styleDefString.charAt(0)=='#'){
			fill = true;
			fillColor = getColorFromHexaString(styleDefString);
		}
		else if(styleDefString!=null && styleDefString.equals("none")){
			fill = false;
		}
		styleDefString = attsmap.get("stroke");
		if (styleDefString!=null && styleDefString.length()>0 && styleDefString.charAt(0)=='#'){
			stroke = true;
			strokeColor = getColorFromHexaString(styleDefString);
		}
		styleDefString = attsmap.get("stroke-width");
		if (styleDefString!=null && styleDefString.length()>0){
			stroke = true;
			strokeWidth = Float.parseFloat(styleDefString);
		}		
		
	}

	static Color getColorFromRGBString(String rgbStr){
		Color res = new Color(0, 0, 0);
		int istart = rgbStr.indexOf("rgb(");
		if (istart == -1) {
			if (rgbStr.indexOf("none") >=0)
				return null;
			else
				return res;
		}
		int iend = rgbStr.indexOf(")");
		if (iend == -1) return res;
		//System.out.println("rgbStr="+rgbStr.substring(istart+4, iend));
		String[] rgbVals = rgbStr.substring(istart+4, iend).split(",");
		if (rgbVals.length != 3) return res;

		res = new Color(Integer.parseInt(rgbVals[0]), Integer.parseInt(rgbVals[1]), Integer.parseInt(rgbVals[2]));
		return res;
	}
	static Color getColorFromHexaString(String hexStr){
		Color res = new Color(0, 0, 0);
		if (hexStr.length()<7) return res;

		String redHex = hexStr.substring(1, 3);
		String greHex = hexStr.substring(3, 5);
		String bluHex = hexStr.substring(5);

		res = new Color(Integer.parseInt(redHex,16), Integer.parseInt(greHex,16), 
				Integer.parseInt(bluHex,16));
		return res;
	}
	static float getStrokeWidth(String widStr){
		int istart = widStr.indexOf(":");
		if (istart == -1) return 1f;
		String numStr = widStr.substring(istart+1).trim();

		return Float.parseFloat(numStr);
	}

	public Color getFillColor() {
		return fillColor;
	}

	public boolean isFill() {
		return fill;
	}

	public boolean isStroke() {
		return stroke;
	}

	public float getStrokeWidth() {
		return strokeWidth;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

}
