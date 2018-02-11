package creatures.shooters;

import static utils.Constantes.*;

import java.awt.Color;
import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;

public class Soldier extends ShooterCreature
{

	public Soldier(double x, double y, Individu brain, Selection selec, World world)
	{
		super(x, y, 1.5, 500, 0.40,0.05,
				0.4,1,60, Integer.MAX_VALUE, 150, Color.RED,
				new Captor[] {
						new EyeCaptor(Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI/7,10,Math.PI/3),
						new EyeCaptor(-Math.PI,5,Math.PI/2),
		},
				new int[][] {
					{
						SOLDIER,
						TANK,
					},
					{
						FUEL,
						POWERUP,
					},
					{
						PROJECTILE,
					}},
				brain, selec, SOLDIER, Color.CYAN, LAYERS_SIZES_SOLDIER[0],
				world);
	}

	@Override
	public void interactWith(Collectable c)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void touchedBy(Creature c)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touch(Creature c)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void applyDecisions(double[] decisions)
	{
		cdShoot--;
		turn(2*(0.5-decisions[0]));
		moveFront(decisions[1]);
		straff(2*(0.5-decisions[2]));
		if (decisions[3]>0.5)
			shoot();
	}

	@Override
	public void targetReport(int targetType)
	{
		switch (targetType)
		{
		case SOLDIER:
			brain.addScore(-1);
			break;
		case TANK:
			brain.addScore(1);
			break;
		default:
			break;
		}
	}

}