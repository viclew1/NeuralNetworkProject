package collectables.expirables;

import collectables.Collectable;

public abstract class ExpirableCollectable extends Collectable {

	private int lifetime;
	
	public ExpirableCollectable(double x, double y, double size, int type, int lifetime) {
		super(x, y, size, type);
		this.lifetime=lifetime;
	}

	@Override
	public void update() {
		lifetime--;
		if (lifetime <= 0) {
			consume();
		}
	}
	
}
