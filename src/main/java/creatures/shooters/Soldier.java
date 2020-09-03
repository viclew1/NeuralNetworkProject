package creatures.shooters;

import static utils.Constantes.FUEL;
import static utils.Constantes.LAYERS_SIZES_SOLDIER;
import static utils.Constantes.POWERUP;
import static utils.Constantes.PROJECTILE;
import static utils.Constantes.SOLDIER;
import static utils.Constantes.TANK;

import java.awt.Color;
import java.util.List;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import fr.lewon.Individual;

public class Soldier extends ShooterCreature
{

	public Soldier(double x, double y, Individual brain, World world)
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
				brain, SOLDIER, Color.CYAN, LAYERS_SIZES_SOLDIER[0],
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
	protected void applyDecisions(List<Double> decisions)
	{
		cdShoot--;
		turn(2*(0.5-decisions.get(0)));
		moveFront(decisions.get(1));
		straff(2*(0.5-decisions.get(2)));
		if (decisions.get(3)>0.5)
			shoot();
	}

	@Override
	public void targetReport(int targetType)
	{
		switch (targetType)
		{
		case SOLDIER:
			break;
		case TANK:
			break;
		default:
			break;
		}
	}

}