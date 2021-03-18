package headfirst.designpatterns.decorator.starbuzz;

public class Soy extends CondimentDecorator {

	public Soy(Beverage beverage) {
		super(beverage);
	}

	public String getDescription() {
		return super.getDescription() + ", Soy";
	}

	public double cost() {
		return .15 + super.cost();
	}
}
