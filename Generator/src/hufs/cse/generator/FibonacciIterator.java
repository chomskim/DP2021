package hufs.cse.generator;

import java.util.Iterator;

public class FibonacciIterator implements Iterator<Integer> {

	private int num1 = 0;
	private int num2 = 1;	
	private int maxCount = Integer.MAX_VALUE;
	private int curCount = 0;
	
	public FibonacciIterator(){}
	public FibonacciIterator(int mcount){
		this.maxCount = mcount;
	}
	public boolean hasNext() {
		if (curCount < maxCount && num2 < Integer.MAX_VALUE)
			return true;
		else
			return false;
	}

	public Integer next() {
		int retValue = num2;
		num2 = num1 + num2;
		num1 = retValue;
		curCount++;
		return new Integer(retValue);
	}

	public void remove() {
		throw new UnsupportedOperationException();

	}

}
