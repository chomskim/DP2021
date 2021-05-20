package hufs.ces.lazy;

import java.util.function.Supplier;

public class Cons<T> {

	public T head = null;
	public Supplier<Cons<T>> tail = null;
	
	public Cons() {
		this.head = null;
		this.tail = null;
	}
	public Cons(T head, Supplier<Cons<T>> tail) {
		this.head = head;
		this.tail = tail;
	}
	public boolean isEmpty() {
		return head == null && tail == null;
	}
	@Override
	public String toString() {
		if (!isEmpty())
			return "Cons("+head+","+tail.get().toString()+")";
		else
			return "nil";
	}
}
