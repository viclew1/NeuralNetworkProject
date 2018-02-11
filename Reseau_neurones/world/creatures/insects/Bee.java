package creatures.insects;

import static utils.Constantes.*;

import java.awt.Color;
import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import captors.LineCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;

public class Bee extends InsectCreature
{

	public Bee(double x, double y, Individu brain, Selection selec, World world)
	{
		super(x, y, 1, 400, 1.2, 6, 1,
				new Captor[]{
						new LineCaptor(0, 12),
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI,6,Math.PI/4),
				},
				new int[][] {
					{
						WASP,
					},
					{
						VEGETABLE,
					},
					{
					}},
				brain, selec,	BEE, Color.YELLOW, LAYERS_SIZES_BEE[0],
				world);
	}

	@Override
	public void interactWith(Collectable c)
	{
		if (c.isConsumed())
			return;
		switch (c.getType())
		{
		case VEGETABLE:
			heal(50);
			brain.addScore(1);
			c.consume();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void touchedBy(Creature c)
	{
		switch (c.getType())
		{
		case WASP:
			if (!isInvincible())
				die();
			break;
		default:
			break;
		}
	}

	@Override
	public void touch(Creature c)
	{
		switch (c.getType())
		{
		case BEE:
			reproduceWith(c);
			break;
		default:
			break;
		}
	}

	@Override
	protected void addSpecialFitness()
	{
		
	}
}
