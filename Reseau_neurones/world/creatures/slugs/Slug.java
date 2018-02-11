package creatures.slugs;

import java.awt.Color;
import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import collectables.Collectable;
import collectables.expirables.Bomb;
import creatures.Creature;
import genetics.Individu;
import genetics.Selection;
import zones.Zone;

import static utils.Constantes.*;

public class Slug extends SlugsCreature {

	public Slug(double x, double y, Individu brain, Selection selec, World world) 
	{
		super(x, y, 2, 500, 0.3, 3, 1, 
				new Captor[]{
						new EyeCaptor(Math.PI/7,8,Math.PI/3),
						new EyeCaptor(-Math.PI/7,8,Math.PI/3),
						new EyeCaptor(-Math.PI,6,Math.PI/4),
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
				brain, selec, SLUG, Color.ORANGE, 
				LAYERS_SIZES_SLUG[0],
				world);
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

	protected void moveFront(double intensity)
	{
		x+=Math.cos(orientation)*speed*intensity;
		y-=Math.sin(orientation)*speed*intensity;

		bombCooldown--;
		if(bombCooldown<=0) {
			bombCooldown = 20;
			collectables.add(new Bomb(x,y,this));
		}
	}

	public void addScore(double i) {
		brain.addScore(i);
	}

	@Override
	public void touchedBy(Creature c)
	{
		switch (c.getType())
		{
		case HEDGEHOG:
			if (!this.isInvincible())
				die();
			break;
		case RHINOCEROS:
			if (!this.isInvincible())
				die();
			break;
		case DRAGON:
			if (!this.isInvincible())
				die();
			break;
		case SLUG:
			break;
		default:
			break;
		}
	}

	@Override
	public void touch(Creature c)
	{

	}


	@Override
	public void interactWith(Zone z) {

	}
}
