/**
 * Created on 2015. 3. 24.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.house.factory;

import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author cskim
 *
 */
public class HouseFactory {

	static final Paint[] roofPaint = {
		Utils.ROOF1, Utils.ROOF2, Utils.ROOF3, Utils.ROOF4,
	};
	static final Paint[] wallPaint = {
		Utils.BLUE_WALL, Utils.COLOR_WALL, Utils.RED_WALL1, Utils.RED_WALL2,
		Utils.RED_WALL3, Utils.RED_WALL4, Utils.YELLOW_WALL, Utils.TEAL_WALL, Utils.WHITE_WALL,
	};
	static final BufferedImage[] doorImage = {
		Utils.BROWN_DOOR_IMAGE, Utils.WHITE_DOOR_IMAGE, Utils.WOOD_DOOR1_IMAGE,
	};
	
	public House createHouse(int baseX, int baseY, int width, int height){
		
		House house = new House(null, baseX, baseY-height, width, height);
		
		int roofType = Utils.dice(1, 3);
		Roof roof;
		if (roofType == 1){
			roof = new CathedralRoof(house, 0, 0, width, height/3);
		}
		else if (roofType == 2){
			roof = new GambrelRoof(house, 0, 0, width, height/3);
		}
		else {
			roof = new DomeRoof(house, 0, 0, width, height/3);
		}
		roof.setPaint(roofPaint[Utils.dice(0,3)]);
		house.setRoof(roof);
		
		Wall wall = new Wall(house, 0, roof.getHeight(), width, height-roof.getHeight());
		
		wall.setPaint(wallPaint[Utils.dice(0,8)]);
		
		int wunit = wall.getWidth()/6;
		
		int winType = Utils.dice(1, 3);
		int doorPos = Utils.dice(1,3);
		
		createFirstFloor(wall, winType, doorPos);
		
		if (wall.getHeight()>=8*wunit){
			createSecondFloor(wall, winType, doorPos);
		}

		house.setWall(wall);
		
		return house;
	}
	void createFirstFloor(Wall wall, int winType, int doorPos){
		int wunit = wall.getWidth()/6;
		Door door = createDoor(wall, doorPos*wunit, wall.getHeight()-4*wunit, 2*wunit, 4*wunit);
		wall.setDoor(door);
		
		wall.windows = new ArrayList<Window>();
		if (doorPos==1){
			Window win = createWindow(winType, wall, 4*wunit, wall.getHeight()-3*wunit, wunit, wunit*2);
			wall.windows.add(win);
		}
		else if (doorPos==3){
			Window win = createWindow(winType, wall, wunit, wall.getHeight()-3*wunit, wunit, wunit*2);			
			wall.windows.add(win);
		}
		else {
			Window win1 = createWindow(winType, wall, (int)(0.5*wunit), wall.getHeight()-3*wunit, wunit, wunit*2);
			Window win2 = createWindow(winType, wall, (int)(4.5*wunit), wall.getHeight()-3*wunit, wunit, wunit*2);
			wall.windows.add(win1);
			wall.windows.add(win2);
		}
	}
	void createSecondFloor(Wall wall, int winType, int doorPos){
		if (doorPos==2){
			wall.windows.addAll(createWindows(wall, 3, winType));
		}
		else {
			wall.windows.addAll(createWindows(wall, 2, winType));
		}
	}
	public ArrayList<Window> createWindows(Wall wall, int winCount, int winType){
		
		ArrayList<Window> windows = new ArrayList<Window>();
		
		int wunit = wall.getWidth()/6;
		if (winCount==2){
			Window win1 = createWindow(winType, wall, wunit, wunit, wunit, wunit*2);
			Window win2 = createWindow(winType, wall, 4*wunit, wunit, wunit, wunit*2);
			windows.add(win1);
			windows.add(win2);
			
		}
		else if (winCount==3){
			Window win1 = createWindow(winType, wall, (int)(0.5*wunit), wunit, wunit, wunit*2);
			Window win2 = createWindow(winType, wall, (int)(2.5*wunit), wunit, wunit, wunit*2);
			Window win3 = createWindow(winType, wall, (int)(4.5*wunit), wunit, wunit, wunit*2);
			windows.add(win1);
			windows.add(win2);
			windows.add(win3);
			
		}
		return windows;
	}
	public Window createWindow(int winType, Wall wall, int x, int y, int width, int height){
		if(winType==2){
			return new DoubleWindow(wall, x, y, width, height);
		}
		else if(winType==3){
			return new QuadWindow(wall, x, y, width, height);
		}
		else {
			return new Window(wall, x, y, width, height);
		}
	}
	public Door createDoor(Wall wall, int x, int y, int width, int height){
		Door door = new Door(wall, x, y, width, height);
		
		Paint dpaint = TexturePaintFactory.createDoorPaint(door, doorImage[Utils.dice(0, 2)]);
		door.setLeftKnob(Utils.dice(0,1)==0);
		door.setPaint(dpaint);
		return door;
	}
}
