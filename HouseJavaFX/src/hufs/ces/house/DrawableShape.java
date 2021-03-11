package hufs.ces.house;

public abstract class DrawableShape implements Drawable {

	protected HouseModel model;

	protected double x = 0;
	protected double y = 0;
	protected double width = 0;
	protected double height = 0;
	protected DrawableShape parent = null;
	
	public DrawableShape(HouseModel model, DrawableShape parent){
		this.model = model;
		this.parent = parent;
		if (parent!=null){
			this.width = parent.width;
			this.height = parent.height;
		}
	}
	public DrawableShape(HouseModel model, DrawableShape parent, int x, int y, int w, int h){
		this.model = model;
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	@Override
	public abstract void draw();

	public double absX(){
		if (parent == null){
			return this.x;
		}
		else {
			return this.x + parent.absX();
		}
	}
	public double absY(){
		if (parent == null){
			return this.y;
		}
		else {
			return this.y + parent.absY();
		}
	}
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	/**
	 * @return the parent
	 */
	public DrawableShape getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(DrawableShape parent) {
		this.parent = parent;
	}

}
