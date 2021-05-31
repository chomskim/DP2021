package hufs.ces.stream;

import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamSum {

	static int genNum = 0;
	
	public static void main(String[] args) {
		IntStream integer = IntStream.iterate(0, n->n+1);
		integer.limit(10).forEach(x->{System.out.println(x);});
		//for(int i : int10) {
		//	System.out.println(i);
		//}
		integer = IntStream.iterate(1, n->n+1);
		int sum =integer.filter(x->x%2==0).limit(10).sum();
		System.out.println(sum);

		Stream.of(3,2,7,6,8,15,4,20,1).sorted((x,y) -> y-x).forEach(x->{System.out.print(x+" ");});

		System.out.println();
		// Using Class instance Before Java 1.8
		EvenTest e = new EvenTest();
		sum =IntStream.iterate(1, n->n+1).filter(e).limit(10).sum();
		System.out.println(sum);

		// Using Anonymous Class Instance Before Java 1.8
		sum =IntStream.iterate(1, n->n+1).filter(
				new IntPredicate() {
					public boolean test(int t) {
						return t % 2 == 0;
					}
				}).limit(10).sum();
		System.out.println(sum);

		// Java 1.8 Passing Method Name
		sum =IntStream.iterate(1, n->n+1).filter(PredicateFunctions::isEven).limit(10).sum();
		System.out.println(sum);

		sum =IntStream.iterate(1, n->n+1).filter(
				new IntPredicate() {
					public boolean test(int t) {
						return PredicateFunctions.isEven(t);
					}
				}).limit(10).sum();
		System.out.println(sum);

		sum =IntStream.iterate(1, n->n+1).filter(PredicateFunctions::isOdd).limit(10).sum();
		System.out.println(sum);

		sum =IntStream.iterate(1, n->n+1).filter(x->x%2==0).limit(10).sum();
		System.out.println(sum);

		sum = IntStream.generate(()->1).limit(10).sum();
		System.out.println(sum); // 10

		sum = IntStream.generate(()->genNum++).limit(10).sum();
		System.out.println(sum); // 45
		
		Supplier<Long> figGen = new FibSupplier();
		Stream.generate(figGen).limit(10).forEach(n->{
			System.out.println(n);
		});

		Stream.iterate(new int[]{0, 1},
				t -> new int[]{t[1],t[0] + t[1]})
		.limit(10)
		.map(t -> t[0])
		.forEach(System.out::println);
	}

}
