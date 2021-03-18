package headfirst.designpatterns.decorator.starbuzz;
 
public class Whip extends CondimentDecorator {
 
	public Whip(Beverage beverage) {
		super(beverage);
	}
 
	public String getDescription() {
		return super.getDescription() + ", Whip";
	}
 
	public double cost() {
		return .10 + super.cost();
	}
}
