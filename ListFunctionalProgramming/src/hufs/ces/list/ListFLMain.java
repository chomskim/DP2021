package hufs.ces.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
interface BinaryFunction<T,S,R> {
	R apply(T a, S b);
}
public class ListFLMain {

	static<T,R> List<R> map (List<T> list, Function<T,R> func) {
		List<R> result = new ArrayList<>();
		for (T t : list) {
			result.add(func.apply(t));
		}
		return result;
	}
	
	static<T,R> List<R> mapForEach (List<T> list, Function<T,R> func) {
		List<R> result = new ArrayList<>();
		list.forEach(t->{result.add(func.apply(t));});
		return result;
	}

	static<T,R> List<R> mapRecursive(List<T> list, Function<T,R> func) {
		if (list.size()==0)
			return new ArrayList<R>();
		else {
			List<R> result = mapRecursive(list.subList(1, list.size()), func);
			result.add(0, func.apply(list.get(0)));
			return result;
		}
	}
	
	static<T,R> List<R> mapRecursive1(List<T> list, Function<T,R> func) {
		if (list.size()==0)
			return new ArrayList<R>();
		else {
			T head = list.remove(0);
			List<R> result = mapRecursive1(list, func);
			result.add(0, func.apply(head));
			return result;
		}
	}

	static<T> List<T> filter(List<T> list, Predicate<T> pred) {
		List<T> result = new ArrayList<>();
		for (T t : list) {
			if (pred.test(t)) {
				result.add(t);
			}
		}
		return result;
	}
	static<T> List<T> tail (List<T> list) throws Exception {
		List<T> result = new ArrayList<>();
		if (list.size()==0)
			throw new Exception("Empty List");
		else
			for (int i=0; i<list.size(); ++i) {
				if (i!=0)
					result.add(list.get(i));
			}
		
		return result;
	}
	static<T> T reduce(List<T> list, BinaryOperator<T> op) throws Exception {
		if (list.size()==0)
			throw new Exception("Empty List");
		else if (list.size()==1)
			return list.get(0);
		else {
			return op.apply(list.get(0), reduce(tail(list), op));
		}
	}
	static<T,S,R> List<R> zip (List<T> L1, List<S> L2, BinaryFunction<T,S,R> func) throws Exception {
		if (L1.size()==0 && L2.size()==0)
			return new ArrayList<R>();
		else if (L1.size()!=L2.size())
			throw new Exception("Zip with unequal length list");
		else {
			List<R> result = new ArrayList<>();
			result.add(func.apply(L1.get(0), L2.get(0)));
			result.addAll(zip(tail(L1), tail(L2), func ));
			return result;
		}
	}
	
	public static void main(String[] args) {
		List<Integer> intList = new ArrayList<>(Arrays.asList(new Integer[]{3,2,7,6,4}));
		List<Integer> intList1 = new ArrayList<>(Arrays.asList(new Integer[]{2,1,4,7,4}));
		List<String> strList = new ArrayList<>(Arrays.asList(new String[]{"a","b", "c","d","e"}));
		
		System.out.println(intList);
		try {
			System.out.println(tail(intList));
			
			System.out.println(map(intList, (x)->x*x));
			System.out.println(mapForEach(intList, (x)->x*x));
			System.out.println(mapRecursive(intList, (x)->x*x));
			System.out.println(mapRecursive1(intList, (x)->x*x));
			intList = new ArrayList<>(Arrays.asList(new Integer[]{3,2,7,6,4}));
			
			System.out.println(filter(intList, (x)->x%2==0));
			
			System.out.println(reduce(intList, (x,y)->x+y));// sum
			
			System.out.println(zip(intList, intList1, (x, y)->Math.min(x, y)));
			System.out.println(zip(intList, intList1, (x, y)->x+y));
			System.out.println(zip(intList, strList, (x, y)->new ArrayList<>(Arrays.asList(new Object[]{x,y}))));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
