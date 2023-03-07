public class Player implements IEntity, ILiving {
    private World world;
    
    private String name;
    public String getName() {
        return name;
    }

    @Override
    public String draw() {
        return "P";
    }
    
    private Position position;
    @Override
    public Position getPosition() {
        return position;
    }

    private int health;
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Player(World world, String name) {
        this.world = world;
        this.name = name;
        setHealth(100);
    }

    public void setPosition(Position pos) {
        this.position = pos;

        world.moveEntity(this);
    }

    @Override
    public void move(String direction) {
        Position pos = Game.movementMap.getOrDefault(direction, Position.zero);
        
        Position nextPos = Position.add(getPosition(), pos);

        if (!world.isInBounds(nextPos)) {
            System.out.println("You cannot move there.");

            return;
        }

        this.position = nextPos;
        
        world.moveEntity(this);
    }

    @Override
    public String toString() {
        return draw();
    }
}
