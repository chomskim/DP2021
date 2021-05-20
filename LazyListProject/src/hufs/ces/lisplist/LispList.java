package hufs.ces.lisplist;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface LispList<T> {

	public LispList<T> cons(T head);
	public T car();
	public LispList<T> cdr();
	
	public boolean isEmpty();
	public Object fold(int n, Object acc, BiFunction<Object,T,Object> f);

	public Object foldAll(Object acc, BiFunction<Object,T,Object> f);
	public ArrayList<T> take(int n);

	public ArrayList<T> takeAll();

	public ArrayList<T> toList();

	public LispList<T> map(Function<T,T> f);

	public LispList<T> filter(Predicate<T> p);

}
