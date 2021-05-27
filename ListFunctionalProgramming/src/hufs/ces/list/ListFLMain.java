package hufs.ces.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	static<T,S> S reduce(List<T> list, BinaryFunction<T,S,S> op, S init) throws Exception {
		if (list.size()==0)
			return init;
		else {
			return op.apply(list.get(0), reduce(tail(list), op, init));
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

	static void testMin1(List<Integer> intList) {
		try {
			System.out.println(Collections.min(intList));
			Collections.sort(intList); System.out.println(intList.get(0));
			System.out.println(reduce(intList, (m,x)->Math.min(m,x)));
			intList.sort((x,y)->x-y); System.out.println(intList.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static int sum;
	static void testSum1(List<Integer> intList) {		
		try {
			System.out.println(reduce(intList, (x,y)->x+y));
			System.out.println(reduce(intList, (x,y)->x+y,0));
			sum=0; intList.forEach(x->{sum+=x;});System.out.println(sum);
			sum=0; for(int x:intList){sum+=x;};System.out.println(sum);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static List<Integer> even;
	static void testEven1(List<Integer> intList) {		
		try {
			System.out.println(filter(intList, x->x%2==0));
			even = new ArrayList<>(); intList.forEach(x->{if(x%2==0)even.add(x);});System.out.println(even);
			even = new ArrayList<>(); for(int x:intList){if(x%2==0)even.add(x);};System.out.println(even);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	///*
	//static List<Integer> flat;
	static void testFlat1(List<List<Integer>> nestedList) {		
		try {
			System.out.println(reduce(nestedList,(li, x)->{
				li.addAll(x); 
				return (ArrayList<Integer>) li;
			},new ArrayList<Integer>()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//*/
	static List<Integer> top3;
	static void testTop3(List<Integer> intList) {		
		try {
			intList.sort((x,y)->x-y);top3=intList.subList(0, 3);System.out.println(top3);
			top3 = new ArrayList<>(); intList.forEach(x->{if(top3.size()<3)top3.add(x);});System.out.println(top3);
			top3 = new ArrayList<>(); for(int x:intList){if(top3.size()<3)top3.add(x);};System.out.println(top3);			
			top3 = new ArrayList<>();intList.sort((x,y)->x-y);top3.add(intList.remove(0));top3.add(intList.remove(0));top3.add(intList.remove(0));System.out.println(top3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static void testStrait(List<Integer> intList) {		
		List<Integer> L1 = intList.subList(0,intList.size()-1);
		List<Integer> L2 = intList.subList(1,intList.size());
		System.out.println(L1);
		System.out.println(L2);
		try {
			System.out.println(reduce(zip(L1,L2,(x,y)->x+1==y), (p,c)->p&&c));
			System.out.println(reduce(map(zip(L1,L2,(x,y)->new Integer[]{x,y}), a->a[0]+1==a[1]), (p,c)->p&&c));
			boolean sr=true; for(boolean x:zip(L1,L2,(x,y)->x+1==y)){sr=sr&&x;};System.out.println(sr);			
			System.out.println(map(zip(L1,L2,(x,y)->x+1==y), b->b?1:0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		List<Integer> intList = new ArrayList<>(Arrays.asList(3,2,7,6,4));
		List<Integer> intList1 = new ArrayList<>(Arrays.asList(new Integer[]{2,1,4,7,4}));
		List<String> strList = new ArrayList<>(Arrays.asList(new String[]{"a","b", "c","d","e"}));

		System.out.println(intList);
		testMin1(intList);
		testSum1(intList);
		testEven1(intList);
		List<List<Integer>> nestedList = new ArrayList<>(Arrays.asList(
				new ArrayList<>(Arrays.asList(3,2)),
				new ArrayList<>(Arrays.asList(7)),
				new ArrayList<>(Arrays.asList(6,4))
				));
		System.out.println(nestedList);
		testFlat1(nestedList);
		intList = new ArrayList<>(Arrays.asList(new Integer[]{3,2,7,6,4}));
		testTop3(intList);
		intList = new ArrayList<>(Arrays.asList(2,3,4,5,6)); 
		testStrait(intList);
		try {
			System.out.println(tail(intList));

			System.out.println(map(intList, (x)->x*x));
			System.out.println(map(intList, (x)->x%2 == 0));
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
