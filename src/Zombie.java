public class Zombie implements IEntity, ILiving {
    private World world;
    
    private int attackDamage;
    public int getAttackDamage() {
        return attackDamage;
    }
    public void setAttackDamage(int value) {
        attackDamage = value;
    }

    @Override
    public String getName() {
        return "Zombie";
    }

    @Override
    public String draw() {
        return "Z";
    }

    private Position position;
    @Override
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position pos) {
        this.position = pos;
    }

    private int health;
    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public Zombie(World world, Position pos) {
        this.health = 100;
        this.attackDamage = 20;
        this.world = world;
        this.position = pos;
    }

    @Override
    public void move(String direction) {
        Position pos = Game.movementMap.getOrDefault(direction, Position.zero);
        
        Position nextPos = Position.add(getPosition(), pos);

        if (!world.isInBounds(nextPos)) {
            return;
        }

        this.position = nextPos;
        
        world.moveEntity(this);
    }

    public void attack(ILiving livingEntity) {
        //Prevent the health from going below 0
        int newHealth = Math.max(livingEntity.getHealth() - attackDamage, 0);

        livingEntity.setHealth(newHealth);

        System.out.format("%s attacks %s for %d damage. %s has %d health remaining.%n",
                        "Zombie",
                        livingEntity.getName(),
                        attackDamage,
                        livingEntity.getName(),
                        livingEntity.getHealth()
         );

         if (newHealth == 0) {
            System.out.format("%s has died.%n", livingEntity.getName());
         }
    }

    @Override
    public String toString() {
        return draw();
    }
}
