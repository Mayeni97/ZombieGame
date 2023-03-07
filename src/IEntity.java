public interface IEntity {
    public String getName();
    public Position getPosition();
    public String draw();

    public void move(String direction);
}
