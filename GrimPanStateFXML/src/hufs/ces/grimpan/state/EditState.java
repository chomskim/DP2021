/**
 * Created on 2015. 4. 28.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.state;

import javafx.scene.input.MouseEvent;

/**
 * @author cskim
 *
 */
public interface EditState {

	public static final int SHAPE_REGULAR = 0;
	public static final int SHAPE_OVAL = 1;
	public static final int SHAPE_POLYGON = 2;
	public static final int SHAPE_LINE = 3;
	public static final int SHAPE_PENCIL = 4;
	public static final int EDIT_MOVE = 5;
	public static final int EDIT_DELETE = 6;
	
	public void performMousePressed(MouseEvent e);
	public void performMouseReleased(MouseEvent e);
	public void performMouseDragged(MouseEvent e);
	public int getStateType();
}
