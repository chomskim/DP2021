package hufs.cse.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestGenIterator {

	public TestGenIterator(){
		Integer[] arr = {1,2,3,4,5};
		List<Integer> intArray = new ArrayList<>(Arrays.asList(arr));
		Iterator<Integer> arrIter = intArray.iterator();
		List<Integer> intArrayCopy = new ArrayList<>();
		while (arrIter.hasNext())
			intArrayCopy.add(arrIter.next());
		
		Iterator<Integer> intIter1 = new IntegerIterator(10);
		Iterator<Integer> intIter2 = new IntegerIterator(10, 20);
		Iterator<Integer> intIter3 = new IntegerIterator(10, 20, 2);
		
		Iterator<Integer> fibIter1 = new FibonacciIterator(10);
		
		printIterator(arrIter);
		printIterator(intIter1);
		printIterator(intIter2);
		printIterator(intIter3);
		printIterator(fibIter1);
		
	}
	public void printIterator(Iterator<Integer> iter){
		System.out.println();
		int count = 0;
		while (iter.hasNext()){
			count++;
			System.out.println(count+": "+iter.next().toString());
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestGenIterator ti = new TestGenIterator();

	}

}
