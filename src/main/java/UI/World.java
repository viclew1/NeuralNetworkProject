package UI;

import UI.controls.Controller;
import UI.controls.WorldController;
import collectables.Collectable;
import creatures.Creature;
import creatures.CreatureFactory;
import fr.lewon.Individual;
import fr.lewon.SelectionProcessor;
import fr.lewon.Trial;
import fr.lewon.exceptions.NNException;
import fr.lewon.nn.impl.NeuralNetworkClassic;
import fr.lewon.selection.SelectionType;
import interactions.CreaCollecInteraction;
import interactions.CreaCreaInteraction;
import interactions.CreaDelimInteraction;
import limitations.Delimitation;
import limitations.DelimitationBox;
import utils.Constantes;
import utils.Draftman;
import utils.IntersectionsChecker;
import zones.Zone;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.Constantes.*;

public abstract class World implements Trial {

    private final String name = "NEURAL NETWORK PROJECT";

    private JPanel jp;
    private int fpsToDraw;

    public List<Delimitation> delimitations;
    public List<Creature> creatures;
    public List<Collectable> collectables;
    public List<Zone> zones;
    public DelimitationBox box;

    protected int collectableAmount;
    protected int meatCount, vegetableCount, powerUpCount, fuelCount, bombCount;

    private Creature selectedCreature;

    /**
     * Cr�e un environnement de la taille de l'�cran
     */
    public World(String name) {
        this(name, Toolkit.getDefaultToolkit().getScreenSize());
    }

    /**
     * Cr�e un environnement de la taille de la dimension pass�e en argument
     *
     * @param dimension Dimension de l'image
     */
    public World(String name, Dimension dimension) {
        this(name, (int) dimension.getWidth(), (int) dimension.getHeight());
    }

    /**
     * Cr�e un environnement avec pour largeur et hauteur d'image les arguments pass�s
     *
     * @param w largeur de l'image
     * @param h hauteur de l'image
     */
    public World(String name, int w, int h) {
        this.init(w, h);
    }

    /**
     * Initialise l'environnement en fonction de la largeur / hauteur pass�s en argument
     *
     * @param w largeur
     * @param h hauteur
     */
    private void init(int w, int h) {
        this.creatures = new ArrayList<>(1000);
        this.collectables = new ArrayList<>(1000);
        this.delimitations = new ArrayList<>(1000);
        this.zones = new ArrayList<>(1000);

        this.jp = new JPanel() {

            private static final long serialVersionUID = -9087172006439142561L;

            @Override
            public void paint(Graphics g) {
                super.getRootPane().updateUI();
                Draftman draftman = new Draftman();
                if (DRAW_ALL) {
                    draftman.drawWorld(World.this.selectedCreature, World.this.creatures, World.this.collectables, World.this.delimitations, World.this.box, g);
                    g.drawString("FPS : " + World.this.fpsToDraw, 10, 15);
                }
                draftman.drawInfos(World.this.infosToPrint(), g);
            }

        };

        Controller ctrl = new WorldController(this);
        this.jp.addMouseListener(ctrl);
        this.jp.addMouseMotionListener(ctrl);
        this.jp.addKeyListener(ctrl);
        this.jp.addMouseWheelListener(ctrl);
        this.jp.setFocusable(true);

    }

    public void start(int x, int y) {
        this.start(x, y, true);
    }

    public void start(int x, int y, boolean visible) {
        this.box = new DelimitationBox(0, 0, x, y);

        SelectionProcessor sp = new SelectionProcessor(this, SelectionType.TOURNAMENT_8.getSelectionImpl(), 0.05, 0.75);

        List<Individual> brains = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            brains.add(new NeuralNetworkClassic(16, 2));
        }
        try {
            Individual best = sp.start(true, this.jp, brains, 2000, Integer.MAX_VALUE, 0);
            System.out.println("Best found : " + best.getFitness());
            while (true) {
                List<Individual> population = Arrays.asList(best);
                this.execute(population);
            }
        } catch (NNException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * Dessinateur
     */

    private List<String> infosToPrint() {
        List<String> infos = new ArrayList<>();
        infos.add((DRAW_CAPTORS ? "Cacher" : "Afficher") + " capteurs : S");
        infos.add((DRAW_HP ? "Cacher" : "Afficher") + " points de vie : H");
        infos.add((DRAW_ALL ? "Cacher" : "Afficher") + " la simulation : G");
        infos.add((SLOW_MO ? "Désactiver" : "Activer") + " le slow motion : V");
        infos.add((!PAUSE ? "Mettre en pause" : "Quitter la pause") + " : SPACE");
        return infos;
    }


    /**
     * M�thodes � impl�menter pour personnaliser le monde
     */

    protected abstract void initSelections();

    protected abstract void generateCollectables();

    protected abstract void generateDelimitations();

    /**
     * M�thodes de calcul de position des objets
     */

    private void sleepAndRefresh() {
        this.updateAndRemoveDelimitations();

        this.updateAndReplaceCollectables();

        this.generateDelimitations();

        this.updateCreatures();
    }


    private void updateAndRemoveDelimitations() {
        for (int i = 0; i < this.delimitations.size(); i++) {
            Delimitation d = this.delimitations.get(i);
            if (d == null) {
                continue;
            }
            if (d.isExpired() || !IntersectionsChecker.contains(this.box, d)) {
                this.delimitations.remove(i);
                i--;
            } else {
                d.update();
            }
        }
    }

    private void updateAndReplaceCollectables() {
        this.meatCount = 0;
        this.vegetableCount = 0;
        this.fuelCount = 0;
        this.powerUpCount = 0;

        for (int i = 0; i < this.collectables.size(); i++) {
            Collectable c = this.collectables.get(i);
            if (c.isConsumed() || !IntersectionsChecker.contains(this.box, c)) {
                this.collectables.remove(i--);
            } else {
                c.update();
                switch (c.getType()) {
                    case MEAT:
                        this.meatCount++;
                        break;
                    case VEGETABLE:
                        this.vegetableCount++;
                        break;
                    case FUEL:
                        this.fuelCount++;
                        break;
                    case POWERUP:
                        this.powerUpCount++;
                        break;
                    case BOMB:
                        this.bombCount++;
                        break;
                    default:
                        System.out.println("World.sleepAndRefresh - Collectable inconnu");
                }
            }
        }
        for (int i = this.collectables.size(); i < this.collectableAmount; i++) {
            this.generateCollectables();
        }
    }

    private void updateCreatures() {
        List<Creature> toRemove = new ArrayList<>();
        for (int i = 0; i < this.creatures.size(); i++) {
            Creature c = this.creatures.get(i);
            if (!c.isAlive() || !IntersectionsChecker.contains(this.box, c)) {
                toRemove.add(c);
                continue;
            }
            c.update();
            for (int j = 0; j < this.collectables.size(); j++) {
                Collectable collect = this.collectables.get(j);
                if (!collect.isConsumed()) {
                    if (IntersectionsChecker.preciseIntersects(c, collect)) {
                        new CreaCollecInteraction(c, collect).process();
                    }
                }
            }
            for (int j = 0; j < this.delimitations.size(); j++) {
                Delimitation delim = this.delimitations.get(j);
                if (!delim.isExpired()) {
                    if (IntersectionsChecker.preciseIntersects(c, delim)) {
                        new CreaDelimInteraction(c, delim).process();
                    }
                }
            }
            for (int j = 0; j < this.creatures.size(); j++) {
                Creature creature2 = this.creatures.get(j);
                if (creature2 == c) {
                    continue;
                }
                if (creature2.isAlive()) {
                    if (IntersectionsChecker.preciseIntersects(c, creature2) || IntersectionsChecker.intersects(c.getAttackHitbox(), creature2)) {
                        new CreaCreaInteraction(c, creature2).process();
                    }
                }
            }
        }
        for (Creature c : toRemove) {
            this.creatures.remove(c);
        }
    }


    /**
     * Actions disponibles pour le Controller
     */

    public void pause() {
        PAUSE = !PAUSE;
    }

    public void changeShowCaptors() {
        DRAW_CAPTORS = !DRAW_CAPTORS;
    }

    public void changeShowHP() {
        DRAW_HP = !DRAW_HP;
    }

    public void changeShowAll() {
        DRAW_ALL = !DRAW_ALL;
    }

    public void changeSlowMo() {
        SLOW_MO = !SLOW_MO;
    }

    public void selectCreature(Point point) {
        double x = point.getX();
        double y = point.getY();
        double realX = (x - SCROLL_X) / SIZE;
        double realY = (y - SCROLL_Y) / SIZE;

        for (int i = 0; i < this.creatures.size(); i++) {
            Creature c = this.creatures.get(i);
            double c_x = c.getX();
            double c_y = c.getY();
            double c_sz = c.getSize();
            if (c_x > realX) {
                continue;
            }
            if (c_y > realY) {
                continue;
            }
            if (c_x + c_sz < realX) {
                continue;
            }
            if (c_y + c_sz < realY) {
                continue;
            }
            this.selectedCreature = c;
            return;
        }
        this.selectedCreature = null;
    }

    @Override
    public void execute(List<Individual> population) throws NNException {
        this.delimitations.clear();
        this.creatures.clear();
        for (Individual individual : population) {
            Creature tested = CreatureFactory.generateSimpleDodger(individual, this);
            this.creatures.add(tested);
        }

        int fps = 0;
        long timeRefFps = System.nanoTime();
        long timeRefRecount = System.nanoTime();
        while (!this.creatures.isEmpty()) {
            if (!SLOW_MO || System.nanoTime() - timeRefFps > Constantes.TIME_TO_WAIT) {
                timeRefFps = System.nanoTime();
                if (!PAUSE) {
                    this.sleepAndRefresh();
                    fps++;
                } else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (System.nanoTime() - timeRefRecount > 1000000000) {
                timeRefRecount = System.nanoTime();
                this.fpsToDraw = fps;
                fps = 0;
            }
        }
    }

}
