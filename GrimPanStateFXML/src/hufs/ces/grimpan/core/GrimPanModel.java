package hufs.ces.grimpan.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import hufs.ces.grimpan.command.AddCommand;
import hufs.ces.grimpan.command.Command;
import hufs.ces.grimpan.command.MoveCommand;
import hufs.ces.grimpan.state.DeleteBuilderState;
import hufs.ces.grimpan.state.EditState;
import hufs.ces.grimpan.state.LineBuilderState;
import hufs.ces.grimpan.state.MoveBuilderState;
import hufs.ces.grimpan.state.OvalBuilderState;
import hufs.ces.grimpan.state.PencilBuilderState;
import hufs.ces.grimpan.state.PolygonBuilderState;
import hufs.ces.grimpan.state.RegularBuilderState;
import hufs.ces.grimpan.svg.SVGGrimShape;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class GrimPanModel {

	private volatile static GrimPanModel uniqueModelInstance;
	
	private ShapeFactory sf = ShapeFactory.getInstance(this);
	
	public EditState editState = null;
	public EditState savedAddState = null;
	public final EditState STATE_REGULAR = new RegularBuilderState(this, sf);
	public final EditState STATE_OVAL = new OvalBuilderState(this, sf);
	public final EditState STATE_POLYGON = new PolygonBuilderState(this, sf);
	public final EditState STATE_LINE = new LineBuilderState(this, sf);
	public final EditState STATE_PENCIL = new PencilBuilderState(this, sf);
	public final EditState STATE_MOVE = new MoveBuilderState(this,sf);
	public final EditState STATE_DELETE = new DeleteBuilderState(this,sf);

	private float shapeStrokeWidth = 10f;
	private Color shapeStrokeColor = Color.BLACK;
	private boolean shapeStroke = true;
	private boolean shapeFill = false;
	private Color shapeFillColor = Color.WHITE;
	
	private Point2D startMousePosition = null;
	private Point2D currMousePosition = null;
	private Point2D prevMousePosition = null;
	
	public ObservableList<SVGGrimShape> shapeList = null;
	public SVGGrimShape curDrawShape = null;
	
	private int nPolygon = 3;
	public ArrayList<Point2D> polygonPoints = null;
	
	private File saveFile = null;
	private int selectedShapeIndex = -1;
	
	private Point2D movedPos = null;

	public Stack<Command> undoCommandStack = null;

	public static GrimPanModel getInstance() {
		if (uniqueModelInstance == null) {
			synchronized (GrimPanModel.class) {
				if (uniqueModelInstance == null) {
					uniqueModelInstance = new GrimPanModel();
				}
			}
		}
		return uniqueModelInstance;
	}
	private GrimPanModel(){
		this.shapeList = FXCollections.observableArrayList();
		this.polygonPoints = new ArrayList<Point2D>();

		this.shapeStrokeColor = Color.BLACK;
		this.shapeFillColor = Color.TRANSPARENT;

		this.setEditState(STATE_PENCIL);
		this.undoCommandStack = new Stack<Command>();
	}

	public EditState getEditState() {
		return editState;
	}

	public void setEditState(EditState editState) {
		this.editState = editState;
	}

	public float getShapeStrokeWidth() {
		return shapeStrokeWidth;
	}

	public void setShapeStrokeWidth(float shapeStrokeWidth) {
		this.shapeStrokeWidth = shapeStrokeWidth;
	}

	public Color getShapeStrokeColor() {
		return shapeStrokeColor;
	}

	public void setShapeStrokeColor(Color shapeStrokeColor) {
		this.shapeStrokeColor = shapeStrokeColor;
	}

	public boolean isShapeStroke() {
		return shapeStroke;
	}

	public void setShapeStroke(boolean shapeStroke) {
		this.shapeStroke = shapeStroke;
	}

	public boolean isShapeFill() {
		return shapeFill;
	}

	public void setShapeFill(boolean shapeFill) {
		this.shapeFill = shapeFill;
	}

	public Color getShapeFillColor() {
		return shapeFillColor;
	}

	public void setShapeFillColor(Color shapeFillColor) {
		this.shapeFillColor = shapeFillColor;
	}
	/**
	 * @return the saveFile
	 */
	public File getSaveFile() {
		return saveFile;
	}

	/**
	 * @param saveFile the saveFile to set
	 */
	public void setSaveFile(File saveFile) {
		this.saveFile = saveFile;
		//mainFrame.setTitle("�׸��� - "+saveFile.getPath());
	}
	/**
	 * @return the nPolygon
	 */
	public int getNPolygon() {
		return nPolygon;
	}

	/**
	 * @param nPolygon the nPolygon to set
	 */
	public void setNPolygon(int nPolygon) {
		this.nPolygon = nPolygon;
	}

	public Point2D getStartMousePosition() {
		return startMousePosition;
	}

	public void setStartMousePosition(Point2D startMousePosition) {
		this.startMousePosition = startMousePosition;
	}

	public Point2D getCurrMousePosition() {
		return currMousePosition;
	}

	public void setCurrMousePosition(Point2D currMousePosition) {
		this.currMousePosition = currMousePosition;
	}

	public Point2D getPrevMousePosition() {
		return prevMousePosition;
	}

	public void setPrevMousePosition(Point2D prevMousePosition) {
		this.prevMousePosition = prevMousePosition;
	}

	public int getSelectedShapeIndex() {
		return this.selectedShapeIndex;
	}

	public void setSelectedShapeIndex(int selIndex) {
		this.selectedShapeIndex = selIndex;
	}
	
	public void getSelectedShape(){
		int selIndex = -1;
		Shape shape = null;
		for (int i=this.shapeList.size()-1; i >= 0; --i){
			shape = this.shapeList.get(i).getShape();
			if (shape.contains(this.getStartMousePosition().getX(), this.getStartMousePosition().getY())){
				selIndex = i;
				break;
			}
		}
		if (selIndex != -1){
			Color scolor = (Color)shape.getStroke();
			Color fcolor = (Color)shape.getFill();
			if (shape.getStroke()!=Color.TRANSPARENT){
				shape.setStroke(new Color (scolor.getRed(), scolor.getGreen(), scolor.getBlue(), 0.5));
			}
			if (shape.getFill()!=Color.TRANSPARENT){
				shape.setFill(new Color (fcolor.getRed(), fcolor.getGreen(), fcolor.getBlue(), 0.5));
			}
		}
		this.setSelectedShapeIndex(selIndex);
	}

		
	public void addShapeAction() {
		Command addCommand = new AddCommand(this, this.curDrawShape);
		this.undoCommandStack.push(addCommand);// save for undo
		addCommand.execute();

	}

	public void moveShapeAction() {
		Command moveCommand = new MoveCommand(this, this.getMovedPos());
		this.undoCommandStack.push(moveCommand);// save for undo
		moveCommand.execute();
	}
	public void undoAction() {
		if (this.undoCommandStack.isEmpty())
			return;
		
		Command comm = this.undoCommandStack.pop();
		comm.undo();
		
	}
	public Point2D getMovedPos() {
		return movedPos;
	}
	public void setMovedPos(Point2D movedPos) {
		this.movedPos = movedPos;
	}
}
