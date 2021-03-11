package headfirst.designpatterns.strategy;

public class MiniDuckSimulator {

	public static void main(String[] args) {

		Duck	mallard = new MallardDuck();
		Duck	rubberDuckie = new RubberDuck();
		Duck	decoy = new DecoyDuck();

		Duck	model = new ModelDuck();

		mallard.performQuack();
		rubberDuckie.performQuack();
		decoy.performQuack();

		model.performFly();	
		model.setFlyBehavior(new FlyRocketPowered());
		model.performFly();
	}
}
