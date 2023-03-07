public class Position {
    public static Position zero = new Position(0, 0);

    int x;
    int y;

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(Position pos) {
        this.x += pos.x;
        this.y += pos.y;
    }

    public static Position add(Position pos1, Position pos2) {
        Position pos = new Position(pos1.x, pos1.y);
        pos.add(pos2);
        
        return pos;
    }
}
