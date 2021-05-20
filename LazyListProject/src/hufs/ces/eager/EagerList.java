package hufs.ces.eager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import hufs.ces.lazy.LazyList;
import hufs.ces.lisplist.LispList;

public class EagerList<T> implements LispList<T> {

	private Cons<T> list = null;

	private EagerList(Cons<T> list) {
		this.list = list;
	}

	static <T> EagerList<T> nil() {
		return new EagerList<>(new Cons<>());
	};
	
	@Override
    public EagerList<T> cons(T head) {
        return new EagerList<>(new Cons<>(head, list));
    }

	@Override
    public T car() {
    	if (list!=null && list.head!=null)
    		return list.head;
    	else
    		return null;
    }

	@Override
    public EagerList<T> cdr() {
    	if (list!=null && list.tail!=null)
    		return new EagerList<>(list.tail);
    	else
    		return nil();
    }

	@Override
    public boolean isEmpty() {
        return list.isEmpty();
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
        });    }
    
	@Override
    public ArrayList<T> toList() {
        return takeAll();
    }

    @Override
    public String toString() {
    	return "EagerList("+toList().toString()+")";
    }

	@Override
    public EagerList<T> map(Function<T,T> f) {
    	if (isEmpty())
    		return nil();
    	else
    		return new EagerList<>(new Cons<>(f.apply(car()), cdr().map(f).list));
    }

	@Override
    public EagerList<T> filter(Predicate<T> p) {
    	if (isEmpty())
    		return nil();
    	else {
    		if (p.test(car()))
    			return new EagerList<T>(new Cons<>(car(),cdr().filter(p).list));
    		else
    			return cdr().filter(p);
    	}
    }

	public static<T> EagerList<T> toEagerList (List<T> list) {
		if (list.size()==0)
			return nil();
			
		EagerList<T> tail = toEagerList(list.subList(1, list.size()));
		T head = list.get(0);
		return tail.cons(head);
	}
	
    private static Cons<Integer> sequence(int n, int m) {
    	if (m==0)
    		return new Cons<>();
    	else
    		return new Cons<>(n, sequence(n+1,m-1));
    }
    
    public static EagerList<Integer> integers(int n, int m) {
        return new EagerList<>(sequence(n,m));
    }

    public static EagerList<Integer> naturals(int m) {
        return integers(1,m);
    }
    
    static<T> EagerList<T> zipWith(EagerList<T> alist, EagerList<T> blist, BiFunction<T,T,T> f) {
    	if (alist.isEmpty() || blist.isEmpty())
    		return nil();
    	else {
    		return new EagerList<>(
    			new Cons<>(f.apply(alist.car(), blist.car()), zipWith(alist.cdr(), blist.cdr(), f).list));
    	}
     }
	public static Random rand = new Random();
	private static  Cons<Integer> randomsSup(int m) {
		if (m==0)
			return new Cons<>();
		return new Cons<>(rand.nextInt(100), randomsSup(m-1));
	}
	public static EagerList<Integer> randoms(int m) {
		return new EagerList<>(randomsSup(m));
	}
	
    public static void main(String[] args) {
    	System.out.println(EagerList.<Integer>nil().toList());
    	System.out.println(EagerList.<Integer>nil());
    	System.out.println(EagerList.<Integer>nil().cons(4).cons(3));
    	EagerList<Integer> llist = EagerList.<Integer>nil().cons(4).cons(3).cons(2).cons(1);
    	System.out.println(llist.list);
    	System.out.println(llist.take(2));
    	System.out.println(llist.toList());
    	System.out.println(llist.map(x->x*x));
    	llist = integers(1,11);
    	System.out.println(llist);
    	System.out.println(llist.list);
    	System.out.println(llist.map(x->x*x).take(10));
    	System.out.println(llist.filter(x->x%2==0).take(10));
    	EagerList<Integer> alist = randoms(100);
    	EagerList<Integer> blist = randoms(100);
    	System.out.println(alist.take(10));
    	System.out.println(blist.take(10));
    	System.out.println(zipWith(alist, blist, (x, y)->x+y).take(10));
    	
    	EagerList<Integer> ilist = toEagerList(new ArrayList<>(Arrays.asList(new Integer[] {1,2,3,4,0,6})));
		System.out.println(ilist);
		try {
			System.out.println(ilist.map(x-> 6 / x).take(3)); // Division by zero
		} catch(Exception e) {
			System.err.println(e);
		}
    	
    }

}
