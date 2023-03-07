import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static HashMap<String, Position> movementMap = new HashMap<>();
    private World world;
    private Player player;
    private ArrayList<Zombie> zombies;
    private Exit exit;
    private Random random;

    private boolean isVictory;
    public void setIsVictory(boolean isVictory) {
        this.isVictory = isVictory;
    }

    private boolean isGameOver;
    public void setIsGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public boolean getIsEndOfGame() {
        return isVictory || isGameOver;
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();

        game.Run();
    }

    public Game() {
        random = new Random();

        movementMap = new HashMap<>();
        movementMap.put("w", new Position(0, -1));
        movementMap.put("a", new Position(-1, 0));
        movementMap.put("s", new Position(0, 1));
        movementMap.put("d", new Position(1, 0));
    }

    public void Run() {
        //Create world
        world = new World(7);
        //Create player
        player = new Ellie(world);
        //Starting position (random top row)
        Position startPos = new Position(random.nextInt(0, world.getSize()), 0);
        player.setPosition(startPos);

        //Create Exit and place in world
        Position exitPos = new Position(random.nextInt(0, world.getSize()), world.getSize() - 1);
        exit = new Exit(world, exitPos);
        world.moveEntity(exit);

        //Create zombies
        createZombies();

        //draw the game, world and entities
        draw();

        Scanner scanner = new Scanner(System.in);
        String input;

        //Print help
        System.out.println("Welcome to the Zombie game (Last of Us: CS210 Edition).");
        System.out.println("Press W/A/S/D to move up/left/down/right or Q to quit.");

        //Game loop
        while (true) {
            //Collect inputs
            System.out.print(">");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("q"))
                break;

            //Move Player
            player.move(input.toLowerCase());

            //Move Zombies
            moveZombies();
            
            //Check Collisions (only with player to keep it simple)
            //Not factoring zombie x zombie
            ArrayList<IEntity> collisions = world.getCollisions(player);

            //Draw the game after updates
            draw();

            //Process Collisions
            if (!collisions.isEmpty())
                world.handleCollisions(this, collisions);

            if (getIsEndOfGame()) {
                break;
            }
        }

        scanner.close();
    }

    public void draw() {
        world.draw();
    }

    public void createZombies() {
        zombies = new ArrayList<>();

        int minZombies = 2;
        int maxZombies = 6;

        for (int i = 0; i < random.nextInt(minZombies, maxZombies); i++) {
            //Randomize x and y, start 2 rows lower than player
            Position startPos = new Position(random.nextInt(0, world.getSize()),
                                            random.nextInt(2, world.getSize()));
            
            Zombie zombie = new Zombie(world, startPos);
            zombies.add(zombie);

            //Place in world
            world.moveEntity(zombie);
        }
    }

    public void moveZombies() {
        for (Zombie zombie : zombies) {
            //Get random direction to move in
            String randomDirection = movementMap.keySet()
                    .toArray(new String[movementMap.size()])[random.nextInt(movementMap.size())];
            
            zombie.move(randomDirection);
        }
    }

    public void gameOver() {
        isGameOver = true;

        System.out.println("Game Over.");
    }

    public void victory() {
        isVictory = true;

        System.out.format("Congratulations. %s survived with %d health.%n", player.getName(), player.getHealth());
    }
}
