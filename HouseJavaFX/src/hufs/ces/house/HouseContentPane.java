/**
 * Created on Mar 9, 2018
 * @author cskim -- hufs.ac.kr, Dept of CES
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.house;

import javafx.scene.layout.BorderPane;

public class HouseContentPane extends BorderPane {
	
	HouseModel model = null;
	DrawPane drawpane = null;
	
	public HouseContentPane() {		
		this.model = new HouseModel();
		this.drawpane = new DrawPane(model);
		
		initialize();
	}
	void initialize() {
		this.setPrefSize(800, 450);
		this.setCenter(drawpane);
		
		buildHouse();
		this.drawpane.redraw();
		
	}
	void buildHouse(){
		House house1 = new House(model, null, 50, 50, 200, 350);
		Roof roof1 = new DomeRoof(model, house1, 0, 0, 200, 100);
		house1.setRoof(roof1);
		Wall wall1 = new Wall(model, house1, 0, 100, 200, 250);
		Window win1 = new DoubleWindow(model, wall1, 40, 40, 40, 80);
		//win1.setSash(true);
		wall1.windows.add(win1);
		Window win2 = new DoubleWindow(model, wall1, 120, 40, 40, 80);
		//win2.setSash(true);
		wall1.windows.add(win2);
		Door door1 = new Door(model, wall1, 86, 180, 35, 70);
		//door1.setLeftKnob(false);
		wall1.setDoor(door1);
		house1.setWall(wall1);
		house1.draw();
				
		house1 = new House(model, null, 250, 100, 300, 300);
		roof1 = new GambrelRoof(model, house1, 0, 0, 300, 100);
		house1.setRoof(roof1);
		wall1 = new Wall(model, house1, 0, 100, 300, 200);
		win1 = new QuadWindow(model, wall1, 80, 20, 36, 70);
		win1.setSash(true);
		wall1.windows.add(win1);
		win2 = new QuadWindow(model, wall1, 200, 20, 36, 70);
		win2.setSash(true);
		wall1.windows.add(win2);
		door1 = new Door(model, wall1, 50, 140, 33, 60);
		//door1.setLeftKnob(false);
		wall1.setDoor(door1);
		house1.setWall(wall1);
		house1.draw();
		
		house1 = new House(model, null, 550, 20, 200, 380);
		roof1 = new CathedralRoof(model, house1, 0, 0, 200, 100);
		house1.setRoof(roof1);
		wall1 = new Wall(model, house1, 0, 100, 200, 280);
		win1 = new Window(model, wall1, 30, 40, 40, 80);
		win1.setSash(true);
		wall1.windows.add(win1);
		win2 = new Window(model, wall1, 130, 40, 40, 80);
		win2.setSash(true);
		wall1.windows.add(win2);
		door1 = new Door(model, wall1, 150, 200, 40, 80);
		door1.setLeftKnob(false);
		wall1.setDoor(door1);
		house1.setWall(wall1);
		house1.draw();
		
	}
	void buildHouse2(){
		House house1 = new House(model, null, 50, 50, 200, 350);
		Roof roof1 = new GambrelRoof(model, house1, 0, 0, 200, 100);
		house1.setRoof(roof1);
		Wall wall1 = new Wall(model, house1, 0, 100, 200, 250);
		Window win1 = new QuadWindow(model, wall1, 40, 40, 40, 80);
		win1.setSash(true);
		wall1.windows.add(win1);
		Window win2 = new QuadWindow(model, wall1, 120, 40, 40, 80);
		win2.setSash(true);
		wall1.windows.add(win2);
		Door door1 = new Door(model, wall1, 86, 180, 35, 70);
		door1.setLeftKnob(false);
		wall1.setDoor(door1);
		house1.setWall(wall1);		
		house1.draw();
		
		house1 = new House(model, null, 250, 100, 300, 300);
		roof1 = new CathedralRoof(model, house1, 0, 0, 300, 100);
		house1.setRoof(roof1);
		wall1 = new Wall(model, house1, 0, 100, 300, 200);
		win1 = new Window(model, wall1, 80, 20, 36, 70);
		wall1.windows.add(win1);
		win2 = new Window(model, wall1, 200, 20, 36, 70);
		wall1.windows.add(win2);
		door1 = new Door(model, wall1, 50, 140, 33, 60);
		wall1.setDoor(door1);
		house1.setWall(wall1);	
		house1.draw();
		
		house1 = new House(model, null, 550, 20, 200, 380);
		roof1 = new DomeRoof(model, house1, 0, 0, 200, 100);
		house1.setRoof(roof1);
		wall1 = new Wall(model, house1, 0, 100, 200, 280);
		win1 = new DoubleWindow(model, wall1, 30, 40, 40, 80);
		win1.setSash(true);
		wall1.windows.add(win1);
		win2 = new DoubleWindow(model, wall1, 130, 40, 40, 80);
		win2.setSash(true);
		wall1.windows.add(win2);
		door1 = new Door(model, wall1, 150, 200, 40, 80);
		wall1.setDoor(door1);
		house1.setWall(wall1);		
		house1.draw();
	}

}
