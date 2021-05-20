package hufs.ces.eager;

public class Cons<T> {

	public T head = null;
	public Cons<T> tail = null;
	
	public Cons() {
		this.head = null;
		this.tail = null;
	}
	public Cons(T head, Cons<T> tail) {
		this.head = head;
		this.tail = tail;
	}
	public boolean isEmpty() {
		return head == null && tail == null;
	}
	@Override
	public String toString() {
		if (!isEmpty())
			return "Cons("+head+","+tail.toString()+")";
		else
			return "nil";
	}
}
