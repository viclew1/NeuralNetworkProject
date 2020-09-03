import UI.World;
import UI.worldsIMPL.WorldOfDodge;


public class Application {

    public static void main(String[] args) {
        World world = new WorldOfDodge(500, 500);

        world.start(40, 40, true);
    }

}
