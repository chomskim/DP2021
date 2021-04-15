package hufs.cse.generator;

import java.util.Iterator;

public class IntegerIterator implements Iterator<Integer> {
	
	private int bound = Integer.MAX_VALUE;
	private int curValue = 0;
	private int stepValue = 1;

	public IntegerIterator(){}
	public IntegerIterator(int bound){
		this.bound = bound;
	}
	public IntegerIterator(int start, int bound){
		this.curValue = start;
		this.bound = bound;
	}
	public IntegerIterator(int start, int bound, int step){
		this(start, bound);
		this.stepValue = step;
	}
	public boolean hasNext() {
		if (curValue < bound)
			return true;
		else
			return false;
	}

	public Integer next() {
		int retValue = curValue;
		curValue += stepValue;
		return new Integer(retValue);
	}

	public void remove() {
		throw new UnsupportedOperationException();

	}

}
