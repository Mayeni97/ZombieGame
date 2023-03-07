import java.util.ArrayList;
import java.util.Arrays;

public class World {
    private int size;
    public int getSize() {
        return size;
    }

    private ArrayList<IEntity>[][] map;
    public ArrayList<IEntity>[][] getMap() {
        return map;
    }
    
    public World(int size) {
        this.size = size;
        map = new ArrayList[size][size];

        //Create our world map, a double array of ArrayList to hold multiple entities
        for (int y = 0; y < map.length; y ++) {
            for (int x = 0; x < map[y].length; x++) {
                map[y][x] = new ArrayList<IEntity>();
            }
        }
    }

    public void draw() {
        String divider = "------------------------------------------------------------";
        System.out.println(divider);

        for (int y = 0; y < map.length; y ++) {
            for (int x = 0; x < map[y].length; x++) {
                String symbol;

                if (map[y][x].isEmpty())
                    symbol = "-";
                else
                    symbol = Arrays.toString(map[y][x].toArray());

                    //System.out.format(" %6s [%s %s] ", symbol, x, y);
                    System.out.format(" %6s ", symbol);
                }
            System.out.println();
        }
        
        System.out.println(divider);
    }

    public boolean isInBounds(Position pos) {
        if (pos.x < 0 || pos.y < 0)
            return false;
        else if (pos.x > (size - 1) || pos.y > (size - 1))
            return false;
        else
            return true;
    }

    public void moveEntity(IEntity entity) {
        boolean found = false;

        //Remove current position
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x].remove(entity))
                {
                    found = true;
                    break;
                }
            }

            if (found)
                break;
        }
        
        Position newPos = entity.getPosition();

        map[newPos.y][newPos.x].add(entity);
    }

    public ArrayList<IEntity> getCollisions(IEntity entity) {
        Position pos = entity.getPosition();
        ArrayList<IEntity> collisions = new ArrayList<>();
        ArrayList<IEntity> occupants = map[pos.y][pos.x];

        if (occupants.size() > 1)
            collisions.addAll(occupants);
            
        return collisions;
    }

    public void handleCollisions(Game game, ArrayList<IEntity> collisions) {
        Player player = null;
        ArrayList<IEntity> others = new ArrayList<>();

        for (IEntity entity : collisions) {
            if (entity instanceof Player)
                player = (Player)entity;
            else
                others.add(entity);
        }

        //Process attacks first
        for (IEntity entity : others) {
            if (entity instanceof Zombie) {
                Zombie zombie = (Zombie)entity;
                zombie.attack(player);

                if (player.getHealth() == 0) {
                    game.gameOver();

                    break;
                }
            }
        }

        //Process escape 2nd
        for (IEntity entity : others) {
            if (entity instanceof Exit) {
                //Victory!
                game.victory();

                break;
            }
        }       

    }
    
}
