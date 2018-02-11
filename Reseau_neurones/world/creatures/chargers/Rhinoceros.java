package creatures.chargers;

import static utils.Constantes.*;

import java.awt.Color;
import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import limitations.Delimitation;
import limitations.throwables.Projectile;

public class Rhinoceros extends ChargerCreature {

	public Rhinoceros(double x, double y,
			Individu brain, Selection selec, World world) {
		super(x, y, 3, 500, 0.5, 1, 
				new Captor[]{
						new EyeCaptor(Math.PI/7,30,Math.PI/3),
						new EyeCaptor(-Math.PI/7,30,Math.PI/3),
						new EyeCaptor(-Math.PI,10,Math.PI/4),
				},
				new int[][] {
					{
						HEDGEHOG,
						SLUG,
						RHINOCEROS,
						DRAGON,
					},
					{
						BOMB,
						VEGETABLE,
					},
					{
						PROJECTILE,
					}},
				brain, selec, RHINOCEROS, Color.GRAY, 
				LAYERS_SIZES_RHINOCEROS[0], 
				world);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void applyDecisions(double[] decisions) {
		chargeCooldown--;
		if(isCharging) {
			chargeTimeLeft--;
		}
		if(chargeTimeLeft<= 0) {
			isCharging = false;
			speed = 0.5;
			chargeTimeLeft=50;
			chargeCooldown=450;
			this.color=Color.GRAY;
		}
		if(!isCharging) {
			turn(2*(0.5-decisions[0]));
		}
		if(!isCharging) {
			moveFront(2*(0.5-decisions[1]));
		}
		if(isCharging) {
			moveFront(0.6+(0.4*decisions[1]));
		}
		if (decisions[2]>0.5)
			charge();
	}
	
	@Override
	public void interactWith(Collectable c) {
		switch (c.getType())
		{
		case BOMB:
			brain.addScore(50);
			c.consume();
		case VEGETABLE:
			brain.addScore(50);
			hp+=20;
			if (hp>hpMax)
				hp=hpMax;
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
		case DRAGON:
			if (!this.isInvincible()) {
				die();
			}
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
		case SLUG:
			if (!c.isInvincible()) {
				hp+=50;
				brain.addScore(10);
			}
			break;
		case HEDGEHOG:
			if (!c.isInvincible()) {
				hp+=80;
				brain.addScore(10);
			}
			break;
		case RHINOCEROS:
			reproduceWith(c);
			break;
		default:
			break;
		}
	}

	public void interactWith(Delimitation d)
	{
		switch (d.getType())
		{
		case PROJECTILE:
			if (((Projectile)d).getSender()==this)
				return;
			if (((Projectile)d).getSender().getType()==HEDGEHOG) {
				hp-=50;
				return;
			}
			break;
		case FIREBALL:
			break;
		default:
			System.out.println("Creature.interactWith(Delimitation d) - Delimitation inconnue : "+d.getType());
			System.exit(0);
			break;
		}
		loseHp(d.getDamages());
		if (hp<=0)
			die();
	}

	@Override
	protected void addSpecialFitness()
	{
		// TODO Auto-generated method stub
		
	}
}
