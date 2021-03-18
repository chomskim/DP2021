package headfirst.designpatterns.decorator.starbuzz;

public class Mocha extends CondimentDecorator {
 
	public Mocha(Beverage beverage) {
		super(beverage);
	}
 
	public String getDescription() {
		return super.getDescription() + ", Mocha";
	}
 
	public double cost() {
		return .20 + super.cost();
	}
}
