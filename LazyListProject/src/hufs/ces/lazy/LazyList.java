package hufs.ces.lazy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import hufs.ces.lisplist.LispList;

public class LazyList<T> implements LispList<T> {

	private Supplier<Cons<T>> list = null;

	private LazyList(Supplier<Cons<T>> list) {
		this.list = list;
	}

	static <T> LazyList<T> nil() {
		return new LazyList<>(()-> new Cons<>());
	};

	@Override
	public LazyList<T> cons(T head) {
		return new LazyList<>(()-> new Cons<>(head, list));
	}

	@Override
	public T car() {
		Cons<T> lst = list.get();
		if (lst!=null && lst.head!=null)
			return lst.head;
		else
			return null;
	}

	@Override
	public LazyList<T> cdr() {
		Cons<T> lst = list.get();
		if (lst!=null && lst.tail!=null)
			return new LazyList<>(lst.tail);
		else
			return nil();
	}

	@Override
	public boolean isEmpty() {
		Cons<T> lst = list.get();
		return lst.isEmpty();
	}

	@Override
	public Object fold(int n, Object acc, BiFunction<Object,T,Object> f) {
		if (n == 0 || isEmpty()) {
			return acc;
		}
		else {
			return cdr().fold(n-1, f.apply(acc, car()), f);
		}
	}

	@Override
	public Object foldAll(Object acc, BiFunction<Object,T,Object> f) {
		if (isEmpty()) {
			return acc;
		}
		else {
			return cdr().foldAll(f.apply(acc, car()), f);
		}
	}

	@Override
	public ArrayList<T> take(int n) {
		return (ArrayList<T>)fold(n, new ArrayList<>(), (acc, item) -> {
			ArrayList<T> res = (ArrayList<T>) acc;
			res.add(item);
			return res;
		});
	}

	@Override
	public ArrayList<T> takeAll() {
		return (ArrayList<T>)foldAll(new ArrayList<>(), (acc, item) -> {
			ArrayList<T> res = (ArrayList<T>) acc;
			res.add(item);
			return res;
		});    
	}

	@Override
	public ArrayList<T> toList() {
		return takeAll();
	}

	@Override
	public String toString() {
		return "LazyList("+toList().toString()+")";
	}

	@Override
	public LazyList<T> map(Function<T,T> f) {
		if (isEmpty())
			return nil();
		else
			return new LazyList<>(()->{
				return new Cons<>(f.apply(car()), cdr().map(f).list);
			});
	}

	@Override
	public LazyList<T> filter(Predicate<T> p) {
		if (isEmpty())
			return nil();
		else {
			if (p.test(car()))
				return new LazyList<>(()->new Cons<>(car(),cdr().filter(p).list));
			else
				return cdr().filter(p);
		}
	}

	public static<T> LazyList<T> toLazyList (List<T> list) {
		if (list.size()==0)
			return nil();
			
		LazyList<T> tail = toLazyList(list.subList(1, list.size()));
		T head = list.get(0);
		return tail.cons(head);
	}
	private static Supplier<Cons<Integer>> sequence(int n) {
		return ()-> new Cons<>(n, sequence(n+1));
	}

	private static<T> Supplier<Cons<T>> sequence(T seed, UnaryOperator<T> f) {
		T next = f.apply(seed);
		return ()-> new Cons<T>(seed, sequence(next, f));
	}
	
	public static LazyList<Integer> integers(int n) {
		return new LazyList<>(sequence(n));
	}

	public static LazyList<Integer> naturals() {
		return integers(1);
	}

	public static<T> LazyList<T> iterate(T seed, UnaryOperator<T> f) {
		return new LazyList<T>(sequence(seed, f));
	}
	public static<T> LazyList<T> zipWith(LazyList<T> alist, LazyList<T> blist, BiFunction<T,T,T> f) {
		if (alist.isEmpty() || blist.isEmpty())
			return nil();
		else {
			return new LazyList<>(()->{
				return new Cons<>(f.apply(alist.car(), blist.car()), zipWith(alist.cdr(), blist.cdr(), f).list);
			});
		}
	}
	public static Random rand = new Random();
	private static  Supplier<Cons<Integer>> randomsSup() {
		return ()-> new Cons<>(rand.nextInt(100), randomsSup());
	}
	public static LazyList<Integer> randoms() {
		return new LazyList<>(randomsSup());
	}

	public static <T> Supplier<Cons<T>> cycleFun(T data) {
		return ()-> new Cons<>(data, cycleFun(data));
	}
	public static <T> LazyList<T> cycle(T data) {
		return new LazyList<>(cycleFun(data));
	}
	private static boolean isPrime(int num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0) return false;
        return true;
	}	
	public static void main(String[] args) {
		System.out.println(LazyList.<Integer>nil().toList());
		System.out.println(LazyList.<Integer>nil());
		System.out.println(LazyList.<Integer>nil().cons(4).cons(3));
		LazyList<Integer> llist = LazyList.<Integer>nil().cons(4).cons(3).cons(2).cons(1);
		System.out.println(llist.list.get());
		System.out.println(llist.take(2));
		System.out.println(llist.toList());
		System.out.println(llist.map(x->x*x));
		llist = integers(1);
		System.out.println(llist.map(x->x*x).take(10));
		System.out.println(llist.filter(x->x%2==0).take(10));
		LazyList<Integer> alist = randoms();
		LazyList<Integer> blist = randoms();
		System.out.println(alist.take(10));
		System.out.println(blist.take(10));
		System.out.println(zipWith(alist, blist, (x, y)->x+y).take(10));

		System.out.println(cycle("hello").take(5));
		
		System.out.println(integers(1).filter(n->isPrime(n)).take(2000));
		
		LazyList<Integer> ilist = toLazyList(new ArrayList<>(Arrays.asList(new Integer[] {1,2,3,4,0,6})));
		System.out.println(ilist);
		System.out.println(ilist.map(x-> 6 / x).take(3)); // Division by zero

		LazyList<Integer> integers = iterate(1, n->n+1);
		System.out.println(integers.map(x->x*x).take(10));
	}
}
