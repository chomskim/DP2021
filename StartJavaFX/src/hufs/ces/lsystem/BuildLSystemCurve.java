/**
 * Created on Nov 28, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.lsystem;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Stack;

/**
 * @author cskim
 *
 */
public class BuildLSystemCurve {

	public static String getDragonCurve(int n){
		return dragon(n).toString();
	}
	static StringBuilder dragon(int n){
		if (n==0)
			return new StringBuilder("F");
		else
			return dragon(n-1).append('-').append(nogard(n-1));
	}
	static StringBuilder nogard(int n){
		if (n==0)
			return new StringBuilder("F");
		else
			return dragon(n-1).append('+').append(nogard(n-1));
	}
	public static String getSierpinskiGasket(int n){
		if (n%2==0)
			return gasketSierpinskiL(n).toString();
		else
			return gasketSierpinskiR(n).toString();

	}
	static StringBuilder gasketSierpinskiR(int n){
		if (n==0)
			return new StringBuilder("F");
		else
			return gasketSierpinskiL(n-1).append('-')
					.append(gasketSierpinskiR(n-1)).append('-')
					.append(gasketSierpinskiL(n-1));
	}
	static StringBuilder gasketSierpinskiL(int n){
		if (n==0)
			return new StringBuilder("F");
		else
			return gasketSierpinskiR(n-1).append('+')
					.append(gasketSierpinskiL(n-1)).append('+')
					.append(gasketSierpinskiR(n-1));
	}
	public static String getHexaGosper(int n){
		return hexaGosperL(n).toString();
	}
	static StringBuilder hexaGosperR(int n){
		if (n==0)
			return new StringBuilder("F");
		else
			return 	new StringBuilder("-")
					.append(hexaGosperL(n-1)).append('+')
					.append(hexaGosperR(n-1))
					.append(hexaGosperR(n-1)).append("++")
					.append(hexaGosperR(n-1)).append('+')
					.append(hexaGosperL(n-1)).append("--")
					.append(hexaGosperL(n-1)).append('-')
					.append(hexaGosperR(n-1));
	}
	static StringBuilder hexaGosperL(int n){
		if (n==0)
			return new StringBuilder("F");
		else
			return 	hexaGosperL(n-1).append('+')
					.append(hexaGosperR(n-1)).append("++")
					.append(hexaGosperR(n-1)).append('-')
					.append(hexaGosperL(n-1)).append("--")
					.append(hexaGosperL(n-1))
					.append(hexaGosperL(n-1)).append('-')
					.append(hexaGosperR(n-1)).append('+');
	}
	public static String getPlant1(int n){
		return olPlant1(n).toString();
	}
	static StringBuilder olPlant1(int n){
		if (n==0)
			return new StringBuilder("F");
		else
			return 	olPlant1(n-1).append("[+")
					.append(olPlant1(n-1)).append(']')
					.append(olPlant1(n-1)).append("[-")
					.append(olPlant1(n-1)).append(']')
					.append(olPlant1(n-1));
	}
	public static String getPlant2(int n){
		return olPlant2X(n).toString();
	}
	static StringBuilder olPlant2X(int n){
		if (n==1)
			return new StringBuilder("FF");
		else
			return 	olPlant2F(n-1).append("[+")
					.append(olPlant2X(n-1)).append("]")
					.append(olPlant2F(n-1))
					.append("[-")
					.append(olPlant2X(n-1)).append("]+")
					.append(olPlant2X(n-1));
	}
	static StringBuilder olPlant2F(int n){
		if (n==0)
			return new StringBuilder("F");
		else
			return olPlant2F(n-1).append(olPlant2F(n-1));
	}
	public static String getPlant3(int n){
		return olPlant3X(n).toString();
	}
	static StringBuilder olPlant3X(int n){
		if (n==1)
			return new StringBuilder("FF");
		else
			return 	olPlant3F(n-1).append("[+")
					.append(olPlant3X(n-1)).append("]")
					.append("[-")
					.append(olPlant3X(n-1)).append("]")
					.append(olPlant3F(n-1))
					.append(olPlant3X(n-1));
	}
	static StringBuilder olPlant3F(int n){
		if (n==0)
			return new StringBuilder("F");
		else
			return olPlant3F(n-1).append(olPlant3F(n-1));
	}
	public static String getKoch(int n){
		return koch(n).toString();
	}
	static StringBuilder koch(int n){
		if (n==0)
			return new StringBuilder("F");
		else
			return 	koch(n-1).append('+')
					.append(koch(n-1))
					.append("--")
					.append(koch(n-1)).append('+')
					.append(koch(n-1));
	}
	public static Path2D buildTutlePathFromLSystem(String lsystem, double turnAngle, double stepLength){
		return buildTurtlePathFromLSystem(lsystem, turnAngle, stepLength, 0);
	}
	public static Path2D buildTurtlePathFromLSystem(String lsystem, double turnAngle, double stepLength, double dir){
		Stack<TurtleState> contextSave = new Stack<TurtleState>(); 
		double radTurnAngle = Math.toRadians(turnAngle);
		double direction = Math.toRadians(dir);
		Point2D location = new Point2D.Double(0,0);
		Path2D turtlePath = new Path2D.Double();
		turtlePath.moveTo(location.getX(), location.getY());
		//System.out.print("inter=");
		
		for (int i=0; i<lsystem.length(); ++i){
			char c1 = lsystem.charAt(i);
			if (c1=='F'){
				Point2D newloc = 
					new Point2D.Double(
						location.getX()+Math.cos(direction)*stepLength,
						location.getY()+Math.sin(direction)*stepLength);
				turtlePath.lineTo(newloc.getX(), newloc.getY());
				location = newloc;
				//System.out.println("F"+location);
			}
			else if (c1=='f'){
				Point2D newloc = 
					new Point2D.Double(
						location.getX()+Math.cos(direction)*stepLength,
						location.getY()+Math.sin(direction)*stepLength);
				turtlePath.moveTo(newloc.getX(), newloc.getY());
				location = newloc;
				//System.out.print("f");
			}
			else if (c1=='-') {// Turn Left
				direction -= radTurnAngle;
				//System.out.print("-");
			}
			else if (c1=='+') {// Turn Right
				direction += radTurnAngle;
				//System.out.print("+");
			}
			else if (c1=='['){
				contextSave.push(new TurtleState(direction, (Point2D)location.clone()));
				//System.out.println("["+location);
			}
			else if (c1==']'){
				TurtleState ctx = contextSave.pop();
				direction = ctx.direction;
				location = ctx.location;
				turtlePath.moveTo(location.getX(), location.getY());
				//System.out.println("]"+location);
			}
		}
		return turtlePath;
	}
}
class TurtleState {
	public double direction;
	public Point2D location;
	public TurtleState(double dir, Point2D loc){
		this.direction = dir;
		this.location = loc;
	}
}

