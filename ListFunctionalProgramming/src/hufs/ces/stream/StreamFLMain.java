package hufs.ces.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.util.Pair;

@FunctionalInterface
interface BinaryFunction<T,S,R> {
	R apply(T a, S b);
}

public class StreamFLMain {


	static<T,R> List<R> map (List<T> list, Function<T,R> func) {
		List<R> result = list.stream()
				.map(func)
				.collect(Collectors.toList());
				
		return result;
	}
	static<T> List<T> filter(List<T> list, Predicate<T> pred) {
		List<T> result = list.stream()
				.filter(pred)
				.collect(Collectors.toList());
		
		return result;
	}
	static<T> List<T> tail (List<T> list) throws Exception {
		List<T> result = list.stream()
				.skip(1)
				.collect(Collectors.toList());

		return result;
	}
	static<T> T reduce(List<T> list, BinaryOperator<T> op) throws Exception {
		Optional<T> opt = list.stream()
				.reduce(op);
		
		if (opt.isPresent()) return opt.get();
		
		return null;
	}
	static<T,S,R> List<R> zip (List<T> L1, List<S> L2, BinaryFunction<T,S,R> func) throws Exception {
		
		List<R> result = IntStream.range(0, Math.min(L1.size(), L2.size()))
				.mapToObj(i -> func.apply(L1.get(i), L2.get(i)))
				.collect(Collectors.toList());	
		
		return result;
	}

	public static void main(String[] args) {
		List<Integer> intList = new ArrayList<>(Arrays.asList(new Integer[]{3,2,7,6,4}));
		List<Integer> intList1 = new ArrayList<>(Arrays.asList(new Integer[]{10,8,2,1,4,7,4,5}));
		List<String> strList = new ArrayList<>(Arrays.asList(new String[]{"a","b", "c","d","e"}));
		
		System.out.println(intList);
		try {
			System.out.println(tail(intList));
			
			System.out.println(map(intList, (x)->x*x));
			
			System.out.println(filter(intList, (x)->x%2==0));
			
			System.out.println(reduce(intList, (x,y)->x+y));// sum
			
			System.out.println(zip(intList, intList1, (x, y)->Math.min(x, y)));
			System.out.println(zip(intList, intList1, (x, y)->x+y));
			System.out.println(zip(intList, strList, (x, y)->new ArrayList<>(Arrays.asList(new Object[]{x,y}))));
			
			List<Integer> res = intList1.stream().filter((x)->x%2==0).map((x)->x*x).sorted().collect(Collectors.toList());
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
