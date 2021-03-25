/**
 * Created on Nov 28, 2012
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.lsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author cskim
 *
 */
public class DrawLSystemMain extends JFrame {

	private JPanel contentPane;
	private JMenuBar menuBar = null;
	private DrawLSystemPanel dPanel = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrawLSystemMain frame = new DrawLSystemMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DrawLSystemMain() {
		setTitle("L-System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		dPanel = new DrawLSystemPanel();
		contentPane.add(dPanel, BorderLayout.CENTER);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu jmFile = new JMenu("File  ");
		menuBar.add(jmFile);
		
		JMenuItem jmiNew = new JMenuItem("New ");
		jmiNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dPanel.shapeList.clear();
				dPanel.repaint();
			}
		});
		jmFile.add(jmiNew);
		
		JMenuItem jmiExit = new JMenuItem("Exit ");
		jmiExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		jmFile.add(jmiExit);
		
		JMenu jmPattern = new JMenu("Pattern");
		menuBar.add(jmPattern);
		
		JMenuItem jmiDragon = new JMenuItem("Dragon");
		jmiDragon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawADragon();
			}
		});
		jmPattern.add(jmiDragon);
		
		JMenuItem jmiDragon4 = new JMenuItem("Dragon4");
		jmiDragon4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				draw4Dragons();
			}
		});
		jmPattern.add(jmiDragon4);
		
		JMenuItem jmiSGasket = new JMenuItem("Sierpinski Gasket");
		jmiSGasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawSierpinskiGasket();
			}
		});
		jmPattern.add(jmiSGasket);
		
		JMenuItem jmiHexagosper = new JMenuItem("HexaGosper");
		jmiHexagosper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawHexaGosper();
			}
		});
		jmPattern.add(jmiHexagosper);
		
		JMenuItem jmiPlant1 = new JMenuItem("Plant1 ");
		jmiPlant1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPlant1();
			}
		});
		jmPattern.add(jmiPlant1);
		
		JMenuItem jmiPlant2 = new JMenuItem("Plant2 ");
		jmiPlant2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawPlant2();
			}
		});
		jmPattern.add(jmiPlant2);
		
		JMenuItem jmiPlant3 = new JMenuItem("Plant3 ");
		jmiPlant3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPlant3();
			}
		});
		jmPattern.add(jmiPlant3);
		
		JMenuItem jmiKoch = new JMenuItem("Koch");
		jmiKoch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawKoch();
			}
		});
		jmPattern.add(jmiKoch);
		
		JMenuItem jmiSnowflake = new JMenuItem("Snowflake");
		jmiSnowflake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawSnowflake();
			}
		});
		jmPattern.add(jmiSnowflake);
		
		
	}

	void drawADragon(){
		// Draw a Dragon Curve
		int level = Integer.parseInt(getDrawLevel(10));
		int stepLength = getStepLength(level);

		String dragon1 = BuildLSystemCurve.getDragonCurve(level);
		//System.out.println("dragon LSystem="+dragon1);
		Path2D dpath1 = BuildLSystemCurve
				.buildTutlePathFromLSystem(dragon1, 90, stepLength);
		dPanel.shapeList.add(new GrimShape(dpath1, Color.BLACK, false));

		int xCenter = dPanel.getWidth() / 2;
		int yCenter = dPanel.getHeight() / 2;
		dPanel.setStartPoint(new Point(xCenter, yCenter));
		dPanel.repaint();
		
	}
	void draw4Dragons(){
		// Draw 4 Dragon Curves
		int level = Integer.parseInt(getDrawLevel(10));
		int stepLength = getStepLength(level);
		
		String dragon1 = BuildLSystemCurve.getDragonCurve(level);
		//System.out.println("dragon LSystem="+dragon1);
		Path2D dpath1 = BuildLSystemCurve
				.buildTutlePathFromLSystem(dragon1, 90, stepLength);
		dPanel.shapeList.add(new GrimShape(dpath1, Color.RED, false));
		dragon1 = "+"+dragon1;
		dpath1 = BuildLSystemCurve
				.buildTutlePathFromLSystem(dragon1, 90, stepLength);
		dPanel.shapeList.add(new GrimShape(dpath1, Color.BLUE, false));
		dragon1 = "+"+dragon1;
		dpath1 = BuildLSystemCurve
				.buildTutlePathFromLSystem(dragon1, 90, stepLength);
		dPanel.shapeList.add(new GrimShape(dpath1, Color.GREEN, false));
		dragon1 = "+"+dragon1;
		dpath1 = BuildLSystemCurve
				.buildTutlePathFromLSystem(dragon1, 90, stepLength);
		dPanel.shapeList.add(new GrimShape(dpath1, Color.YELLOW, false));
		
		int xCenter = dPanel.getWidth() / 2;
		int yCenter = dPanel.getHeight() / 2;
		dPanel.setStartPoint(new Point(xCenter, yCenter));
		dPanel.repaint();

	}
	void drawSierpinskiGasket(){
		int level = Integer.parseInt(getDrawLevel(7));
		int stepLength = getStepLength(level)/4;
		String sierpinski = BuildLSystemCurve.getSierpinskiGasket(level);
		Path2D dpath1 = BuildLSystemCurve
				.buildTutlePathFromLSystem(sierpinski, 60, stepLength);
		GrimShape gShape = new GrimShape(dpath1, Color.BLACK, false);
		dPanel.shapeList.add(gShape);
		
		int px = stepLength;
		int py = dPanel.getHeight()-stepLength;
		dPanel.setStartPoint(new Point(px, py));
		dPanel.repaint();
	}
	void drawHexaGosper(){
		int level = Integer.parseInt(getDrawLevel(4));
		int stepLength = getStepLength(level)/level;
		String gosper = BuildLSystemCurve.getHexaGosper(level);
		Path2D dpath1 = BuildLSystemCurve
				.buildTutlePathFromLSystem(gosper, 60, stepLength);
		GrimShape gShape = new GrimShape(dpath1, Color.BLACK, false);
		dPanel.shapeList.add(gShape);
		
		int px = dPanel.getWidth()*3/4;
		int py = dPanel.getHeight()/8;
		dPanel.setStartPoint(new Point(px, py));
		dPanel.repaint();
	}
	void drawPlant1(){
		int level = Integer.parseInt(getDrawLevel(5));
		int stepLength = getStepLength(level)/(2*level);
		String plant1 = BuildLSystemCurve.getPlant1(level);
		Path2D dpath1 = BuildLSystemCurve
				.buildTurtlePathFromLSystem(plant1, 25.7, stepLength, -90);
		GrimShape gShape = new GrimShape(dpath1, Color.BLACK, false);
		dPanel.shapeList.add(gShape);
		
		int px = dPanel.getWidth()/2;
		int py = dPanel.getHeight()-stepLength;
		dPanel.setStartPoint(new Point(px, py));
		dPanel.repaint();
	}
	void drawPlant2(){
		int level = Integer.parseInt(getDrawLevel(7));
		int stepLength = getStepLength(level)/level*2;
		String plant2 = BuildLSystemCurve.getPlant2(level);
		//System.out.println("plant2="+plant2);
		Path2D dpath1 = BuildLSystemCurve
				.buildTurtlePathFromLSystem(plant2, 20, stepLength, -90);
		GrimShape gShape = new GrimShape(dpath1, Color.BLACK, false);
		dPanel.shapeList.add(gShape);
		
		int px = dPanel.getWidth()/2;
		int py = dPanel.getHeight()-stepLength;
		dPanel.setStartPoint(new Point(px, py));
		dPanel.repaint();
	}
	void drawPlant3(){
		int level = Integer.parseInt(getDrawLevel(7));
		int stepLength = getStepLength(level)/level*2;
		String plant3 = BuildLSystemCurve.getPlant3(level);
		Path2D dpath1 = BuildLSystemCurve
				.buildTurtlePathFromLSystem(plant3, 25.7, stepLength, -90);
		GrimShape gShape = new GrimShape(dpath1, Color.BLACK, false);
		dPanel.shapeList.add(gShape);
		
		int px = dPanel.getWidth()/2;
		int py = dPanel.getHeight()-stepLength;
		dPanel.setStartPoint(new Point(px, py));
		dPanel.repaint();
	}
	void drawKoch(){
		int level = Integer.parseInt(getDrawLevel(5));
		int stepLength = getStepLength(level)/(2*level);
		String koch1 = BuildLSystemCurve.getKoch(level);
		Path2D dpath1 = BuildLSystemCurve
				.buildTurtlePathFromLSystem(koch1, -60, stepLength, 0);
		GrimShape gShape = new GrimShape(dpath1, Color.BLACK, false);
		dPanel.shapeList.add(gShape);
		
		int px = stepLength;
		int py = dPanel.getHeight()/2;
		dPanel.setStartPoint(new Point(px, py));
		dPanel.repaint();
	}
	void drawSnowflake(){
		int level = Integer.parseInt(getDrawLevel(5));
		int stepLength = getStepLength(level)/(2*level);
		String koch1 = BuildLSystemCurve.getKoch(level);
		String snowflake = "+"+koch1+"--"+koch1+"--"+koch1;
		Path2D dpath1 = BuildLSystemCurve
				.buildTurtlePathFromLSystem(snowflake, -60, stepLength, 0);
		GrimShape gShape = new GrimShape(dpath1, Color.BLACK, false);
		dPanel.shapeList.add(gShape);
		
		int px = stepLength;
		int py = dPanel.getHeight()*3/4-stepLength;
		dPanel.setStartPoint(new Point(px, py));
		dPanel.repaint();
	}
	String getDrawLevel(int defval){
		Object[] possibleValues = { 
				"1", "2", "3", "4", "5", "6", "7",
				"8", "9", "10", "11", "12",
				"13", "14", "15"
				};
		Object selectedValue = JOptionPane.showInputDialog(
				null,
				"Choose one", "Input",
		        JOptionPane.INFORMATION_MESSAGE, null,
	            possibleValues, possibleValues[defval-1]);
		if (selectedValue==null)
			selectedValue = "10";
		return (String)selectedValue;
	}
	int getStepLength(int level){
		return getWidth()/((level+1)*(level+1));
	}
}
