package hufs.cse.house.factory;
/**
 * Created on Nov 20, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */

import java.awt.BorderLayout;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

/**
 * @author cskim
 *
 */
public class HouseFrame extends JFrame {
	
	static final int SCREEN_WIDTH = 1200;
	static final int SCREEN_HEIGHT = 700;
	static final int HOUSE_COUNT = 10;
	
	private JPanel contentPane;
	private HouseModel houseModel = new HouseModel();
	private DrawPanel drawPanel = new DrawPanel(houseModel);

	/**
	 * Create the frame.
	 */
	public HouseFrame() {
		
		initialize();
		
		buildHouse();
	}

	private void initialize(){
		
		setTitle("Houses");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, SCREEN_WIDTH+20, SCREEN_HEIGHT);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		drawPanel.setBackground(new Color(238, 232, 170));
		contentPane.add(drawPanel, BorderLayout.CENTER);
		
		//SCREEN_WIDTH = drawPanel.getWidth();
		//SCREEN_HEIGHT = drawPanel.getHeight();
	}
	
	void buildHouse(){
		HouseFactory hf = new HouseFactory();
		int defWidth = SCREEN_WIDTH/HOUSE_COUNT;
		//int varWidth = Math.round(defWidth/3f);
		int varWidth = 0;
		int varHeight = Math.round(defWidth/6f);
		int xpos = 0;
		int ybase = (defWidth+varWidth+varHeight)*2;
		
		for (int i=1; i<=HOUSE_COUNT; ++i){
			//int width = defWidth + Utils.dice(-1, 1)*varWidth;
			int width = defWidth;
			int height =  (Utils.dice(1, 2)==1?width:width*2-varHeight) + Utils.dice(1,2)*varHeight;
			houseModel.houseList.add(hf.createHouse(xpos, ybase, width, height));
			xpos += width;
		}
		
		xpos = 0;
		ybase += ybase;
		for (int i=1; i<=HOUSE_COUNT; ++i){
			//int width = defWidth + Utils.dice(-1, 1)*varWidth;
			int width = defWidth;
			int height =  (Utils.dice(1, 2)==1?width:width*2-varHeight) + Utils.dice(1,2)*varHeight;
			houseModel.houseList.add(hf.createHouse(xpos, ybase, width, height));
			xpos += width;
		}

		drawPanel.repaint();
	}
}
