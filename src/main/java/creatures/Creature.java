package creatures;

import UI.World;
import captors.Captor;
import collectables.Collectable;
import fr.lewon.Individual;
import fr.lewon.exceptions.NNException;
import limitations.Delimitation;
import limitations.DelimitationBox;
import limitations.throwables.Projectile;
import zones.Zone;

import java.awt.*;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static utils.Constantes.*;

public abstract class Creature {
    private boolean invincible = true;
    private int invincibleTime = 60;
    private int invincibleTimeLeft = 60;

    protected double x;
    protected final double hpMax;
    protected double hp;
    protected double y;
    protected final double radius;
    private final double range;

    protected double damages = 0;

    protected double orientation = 0;
    protected final double dOrientation;
    protected final double orientationMin = 0;
    protected final double orientationMax = 2 * Math.PI;
    protected double speed;
    protected Individual brain;
    protected Captor[] captors;
    private boolean alive;
    private final int type;
    protected Color color;
    private boolean selected = false;

    protected int nbInput;
    protected double hpLostPerInstant;

    protected double maturity = 0;
    protected int childCount = 0;
    protected final double adultAge = 25;

    protected int ttl = 12000;
    private int timeLived = 0;

    protected World world;
    protected List<Creature> creatures;
    protected List<Collectable> collectables;
    protected List<Delimitation> delimitations;
    protected DelimitationBox box;

    private Line2D attackHitbox;

    public Creature(double x, double y, double radius, double hpMax, double speed, double rotationSpeed, double hpLostPerInstant, Captor[] captors, int[][] thingsToSee, Individual brain, int type, Color color, int nbInput, World world) {
        this.invincibleTimeLeft = this.invincibleTime;
        this.invincible = true;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.range = 1.1 * radius;
        this.attackHitbox = new Line2D.Double(x + radius / 2, y + radius / 2, x + radius / 2 + Math.cos(this.orientation) * this.range, y + radius / 2 - Math.sin(this.orientation) * this.range);
        this.hpMax = hpMax;
        this.hp = hpMax;
        this.speed = speed;
        this.dOrientation = rotationSpeed * Math.PI / 40;
        this.hpLostPerInstant = hpLostPerInstant;
        this.captors = captors;
        this.brain = brain;
        this.type = type;
        this.alive = true;
        this.color = color;
        this.nbInput = nbInput;
        this.world = world;
        this.creatures = world.creatures;
        this.collectables = world.collectables;
        this.delimitations = world.delimitations;
        this.box = world.box;
        this.initCaptors(thingsToSee);
    }

    public void reset(double x, double y, Individual newBrain) {
        this.x = x;
        this.y = y;
        this.brain = newBrain;
        this.maturity = 0;
        this.childCount = 0;
        this.revive();
        this.invincibleTimeLeft = this.invincibleTime;
        this.invincible = true;

    }

    public Line2D getAttackHitbox() {
        return this.attackHitbox;
    }

    public double getDamages() {
        return this.damages;
    }

    public void die() {
        this.alive = false;
        this.hp = 0;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void heal(double hp) {
        this.hp += hp;
        if (this.hp > this.hpMax) {
            this.hp = this.hpMax;
        }
    }

    public void loseHp(double damages) {
        if (!this.isInvincible()) {
            this.hp -= damages;
            if (this.hp <= 0) {
                this.die();
            }
        }
    }

    public void revive() {
        this.alive = true;
        this.hp = this.hpMax;
        this.timeLived = 0;
    }

    public void draw(Graphics g, boolean selected) {
        this.selected = selected;
        if (selected) {
            this.drawScore(g);
        }
        if (selected || DRAW_HP) {
            this.drawHP(g);
        }
        if (selected || DRAW_CAPTORS) {
            this.drawCaptors(g);
        }
        this.drawCreature(g);
    }

    private void drawScore(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(Color.BLACK);
        g.drawString("Score cr�ature : " + new DecimalFormat("##.##").format(this.brain.getFitness()), 10, 150);
        g.drawString("Maturit� : " + new DecimalFormat("##.##").format(this.maturity), 10, 170);
        g.drawString("Compte d'enfants : " + this.childCount, 10, 190);
        g.drawString("�ge : " + this.timeLived, 10, 210);
        g.setColor(oldColor);
    }

    private void drawHP(Graphics g) {
        Color oldColor = g.getColor();
        int xF = this.xFinal();
        int yF = (int) ((this.y - this.radius / 2) * SIZE + SCROLL_Y);
        int szF = this.sizeFinal();
        int hpSzF = (int) (szF * this.hp / this.hpMax);
        int hF = (int) ((this.radius / 5) * SIZE);

        g.setColor(Color.GREEN);
        g.fillRect(xF, yF, hpSzF, hF);
        g.setColor(Color.BLACK);
        g.drawRect(xF, yF, szF, hF);
        g.setColor(oldColor);

    }

    private void drawCreature(Graphics g) {
        Color oldColor = g.getColor();
        if (!this.selected) {
            g.setColor(this.color);
        } else {
            g.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 127));
        }

        g.drawLine(
                (int) (this.attackHitbox.getX1() * SIZE + SCROLL_X),
                (int) (this.attackHitbox.getY1() * SIZE + SCROLL_Y),
                (int) (this.attackHitbox.getX2() * SIZE + SCROLL_X),
                (int) (this.attackHitbox.getY2() * SIZE + SCROLL_Y));
        g.fillOval(this.xFinal(), this.yFinal(), this.sizeFinal(), this.sizeFinal());
        g.setColor(Color.BLACK);
        g.drawOval(this.xFinal(), this.yFinal(), this.sizeFinal(), this.sizeFinal());
        g.setColor(oldColor);
    }

    public void drawBrain(Graphics g) {
    }

    private void drawCaptors(Graphics g) {
        for (Captor c : this.captors) {
            c.draw(g);
        }
    }

    public void update() {
        this.loseHp(this.hpLostPerInstant);

        this.updateInvincibility();
        this.detect();
        this.updatePosition();
        this.updateCaptors();

        this.addSpecialFitness();

        this.brain.setFitness(this.brain.getFitness() + 0.01 * (this.childCount + 1));
        this.maturity += 0.01;
        this.timeLived += 0.01;
        if (this.timeLived >= this.ttl) {
            this.die();
        }
    }

    protected abstract void addSpecialFitness();

    private void updateInvincibility() {
        if (this.invincibleTimeLeft > 0) {
            this.invincibleTimeLeft--;
            if (this.invincibleTimeLeft <= 0) {
                this.invincible = false;
            }
        }
    }

    private void initCaptors(int[][] thingsToSee) {
        for (Captor c : this.captors) {
            c.setCreature(this);
            c.setThingsToSee(thingsToSee);
            c.update(this.x + this.radius / 2, this.y + this.radius / 2, this.orientation);
        }
    }

    public void updateCaptors() {
        for (Captor c : this.captors) {
            c.update(this.x + this.radius / 2, this.y + this.radius / 2, this.orientation);
        }
    }

    public void detect() {
        for (Captor c : this.captors) {
            c.detect(this.creatures, this.collectables, this.delimitations, this.box);
        }
    }

    private void updatePosition() {
        //System.out.println(CreatureFactory.getTypeStrFromTypeInt(getType()) + " " + (captors[0].getResultCount() * captors.length + 7));
        double[] inputs = new double[this.nbInput];
        int cpt = 0;
        for (int i = 0; i < this.captors.length; i++) {
            double[] results = this.captors[i].getResults();
            for (int j = 0; j < results.length; j++) {
                inputs[cpt++] = results[j];
            }
        }
        double xCenter = this.box.width / 2;
        double yCenter = this.box.height / 2;
        double xRatio = this.x - xCenter;
        double yRatio = this.y - yCenter;
        inputs[cpt++] = (xRatio > 0) ? xRatio / xCenter : 0;
        inputs[cpt++] = (xRatio > 0) ? 0 : -xRatio / xCenter;
        inputs[cpt++] = (yRatio > 0) ? yRatio / yCenter : 0;
        inputs[cpt++] = (yRatio > 0) ? 0 : -yRatio / yCenter;
        this.addParticularInput(inputs, cpt);
        List<Double> inputsList = new ArrayList<>();
        for (Double d : inputs) {
            inputsList.add(d);
        }
        List<Double> decisions;
        try {
            decisions = this.brain.getOutputs(inputsList);
            this.applyDecisions(decisions);
        } catch (NNException e) {
            e.printStackTrace();
        }

        this.attackHitbox.setLine(
                this.x + this.radius / 2,
                this.y + this.radius / 2,
                this.x + this.radius / 2 + Math.cos(this.orientation) * this.range,
                this.y + this.radius / 2 - Math.sin(this.orientation) * this.range);
    }

    protected abstract void addParticularInput(double[] inputs, int currentCount);

    protected abstract void applyDecisions(List<Double> decisions);

    protected void moveFront(double intensity) {
        this.x += Math.cos(this.orientation) * this.speed * intensity;
        this.y -= Math.sin(this.orientation) * this.speed * intensity;
    }

    protected void turn(double intensity) {
        this.orientation += this.dOrientation * intensity;
        if (this.orientation > this.orientationMax) {
            this.orientation -= Math.PI * 2;
        }
        if (this.orientation < this.orientationMin) {
            this.orientation += Math.PI * 2;
        }
    }

    protected void straff(double intensity) {
        this.x -= Math.cos(this.orientation + Math.PI / 2) * this.speed * intensity;
        this.y += Math.sin(this.orientation + Math.PI / 2) * this.speed * intensity;
    }

    /**
     * Interactions
     */

    public abstract void interactWith(Collectable c);

    public abstract void touchedBy(Creature c);

    public abstract void touch(Creature c);

    public abstract void interactWith(Zone z);

    public void interactWith(Delimitation d) {
        switch (d.getType()) {
            case PROJECTILE:
                if (((Projectile) d).getSender() == this) {
                    return;
                }
                break;
            case FIREBALL:
                break;
            default:
                System.out.println("Creature.interactWith(Delimitation d) - Delimitation inconnue : " + d.getType());
                System.exit(0);
                break;
        }
        this.loseHp(d.getDamages());
    }


    /**
     * Reproduction
     */

    public void reproduceWith(Creature c) {
		/*if (canReproduce() && c.canReproduce() && c.getType() == type)
		{
			maturity = 0;
			c.maturity = 0;
			if (team != null)
			{
				team.add(CreatureFactory.generate(CreatureFactory.getTypeStrFromTypeInt(type), selec.getOffspring(this.getBrain(),c.getBrain()), selec, world));
			}
			else
			{
				creatures.add(CreatureFactory.generate(CreatureFactory.getTypeStrFromTypeInt(type), selec.getOffspring(this.getBrain(),c.getBrain()), selec, world));
			}
			childCount ++;
			c.childCount ++;
		}*/
    }

    public boolean canReproduce() {
        return this.maturity >= this.adultAge;
    }


    /**
     * CALCULS DE LA POSITION SUR L'ECRAN
     */

    protected int xFinal() {
        return (int) (this.x * SIZE + SCROLL_X);
    }

    protected int yFinal() {
        return (int) (this.y * SIZE + SCROLL_Y);
    }

    protected int sizeFinal() {
        return (int) (this.radius * SIZE);
    }

    /**
     * GETTERS
     */

    public int getType() {
        return this.type;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getSize() {
        return this.radius;
    }

    public Individual getBrain() {
        return this.brain;
    }

    public double getHp() {
        return this.hp;
    }

    public boolean isInvincible() {
        return this.invincible;
    }

}
