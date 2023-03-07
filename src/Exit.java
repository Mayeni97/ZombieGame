public class Exit implements IEntity {
    private Position position;


    @Override
    public String getName() {
        return "Exit";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position pos) {
        this.position = pos;
    }

    public Exit(World world, Position pos) {
        setPosition(pos);
    }

    @Override
    public String draw() {
        return "X";
    }

    @Override
    public void move(String direction) {
        //Not implemented as example, our Exit doesn't move
    }
    
    @Override
    public String toString() {
        return draw();
    }

}
