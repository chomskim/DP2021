/**
 * Created on Dec 3, 2015
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.rmi;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import hufs.ces.svggrim.DrawPanel;
import hufs.ces.svggrim.GrimPanModel;
import hufs.ces.svggrim.GrimShape;
import hufs.ces.svggrim.GrimShape2SVGTranslator;
import hufs.ces.svggrim.SVG2GrimShapeTranslator;
import hufs.ces.svggrim.SaxSVGParseHandler;

public class RMIGrimTalkClient extends JFrame {

	private static final String HOST = "localhost";

	private static RMIMessanger rmiObj;

	private String clientID = null;
	private String partnerID = null;
	private ReceiverThread receiver = null;

	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 600;
	private final String defaultDir = "/home/cskim/temp/";
	private final FileNameExtensionFilter grimFileModelFilter = 
			new FileNameExtensionFilter("Grim Files", "grm");

	private JFileChooser jFileChooser1 = null;
	private JFileChooser jFileChooser2 = null;

	private GrimPanModel model = null;

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuHelp;
	private JMenuItem jmiNew;
	private JMenuItem jmiOpen;
	private JMenuItem jmiExit;
	private JMenuItem jmiAbout;

	RMIGrimTalkClient thisClass =  this;
	
	private DrawPanel drawPanel;
	private JMenu menuShape;
	private JMenu menuSetting;
	JRadioButtonMenuItem rdbtnmntmLine;
	JRadioButtonMenuItem rdbtnmntmPen;	
	JRadioButtonMenuItem rdbtnmntmPoly;

	private JMenuItem jmiStrokeWidth;
	private JMenuItem jmiStrokeColor;
	private JMenuItem jmiFillColor;
	private JCheckBoxMenuItem jcmiFill;

	private ButtonGroup btnGroup = new ButtonGroup();
	private JRadioButtonMenuItem rdbtnmntmReg;
	private JRadioButtonMenuItem rdbtnmntmEllipse;
	private JMenuItem jmiSave;
	private JMenuItem jmiSaveAs;
	private JMenu menuEdit;
	private JMenuItem jmiMove;
	private JMenuItem jmiDelete;
	private JMenuItem jmiSaveAsSVG;
	private JPanel btnPanel;
	private JButton btnRegister;
	private JButton btnConnect;
	private JButton btnSend;
	private JPanel statusPanel;
	private JLabel lblMessage;

	/**
	 * Create the frame.
	 */
	public RMIGrimTalkClient() {
		model = new GrimPanModel();
		initialize();
	}

	void initialize(){

		setTitle("GrimTalk");		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, SCREEN_WIDTH, SCREEN_HEIGHT);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menuFile = new JMenu("File  ");
		menuBar.add(menuFile);

		jmiNew = new JMenuItem("New  ");
		jmiNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearDrawPanel();
			}
		});
		menuFile.add(jmiNew);

		jmiOpen = new JMenuItem("Open ");
		jmiOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAction();
			}
		});
		menuFile.add(jmiOpen);

		jmiSave = new JMenuItem("Save ");
		jmiSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAction();
			}
		});
		menuFile.add(jmiSave);

		jmiSaveAs = new JMenuItem("Save As ...");
		jmiSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAsAction();
			}
		});
		menuFile.add(jmiSaveAs);

		jmiSaveAsSVG = new JMenuItem("Save As SVG");
		jmiSaveAsSVG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAsSVGAction();
			}
		});
		menuFile.add(jmiSaveAsSVG);

		menuFile.addSeparator();

		jmiExit = new JMenuItem("Exit  ");
		jmiExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuFile.add(jmiExit);

		menuShape = new JMenu("Shape  ");
		menuBar.add(menuShape);

		rdbtnmntmLine = new JRadioButtonMenuItem("����");
		rdbtnmntmLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setEditState(GrimPanModel.SHAPE_LINE);
			}
		});
		rdbtnmntmLine.setSelected(true);
		menuShape.add(rdbtnmntmLine);

		rdbtnmntmPen = new JRadioButtonMenuItem("����");
		rdbtnmntmPen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setEditState(GrimPanModel.SHAPE_PENCIL);
			}
		});
		menuShape.add(rdbtnmntmPen);

		rdbtnmntmPoly = new JRadioButtonMenuItem("�ٰ���");
		rdbtnmntmPoly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setEditState(GrimPanModel.SHAPE_POLYGON);
			}
		});
		menuShape.add(rdbtnmntmPoly);

		rdbtnmntmReg = new JRadioButtonMenuItem("���ٰ���");
		rdbtnmntmReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setEditState(GrimPanModel.SHAPE_REGULAR);
				Object[] possibleValues = { 
						"3", "4", "5", "6", "7",
						"8", "9", "10", "11", "12"
				};
				Object selectedValue = JOptionPane.showInputDialog(thisClass,
						"Choose one", "Input",
						JOptionPane.INFORMATION_MESSAGE, null,
						possibleValues, possibleValues[0]);
				model.setNPolygon(Integer.parseInt((String)selectedValue));
				drawPanel.repaint();
			}
		});
		menuShape.add(rdbtnmntmReg);

		rdbtnmntmEllipse = new JRadioButtonMenuItem("Ÿ����");
		rdbtnmntmEllipse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setEditState(GrimPanModel.SHAPE_OVAL);
			}
		});
		menuShape.add(rdbtnmntmEllipse);

		menuEdit = new JMenu("Edit  ");
		menuBar.add(menuEdit);

		jmiMove = new JMenuItem("Move ");
		jmiMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveEditAction();
			}
		});
		menuEdit.add(jmiMove);

		jmiDelete = new JMenuItem("Delete");
		menuEdit.add(jmiDelete);

		menuSetting = new JMenu("Setting ");
		menuBar.add(menuSetting);

		jmiStrokeWidth = new JMenuItem("���β�");
		jmiStrokeWidth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputVal = JOptionPane.showInputDialog(thisClass, "���β�", "1");
				if (inputVal!=null){
					model.setStrokeWidth(Float.parseFloat(inputVal));
					drawPanel.repaint();
				}
			}
		});

		menuSetting.add(jmiStrokeWidth);

		jmiStrokeColor = new JMenuItem("������");
		jmiStrokeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = 
						JColorChooser.showDialog(thisClass, 
								"Choose a color",
								Color.black);					
				model.setStrokeColor(color);
				drawPanel.repaint();
			}
		});
		menuSetting.add(jmiStrokeColor);

		jcmiFill = new JCheckBoxMenuItem("ä���");
		jcmiFill.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean fillState = jcmiFill.getState();
				model.setShapeFill(fillState);
				drawPanel.repaint();
			}
		});
		menuSetting.add(jcmiFill);

		jmiFillColor = new JMenuItem("ä�����");
		jmiFillColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = 
						JColorChooser.showDialog(thisClass, 
								"Choose a color",
								Color.black);					
				model.setFillColor(color);
				drawPanel.repaint();
			}
		});
		menuSetting.add(jmiFillColor);

		menuHelp = new JMenu("Help  ");
		menuBar.add(menuHelp);

		jmiAbout = new JMenuItem("About");
		jmiAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(thisClass,
						"GrimPan Ver0.3 \nProgrammed by cskim, cse, hufs.ac.kr", 
						"About", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		menuHelp.add(jmiAbout);

		btnGroup.add(rdbtnmntmLine);
		btnGroup.add(rdbtnmntmPen);
		btnGroup.add(rdbtnmntmPoly);
		btnGroup.add(rdbtnmntmReg);
		btnGroup.add(rdbtnmntmEllipse);


		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		drawPanel = new DrawPanel(model);
		drawPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(drawPanel, BorderLayout.CENTER);
		drawPanel.setLayout(new BorderLayout(0, 0));

		jFileChooser1 = new JFileChooser(defaultDir);
		jFileChooser1.setDialogTitle("Open Saved GrimPan");
		jFileChooser1.setFileFilter(grimFileModelFilter);


		jFileChooser2 = new JFileChooser(defaultDir);
		jFileChooser2.setDialogType(JFileChooser.SAVE_DIALOG);
		jFileChooser2.setDialogTitle("Save As ...");
		jFileChooser2.setFileFilter(grimFileModelFilter);

		
		btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.NORTH);
		btnPanel.setLayout(new GridLayout(1, 3, 0, 0));
		
		btnRegister = new JButton("Register");
		btnPanel.add(btnRegister);
		btnRegister.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String id = JOptionPane.showInputDialog(thisClass, "Enter Your ID");
				if (id==null) return;
				try {
					if (rmiObj.register(id) ) {
						clientID = id; 
						btnRegister.setEnabled(false);
						thisClass.setTitle("ID <"+id+"> ");
					}
					else {
						lblMessage.setText("Invalid ID:" + id + "\n");
					}
				} 
				catch (RemoteException e1) {
					e1.printStackTrace();

				}
			}
		});
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {

				try {
					String id = rmiObj.connect(clientID);
					if (id != null) {
						partnerID = id;

						lblMessage.setText("You connected to ID:" + partnerID + "\n");
						btnConnect.setEnabled(false);

						receiver = new ReceiverThread(rmiObj);
						receiver.start();
					}
					else
						lblMessage.setText("You can not connect. Try again\n");

				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		
		btnPanel.add(btnConnect);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendGrimAction();
			}
		});
		btnPanel.add(btnSend);
		
		statusPanel = new JPanel();
		contentPane.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setLayout(new BorderLayout(0, 0));
		
		lblMessage = new JLabel("");
		lblMessage.setPreferredSize(new Dimension(300, 30));
		statusPanel.add(lblMessage, BorderLayout.WEST);
	}

	void clearDrawPanel(){
		model.shapeList = new ArrayList<GrimShape>();
		model.polygonPoints = new ArrayList<Point>();

		drawPanel.repaint();
	}
	void openAction(){
		if (jFileChooser1.showOpenDialog(this) ==
				JFileChooser.APPROVE_OPTION) {
			File selFile = jFileChooser1.getSelectedFile();
			readShapeFromSaveFile(selFile);
			model.setSaveFile(selFile);
			drawPanel.repaint();
		}
	}
	void saveAction(){
		if (model.getSaveFile()==null){
			model.setSaveFile(new File(defaultDir+"noname.grm"));
		}
		File selFile = model.getSaveFile();
		saveGrimPanData(selFile);	
	}
	void saveAsAction(){
		if (jFileChooser2.showSaveDialog(this) ==
				JFileChooser.APPROVE_OPTION) {
			File selFile = jFileChooser2.getSelectedFile();
			if (selFile!=null){
				model.setSaveFile(selFile);
				saveGrimPanData(selFile);
			}
		}
	}
	void readShapeFromSaveFile(File saveFile) {
		model.setSaveFile(saveFile);
		try {
			ObjectInputStream input =
					new ObjectInputStream(new FileInputStream(model.getSaveFile()));
			model.shapeList = (ArrayList<GrimShape>) input.readObject();
			input.close();

		} catch (ClassNotFoundException e) {
			System.err.println("Class not Found");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void saveGrimPanData(File saveFile){
		ObjectOutputStream output;
		try {
			output = new ObjectOutputStream(new FileOutputStream(saveFile));
			output.writeObject(model.shapeList);
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void moveEditAction(){
		model.setEditState(GrimPanModel.EDIT_MOVE);
		if (model.curDrawShape != null){
			model.shapeList
			.add(new GrimShape(model.curDrawShape, 
					model.getStrokeWidth(), model.getStrokeColor(),
					model.getFillColor(), model.isShapeFill()));
			model.curDrawShape = null;
		}
		drawPanel.repaint();
	}
	void saveAsSVGAction(){

		File svgFile = new File(defaultDir+"noname.svg");

		if (model.getSaveFile()!=null){
			String saveFileName = model.getSaveFile().getName();
			svgFile = new File(defaultDir+saveFileName.replace(".grm", ".svg"));
		}

		PrintWriter svgout = null;
		try {
			svgout = new PrintWriter(svgFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		svgout.println("<?xml version='1.0' encoding='utf-8' standalone='no'?>");
		//svgout.println("<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.0//EN' 'http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd'>");
		svgout.print("<svg xmlns:svg='http://www.w3.org/2000/svg' ");
		svgout.println("     xmlns='http://www.w3.org/2000/svg' ");
		svgout.print("width='"+this.getWidth()+"' ");
		svgout.print("height='"+this.getHeight()+"' ");
		svgout.println("overflow='visible' xml:space='preserve'>");
		for (GrimShape gs:model.shapeList){
			svgout.println("    "+GrimShape2SVGTranslator.translateShape2SVG(gs));
		}
		svgout.println("</svg>");
		svgout.close();
	}
	void sendGrimAction(){
		StringBuilder sb = new StringBuilder();
		
		//sb.append("<?xml version='1.0' encoding='utf-8' standalone='no'?> \n");
		sb.append("<svg xmlns:svg='http://www.w3.org/2000/svg' ");
		sb.append("     xmlns='http://www.w3.org/2000/svg' \n");
		sb.append("width='"+SCREEN_WIDTH+"' ");
		sb.append("height='"+SCREEN_HEIGHT+"' ");
		sb.append("overflow='visible' xml:space='preserve'> \n");
		for (GrimShape gs:model.shapeList){
			sb.append(GrimShape2SVGTranslator.translateShape2SVG(gs));
			sb.append('\n');
		}
		sb.append("</svg>\n");

		String theLines = sb.toString();
		//System.out.println(theLines);

		try {
			rmiObj.write(clientID, theLines);
		} 
		catch (RemoteException e1) {
			e1.printStackTrace();

		}
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//System.setSecurityManager(new RMISecurityManager());
		Object obj = null;
		Context namingContext = null;
		String rmiObjectName = "rmi://" + HOST + "/RMIMessanger";
		try {
			namingContext = new InitialContext();
			System.out.println("RMI registry bindings: ");
			Enumeration<NameClassPair> e = namingContext.list("rmi://localhost/");
			while (e.hasMoreElements())
				System.out.println(e.nextElement().getName());
			
			obj = namingContext.lookup(rmiObjectName);
			rmiObj = (RMIMessanger) obj;
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RMIGrimTalkClient frame = new RMIGrimTalkClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	class ReceiverThread extends Thread {

		private RMIMessanger messanger;

		public ReceiverThread(RMIMessanger messanger) {
			this.messanger = messanger;
		}

		public void run() {

			while (true) {
				try {
					String theLines = messanger.read(clientID);
					//System.out.println("received svg="+theLines);
					InputStream grimStream = new ByteArrayInputStream(theLines.getBytes());
					model.attsMapList = new ArrayList<HashMap<String, String>>();
					SaxSVGParseHandler saxTreeHandler = new SaxSVGParseHandler(model); 

					try {
						SAXParserFactory saxf = SAXParserFactory.newInstance();
						//saxf.setFeature("http://xml.org/sax/features/validation", false);
						SAXParser saxParser = saxf.newSAXParser();
						saxParser.parse(new InputSource(grimStream), saxTreeHandler);
					}
					catch(Exception e){
						e.printStackTrace();
						JOptionPane.showMessageDialog(thisClass, e);
					}
					//System.out.println("map siz="+model.attsMapList.size());

					//int drawCount = 0;
					for (HashMap<String, String> map:model.attsMapList){
						
						ArrayList<GrimShape> gslist = SVG2GrimShapeTranslator.translateSVG2Shape(map);
						//System.out.println("shapelist size="+view.gmodel.shapeList.size());
						if (gslist != null && gslist.size()!=0){
							synchronized(model.shapeList){
								model.shapeList.addAll(gslist);
							}
							drawPanel.repaint();
						}
						//drawCount++;
						//System.out.println("drawcount="+drawCount);
					}
					
					Thread.yield();
				} 
				catch (RemoteException e) {
					e.printStackTrace();
				}

			}  

		}

	}


}
