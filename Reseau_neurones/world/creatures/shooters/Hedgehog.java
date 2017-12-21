package creatures.shooters;

import java.awt.Color;
import java.util.List;

import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import limitations.Delimitation;
import limitations.DelimitationBox;

import static utils.Constantes.*;

public class Hedgehog extends ShooterCreature{

	static Color brown = new Color(156, 93, 82);
	
	public Hedgehog(double x, double y, Individu brain, Selection selec, 
			List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box) {
		super(x, y, 3, 300, 0.7, 0.5, 0.5, 1, 60, 80, brown,
				new Captor[]{
						new EyeCaptor(Math.PI/7,18,Math.PI/3),
						new EyeCaptor(-Math.PI/7,18,Math.PI/3),
						new EyeCaptor(-Math.PI,10,Math.PI/4),
				},
				new int[] {SLUG, BOMB, HEDGEHOG, WALL, VEGETABLE}, 
				brain, selec, HEDGEHOG, brown, 
				LAYERS_SIZES_HEDGEHOG[0],
				creatures, collectables, delimitations, box);
	}

	@Override
	public void interactWith(Collectable c) {
		switch (c.getType())
		{
		case BOMB:
			//brain.addScore(-brain.getScore()*3/4);
			alive=false;
			break;
		case VEGETABLE:
			brain.addScore(200);
			hp+=50;
			if (hp>hpMax)
				hp=hpMax;
			c.consume();
			break;
			
		default:
			break;
		}
	}

	@Override
	public void interactWith(Creature c) {
		switch (c.getType())
		{
		case SLUG:
			//brain.addScore(-brain.getScore()*3/4);
			alive=false;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void targetReport(int targetType) {
		switch (targetType)
		{
		case SLUG:
			brain.addScore(5);
			break;
		case BOMB:
			brain.addScore(5);
			break;
		default:
			break;
		}
	}

	@Override
	protected void updateScore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void applySeenFitness(List<Integer> seenThings) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void applyDecisions(double[] decisions) {
		cdShoot--;
		turn(2*(0.5-decisions[0]));
		moveFront(2*(0.5-decisions[1]));
		straff(2*(0.5-decisions[2]));
		if (decisions[3]>0.5)
			shoot();		
	}
}
