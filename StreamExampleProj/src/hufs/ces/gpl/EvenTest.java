package hufs.ces.gpl;

import java.util.function.IntPredicate;

public class EvenTest implements IntPredicate {

	@Override
	public boolean test(int t) {
		return t % 2 == 0;
	}
	
}
