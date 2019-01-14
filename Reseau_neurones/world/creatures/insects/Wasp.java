package creatures.insects;

import static utils.Constantes.BEE;
import static utils.Constantes.LAYERS_SIZES_WASP;
import static utils.Constantes.MEAT;
import static utils.Constantes.WASP;

import java.awt.Color;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import captors.LineCaptor;
import collectables.Collectable;
import creatures.Creature;
import fr.lewon.Individual;

public class Wasp extends InsectCreature
{

	public Wasp(double x, double y, Individual brain, World world)
	{
		super(x, y, 2, 400, 0.9, 2, 2,
				new Captor[]{
						new LineCaptor(0, 17),
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
		},
				new int[][] {
			{
				WASP,
				BEE,
			},
			{
			},
			{
			}},
				brain, WASP, Color.ORANGE, LAYERS_SIZES_WASP[0],
				world);
	}

	@Override
	public void interactWith(Collectable c)
	{
		if (c.isConsumed())
			return;
		switch (c.getType())
		{
		case MEAT:
			heal(25);
			c.consume();
			break;
		default:
			break;
		}
	}

	@Override
	public void touchedBy(Creature c)
	{
		// TODO Auto-generated method stub

	}

	private void eat()
	{
	}

	@Override
	public void touch(Creature c)
	{
		switch (c.getType())
		{
		case WASP:
			reproduceWith(c);
			break;
		case BEE:
			if (!c.isInvincible())
				eat();
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