package hufs.ces.stream;

import java.util.function.IntPredicate;

public class EvenTest implements IntPredicate {

	@Override
	public boolean test(int t) {
		return t % 2 == 0;
	}
	
}
