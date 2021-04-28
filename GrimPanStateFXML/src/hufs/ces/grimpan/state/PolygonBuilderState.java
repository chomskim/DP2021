/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.state;

import hufs.ces.grimpan.core.GrimPanModel;
import hufs.ces.grimpan.core.ShapeFactory;
import hufs.ces.grimpan.svg.SVGGrimPolygon;
import hufs.ces.grimpan.svg.SVGGrimPolyline;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

/**
 * @author cskim
 *
 */
public class PolygonBuilderState implements EditState {

	ShapeFactory sf = null;	
	GrimPanModel model = null;
	
	public PolygonBuilderState(GrimPanModel model, ShapeFactory sf){
		this.model = model;
		this.sf = sf;
	}
	@Override
	public int getStateType() {
		return EditState.SHAPE_POLYGON;
	}
	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMousePressed(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setStartMousePosition(p1);
		model.setCurrMousePosition(p1);
		model.setPrevMousePosition(p1);		

		model.polygonPoints.add(model.getCurrMousePosition());
		if (event.isShiftDown()) {
			//((Path)model.curDrawShape).getElements().add(new ClosePath());
			model.curDrawShape = new SVGGrimPolygon((Polygon)(sf.createPolygonFromClickedPoints()));
			model.polygonPoints.clear();
			model.shapeList.add(model.curDrawShape);
			model.curDrawShape = null;
			model.addShapeAction();
		}
		else {
			model.curDrawShape = new SVGGrimPolyline((Polyline)(sf.createPolylineFromClickedPoints()));
		}
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseReleased(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setPrevMousePosition(model.getCurrMousePosition());
		model.setCurrMousePosition(p1);

	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseDragged(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setPrevMousePosition(model.getCurrMousePosition());
		model.setCurrMousePosition(p1);

	}

}
