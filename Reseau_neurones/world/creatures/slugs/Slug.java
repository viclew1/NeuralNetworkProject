package creatures.slugs;

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
import zones.Zone;

import static utils.Constantes.*;

public class Slug extends SlugsCreature {

	public Slug(double x, double y, Captor[] captors, int[] thingsToSee, Individu brain, Selection selec, int nbInput, 
			List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box) {
		super(x, y, 2, 500, 0.3, 3, 1, 
				new Captor[]{
						new EyeCaptor(Math.PI/7,8,Math.PI/3),
						new EyeCaptor(-Math.PI/7,8,Math.PI/3),
						new EyeCaptor(-Math.PI,6,Math.PI/4),
				}, 
				new int[] {
						BOMB,
						HEDGEHOG
				},
				brain, selec, SLUG, Color.GREEN, 
				LAYERS_SIZES_SLUG[0], //Définir la tete du résal de neurones dans les constantes
				creatures, collectables, delimitations, box);
	}

	@Override
	public void interactWith(Collectable c) {
		if (c.isConsumed()) {
			return;
		}
		switch (c.getType())
		{
		case BOMB:
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public void interactWith(Creature c) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void interactWith(Zone z) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void updateScore() {
		
	}

	@Override
	protected void applySeenFitness(List<Integer> seenThings) {
		
	}

	@Override
	protected void applyDecisions(double[] decisions) {
		
	}
	
}
