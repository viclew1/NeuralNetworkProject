package creatures.dodgers;

import UI.World;
import captors.Captor;
import captors.EyeCaptor;
import creatures.Creature;
import fr.lewon.Individual;

import java.awt.*;
import java.util.List;

import static utils.Constantes.LAYERS_SIZES_SIMPLEDODGER;
import static utils.Constantes.SIMPLEDODGER;

public class SimpleDodger extends DodgerCreature {

    public SimpleDodger(double x, double y, Individual brain, World world) {
        super(x, y, 1, 1, 1, 0, new Captor[]{
                new EyeCaptor(Math.PI / 4, 10, Math.PI / 4),
                new EyeCaptor(3 * Math.PI / 4, 10, Math.PI / 4),
                new EyeCaptor(Math.PI / 2, 10, Math.PI / 4),
                new EyeCaptor(-Math.PI / 2, 10, Math.PI / 4),
                new EyeCaptor(-Math.PI / 4, 10, Math.PI / 4),
                new EyeCaptor(-3 * Math.PI / 4, 10, Math.PI / 4),
                new EyeCaptor(-Math.PI, 10, Math.PI / 4),
                new EyeCaptor(0, 10, Math.PI / 4),
        }, brain, SIMPLEDODGER, Color.GREEN, LAYERS_SIZES_SIMPLEDODGER[0], world);
    }

    private void move(double intensityX, double intensityY) {
        this.x += this.speed * intensityX;
        this.y += this.speed * intensityY;
    }

    @Override
    protected void applyDecisions(List<Double> decisions) {
        double intensityX = 2 * (0.5 - decisions.get(0));
        double intensityY = 2 * (0.5 - decisions.get(1));
        this.move(intensityX, intensityY);
    }

    @Override
    public void touchedBy(Creature c) {
        // TODO Auto-generated method stub

    }

    @Override
    public void touch(Creature c) {
        switch (c.getType()) {
            case SIMPLEDODGER:
                this.reproduceWith(c);
                break;
            default:
                break;
        }
    }

    @Override
    protected void addSpecialFitness() {

    }
}
