package headfirst.designpatterns.decorator.starbuzz;

public class CondimentDecorator extends Beverage {
	
	private Beverage beverage;
	
	public CondimentDecorator(Beverage beverage) {
		this.beverage = beverage;
	}
	public String getDescription() {
		
		return beverage.getDescription();
	}

	@Override
	public double cost() {
		return beverage.cost();
	}
}
