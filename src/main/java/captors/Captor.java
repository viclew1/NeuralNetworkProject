package captors;

import collectables.Collectable;
import creatures.Creature;
import limitations.Delimitation;
import limitations.DelimitationBox;
import utils.DistanceChecker;
import utils.IntersectionsChecker;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public abstract class Captor {
    protected final double range, orientation;
    protected Creature creature;

    private double[] ponderatedResults;
    protected double wallResult;

    protected Rectangle2D around;
    protected Shape hitbox;
    private int[][] thingsToSee;
    private int resultCount;


    public Captor(double orientation, double range) {
        this.orientation = orientation;
        this.range = range;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    public abstract void update(double x, double y, double deltaOrientation);

    public abstract void draw(Graphics g);

    public void detect(List<Creature> creatures, List<Collectable> collectables, List<Delimitation> delimitations, DelimitationBox box) {
        int cpt = 0;
        this.ponderatedResults = new double[this.resultCount];

        if (this.thingsToSee[0].length > 0) {
            Result<Creature>[] closestCreaResult = this.detectCreatures(creatures);

            for (Result<Creature> r : closestCreaResult) {
                double resultDist = 1 - r.getValue() / this.range;
                if (resultDist > 1) {
                    resultDist = 1;
                }
                if (resultDist < 0) {
                    resultDist = 0;
                }
                this.ponderatedResults[cpt++] = resultDist;
            }
        }

        if (this.thingsToSee[1].length > 0) {
            Result<Collectable>[] closestCollecResult = this.detectCollectables(collectables);

            for (Result<Collectable> r : closestCollecResult) {
                double resultDist = 1 - r.getValue() / this.range;
                if (resultDist > 1) {
                    resultDist = 1;
                }
                if (resultDist < 0) {
                    resultDist = 0;
                }
                this.ponderatedResults[cpt++] = resultDist;
            }
        }


        if (this.thingsToSee[2].length > 0) {
            Result<Delimitation>[] closestDelimResult = this.detectDelimitations(delimitations);

            for (Result<Delimitation> r : closestDelimResult) {
                double resultDist = 1 - r.getValue() / this.range;
                if (resultDist > 1) {
                    resultDist = 1;
                }
                if (resultDist < 0) {
                    resultDist = 0;
                }
                this.ponderatedResults[cpt++] = resultDist;
            }
        }

        this.detectWall(box);
    }

    private Result<Creature>[] detectCreatures(List<Creature> creatures) {
        Result<Creature>[] closest = new Result[this.thingsToSee[0].length];
        for (int i = 0; i < closest.length; i++) {
            closest[i] = new Result<>(null, Integer.MAX_VALUE);
        }
        for (int i = 0; i < creatures.size(); i++) {
            Creature c = creatures.get(i);
            if (c != this.creature) {
                for (int j = 0; j < closest.length; j++) {
                    if (this.thingsToSee[0][j] == c.getType()) {
                        this.processDetection(c, closest[j]);
                    }
                }

            }
        }
        return closest;
    }

    private Result<Collectable>[] detectCollectables(List<Collectable> collectables) {
        Result<Collectable>[] closest = new Result[this.thingsToSee[1].length];
        for (int i = 0; i < closest.length; i++) {
            closest[i] = new Result<>(null, Integer.MAX_VALUE);
        }
        for (int i = 0; i < collectables.size(); i++) {
            Collectable c = collectables.get(i);
            for (int j = 0; j < closest.length; j++) {
                if (this.thingsToSee[1][j] == c.getType()) {
                    this.processDetection(c, closest[j]);
                }
            }
        }
        return closest;
    }

    private Result<Delimitation>[] detectDelimitations(List<Delimitation> delimitations) {
        Result<Delimitation>[] closest = new Result[this.thingsToSee[2].length];
        for (int i = 0; i < closest.length; i++) {
            closest[i] = new Result<>(null, Integer.MAX_VALUE);
        }
        for (int i = 0; i < delimitations.size(); i++) {
            Delimitation d = delimitations.get(i);
            for (int j = 0; j < closest.length; j++) {
                if (this.thingsToSee[2][j] == d.getType()) {
                    this.processDetection(d, closest[j]);
                }
            }
        }
        return closest;
    }

    protected abstract void detectWall(DelimitationBox box);


    private void processDetection(Creature c, Result<Creature> closest) {
        if (!this.checkIntersection(c)) {
            return;
        }
        double value = DistanceChecker.distance(this.creature, c);
        if (closest.getValue() > value) {
            closest.setValue(value);
            closest.setSeen(c);
        }
    }

    private void processDetection(Collectable c, Result<Collectable> closest) {
        if (!this.checkIntersection(c)) {
            return;
        }
        double value = DistanceChecker.distance(this.creature, c);
        if (closest.getValue() > value) {
            closest.setValue(value);
            closest.setSeen(c);
        }
    }

    private void processDetection(Delimitation d, Result<Delimitation> closest) {
        if (!this.checkIntersection(d)) {
            return;
        }
        double value = DistanceChecker.distance(this.creature, d);
        if (closest.getValue() > value) {
            closest.setValue(value);
            closest.setSeen(d);
        }
    }

    public double[] getResults() {
        return this.ponderatedResults;
    }

    protected boolean checkIntersection(Creature c) {
        if (!IntersectionsChecker.contains(this.around, c)) {
            return false;
        }
        return IntersectionsChecker.intersects(this.hitbox, c);
    }

    protected boolean checkIntersection(Collectable c) {
        if (!IntersectionsChecker.contains(this.around, c)) {
            return false;
        }
        return IntersectionsChecker.intersects(this.hitbox, c);
    }

    protected boolean checkIntersection(Delimitation d) {
        if (!IntersectionsChecker.contains(this.around, d)) {
            return false;
        }
        return IntersectionsChecker.intersects(this.hitbox, d);
    }

    public void setThingsToSee(int[][] thingsToSee) {
        this.thingsToSee = thingsToSee;
        this.resultCount = 0;
        if (thingsToSee[0].length > 0) {
            this.resultCount += 1;
            this.resultCount += thingsToSee[0].length;
        }
        if (thingsToSee[1].length > 0) {
            this.resultCount += thingsToSee[1].length;
        }
        if (thingsToSee[2].length > 0) {
            this.resultCount += thingsToSee[2].length;
        }
    }

    public int getResultCount() {
        return this.resultCount;
    }

}
