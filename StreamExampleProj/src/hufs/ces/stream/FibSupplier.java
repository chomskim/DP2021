package hufs.ces.stream;

import java.util.function.Supplier;

public class FibSupplier implements Supplier<Long> {

	private long num1 = 0;
	private long num2 = 1;	

	public FibSupplier(){}

	@Override
	public Long get() {
		long retValue = num1;
		long nextValue = num1 + num2;
		num1 = num2;
		num2 = nextValue;
		return retValue;
	}
	public static void main(String[] args) {

		FibSupplier fib = new FibSupplier();

		for (int i=0; i<10; i++) {
			System.out.println(fib.get());
		}

	}
}
