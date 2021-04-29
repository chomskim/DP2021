package hufs.ces.cirbuf.gui;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;

import hufs.ces.cirbuf.CircularBuffer;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CircularBufferSimController extends AnchorPane {

	private final static int DEFAULT_BUFFER_COUNT = 10;
	
	public Stage parentStage = null;

	volatile CircularBuffer<String> cirbuf = null;
	volatile Iterator<BigInteger> fibGen = null;
	BufferShape[] bufShapes = null;
	
	//volatile IntegerProperty bufUseCount = new SimpleIntegerProperty(0);
	

    @FXML
    private AnchorPane root;

    @FXML
    private TextField tfBufSize;

    @FXML
    private Label lblCount;

    @FXML
    private Button btnStart;

    @FXML
    private Pane drawPane;

    @FXML
    void handleBtnStart(ActionEvent event) {
    	if (cirbuf==null) {
    		initCircularBuffer(DEFAULT_BUFFER_COUNT);
    	}
		Thread prod = new Thread(new ProducerTask());
		Thread cons = new Thread(new ConsumerTask());
		prod.start();
		cons.start();
		Thread prod2 = new Thread(new ProducerTask());
		Thread cons2 = new Thread(new ConsumerTask());
		prod2.start();
		cons2.start();
    }

    @FXML
    void handleBufSizeIn(ActionEvent event) {
    	int siz  = Integer.parseInt(tfBufSize.getText());
		initCircularBuffer(siz);
    }
    
	public CircularBufferSimController() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cirbuf.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initialize();
	}

	private void initialize() {
		fibGen = new BigFibonacciIterator();
		//fibGen = new BigIntegerIterator();
		
	}
	void initCircularBuffer(int siz) {
    	//bufUseCount = new SimpleIntegerProperty(0);
		cirbuf =  new CircularBuffer<String>(siz);
		bufShapes = new BufferShape[cirbuf.getBufSize()];
		buildCircularBufferShape();
	}
	void buildCircularBufferShape() {
		drawPane.getChildren().clear();
		
		double cx = drawPane.getWidth()/2;
		double cy = drawPane.getHeight()/2;
		//System.out.println("cx="+cx+" cy="+cy);

		double outrad = cx*0.4;
		
		int siz = cirbuf.getBufSize(); 
		double angl = 2*Math.PI/siz;
		
		for (int i=0; i<siz; ++i) {
			bufShapes[i] = new BufferShape();
			bufShapes[i].setBufPath(cx, cy, outrad, angl);
			bufShapes[i].setText(String.valueOf(i), cx, cy, outrad, angl);
			bufShapes[i].setRot(i*Math.toDegrees(angl), cx, cy);
			bufShapes[i].setBackground(Color.SNOW);
			drawPane.getChildren().add(bufShapes[i]);
		}

	}
	
	void setBufferShapeColor() {
		int siz = cirbuf.getBufSize();
		int front = cirbuf.getFront();
		int rear = cirbuf.getRear();
		for (int i=0; i<siz; ++i) {
			bufShapes[i].setBackground(Color.SNOW);
		}
		int bp = front;
		for (int count=1; count <= cirbuf.getOccupiedBufferCount(); ++count) {
			bufShapes[bp].setBackground(Color.CYAN);
			bp = (bp + 1) % siz;
		}
		if (front!=rear ) {
			bufShapes[front].setBackground(Color.GREEN);
			bufShapes[rear].setBackground(Color.BLUE);
		}
		else if (cirbuf.getOccupiedBufferCount()>0) {
			bufShapes[front].setBackground(Color.GREEN);
		}
		else {
			bufShapes[front].setBackground(Color.CYAN);
		}
		
	}
	private class ProducerTask implements Runnable {
		public void run() {
			try {
				while (true) {
					//System.out.println("Producer writes " + i);
					cirbuf.write(String.valueOf(fibGen.next())); // Add a value to the buffer
					Platform.runLater(()->{
						setBufferShapeColor();
						lblCount.setText(String.valueOf(cirbuf.getOccupiedBufferCount()));
						double ratio = (double)cirbuf.getOccupiedBufferCount()/cirbuf.getBufSize();
						Utils.setBackground(lblCount, Utils.getRatioColor(ratio));
						//System.out.println("lblCount Style = "+lblCount.getStyle());
					});
					// Put the thread into sleep
					Thread.sleep((int)(Math.random() * 1000));
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	// A task for reading and deleting an int from the buffer
	private class ConsumerTask implements Runnable {
		public void run() {
			try {
				while (true) {
					String sval = cirbuf.read();
					System.out.println("\t\t\tConsumer reads " + sval);
					Platform.runLater(()->{
						setBufferShapeColor();
						lblCount.setText(String.valueOf(cirbuf.getOccupiedBufferCount()));
						double ratio = (double)cirbuf.getOccupiedBufferCount()/cirbuf.getBufSize();
						Utils.setBackground(lblCount, Utils.getRatioColor(ratio));
						//System.out.println("lblCount Style = "+lblCount.getStyle());
					});
					// Put the thread into sleep
					Thread.sleep((int)(Math.random() * 1000));
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

}
